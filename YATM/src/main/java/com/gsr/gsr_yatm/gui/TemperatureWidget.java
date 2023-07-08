package com.gsr.gsr_yatm.gui;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TemperatureWidget extends ImageWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	public static final int WIDTH = 102;
	public static final int HEIGHT = 11;
	// tank starts at 0 84, is 102 11 in dimensions
	// overlay lines at 0 95, is 102 11 in dimensions
	private final int m_maxTemperature;
	private int m_temperature = 0;
	private int m_pixelsToDrawOverCount = 0;
	
	
	public TemperatureWidget(int toX, int toY, int maxTemperature) 
	{
		super(toX, toY, WIDTH, HEIGHT, WIDGET_THINGS);
		this.m_maxTemperature = maxTemperature;
		this.updateTooltip();
	} // end constructor()
	
	
	

	public int getMaxTemperature()
	{
		return this.m_maxTemperature;
	} // end getCapacity()
	
	
	
	public void setTemperature(int amount) 
	{
		this.m_temperature = Math.min(amount, this.m_maxTemperature);
		float temperaturePercentageOfMax = (this.m_maxTemperature == 0) ? 0 : (((float)this.m_temperature) / ((float)this.m_maxTemperature));
		this.m_pixelsToDrawOverCount = (int)(((float)WIDTH) * temperaturePercentageOfMax);
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void updateTooltip() 
	{
		this.setTooltip(Tooltip.create(Component.literal(("(" + (this.m_temperature + "K/" + this.m_maxTemperature)+ "K)"))));
	} // end updateTooltip()
	

	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		graphics.blit(WIDGET_THINGS, this.getX(), this.getY(), 0, 84, WIDTH, HEIGHT);
		if(this.m_temperature > 0) 
		{			
			graphics.blit(WIDGET_THINGS, this.getX(), this.getY(), 0, 95, this.m_pixelsToDrawOverCount, HEIGHT);
		}		
	} // end renderWidget()
	
} // end class