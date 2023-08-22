package com.gsr.gsr_yatm.utilities.shape;

import com.google.common.collect.ImmutableBiMap;

import net.minecraft.world.phys.shapes.BooleanOp;

public class ShapeUtilities
{
	public static final ImmutableBiMap<BinaryOperation, BooleanOp> EQUIVALENCE_TABLE = ImmutableBiMap.of(
			BinaryOperation.AND, BooleanOp.AND, 
			BinaryOperation.OR, BooleanOp.OR, 
			BinaryOperation.NAND, BooleanOp.NOT_AND, 
			BinaryOperation.NOR, BooleanOp.NOT_OR, 
			BinaryOperation.XOR, BooleanOp.NOT_SAME);
	
	
	
	public static BooleanOp toBooleanOp(BinaryOperation operation) 
	{
		return ShapeUtilities.EQUIVALENCE_TABLE.get(operation);
	} // end toBooleanOp()
	
} // end class