package com.gsr.gsr_yatm.block.device.crafting.extractor;

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
		
		.last(defaultCapabilityProvider)
		
		.end();
		this.m_inventory = inv.get();
	} // end define()
	
} // end class