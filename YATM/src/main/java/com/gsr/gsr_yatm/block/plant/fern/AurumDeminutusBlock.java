package com.gsr.gsr_yatm.block.plant.fern;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.block.IHarvestable;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.VoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

// TODO, create by using bottled soul essence on a block of gilded blackstone with a gold block on top it, doesn't not naturally reproduce
// TODO, fix lower not break when upper's broken
// TODO, make damaging contacting entities work
public class AurumDeminutusBlock extends CropBlock implements IHarvestable
{
	public static final int DOUBLES_PAST_THRESHOLD = 2;
	public static final EnumProperty<DoubleBlockHalf> HALF = YATMBlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_FIVE;
	private final VoxelShapeProvider m_shape;
	private final Supplier<ItemLike> m_seed;
	private final Supplier<ItemStack> m_harvestResult;
	
	
	
	public AurumDeminutusBlock(Properties properties, VoxelShapeProvider shape, Supplier<ItemLike> seed, Supplier<ItemStack> harvestResult)
	{
		super(properties);
		
		this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
		
		this.m_shape = shape;
		this.m_seed = seed;
		this.m_harvestResult = harvestResult;
	} // end constructor

	
	
	@Override
	protected IntegerProperty getAgeProperty()
	{
		return AurumDeminutusBlock.AGE;
	} // end getAgeProperty()

	@Override
	public int getMaxAge()
	{
		return 4;
	} // end getMaxAge()

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(AurumDeminutusBlock.AGE, AurumDeminutusBlock.HALF);
	} // end createBlockStateDefinition()






	@Override
	protected ItemLike getBaseSeedId()
	{
		return this.m_seed.get();
	} // end getBaseSeedId()
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		double vector = entity.getDeltaMovement().distanceTo(Vec3.ZERO);
		if(vector > 0.1d) 
		{
			entity.hurt(level.damageSources().thorns((Entity)null), (((float)vector) * 4.0f));
		}
	} // end entityInside()
	
	

	@Override
	public boolean isRandomlyTicking(BlockState state)
	{
		return super.isRandomlyTicking(state) && state.getValue(HALF) == DoubleBlockHalf.LOWER;
	} // end isRandomlyTicking()

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int goingToAge = this.getAge(state) + 1;				
		if (goingToAge <= this.getMaxAge() && !this.isGrowthBlocked(level, state, position))
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(36) == 0))
			{
				level.setBlock(position, this.getStateForAge(goingToAge), 2);
				if(goingToAge > DOUBLES_PAST_THRESHOLD) 
				{
					BlockState above = level.getBlockState(position.above());
					if(above.is(this)) 
					{
						level.setBlock(position.above(), above.setValue(this.getAgeProperty(), goingToAge).setValue(AurumDeminutusBlock.HALF, DoubleBlockHalf.UPPER), 2);
					}
					else
					{
						level.setBlock(position.above(), this.getStateForAge(goingToAge).setValue(AurumDeminutusBlock.HALF, DoubleBlockHalf.UPPER), 3);
					}
				}
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()



	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return state.is(YATMBlockTags.AURUM_GROWS_ON);
	} // end mayPlaceOn()

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos position)
	{
		BlockPos check = position.below();
		BlockState below = level.getBlockState(check);
		return state.getValue(AurumDeminutusBlock.HALF) == DoubleBlockHalf.LOWER 
				? this.mayPlaceOn(below, level, check)
				: this.isTopSupport(below);
	} // end canSurvive()

	@Override
	public boolean isValidBonemealTarget(LevelReader p_255715_, BlockPos p_52259_, BlockState p_52260_, boolean p_52261_)
	{
		return false;
	} // end isValidBonemealTarget()



	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()

	// should only be called with lower state
	protected void setToYoungest(Level level, BlockState state, BlockPos position) 
	{
		if(level.isClientSide) 
		{
			return;
		}
		
		BlockPos above = position.above();
		if(level.getBlockState(above).is(this)) 
		{
			level.setBlock(above, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		}
		level.setBlock(position, state.setValue(AurumDeminutusBlock.HALF, DoubleBlockHalf.LOWER).setValue(this.getAgeProperty(), 0), Block.UPDATE_CLIENTS);
	} // end setToYoungest
	
	protected boolean isTopSupport(BlockState state) 
	{
		return state.is(this) && state.getValue(AurumDeminutusBlock.HALF) == DoubleBlockHalf.LOWER && this.isPastDoubleBlockThreshold(state);
	} // end isTopSupport()
	
	protected boolean isPastDoubleBlockThreshold(BlockState state) 
	{
		return state.getValue(this.getAgeProperty()) > DOUBLES_PAST_THRESHOLD;
	} // end isPastDoubleBlockThreshold()
	
	protected boolean isGrowthBlocked(Level level, BlockState state, BlockPos position) 
	{
		int nextAgeUp = Math.min(state.getValue(this.getAgeProperty()) + 1, this.getMaxAge());
		BlockState abvState = level.getBlockState(position.above());
		return this.isPastDoubleBlockThreshold(state.setValue(this.getAgeProperty(), nextAgeUp)) && !(abvState.canBeReplaced() || abvState.is(this)); 
	} // end isGrowthBlocked()
	

	
	@Override
 	public void onHarvest(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(!level.isClientSide) 
		{
			BlockPos positionOfBottom = state.getValue(AurumDeminutusBlock.HALF) == DoubleBlockHalf.UPPER ? position.below() : position;
			BlockState bottomPart = state.getValue(AurumDeminutusBlock.HALF) == DoubleBlockHalf.UPPER ? level.getBlockState(positionOfBottom) : state;
	
			this.setToYoungest(level, bottomPart, positionOfBottom);
		}
	} // end onHarvest

	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		return state.getValue(this.getAgeProperty()) > 0 ? List.of(ToolActions.PICKAXE_DIG) : List.of();
	} // end validActions()

	@Override
	public boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return state.getValue(this.getAgeProperty()) > 0;
	} // end isHarvestable()

	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if (this.isHarvestable(level, state, position, action))
		{
			return state.setValue(this.getAgeProperty(), 0);
		}
		return null;
	} // end getResultingState()

	@Override
	public @Nullable NonNullList<ItemStack> getResults(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(this.isHarvestable(level, state, position, action)) 
		{
			ItemStack res = this.m_harvestResult.get();
			res.grow(this.getBonusFrondsPerAge(state));
			return NonNullList.of(ItemStack.EMPTY, res);
		}
		return null;
	} // end getResults()
	
	private int getBonusFrondsPerAge(BlockState state) 
	{
		return switch(state.getValue(this.getAgeProperty())) 
		{
			case 0 -> 0;
			case 1 -> 0;
			case 2 -> 1;
			case 3 -> 3;
			case 4 -> 5;
			default -> throw new IllegalArgumentException("Unexpected value of: " + state.getValue(this.getAgeProperty()));
		};
	} // end getBonusFrondsPerAge()
	
} // end class