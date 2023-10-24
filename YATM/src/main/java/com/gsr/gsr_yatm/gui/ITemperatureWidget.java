package com.gsr.gsr_yatm.gui;

import net.minecraft.client.gui.components.ImageWidget;

public interface ITemperatureWidget
{
	public default ImageWidget getThis() 
	{
		return (ImageWidget)this;
	} // end getThis()
	
	public int getMaxTemperature();
	
	public void setTemperature(int amount);
	
} // end interface