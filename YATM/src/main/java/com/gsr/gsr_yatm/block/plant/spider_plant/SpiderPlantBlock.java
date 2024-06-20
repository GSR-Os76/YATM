package com.gsr.gsr_yatm.block.plant.spider_plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IHarvestableBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMHarvestLoot;
import com.gsr.gsr_yatm.utilities.BlockUtil;
import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class SpiderPlantBlock extends ShapeBlock implements IAgingBlock, IHarvestableBlock, IYATMPlantableBlock, BonemealableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	
	
	
	public SpiderPlantBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0));
	}

	
	
	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return SpiderPlantBlock.AGE;
	} // end getAgeProperty()



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(this.getAgeProperty()));
	} // end createBlockStateDefinition()
	
	
	
	@Override
	public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack held, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestableBlock.useItemOn(this, held, level, state, position);
	} // end useItemOn()
	
	@Override
	protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos position, Player player, BlockHitResult hitResult)
	{
		return IHarvestableBlock.useItemOn(this, ItemStack.EMPTY, level, state, position).result();
	} // end useWithoutItem()
	


	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.above()).is(YATMBlockTags.SPIDER_PLANT_CAN_GROW_ON_KEY);
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
		BlockPos below = position.below();
		return face == Direction.DOWN
				&& this.canSurvive(level.getBlockState(below), level, below) 
				&& level.getBlockState(position.above()).is(YATMBlockTags.SPIDER_PLANT_CAN_GROW_IN_KEY);
	} // end canPlantOn()



	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return this.getAge(state) < this.getMaxAge();
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if (BlockUtil.isLightLevelBelow(level, position, YATMConfigs.SPIDER_PLANT_MINIMUM_LIGHT_LEVEL.get())) 
		{
			return;
		}
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge)
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.SPIDER_PLANT_GROWTH_RARITY.get()) == 0)) 
			{
				level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()
	
	
	
	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return this.getAge(state) != this.getMaxAge();
	} // end isValidBonemealTarget()
	
	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.SPIDER_PLANT_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		level.setBlock(position, this.getStateForAge(state, Math.min(this.getMaxAge(), this.getAge(state) + random.nextIntBetweenInclusive(Math.min(YATMConfigs.SPIDER_PLANT_MIN_AGE_INCREASE.get(), YATMConfigs.SPIDER_PLANT_MAX_AGE_INCREASE.get()), Math.max(YATMConfigs.SPIDER_PLANT_MIN_AGE_INCREASE.get(), YATMConfigs.SPIDER_PLANT_MAX_AGE_INCREASE.get())))), Block.UPDATE_CLIENTS);
	} // end performBonemeal()
	
	

	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		List<@Nullable ToolAction> as = new ArrayList<>();
		as.add((ToolAction)null);
		as.add((ToolAction)ToolActions.SHEARS_HARVEST);
		return this.getAge(state) == this.getMaxAge() ? as : List.of();
	} // end validActions()
	
	@Override
	public @NotNull BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return state.setValue(this.getAgeProperty(), this.getMaxAge() - 1);
	} // end getResultingState()

	@Override
	public @NotNull NonNullList<ItemStack> getResults(@NotNull ServerLevel level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		if(action == null)
		{
			return NonNullList.of((ItemStack)null, level.getServer().reloadableRegistries().getLootTable(YATMHarvestLoot.SPIDER_PLANT_MANUAL).getRandomItems(new LootParams.Builder(level).create(LootContextParamSets.EMPTY)).stream().filter((i) -> i != null).toList().toArray(new ItemStack[0]));

		}
		else if (action == ToolActions.SHEARS_HARVEST) 
		{
			return NonNullList.of((ItemStack)null, level.getServer().reloadableRegistries().getLootTable(YATMHarvestLoot.SPIDER_PLANT_SHEARED).getRandomItems(new LootParams.Builder(level).create(LootContextParamSets.EMPTY)).stream().filter((i) -> i != null).toList().toArray(new ItemStack[0]));
		}
		else 
		{
			throw new IllegalArgumentException(action + ", is not a currently valid action for harvesting a Spider Plant");
		}
	} // end getResults()

} // end class