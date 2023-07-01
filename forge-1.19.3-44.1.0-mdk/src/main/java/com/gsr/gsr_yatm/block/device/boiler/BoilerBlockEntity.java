package com.gsr.gsr_yatm.block.device.boiler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.BoilingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.NetworkUtilities;
import com.gsr.gsr_yatm.utilities.SlotUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class BoilerBlockEntity extends CraftingDeviceBlockEntity<BoilingRecipe, Container>
{
	public static final int INVENTORY_SLOT_COUNT = 4;
	public static final int DATA_SLOT_COUNT = 20;


	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;
	public static final int HEAT_SLOT = 3;

	public static final int LAST_INVENTORY_SLOT = HEAT_SLOT;
	public static final int FIRST_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int LAST_FLUID_SLOT = DRAIN_RESULT_TANK_SLOT;
	public static final int FIRST_FILL_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int LAST_FILL_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int FIRST_DRAIN_FLUID_SLOT = DRAIN_INPUT_TANK_SLOT;
	public static final int LAST_DRAIN_FLUID_SLOT = DRAIN_RESULT_TANK_SLOT;

	public static final int BOIL_PROGESS_INDEX = 0;
	public static final int BOIL_TIME_INDEX = 1;
	public static final int BURN_TIME_ELAPSED_INDEX = 2;
	public static final int BURN_TIME_INDEX = 3;
	public static final int TEMPERATURE_INDEX = 4;
	public static final int MAXIMUM_TEMPERATURE_INDEX = 5;
	public static final int INPUT_CAPACITY_INDEX = 6;
	public static final int INPUT_HOLDING_INDEX = 7;
	public static final int RESULT_CAPACITY_INDEX = 8;
	public static final int RESULT_HOLDING_INDEX = 9;
	public static final int FILL_INPUT_TANK_TRANSFER_PROGRESS = 10;
	public static final int FILL_INPUT_TANK_TRANSFER_INITIAL = 11;
	public static final int DRAIN_INPUT_TANK_TRANSFER_PROGRESS = 12;
	public static final int DRAIN_INPUT_TANK_TRANSFER_INITIAL = 13;
	public static final int DRAIN_RESULT_TANK_TRANSFER_PROGRESS = 14;
	public static final int DRAIN_RESULT_TANK_TRANSFER_INITIAL = 15;
	public static final int INPUT_TANK_FLUID_INDEX_LOW = 16;
	public static final int INPUT_TANK_FLUID_INDEX_HIGH = 17;
	public static final int RESULT_TANK_FLUID_INDEX_LOW = 18;
	public static final int RESULT_TANK_FLUID_INDEX_HIGH = 19;

	private static final String MAX_TEMPERATURE_TAG_NAME = "maxTemperature";
	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";

	private static final String INPUT_TANK_TAG_NAME = "inputTank";
	private static final String RESULT_TANK_TAG_NAME = "resultTank";
	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
	private static final String BURN_TEMP_TAG_NAME = "burnTemp";
	private static final String FILL_INPUT_TRANSFER_BUFFER_TAG_NAME = "fillInputBuffer";
	private static final String FILL_INPUT_TRANSFER_INITIAL_TAG_NAME = "fillInputInitial";
	private static final String DRAIN_INPUT_COUNT_DOWN_TAG_NAME = "drainInputCount";
	private static final String DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME = "drainInputInitial";
	private static final String DRAIN_RESULT_COUNT_DOWN_TAG_NAME = "drainResultCount";
	private static final String DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME = "drainResultInitial";

	
	
	private LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> m_inventory);

	private ConfigurableInventoryWrapper m_fillInputTankSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ FILL_INPUT_TANK_SLOT });
	private LazyOptional<IItemHandler> m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> m_fillInputTankSlot);

	private ConfigurableInventoryWrapper m_drainInputTankSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ DRAIN_INPUT_TANK_SLOT });
	private LazyOptional<IItemHandler> m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> m_drainInputTankSlot);

	private ConfigurableInventoryWrapper m_drainResultTankSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ DRAIN_RESULT_TANK_SLOT });
	private LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> m_drainResultTankSlot);

	private ConfigurableInventoryWrapper m_heatingSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ HEAT_SLOT });
	private LazyOptional<IItemHandler> m_heatingSlotLazyOptional = LazyOptional.of(() -> m_heatingSlot);

	private int m_maxFluidTransferRate;

	private FluidTank m_rawInputTank;
	private ConfigurableTankWrapper m_inputTank;
	private LazyOptional<IFluidHandler> m_inputTankLazyOptional;

	private FluidTank m_rawResultTank;
	private ConfigurableTankWrapper m_resultTank;
	
	private FluidTank m_inputTankFillBuffer;
	private int m_initialFillInputTankTransferSize = 0;
	private int m_inputTankDrainCountDown = 0;
	private int m_initialDrainInputTankTransferSize = 0;
	private int m_resultTankDrainCountDown = 0;
	private int m_initialDrainResultTankTransferSize = 0;
	
	private Direction m_blocksFacingIn;
	
	private int m_burnProgress = 0;
	private int m_burnTime = 0;
	private int m_temperature = 0;
	private int m_maxTemperature = 0;
	
	protected ContainerData m_dataAccessor = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			return switch(index) 
			{
				case BoilerBlockEntity.BOIL_PROGESS_INDEX -> BoilerBlockEntity.this.m_craftProgress;
				case BoilerBlockEntity.BOIL_TIME_INDEX -> BoilerBlockEntity.this.m_craftTime;
				case BoilerBlockEntity.BURN_TIME_ELAPSED_INDEX -> BoilerBlockEntity.this.m_burnProgress;
				case BoilerBlockEntity.BURN_TIME_INDEX -> BoilerBlockEntity.this.m_burnTime;
				case BoilerBlockEntity.TEMPERATURE_INDEX -> BoilerBlockEntity.this.m_temperature;
				case BoilerBlockEntity.MAXIMUM_TEMPERATURE_INDEX -> BoilerBlockEntity.this.m_maxTemperature;
				case BoilerBlockEntity.INPUT_CAPACITY_INDEX -> BoilerBlockEntity.this.m_inputTank.getCapacity();
				case BoilerBlockEntity.INPUT_HOLDING_INDEX -> BoilerBlockEntity.this.m_inputTank.getFluidAmount();
				case BoilerBlockEntity.RESULT_CAPACITY_INDEX -> BoilerBlockEntity.this.m_resultTank.getCapacity();
				case BoilerBlockEntity.RESULT_HOLDING_INDEX -> BoilerBlockEntity.this.m_resultTank.getFluidAmount();
				case BoilerBlockEntity.FILL_INPUT_TANK_TRANSFER_PROGRESS -> BoilerBlockEntity.this.m_inputTankFillBuffer.getFluidAmount();
				case BoilerBlockEntity.FILL_INPUT_TANK_TRANSFER_INITIAL -> BoilerBlockEntity.this.m_initialFillInputTankTransferSize;
				case BoilerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_PROGRESS -> BoilerBlockEntity.this.m_inputTankDrainCountDown;
				case BoilerBlockEntity.DRAIN_INPUT_TANK_TRANSFER_INITIAL -> BoilerBlockEntity.this.m_initialDrainInputTankTransferSize;
				case BoilerBlockEntity.DRAIN_RESULT_TANK_TRANSFER_PROGRESS -> BoilerBlockEntity.this.m_resultTankDrainCountDown;
				case BoilerBlockEntity.DRAIN_RESULT_TANK_TRANSFER_INITIAL -> BoilerBlockEntity.this.m_initialDrainResultTankTransferSize;
				case BoilerBlockEntity.INPUT_TANK_FLUID_INDEX_LOW -> NetworkUtilities.splitInt(NetworkUtilities.getFluidIndex(BoilerBlockEntity.this.m_inputTank.getFluid().getFluid()), false);
				case BoilerBlockEntity.INPUT_TANK_FLUID_INDEX_HIGH -> NetworkUtilities.splitInt(NetworkUtilities.getFluidIndex(BoilerBlockEntity.this.m_inputTank.getFluid().getFluid()), true);
				case BoilerBlockEntity.RESULT_TANK_FLUID_INDEX_LOW -> NetworkUtilities.splitInt(NetworkUtilities.getFluidIndex(BoilerBlockEntity.this.m_resultTank.getFluid().getFluid()), false);
				case BoilerBlockEntity.RESULT_TANK_FLUID_INDEX_HIGH -> NetworkUtilities.splitInt(NetworkUtilities.getFluidIndex(BoilerBlockEntity.this.m_resultTank.getFluid().getFluid()), true);
				
				default -> throw new IllegalArgumentException("Unexpected value of: " + index);
			};
		} // end get()

		@Override
		public void set(int index, int value)
		{
			return;
		} // end set()

		@Override
		public int getCount()
		{
			return BoilerBlockEntity.DATA_SLOT_COUNT;
		} // end getCount()
	};



	public BoilerBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0, 0);
	} // end constructor
	
	public BoilerBlockEntity(BlockPos blockPos, BlockState blockState, int maxTemperature, int tankCapacities, int maxFluidTransferRate)
	{
		super(YATMBlockEntityTypes.BOILER.get(), blockPos, blockState, BoilerBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.BOILING.get());
		this.m_blocksFacingIn = blockState.getValue(BoilerBlock.FACING);
		this.setup(maxTemperature, tankCapacities, maxFluidTransferRate);
	} // end constructor
	
	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(MAX_TEMPERATURE_TAG_NAME, this.m_maxTemperature);
		tag.putInt(TANK_CAPACITY_TAG_NAME, this.m_rawInputTank.getCapacity());
		tag.putInt(MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		int maxTemperature = 0;
		int fluidCapacity = 0;
		int maxFluidTransferRate = 0;
		if(tag.contains(MAX_TEMPERATURE_TAG_NAME)) 
		{
			maxTemperature = tag.getInt(MAX_TEMPERATURE_TAG_NAME);
		}
		if(tag.contains(TANK_CAPACITY_TAG_NAME)) 
		{
			fluidCapacity = tag.getInt(TANK_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_FLUID_TRANSFER_RATE_TAG_NAME)) 
		{
			maxFluidTransferRate = tag.getInt(MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		this.setup(maxTemperature, fluidCapacity, maxFluidTransferRate);
	} // end setupFromNBT()

	private void setup(int maxTemperature, int tankCapacities, int maxFluidTransferRate) 
	{
		this.m_maxTemperature = maxTemperature;
		this.m_rawInputTank = new FluidTank(tankCapacities);
		this.m_rawResultTank = new FluidTank(tankCapacities);
		
		this.m_inputTankFillBuffer = new FluidTank(tankCapacities);
		this.m_maxFluidTransferRate = maxFluidTransferRate;
		
		this.m_inputTank = new ConfigurableTankWrapper(this.m_rawInputTank, this::onFluidContentsChanged);
		this.m_inputTankLazyOptional = LazyOptional.of(() -> this.m_inputTank);
		
		this.m_resultTank = new ConfigurableTankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);
	} // end setup()
	


	public LazyOptional<IFluidHandler> getInputTank()
	{
		return this.m_inputTankLazyOptional;
	} // end getRawInputTank()

	@Override
	public ContainerData getDataAccessor()
	{
		return this.m_dataAccessor;
	} // end getDataAccesor



	public boolean isLit()
	{
		// TODO, add other conditions once implemented
		return this.m_burnProgress > 0;
	} // end isLit()


	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch(slot) 
		{
			case FILL_INPUT_TANK_SLOT -> SlotUtilities.isValidTankFillSlotInsert(itemStack);
			case DRAIN_INPUT_TANK_SLOT, DRAIN_RESULT_TANK_SLOT -> SlotUtilities.isValidTankDrainSlotInsert(itemStack);
			case HEAT_SLOT -> SlotUtilities.isValidHeatingSlotInsert(itemStack);
			
			default -> throw new IllegalArgumentException("Unexpected value: " + slot);
		};
	} // end validateSlotInsertion()



	@Override
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		boolean wasLitInitially = this.isLit();
		boolean changed = doHeat();
		changed |= this.doFillInputTank();
		changed |= /* this.m_activeRecipe != null && */this.m_waitingForLoad ? false : this.doCrafting();
		changed |= this.doDrainInputTank();
		changed |= this.doDrainResultTank();
		
		if (wasLitInitially != this.isLit())
		{
			level.setBlockAndUpdate(pos, blockState.setValue(BoilerBlock.LIT, this.isLit()));
		}
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doHeat() 
	{
		boolean changed = false;
		if (this.m_burnProgress > 0)
		{
			if (--this.m_burnProgress == 0)
			{
				this.m_temperature = 0;
				this.m_burnTime = 0;
			}
			changed = true;
		}
		else
		{
			if (SlotUtilities.getHeatingBurnTime(this.m_inventory.getStackInSlot(HEAT_SLOT)) > 0)
			{
				ItemStack i = this.m_inventory.extractItem(HEAT_SLOT, 1, false);
				if(i.hasCraftingRemainingItem()) 
				{
					InventoryUtilities.insertItemOrDrop(this.level, this.worldPosition, this.m_inventory, HEAT_SLOT, i.getCraftingRemainingItem());
				}				

				this.m_burnTime = SlotUtilities.getHeatingBurnTime(i);
				this.m_burnProgress = this.m_burnTime;
				this.m_temperature = SlotUtilities.getHeatingTemperature(i);
				changed = true;
			}
		}
		return changed;
	} // end doHeat()

	private boolean doFillInputTank() 
	{
		boolean changed = false;
		if(this.m_inputTankFillBuffer.getFluidAmount() <= 0) 
		{
			this.m_initialFillInputTankTransferSize = SlotUtilities.queueToFillFromSlot(this.level, this.worldPosition, this.m_inventory, BoilerBlockEntity.FILL_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_inputTankFillBuffer, this.m_maxFluidTransferRate);
			if(this.m_initialFillInputTankTransferSize > 0) 
			{
				changed = true;
			}
		}
		if (this.m_inputTankFillBuffer.getFluidAmount() > 0)
		{
			this.m_inputTank.fill(this.m_inputTankFillBuffer.drain(this.m_maxFluidTransferRate, FluidAction.EXECUTE), FluidAction.EXECUTE);
			if (this.m_inputTankFillBuffer.getFluidAmount() <= 0)
			{
				this.m_initialFillInputTankTransferSize = 0;
			}
			changed = true;
		}
		return changed;
	}// end doFillInputTank() 

	private boolean doDrainInputTank() 
	{
		boolean changed = false;		
		if (this.m_inputTankDrainCountDown > 0)
		{
			this.m_inputTankDrainCountDown = SlotUtilities.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_initialDrainInputTankTransferSize, this.m_inputTankDrainCountDown, this.m_maxFluidTransferRate);
			if (this.m_inputTankDrainCountDown <= 0)
			{
				this.m_initialDrainInputTankTransferSize = 0;
			}
			changed = true;
		}
		if(m_initialDrainInputTankTransferSize == 0) 
		{
			this.m_initialDrainInputTankTransferSize = SlotUtilities.queueToDrainToSlot(this.m_inventory, BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_maxFluidTransferRate);
			this.m_inputTankDrainCountDown = this.m_initialDrainInputTankTransferSize;
			
			changed = true;
		}
		return changed;
	} // end doDrainInputTank()
	
	private boolean doDrainResultTank() 
	{
		boolean changed = false;		
		if (this.m_resultTankDrainCountDown > 0)
		{
			this.m_resultTankDrainCountDown = SlotUtilities.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_initialDrainResultTankTransferSize, this.m_resultTankDrainCountDown, this.m_maxFluidTransferRate);
			if (this.m_resultTankDrainCountDown <= 0)
			{
				this.m_initialDrainResultTankTransferSize = 0;
			}
			changed = true;
		}
		if(m_initialDrainResultTankTransferSize == 0) 
		{
			this.m_initialDrainResultTankTransferSize = SlotUtilities.queueToDrainToSlot(this.m_inventory, DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_maxFluidTransferRate);
			this.m_resultTankDrainCountDown = this.m_initialDrainResultTankTransferSize;
			changed = true;
		}
		return changed;
	} // end doDrainResultTank()
	
	@Override
	protected void setRecipeResults(BoilingRecipe from)
	{
		from.setResults(this.m_rawResultTank);
	} // setRecipeResults()

	@Override
	protected boolean canUseRecipe(BoilingRecipe from)
	{
		return from.canBeUsedOn(this.m_inputTank, this.m_resultTank, this.m_temperature);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(BoilingRecipe from)
	{
		from.startRecipe(this.m_inputTank);
	} // end startRecipe()

	
	
	@Override
 	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.putInt(BURN_TEMP_TAG_NAME, this.m_temperature);
		if(this.m_rawInputTank.getFluidAmount() > 0) 
		{
			tag.put(INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
		}
		if(this.m_rawResultTank.getFluidAmount() > 0) 
		{
			tag.put(RESULT_TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
		}
		if(this.m_burnProgress > 0 && this.m_burnTime > 0) 
		{
			tag.putInt(BURN_TIME_ELAPSED_TAG_NAME, this.m_burnProgress);
			tag.putInt(BURN_TIME_INITIAL_TAG_NAME, this.m_burnTime);
		}
		if(this.m_initialFillInputTankTransferSize > 0 && this.m_inputTankFillBuffer.getFluidAmount() > 0) 
		{
			tag.put(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME, this.m_inputTankFillBuffer.writeToNBT(new CompoundTag()));
			tag.putInt(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_initialFillInputTankTransferSize);
		}
		if(this.m_inputTankDrainCountDown > 0 && this.m_initialDrainInputTankTransferSize > 0) 
		{
			tag.putInt(DRAIN_INPUT_COUNT_DOWN_TAG_NAME, this.m_inputTankDrainCountDown);
			tag.putInt(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_initialDrainInputTankTransferSize);
		}
		if(this.m_resultTankDrainCountDown > 0 && this.m_initialDrainResultTankTransferSize > 0) 
		{
			tag.putInt(DRAIN_RESULT_COUNT_DOWN_TAG_NAME, this.m_resultTankDrainCountDown);
			tag.putInt(DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME, this.m_initialDrainResultTankTransferSize);
		}
	} // end saveAdditional

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if (tag.contains(BURN_TEMP_TAG_NAME))
		{
			this.m_temperature = tag.getInt(BURN_TEMP_TAG_NAME);
		}		
		if (tag.contains(INPUT_TANK_TAG_NAME))
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(INPUT_TANK_TAG_NAME));
		}
		if (tag.contains(RESULT_TANK_TAG_NAME))
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(RESULT_TANK_TAG_NAME));
		}
		if (tag.contains(BURN_TIME_ELAPSED_TAG_NAME) && tag.contains(BURN_TIME_INITIAL_TAG_NAME))
		{
			this.m_burnProgress = tag.getInt(BURN_TIME_ELAPSED_TAG_NAME);
			this.m_burnTime = tag.getInt(BURN_TIME_INITIAL_TAG_NAME);
		}
		if (tag.contains(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME) && tag.contains(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_inputTankFillBuffer.readFromNBT(tag.getCompound(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME));
			this.m_initialFillInputTankTransferSize = tag.getInt(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
		if (tag.contains(DRAIN_INPUT_COUNT_DOWN_TAG_NAME) && tag.contains(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_inputTankDrainCountDown = tag.getInt(DRAIN_INPUT_COUNT_DOWN_TAG_NAME);
			this.m_initialDrainInputTankTransferSize = tag.getInt(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
		if (tag.contains(DRAIN_RESULT_COUNT_DOWN_TAG_NAME) && tag.contains(DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_resultTankDrainCountDown = tag.getInt(DRAIN_RESULT_COUNT_DOWN_TAG_NAME);
			this.m_initialDrainResultTankTransferSize = tag.getInt(DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME);
		}
	} // end load()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		LazyOptional<IItemHandler> releventSlot = null;
		if (side == this.m_blocksFacingIn) 
		{				
			releventSlot = this.m_heatingSlotLazyOptional;
		}
		else if(side == this.m_blocksFacingIn.getClockWise())
		{				
			releventSlot = this.m_fillInputTankSlotLazyOptional;
		}
		else if(side == this.m_blocksFacingIn.getClockWise().getClockWise())
		{				
			releventSlot = this.m_drainInputTankSlotLazyOptional;
		}
		else if(side == this.m_blocksFacingIn.getCounterClockWise())
		{				
			releventSlot = this.m_drainResultTankSlotLazyOptional;
		}
		else if(side == Direction.DOWN && cap != ForgeCapabilities.ITEM_HANDLER) 
		{
			releventSlot = this.m_heatingSlotLazyOptional;
		}
		
		if(releventSlot != null) 
		{
			LazyOptional<T> c = SlotUtilities.getSlotsCapability(releventSlot, cap);
			if(c != null) 
			{
				return c;
			}
		}
		
		return super.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_inventoryLazyOptional.invalidate();
		this.m_fillInputTankSlotLazyOptional.invalidate();
		this.m_drainInputTankSlotLazyOptional.invalidate();
		this.m_drainResultTankSlotLazyOptional.invalidate();
		this.m_heatingSlotLazyOptional.invalidate();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_inventoryLazyOptional = LazyOptional.of(() -> m_inventory);
		this.m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> m_fillInputTankSlot);
		this.m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> m_drainInputTankSlot);
		this.m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> m_drainResultTankSlot);
		this.m_heatingSlotLazyOptional = LazyOptional.of(() -> m_heatingSlot);
	} // end revivieCaps()

} // end class