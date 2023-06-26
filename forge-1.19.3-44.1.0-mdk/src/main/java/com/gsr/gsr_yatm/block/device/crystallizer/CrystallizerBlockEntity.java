package com.gsr.gsr_yatm.block.device.crystallizer;

import java.util.List;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.YATMBlocks;
import com.gsr.gsr_yatm.YATMRecipeTypes;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.CrystallizationRecipe;
import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.NetworkUtilities;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;
import com.gsr.gsr_yatm.utilities.SlotUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CrystallizerBlockEntity extends BlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	public static final int DATA_SLOT_COUNT = 10;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int SEED_SLOT = 2;
	public static final int RESULT_SLOT = 3;
	public static final int POWER_SLOT = 4;

	
	
	public static final int CRYSTALLIZE_PROGRESS_SLOT = 0;
	public static final int CRYSTALLIZE_TIME_SLOT = 1;
	
	public static final int FLUID_AMOUNT_SLOT = 2;
	public static final int FLUID_CAPACITY_SLOT = 3;
	
	public static final int FLUID_INDEX_LOW_SLOT = 4;
	public static final int FLUID_INDEX_HIGH_SLOT = 5;
	
	public static final int FILL_INPUT_TANK_TRANSFER_PROGRESS = 6;
	public static final int FILL_INPUT_TANK_TRANSFER_INITIAL = 7;
	public static final int DRAIN_INPUT_TANK_TRANSFER_PROGRESS = 8;
	public static final int DRAIN_INPUT_TANK_TRANSFER_INITIAL = 9;
	
	
	
	public static final String ACTIVE_RECIPE_TAG_NAME = "recipe";
	public static final String CRYSTALLIZE_PROGRESS_TAG_NAME = "crsytallizeProgress";
	public static final String CRYSTALLIZE_TIME_TAG_NAME = "crystallizeTime";
	public static final String FILL_INPUT_TRANSFER_BUFFER_TAG_NAME = "fillInputBuffer";
	public static final String FILL_INPUT_TRANSFER_INITIAL_TAG_NAME = "fillInputInitial";
	public static final String DRAIN_INPUT_COUNT_DOWN_TAG_NAME = "drainInputCount";
	public static final String DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME = "drainInputInitial";
	public static final String INPUT_TANK_TAG_NAME = "inputTank";
	public static final String INVENTORY_TAG_NAME = "inventory";


	private ItemStackHandler m_rawInventory = new ItemStackHandler(INVENTORY_SLOT_COUNT);
	private ConfigurableInventoryWrapper m_uncheckedInventory = new ConfigurableInventoryWrapper.Builder(this.m_rawInventory).onInsertion(this::onItemInsertion).onWithdrawal(this::onItemWithdrawal).build();
	private ConfigurableInventoryWrapper m_inventory = new ConfigurableInventoryWrapper.Builder(this.m_uncheckedInventory).slotValidator(this::validateSlotInsertion).build();
	
	private FluidTank m_rawInputTank;
	private ConfigurableTankWrapper m_inputTank;
	
	private int m_maxTransferRate;
	
	

	
	private static final int RECHECK_CRAFTING_PERIOD = RecipeUtilities.RECHECK_CRAFTING_PERIOD;
	private int m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
	private String m_activeRecipeIdentifier = null;
	private CrystallizationRecipe m_activeRecipe = null;
	private boolean m_tickWasPowered = false;
	
	private int m_crystallizeProgress = 0;
	private int m_crystallizeTime = 0;
	private FluidTank m_fillInputTankBuffer;
	private int m_fillInputTankInitialTransferSize = 0;
	private int m_drainInputTankCountDown = 0;
	private int m_drainInputTankInitialTransferSize = 0;
	
	private final ContainerData m_data = new ContainerData()
	{
		@Override
		public int get(int value)
		{
			return switch(value) 
				{
				case CrystallizerBlockEntity.CRYSTALLIZE_PROGRESS_SLOT -> CrystallizerBlockEntity.this.m_crystallizeProgress;
				case CrystallizerBlockEntity.CRYSTALLIZE_TIME_SLOT -> CrystallizerBlockEntity.this.m_crystallizeTime;
				
				case CrystallizerBlockEntity.FLUID_AMOUNT_SLOT -> CrystallizerBlockEntity.this.m_inputTank.getFluidAmount();
				case CrystallizerBlockEntity.FLUID_CAPACITY_SLOT -> CrystallizerBlockEntity.this.m_inputTank.getCapacity();
				
				case CrystallizerBlockEntity.FLUID_INDEX_LOW_SLOT -> NetworkUtilities.splitInt(NetworkUtilities.getFluidIndex(CrystallizerBlockEntity.this.m_rawInputTank.getFluid().getFluid()), false);
				case CrystallizerBlockEntity.FLUID_INDEX_HIGH_SLOT -> NetworkUtilities.splitInt(NetworkUtilities.getFluidIndex(CrystallizerBlockEntity.this.m_rawInputTank.getFluid().getFluid()), true);
				
				case CrystallizerBlockEntity.FILL_INPUT_TANK_TRANSFER_PROGRESS -> CrystallizerBlockEntity.this.m_fillInputTankBuffer.getFluidAmount();
				case CrystallizerBlockEntity.FILL_INPUT_TANK_TRANSFER_INITIAL -> CrystallizerBlockEntity.this.m_fillInputTankInitialTransferSize;
				case CrystallizerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_PROGRESS -> CrystallizerBlockEntity.this.m_drainInputTankCountDown;
				case CrystallizerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_INITIAL -> CrystallizerBlockEntity.this.m_drainInputTankInitialTransferSize;
				
				default ->	throw new IllegalArgumentException("Unexpected index of: " + value);
				};
		} // end get()

		@Override
		public void set(int index, int value)
		{
			// client should not be allowed to modify our state through any of the provided data, it should be one way for rendering is all. Theoretically
			return;
		} // end set()

		@Override
		public int getCount()
		{
			return CrystallizerBlockEntity.DATA_SLOT_COUNT;
		} // end getCount()
	};
	
	
	
	public CrystallizerBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, YATMBlocks.STEEL_DEVICE_TANK_CAPACITY, YATMBlocks.STEEL_MAXIMUM_FLUID_TRANSFER_RATE);
	} // end constructor
	
	public CrystallizerBlockEntity(BlockPos blockPos, BlockState blockState, int tankCapacities, int maxFluidTransferRate)
	{
		super(YATMBlockEntityTypes.CRYSTALLIZER.get(), blockPos, blockState);
		
		this.m_maxTransferRate = maxFluidTransferRate;
		
		this.m_rawInputTank = new FluidTank(tankCapacities);
		this.m_fillInputTankBuffer = new FluidTank(tankCapacities);
		
		this.m_inputTank = new ConfigurableTankWrapper(this.m_rawInputTank, this::onFluidContentsChanged);
	} // end constructor
	
	
	
	public IItemHandler getInventory()
	{
		return this.m_inventory;
	} // end getInventory()

	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
	private boolean validateSlotInsertion(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch(slot) 
				{
					case CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT -> SlotUtilities.isValidTankFillSlotInsert(itemStack);
					case CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT -> SlotUtilities.isValidTankDrainSlotInsert(itemStack);
					case CrystallizerBlockEntity.RESULT_SLOT -> false;
					default -> true;
				};
	} // end validateSlotInsertion()

	private void onItemInsertion(int slot, ItemStack itemStack)
	{
		this.setChanged();
	} // end onItemInsertion()

	private void onItemWithdrawal(int slot, int amount)
	{
		this.setChanged();
	} // onItemWithdrawal
	
	private void onFluidContentsChanged() 
	{
		this.setChanged();
	} // end onFluidContentsChanged()
	
	
	
	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CrystallizerBlockEntity blockEntity)
	{
		if(!level.isClientSide) 
		{
			blockEntity.serverTick(level, blockPos, blockState);
		}
	} // end tick()

	private void serverTick(Level level, BlockPos blockPos, BlockState blockState) 
	{
		// TODO, fix recipe loading, seems this ticks before the recipes are ever loaded
		if(this.m_activeRecipe == null && this.m_activeRecipeIdentifier != null) 
		{
			this.m_activeRecipe = RecipeUtilities.loadRecipe(this.m_activeRecipeIdentifier, level, YATMRecipeTypes.CRYSTALLIZATION_RECIPE_TYPE.get());
			this.m_activeRecipeIdentifier = null;
			if(this.m_activeRecipe == null) 
			{
				YetAnotherTechMod.LOGGER.info("failed to load the recipe");
			}
			else 
			{
				YetAnotherTechMod.LOGGER.info("succeed to load the recipe with id: " + this.m_activeRecipe.getId());
			}
			this.m_crystallizeProgress = 0;
			this.m_crystallizeTime = 0;
		}

		boolean changed = this.doAcceptPower();
		changed |= this.doFillInputTank();
		changed |= this.doCrafting();
		changed |= this.doDrainInputTank();
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doAcceptPower() 
	{
		boolean changed = false;
		
		this.m_tickWasPowered = false;
		// if the power slot has an item that can power this, and the recipe needs power
		// take the needed amount of power from the slot, and if the amount taken's the full required amount, mark this tick as powered
		int tickRequiredPower = this.m_activeRecipe != null ? this.m_activeRecipe.getCurrentPerTick() : 0;
		this.m_tickWasPowered = SlotUtilities.tryToPower(this.m_inventory, CrystallizerBlockEntity.POWER_SLOT, tickRequiredPower);
		
		return changed;
	} // end doAcceptPower()
	
	private boolean doFillInputTank() 
	{
		boolean changed = false;
		if(this.m_fillInputTankBuffer.getFluidAmount() <= 0) 
		{
			this.m_fillInputTankInitialTransferSize = SlotUtilities.queueToFillFromSlot(this.level, this.worldPosition, this.m_inventory, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_fillInputTankBuffer, this.m_maxTransferRate);
			if(this.m_fillInputTankInitialTransferSize > 0) 
			{
				changed = true;
			}
		}
		if (this.m_fillInputTankBuffer.getFluidAmount() > 0)
		{
			this.m_inputTank.fill(this.m_fillInputTankBuffer.drain(this.m_maxTransferRate, FluidAction.EXECUTE), FluidAction.EXECUTE);
			if (this.m_fillInputTankBuffer.getFluidAmount() <= 0)
			{
				this.m_fillInputTankInitialTransferSize = 0;
			}
			changed = true;
		}
		return changed;
	}// end doFillInputTank() 

	private boolean doCrafting() 
	{
		boolean changed = false;
		
		if (this.m_crystallizeProgress > 0)
		{
			if (this.m_activeRecipe == null) 
			{
				this.m_timeSinceRecheck = 0;
				this.tryStartNewRecipe();
			}
			else if (this.m_tickWasPowered && --this.m_crystallizeProgress <= 0)
			{
				this.m_activeRecipe.setResults(this.m_uncheckedInventory);
				this.tryStartNewRecipe();
			}
			changed = true;
		}
		else if (++this.m_timeSinceRecheck > RECHECK_CRAFTING_PERIOD)
		{
			this.m_timeSinceRecheck = 0;
			this.tryStartNewRecipe();
			changed = true;
		}		
		return changed;
	} // end doCrafting()

	private void tryStartNewRecipe()
	{
		this.m_activeRecipe = null;
		this.m_crystallizeTime = 0;
		this.m_crystallizeProgress = 0;
		List<CrystallizationRecipe> recipes = level.getRecipeManager().getAllRecipesFor(YATMRecipeTypes.CRYSTALLIZATION_RECIPE_TYPE.get());
		for (CrystallizationRecipe r : recipes)
		{
			YetAnotherTechMod.LOGGER.info("considering a recipe to start: " + r.getId());
			if (r.canBeUsedOn(this.m_uncheckedInventory, this.m_inputTank))
			{
				this.m_activeRecipe = r;
				this.m_crystallizeTime = r.getTimeInTicks();
				this.m_crystallizeProgress = this.m_crystallizeTime;
				r.startRecipe(this.m_inventory, this.m_inputTank);
			}
		}
	} // end tryStartNewRecipe()	

	private boolean doDrainInputTank() 
	{
		boolean changed = false;		
		if (this.m_drainInputTankCountDown > 0)
		{
			this.m_drainInputTankCountDown = SlotUtilities.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_drainInputTankInitialTransferSize, this.m_drainInputTankCountDown, this.m_maxTransferRate);
			if (this.m_drainInputTankCountDown <= 0)
			{
				this.m_drainInputTankInitialTransferSize = 0;
			}
			changed = true;
		}
		if(m_drainInputTankInitialTransferSize == 0) 
		{
			this.m_drainInputTankInitialTransferSize = SlotUtilities.queueToDrainToSlot(this.m_inventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_maxTransferRate);
			this.m_drainInputTankCountDown = this.m_drainInputTankInitialTransferSize;
			
			changed = true;
		}
		return changed;
	} // end doDrainInputTank()
	
	

	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.put(INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
		if(this.m_rawInputTank.getFluidAmount() > 0) 
		{
			tag.put(INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
		}
		if(this.m_activeRecipe != null) 
		{
			tag.putString(ACTIVE_RECIPE_TAG_NAME, this.m_activeRecipe.getId().toString());
		}
		if(this.m_crystallizeProgress > 0 && this.m_crystallizeTime > 0) 
		{
			tag.putInt(CRYSTALLIZE_PROGRESS_TAG_NAME, this.m_crystallizeProgress);
			tag.putInt(CRYSTALLIZE_TIME_TAG_NAME, this.m_crystallizeTime);
		}
		if(this.m_fillInputTankBuffer.getFluidAmount() > 0 && this.m_fillInputTankInitialTransferSize > 0)
		{
			tag.put(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME, this.m_fillInputTankBuffer.writeToNBT(new CompoundTag()));
			tag.putInt(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_fillInputTankInitialTransferSize);
		}
		if(this.m_drainInputTankCountDown > 0 && this.m_drainInputTankInitialTransferSize > 0) 
		{
			tag.putInt(DRAIN_INPUT_COUNT_DOWN_TAG_NAME, this.m_drainInputTankCountDown);
			tag.putInt(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_drainInputTankInitialTransferSize);
		}
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(INVENTORY_TAG_NAME)) 
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(INVENTORY_TAG_NAME));
		}
		if(tag.contains(INPUT_TANK_TAG_NAME)) 
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(INPUT_TANK_TAG_NAME));
		}
		if(tag.contains(ACTIVE_RECIPE_TAG_NAME)) 
		{
			this.m_activeRecipeIdentifier = tag.getString(ACTIVE_RECIPE_TAG_NAME);
			YetAnotherTechMod.LOGGER.info("loaded recipe identifier: " + this.m_activeRecipeIdentifier);
		}
		if(tag.contains(CRYSTALLIZE_PROGRESS_TAG_NAME) && tag.contains(CRYSTALLIZE_TIME_TAG_NAME)) 
		{
			this.m_crystallizeProgress = tag.getInt(CRYSTALLIZE_PROGRESS_TAG_NAME);
			this.m_crystallizeTime = tag.getInt(CRYSTALLIZE_TIME_TAG_NAME);
		}
		if(tag.contains(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME) && tag.contains(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME)) 
		{
			this.m_fillInputTankBuffer.readFromNBT(tag.getCompound(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME));
			this.m_fillInputTankInitialTransferSize = tag.getInt(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
		if(tag.contains(DRAIN_INPUT_COUNT_DOWN_TAG_NAME) && tag.contains(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME)) 
		{
			this.m_drainInputTankCountDown = tag.getInt(DRAIN_INPUT_COUNT_DOWN_TAG_NAME);
			this.m_drainInputTankInitialTransferSize = tag.getInt(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}		
	} // end load()
	
} // end class