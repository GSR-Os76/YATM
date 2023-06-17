package com.gsr.gsr_yatm.block.device.extractor;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.YATMRecipeTypes;
import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.recipe.ExtractionRecipe;
import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.slot.SlotUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ExtractorBlockEntity extends BlockEntity
{
	public static final int DATA_SLOT_COUNT = 9;
	public static final int INVENTORY_SLOT_COUNT = 4;
	
	
	public static final int INPUT_SLOT = 0;
	public static final int INPUT_REMAINDER_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;
	public static final int POWER_SLOT = 3;

	public static final int EXTRACT_PROGRESS_SLOT = 0;
	public static final int EXTRACT_TIME_SLOT = 1;
	public static final int STORED_FlUID_AMOUNT_SLOT = 2;
	public static final int FLUID_CAPACITY_SLOT = 3;
	public static final int FLUID_TRANSFER_COUNT_DOWN_SLOT = 4;
	public static final int FLUID_TRANSFER_SIZE_SLOT = 5;
	public static final int STORED_POWER_SLOT = 6;
	public static final int POWER_CAPACITY_SLOT = 7;
	// TODO, add
	public static final int DATA_FLAGS_SLOT = 8;

	public static final String ACTIVE_RECIPE_TAG_NAME = "recipe";
	public static final String EXTRACT_PROGRESS_TAG_NAME = "extractProgress";
	public static final String EXTRACT_TIME_TAG_NAME = "extractTime";
	public static final String POWER_TAG_NAME = "power";
	public static final String INVENTORY_TAG_NAME = "inventory";
	public static final String TANK_TAG_NAME = "resultTank";


	
	private String m_activeRecipeIdentifier;
	private int m_extractProgress = 0;
	private int m_extractTime = 0;
	
	private int m_flags = 0;
	
	// TODO, allegedly can transfer at most the lowest 16 bits, to show reasonable amounts of power somewheres maybe use two then and merge on client, confirm this detail
	private ContainerData m_data = new ContainerData()
	{

		@Override
		public int get(int index)
		{
			switch (index)
			{
				case EXTRACT_PROGRESS_SLOT:
				{
					return m_extractProgress;
				}
				case EXTRACT_TIME_SLOT:
				{
					return m_extractTime;
				}				
				case STORED_FlUID_AMOUNT_SLOT:
				{
					return m_resultTank.getFluidAmount();
				}
				case FLUID_CAPACITY_SLOT:
				{
					return m_resultTank.getCapacity();
				}				
				case FLUID_TRANSFER_COUNT_DOWN_SLOT:
				{
					return m_resultTankDrainCountDown;
				}
				case FLUID_TRANSFER_SIZE_SLOT:
				{
					return m_initialDrainResultTankTransferSize;
				}
				case STORED_POWER_SLOT:
				{
					return m_internalCurrentStorer.storedCapacity();
				}
				case POWER_CAPACITY_SLOT:
				{
					return m_internalCurrentStorer.storageCapacity();
				}
				
				case DATA_FLAGS_SLOT:
				{
					return m_flags;
				}
				default:
				{
					throw new IndexOutOfBoundsException("get index of: " + index + ", is out of the range");
				}
			}
		} // end get()

		@Override
		public void set(int index, int value)
		{
			switch (index)
			{
				case EXTRACT_PROGRESS_SLOT:
				{
					m_extractProgress = value;
					break;
				}
				case EXTRACT_TIME_SLOT:
				{
					m_extractTime = value;
					break;
				}				
				case STORED_FlUID_AMOUNT_SLOT:
				{
					return;// m_resultTank.getFluidAmount();
				}
				case FLUID_CAPACITY_SLOT:
				{
					return;// m_resultTank.getCapacity();
				}	
				case FLUID_TRANSFER_COUNT_DOWN_SLOT:
				{
					m_resultTankDrainCountDown = value;
					break;
				}
				case FLUID_TRANSFER_SIZE_SLOT:
				{
					m_initialDrainResultTankTransferSize = value;
					break;
				}
				case STORED_POWER_SLOT:
				{
					return;// m_storedPower = value;
				}
				case POWER_CAPACITY_SLOT:
				{
					return;// m_powerCapacity;
				}
				case DATA_FLAGS_SLOT:
				{
					m_flags = value;
					break;
				}
				default:
				{
					throw new IndexOutOfBoundsException("get index of: " + index + ", is out of the range");
				}
			}
		} // end set()

		@Override
		public int getCount()
		{
			return DATA_SLOT_COUNT;
		} // end getCount()

	};
	
	
	
	private static final int RECHECK_CRAFTING_PERIOD = 20;
	private int m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
	private ExtractionRecipe m_activeRecipe = null;

	
	
	
	private ItemStackHandler m_rawInventory = new ItemStackHandler(INVENTORY_SLOT_COUNT);
	private ConfigurableInventoryWrapper m_uncheckedInventory = new ConfigurableInventoryWrapper.Builder(this.m_rawInventory).onInsertion(this::onItemInsertion).onWithdrawal(this::onItemWithdrawal).build();
	private ConfigurableInventoryWrapper m_inventory = new ConfigurableInventoryWrapper.Builder(this.m_uncheckedInventory).slotValidator(this::itemInsertionValidator).build();
	
	private FluidTank m_resultTank;
	
	private CurrentUnitHandler m_internalCurrentStorer;
	
	private final int m_maxDrainRate;
	private final int m_maxCurrentTransfer;
	private int m_currentTransferedThisTick = 0;
	
	// TODO, possible save this, possibly, nice but not game breaking
	private int m_resultTankDrainCountDown = 0;
	private int m_initialDrainResultTankTransferSize = 0;
	
	
	
	public ExtractorBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 32768, 4000, 128, 32);
	} // end constructor

	
	public ExtractorBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int fluidCapacity, int maxCurrentTransfer, int maxDrainRate)
	{
		super(YATMBlockEntityTypes.EXTRACTOR_BLOCK_ENTITY.get(), blockPos, blockState);
		this.m_resultTank = new FluidTank(fluidCapacity);
		this.m_internalCurrentStorer = new CurrentUnitHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxCurrentTransfer = maxCurrentTransfer;
		this.m_maxDrainRate = maxDrainRate;
	} // end constructor

	
	
	public IItemHandler getInventory()
	{
		return this.m_inventory;
	} // end getInventory()

	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
	public static void tick(Level level, BlockPos blockPos, BlockState blockState, ExtractorBlockEntity blockEntity)
	{
		if (!level.isClientSide)
		{
			blockEntity.serverTick(level, blockPos, blockState, blockEntity);
		}
	} // end tick()

	private void serverTick(Level level, BlockPos blockPos, BlockState blockState, ExtractorBlockEntity blockEntity)
	{
		if(this.m_currentTransferedThisTick > m_maxCurrentTransfer) 
		{
			// TODO, blow this up
		}
		else 
		{
			this.m_currentTransferedThisTick = 0;
		}
		
		// TODO, accept power from power slot if possible
		
		if (this.m_extractProgress > 0)
		{
			// TODO, do power things, later after other test're done
			if( --this.m_extractProgress == 0) 
			{
				// things are changed, be sure they'll be save; maybe move this to the end so the whole method definitely only does it once preventing repeats
				this.setChanged();
				
				if(this.m_activeRecipeIdentifier != null &&
						this.m_activeRecipe == null 
						? this.loadRecipe(level) 
								: true	) 
				{
					this.m_activeRecipe.setResults(this.m_uncheckedInventory, this.m_resultTank);
					this.tryStartNewRecipe();
				}				
			}
		}
		else if (this.m_timeSinceRecheck++ > RECHECK_CRAFTING_PERIOD)
		{
			this.m_timeSinceRecheck = 0;
			this.tryStartNewRecipe();
		}

		// move fluid to drain slot as's appropriate
		// if drain slot has item
		// see if that item can accept at the max rate
			// if so prepare to do that thing
		// else see if it can accept at 1000 mb
			// if so prepare to do that thing, do so by counting down according to the max drain rate, then do the moving thing
	
		// queue to drain the input tank into the slot
				if (!this.m_inventory.getStackInSlot(DRAIN_RESULT_TANK_SLOT).isEmpty() && this.m_resultTank.getFluidAmount() > 0 && this.m_initialDrainResultTankTransferSize <= 0)
				{
					if (SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_RESULT_TANK_SLOT), this.m_resultTank, Math.min(this.m_resultTank.getFluidAmount(), this.m_maxDrainRate)))
					{
						this.m_resultTankDrainCountDown = Math.min(this.m_resultTank.getFluidAmount(), this.m_maxDrainRate);
						this.m_initialDrainResultTankTransferSize = m_resultTankDrainCountDown;
					}
					else if (this.m_resultTank.getFluidAmount() >= 1000 && SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_RESULT_TANK_SLOT), this.m_resultTank, 1000))
					{
						this.m_resultTankDrainCountDown = 1000;
						this.m_initialDrainResultTankTransferSize = m_resultTankDrainCountDown;
					}
				}
				
				if (this.m_resultTankDrainCountDown > 0)
				{
					this.m_resultTankDrainCountDown -= this.m_maxDrainRate;
				}
				else if (this.m_resultTankDrainCountDown <= 0 && this.m_initialDrainResultTankTransferSize > 0)
				{
					if (SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_RESULT_TANK_SLOT), this.m_resultTank, this.m_initialDrainResultTankTransferSize))
					{
						this.m_rawInventory.insertItem(DRAIN_RESULT_TANK_SLOT, SlotUtilities.drainTankTo(this.m_inventory.extractItem(DRAIN_RESULT_TANK_SLOT, this.m_inventory.getSlotLimit(DRAIN_RESULT_TANK_SLOT), false), this.m_resultTank, 1000), false);
					}
					this.m_resultTankDrainCountDown = 0;
					this.m_initialDrainResultTankTransferSize = 0;
				}
		
	} // end serverTick()

	private boolean loadRecipe(Level level)
	{
		List<ExtractionRecipe> recipes = level.getRecipeManager().getAllRecipesFor(YATMRecipeTypes.EXTRACTION_RECIPE_TYPE.get());
		for (ExtractionRecipe r : recipes)
		{
			if (r.getId().toString() == this.m_activeRecipeIdentifier)
			{
				this.m_activeRecipe = r;
				return true;
			}
		}
		return false;
	} // end loadRecipe()

	private void tryStartNewRecipe() 
	{
		this.m_activeRecipeIdentifier = null;
		this.m_activeRecipe = null;
		this.m_extractTime = 0;
		this.m_extractProgress = 0;
		this.m_flags &= ~Flags.CURRENT_RECIPE_HAS_REMAINDER.FLAG;
		
		List<ExtractionRecipe> recipes = level.getRecipeManager().getAllRecipesFor(YATMRecipeTypes.EXTRACTION_RECIPE_TYPE.get());
		for (ExtractionRecipe r : recipes)
		{
			if (r.canBeUsedOn(this.m_rawInventory, this.m_resultTank))
			{
				this.m_activeRecipeIdentifier = r.getId().toString();
				this.m_activeRecipe = r;
				this.m_extractTime = r.getTimeInTicks();
				this.m_extractProgress = this.m_extractTime;
				if(r.hasRemainder()) 
				{
					this.m_flags |= Flags.CURRENT_RECIPE_HAS_REMAINDER.FLAG;
				}
				r.startRecipe(this.m_uncheckedInventory, this.m_resultTank);
				this.setChanged();
			}
		}
	} // end tryStartNewRecipe()
	
	
	
	private Boolean itemInsertionValidator(int slot, ItemStack toBeInserted, Boolean simulated) 
	{
		if(slot == INPUT_REMAINDER_SLOT) 
		{
			return false;
		}
		if(slot == DRAIN_RESULT_TANK_SLOT) 
		{
			return SlotUtilities.isValidTankDrainSlotInsert(toBeInserted);
		}
		return true;
	} // end itemInsertionValidator()
	
	private void onItemInsertion(int slot, ItemStack inserted) 
	{
		if(slot == INPUT_SLOT) 
		{
			this.m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
		}
		this.setChanged();
	} // end onItemInsertion()
	
	private void onItemWithdrawal(int slot, int withDrawn) 
	{
		if(slot == INPUT_REMAINDER_SLOT) 
		{
			this.m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
		}
		this.setChanged();
	} // end onItemWithdrawal()
	
	private void onCurrentExchanged(int amount) 
	{
		// explode if exceeding max current
		this.m_currentTransferedThisTick += amount;
		this.setChanged();
	} // end onCurrentExchanged()
	
	
	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		if(this.m_activeRecipeIdentifier != null) 
		{
			tag.putString(ACTIVE_RECIPE_TAG_NAME, this.m_activeRecipeIdentifier);
		}		
		tag.putInt(EXTRACT_PROGRESS_TAG_NAME, this.m_extractProgress);
		tag.putInt(EXTRACT_TIME_TAG_NAME, this.m_extractTime);
		tag.put(POWER_TAG_NAME, this.m_internalCurrentStorer.serializeNBT());
		tag.put(INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
		tag.put(TANK_TAG_NAME, this.m_resultTank.writeToNBT(new CompoundTag()));
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(ACTIVE_RECIPE_TAG_NAME)) 
		{
			this.m_activeRecipeIdentifier = tag.getString(ACTIVE_RECIPE_TAG_NAME);
		}
		if(tag.contains(EXTRACT_PROGRESS_TAG_NAME)) 
		{
			this.m_extractProgress = tag.getInt(EXTRACT_PROGRESS_TAG_NAME);
		}
		if(tag.contains(EXTRACT_TIME_TAG_NAME)) 
		{
			this.m_extractTime = tag.getInt(EXTRACT_TIME_TAG_NAME);
		}
		if(tag.contains(POWER_TAG_NAME)) 
		{
			this.m_internalCurrentStorer.deserializeNBT(tag.getCompound(POWER_TAG_NAME));
		}
		if(tag.contains(INVENTORY_TAG_NAME)) 
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(INVENTORY_TAG_NAME));
		}
		if(tag.contains(TANK_TAG_NAME)) 
		{
			this.m_resultTank.readFromNBT(tag.getCompound(TANK_TAG_NAME));
		}
	} // end load()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		// front face or right go into drain
		// top or left go into input slot
		// bottom remainder output
		// back go current
		// add configuration soon
		
		// TODO Auto-generated method stub
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps()
	{
		// TODO Auto-generated method stub
		super.invalidateCaps();
	}

		@Override
	public void reviveCaps()
	{
		// TODO Auto-generated method stub
		super.reviveCaps();
	}

		
		
		public static enum Flags
		{
			CURRENT_RECIPE_HAS_REMAINDER(0x0000_0000_0000_0000_0000_0000_0000_0001);
			
			public final int FLAG;
			
			private Flags(int flag) 
			{
				this.FLAG = flag;
			}
		} // end Flags
		
} // end class