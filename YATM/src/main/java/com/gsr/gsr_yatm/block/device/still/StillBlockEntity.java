package com.gsr.gsr_yatm.block.device.still;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.builder.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class StillBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int DRAIN_RESULT_HIGH_TANK_SLOT = 2;
	public static final int DRAIN_RESULT_LOW_TANK_SLOT = 3;
	public static final int HEAT_SLOT = 4;

	

	public StillBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.STILL.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor



	@Override
	protected @Nullable DeviceBuilder getBuilder()
	{
		FluidTank in = new FluidTank(YATMConfigs.STILL_INPUT_TANK_CAPACITY.get()) {

			@Override
			protected void onContentsChanged()
			{
				StillBlockEntity.this.setChanged();
			}};
			
		return DeviceBuilder.of(YATMBlockEntityTypes.STILL.get())
				.slot().insertionValidator(SlotUtil::isValidTankFillSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidTankDrainSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidTankDrainSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidTankDrainSlotInsert).end()
				.slot().insertionValidator(SlotUtil::isValidHeatingSlotInsert).end()
				
				.behavior((inv) -> new FillTankManager(inv, StillBlockEntity.FILL_INPUT_TANK_SLOT, in, YATMConfigs.STILL_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get()))
				// TODO, literally everythin else
				.end();
	} // end getBuilder

	
} // end class