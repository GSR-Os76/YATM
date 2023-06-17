package com.gsr.gsr_yatm.fluid;

import com.gsr.gsr_yatm.YATMBlocks;
import com.gsr.gsr_yatm.YATMFluidTypes;
import com.gsr.gsr_yatm.YATMFluids;
import com.gsr.gsr_yatm.YATMItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;

public abstract class LatexFluid extends FlowingFluid
{

	@Override
	public Fluid getFlowing()
	{
		return YATMFluids.LATEX_FLOWING.get();
	} // end getFlowing()

	@Override
	public Fluid getSource()
	{
		return YATMFluids.LATEX.get();
	} // end getSource()
	
	

	@Override
	protected boolean canConvertToSource(Level level)
	{
		return false;
	} // end canConvertToSource

	@Override
	protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState)
	{
		
	} // end beforeDestroyingBlock()

	@Override
	protected int getSlopeFindDistance(LevelReader p_76074_)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getDropOff(LevelReader levelReader)
	{
		// TODO, figure out what exactly this's
		return 1;
	} // end getDropOff()

	@Override
	public Item getBucket()
	{
		return YATMItems.LATEX_BUCKET.get();
	} // end getBucket()

	@Override
	protected boolean canBeReplacedWith(FluidState p_76127_, BlockGetter p_76128_, BlockPos p_76129_, Fluid p_76130_, Direction p_76131_)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTickDelay(LevelReader p_76120_)
	{
		return 4;
	} // end getTickDelay()

	@Override
	protected float getExplosionResistance()
	{
		// TODO, learn explosion resistance
		return 2f;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState state)
	{
		return YATMBlocks.LATEX_LIQUID_BLOCK.get().defaultBlockState();
	} // end createLegacyBlock()

	// maybe override isSame()
	
	@Override
	public FluidType getFluidType()
	{
		return YATMFluidTypes.LATEX.get();
	} // end getFluidType()
	
	
	// IMPLEMENTATIONS \\
	public static class Flowing extends LatexFluid
	{
		

		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder)
		{
			super.createFluidStateDefinition(builder.add(LEVEL));
		} // end createFluidStateDefinition()

		public int getAmount(FluidState state)
		{
			return state.getValue(LEVEL);
		} // end getAmount()

		public boolean isSource(FluidState state)
		{
			return false;
		} // end isSource()

	} // end flowing class

	public static class Source extends LatexFluid
	{
		public int getAmount(FluidState state)
		{
			return 8;
		} // end getAmount

		public boolean isSource(FluidState state)
		{
			return true;
		} // end isSource()

	} // end source class
} // end class