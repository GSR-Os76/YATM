package com.gsr.gsr_yatm.block.device;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

import net.minecraft.world.level.block.Block;

public class CurrentRegulatorBlock extends Block implements ICurrentHandler
{


	public CurrentRegulatorBlock(Properties p_49795_, int regulatorChipSlots)
	{		
		super(p_49795_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int capacity()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int stored()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	//public void 
	
	



} // end class