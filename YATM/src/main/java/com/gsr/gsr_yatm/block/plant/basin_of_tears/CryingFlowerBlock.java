package com.gsr.gsr_yatm.block.plant.basin_of_tears;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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

public class CryingFlowerBlock extends ShapeBlock implements IAgingBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_FOUR;
	public static final IntegerProperty FLOWER_COUNT = YATMBlockStateProperties.FLOWER_COUNT_FOUR;
	public static final BooleanProperty NECTAR_FULL = YATMBlockStateProperties.NECTAR_FULL;
	
	//bud - young - open/nectar - seedpod - reseeded - repeat
	
	public CryingFlowerBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState()
				.setValue(CryingFlowerBlock.AGE, 0)
				.setValue(CryingFlowerBlock.FLOWER_COUNT, 1)
				.setValue(CryingFlowerBlock.NECTAR_FULL, false));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return CryingFlowerBlock.AGE;
	} // end getAgeProperty()
	
	
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(CryingFlowerBlock.AGE, CryingFlowerBlock.FLOWER_COUNT, CryingFlowerBlock.NECTAR_FULL));
	} // end createBlockStateDefinition()

	
	
	
	
	@Override
	public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack held, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if(held.is(Items.GLASS_BOTTLE) && state.getValue(CryingFlowerBlock.NECTAR_FULL)) 
		{
			// cauldron in creative drains state, doesn't consume bottle, and only yield bottle water if none's already held.
			
			level.setBlock(position, state.setValue(CryingFlowerBlock.NECTAR_FULL, false), Block.UPDATE_CLIENTS);
			boolean instabuild = player.getAbilities().instabuild;
			if(!instabuild) 
			{
				held.shrink(1);
			}
			
			if(!(instabuild 
				&& player.getInventory().hasAnyMatching((is) -> is.is(YATMItems.DILUTED_TEAR_BOTTLE.get())))) 
			{
				ItemStack toDrop = new ItemStack(YATMItems.DILUTED_TEAR_BOTTLE.get());
				if (!player.addItem(toDrop))
				{
					InventoryUtil.drop(level, position, toDrop);
				}
			}
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		else if(held.canPerformAction(ToolActions.SHEARS_CARVE) // TODO, maybe another ToolAction, it just needs too semantically fit thinning
				&& state.getValue(CryingFlowerBlock.FLOWER_COUNT) > 1 
				&& state.getValue(CryingFlowerBlock.AGE) == 0)
		{
			if(!level.isClientSide) 
			{
				level.setBlock(position, state.setValue(CryingFlowerBlock.FLOWER_COUNT, state.getValue(CryingFlowerBlock.FLOWER_COUNT) - 1), Block.UPDATE_CLIENTS);
			}
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		else if(held.is(YATMItems.CRYING_PLANT_SEEDS.get())
				&& state.getValue(CryingFlowerBlock.FLOWER_COUNT) < 4 
				&& state.getValue(CryingFlowerBlock.AGE) == 0)
		{
			if(!level.isClientSide) 
			{
				level.setBlock(position, state.setValue(CryingFlowerBlock.FLOWER_COUNT, state.getValue(CryingFlowerBlock.FLOWER_COUNT) + 1), Block.UPDATE_CLIENTS);
			}
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useItemOn(held, state, level, position, player, hand, hitResult);
	} // end useItemOn()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.CRYING_FLOWER_CAN_GROW_ON_KEY);
	} // end canSurvive()

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
				&& level.getBlockState(position.above()).is(YATMBlockTags.CRYING_FLOWER_CAN_GROW_IN_KEY);
	} // end canPlantOn()


	
	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age == maxAge) 
		{
			if(ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.CRYING_FLOWER_RESEED_RARITY.get()) == 0)) 
			{
				level.setBlock(position, YATMBlocks.CRYING_PLANT.get().defaultBlockState(), Block.UPDATE_ALL);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
		else 
		{
			if(ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.CRYING_FLOWER_GROWTH_RARITY.get()) == 0))
			{
				level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
			else if((random.nextInt(YATMConfigs.CRYING_FLOWER_NECTAR_REPLENISH_RARITY.get()) == 0)
					&& (age + 1) == maxAge 
					&& state.getValue(CryingFlowerBlock.FLOWER_COUNT) == 1
					&& !state.getValue(CryingFlowerBlock.NECTAR_FULL))
			{
				level.setBlock(position, state.setValue(CryingFlowerBlock.NECTAR_FULL, true), Block.UPDATE_CLIENTS);
			}
		}
	} // end randomTick()
	
} // end class