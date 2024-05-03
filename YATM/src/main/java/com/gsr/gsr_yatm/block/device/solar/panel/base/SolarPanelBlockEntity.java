package com.gsr.gsr_yatm.block.device.solar.panel.base;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableSet;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentDrainManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.OutputCurrentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.SolarGeneratorManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.utility.TickableBehaviorConditioner;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelSettings;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

public abstract class SolarPanelBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 1;
	
	public static final int DRAIN_POWER_SLOT = 0;
	
	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT)
			));
	
	private static final Set<Direction> POWERABLE_FACES = ImmutableSet.of(Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
	
	
	
	public SolarPanelBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state)
	{
		super(Objects.requireNonNull(type), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> i);
		CurrentHandler c = this.m_helpers.newCurrentHandler(this.getSettings().currentCapacity(), this.getSettings().maxCurrentTransfer());
		ICurrentHandler drainC = this.m_helpers.noFill(c);
		
		BackedFunction<IItemHandler, CurrentDrainManager> cDM = new BackedFunction<>((i) -> new CurrentDrainManager(i, SolarPanelBlockEntity.DRAIN_POWER_SLOT, c, this.getSettings().maxCurrentTransfer()));
		BackedFunction<IItemHandler, OutputComponentManager> cDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, SolarPanelBlockEntity.DRAIN_POWER_SLOT, () -> List.copyOf(SolarPanelBlockEntity.POWERABLE_FACES), this.getSettings().outputRecheckPeriod()));

		
		
		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(c, "current")).allDefaults().end()
		.behavior(new SolarGeneratorManager(c, this.getSettings())).allDefaults().end()
		.behavior(cDM::apply).allDefaults().end()
		.behavior(cDCM::apply).allDefaults().end()
		.behavior(new TickableBehaviorConditioner(() -> !cDCM.get().hasComponent(), new OutputCurrentManager(() -> List.copyOf(SolarPanelBlockEntity.POWERABLE_FACES), this.getSettings().outputRecheckPeriod(), c, this.getSettings().maxCurrentTransfer()))).allDefaults().end()
		
		
		.containerData()
		.addContainerData(new CurrentHandlerContainerData(c))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		
		.face(() -> POWERABLE_FACES)
		.returns(CapabilityUtil.providerOrCapabiltyOrDefault(cDCM.get()::hasComponent, cDCM.get(), YATMCapabilities.CURRENT, drainC, defaultCapabilityProvider)).end()
//.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), SolarPanelBlockEntity.DRAIN_POWER_SLOT)).end()
		.last(defaultCapabilityProvider)
			
		.end();
	} // end define()
	
	
	
	public abstract @NotNull SolarPanelSettings getSettings();

} // end class