package com.gsr.gsr_yatm.fluid;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

public class LatexFluidType extends FluidType
{

	public LatexFluidType(Properties properties)
	{
		super(properties);
	} // end constructor

	

	public boolean isVaporizedOnPlacement(Level level, BlockPos pos, FluidStack stack)
	{
		return level.dimensionType().ultraWarm();
	} // end isVaporizedOnPlacement()

	@Override
	public void onVaporize(@Nullable Player player, Level level, BlockPos pos, FluidStack stack)
	{
		super.onVaporize(player, level, pos, stack);
		if(stack.getAmount() >= 1000) 
		{
			level.setBlockAndUpdate(pos, YATMBlocks.RUBBER_BLOCK.get().defaultBlockState());
		}
	} // end onVaporize()	




} // end class