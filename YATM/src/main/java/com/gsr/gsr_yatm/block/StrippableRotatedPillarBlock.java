package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.NonNullSupplier;

public class StrippableRotatedPillarBlock extends RotatedPillarBlock
{
	private final NonNullSupplier<Block> m_whenStripped;
	
	
	
	public StrippableRotatedPillarBlock(Properties properties, @NotNull NonNullSupplier<Block> whenStripped)
	{
		super(properties);
		this.m_whenStripped = whenStripped;
	} // end constructor

	
	
	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
	{
		if(toolAction == ToolActions.AXE_STRIP) 
		{
			// retain current rotation if possible, done so by changing clicked face in the passed context to the getStateForPlacement() call to align with the block being stripped's current axis
			BlockPlaceContext bpc = new BlockPlaceContext(context);
			if(this.m_whenStripped.get() instanceof RotatedPillarBlock) 
			{
				bpc = new BlockPlaceContext(
						context.getLevel(), 
						context.getPlayer(), 
						context.getHand(), 
						context.getItemInHand(), 
						new BlockHitResult(context.getClickLocation(), fromAxis(state.getValue(AXIS)), context.getClickedPos(), context.isInside()));
			}
			BlockState bs = this.m_whenStripped.get().getStateForPlacement(bpc);
			if(!simulate && bs != null) 
			{
				context.getLevel().setBlock(context.getClickedPos(), bs, UPDATE_ALL);
			}
			return bs;
		}
		return null;
	} // end getToolModifiedState()
	
	private Direction fromAxis(Direction.Axis axis) 
	{
		return switch (axis)
		{
			case X -> Direction.EAST;
			case Y -> Direction.UP;
			case Z -> Direction.NORTH;
			default -> throw new IllegalArgumentException("unexpected arguement for a Direction.Axis:" + axis);

		};
	} // end fromAxis()
	
} // end class