package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import oshi.util.tuples.Pair;

public interface IDeviceBlockEntity// extends ITickingBlockEntity, IInventoryDroppingBlockEntity
{
	public static @NotNull Pair<Integer, Integer> deviceHeatEquation(@NotNegative int self, @NotNegative int other)
	{
		return IHeatHandler.levelTemperatures(self, self <= other ? other : Math.max(other, self - Math.max(DeviceBlockEntity.MINIMUM_CHANGE_PER_AMBIENT_COOLING, ((int)((self - other) * DeviceBlockEntity.AMBIENT_COOLING_FACTOR)))));
	} // end deviceHeatEquation()
	
	
	default @NotNull BlockEntity self() 
	{
		// TODO, maybe give more informative exception
		return (BlockEntity)this;
	} // end self()
	
	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull IDeviceBlockEntity blockEntity)
	{
		if (level.isClientSide)
		{
			blockEntity.clientTick(level, position, state);
		}
		else
		{
			blockEntity.serverTick(level, position, state);
		}
	} // end tick()

	default void clientTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) { } // end clientTick()
	
	default void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) { } // end serverTick()
	
	@NotNull NonNullList<ItemStack> getDropInventory();

} // end interface