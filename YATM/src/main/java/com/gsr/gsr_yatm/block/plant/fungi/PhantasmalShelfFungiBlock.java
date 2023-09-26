// fungi aren't plant's in a taxonomical sense, i just sort them here for convenience and organization, a raname would be apt
package com.gsr.gsr_yatm.block.plant.fungi;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;

import com.gsr.gsr_yatm.block.IHarvestableBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.itemstack.RandomCountItemStackSupplier;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class PhantasmalShelfFungiBlock extends CropBlock implements IHarvestableBlock
{
	private static final int MIN_SPREAD_ATTEMPT_COUNT = 12;
	private static final int MAX_SPREAD_ATTEMPT_COUNT = 24;
	
	private static final int HORIZONTAL_SPREAD_RANGE = 5;
	private static final int VERTICAL_SPREAD_RANGE = 1;

	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	private final ICollisionVoxelShapeProvider m_shape;
	private final Supplier<ItemLike> m_seed;
	/*TODO, probably unhardcode this*/private final Supplier<ItemStack> m_harvestResults = new RandomCountItemStackSupplier(() -> Items.PHANTOM_MEMBRANE, 0, 3, RandomSource.createNewThreadLocalInstance());
	

	
	public PhantasmalShelfFungiBlock(Properties properties, ICollisionVoxelShapeProvider shape, Supplier<ItemLike> seed)
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(PhantasmalShelfFungiBlock.FACING, Direction.NORTH));
		this.m_shape = shape;
		this.m_seed = seed;
	} // end constructor



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(PhantasmalShelfFungiBlock.FACING));
	} // end createBlockStateDefinition()
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		Direction face = context.getClickedFace();
		BlockState state = level.getBlockState(position.relative(face.getOpposite()));

		return Direction.Plane.VERTICAL.test(face) ? null : this.canPlaceOn(level, position, state, face) ? super.getStateForPlacement(context).setValue(PhantasmalShelfFungiBlock.FACING, face) : null;
	} // end getStateForPlacement()

	

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		InteractionResult hrvRes = IHarvestableBlock.use(this, level, state, position, player, hand, hitResult);
		if(hrvRes != InteractionResult.PASS) 
		{
			return hrvRes;
		}
		
		ItemStack usdItm = player.getItemInHand(hand);
		if (usdItm.is(YATMItemTags.SPREADS_PHANTASMAL_SHELF_FUNGI_KEY) && this.isFullyGrown(state))
		{
			if (!level.isClientSide)
			{
				this.spread((ServerLevel) level, state, position, level.random);
				if(!player.getAbilities().instabuild) 
				{
					usdItm.shrink(1);			
				}
			}
			return InteractionResult.sidedSuccess(!level.isClientSide);
		}
		if (usdItm.is(YATMItemTags.GROWS_PHANTASMAL_SHELF_FUNGI_KEY) && !this.isFullyGrown(state))
		{
			if (!level.isClientSide)
			{
				this.grow((ServerLevel) level, state, position);if(!player.getAbilities().instabuild) 
				{
					usdItm.shrink(1);
				}
			}
			return InteractionResult.sidedSuccess(!level.isClientSide);
		}
		return super.use(state, level, position, player, hand, hitResult);
	} // end use()

		
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int i = this.getAge(state);
		if (i < this.getMaxAge())
		{
			float f = getGrowthSpeed(this, level, position);
			if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt((int) (25.0F / f) + 1) == 0))
			{
				level.setBlock(position, state.setValue(this.getAgeProperty(), i + 1), 2);
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()
	
	
	



	@Override
	protected ItemLike getBaseSeedId()
	{
		return this.m_seed.get();
	} // end getBaseSeedId()
	
	@Override
	public boolean isValidBonemealTarget(LevelReader p_255715_, BlockPos p_52259_, BlockState p_52260_, boolean p_52261_)
	{
		return false;
	} // end isValidBonemealTarget()

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos position)
	{
		return this.canPlaceOn(getter, position, state, Direction.DOWN);
	} // end mayPlaceOn()

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos position)
	{
		Direction facing = state.getValue(PhantasmalShelfFungiBlock.FACING);
		BlockPos checkingPos = position.relative(facing.getOpposite());
		return Block.isFaceFull(level.getBlockState(checkingPos).getBlockSupportShape(level, checkingPos), facing);
	} // end canSurvive()

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()



	public void grow(ServerLevel level, BlockState state, BlockPos position) 
	{
		int ageIncd = state.getValue(this.getAgeProperty()) + 1;
		level.setBlock(position, state.setValue(this.getAgeProperty(), Math.min(ageIncd, this.getMaxAge())), Block.UPDATE_CLIENTS);
	} // end grow()
	
	public void spread(ServerLevel level, BlockState state, BlockPos position, RandomSource random) 
	{
		int spreaded = 0;
		for(int a = 0; a < random.nextIntBetweenInclusive(MIN_SPREAD_ATTEMPT_COUNT, MAX_SPREAD_ATTEMPT_COUNT); a++) 
		{
			BlockPos toCheck = position.offset(random.nextIntBetweenInclusive(-HORIZONTAL_SPREAD_RANGE, HORIZONTAL_SPREAD_RANGE), random.nextIntBetweenInclusive(-VERTICAL_SPREAD_RANGE, VERTICAL_SPREAD_RANGE), random.nextIntBetweenInclusive(-HORIZONTAL_SPREAD_RANGE, HORIZONTAL_SPREAD_RANGE));
			if(level.isLoaded(toCheck)) 
			{
				BlockState checking = level.getBlockState(toCheck);
				if(checking.is(YATMBlockTags.PHANTASMAL_SHELF_FUNGI_SPREAD_TO_KEY)) 
				{
					for(Direction face : Direction.Plane.HORIZONTAL.shuffledCopy(random)) 
					{
						if(level.getBlockState(toCheck.relative(face)).isAir() && Block.isFaceFull(checking.getBlockSupportShape(level, toCheck), face)) 
						{
							level.setBlock(toCheck.relative(face), this.defaultBlockState().setValue(FACING, face), 3);
							spreaded++;
						}
						if(spreaded > 3) 
						{
							return;
						}
					}
				}
			}
		}
	} // end spread()
	

	
	private boolean canPlaceOn(BlockGetter getter, BlockPos onBlock, BlockState onState, Direction cansface) 
	{
		return Block.isFaceFull(onState.getBlockSupportShape(getter, onBlock), cansface);
	} // end canPlaceOn()

	private boolean isFullyGrown(BlockState state) 
	{
		return state.getValue(this.getAgeProperty()) == this.getMaxAge();
	} // end isFullyGrow()

	
	
	@Override
	public boolean allowEventHandling()
	{
		return false;
	} // end allowsEventHandling()
	


	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		return this.isFullyGrown(state) ? List.of(ToolActions.SHEARS_HARVEST) : List.of();
	} // end validActions()

	@Override
	public boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return this.isFullyGrown(state) && this.validActions(level, state, position).contains(action);
	} // end isHarvestAble()

	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(this.isHarvestable(level, state, position, action)) 
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
			return NonNullList.of(ItemStack.EMPTY, this.m_harvestResults.get());
		}
		return null;
	} // end getResults()
	
} // end class