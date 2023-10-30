package com.gsr.gsr_yatm.fluid;

import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMFluidTypes;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.material.LavaFluid;
import net.minecraftforge.fluids.FluidType;

public abstract class SoulSyrupFluid extends FlowingFluid implements IBottleableFluid
{
	// TODO, figure out how to set that up close fog effect lava and water have when you're submerged in them.
	// TODO, figure out how to give constant soul affliction to entities which're in content.
	// TODO, maybe add randomly soul afflicting nearby entities,
	@Override
	public Fluid getSource()
	{
		return YATMFluids.SOUL_SYRUP.get();
	} // end getSource()
	
	@Override
	public Fluid getFlowing()
	{
		return YATMFluids.SOUL_SYRUP_FLOWING.get();
	} // end getFlowing()

	@Override
	protected BlockState createLegacyBlock(FluidState state)
	{
		return YATMBlocks.SOUL_SYRUP_LIQUID_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
	} // end createLegacyBlock()

	@Override
	public FluidType getFluidType()
	{
		return YATMFluidTypes.SOUL_SYRUP.get();
	} // end getFluidType()

	@Override
	public Item getBucket()
	{
		return YATMItems.SOUL_SYRUP_BUCKET.get();
	} // end getBucket()
	
	@Override
	public Item getBottle()
	{
		return YATMItems.SOUL_SYRUP_BOTTLE.get();
	} // end getBottle()

	@Override
	public boolean isSame(Fluid fluid)
	{
		return fluid == YATMFluids.SOUL_SYRUP.get() || fluid == YATMFluids.SOUL_SYRUP_FLOWING.get();
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
		//TODO, could make spread further in soul things related biomes, however without a position arguement that's hard to approach
		return 3;
	} // end getDropOff()

	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction)
	{
		return false;
	} // end canBeReplacedWith()

	@Override
	public int getTickDelay(LevelReader levelReader)
	{
		return 32;
	} // end getTickDelay()

	@Override
	protected float getExplosionResistance()
	{
		// TODO, learn explosion resistance
		return 20f;
	} // end getExplosionResistance()



	@Override
	protected void animateTick(Level level, BlockPos blockPos, FluidState fluidState, RandomSource randomSource)
	{
		BlockState above = level.getBlockState(blockPos.above());
		if((above.isAir() || above.isSolidRender(level, blockPos)) && randomSource.nextInt(60) == 0) 
		{
			double atX = (double)blockPos.getX() + randomSource.nextDouble();
			double atY = (double)blockPos.getY() + 1d;
			double atZ = (double)blockPos.getZ() + randomSource.nextDouble();
			level.addParticle(ParticleTypes.SOUL, 
					atX, atY, atZ, 
					(double)((randomSource.nextFloat() / 12F) * (randomSource.nextIntBetweenInclusive(0, 1) == 0 ? -1 : 1)), (double)(randomSource.nextFloat() / 12F)* (randomSource.nextIntBetweenInclusive(0, 1) == 0 ? -1 : 1), (double)(randomSource.nextFloat() / 12F)* (randomSource.nextIntBetweenInclusive(0, 1) == 0 ? -1 : 1));
			
			level.playLocalSound(atX, atY, atZ, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS
					// TODO, figure out better what these floats represent, first's seemingly volume, second's a mystery to me
					, 12f * randomSource.nextFloat(), 1f					
					, false);
		}
	} // end animateTick()






	// IMPLEMENTATIONS \\
	public static class Flowing extends SoulSyrupFluid
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

	
	public static class Source extends SoulSyrupFluid
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