package com.gsr.gsr_yatm.block.device.current_storer;

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
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentFillManager;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.grinder.GrinderBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
	
	
	
	public CurrentStorerBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CURRENT_STORER.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		//current holding thingy
		
		//in component and out component
		//conditioned on component push out
		
		//capability provider exposing internal battery, or slotted component, conditioned by face on component absence/presence
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> InventoryWrapper.Builder.of(i).slotValidator((s, is, sim) -> s != GrinderBlockEntity.RESULT_SLOT).build());
		CurrentHandler c = this.m_helpers.newCurrentHandler(this.getCurrentCapacity(), this.getMaxCurrentTransfer());
		
		BackedFunction<IItemHandler, CurrentFillManager> cFM = new BackedFunction<>((i) -> new CurrentFillManager(i, GrinderBlockEntity.POWER_SLOT, c, this.getMaxCurrentTransfer()));
		// cDM, current drain manager
		BackedFunction<IItemHandler, InputComponentManager<ICurrentHandler>> cFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, GrinderBlockEntity.POWER_SLOT, this.m_helpers.noDrain(c), YATMCapabilities.CURRENT));
		BackedFunction<IItemHandler, OutputComponentManager> cDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, GrinderBlockEntity.POWER_SLOT, () -> List.of(this.getBlockState().getValue(CurrentStorerBlock.FACING)), this.getOutputRecheckPeriod()));

		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(c, "current")).allDefaults().end()
		.behavior(cFM::apply).allDefaults().end()
		.behavior(cFCM::apply).allDefaults().end()
		// TODO .behavior(cDM::apply).allDefaults.end()
		.behavior(cDCM::apply).allDefaults().end()
		// TODO .behavior(push current out, conditioned on output component presence)
		
		.containerData()
		.addContainerData(new CurrentHandlerContainerData(c))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		
		.face(() -> Stream.of(Direction.values()).filter((d) -> d == this.getBlockState().getValue(CurrentStorerBlock.FACING)).collect(Collectors.toSet()))
		.returns(CapabilityUtil.providerOrCapabiltyOrDefault(cFCM.get()::hasComponent, cFCM.get(), YATMCapabilities.CURRENT, c, defaultCapabilityProvider))
		.end()
		
		.face(() -> Set.of(this.getBlockState().getValue(CurrentStorerBlock.FACING)))
		.returns(CapabilityUtil.providerOrCapabiltyOrDefault(cDCM.get()::hasComponent, cFCM.get(), YATMCapabilities.CURRENT, c, defaultCapabilityProvider))
		.end()
		
		.last(defaultCapabilityProvider)
		.end();
	} // end define()
	
	
	
	protected abstract @NotNegative int getCurrentCapacity();

	protected abstract @NotNegative int getMaxCurrentTransfer();
	
	protected abstract @NotNegative int getOutputRecheckPeriod();
} // end class