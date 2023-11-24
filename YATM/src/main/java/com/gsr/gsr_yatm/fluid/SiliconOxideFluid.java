package com.gsr.gsr_yatm.fluid;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMFluidTypes;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidType;

public abstract class SiliconOxideFluid extends FlowingFluid
{
	
	@Override
	public Fluid getSource()
	{
		return YATMFluids.SILICON_OXIDE.get();
	} // end getSource()
	
	@Override
	public Fluid getFlowing()
	{
		return YATMFluids.SILICON_OXIDE_FLOWING.get();
	} // end getFlowing()

	@Override
	protected BlockState createLegacyBlock(@NotNull FluidState state)
	{
		return YATMBlocks.SILICON_OXIDE_LIQUID_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
	} // end createLegacyBlock()

	@Override
	public FluidType getFluidType()
	{
		return YATMFluidTypes.SILICON_OXIDE.get();
	} // end getFluidType()
	
	@Override
	public Item getBucket()
	{
		return YATMItems.SILICON_OXIDE_BUCKET.get();
	} // end getBucket()
	
	@Override
	public boolean isSame(Fluid fluid)
	{
		return fluid == YATMFluids.SILICON_OXIDE.get() || fluid == YATMFluids.SILICON_OXIDE_FLOWING.get();
	} // end isSame()

	
	// TODO, add the burning of surroundings and such
	
	
	@Override
	protected boolean canConvertToSource(@NotNull Level level)
	{
		return false;
	} // end canConvertToSource

	@Override
	protected void beforeDestroyingBlock(@NotNull LevelAccessor level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		
	} // end beforeDestroyingBlock()

	Fluids l;
	
	@Override
	protected int getSlopeFindDistance(@NotNull LevelReader level)
	{
		return level.dimensionType().ultraWarm() ? 4 : 2;
	} // end getSlopeFindDistance()

	@Override
	protected int getDropOff(@NotNull LevelReader level)
	{
		return level.dimensionType().ultraWarm() ? 1 : 2;
	} // end getDropOff()

	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter level, BlockPos position, Fluid fluid, Direction direction)
	{
		return false;
	} // end canBeReplacedWith()

	@Override
	public int getTickDelay(@NotNull LevelReader level)
	{
		return level.dimensionType().ultraWarm() ? 20 : 60;
	} // end getTickDelay()

	@Override
	protected float getExplosionResistance()
	{
		return 100f;
	} // end getExplosionResistance()
	
	


	// IMPLEMENTATIONS \\
	public static class Flowing extends SiliconOxideFluid
	{
		protected void createFluidStateDefinition(@NotNull StateDefinition.Builder<Fluid, FluidState> builder)
		{
			super.createFluidStateDefinition(builder.add(Flowing.LEVEL));
		} // end createFluidStateDefinition()

		public int getAmount(@NotNull FluidState state)
		{
			return state.getValue(Flowing.LEVEL);
		} // end getAmount()

		public boolean isSource(@NotNull FluidState state)
		{
			return false;
		} // end isSource()

	} // end flowing class

	public static class Source extends SiliconOxideFluid
	{
		public int getAmount(@NotNull FluidState state)
		{
			return 8;
		} // end getAmount

		public boolean isSource(@NotNull FluidState state)
		{
			return true;
		} // end isSource()

	} // end source class
} // end class