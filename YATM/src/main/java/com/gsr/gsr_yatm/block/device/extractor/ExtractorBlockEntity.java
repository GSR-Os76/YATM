package com.gsr.gsr_yatm.block.device.extractor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.CraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentFillManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.DrainTankManager;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.generic.SetUtil;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple3;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.bool.BooleanFlagHandler;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentHandlerContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class ExtractorBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 4;
	
	public static final int INPUT_SLOT = 0;
	public static final int INPUT_REMAINDER_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;
	public static final int POWER_SLOT = 3;
	
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	public static final String DRAIN_RESULT_PROGRESS_SPEC_KEY = "drainInputProgress";
	public static final String RESULT_TANK_SPEC_KEY = "inputTank";
	public static final String FLAGS_SPEC_KEY = "flags";
	
	public static final int HAS_REMAINDER_FLAG_INDEX = 1;
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CRAFT_PROGRESS_SPEC_KEY, CraftingManager.SLOT_COUNT),
			Map.entry(CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT),
			Map.entry(DRAIN_RESULT_PROGRESS_SPEC_KEY, DrainTankManager.SLOT_COUNT),
			Map.entry(FLAGS_SPEC_KEY, BooleanFlagHandler.SLOT_COUNT),
			Map.entry(RESULT_TANK_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT)
			));
	
	
	
	public ExtractorBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.EXTRACTOR.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> InventoryWrapper.Builder.of(i).slotValidator((s, is, sim) -> s != ExtractorBlockEntity.INPUT_REMAINDER_SLOT).build());
		FluidTank r = this.m_helpers.newTank(YATMConfigs.EXTRACTOR_RESULT_TANK_CAPACITY.get());
		TankWrapper wR = TankWrapper.Builder.of(r).fillValidator((f) -> false).maxDrainTransfer(YATMConfigs.EXTRACTOR_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE.get()).build();
		CurrentHandler c = this.m_helpers.newCurrentHandler(YATMConfigs.EXTRACTOR_CURRENT_CAPACITY.get(), YATMConfigs.EXTRACTOR_MAX_CURRENT_TRANSFER.get());
		
		BackedFunction<IItemHandler, CurrentFillManager> cFM = new BackedFunction<>((i) -> new CurrentFillManager(i, ExtractorBlockEntity.POWER_SLOT, c, YATMConfigs.EXTRACTOR_MAX_CURRENT_TRANSFER.get()));
		BackedFunction<IItemHandler, InputComponentManager<ICurrentHandler>> cFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, ExtractorBlockEntity.POWER_SLOT, this.m_helpers.noDrain(c), YATMCapabilities.CURRENT));
		
		BackedFunction<IItemHandler, CraftingManager<ExtractingRecipe, Container, Tuple3<IItemHandler, IFluidHandler, ICurrentHandler>>> cM = new BackedFunction<>((i) -> new CraftingManager<>(YATMRecipeTypes.EXTRACTING.get(), () -> Tuple.of(i, r, c)));
		
		BackedFunction<IItemHandler, DrainTankManager> rDM = new BackedFunction<>((i) -> new DrainTankManager(i, ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT, r, YATMConfigs.EXTRACTOR_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, OutputComponentManager> rDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT, () -> List.of(Direction.DOWN), YATMConfigs.EXTRACTOR_DRAIN_RESULT_RECHECK_PERIOD.get()));

		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().end()
		.slot().end()
		.slot().insertionValidator(SlotUtil.TANK_DRAIN_SLOT_INSERTION_VALIDATOR).end()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(() -> r.writeToNBT(new CompoundTag()), r::readFromNBT, "resultTank")).allDefaults().end()
		.behavior(new SerializableBehavior(c, "current")).allDefaults().end()
		.behavior(cFM::apply).allDefaults().end()
		.behavior(cFCM::apply).allDefaults().end()
		.behavior(cM::apply).allDefaults().end()
		.behavior(rDM::apply).allDefaults().end()
		.behavior(rDCM::apply).allDefaults().end()
		
		.containerData()
		.addContainerData(cM.get().getData())
		.addContainerData(new CurrentHandlerContainerData(c))
		.addContainerData(rDM.get().getData())
		.addProperty(new Property<>(() -> BooleanFlagHandler.setValue(0, ExtractorBlockEntity.HAS_REMAINDER_FLAG_INDEX, cM.get().getActiveRecipe() != null && cM.get().getActiveRecipe().hasRemainder()), (i) -> {}))
		.addContainerData(new FluidHandlerContainerData(r))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(ForgeCapabilities.FLUID_HANDLER, wR)
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		
		.face(Direction.UP)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), ExtractorBlockEntity.INPUT_SLOT)).end()
		
		.face(Set::of)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), ExtractorBlockEntity.INPUT_REMAINDER_SLOT)).end()
		
		.face(Direction.DOWN)
		.returns(rDCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT)).end()
		
		.face(() -> SetUtil.of(Direction.Plane.HORIZONTAL.stream()))
		.returns(cFCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), ExtractorBlockEntity.POWER_SLOT)).end()
		.end()
		
		.end();
		this.m_inventory = inv.get();
	} // end define()
	
} // end class
/*
CraftingDeviceBlockEntity<ExtractingRecipe, Container, Tuple3<IItemHandler, IFluidHandler, ICurrentHandler>>
{
	public static final int DATA_SLOT_COUNT = 11;
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
	public static final int DATA_FLAGS_SLOT = 8;
	public static final int FLUID_INDEX_LOW_SLOT = 9;
	public static final int FLUID_INDEX_HIGH_SLOT = 10;
	
	public static final int HAS_REMAINDER_FLAG_INDEX = 1;

	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";
	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";

	public static final String TANK_TAG_NAME = "resultTank";



	private FluidTank m_rawResultTank;
	private IFluidHandler m_resultTank;
	
	private int m_maxFluidTransferRate;

	// TODO, add saving this, nice but not game breaking
	private int m_resultTankDrainCountDown = 0;
	private int m_initialDrainResultTankTransferSize = 0;
	
	private BooleanFlagHandler m_flagHandler = new BooleanFlagHandler();
	@Deprecated(forRemoval = true) public ICurrentHandler m_internalCurrentStorer;
	@Deprecated(forRemoval = true) public int m_maxSafeCurrentTransfer;
	
	private ContainerData m_data = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			return switch(index) 
			{
				case ExtractorBlockEntity.EXTRACT_PROGRESS_SLOT -> 0;//ExtractorBlockEntity.this.m_craftCountDown;
				case ExtractorBlockEntity.EXTRACT_TIME_SLOT -> 0;//ExtractorBlockEntity.this.m_craftTime;
				case ExtractorBlockEntity.STORED_FlUID_AMOUNT_SLOT -> ExtractorBlockEntity.this.m_rawResultTank.getFluidAmount();
				case ExtractorBlockEntity.FLUID_CAPACITY_SLOT -> ExtractorBlockEntity.this.m_rawResultTank.getCapacity();
				case ExtractorBlockEntity.FLUID_TRANSFER_COUNT_DOWN_SLOT -> ExtractorBlockEntity.this.m_resultTankDrainCountDown;
				case ExtractorBlockEntity.FLUID_TRANSFER_SIZE_SLOT -> ExtractorBlockEntity.this.m_initialDrainResultTankTransferSize;
				case ExtractorBlockEntity.STORED_POWER_SLOT -> ExtractorBlockEntity.this.m_internalCurrentStorer.stored();
				case ExtractorBlockEntity.POWER_CAPACITY_SLOT -> ExtractorBlockEntity.this.m_internalCurrentStorer.capacity();
				
				case ExtractorBlockEntity.DATA_FLAGS_SLOT -> ExtractorBlockEntity.this.m_flagHandler.getValue();//m_flags;
				
				case ExtractorBlockEntity.FLUID_INDEX_LOW_SLOT -> NetworkUtil.splitInt(NetworkUtil.getFluidIndex(ExtractorBlockEntity.this.m_rawResultTank.getFluid().getFluid()), false);
				case ExtractorBlockEntity.FLUID_INDEX_HIGH_SLOT -> NetworkUtil.splitInt(NetworkUtil.getFluidIndex(ExtractorBlockEntity.this.m_rawResultTank.getFluid().getFluid()), true);
				
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
			return ExtractorBlockEntity.DATA_SLOT_COUNT;
		} // end getCount()
	};
	
	
	
	public ExtractorBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0, 0, 0);
	} // end constructor
	
	public ExtractorBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxCurrentTransfer, int fluidCapacity, int maxFluidTransferRate)
	{
		super(YATMBlockEntityTypes.EXTRACTOR.get(), blockPos, blockState, ExtractorBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.EXTRACTING.get());
		this.setup(currentCapacity, maxCurrentTransfer, fluidCapacity, maxFluidTransferRate);
	} // end constructor
	
	@Override
	public @NotNull Tuple3<IItemHandler, IFluidHandler, ICurrentHandler> getContext()
	{
		return new Tuple3<>(this.m_inventory, this.m_resultTank, this.m_internalCurrentStorer);
	} // end getContext()

	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CURRENT_CAPACITY_TAG_NAME, this.m_internalCurrentStorer.capacity());
		tag.putInt(MAX_CURRENT_TAG_NAME, this.m_maxSafeCurrentTransfer);
		tag.putInt(TANK_CAPACITY_TAG_NAME, this.m_rawResultTank.getCapacity());
		tag.putInt(MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(CompoundTag tag)
	{
		int currentCapacity = 0;
		int maxCurrentTransfer = 0;
		int fluidCapacity = 0;
		int maxFluidTransferRate = 0;
		
		if(tag.contains(CURRENT_CAPACITY_TAG_NAME)) 
		{
			currentCapacity = tag.getInt(CURRENT_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_CURRENT_TAG_NAME)) 
		{
			maxCurrentTransfer = tag.getInt(MAX_CURRENT_TAG_NAME);
		}
		if(tag.contains(TANK_CAPACITY_TAG_NAME)) 
		{
			fluidCapacity = tag.getInt(TANK_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_FLUID_TRANSFER_RATE_TAG_NAME)) 
		{
			maxFluidTransferRate = tag.getInt(MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		this.setup(currentCapacity, maxCurrentTransfer, fluidCapacity, maxFluidTransferRate);
	} // end setupFromNBT()
	
	private void setup(int currentCapacity, int maxCurrentTransfer, int fluidCapacity, int maxFluidTransferRate) 
	{
		this.m_rawResultTank = new FluidTank(fluidCapacity);
		this.m_resultTank = new TankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);
		this.m_internalCurrentStorer = new CurrentHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxSafeCurrentTransfer = maxCurrentTransfer;
		this.m_maxFluidTransferRate = maxFluidTransferRate;
	} // end setup()
	
	
	
	@Override
	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack stack, boolean simulate)
	{
		return switch(slot) 
		{
			case ExtractorBlockEntity.INPUT_SLOT -> true;
			case ExtractorBlockEntity.DRAIN_RESULT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(stack);
			case ExtractorBlockEntity.INPUT_REMAINDER_SLOT -> false;
			case ExtractorBlockEntity.POWER_SLOT -> SlotUtil.isValidPowerSlotInsert(stack);
			default -> throw new IllegalArgumentException("Unexpected value of: " + slot);
		};
	} // end itemInsertionValidator()

	

	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState state)
	{
		boolean changed = this.m_craftingManager.tick(level, blockPos);
		changed |= this.doDrainResultTank();
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()
	
	private boolean doDrainResultTank() 
	{
		boolean changed = false;		
		if (this.m_resultTankDrainCountDown > 0)
		{
			this.m_resultTankDrainCountDown = SlotUtil.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_initialDrainResultTankTransferSize, this.m_resultTankDrainCountDown, this.m_maxFluidTransferRate);
			if (this.m_resultTankDrainCountDown <= 0)
			{
				this.m_initialDrainResultTankTransferSize = 0;
			}
			changed = true;
		}
		if(m_initialDrainResultTankTransferSize == 0) 
		{
			this.m_initialDrainResultTankTransferSize = SlotUtil.queueToDrainToSlot(this.m_inventory, DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_maxFluidTransferRate);
			this.m_resultTankDrainCountDown = this.m_initialDrainResultTankTransferSize;
			changed = true;
		}
		return changed;
	} // end doDrainResultTank()
	
	protected void setRemainderFlag(boolean value) 
	{
		this.m_flagHandler.setValue(ExtractorBlockEntity.HAS_REMAINDER_FLAG_INDEX, value);
	} // end setRemainderFlag()
	
	

	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put(ExtractorBlockEntity.TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(ExtractorBlockEntity.TANK_TAG_NAME)) 
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(ExtractorBlockEntity.TANK_TAG_NAME));
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


} // end class
*/