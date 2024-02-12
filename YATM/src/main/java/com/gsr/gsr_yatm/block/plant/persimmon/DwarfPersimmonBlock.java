package com.gsr.gsr_yatm.block.plant.persimmon;

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

public class DwarfPersimmonBlock extends ShapeBlock implements BonemealableBlock, IAgingBlock, IHarvestableBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	
	
	
	public DwarfPersimmonBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
	} // end constructor



	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return DwarfPersimmonBlock.AGE;
	} // end getAgeProperty()


	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(this.getAgeProperty()));
	} // end builder()


	
	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestableBlock.use(this, level, state, position, player, hand);
	} // end use()
	
	

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.DWARF_PERSIMMON_CAN_GROW_ON_KEY);
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
				&& level.getBlockState(position.above()).is(YATMBlockTags.DWARF_PERSIMMON_CAN_GROW_IN_KEY);
	} // end canPlantOn()

	
	
	
	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return this.getAge(state) != this.getMaxAge();
	} // end state()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if (BlockUtil.isLightLevelBelow(level, position, YATMConfigs.DWARF_PERSIMMON_MINIMUM_LIGHT_LEVEL.get())) 
		{
			return;
		}
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge)
		{
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.DWARF_PERSIMMON_GROWTH_RARITY.get()) == 0)) 
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
		return random.nextInt(YATMConfigs.DWARF_PERSIMMON_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		level.setBlock(position, this.getStateForAge(state, Math.min(this.getMaxAge(), this.getAge(state) + random.nextIntBetweenInclusive(Math.min(YATMConfigs.DWARF_PERSIMMON_MIN_AGE_INCREASE.get(), YATMConfigs.DWARF_PERSIMMON_MAX_AGE_INCREASE.get()), Math.max(YATMConfigs.DWARF_PERSIMMON_MIN_AGE_INCREASE.get(), YATMConfigs.DWARF_PERSIMMON_MAX_AGE_INCREASE.get())))), Block.UPDATE_CLIENTS);
	} // end performBonemeal()
	
	

	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		List<@Nullable ToolAction> as = new ArrayList<>();
		as.add((ToolAction)null);
		return this.getAge(state) == this.getMaxAge() ? as : List.of();
	} // end validActions()
	
	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return state.setValue(this.getAgeProperty(), this.getMaxAge() - 1);
	} // end getResultingState()

	@Override
	public @Nullable NonNullList<ItemStack> getResults(@NotNull ServerLevel level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return NonNullList.of((ItemStack)null, level.getServer().getLootData().getLootTable(YATMHarvestLoot.DWARF_PERSIMMON).getRandomItems(new LootParams.Builder(level).create(LootContextParamSets.EMPTY)).stream().filter((i) -> i != null).toList().toArray(new ItemStack[0]));
	} // end getResults()

} // end class