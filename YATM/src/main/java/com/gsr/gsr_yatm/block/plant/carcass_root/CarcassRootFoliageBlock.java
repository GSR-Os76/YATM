package com.gsr.gsr_yatm.block.plant.carcass_root;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IBlockForPlacementByBlockSupplier;
import com.gsr.gsr_yatm.block.IHarvestableBlock;
import com.gsr.gsr_yatm.block.IOccasionallySpreadableBlock;
import com.gsr.gsr_yatm.block.ISpreadabilitySettableBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

// TODO, investigate IPlantable interface, and more avoiding extending CropBlock
public class CarcassRootFoliageBlock extends CropBlock implements IHarvestableBlock, IOccasionallySpreadableBlock, ISpreadabilitySettableBlock
{
	
	public static final int ROOTING_DEPTH = 3;
	public static final int ROOTING_RADIUS = 4;
	public static final int DOUBLES_PAST_THRESHOLD = 0;
	public static final EnumProperty<DoubleBlockHalf> HALF = YATMBlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_TWO;
	public static final BooleanProperty CAN_SPREAD = YATMBlockStateProperties.CAN_SPREAD;
	private final ICollisionVoxelShapeProvider m_shape;
	private final Supplier<ItemLike> m_seeds;
	private final Supplier<ItemStack> m_harvestResults;
	private final IBlockForPlacementByBlockSupplier m_roots;
	
	
	
	public CarcassRootFoliageBlock(Properties properties, ICollisionVoxelShapeProvider shape, Supplier<ItemLike> seeds, Supplier<ItemStack> harvestResults, IBlockForPlacementByBlockSupplier roots)
	{
		super(properties);
		
		this.registerDefaultState(this.defaultBlockState().setValue(CarcassRootFoliageBlock.HALF, DoubleBlockHalf.LOWER).setValue(CarcassRootFoliageBlock.CAN_SPREAD, true));
		
		this.m_shape = shape;
		this.m_seeds = seeds;
		this.m_harvestResults = harvestResults;
		this.m_roots = roots;
	} // end constructor


	
	@Override
	protected IntegerProperty getAgeProperty()
	{
		return CarcassRootFoliageBlock.AGE;
	} // end getAgeProperty()
	
