package com.gsr.gsr_yatm.utilities.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import com.google.common.collect.ImmutableList;
import com.google.common.math.DoubleMath;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import oshi.util.tuples.Pair;


public class VoxelShapeBuilder implements IVoxelShapeBuilder
{
	private static final double EPSILON = 0.000001d;
	private final double m_x;
	private final double m_y;
	private final double m_z;
	private final double m_toX;
	private final double m_toY;
	private final double m_toZ;
	
	// TODO, rename
	private final @NotNull List<@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder>> m_additionalParts;
	
	
	
	protected VoxelShapeBuilder(double x, double y, double z, double toX, double toY, double toZ, @NotNull List<@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder>> additionalParts)
	{
		if((DoubleMath.fuzzyEquals(x, toX, EPSILON)) || (DoubleMath.fuzzyEquals(y, toY, EPSILON)) || (DoubleMath.fuzzyEquals(z, toZ, EPSILON))) 
		{
			throw new IllegalArgumentException("The vertices must not be linearly dependent.");
		}
		
		this.m_x = Math.min(x, toX);
		this.m_y = Math.min(y, toY);
		this.m_z = Math.min(z, toZ);
		this.m_toX = Math.max(x, toX);
		this.m_toY = Math.max(y, toY);
		this.m_toZ = Math.max(z, toZ);
		
		
		this.m_additionalParts = Objects.requireNonNull(additionalParts).stream().map((p) -> 
		{
			Objects.requireNonNull(p);
			BinaryOperation op = Objects.requireNonNull(p.getA());
			IVoxelShapeBuilder vs = Objects.requireNonNull(p.getB());
			return new Pair<>(op, vs);
		}).collect(ImmutableList.toImmutableList());
		
	} // end constructor
	
	
	
	public @NotNull Vector3d getLowVertex() 
	{
		return new Vector3d(this.m_x, this.m_y, this.m_z);
	} // end getLowVertex()
	
	public @NotNull Vector3d getHighVertex() 
	{
		return new Vector3d(this.m_toX, this.m_toY, this.m_toZ);
	} // end getHighVertex()
	
	public @NotNull List<@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder>> getAdditionalParts() 
	{
		return this.m_additionalParts;
	} // end getHighVertex()
	
	
	
	@Override
	public @NotNull  VoxelShapeBuilder binary(@NotNull BinaryOperation operation, @NotNull IVoxelShapeBuilder b) 
	{
		return VoxelShapeBuilder.binary(operation, this, b);
	} // end binary()
	
	@Override
	public @NotNull VoxelShapeBuilder yRotateLookingDownClockwise(Vector3d pivot) 
	{
		return VoxelShapeBuilder.yRotateLookingDownClockwise(this, pivot);
	} // end shape;
	
	@Override
	public @NotNull VoxelShape toMCVoxelShape() 
	{
		return VoxelShapeBuilder.toMCVoxelShape(this);
	} // end toMCVoxelShape()
	
	
	
	
	
	public static @NotNull VoxelShapeBuilder box(double x, double y, double z, double toX, double toY, double toZ) 
	{
		return new VoxelShapeBuilder(x, y, z, toX, toY, toZ, List.of());
	} // end box()
	

	
	public static @NotNull VoxelShapeBuilder binary(@NotNull BinaryOperation operation, @NotNull IVoxelShapeBuilder a, @NotNull IVoxelShapeBuilder b) 
	{
		Vector3d low = a.getLowVertex();
		Vector3d high = a.getHighVertex();
		
		List<@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder>> l = new ArrayList<>(a.getAdditionalParts());
		l.add(new Pair<>(operation, b));
		return new VoxelShapeBuilder(low.x, low.y, low.z, high.x, high.y, high.z, l);
	} // end binary()
	
	public static @NotNull VoxelShapeBuilder and(@NotNull IVoxelShapeBuilder a, @NotNull IVoxelShapeBuilder b) 
	{
		return VoxelShapeBuilder.binary(BinaryOperation.AND, a, b);
	} // end and()
	
	public static @NotNull VoxelShapeBuilder or(@NotNull IVoxelShapeBuilder a, @NotNull IVoxelShapeBuilder b) 
	{
		return VoxelShapeBuilder.binary(BinaryOperation.OR, a, b);
	} // end or()
	
	public static @NotNull VoxelShapeBuilder nand(@NotNull IVoxelShapeBuilder a, @NotNull IVoxelShapeBuilder b) 
	{
		return VoxelShapeBuilder.binary(BinaryOperation.NAND, a, b);
	} // end nand()
	
	public static @NotNull VoxelShapeBuilder nor(@NotNull IVoxelShapeBuilder a, @NotNull IVoxelShapeBuilder b) 
	{
		return VoxelShapeBuilder.binary(BinaryOperation.NOR, a, b);
	} // end nor()
	
	public static @NotNull VoxelShapeBuilder xor(@NotNull IVoxelShapeBuilder a, @NotNull IVoxelShapeBuilder b) 
	{
		return VoxelShapeBuilder.binary(BinaryOperation.XOR, a, b);
	} // end xor()
	
	
	
	public static @NotNull VoxelShape toMCVoxelShape(@NotNull VoxelShapeBuilder shape) 
	{
		if(shape.getAdditionalParts().isEmpty()) 
		{
			return VoxelShapeBuilder.getBox(shape);
		}
		else 
		{
			VoxelShape base = VoxelShapeBuilder.getBox(shape);
			for(@NotNull Pair<@NotNull BinaryOperation, @NotNull IVoxelShapeBuilder> pair : shape.getAdditionalParts()) 
			{
				base = Shapes.join(base, pair.getB().toMCVoxelShape(), ShapeUtilities.toBooleanOp(pair.getA()));
			}
			return base;
		}
	} // end toMCVoxelShape()
		
	private static VoxelShape getBox(VoxelShapeBuilder shape) 
	{
		Vector3d lowVertex = shape.getLowVertex();
		Vector3d highVertex = shape.getHighVertex();
		
		return Block.box(lowVertex.x, lowVertex.y, lowVertex.z, highVertex.x, highVertex.y, highVertex.z);
	} // end getBox()
	
	
	
	// rotation
	public static @NotNull VoxelShapeBuilder yRotateLookingDownClockwise(@NotNull VoxelShapeBuilder shape, @NotNull Vector3d pivot) 
	{
		// TODO, possibly not pivoting around correctly for pivot where (pivot.x != pivot.z), review
		Vector3d lVert = shape.getLowVertex();
		Vector3d hVert = shape.getHighVertex();
		
		double x = Math.min(lVert.z, hVert.z);
		double y = lVert.y;
		double z = Math.min(((pivot.x - lVert.x) + pivot.x), ((pivot.x - hVert.x) + pivot.x));
		double toX = Math.max(lVert.z, hVert.z);;
		double toY = hVert.y;
		double toZ = Math.max(((pivot.x - lVert.x) + pivot.x), ((pivot.x - hVert.x) + pivot.x));
		
		return new VoxelShapeBuilder(x, y, z, toX, toY, toZ, shape.getAdditionalParts().stream().map((p) -> new Pair<>(p.getA(), p.getB().yRotateLookingDownCounterClockwise())).toList());
	} // end shape;
	
} // end class