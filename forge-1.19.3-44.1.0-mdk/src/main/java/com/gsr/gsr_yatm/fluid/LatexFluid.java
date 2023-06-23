package com.gsr.gsr_yatm.fluid;

import com.gsr.gsr_yatm.YATMBlocks;
import com.gsr.gsr_yatm.YATMFluidTypes;
import com.gsr.gsr_yatm.YATMFluids;
import com.gsr.gsr_yatm.YATMItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
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
	protected BlockState createLegacyBlock(FluidState state)
	{
		return YATMBlocks.LATEX_LIQUID_BLOCK.get()
				.defaultBlockState()
				.setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
	} // end createLegacyBlock()
	
	@Override
	public FluidType getFluidType()
	{
		return YATMFluidTypes.LATEX.get();
	} // end getFluidType()
	
	@Override
	public Item getBucket()
	{
		return YATMItems.LATEX_BUCKET.get();
	} // end getBucket()
	
	@Override
	public boolean isSame(Fluid fluid)
	{
		return fluid == YATMFluids.LATEX.get() || fluid == YATMFluids.LATEX_FLOWING.get();
	} // end isSame()

	

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
	protected int getSlopeFindDistance(LevelReader levelReader)
	{
		return 1;
	} // end getSlopeFindDistance()

	@Override
	protected int getDropOff(LevelReader levelReader)
	{
		return 1;
	} // end getDropOff()

	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTickDelay(LevelReader levelReader)
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
	protected boolean isRandomlyTicking()
	{
		return true;
	} // end isRandomlyTicking()

	@Override
	protected void randomTick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource randomSource)
	{
		if(!fluidState.isSource()) 
		{
			return;
		}
		// TODO, temperature should be the one factoring in height and such things, that method is private though and deprecated, hmmmmm
		int temperatureScaledEvaporationSpeed = (int)(10f * (1f / level.getBiome(blockPos).get().getBaseTemperature()));
		if(randomSource.nextInt(temperatureScaledEvaporationSpeed) == 1)
		{
			level.setBlock(blockPos, YATMBlocks.RUBBER_BLOCK.get().defaultBlockState(), 3);
		}
	} // end randomTick()







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