package com.gsr.gsr_yatm.utilities.shape;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import net.minecraft.world.phys.shapes.VoxelShape;
import oshi.util.tuples.Pair;

public interface IVoxelShapeBuilder
{
	public static final Vector3d BLOCK_CENTERIC_PIVOT = new Vector3d(8.0d, 8.0d, 8.0d);
	public @NotNull Vector3d getLowVertex();
	
	public @NotNull Vector3d getHighVertex();
	
	public @NotNull List<@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder>> getAdditionalParts();
	
	
	
	public @NotNull IVoxelShapeBuilder binary(@NotNull BinaryOperation operation, @NotNull IVoxelShapeBuilder b);
	
	public default IVoxelShapeBuilder and(@NotNull IVoxelShapeBuilder b) 
	{
		return this.binary(BinaryOperation.AND, b);
	} // end and()
	
	public default @NotNull IVoxelShapeBuilder or(@NotNull IVoxelShapeBuilder b) 
	{
		return this.binary(BinaryOperation.OR, b);
	} // end or()
	
	public default @NotNull IVoxelShapeBuilder nand(@NotNull IVoxelShapeBuilder b) 
	{
		return this.binary(BinaryOperation.NAND, b);
	} // end nand()
	
	public default @NotNull IVoxelShapeBuilder nor(@NotNull IVoxelShapeBuilder b) 
	{
		return this.binary(BinaryOperation.NOR, b);
	} // end nor()
	
	public default @NotNull IVoxelShapeBuilder xor(@NotNull IVoxelShapeBuilder b) 
	{
		return this.binary(BinaryOperation.XOR, b);
	} // end xor()
	
	
	
	// a ninty degree rotation to be specific
	public default @NotNull IVoxelShapeBuilder yRotateLookingDownCounterClockwise() 
	{
		return this.yRotateLookingDownClockwise(IVoxelShapeBuilder.BLOCK_CENTERIC_PIVOT);
	} // end yRotateLookingDownClockwise
	
	public @NotNull IVoxelShapeBuilder yRotateLookingDownClockwise(Vector3d pivot);
	
	
	
	public @NotNull VoxelShape toMCVoxelShape();	
	
	
	
	
	
	public static class MutableWrapper implements IVoxelShapeBuilder
	{
		private IVoxelShapeBuilder m_wrapped;
		
		
		
		public MutableWrapper(IVoxelShapeBuilder wrapped) 
		{
			this.m_wrapped = wrapped;
		} // end constructor
		
		public static MutableWrapper of(IVoxelShapeBuilder wrapped) 
		{
			return new MutableWrapper(wrapped);
		} // end of()



		@Override
		public @NotNull Vector3d getLowVertex()
		{
			return this.m_wrapped.getLowVertex();
		} // end getLowVertex()

		@Override
		public @NotNull Vector3d getHighVertex()
		{
			return this.m_wrapped.getHighVertex();
		} // end getHighVertex()

		@Override
		public @NotNull List<@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder>> getAdditionalParts()
		{
			return this.m_wrapped.getAdditionalParts();
		} // end getAdditionalParts()



		@Override
		public IVoxelShapeBuilder binary(BinaryOperation operation, IVoxelShapeBuilder b)
		{
			this.m_wrapped = this.m_wrapped.binary(operation, b);
			return this.m_wrapped;
		} // end binary()

		@Override
		public IVoxelShapeBuilder yRotateLookingDownClockwise(Vector3d pivot)
		{
			this.m_wrapped = this.m_wrapped.yRotateLookingDownClockwise(pivot);
			return this.m_wrapped;
		} // end yRotateLookingDownClockwise()

		@Override
		public VoxelShape toMCVoxelShape()
		{
			return this.m_wrapped.toMCVoxelShape();
		} // end toMCVoxelShape()
		
	} // end inner class
} // end interface