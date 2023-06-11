package com.gsr.gsr_yatm.block.device.boiler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.ConfigurableTankWrapper;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BoilerBlockEntity extends BlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 4;
	public static final int DATA_SLOT_COUNT = 13;


	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;
	public static final int FUEL_SLOT = 3;

	public static final int LAST_INVENTORY_SLOT = FUEL_SLOT;
	public static final int FIRST_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int LAST_FLUID_SLOT = DRAIN_RESULT_TANK_SLOT;
	public static final int FIRST_FILL_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int LAST_FILL_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int FIRST_DRAIN_FLUID_SLOT = DRAIN_INPUT_TANK_SLOT;
	public static final int LAST_DRAIN_FLUID_SLOT = DRAIN_RESULT_TANK_SLOT;

	public static final int BURN_TIME_ELAPSED_INDEX = 0;
	public static final int BURN_TIME_INDEX = 1;
	public static final int BURN_TEMP_INDEX = 2;
	public static final int INPUT_CAPACITY_INDEX = 3;
	public static final int INPUT_HOLDING_INDEX = 4;
	public static final int RESULT_CAPACITY_INDEX = 5;
	public static final int RESULT_HOLDING_INDEX = 6;
	public static final int FILL_INPUT_TANK_TRANSFER_PROGRESS = 7;
	public static final int FILL_INPUT_TANK_TRANSFER_INITIAL = 8;
	public static final int DRAIN_INPUT_TANK_TRANSFER_PROGRESS = 9;
	public static final int DRAIN_INPUT_TANK_TRANSFER_INITIAL = 10;
	public static final int DRAIN_RESULT_TANK_TRANSFER_PROGRESS = 11;
	public static final int DRAIN_RESULT_TANK_TRANSFER_INITIAL = 12;

	private static final String INVENTORY_TAG_NAME = "inventory";
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

	private int m_burnTimeElapsed = 0;
	private int m_burnTime = 0;
	private int m_burnTemp = 0;
	protected ContainerData m_dataAccessor = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			switch (index)
			{
				case BURN_TIME_ELAPSED_INDEX:
				{
					return m_burnTimeElapsed;
				}
				case BURN_TIME_INDEX:
				{
					return m_burnTime;
				}
				case BURN_TEMP_INDEX:
				{
					return m_burnTemp;
				}

				case INPUT_CAPACITY_INDEX:
				{
					return m_inputTank.getCapacity();
				}
				case INPUT_HOLDING_INDEX:
				{
					return m_inputTank.getFluidAmount();
				}
				case RESULT_CAPACITY_INDEX:
				{
					return m_resultTank.getCapacity();
				}
				case RESULT_HOLDING_INDEX:
				{
					return m_resultTank.getFluidAmount();
				}

				case FILL_INPUT_TANK_TRANSFER_PROGRESS:
				{
					return m_inputTankFillBuffer.getFluidAmount();
				}
				case FILL_INPUT_TANK_TRANSFER_INITIAL:
				{
					return m_initialFillInputTankTransferSize;
				}
				case DRAIN_INPUT_TANK_TRANSFER_PROGRESS:
				{
					return m_inputTankDrainCountDown;
				}
				case DRAIN_INPUT_TANK_TRANSFER_INITIAL:
				{
					return m_initialDrainInputTankTransferSize;
				}
				case DRAIN_RESULT_TANK_TRANSFER_PROGRESS:
				{
					return m_resultTankDrainCountDown;
				}
				case DRAIN_RESULT_TANK_TRANSFER_INITIAL:
				{
					return m_initialDrainResultTankTransferSize;
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
				case BURN_TIME_ELAPSED_INDEX:
				{
					m_burnTimeElapsed = value;
					break;
				}
				case BURN_TIME_INDEX:
				{
					m_burnTime = value;
					break;
				}
				case BURN_TEMP_INDEX:
				{
					m_burnTemp = value;
					break;
				}
				case INPUT_CAPACITY_INDEX:
				{
					if (m_inputTank.getCapacity() != value)
					{
						m_rawInputTank = new FluidTank(value);
					}
					break;
				}
				case INPUT_HOLDING_INDEX:
				{
					FluidStack held = m_inputTank.getFluid();
					if (held != null && held.getAmount() != value)
					{
						m_rawInputTank.setFluid(new FluidStack(held.getFluid(), value));
					}
					break;
				}
				case RESULT_CAPACITY_INDEX:
				{
					if (m_resultTank.getCapacity() != value)
					{
						m_resultTank = new FluidTank(value);
					}
					break;
				}
				case RESULT_HOLDING_INDEX:
				{
					FluidStack held = m_resultTank.getFluid();
					if (held != null && held.getAmount() != value)
					{
						m_resultTank.setFluid(new FluidStack(held.getFluid(), value));
					}
					break;
				}
				case FILL_INPUT_TANK_TRANSFER_PROGRESS:
				{
					FluidStack held = m_inputTankFillBuffer.getFluid();
					if (held != null && held.getAmount() != value)
					{
						m_inputTankFillBuffer.setFluid(new FluidStack(held.getFluid(), value));
					}
					break;
				}
				case FILL_INPUT_TANK_TRANSFER_INITIAL:
				{
					m_initialFillInputTankTransferSize = value;
					break;
				}
				case DRAIN_INPUT_TANK_TRANSFER_PROGRESS:
				{
					m_inputTankDrainCountDown = 0;
//					FluidStack held = m_inputTankDrainBuffer.getFluid();
//					if(held != null && held.getAmount() != value) 
//					{
//						m_inputTankDrainBuffer.setFluid(new FluidStack(held.getFluid(), value));
//					}
					break;
				}
				case DRAIN_INPUT_TANK_TRANSFER_INITIAL:
				{
					m_initialDrainInputTankTransferSize = value;
					break;
				}
				case DRAIN_RESULT_TANK_TRANSFER_PROGRESS:
				{
					m_resultTankDrainCountDown = value;
//					FluidStack held = m_resultTankDrainBuffer.getFluid();
//					if(held != null && held.getAmount() != value) 
//					{
//						m_resultTankDrainBuffer.setFluid(new FluidStack(held.getFluid(), value));
//					}
					break;
				}
				case DRAIN_RESULT_TANK_TRANSFER_INITIAL:
				{
					m_initialDrainResultTankTransferSize = value;
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



	private ItemStackHandler m_rawInventory = new ItemStackHandler(INVENTORY_SLOT_COUNT);
	private ConfigurableInventoryWrapper m_inventory = new ConfigurableInventoryWrapper(m_rawInventory, new int[]
	{ 0, 1, 2, 3 }, this::validateSlotInsertion, this::onItemInsertion, this::onItemWithdrawal);
	private LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> m_inventory);



	private ConfigurableInventoryWrapper m_fillInputTankSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ 0 });
	private LazyOptional<IItemHandler> m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> m_fillInputTankSlot);

	private ConfigurableInventoryWrapper m_drainInputTankSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ 1 });
	private LazyOptional<IItemHandler> m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> m_drainInputTankSlot);

	private ConfigurableInventoryWrapper m_drainResultTankSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ 2 });
	private LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> m_drainResultTankSlot);

	private ConfigurableInventoryWrapper m_heatingSlot = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ 3 });// , (s,i,sim) -> isValidFuel(i));
	private LazyOptional<IItemHandler> m_heatingSlotLazyOptional = LazyOptional.of(() -> m_heatingSlot);



	private FluidTank m_rawInputTank = new FluidTank(4000);
	private ConfigurableTankWrapper m_inputTank = new ConfigurableTankWrapper(this.m_rawInputTank, () -> this.setChanged());
	private LazyOptional<IFluidHandler> m_inputTankLazyOptional = LazyOptional.of(() -> this.m_inputTank);

	private FluidTank m_resultTank = new FluidTank(4000);

	private int m_maxTransferRate = 16;

	private FluidTank m_inputTankFillBuffer = new FluidTank(1000);
	private int m_initialFillInputTankTransferSize = 0;
	// private FluidTank m_inputTankDrainBuffer = new FluidTank(1000);
	private int m_inputTankDrainCountDown = 0;
	private int m_initialDrainInputTankTransferSize = 0;
	// private FluidTank m_resultTankDrainBuffer = new FluidTank(1000);
	private int m_resultTankDrainCountDown = 0;
	private int m_initialDrainResultTankTransferSize = 0;



	// private final FaceAccessMap m_faceConfiguration;

	// private BlockState m_blocksBlockState;
	private Direction m_blocksFacingIn;



	public BoilerBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(YATMBlockEntityTypes.BOILER_BLOCK_ENTITY.get(), blockPos, blockState);
		this.m_blocksFacingIn = blockState.getValue(BoilerBlock.FACING);

		/*
		 * this.m_faceConfiguration = new FaceAccessMap(new Direction[] {Direction.DOWN,
		 * Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST});
		 * this.m_faceConfiguration.addOption(
		 * Component.translatable(FUEL_SLOT_INTERNATIONALIZATION),
		 * ForgeCapabilities.ITEM_HANDLER, m_heatingSlotLazyOptional);
		 */
		// m_faceConfiguration
	} // end constructor



	public IItemHandler getInventory()
	{
		return m_inventory;
	} // end getInventory()

	public LazyOptional<IFluidHandler> getInputTank()
	{
		return this.m_inputTankLazyOptional;
	} // end getRawInputTank()

	public ContainerData getDataAccessor()
	{
		return this.m_dataAccessor;
	} // end getDataAccesor



	public boolean isLit()
	{
		// TODO, add other conditions once implemented
		return this.m_burnTimeElapsed > 0;
	} // end isLit()



	// this is relatively inflexible code, relies on hard coding indices from the
	// menu type
	private boolean validateSlotInsertion(int slot, ItemStack itemStack, boolean simulate)
	{
		switch (slot)
		{
			case 0:
			{
				return SlotUtilities.isValidTankFillSlotInsert(itemStack);
			}
			case 1:
			case 2:
			{
				return SlotUtilities.isValidTankDrainSlotInsert(itemStack);
			}
			case 3:
			{
				return SlotUtilities.isValidHeatingSlotInsert(itemStack);
			}
			default:
			{
				return false;
			}
		}
	}

	private void onItemInsertion(int slot, ItemStack itemStack)
	{
		this.setChanged();
	} // end onItemInsertion()

	private void onItemWithdrawal(int slot, int amount)
	{
		this.setChanged();
	} // onItemWithdrawal



	public static void tick(Level level, BlockPos pos, BlockState blockState, BoilerBlockEntity blockEntity)
	{
		if (!level.isClientSide)
		{
			blockEntity.serverTick(level, pos, blockState, blockEntity);
		}
	} // end tick()

	// maybe do so on both sides to keep them closer together in sync?
	public void serverTick(Level level, BlockPos pos, BlockState blockState, BoilerBlockEntity be)
	{
		boolean wasLitInitially = be.isLit();

		if (this.m_burnTimeElapsed > 0)
		{
			// hopefully the decrement work like in c#
			// TODO, verify
			if (--this.m_burnTimeElapsed == 0)
			{
				this.m_burnTemp = 0;
				this.m_burnTime = this.m_burnTimeElapsed;
			}
			this.setChanged();
		}
		else
		{
			if (SlotUtilities.getHeatingBurnTime(be.m_inventory.getStackInSlot(3)) > 0)
			{
				ItemStack i = this.m_inventory.extractItem(3, 1, false);
				// TODO, remainder
				this.m_burnTimeElapsed = SlotUtilities.getHeatingBurnTime(i);
				this.m_burnTime = this.m_burnTimeElapsed;
				this.m_burnTemp = SlotUtilities.getHeatingTemperature(i);
				this.setChanged();
			}
		}

		// begin to transfer from slot into the input tank
		if (!this.m_inventory.getStackInSlot(FILL_INPUT_TANK_SLOT).isEmpty() && this.m_inputTank.getFluidAmount() < this.m_inputTank.getCapacity() && this.m_inputTankFillBuffer.getFluidAmount() <= 0)
		{
			if (SlotUtilities.canFillTankFrom(this.m_inventory.getStackInSlot(FILL_INPUT_TANK_SLOT), this.m_inputTank, this.m_maxTransferRate))
			{
				this.m_rawInventory.insertItem(FILL_INPUT_TANK_SLOT, SlotUtilities.fillTankFrom(this.m_inventory.extractItem(FILL_INPUT_TANK_SLOT, this.m_inventory.getSlotLimit(FILL_INPUT_TANK_SLOT), false), this.m_inputTankFillBuffer,this.m_maxTransferRate), false);
			}
			else if ((this.m_inputTank.getCapacity() - this.m_inputTank.getFluidAmount()) >= 1000 && SlotUtilities.canFillTankFrom(this.m_inventory.getStackInSlot(FILL_INPUT_TANK_SLOT), this.m_inputTank, 1000))
			{
				this.m_rawInventory.insertItem(FILL_INPUT_TANK_SLOT, SlotUtilities.fillTankFrom(this.m_inventory.extractItem(FILL_INPUT_TANK_SLOT, this.m_inventory.getSlotLimit(FILL_INPUT_TANK_SLOT), false), this.m_inputTankFillBuffer, 1000), false);
			}
			this.m_initialFillInputTankTransferSize = this.m_inputTankFillBuffer.getFluidAmount();
		}

		// queue to drain the input tank into the slot
		if (!this.m_inventory.getStackInSlot(DRAIN_INPUT_TANK_SLOT).isEmpty() && this.m_inputTank.getFluidAmount() > 0 && this.m_initialDrainInputTankTransferSize <= 0)
		{
			if (SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_INPUT_TANK_SLOT), this.m_inputTank, Math.min(this.m_inputTank.getFluidAmount(), this.m_maxTransferRate)))
			{
				this.m_inputTankDrainCountDown = Math.min(this.m_inputTank.getFluidAmount(), this.m_maxTransferRate);
				this.m_initialDrainInputTankTransferSize = m_inputTankDrainCountDown;
			}
			else if (this.m_inputTank.getFluidAmount() >= 1000 && SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_INPUT_TANK_SLOT), this.m_inputTank, 1000))
			{
				this.m_inputTankDrainCountDown = 1000;
				this.m_initialDrainInputTankTransferSize = m_inputTankDrainCountDown;
			}
		}
		// queue to drain the result tank into the slot
		if (!this.m_inventory.getStackInSlot(DRAIN_RESULT_TANK_SLOT).isEmpty() && this.m_resultTank.getFluidAmount() > 0)
		{
			this.m_rawInventory.insertItem(DRAIN_RESULT_TANK_SLOT, SlotUtilities.drainTankTo(this.m_inventory.extractItem(DRAIN_RESULT_TANK_SLOT, this.m_inventory.getSlotLimit(DRAIN_INPUT_TANK_SLOT), false), this.m_resultTank, 1000), false);
		}



		if (this.m_inputTankFillBuffer.getFluidAmount() > 0)
		{
			this.m_inputTank.fill(this.m_inputTankFillBuffer.drain(this.m_maxTransferRate, FluidAction.EXECUTE), FluidAction.EXECUTE);
			if (this.m_inputTankFillBuffer.getFluidAmount() <= 0)
			{
				this.m_initialFillInputTankTransferSize = 0;
			}
		}

		// calculate transfer period that's remaining, and if it's elapsed transfer, for input tank being drained
		if (this.m_inputTankDrainCountDown > 0)
		{
			this.m_inputTankDrainCountDown -= this.m_maxTransferRate;
		}
		if (this.m_inputTankDrainCountDown <= 0 && this.m_initialDrainInputTankTransferSize > 0)
		{
			if (SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_INPUT_TANK_SLOT), this.m_inputTank, this.m_initialDrainInputTankTransferSize))
			{
				this.m_rawInventory.insertItem(DRAIN_INPUT_TANK_SLOT, SlotUtilities.drainTankTo(this.m_inventory.extractItem(DRAIN_INPUT_TANK_SLOT, this.m_inventory.getSlotLimit(DRAIN_INPUT_TANK_SLOT), false), this.m_inputTank, 1000), false);
			}
			this.m_inputTankDrainCountDown = 0;
			this.m_initialDrainInputTankTransferSize = 0;
		}
		
		// draing result tank
		if (this.m_resultTankDrainCountDown > 0)
		{
			this.m_resultTankDrainCountDown -= this.m_maxTransferRate;
		}
		if (this.m_resultTankDrainCountDown <= 0 && this.m_initialDrainResultTankTransferSize > 0)
		{
			if (SlotUtilities.canDrainTankto(this.m_inventory.getStackInSlot(DRAIN_RESULT_TANK_SLOT), this.m_inputTank, this.m_initialDrainResultTankTransferSize))
			{
				this.m_rawInventory.insertItem(DRAIN_INPUT_TANK_SLOT, SlotUtilities.drainTankTo(this.m_inventory.extractItem(DRAIN_RESULT_TANK_SLOT, this.m_inventory.getSlotLimit(DRAIN_RESULT_TANK_SLOT), false), this.m_resultTank, 1000), false);
			}
			this.m_resultTankDrainCountDown = 0;
			this.m_initialDrainResultTankTransferSize = 0;
		}


		
		if (wasLitInitially != be.isLit())
		{
			level.setBlockAndUpdate(pos, blockState.setValue(BoilerBlock.LIT, be.isLit()));
		}
	} // end tick()



	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put(INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
		tag.put(INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
		tag.put(RESULT_TANK_TAG_NAME, this.m_resultTank.writeToNBT(new CompoundTag()));

		tag.putInt(BURN_TIME_ELAPSED_TAG_NAME, this.m_burnTimeElapsed);
		tag.putInt(BURN_TIME_INITIAL_TAG_NAME, this.m_burnTime);
		tag.putInt(BURN_TEMP_TAG_NAME, this.m_burnTemp);

		tag.put(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME, this.m_inputTankFillBuffer.writeToNBT(new CompoundTag()));
		tag.putInt(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_initialFillInputTankTransferSize);
		tag.putInt(DRAIN_INPUT_COUNT_DOWN_TAG_NAME, this.m_inputTankDrainCountDown);
		tag.putInt(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_initialDrainInputTankTransferSize);
		tag.putInt(DRAIN_RESULT_COUNT_DOWN_TAG_NAME, this.m_resultTankDrainCountDown);
		tag.putInt(DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME, this.m_initialDrainResultTankTransferSize);

	} // end saveAdditional

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if (tag.contains(INVENTORY_TAG_NAME))
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(INVENTORY_TAG_NAME));
		}
		if (tag.contains(INPUT_TANK_TAG_NAME))
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(INPUT_TANK_TAG_NAME));
		}
		if (tag.contains(RESULT_TANK_TAG_NAME))
		{
			this.m_resultTank.readFromNBT(tag.getCompound(RESULT_TANK_TAG_NAME));
		}

		if (tag.contains(BURN_TIME_ELAPSED_TAG_NAME))
		{
			this.m_burnTimeElapsed = tag.getInt(BURN_TIME_ELAPSED_TAG_NAME);
		}
		if (tag.contains(BURN_TIME_INITIAL_TAG_NAME))
		{
			this.m_burnTime = tag.getInt(BURN_TIME_INITIAL_TAG_NAME);
		}
		if (tag.contains(BURN_TEMP_TAG_NAME))
		{
			this.m_burnTemp = tag.getInt(BURN_TEMP_TAG_NAME);
		}

		if (tag.contains(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME))
		{
			this.m_inputTankFillBuffer.readFromNBT(tag.getCompound(FILL_INPUT_TRANSFER_BUFFER_TAG_NAME));
		}
		if (tag.contains(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_initialFillInputTankTransferSize = tag.getInt(FILL_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
		if (tag.contains(DRAIN_INPUT_COUNT_DOWN_TAG_NAME))
		{
			this.m_inputTankDrainCountDown = tag.getInt(DRAIN_INPUT_COUNT_DOWN_TAG_NAME);
		}
		if (tag.contains(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_initialDrainInputTankTransferSize = tag.getInt(DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
		if (tag.contains(DRAIN_RESULT_COUNT_DOWN_TAG_NAME))
		{
			this.m_resultTankDrainCountDown = tag.getInt(DRAIN_RESULT_COUNT_DOWN_TAG_NAME);
		}
		if (tag.contains(DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME))
		{
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



/*
 * public void logInventoryContents() { for(int i = 0; i <
 * m_inventory.getSlots(); i++) {
 * YetAnotherTechMod.LOGGER.info(m_inventory.extractItem(i, 64,
 * true).toString()); } } // end logInventoryContents
 */


// private static final String DATA_TAG_NAME = "data";


//private int getData(int index) 
//{
//	return new int[] {
//			
//} // end getData()
//

//private int[] getData() 
//{
//	
////	return new int[] {
////			this.m_burnTimeElapsed, 
////			this.m_burnTime, 
////			this.m_burnTemp, 
////			
////			this.m_inputTank.getCapacity(), 
////			this.m_inputTank.getFluidAmount(), 
////			this.m_resultTank.getCapacity(), 
////			this.m_resultTank.getFluidAmount(),
////			
////			this.m_inputTankFillBuffer.getFluidAmount(),
////			this.m_initialFillInputTankTransferSize,
////			this.m_inputTankDrainBuffer.getFluidAmount(),
////			this.m_initialDrainInputTankTransferSize,
////			this.m_resultTankDrainBuffer.getFluidAmount(),
////			this.m_initialDrainResultTankTransferSize};
//} // end getData()
//
//private void setAllData(int[] data) 
//{
//	for(int i = 0; i < data.length;i++) 
//	{
//		setData(i, data[i]);
//	}
//	this.setChanged();
//} // end setAllData()
//
//private void setData(int index, int value) 
//{
//	switch(index) 
//	{
//		case BURN_TIME_ELAPSED_INDEX:
//		{
//			this.m_burnTimeElapsed = value;
//			break;
//		}
//		case BURN_TIME_INDEX: 
//		{
//			this.m_burnTime = value;
//			break;
//		}
//		case BURN_TEMP_INDEX:
//		{
//			this.m_burnTemp = value;
//			break;
//		}
//		case INPUT_CAPACITY_INDEX: 
//		{
//			if(this.m_inputTank.getCapacity() != value) 
//			{
//				this.m_rawInputTank = new FluidTank(value);
//			}
//			break;
//		}
//		case INPUT_HOLDING_INDEX: 
//		{
//			FluidStack held = this.m_inputTank.getFluid();
//			if(held != null && held.getAmount() != value) 
//			{
//				this.m_rawInputTank.setFluid(new FluidStack(held.getFluid(), value));
//			}
//			break;
//		}
//		case RESULT_CAPACITY_INDEX: 
//		{
//			if(this.m_resultTank.getCapacity() != value) 
//			{
//				this.m_resultTank = new FluidTank(value);
//			}
//			break;
//		}
//		case RESULT_HOLDING_INDEX: 
//		{
//			FluidStack held = this.m_resultTank.getFluid();
//			if(held != null && held.getAmount() != value) 
//			{
//				this.m_resultTank.setFluid(new FluidStack(held.getFluid(), value));
//			}
//			break;
//		}			
//		case FILL_INPUT_TANK_TRANSFER_PROGRESS: 
//		{
//			FluidStack held = this.m_inputTankFillBuffer.getFluid();
//			if(held != null && held.getAmount() != value) 
//			{
//				this.m_inputTankFillBuffer.setFluid(new FluidStack(held.getFluid(), value));
//			}
//			break;
//		}
//		case FILL_INPUT_TANK_TRANSFER_INITIAL:
//		{
//			this.m_initialFillInputTankTransferSize = value;
//			break;
//		}
//		case DRAIN_INPUT_TANK_TRANSFER_PROGRESS: 
//		{
//			FluidStack held = this.m_inputTankDrainBuffer.getFluid();
//			if(held != null && held.getAmount() != value) 
//			{
//				this.m_inputTankDrainBuffer.setFluid(new FluidStack(held.getFluid(), value));
//			}
//			break;
//		}
//		case DRAIN_INPUT_TANK_TRANSFER_INITIAL:
//		{
//			this.m_initialDrainInputTankTransferSize = value;
//			break;
//		}			
//		case DRAIN_RESULT_TANK_TRANSFER_PROGRESS: 
//		{
//			FluidStack held = this.m_resultTankDrainBuffer.getFluid();
//			if(held != null && held.getAmount() != value) 
//			{
//				this.m_resultTankDrainBuffer.setFluid(new FluidStack(held.getFluid(), value));
//			}
//			break;
//		}
//		case DRAIN_RESULT_TANK_TRANSFER_INITIAL:
//		{
//			this.m_initialDrainResultTankTransferSize = value;
//			break;
//		}			
//		
//		default:
//		{
//			throw new IndexOutOfBoundsException("get index of: " + index + ", is out of the range");
//		}
//	}
//} // end setData()

//tag.putIntArray(DATA_TAG_NAME, this.getData());

//if(tag.contains(DATA_TAG_NAME)) 
//{
//	this.setAllData(tag.getIntArray(DATA_TAG_NAME));
//}