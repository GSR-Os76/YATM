package com.gsr.gsr_yatm.block.device.current_storer.base;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentDrainManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentFillManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.OutputCurrentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.utility.TickableBehaviorConditioner;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.CapabilityProviderBuilderL;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.utilities.DirectionUtil;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
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

public abstract class CurrentStorerBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 2;
	
	public static final int FILL_POWER_SLOT = 0;
	public static final int DRAIN_POWER_SLOT = 1;
	
	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT)
			));
	
	
	
	public CurrentStorerBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state)
	{
		super(Objects.requireNonNull(type), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		//current holding thingy
		
		//in component and out component
		//conditioned on component push out
		
		//capability provider exposing internal battery, or slotted component, conditioned by face on component absence/presence
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> i);
		CurrentHandler c = this.m_helpers.newCurrentHandler(this.getCurrentCapacity(), this.getMaxCurrentTransfer());
		ICurrentHandler drainC = this.m_helpers.noFill(c);
		ICurrentHandler fillC = this.m_helpers.noDrain(c);
		
		BackedFunction<IItemHandler, CurrentFillManager> cFM = new BackedFunction<>((i) -> new CurrentFillManager(i, CurrentStorerBlockEntity.FILL_POWER_SLOT, c, this.getMaxCurrentTransfer()));
		BackedFunction<IItemHandler, CurrentDrainManager> cDM = new BackedFunction<>((i) -> new CurrentDrainManager(i, CurrentStorerBlockEntity.DRAIN_POWER_SLOT, c, this.getMaxCurrentTransfer()));
		BackedFunction<IItemHandler, InputComponentManager<ICurrentHandler>> cFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, CurrentStorerBlockEntity.FILL_POWER_SLOT, this.m_helpers.noDrain(c), YATMCapabilities.CURRENT));
		BackedFunction<IItemHandler, OutputComponentManager> cDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, CurrentStorerBlockEntity.DRAIN_POWER_SLOT, () -> List.of(this.getBlockState().getValue(CurrentStorerBlock.FACING)), this.getOutputRecheckPeriod()));

		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(c, "current")).allDefaults().end()
		.behavior(cFM::apply).allDefaults().end()
		.behavior(cFCM::apply).allDefaults().end()
		.behavior(cDM::apply).allDefaults().end()
		.behavior(cDCM::apply).allDefaults().end()
		.behavior(new TickableBehaviorConditioner(() -> !cDCM.get().hasComponent(), new OutputCurrentManager(() -> List.of(this.getBlockState().getValue(CurrentStorerBlock.FACING)), this.getOutputRecheckPeriod(), c, this.getMaxCurrentTransfer()))).allDefaults().end()
		
		.containerData()
		.addContainerData(new CurrentHandlerContainerData(c))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		// TODO, assure hoppers can fill and place items in slots?
		.face(() -> Stream.of(Direction.values()).filter((d) -> d != this.getBlockState().getValue(CurrentStorerBlock.FACING)).collect(Collectors.toSet()))
		.returns(CapabilityUtil.conditionProvider(cFCM.get()::hasComponent, cFCM.get(), 
				new CapabilityProviderBuilderL()
				.face(() -> DirectionUtil.ALL_AND_NULL)
				.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), CurrentStorerBlockEntity.FILL_POWER_SLOT))
				.elifReturnsWhen(YATMCapabilities.CURRENT, fillC).end().last(defaultCapabilityProvider)
				))
		.end()
		
		.face(() -> Set.of(this.getBlockState().getValue(CurrentStorerBlock.FACING)))
		.returns(CapabilityUtil.conditionProvider(cDCM.get()::hasComponent, cDCM.get(), 
				new CapabilityProviderBuilderL()
				.face(() -> DirectionUtil.ALL_AND_NULL)
				.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), CurrentStorerBlockEntity.DRAIN_POWER_SLOT))
				.elifReturnsWhen(YATMCapabilities.CURRENT, drainC).end().last(defaultCapabilityProvider)
				))
		.end()
		
		.last(defaultCapabilityProvider)
		.end();
	} // end define()
	
	
	
	protected abstract @NotNegative int getCurrentCapacity();

	protected abstract @NotNegative int getMaxCurrentTransfer();
	
	protected abstract @NotNegative int getOutputRecheckPeriod();
} // end class