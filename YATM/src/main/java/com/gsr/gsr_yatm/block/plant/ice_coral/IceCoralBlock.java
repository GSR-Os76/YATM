package com.gsr.gsr_yatm.block.plant.ice_coral;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class IceCoralBlock extends Block implements IAgingBlock, IYATMPlantableBlock, SimpleWaterloggedBlock, BonemealableBlock
{
	public static final @NotNull IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final @NotNull BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	private static final int DIE_WITHOUT_WATER_RARITY = 12;
	private static final int GROWTH_RARITY = 32;
	
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	
	public IceCoralBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		
		this.registerDefaultState(this.defaultBlockState().setValue(IceCoralBlock.AGE, 0).setValue(IceCoralBlock.WATERLOGGED, false));
		
		this.m_shape = Objects.requireNonNull(shape);
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return IceCoralBlock.AGE;
	} // end getAgeProperty()



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(IceCoralBlock.AGE, IceCoralBlock.WATERLOGGED));
	} // end createBlockStateDefinition()

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return super.getStateForPlacement(context).setValue(IceCoralBlock.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	} // end getStateForPlacement()

	@SuppressWarnings("deprecation")
	public @NotNull BlockState updateShape(@NotNull BlockState state, Direction direction, BlockState stateSecond, @NotNull LevelAccessor level, @NotNull BlockPos position, BlockPos positionSecond)
	{
		if (state.getValue(IceCoralBlock.WATERLOGGED))
		{
			level.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, stateSecond, level, position, positionSecond);
	} // end updateShape()

	@SuppressWarnings("deprecation")
	public @NotNull FluidState getFluidState(@NotNull BlockState state)
	{
		return state.getValue(IceCoralBlock.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	} // end getFluidState



	
	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.ICE_CORAL_CAN_GROW_ON_KEY);
	} // end canSurvive()
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Block formerNeighbor, @NotNull BlockPos neighborPos, boolean p_60514_)
	{
		if(!this.canSurvive(state, level, position)) 
		{
			BlockUtilities.breakBlock(level, state, position);
		}
		super.neighborChanged(state, level, position, formerNeighbor, neighborPos, p_60514_);
	} // end neighborChanged()

	@Override
	public boolean canPlantOn(@NotNull LevelReader level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Direction face)
	{
		BlockPos above = position.above();
		return face == Direction.UP 
				&& this.canSurvive(level.getBlockState(above), level, above)
				&& level.getBlockState(position.above()).is(YATMBlockTags.ICE_CORAL_CAN_GROW_IN_KEY);
	} // end canPlantOn()

	
	
	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if(!state.getValue(IceCoralBlock.WATERLOGGED)) 
		{
			if(random.nextInt(IceCoralBlock.DIE_WITHOUT_WATER_RARITY) == 0) 
			{
				BlockUtilities.breakBlockNoDrops(level, state, position);
			}
		}
		else 
		{
			int age = this.getAge(state);
			if (age < this.getMaxAge())
			{
				if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(IceCoralBlock.GROWTH_RARITY) == 0))
				{
					level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
					ForgeHooks.onCropsGrowPost(level, position, state);
				}
			}
		}
	} // end randomTick
	
	@Override
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos position, @NotNull CollisionContext context)
	{
		return this.m_shape.getShape(state, blockGetter, position, context);
	} // end getShape()



	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state, boolean p_50900_)
	{
		return true;
	} // end isValidBonemealTarget()

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return true;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos().set(position);
		for(int x = -5; x <= 5; x++) 
		{
			for(int z = -5; z <= 5; z++) 
			{
				for(int y = -5; y <= 5; y++) 
				{								
					mPos.setWithOffset(position, x, y, z);
					BlockState at = level.getBlockState(mPos);
					if(at.is(this) && (random.nextInt(((int) (Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2) + 1) == 0) || mPos.equals(position))
					{
						BlockState bleachTo = (switch(at.getValue(IceCoralBlock.AGE)) 
						{
							case 0, 1 -> YATMBlocks.BLEACHED_ICE_CORAL_POLYP.get();
							case 2, 3 -> YATMBlocks.BLEACHED_ICE_CORAL_YOUNG.get();
							case 4, 5, 6 -> YATMBlocks.BLEACHED_ICE_CORAL_ADOLESCENT.get();
							case 7 -> YATMBlocks.BLEACHED_ICE_CORAL_OLD.get();
							default -> throw new IllegalArgumentException("Unexpected value of: " + at.getValue(IceCoralBlock.AGE));
							
						}).defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, at.getFluidState().getType() == Fluids.WATER);
						level.setBlock(mPos, bleachTo, Block.UPDATE_CLIENTS);
					}
				} // end y loop
			} // end z loop
		} // end x loop
	} // end performBonemeal()
	
} // end class