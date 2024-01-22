package com.gsr.gsr_yatm.block.device.grinder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.CraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentFillManager;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.generic.SetUtil;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentHandlerContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

public class GrinderBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 3;

	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int POWER_SLOT = 2;
	
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CRAFT_PROGRESS_SPEC_KEY, CraftingManager.SLOT_COUNT),
			Map.entry(CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT)
			));
	
	
	
	public GrinderBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.GRINDER.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		CurrentHandler c = this.m_helpers.newCurrentHandler(YATMConfigs.GRINDER_CURRENT_CAPACITY.get());
		
		BackedFunction<IItemHandler, CurrentFillManager> cFM = new BackedFunction<>((i) -> new CurrentFillManager(i, GrinderBlockEntity.POWER_SLOT, c, YATMConfigs.GRINDER_MAX_CURRENT_TRANSFER.get()));
		BackedFunction<IItemHandler, InputComponentManager<ICurrentHandler>> cFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, GrinderBlockEntity.POWER_SLOT, this.m_helpers.noDrain(c), YATMCapabilities.CURRENT));
		BackedFunction<IItemHandler, CraftingManager<GrindingRecipe, Container, Tuple2<IItemHandler, ICurrentHandler>>> cM = new BackedFunction<>((i) -> new CraftingManager<>(YATMRecipeTypes.GRINDING.get(), () -> Tuple.of(i, c)));

		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> InventoryWrapper.Builder.of(i).slotValidator((s, is, sim) -> s != GrinderBlockEntity.RESULT_SLOT).build());
		
		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().end()
		.slot().end()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(c, "current")).allDefaults().end()
		.behavior(cFM::apply).allDefaults().end()
		.behavior(cFCM::apply).allDefaults().end()
		.behavior(cM::apply).allDefaults().end()
		
		.containerData()
		.addContainerData(cM.get().getData())
		.addContainerData(new CurrentHandlerContainerData(c))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		
		.face(Direction.UP)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), GrinderBlockEntity.INPUT_SLOT)).end()
		
		.face(Direction.DOWN)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), GrinderBlockEntity.RESULT_SLOT)).end()
		
		.face(() -> SetUtil.of(Direction.Plane.HORIZONTAL.stream()))
		.returns(cFCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), GrinderBlockEntity.POWER_SLOT)).end()
		.end()
		
		.end();
		this.m_inventory = inv.get();
	} // end define()
	
} // end class

/*CraftingDeviceBlockEntity<GrindingRecipe, Container, Tuple2<IItemHandler, ICurrentHandler>>
{	
	public static final int INVENTORY_SLOT_COUNT = 3;
	public static final int DATA_SLOT_COUNT = 4;

	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int POWER_SLOT = 2;
	
	public static final int GRIND_PROGESS_INDEX = 0;
	public static final int GRIND_TIME_INDEX = 1;
	public static final int CURRENT_STORED_INDEX = 2;
	public static final int CURRENT_CAPACITY_INDEX = 3;
	//public static final int TEST = 4;
	
	
	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";
	
	@Deprecated(forRemoval = true) public ICurrentHandler m_internalCurrentStorer;
	@Deprecated(forRemoval = true) public int m_maxSafeCurrentTransfer;
	
	protected ContainerData m_dataAccessor = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			return switch(index) 
			{
				// seperate this out
				case GRIND_PROGESS_INDEX -> 0;//GrinderBlockEntity.this.m_craftCountDown;
				case GRIND_TIME_INDEX -> 0;//GrinderBlockEntity.this.m_craftTime;
				// end
				// maybe start again
				case CURRENT_STORED_INDEX -> GrinderBlockEntity.this.m_internalCurrentStorer.stored();
				case CURRENT_CAPACITY_INDEX -> GrinderBlockEntity.this.m_internalCurrentStorer.capacity();
				// maybe end again
				//case TEST -> 0b0111_1111_1111_1111_1111_1111_1111_1111;
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
			return GrinderBlockEntity.DATA_SLOT_COUNT;
		} // end getCount
	};
	
	
	
	public GrinderBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0);
	} // end constructor
	
	public GrinderBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxCurrentTransfer)
	{
		super(YATMBlockEntityTypes.GRINDER.get(), blockPos, blockState, GrinderBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.GRINDING.get());
		this.setup(currentCapacity, maxCurrentTransfer);
	} // end constructor

	@Override
	public @NotNull Tuple2<IItemHandler, ICurrentHandler> getContext()
	{
		return new Tuple2<>(this.m_inventory, this.m_internalCurrentStorer);
	} // end getContext()

	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CURRENT_CAPACITY_TAG_NAME, this.m_internalCurrentStorer.capacity());
		tag.putInt(MAX_CURRENT_TAG_NAME, this.m_maxSafeCurrentTransfer);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(CompoundTag tag)
	{
		int currentCapacity = 0;
		int maxCurrentTransfer = 0;
		
		if(tag.contains(CURRENT_CAPACITY_TAG_NAME)) 
		{
			currentCapacity = tag.getInt(CURRENT_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_CURRENT_TAG_NAME)) 
		{
			maxCurrentTransfer = tag.getInt(MAX_CURRENT_TAG_NAME);
		}
		this.setup(currentCapacity, maxCurrentTransfer);
	} // end setupFromNBT()
	
	private void setup(int currentCapacity, int maxCurrentTransfer) 
	{
		this.m_internalCurrentStorer = new CurrentHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxSafeCurrentTransfer = maxCurrentTransfer;		
	} // end setup()
	
	
	
	
	
	
	@Override
	public ContainerData getDataAccessor()
	{
		return this.m_dataAccessor;
	} // end getDataAccussor()

	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack stack, boolean simulate)
	{
		return switch(slot) 
		{
			case GrinderBlockEntity.INPUT_SLOT -> true;
			case GrinderBlockEntity.POWER_SLOT -> SlotUtil.isValidPowerSlotInsert(stack);
			case GrinderBlockEntity.RESULT_SLOT -> false;
			
			default -> throw new IllegalArgumentException("Unexpected value: " + slot);
		};
	} // end itemInsertionValidator()

	
	
	@Override
	public void serverTick(Level level, BlockPos position, BlockState state)
	{
		boolean changed = this.m_craftingManager.tick(level, position);
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

} // end class*/