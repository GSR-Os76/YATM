package com.gsr.gsr_yatm.block.device.still;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.CraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.HeatAcceleratedCraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.HeatingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.LitSetterBehavior;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.IInvalidatableCapabilityProvider;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.distilling.DistillingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.CompoundTank;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.generic.SetUtil;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple4;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class StillBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int DRAIN_REMAINDER_TANK_SLOT = 3;
	public static final int DRAIN_DISTILLATE_TANK_SLOT = 2;
	public static final int HEAT_SLOT = 4;

	public static final int LAST_INVENTORY_SLOT = StillBlockEntity.INVENTORY_SLOT_COUNT - 1;
	public static final int FIRST_DRAIN_FLUID_SLOT = StillBlockEntity.DRAIN_INPUT_TANK_SLOT;
	public static final int LAST_DRAIN_FLUID_SLOT = StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT;

	public static final String INPUT_TANK_SPEC_KEY = "inputTank";
	public static final String REMAINDER_TANK_SPEC_KEY = "remainderTank";
	public static final String DISTILLATE_TANK_SPEC_KEY = "distillateTank";
	public static final String TEMPERATURE_SPEC_KEY = "temperature";
	public static final String MAX_TEMPERATURE_SPEC_KEY = "maxTemperature";
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	
	public static final String FILL_INPUT_PROGRESS_SPEC_KEY = "fillInputProgress";
	public static final String DRAIN_INPUT_PROGRESS_SPEC_KEY = "drainInputProgress";
	public static final String DRAIN_REMAINDER_PROGRESS_SPEC_KEY = "drainRemainderProgress";
	public static final String DRAIN_DISTILLATE_PROGRESS_SPEC_KEY = "drainDistillateProgress";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(INPUT_TANK_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT),
			Map.entry(REMAINDER_TANK_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT),
			Map.entry(DISTILLATE_TANK_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT),
			Map.entry(TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(MAX_TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(CRAFT_PROGRESS_SPEC_KEY, CraftingManager.SLOT_COUNT),
			Map.entry(BURN_PROGRESS_SPEC_KEY, HeatingManager.SLOT_COUNT),
			Map.entry(FILL_INPUT_PROGRESS_SPEC_KEY, FillTankManager.SLOT_COUNT),
			Map.entry(DRAIN_INPUT_PROGRESS_SPEC_KEY, DrainTankManager.SLOT_COUNT),
			Map.entry(DRAIN_REMAINDER_PROGRESS_SPEC_KEY, DrainTankManager.SLOT_COUNT),
			Map.entry(DRAIN_DISTILLATE_PROGRESS_SPEC_KEY, DrainTankManager.SLOT_COUNT)
			));
	
	

	public StillBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.STILL.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		FluidTank in = this.m_helpers.newTank(YATMConfigs.STILL_INPUT_TANK_CAPACITY.get());
		FluidTank r = this.m_helpers.newTank(YATMConfigs.STILL_REMAINDER_TANK_CAPACITY.get());
		FluidTank d = this.m_helpers.newTank(YATMConfigs.STILL_DISTILLATE_TANK_CAPACITY.get());
		
		IFluidTank wIn = TankWrapper.Builder.of(in).maxFillTransfer(YATMConfigs.STILL_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get()).maxDrainTransfer(YATMConfigs.STILL_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get()).build();
		IFluidTank wR = TankWrapper.Builder.of(r).maxDrainTransfer(YATMConfigs.STILL_DRAIN_REMAINDER_MAX_FLUID_TRANSFER_RATE.get()).build();
		IFluidTank wD = TankWrapper.Builder.of(d).maxDrainTransfer(YATMConfigs.STILL_DRAIN_DISTILLATE_MAX_FLUID_TRANSFER_RATE.get()).build();
		IFluidHandler cT = new CompoundTank(wIn, wR, wD);
		
		OnChangedHeatHandler h = this.m_helpers.newHeatHandler(IHeatHandler.getAmbientTemp(this.getLevel(), this.getBlockPos()), YATMConfigs.STILL_MAX_TEMPERATURE.get());
		
		BackedFunction<IItemHandler, FillTankManager> inFM = new BackedFunction<>((i) -> new FillTankManager(i, StillBlockEntity.FILL_INPUT_TANK_SLOT, in, YATMConfigs.STILL_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, HeatingManager> hM = new BackedFunction<>((i) -> new HeatingManager(i, StillBlockEntity.HEAT_SLOT, h));
		BackedFunction<IItemHandler, InputComponentManager<IFluidHandler>> inFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, StillBlockEntity.FILL_INPUT_TANK_SLOT, TankWrapper.Builder.of(in).canDrain(() -> false).build(), ForgeCapabilities.FLUID_HANDLER));
		BackedFunction<IItemHandler, InputComponentManager<IHeatHandler>> hFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, StillBlockEntity.HEAT_SLOT, h, YATMCapabilities.HEAT));

		BackedFunction<IItemHandler, CraftingManager<DistillingRecipe, Container, Tuple4<IFluidHandler, IFluidHandler, IFluidHandler, IHeatHandler>>> cM = new BackedFunction<>((i) -> new CraftingManager<>(YATMRecipeTypes.DISTILLING.get(), () -> Tuple.of(in, r, d, h)));
		// TODO, should drain max rate be specified here?
		BackedFunction<IItemHandler, DrainTankManager> inDM = new BackedFunction<>((i) -> new DrainTankManager(i, StillBlockEntity.DRAIN_INPUT_TANK_SLOT, in, YATMConfigs.STILL_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, DrainTankManager> rDM = new BackedFunction<>((i) -> new DrainTankManager(i, StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT, r, YATMConfigs.STILL_DRAIN_REMAINDER_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, DrainTankManager> dDM = new BackedFunction<>((i) -> new DrainTankManager(i, StillBlockEntity.DRAIN_DISTILLATE_TANK_SLOT, d, YATMConfigs.STILL_DRAIN_DISTILLATE_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, OutputComponentManager> inDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, StillBlockEntity.DRAIN_INPUT_TANK_SLOT, () -> List.of(), YATMConfigs.STILL_DRAIN_INPUT_RECHECK_PERIOD.get()));
		BackedFunction<IItemHandler, OutputComponentManager> rDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT, () -> List.of(Direction.DOWN), YATMConfigs.STILL_DRAIN_REMAINDER_RECHECK_PERIOD.get()));
		BackedFunction<IItemHandler, OutputComponentManager> dDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, StillBlockEntity.DRAIN_DISTILLATE_TANK_SLOT, () -> List.of(Direction.UP), YATMConfigs.STILL_DRAIN_DISTILLATE_RECHECK_PERIOD.get()));
		
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> i);
				
		
		IInvalidatableCapabilityProvider fCP = new IInvalidatableCapabilityProvider()
		{
			private @NotNull LazyOptional<IHeatHandler> hL = LazyOptional.of(() -> h);
			@Override
			public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
			{
				if(cap == YATMCapabilities.HEAT) 
				{
					this.hL.cast();
				}
				return defaultCapabilityProvider.getCapability(cap);
			} // end getCapability()

			@Override
			public void invalidateCaps()
			{
				hL.invalidate();
			} // end invalidateCaps()

			@Override
			public void reviveCaps()
			{
				hL = LazyOptional.of(() -> h);
			} // end reviveCaps()
			
		};
		DeviceBuilder.of(this::createInventory, definitionReceiver)
				.inventory()
				.slot().insertionValidator(SlotUtil.TANK_FILL_SLOT_INSERTION_VALIDATOR).end()
				.slot().insertionValidator(SlotUtil.TANK_DRAIN_SLOT_INSERTION_VALIDATOR).end()
				.slot().insertionValidator(SlotUtil.TANK_DRAIN_SLOT_INSERTION_VALIDATOR).end()
				.slot().insertionValidator(SlotUtil.TANK_DRAIN_SLOT_INSERTION_VALIDATOR).end()
				.slot().insertionValidator(SlotUtil.HEAT_SLOT_INSERTION_VALIDATOR).end()
				.end()
				
				.getInventory(inv::apply)
				
				.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
				.behavior(new SerializableBehavior(() -> in.writeToNBT(new CompoundTag()), in::readFromNBT, "inputTank")).allDefaults().end()
				.behavior(new SerializableBehavior(() -> d.writeToNBT(new CompoundTag()), d::readFromNBT, "distillateTank")).allDefaults().end()
				.behavior(new SerializableBehavior(() -> r.writeToNBT(new CompoundTag()), r::readFromNBT, "remainderTank")).allDefaults().end()
				.behavior(new SerializableBehavior(h, "heatHandler")).allDefaults().end()
				.behavior(inFM::apply).allDefaults().end()
				.behavior(hM::apply).allDefaults().end()
				.behavior(inFCM::apply).allDefaults().end()
				.behavior(hFCM::apply).allDefaults().end()
				.behavior(cM::apply).changeListener().serializable().end()
				.behavior(new HeatAcceleratedCraftingManager(cM.get()::tick, h, cM.get()::getActiveRecipe)).allDefaults().end()
				.behavior(inDM::apply).tickable().asSerializableWithKey("inputDrainManager").end()
				.behavior(dDM::apply).tickable().asSerializableWithKey("distillateDrainManager").end()
				.behavior(rDM::apply).tickable().asSerializableWithKey("remainderDrainManager").end()
				.behavior(inDCM::apply).allDefaults().end()
				.behavior(rDCM::apply).allDefaults().end()
				.behavior(dDCM::apply).allDefaults().end()
				.behavior(new LitSetterBehavior(h, YATMConfigs.STILL_LIT_ABOVE_TEMPERATURE)).allDefaults().end()
				
				.containerData()
				.addContainerData(new FluidHandlerContainerData(in))
				.addContainerData(new FluidHandlerContainerData(r))
				.addContainerData(new FluidHandlerContainerData(d))
				.addProperty(new Property<>(h::getTemperature, (i) -> {}))
				.addProperty(new Property<>(h::maxTemperature, (i) -> {}))
				.addContainerData(cM.get().getData())
				.addContainerData(hM.get().getData())
				.addContainerData(inFM.get().getData())
				.addContainerData(inDM.get().getData())
				.addContainerData(rDM.get().getData())
				.addContainerData(dDM.get().getData())
				.end()
				
				.capabilityProvider()
				.onInvalidate(() -> {inFCM.get().invalidateCaps(); hFCM.get().invalidateCaps(); inDCM.get().invalidateCaps(); rDCM.get().invalidateCaps(); dDCM.get().invalidateCaps(); fCP.invalidateCaps();})
				.onRevive(fCP::reviveCaps)
				.face((Direction)null)
				.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
				.elifReturnsWhen(ForgeCapabilities.FLUID_HANDLER, cT)
				.elifReturnsWhen(YATMCapabilities.HEAT, h).end()
				// ordered by index
				// fill input tank
				.face(() -> SetUtil.of(this.getBlockState().getValue(StillBlock.FACING)))
				.returns(inFCM.get())
				.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), StillBlockEntity.FILL_INPUT_TANK_SLOT)).end()
				// drain input tank
				.face(() -> SetUtil.of())
				.returns(inDCM.get())
				.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), StillBlockEntity.DRAIN_INPUT_TANK_SLOT)).end()
				// drain remainder tank
				.face(Direction.DOWN)
				.returns(rDCM.get())
				.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT)).end()
				// drain distillate tank
				.face(Direction.UP)
				.returns(dDCM.get())
				.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), StillBlockEntity.DRAIN_DISTILLATE_TANK_SLOT)).end()
				// heat
				.face(() -> SetUtil.of(Direction.Plane.HORIZONTAL.stream().filter((f) -> f != this.getBlockState().getValue(StillBlock.FACING)))) 
				.returns(hFCM.get())
				.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), StillBlockEntity.HEAT_SLOT)).end()
				.last(fCP)
				
				.end();
	} // end getDeviceDefinition()

} // end class