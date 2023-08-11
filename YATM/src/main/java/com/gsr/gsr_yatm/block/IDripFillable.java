package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public interface IDripFillable
{
	public boolean canRecieveFluid(@NotNull Fluid fluid);
	
	public void recieveFluid(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Fluid fluid);

	
	
//	public static boolean canBlockRecieveFluid(@NotNull Block block, @NotNull Fluid fluid) 
//	{
//		if(block instanceof IDripFillable df) 
//		{
//			return df.canRecieveFluid(fluid);
//		}
//		else if(block instanceof AbstractCauldronBlock acb) 
//		{
//			acb.
//		}
//		return false;
//	} // end canBlockRevieceFluid()
	
} // end interface