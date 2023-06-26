package com.gsr.gsr_yatm.fluid;

import com.gsr.gsr_yatm.YATMBlocks;
import com.gsr.gsr_yatm.YATMFluidTypes;
import com.gsr.gsr_yatm.YATMFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

public abstract class LunarLightFluid extends FlowingFluid
{
	
	@Override
	public Fluid getSource()
	{
		return YATMFluids.LUNAR_LIGHT.get();
	} // end getSource()
	
	@Override
	public Fluid getFlowing()
	{
		return YATMFluids.LUNAR_LIGHT_FLOWING.get();
	} // end getFlowing()

	@Override
	protected BlockState createLegacyBlock(FluidState state)
	{
		return YATMBlocks.LUNAR_LIGHT_LIQUID_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
	} // end createLegacyBlock()

	@Override
	public FluidType getFluidType()
	{
		return YATMFluidTypes.LUNAR_LIGHT.get();
	} // end getFluidType()
	
	@Override
	public Item getBucket()
	{
		return Items.AIR;
	} // end getBucket()
	
		@Override
	public boolean isSame(Fluid fluid)
	{
		return fluid == YATMFluids.LUNAR_LIGHT.get() || fluid == YATMFluids.LUNAR_LIGHT_FLOWING.get();
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
		return 0;
	} // end getSlopeFindDistance()

	@Override
	protected int getDropOff(LevelReader levelReader)
	{
		return 8;
	} // end getDropOff()

	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction)
	{
		return false;
	} // end canBeReplacedWith()

	@Override
	public int getTickDelay(LevelReader levelReader)
	{
		return Integer.MAX_VALUE;
	} // end getTickDelay()

	@Override
	protected float getExplosionResistance()
	{
		return 1000f;
	} // end getExplosionResistance()
	




	// IMPLEMENTATIONS \\
	public static class Flowing extends LunarLightFluid
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

	public static class Source extends LunarLightFluid
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