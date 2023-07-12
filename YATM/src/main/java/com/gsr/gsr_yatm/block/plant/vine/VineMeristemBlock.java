package com.gsr.gsr_yatm.block.plant.vine;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VineMeristemBlock extends GrowingPlantHeadBlock
{
	private final Supplier<Block> m_body;
	
	
	public VineMeristemBlock(Properties properties, Supplier<Block> body)
	{
		super(properties, Direction.DOWN, Block.box(2.0D, 2.0D, 2.0D, 14.0D, 16.0D, 14.0D), false, .2d);
		this.m_body = body;
	} // end constructor()

	
	
	@Override
	protected Block getBodyBlock()
	{
		return this.m_body.get();
	} // end getBodyBlock()



	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos position, BlockState state, boolean unknown)
	{
		return false;
	} // end isValidBonemealTarget

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource random)
	{
		return 1;
	} // end getBlocksToGrowWhenBonemealed()

	@Override
	protected boolean canGrowInto(BlockState state)
	{
		return state.isAir();
	} // end canGrowInto()
	
} // end class