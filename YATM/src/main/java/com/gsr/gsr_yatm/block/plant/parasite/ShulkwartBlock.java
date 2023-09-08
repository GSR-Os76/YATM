package com.gsr.gsr_yatm.block.plant.parasite;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IAging;
import com.gsr.gsr_yatm.block.IHarvestable;
import com.gsr.gsr_yatm.block.IYATMPlantable;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ShulkwartBlock extends Block implements IAging, IHarvestable, IYATMPlantable
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	private static final int MAX_SPORE_FALL_DISTANCE = 16;
	private static final int SPORE_DISPERSION_DISTANCE = 8;
	
	private final Supplier<Block> m_fallenSpores;
	/*TODO, probably unhardcode this, make into a data driven loottable if is possible*/
	private final Supplier<ItemStack> m_harvestResults;
	private final Supplier<Item> m_seed;
	private final ICollisionVoxelShapeProvider m_shape;
	
	
	
	public ShulkwartBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull Supplier<Item> seed, @NotNull Supplier<Block> fallenSpores, @NotNull Supplier<ItemStack> harvestResults)
	{
		super(Objects.requireNonNull(properties));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0));
		
		this.m_fallenSpores = Objects.requireNonNull(fallenSpores);
		this.m_harvestResults = Objects.requireNonNull(harvestResults);
		this.m_seed = Objects.requireNonNull(seed);
		this.m_shape = Objects.requireNonNull(shape);
	} // end constructor

	
	
	@Override
	public Item asItem()
	{
		return this.m_seed.get();
	} // end asItem()



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(this.getAgeProperty()));
	} // end createBlockStateDefinition()
	
	@Override
	public @NotNull BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos().above();
		BlockState state = level.getBlockState(position);
		return this.canPlantOn(level, state, position, Direction.DOWN) 
				? super.getStateForPlacement(context) 
				: null;
	} // end getStateForPlacement()



	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestable.use(this, level, state, position, player, hand, hitResult);
	} // end use()



	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos position)
	{
		BlockPos check = position.above();
		return this.canPlantOrSurviveOn(level.getBlockState(check), Direction.DOWN);
	} // end canSurvive()



	@Override
	public boolean isRandomlyTicking(BlockState state)
	{
		return this.getAge(state) != this.getMaxAge();
	} // end isRandomlyTicking()
	



	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		int age = this.getAge(state);
		if (age < this.getMaxAge())
		{
			float f = (3.0f * ((float)state.getValue(this.getAgeProperty()))) + 1.0f;
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt((int) (25.0F / f) + 1) == 0))
			{
				int goingToAge = age + 1;
				if(goingToAge == this.getMaxAge()) 
				{
					this.tryDropSpores(level, position, random);
				}
				level.setBlock(position, this.getStateForAge(state, goingToAge), 2);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()

	protected void tryDropSpores(Level level, BlockPos from, RandomSource random) 
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
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()


	
	public @NotNull IntegerProperty getAgeProperty()
	{
		return ShulkwartBlock.AGE;
	} // end getAgeProperty()
	
	

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
		return (state.getValue(this.getAgeProperty()) == this.getMaxAge()) && action != null && this.validActions(level, state, position).contains(action); // action == ToolActions.SHEARS_HARVEST;
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



	@Override
	public boolean canPlantOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Direction face)
	{
		return this.canPlantOrSurviveOn(state, face);
	} // end canPkantOn()
	
	private boolean canPlantOrSurviveOn(BlockState state, Direction face) 
	{
		return YATMBlockTags.SHULKWART_GROWS_ON.contains(state.getBlock()) && face == Direction.DOWN;
	} // end canPlantOrSurviveOn()
	
} // end class