	@Override
	public int getMaxAge()
	{
		return 1;
	} // end getMaxAge()

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(CarcassRootFoliageBlock.AGE, CarcassRootFoliageBlock.CAN_SPREAD, CarcassRootFoliageBlock.HALF);
	} // end createBlockStateDefinition()

	protected BlockState getStateForAge(BlockState state, int age) 
	{
		return state.setValue(CarcassRootFoliageBlock.AGE, age);
	} // end getStateForAge()
	
	
	
	@Override
	protected ItemLike getBaseSeedId()
	{
		return this.m_seeds.get();
	} // end getBaseSeedId()
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestableBlock.use(this, level, state, position, player, hand, hitResult);
	} // end use()
	
	

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int goingToAge = this.getAge(state) + 1;				
		if (goingToAge <= this.getMaxAge() && !this.isGrowthBlocked(level, state, position))
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(12) == 0))
			{
				BlockState baseState = this.getStateForAge(state, goingToAge);
				level.setBlock(position, baseState, Block.UPDATE_CLIENTS);				
				level.setBlock(position.above(), this.getStateForAge(goingToAge).setValue(CarcassRootFoliageBlock.HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
				this.sendOutRoots(level, baseState, position, random);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()

	
	
	

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos position, BlockState state)
	{
		// TODO Auto-generated method stub
		return super.isValidBonemealTarget(levelReader, position, state) && !this.isGrowthBlocked(levelReader, state, position);
	}

	@Override
	public void growCrops(Level level, BlockPos position, BlockState state)
	{
		int goingToAge = this.getAge(state) + 1;
		BlockState baseState = this.getStateForAge(state, goingToAge);
		level.setBlock(position, baseState, Block.UPDATE_CLIENTS);				
		level.setBlock(position.above(), this.getStateForAge(goingToAge).setValue(CarcassRootFoliageBlock.HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
		this.sendOutRoots((ServerLevel)level, baseState, position, level.random);
	} // end growCrops()



	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return state.is(YATMBlockTags.CARCASS_ROOT_CAN_GROW_ON_KEY);
	} // end mayPlaceOn()

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos position)
	{
		BlockPos check = position.below();
		BlockState below = level.getBlockState(check);
		return state.getValue(CarcassRootFoliageBlock.HALF) == DoubleBlockHalf.LOWER 
				? this.mayPlaceOn(below, level, check)
				: this.isTopSupport(below);
	} // end canSurvive()

	
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()
	
	
	
	protected boolean isTopSupport(BlockState state) 
	{
		return state.is(this) && state.getValue(CarcassRootFoliageBlock.HALF) == DoubleBlockHalf.LOWER && this.isPastDoubleBlockThreshold(state);
	} // end isTopSupport()
	
	protected boolean isPastDoubleBlockThreshold(BlockState state) 
	{
		return this.getAge(state) > DOUBLES_PAST_THRESHOLD;
	} // end isPastDoubleBlockThreshold()
	
	// TODO, note, somewhat reused code with AurumDeminutusBlock
	protected boolean isGrowthBlocked(LevelReader level, BlockState state, BlockPos position) 
	{
		int nextAgeUp = Math.min(state.getValue(this.getAgeProperty()) + 1, this.getMaxAge());
		BlockState abvState = level.getBlockState(position.above());
		return this.isPastDoubleBlockThreshold(state.setValue(this.getAgeProperty(), nextAgeUp)) && !(abvState.canBeReplaced() || abvState.is(this)); 
	} // end isGrowthBlocked()
	
	// TODO, possibly make this feature based
	protected void sendOutRoots(ServerLevel level, BlockState thissState, BlockPos position, RandomSource random)
	{
		if(level.isClientSide) 
		{
			return;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos().set(position.below());
		level.setBlock(pos, this.m_roots.get(level, thissState, position, level, pos), Block.UPDATE_ALL);
		for(int y = -2; y >= -CarcassRootFoliageBlock.ROOTING_DEPTH - 1; y--) 
		{
			pos.setWithOffset(position, CarcassRootFoliageBlock.ROOTING_RADIUS + 1, y, CarcassRootFoliageBlock.ROOTING_RADIUS + 1);
			for(int x = CarcassRootFoliageBlock.ROOTING_RADIUS; x >= -CarcassRootFoliageBlock.ROOTING_RADIUS; x--) 
			{
				pos.setWithOffset(position, x, y, CarcassRootFoliageBlock.ROOTING_RADIUS + 1);
				for(int z = CarcassRootFoliageBlock.ROOTING_RADIUS; z >= -CarcassRootFoliageBlock.ROOTING_RADIUS; z--) 
				{
					pos.move(0, 0, -1);
					this.maybePlaceRoot(level, thissState, position, pos, random);
				}
			}
		}
	} // end sendOutRoots()
	
	protected void maybePlaceRoot(ServerLevel level, BlockState placerState, BlockPos placerPosition, BlockPos placingPosition, RandomSource random) 
	{
		if(!level.isClientSide && random.nextInt(2) == 0) 
		{
			BlockState state = this.m_roots.get(level, placerState, placerPosition, level, placingPosition);
			if(state != null && !Direction.stream().anyMatch((d) -> level.getBlockState(placingPosition.relative(d)).canBeReplaced())) 
			{					
				level.setBlock(placingPosition, state, Block.UPDATE_ALL);
			}
		}
	} // end maybePlaceRoot()
	
	

	@Override
	public boolean allowEventHandling()
	{
		return false;
	} // end allowEventHandling()
	



	@Override
	public void onHarvest(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(state.getValue(CarcassRootFoliageBlock.HALF) == DoubleBlockHalf.LOWER)
		{
			level.setBlock(position.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		}
		else 
		{
			level.setBlock(position.below(), this.defaultBlockState(), Block.UPDATE_ALL);
		}
	} // end onHarvest()
	



	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		return this.getAge(state) > 0 ? List.of(ToolActions.SHEARS_HARVEST) : List.of();
	} // end validActions()
	
	@Override
	public boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return this.getAge(state) > 0 && this.validActions(level, state, position).contains(action);
	} // end isHarvestable()

	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(this.isHarvestable(level, state, position, action))
		{
			return state.getValue(CarcassRootFoliageBlock.HALF) == DoubleBlockHalf.LOWER ? state.setValue(this.getAgeProperty(), 0) : Blocks.AIR.defaultBlockState();
		}
		return null;
	} // end getResultingState()

	@Override
	public @Nullable NonNullList<ItemStack> getResults(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(this.isHarvestable(level, state, position, action))
		{
			return NonNullList.of(ItemStack.EMPTY, this.m_harvestResults.get());
		}
		return null;
	} // end getResult()



	@Override
	public boolean canSpread(Level level, BlockState state, BlockPos pos)
	{
		return state.getValue(CarcassRootFoliageBlock.CAN_SPREAD);
	} // end canSpread

	@Override
	public @NotNull BlockState setSpreadability(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, boolean isSpreadable)
	{
		return state.setValue(CarcassRootFoliageBlock.CAN_SPREAD, isSpreadable);
	} // end setSpreadability()
	
} // end class