package com.gsr.gsr_yatm.block.device.spinning_wheel;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipe;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.VoxelShapeProvider;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpinningWheelBlock extends Block
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	
	private final VoxelShapeProvider m_shape;
	
	
	
	public SpinningWheelBlock(Properties properties, VoxelShapeProvider shape)
	{
		super(properties);
		this.m_shape = shape;
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	} // end constructor

	
	

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FACING));
	} // end createBlockStateDefinition()
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection());
	} // end getStateForPlacement()



	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult)
	{
		ItemStack held = player.getItemInHand(hand);
		SpinningRecipe recipe = RecipeUtilities.firstRecipeMatching(level, YATMRecipeTypes.SPINNING.get(), (r) -> r.canBeUsedOn(held));
		if(recipe != null) 
		{
			if(!level.isClientSide) 
			{
				held.shrink(recipe.getInputCount(held.getItem()));
				InventoryUtilities.drop(level, pos, recipe.getResultItem(level.registryAccess()).copy());
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end use()

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()
	
} // end class