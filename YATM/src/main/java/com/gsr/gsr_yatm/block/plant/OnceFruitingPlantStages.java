package com.gsr.gsr_yatm.block.plant;

import net.minecraft.util.StringRepresentable;

public enum OnceFruitingPlantStages implements StringRepresentable
{
	IMMATURE("immature"),
	FRUITING("fruiting"),
	HARVESTED("harvested");

	private final String m_name;
	  
	private OnceFruitingPlantStages(String name) 
	{
	     this.m_name = name;
	}
	
	@Override
	public String getSerializedName()
	{
		return this.m_name;
	} // end getSerializedName()
	
} // end enum