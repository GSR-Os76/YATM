package com.gsr.gsr_yatm.block.device.crafting.injector;

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
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.injecting.InjectingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.generic.SetUtil;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple3;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
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

public class InjectorBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int SUBSTRATE_SLOT = 2;
	public static final int RESULT_SLOT = 3;
	public static final int POWER_SLOT = 4;
	
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	public static final String DRAIN_INPUT_PROGRESS_SPEC_KEY = "drainInputProgress";
	public static final String FILL_INPUT_PROGRESS_SPEC_KEY = "fillInputProgress";
	public static final String INPUT_TANK_SPEC_KEY = "inputTank";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CRAFT_PROGRESS_SPEC_KEY, CraftingManager.SLOT_COUNT),
			Map.entry(CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT),
			Map.entry(DRAIN_INPUT_PROGRESS_SPEC_KEY, DrainTankManager.SLOT_COUNT),
			Map.entry(FILL_INPUT_PROGRESS_SPEC_KEY, FillTankManager.SLOT_COUNT),
			Map.entry(INPUT_TANK_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT)
			));
	
	
	
	public InjectorBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.INJECTOR.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> InventoryWrapper.Builder.of(i).slotValidator((s, is, sim) -> s != InjectorBlockEntity.RESULT_SLOT).build());
		FluidTank in = this.m_helpers.newTank(YATMConfigs.INJECTOR_INPUT_TANK_CAPACITY.get());
		TankWrapper wIn = TankWrapper.Builder.of(in).maxDrainTransfer(YATMConfigs.INJECTOR_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get()).build();
		CurrentHandler c = this.m_helpers.newCurrentHandler(YATMConfigs.INJECTOR_CURRENT_CAPACITY.get(), YATMConfigs.INJECTOR_MAX_CURRENT_TRANSFER.get());
		
		BackedFunction<IItemHandler, FillTankManager> inFM = new BackedFunction<>((i) -> new FillTankManager(i, InjectorBlockEntity.FILL_INPUT_TANK_SLOT, in, YATMConfigs.INJECTOR_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, CurrentFillManager> cFM = new BackedFunction<>((i) -> new CurrentFillManager(i, InjectorBlockEntity.POWER_SLOT, c, YATMConfigs.INJECTOR_MAX_CURRENT_TRANSFER.get()));
		BackedFunction<IItemHandler, InputComponentManager<IFluidHandler>> inFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, InjectorBlockEntity.FILL_INPUT_TANK_SLOT, TankWrapper.Builder.of(in).canDrain(() -> false).build(), ForgeCapabilities.FLUID_HANDLER));
		BackedFunction<IItemHandler, InputComponentManager<ICurrentHandler>> cFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, InjectorBlockEntity.POWER_SLOT, this.m_helpers.noDrain(c), YATMCapabilities.CURRENT));
		
		BackedFunction<IItemHandler, CraftingManager<InjectingRecipe, Container, Tuple3<IFluidHandler, IItemHandler, ICurrentHandler>>> cM = new BackedFunction<>((i) -> new CraftingManager<>(YATMRecipeTypes.INJECTING.get(), () -> Tuple.of(in, i, c)));
		
		BackedFunction<IItemHandler, DrainTankManager> inDM = new BackedFunction<>((i) -> new DrainTankManager(i, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT, in, YATMConfigs.INJECTOR_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, OutputComponentManager> inDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT, () -> List.of(), YATMConfigs.INJECTOR_DRAIN_INPUT_RECHECK_PERIOD.get()));

		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().insertionValidator(SlotUtil.TANK_FILL_SLOT_INSERTION_VALIDATOR).end()
		.slot().insertionValidator(SlotUtil.TANK_DRAIN_SLOT_INSERTION_VALIDATOR).end()
		.slot().end()
		.slot().end()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(() -> in.writeToNBT(new CompoundTag()), in::readFromNBT, "inputTank")).allDefaults().end()
		.behavior(new SerializableBehavior(c, "current")).allDefaults().end()
		.behavior(inFM::apply).allDefaults().end()
		.behavior(cFM::apply).allDefaults().end()
		.behavior(inFCM::apply).allDefaults().end()
		.behavior(cFCM::apply).allDefaults().end()
		.behavior(cM::apply).allDefaults().end()
		.behavior(inDM::apply).allDefaults().end()
		.behavior(inDCM::apply).allDefaults().end()
		
		.containerData()
		.addContainerData(cM.get().getData())
		.addContainerData(new CurrentHandlerContainerData(c))
		.addContainerData(inDM.get().getData())
		.addContainerData(inFM.get().getData())
		.addContainerData(new FluidHandlerContainerData(in))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(ForgeCapabilities.FLUID_HANDLER, wIn)
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		
		.face(Direction.UP)
		.returns(inFCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), InjectorBlockEntity.FILL_INPUT_TANK_SLOT)).end()
		
		.face(Set::of)
		.returns(inDCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT)).end()
		
		.face(Set::of)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), InjectorBlockEntity.SUBSTRATE_SLOT)).end()
		
		.face(Direction.DOWN)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), InjectorBlockEntity.RESULT_SLOT)).end()
		
		.face(() -> SetUtil.of(Direction.Plane.HORIZONTAL.stream()))
		.returns(cFCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), InjectorBlockEntity.POWER_SLOT)).end()
	
		.last(defaultCapabilityProvider)
		
		.end();
		this.m_inventory = inv.get();
	} // end define()
	
} // end class