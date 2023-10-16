package com.gsr.gsr_yatm.block.plant.ferrum;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.block.IHarvestableBlock;
import com.gsr.gsr_yatm.block.IYATMPlantableBlock;
import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class FerrumBlock extends ShapeBlock implements IAgingBlock, IHarvestableBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final BooleanProperty HAS_FRUIT = YATMBlockStateProperties.HAS_FRUIT;
	
	public static final int FRUIT_RARITY = 3;
	public static final int GROWTH_RARITY = 36;
	public static final int MAX_FRUIT_COUNT = 3;
	
	public FerrumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(FerrumBlock.AGE, 0).setValue(FerrumBlock.HAS_FRUIT, false));
	} // end constructor

	
	
	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return FerrumBlock.AGE;
	} // end getAgeProperty()
	
	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		if (entity instanceof LivingEntity)
		{
			if (!level.isClientSide)
			{
				Vec3 vector = new Vec3(Math.abs(entity.getX() - entity.xOld), Math.abs(entity.getY() - entity.yOld), Math.abs(entity.getZ() - entity.zOld));
				double vecLength = vector.length();
				int age = this.getAge(state);
				
				float damage = (((float) vecLength) * (6.0f * age));
				if (vecLength > 0.1d)
				{
					// TODO, create custom damage source for this and for aurum
					entity.hurt(level.damageSources().thorns((Entity) null), damage);
				}
			}
		}
	} // end entityInside()
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FerrumBlock.AGE, FerrumBlock.HAS_FRUIT));
	} // end createBlockStateDefinition()
	
	
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestableBlock.use(this, level, state, position, player, hand, hitResult);
	} // end use()



	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return level.getBlockState(position.below()).is(YATMBlockTags.FERRUM_CAN_GROW_ON_KEY);
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
				&& level.getBlockState(position.above()).is(Blocks.AIR);
	} // end canPlantOn()
	
	

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return this.getAge(state) < this.getMaxAge() && !state.getValue(FerrumBlock.HAS_FRUIT);
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge && !state.getValue(FerrumBlock.HAS_FRUIT))
		{
			if(age + 1 == maxAge && ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(FerrumBlock.FRUIT_RARITY) == 0)) 
			{
				level.setBlock(position, state.setValue(FerrumBlock.HAS_FRUIT, true), Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
			else if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(FerrumBlock.GROWTH_RARITY) == 0)) 
			{
				level.setBlock(position, this.getStateForAge(state, age + 1), Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
			}
		}
	} // end randomTick()
	
	
	
	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		return state.getValue(FerrumBlock.HAS_FRUIT) ? List.of(ToolActions.PICKAXE_DIG) : List.of();
	} // end validActions()

	@Override
	public boolean isHarvestable(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return state.getValue(FerrumBlock.HAS_FRUIT);
	} // end isHarvestable()

	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return state.setValue(FerrumBlock.HAS_FRUIT, false);
	} // end getResultingState()

	@Override
	public @Nullable NonNullList<ItemStack> getResults(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return NonNullList.of((ItemStack)null, this.getHarvestResult());
	} // end getResults()
	
	protected @NotNull ItemStack getHarvestResult()
	{
		return new ItemStack(Items.IRON_NUGGET, RandomSource.create().nextIntBetweenInclusive(1, FerrumBlock.MAX_FRUIT_COUNT));
	} // end getHarvestResult()

} // end class