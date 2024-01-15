package com.gsr.gsr_yatm.block.plant.tree;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.IDripFillableBlock;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.util.TriPredicate;

public class TappedLogBlock extends RotatedPillarBlock
{
	public static final BooleanProperty FLOWING = YATMBlockStateProperties.FLOWING;
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_NOT_UP;
	
	public static final int MAX_DRIP_DISTANCE = 24;
	
	private final @NotNull Supplier<ParticleOptions> m_dripParticle;
	private final @NotNull Supplier<Fluid> m_fluid;
	private final @NotNull TriPredicate<Level, BlockState, BlockPos> m_sapProviderTest;
	
	
	
	
	public TappedLogBlock(@NotNull Properties properties, @NotNull Supplier<Fluid> fluid, @NotNull TriPredicate<Level, BlockState, BlockPos> sapProviderTest, @NotNull Supplier<ParticleOptions> dripParticle)
	{
		super(Objects.requireNonNull(properties));
		this.registerDefaultState(this.defaultBlockState().setValue(FLOWING, true).setValue(FACING, Direction.NORTH));
		
		this.m_dripParticle = Objects.requireNonNull(dripParticle);
		this.m_fluid = Objects.requireNonNull(fluid);
		this.m_sapProviderTest = Objects.requireNonNull(sapProviderTest);
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(TappedLogBlock.FLOWING, TappedLogBlock.FACING));
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Direction facing = context.getClickedFace().getAxis() == Axis.Y ? context.getHorizontalDirection().getOpposite() : Direction.DOWN;
		return super.getStateForPlacement(context).setValue(TappedLogBlock.FACING, facing);		
	} // end getStateForPlacement()



	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		boolean shouldFlow = false;
		if (Direction.stream().map((d) -> position.relative(d)).anyMatch((p) -> level.isLoaded(p) && this.m_sapProviderTest.test(level, level.getBlockState(p), p)))
		{
			shouldFlow = true;
		}
		else
		{
			int matchCount = 0;
			BlockPos.MutableBlockPos toTestPos = new BlockPos.MutableBlockPos();
			for(int i = 0; i < 24; i++) 
			{
				toTestPos.setWithOffset(position, 
						random.nextIntBetweenInclusive(-2, 2), 
						random.nextIntBetweenInclusive(-2, 2), 
						random.nextIntBetweenInclusive(-2, 2));
				if(level.isLoaded(toTestPos))
				{
					BlockState toTestState = level.getBlockState(toTestPos);
					if(this.m_sapProviderTest.test(level, toTestState, toTestPos)) 
					{
						++matchCount;
					}
				}
				if(matchCount >= 6) 
				{
					shouldFlow = true;
					break;
				}
			}
		}

		if (shouldFlow != state.getValue(TappedLogBlock.FLOWING))
		{
			level.setBlock(position, state.setValue(TappedLogBlock.FLOWING, shouldFlow), Block.UPDATE_CLIENTS);
		}
		
		if(shouldFlow && random.nextInt(24) == 0) 
		{
			BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos().set(position.relative(state.getValue(TappedLogBlock.FACING)));
			for(int i = 1; i <= TappedLogBlock.MAX_DRIP_DISTANCE; i++) 
			{
				mbp.move(Direction.DOWN);
				BlockState toCheckState = level.getBlockState(mbp);
				if(toCheckState.getBlock() instanceof IDripFillableBlock df) 
				{
					if(df.canRecieveFluid(this.m_fluid.get()))
					{
						df.recieveFluid(level, toCheckState, mbp, this.m_fluid.get());
					}					
				}
			}
		}
	} // end randomTick()



	@Override
	public void animateTick(BlockState state, Level level, BlockPos position, RandomSource random)
	{
		if(state.getValue(TappedLogBlock.FLOWING) && level.random.nextInt(48) == 0) 
		{
			// TODO, positioning needs some conditional tuning, possibly extract into a method all that, return them packaged into some sort of vector tuple kinda thing
			Direction facing = state.getValue(TappedLogBlock.FACING);
			double x = ((double)position.relative(facing).getX());
			double y = ((double)position.relative(facing).getY());
			double z = ((double)position.relative(facing).getZ());
			level.addParticle(this.m_dripParticle.get(), x, y, z, 0.0d, 0.0d, 0.0d);
		}
	} // end animateTick()
	
} // end class