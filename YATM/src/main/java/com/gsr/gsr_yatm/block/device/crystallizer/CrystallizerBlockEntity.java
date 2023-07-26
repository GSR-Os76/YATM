package com.gsr.gsr_yatm.block.device.crystallizer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.SlotUtilities;
import com.gsr.gsr_yatm.utilities.network.NetworkUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CrystallizerBlockEntity extends CraftingDeviceBlockEntity<CrystallizingRecipe, Container>
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
	
	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";

	public static final String FILL_INPUT_TRANSFER_BUFFER_TAG_NAME = "fillInputBuffer";
	public static final String FILL_INPUT_TRANSFER_INITIAL_TAG_NAME = "fillInputInitial";
	public static final String DRAIN_INPUT_COUNT_DOWN_TAG_NAME = "drainInputCount";
	public static final String DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME = "drainInputInitial";
	public static final String INPUT_TANK_TAG_NAME = "inputTank";


	
	private int m_maxFluidTransferRate;

	private FluidTank m_rawInputTank;
	private ConfigurableTankWrapper m_inputTank;
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
				case CrystallizerBlockEntity.CRYSTALLIZE_PROGRESS_SLOT -> CrystallizerBlockEntity.this.m_craftProgress;
				case CrystallizerBlockEntity.CRYSTALLIZE_TIME_SLOT -> CrystallizerBlockEntity.this.m_craftTime;
				
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
			// TODO, learn and consider, what if this is used to sync to the client BlockEntity and back to through the menu?
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
		this(blockPos, blockState, 0, 0);
	} // end constructor
	
	public CrystallizerBlockEntity(BlockPos blockPos, BlockState blockState, int tankCapacities, int maxFluidTransferRate)
	{
		super(YATMBlockEntityTypes.CRYSTALLIZER.get(), blockPos, blockState, CrystallizerBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.CRYSTALLIZING.get());
		this.setup(tankCapacities, maxFluidTransferRate);
	} // end constructor
	
	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(TANK_CAPACITY_TAG_NAME, this.m_rawInputTank.getCapacity());
		tag.putInt(MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		int fluidCapacity = 0;
		int maxFluidTransferRate = 0;
		
		if(tag.contains(TANK_CAPACITY_TAG_NAME)) 
		{
			fluidCapacity = tag.getInt(TANK_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_FLUID_TRANSFER_RATE_TAG_NAME)) 
		{
			maxFluidTransferRate = tag.getInt(MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		this.setup(fluidCapacity, maxFluidTransferRate);
	} // end setupFromNBT()
	
	private void setup(int tankCapacities, int maxFluidTransferRate) 
	{
		this.m_maxFluidTransferRate = maxFluidTransferRate;		
		this.m_fillInputTankBuffer = new FluidTank(tankCapacities);		
		this.m_rawInputTank = new FluidTank(tankCapacities);
		this.m_inputTank = new ConfigurableTankWrapper(this.m_rawInputTank, this::onFluidContentsChanged);
	} // end setup()
	
	
	
	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch(slot) 
				{
					case CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT -> SlotUtilities.isValidTankFillSlotInsert(itemStack);
					case CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT -> SlotUtilities.isValidTankDrainSlotInsert(itemStack);
					case CrystallizerBlockEntity.RESULT_SLOT -> false;
					default -> true;
				};
	} // end validateSlotInsertion()

	
	
	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState) 
	{
		super.serverTick(level, blockPos, blockState);
		
		boolean changed = this.doAcceptPower();
		changed |= this.doFillInputTank();
		changed |= this.m_waitingForLoad 
				? false 
				: this.m_activeRecipe != null && this.m_currentTransferedThisTick < this.m_activeRecipe.getCurrentPerTick() 
					? false 
					: this.doCrafting();
		changed |= this.doDrainInputTank();
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doAcceptPower() 
	{
		boolean changed = false;
		
		// if the power slot has an item that can power this, and the recipe needs power
		// take the needed amount of power from the slot, and if the amount taken's the full required amount, mark this tick as powered
		// alternatively take as much as possible always, if it's wasting power or it overloads the device, so be it, recording it in to m_currentTransferedThisTick
		return changed;
	} // end doAcceptPower()
	
	private boolean doFillInputTank() 
	{
		boolean changed = false;
		if(this.m_fillInputTankBuffer.getFluidAmount() <= 0) 
		{
			this.m_fillInputTankInitialTransferSize = SlotUtilities.queueToFillFromSlot(this.level, this.worldPosition, this.m_inventory, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_fillInputTankBuffer, this.m_maxFluidTransferRate);
			if(this.m_fillInputTankInitialTransferSize > 0) 
			{
				changed = true;
			}
		}
		if (this.m_fillInputTankBuffer.getFluidAmount() > 0)
		{
			this.m_inputTank.fill(this.m_fillInputTankBuffer.drain(this.m_maxFluidTransferRate, FluidAction.EXECUTE), FluidAction.EXECUTE);
			if (this.m_fillInputTankBuffer.getFluidAmount() <= 0)
			{
				this.m_fillInputTankInitialTransferSize = 0;
			}
			changed = true;
		}
		return changed;
	}// end doFillInputTank() 

	private boolean doDrainInputTank() 
	{
		boolean changed = false;		
		if (this.m_drainInputTankCountDown > 0)
		{
			this.m_drainInputTankCountDown = SlotUtilities.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_drainInputTankInitialTransferSize, this.m_drainInputTankCountDown, this.m_maxFluidTransferRate);
			if (this.m_drainInputTankCountDown <= 0)
			{
				this.m_drainInputTankInitialTransferSize = 0;
			}
			changed = true;
		}
		if(m_drainInputTankInitialTransferSize == 0) 
		{
			this.m_drainInputTankInitialTransferSize = SlotUtilities.queueToDrainToSlot(this.m_inventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_maxFluidTransferRate);
			this.m_drainInputTankCountDown = this.m_drainInputTankInitialTransferSize;
			
			changed = true;
		}
		return changed;
	} // end doDrainInputTank()
	
	
	
	@Override
	protected void setRecipeResults(CrystallizingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory);
	} // end setRecipeResults()

	@Override
	protected boolean canUseRecipe(CrystallizingRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory, this.m_inputTank);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(CrystallizingRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory, this.m_inputTank);
	} // end startRecipe()
	
	

	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		if(this.m_rawInputTank.getFluidAmount() > 0) 
		{
			tag.put(INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
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
		if(tag.contains(INPUT_TANK_TAG_NAME)) 
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(INPUT_TANK_TAG_NAME));
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