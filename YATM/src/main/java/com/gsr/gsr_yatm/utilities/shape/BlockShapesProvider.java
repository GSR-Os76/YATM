package com.gsr.gsr_yatm.utilities.shape;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockShapesProvider
{
	private final @NotNull IVoxelShapeProvider m_blockSupportShape;
	private final @NotNull ICollisionVoxelShapeProvider m_collisionShape;
	private final @NotNull IVoxelShapeProvider m_interactionShape;
	private final @NotNull IVoxelShapeProvider m_occlusionShape;
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	private final @NotNull ICollisionVoxelShapeProvider m_visualShape;
	
	
	
	public BlockShapesProvider(@NotNull IVoxelShapeProvider blockSupportShape, @NotNull ICollisionVoxelShapeProvider collisionShape, @NotNull IVoxelShapeProvider interactionShape, @NotNull IVoxelShapeProvider occlusionShape, @NotNull ICollisionVoxelShapeProvider shape, @NotNull ICollisionVoxelShapeProvider visualShape) 
	{
		this.m_blockSupportShape = Objects.requireNonNull(blockSupportShape);
		this.m_collisionShape = Objects.requireNonNull(collisionShape);
		this.m_interactionShape = Objects.requireNonNull(interactionShape);
		this.m_occlusionShape = Objects.requireNonNull(occlusionShape);
		this.m_shape = Objects.requireNonNull(shape);
		this.m_visualShape = Objects.requireNonNull(visualShape);
	} // end constructor
	
	
	
	
	
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return this.m_blockSupportShape.getShape(state, blockGetter, position);
	} // end getBlockSupportShape()

	public VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext context)
	{
		return this.m_collisionShape.getShape(state, blockGetter, position, context);
	} // end getCollisionShape()
	
	public VoxelShape getInteractionShape(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return this.m_interactionShape.getShape(state, blockGetter, position);
	} // end getInteractionShape()
	
	public VoxelShape getOcclusionShape(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return this.m_occlusionShape.getShape(state, blockGetter, position);
	} // end getOcclusionShape()
	
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext context)
	{
		return m_shape.getShape(state, blockGetter, position, context);
	} // end getShape()

	public VoxelShape getVisualShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext context)
	{
		return this.m_visualShape.getShape(state, blockGetter, position, context);
	} // end getVisualShape()
	
	
	
	public static class Builder
	{
		private IVoxelShapeProvider m_blockSupportShape;
		private ICollisionVoxelShapeProvider m_collisionShape;
		private IVoxelShapeProvider m_interactionShape;
		private IVoxelShapeProvider m_occlusionShape;
		private ICollisionVoxelShapeProvider m_shape;
		private ICollisionVoxelShapeProvider m_visualShape;
		
		
		
		protected Builder() { } // end constructor
		
		public static Builder of() 
		{
			return new Builder();
		} // end of()
		
		public static Builder of(IVoxelShapeProvider allShapes) 
		{
			Builder builder = new Builder();
			builder.m_blockSupportShape = allShapes;
			builder.m_collisionShape = (bs, bg, bp, cc) -> allShapes.getShape(bs, bg, bp);
			builder.m_interactionShape = allShapes;
			builder.m_occlusionShape = allShapes;
			builder.m_shape = (bs, bg, bp, cc) -> allShapes.getShape(bs, bg, bp);
			builder.m_visualShape = (bs, bg, bp, cc) -> allShapes.getShape(bs, bg, bp);
			return builder;
		} // end of()
		
		public static Builder of(ICollisionVoxelShapeProvider allShapes) 
		{
			Builder builder = new Builder();
			builder.m_blockSupportShape = (bs, bg, bp) -> allShapes.getShape(bs, bg, bp, CollisionContext.empty());
			builder.m_collisionShape = allShapes;
			builder.m_interactionShape = (bs, bg, bp) -> allShapes.getShape(bs, bg, bp, CollisionContext.empty());
			builder.m_occlusionShape = (bs, bg, bp) -> allShapes.getShape(bs, bg, bp, CollisionContext.empty());
			builder.m_shape = allShapes;
			builder.m_visualShape = allShapes;
			return builder;
		} // end of()
		
		@SuppressWarnings("deprecation")
		public static Builder copyOf(Block copy) 
		{
			Builder builder = new Builder();
			builder.m_blockSupportShape = copy::getBlockSupportShape;
			builder.m_collisionShape = copy::getCollisionShape;
			builder.m_interactionShape = copy::getInteractionShape;
			builder.m_occlusionShape = copy::getOcclusionShape;
			builder.m_shape = copy::getShape;
			builder.m_visualShape = copy::getVisualShape;
			return builder;
		} // end of()
		
		
		
		public Builder interactionShape(IVoxelShapeProvider interactionShape) 
		{
			this.m_interactionShape = interactionShape;
			return this;
		} // end interactionShape()
		
		public Builder shape(ICollisionVoxelShapeProvider shape) 
		{
			this.m_shape = shape;
			return this;
		} // end shape()
		
		
		// TODO, others
		
		
		public BlockShapesProvider build() 
		{
			return new BlockShapesProvider(this.m_blockSupportShape, this.m_collisionShape, this.m_interactionShape, this.m_occlusionShape, this.m_shape, this.m_visualShape);
		} // end build()
		
	} // end inner class
} // end class