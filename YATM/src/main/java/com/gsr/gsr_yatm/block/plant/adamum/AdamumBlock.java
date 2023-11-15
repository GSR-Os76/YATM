package com.gsr.gsr_yatm.block.plant.adamum;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class AdamumBlock extends ShapeBlock implements IAgingBlock, IHarvestableBlock, IYATMPlantableBlock
{
	public static final IntegerProperty AGE = YATMBlockStateProperties.AGE_EIGHT;
	public static final IntegerProperty MAJOR_AGE = YATMBlockStateProperties.AGE_EIGHT_MAJOR;
	public static final BooleanProperty HAS_FRUIT = YATMBlockStateProperties.HAS_FRUIT;
	
	public static final int MAX_MAJOR_AGE = 7;
	
	
	
	public AdamumBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(this.getAgeProperty(), 0).setValue(AdamumBlock.MAJOR_AGE, 0).setValue(AdamumBlock.HAS_FRUIT, false));
	} // end constructor

	

	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return AdamumBlock.AGE;
	} // end getAgeProperty()

	@Override
	public void entityInside(BlockState state, Level level, BlockPos position, Entity entity)
	{
		if (entity instanceof LivingEntity)
		{
			if (!level.isClientSide)
			{
				double speed = new Vec3(Math.abs(entity.getX() - entity.xOld), Math.abs(entity.getY() - entity.yOld), Math.abs(entity.getZ() - entity.zOld)).length();
				
				double damageFactor = YATMConfigs.ADAMUM_DAMAGE_FACTOR.get();
				float damage = (((float) speed) * (((float)damageFactor) * (this.getAge(state))));
				if (speed > YATMConfigs.ADAMUM_DAMAGE_TRIGGER_TOLERANCE.get())
				{
					// TODO, create custom damage source for this too
					entity.hurt(level.damageSources().thorns((Entity) null), damage);
				}
			}
		}
	} // end entityInside()
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(this.getAgeProperty(), AdamumBlock.MAJOR_AGE, AdamumBlock.HAS_FRUIT));
	} // end createBlockStateDefinition()
	


	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return IHarvestableBlock.use(this, level, state, position, player, hand, hitResult);
	} // end use()



	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		BlockState above = level.getBlockState(position.above());
		return above.is(YATMBlockTags.ADAMUM_CAN_GROW_ON_KEY) 
				|| (above.is(this) && this.getAge(above) == this.getMaxAge());
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
		BlockPos below = position.below();
		return face == Direction.DOWN 
				&& this.canSurvive(level.getBlockState(below), level, below) 
				&& level.getBlockState(position.below()).is(YATMBlockTags.ADAMUM_CAN_GROW_IN_KEY);
	} // end canPlantOn()
	


	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return this.getAge(state) < this.getMaxAge() 
				&& state.getValue(AdamumBlock.MAJOR_AGE) < AdamumBlock.MAX_MAJOR_AGE;
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		int age = this.getAge(state);
		int maxAge = this.getMaxAge();
		if(age < maxAge && state.getValue(AdamumBlock.MAJOR_AGE) < AdamumBlock.MAX_MAJOR_AGE)
		{
			int dAge = age + 1;
			BlockState dState = this.getStateForAge(state, dAge);
			// don't grow down if blocked.
			if(dAge == maxAge && !level.getBlockState(position.below()).is(YATMBlockTags.ADAMUM_CAN_GROW_IN_KEY))
			{
				return;
			} 
			if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.ADAMUM_GROWTH_RARITY.get()) == 0)) 
			{
				level.setBlock(position, dState, Block.UPDATE_CLIENTS);
				ForgeHooks.onCropsGrowPost(level, position, state);
				if(dAge == maxAge) 
				{
					level.setBlock(position.below(), this.getStateForAge(dState.setValue(AdamumBlock.MAJOR_AGE, Math.min(AdamumBlock.MAX_MAJOR_AGE, dState.getValue(AdamumBlock.MAJOR_AGE) + (1 + random.nextIntBetweenInclusive(YATMConfigs.ADAMUM_MIN_MAJOR_AGE_INCREASE.get(), YATMConfigs.ADAMUM_MAX_MAJOR_AGE_INCREASE.get())))), 0).setValue(AdamumBlock.HAS_FRUIT, random.nextInt(YATMConfigs.ADAMUM_FRUITING_RARITY.get()) == 0), Block.UPDATE_ALL);
				}
			}
		}
	} // end randomTick()



	@Override
	public @NotNull List<@Nullable ToolAction> validActions(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position)
	{
		return state.getValue(AdamumBlock.HAS_FRUIT) ? List.of(ToolActions.PICKAXE_DIG) : List.of();
	} // end validActions()

	@Override
	public @Nullable BlockState getResultingState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		return this.isHarvestable(level, state, position, action) ? state.setValue(AdamumBlock.HAS_FRUIT, false) : null;
	} // end getResultingState()

	@Override
	public @Nullable NonNullList<ItemStack> getResults(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @Nullable ToolAction action)
	{
		// TODO, add loottable instead
		return this.isHarvestable(level, state, position, action) ? NonNullList.of((ItemStack)null, new ItemStack(Items.DIAMOND, level.random.nextIntBetweenInclusive(1, 2))) : null;
	} // end getResults()
	
} // end class