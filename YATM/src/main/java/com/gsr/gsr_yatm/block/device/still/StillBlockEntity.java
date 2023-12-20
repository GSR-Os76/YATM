package com.gsr.gsr_yatm.block.device.still;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.CraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.HeatAcceleratedCraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.HeatingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.LitSetterBehavior;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.distilling.DistillingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple4;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class StillBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int DRAIN_DISTILLATE_TANK_SLOT = 2;
	public static final int DRAIN_REMAINDER_TANK_SLOT = 3;
	public static final int HEAT_SLOT = 4;

	public static final int LAST_INVENTORY_SLOT = StillBlockEntity.INVENTORY_SLOT_COUNT - 1;
	public static final int FIRST_DRAIN_FLUID_SLOT = StillBlockEntity.DRAIN_INPUT_TANK_SLOT;
	public static final int LAST_DRAIN_FLUID_SLOT = StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT;

	

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
//		TankWrapper dW = TankWrapper.Builder.of(d).fillValidator((f) -> false).build();
		OnChangedHeatHandler h = this.m_helpers.newHeatHandler(YATMConfigs.STILL_MAX_TEMPERATURE.get());
		
		BackedFunction<IItemHandler, FillTankManager> inFM = new BackedFunction<>((i) -> new FillTankManager(i, StillBlockEntity.FILL_INPUT_TANK_SLOT, in, YATMConfigs.STILL_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, HeatingManager> hM = new BackedFunction<>((i) -> new HeatingManager(i, StillBlockEntity.HEAT_SLOT, h));
		BackedFunction<IItemHandler, CraftingManager<DistillingRecipe, Container, Tuple4<IFluidHandler, IFluidHandler, IFluidHandler, IHeatHandler>>> cM = new BackedFunction<>((i) -> new CraftingManager<>(YATMRecipeTypes.DISTILLING.get(), () -> new Tuple4<IFluidHandler, IFluidHandler, IFluidHandler, IHeatHandler>(in, r, d, h)));
		BackedFunction<IItemHandler, DrainTankManager> inDM = new BackedFunction<>((i) -> new DrainTankManager(i, StillBlockEntity.DRAIN_INPUT_TANK_SLOT, in, YATMConfigs.STILL_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, DrainTankManager> dDM = new BackedFunction<>((i) -> new DrainTankManager(i, StillBlockEntity.DRAIN_DISTILLATE_TANK_SLOT, d, YATMConfigs.STILL_DRAIN_DISTILLATE_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, DrainTankManager> rDM = new BackedFunction<>((i) -> new DrainTankManager(i, StillBlockEntity.DRAIN_REMAINDER_TANK_SLOT, r, YATMConfigs.STILL_DRAIN_REMAINDER_MAX_FLUID_TRANSFER_RATE.get()));
		
		new DeviceBuilder<>((Void)null, this::createInventory, definitionReceiver)
				.inventory()
				.slot().insertionValidator(SlotUtil::isValidTankFillSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidTankDrainSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidTankDrainSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidTankDrainSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidHeatingSlotInsert).end()
				.end()
				.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
				.behavior(new SerializableBehavior(() -> in.writeToNBT(new CompoundTag()), in::readFromNBT, "inputTank")).allDefaults().end()
				.behavior(new SerializableBehavior(() -> d.writeToNBT(new CompoundTag()), d::readFromNBT, "distillateTank")).allDefaults().end()
				.behavior(new SerializableBehavior(() -> r.writeToNBT(new CompoundTag()), r::readFromNBT, "remainderTank")).allDefaults().end()
				.behavior(new SerializableBehavior(h, "heatHandler")).allDefaults().end()
				.behavior(inFM::apply).allDefaults().end()
				.behavior(hM::apply).allDefaults().end()
				.behavior(new LitSetterBehavior(h, YATMConfigs.STILL_LIT_ABOVE_TEMPERATURE)).allDefaults().end()
				// components, in
				// TODO, crafting may not be working, seemingly is not
				.behavior(cM::apply).changeListener().serializable().end()
				.behavior(new HeatAcceleratedCraftingManager(cM.get()::tick, h, cM.get()::getActiveRecipe)).allDefaults().end()
				.behavior(inDM::apply).tickable().asSerializableWithKey("inputDrainManager").end()
				.behavior(dDM::apply).tickable().asSerializableWithKey("distillateDrainManager").end()
				.behavior(rDM::apply).tickable().asSerializableWithKey("remainderDrainManager").end()
				// components, out
				// containerData
				// capabilities
				.end();
	} // end getDeviceDefinition()

	
} // end class