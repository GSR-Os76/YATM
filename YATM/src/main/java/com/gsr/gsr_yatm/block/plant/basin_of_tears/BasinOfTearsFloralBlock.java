package com.gsr.gsr_yatm.block.plant.basin_of_tears;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolActions;

// TODO, test all this, every little bit, every single drop
public class BasinOfTearsFloralBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_FOUR;
	public static final IntegerProperty FLOWER_COUNT = YATMBlockStateProperties.FLOWER_COUNT_FOUR;
	public static final BooleanProperty NECTAR_FULL = YATMBlockStateProperties.NECTAR_FULL;
	
	private static final int GROWTH_RARITY = 36;
	private static final int RESEED_RARITY = 36;
	
	
	
	public BasinOfTearsFloralBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState()
				.setValue(BasinOfTearsFloralBlock.AGE, 0)
				.setValue(BasinOfTearsFloralBlock.FLOWER_COUNT, 1)
				.setValue(BasinOfTearsFloralBlock.NECTAR_FULL, false));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return BasinOfTearsFloralBlock.AGE;
	} // end getAgeProperty()
	
	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(BasinOfTearsFloralBlock.AGE, BasinOfTearsFloralBlock.FLOWER_COUNT, BasinOfTearsFloralBlock.NECTAR_FULL));
	} // end createBlockStateDefinition()

	
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult)
	{
		ItemStack held = player.getItemInHand(hand);
		if(held.is(Items.GLASS_BOTTLE) && state.getValue(BasinOfTearsFloralBlock.NECTAR_FULL)) 
		{
			// cauldron in creative drains state, doesn't consume bottle, and only yield bottle water if none's already held.
			
			// TODO, should this be server only?
			if(!level.isClientSide) 
			{
				level.setBlock(position, state.setValue(BasinOfTearsFloralBlock.NECTAR_FULL, false), Block.UPDATE_CLIENTS);
			}
			
			boolean instabuild = player.getAbilities().instabuild;
			if(!instabuild) 
			{
				held.shrink(1);
			}
			
			if(
					!(instabuild 
					&& player.getInventory().hasAnyMatching((is) -> is.is(YATMItems.DILUTED_TEAR_BOTTLE.get()))
					)) 
			{
				ItemStack toDrop = new ItemStack(YATMItems.DILUTED_TEAR_BOTTLE.get());
				if (!player.addItem(toDrop))
				{
					InventoryUtilities.drop(level, position, toDrop);
				}
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		else if(held.canPerformAction(ToolActions.SHEARS_CARVE) // TODO, maybe another ToolAction, it just needs too semantically fit thinning
				&& state.getValue(BasinOfTearsFloralBlock.FLOWER_COUNT) > 1 
				&& state.getValue(BasinOfTearsFloralBlock.AGE) == 0)
		{
			if(!level.isClientSide) 
			{
				level.setBlock(position, state.setValue(BasinOfTearsFloralBlock.FLOWER_COUNT, state.getValue(BasinOfTearsFloralBlock.FLOWER_COUNT) - 1), Block.UPDATE_CLIENTS);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		else if(held.is(YATMItems.BASIN_OF_TEARS_SEED.get())
				&& state.getValue(BasinOfTearsFloralBlock.FLOWER_COUNT) < 4 
				&& state.getValue(BasinOfTearsFloralBlock.AGE) == 0)
		{
			if(!level.isClientSide) 
			{
				level.setBlock(position, state.setValue(BasinOfTearsFloralBlock.FLOWER_COUNT, state.getValue(BasinOfTearsFloralBlock.FLOWER_COUNT) + 1), Block.UPDATE_CLIENTS);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.use(state, level, position, player, hand, hitResult);
	} // end use()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.BASIN_OF_TEARS_FLOWERS_CAN_GROW_ON_KEY);
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.BASIN_OF_TEARS_FLOWERS_CAN_GROW_IN_KEY);
	} // end canPlantOn()



	
	
	
	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age == maxAge) 
		{
			if(ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(BasinOfTearsFloralBlock.RESEED_RARITY) == 0)) 
			{
				level.setBlock(position, YATMBlocks.BASIN_OF_TEARS_VEGETATION.get().defaultBlockState(), Block.UPDATE_ALL);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
		else 
		{
			boolean shouldGrow = random.nextInt(BasinOfTearsFloralBlock.GROWTH_RARITY) == 0;
			if(shouldGrow) 
			{
				if(ForgeHooks.onCropsGrowPre(level, position, state, shouldGrow))
				{
					level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
					ForgeHooks.onCropsGrowPost(level, position, state);
				}
			}
			else if((age + 1) == maxAge 
					&& state.getValue(BasinOfTearsFloralBlock.FLOWER_COUNT) == 1
					&& !state.getValue(BasinOfTearsFloralBlock.NECTAR_FULL))
			{
				level.setBlock(position, state.setValue(BasinOfTearsFloralBlock.NECTAR_FULL, true), Block.UPDATE_CLIENTS);
				
			}
		}
	} // end randomTick()
	
} // end class