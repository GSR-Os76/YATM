package com.gsr.gsr_yatm.block.device.still;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StillBlockEntity extends BlockEntity
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

} // end class