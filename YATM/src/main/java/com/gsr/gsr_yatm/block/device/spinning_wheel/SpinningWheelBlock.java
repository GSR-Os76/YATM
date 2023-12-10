package com.gsr.gsr_yatm.block.device.spinning_wheel;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.ShapeBlock;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipe;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SpinningWheelBlock extends ShapeBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	
	
	public SpinningWheelBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape));
		this.registerDefaultState(this.defaultBlockState().setValue(SpinningWheelBlock.FACING, Direction.NORTH));
	} // end constructor

	
	

	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(SpinningWheelBlock.FACING));
	} // end createBlockStateDefinition()
	
	@Override
	public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		return super.getStateForPlacement(context).setValue(SpinningWheelBlock.FACING, context.getHorizontalDirection());
	} // end getStateForPlacement()



	@Override
	public InteractionResult use(BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Player player, @NotNull InteractionHand hand, BlockHitResult hitResult)
	{
		ItemStack held = player.getItemInHand(hand);
		RecipeHolder<SpinningRecipe> rHolder = RecipeUtil.firstRecipeMatching(level, YATMRecipeTypes.SPINNING.get(), (r) -> r.matches(held));
		if(rHolder != null) 
		{
			if(!level.isClientSide) 
			{
				held.shrink(rHolder.value().getInputCount(held.getItem()));
				InventoryUtil.drop(level, position, rHolder.value().getResultItem(level.registryAccess()).copy());
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end use()
	
} // end class