package com.gsr.gsr_yatm.block.plant.parasite;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IHarvestable;
import com.gsr.gsr_yatm.block.plant.CustomSeedCropBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.VoxelShapeProvider;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ShulkwartBlock extends CustomSeedCropBlock implements IHarvestable
{
	private static final int MAX_SPORE_FALL_DISTANCE = 16;
	private static final int SPORE_DISPERSION_DISTANCE = 8;
	private final VoxelShapeProvider m_shape;
	private final Supplier<Block> m_fallenSpores;
	/*TODO, probably unhardcode this, make into a data driven loottable if is possible*/private final Supplier<ItemStack> m_harvestResults;
	
	
	public ShulkwartBlock(Properties properties, VoxelShapeProvider shape, Supplier<ItemLike> seed, Supplier<Block> fallenSpores, Supplier<ItemStack> harvestResults)
	{
		super(properties, seed);
		this.m_shape = shape;
		this.m_fallenSpores = fallenSpores;
		this.m_harvestResults = harvestResults;
	} // end constructor



	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestable.use(this, level, state, position, player, hand, hitResult);
	} // end use()

	
	
	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return YATMBlockTags.SHULKWART_GROWS_ON.contains(state.getBlock());
	} // end mayPlaceOn()

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos position)
	{
		BlockPos check = position.above();
		return this.mayPlaceOn(level.getBlockState(check), level, check);
	} // end canSurvive()

	

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		// TODO, i'm assuming here I don't need to check if anything is loaded since this is all contained in the one position, and it should be up to the called to verify loadedness of chunks
		int i = this.getAge(state);
		if (i < this.getMaxAge())
		{
			float f = (3.0f * ((float)state.getValue(this.getAgeProperty()))) + 1.0f;
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt((int) (25.0F / f) + 1) == 0))
			{
				int goingToAge = i + 1;
				if(goingToAge == this.getMaxAge()) 
				{
					this.tryDropSpores(level, position, random);
				}
				level.setBlock(position, this.getStateForAge(goingToAge), 2);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()

	private void tryDropSpores(Level level, BlockPos from, RandomSource random) 
	{
		if(level.isClientSide) 
		{
			return;
		}
		for(int i = 1; i <= ShulkwartBlock.MAX_SPORE_FALL_DISTANCE; i++) 
		{
			BlockPos checking = from.below(i);
			BlockState bs = level.getBlockState(checking);
			if(!bs.is(Blocks.AIR)) 
			{
				if(i > 1 && Block.isFaceFull(bs.getBlockSupportShape(level, checking), Direction.UP)) 
				{
					level.setBlock(checking.above(), this.m_fallenSpores.get().defaultBlockState(), 3);
				}
				int dispersionLevel = i / ShulkwartBlock.SPORE_DISPERSION_DISTANCE;
				if(dispersionLevel > 0) 
				{
					for(int j = 1; j <= dispersionLevel; j++) 
					{
						for(int k = 0; k <= random.nextInt(4); k++) 
						{
							BlockPos dpChkClm = from.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random), j); 
							if(level.isLoaded(dpChkClm))
							{
								for(int l = 1; l <= Math.min(i + 3, ShulkwartBlock.MAX_SPORE_FALL_DISTANCE); l++) 
								{
									BlockPos dpChk = dpChkClm.below(l); 
									BlockState dpbs = level.getBlockState(dpChk);	
									if(!dpbs.is(Blocks.AIR)) 
									{
										if(((l / ShulkwartBlock.SPORE_DISPERSION_DISTANCE) >= j) && Block.isFaceFull(dpbs.getBlockSupportShape(level, dpChk), Direction.UP)) 
										{
											level.setBlock(dpChk.above(), this.m_fallenSpores.get().defaultBlockState(), 3);
										}
										break;
									}
								}
							}
						}
					}
				} // end dispersion
				return;
			}
		}
	} // end dropSpored()
	
	
	
	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos position, BlockState state, boolean p_52261_)
	{
		return false;
	} // end isValidBonemealTarget()
	
	@Override
	public void growCrops(Level level, BlockPos position, BlockState state)
	{
		int maxAge = this.getMaxAge();
		int targetAge = Math.min(maxAge, this.getAge(state) + this.getBonemealAgeIncrease(level));
		if (targetAge == maxAge)
		{
			this.tryDropSpores(level, position, level.getRandom());
		}

		level.setBlock(position, this.getStateForAge(targetAge), 2);
	} // end growCrops()

	
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()



	@Override
	public boolean allowEventHandling()
	{
		return false;
	} // end allowsEventHandling()
	


	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		return state.getValue(this.getAgeProperty()) == this.getMaxAge() ? List.of(ToolActions.SHEARS_HARVEST) : List.of();
	} // end validAction()

	@Override
	public boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return (state.getValue(this.getAgeProperty()) == this.getMaxAge()) && action != null && action == ToolActions.SHEARS_HARVEST;
	} // end isHarvestable()

	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(this.isHarvestable(level, state, position, action)) 
		{
			return state.setValue(this.getAgeProperty(), 1);
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