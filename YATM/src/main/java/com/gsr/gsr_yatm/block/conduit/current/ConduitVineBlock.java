package com.gsr.gsr_yatm.block.conduit.current;

import java.util.Map.Entry;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.FaceBlock;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ConduitVineBlock extends FaceBlock // implements EntityBlock
{
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	
	public ConduitVineBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		this.m_shape = Objects.requireNonNull(shape);
	} // end constructor



	@Override
	protected boolean canPlaceOn(@NotNull BlockGetter getter, @NotNull BlockPos onPosition, @NotNull BlockState onState, @NotNull Direction cansFace)
	{
		BlockPos placedInPosition = onPosition.relative(cansFace);
		BlockState placedIn = getter.getBlockState(placedInPosition);
		BlockPos abovePlacedInPosition = placedInPosition.above();
		BlockState abovePlaceIn = getter.getBlockState(abovePlacedInPosition);
		return super.canPlaceOn(getter, onPosition, onState, cansFace) 
				|| (
						// if the face's on the horizontal plan it can be supported by another block above it with that face or up, or by the block that it is having up
						Direction.Plane.HORIZONTAL.test(cansFace) 
						&& (
								(
									abovePlaceIn.is(this) 
									&& this.hasFace(abovePlaceIn, cansFace.getOpposite())
								)
								|| 
								(
									placedIn.is(this) 
									&& placedIn.getValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(Direction.UP)) 
									&& this.canPlaceOn(getter, abovePlacedInPosition, abovePlaceIn, Direction.DOWN)
								)
							)
					);
	} // end canPlaceOn()

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos position, Block oldNeighbor, BlockPos neighborPosition, boolean p_60514_)
	{
		this.updateState(level, state, position);
	} // end neighborChanged()
	
	protected void updateState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos) 
	{
		Boolean changed = false;
		BlockState updateTo = state;
		BlockState drop = this.defaultBlockState();
		
		for(Entry<Direction, BooleanProperty> e : FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.entrySet()) 
		{
			BooleanProperty targetProp = e.getValue();
			if(state.getValue(targetProp)) 
			{
				Direction targetFace = e.getKey();
				BlockPos onPos = pos.relative(targetFace);
				BlockState onState = level.getBlockState(onPos);
				Direction onFace = targetFace.getOpposite();
				if(!this.canPlaceOn(level, onPos, onState, onFace)) 
				{
					changed = true;
					updateTo = updateTo.setValue(targetProp, false);
					drop = drop.setValue(targetProp, true);
				}
			}
		}
		
		if(changed) 
		{
			BlockState f = updateTo;
			level.setBlock(
					pos, 
					Direction.stream().anyMatch((d) -> f.getValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d))) ? updateTo : Blocks.AIR.defaultBlockState(), 
					Block.UPDATE_ALL);
			Block.dropResources(drop, level, pos);
		}
	} // end updateState()
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()



	
	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos position)
	{
		return true;
	} // end propagatesSkylightDown()
	
} // end block