package com.gsr.gsr_yatm.block;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class FaceBlock extends Block
{
	public static final Map<Direction, BooleanProperty> HAS_FACE_PROPERTIES_BY_DIRECTION = YATMBlockStateProperties.HAS_DIRECTION_PROPERTIES_BY_DIRECTION;
	
	
	
	public FaceBlock(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
		
		BlockState state = this.defaultBlockState();
		for(Entry<Direction, BooleanProperty> p : FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.entrySet()) 
		{
			state = state.setValue(p.getValue(), false);
		}
		this.registerDefaultState(state);
	} // end constructor

	
	
	@Override
	public boolean canBeReplaced(BlockState state, @NotNull BlockPlaceContext context)
	{
		return context.getPlayer().getItemInHand(context.getHand()).getItem() == this.asItem();
	} // end canBeReplaced()

	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.values().forEach((p) -> builder.add(p));
		super.createBlockStateDefinition(builder);
	} // end createBlockStateDefinition()

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		BlockState replacing = level.getBlockState(position);
		Direction targetFace = context.getClickedFace().getOpposite();
		

		BlockState result = replacing.is(this) ? replacing : this.defaultBlockState();
		BlockPos willBeOnPos = position.relative(targetFace);
		
		if (this.canPlaceOn(level, willBeOnPos, level.getBlockState(willBeOnPos), targetFace.getOpposite()) 
				&& !this.hasFace(result, targetFace))
		{
			result = result.setValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(targetFace), true);
		}
		else
		{
			return null;
		}
		return result;
	} // end getStateForPlacement()
	
	// takes in the position and state of the block that's being placed onto, and the face that's being placed on of that specific block.
	// if you're trying to add a bottom face you're then placing on the below block's top face.
	protected boolean canPlaceOn(@NotNull BlockGetter getter, @NotNull BlockPos onPosition, @NotNull BlockState onState, @NotNull Direction cansFace) 
	{
		return Block.isFaceFull(onState.getBlockSupportShape(getter, onPosition), cansFace);
	} // end canPlaceOn()
	
	protected boolean hasFace(BlockState state, Direction face) 
	{
		return state.getValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(face));
	} // end hasFace()
	
} // end class