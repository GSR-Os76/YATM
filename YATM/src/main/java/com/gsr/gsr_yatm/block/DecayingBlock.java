package com.gsr.gsr_yatm.block;

import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DecayingBlock extends Block
{
	private final ICollisionVoxelShapeProvider m_shape;
	private final int m_decayChance;
	private final BlockState m_decaysInto = Blocks.AIR.defaultBlockState();
	
	
	public DecayingBlock(Properties properties, ICollisionVoxelShapeProvider shape, int decayChance)
	{
		super(properties);
		this.m_shape = shape;
		this.m_decayChance = decayChance;
	} // end constructor

	
	
	
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random)
	{
		if(random.nextInt(this.m_decayChance) == 0) 
		{
			level.setBlock(position, this.m_decaysInto, 3);
		}
	} // end randomTick()

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()
	
} // end class