package com.gsr.gsr_yatm.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChorusBioFluid extends Fluid
{

	
	
	@Override
	public Item getBucket()
	{
		// TODO Auto-generated method stub
		return null;
		//WaterFluid d ;
	}

	@Override
	protected boolean canBeReplacedWith(FluidState p_76127_, BlockGetter p_76128_, BlockPos p_76129_, Fluid p_76130_, Direction p_76131_)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Vec3 getFlow(BlockGetter p_76110_, BlockPos p_76111_, FluidState p_76112_)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTickDelay(LevelReader p_76120_)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected float getExplosionResistance()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight(FluidState p_76124_, BlockGetter p_76125_, BlockPos p_76126_)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOwnHeight(FluidState p_76123_)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected BlockState createLegacyBlock(FluidState p_76136_)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSource(FluidState p_76140_)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAmount(FluidState p_76141_)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VoxelShape getShape(FluidState p_76137_, BlockGetter p_76138_, BlockPos p_76139_)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
