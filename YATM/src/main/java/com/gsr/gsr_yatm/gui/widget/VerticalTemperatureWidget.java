package com.gsr.gsr_yatm.gui.widget;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class VerticalTemperatureWidget extends FillBarWidget implements ITemperatureWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	public static final int WIDTH = 11;
	public static final int HEIGHT = 84;
	private final int m_maxTemperature;
	private int m_temperature = 0;
	private int m_pixelsToDrawOverCount = 0;
	
	
	public VerticalTemperatureWidget(int toX, int toY, int maxTemperature) 
	{
		super(toX, toY, VerticalTemperatureWidget.WIDTH, VerticalTemperatureWidget.HEIGHT, Component.empty());
		this.m_maxTemperature = maxTemperature;
		this.updateTooltip();
	} // end constructor()
	
	
	
	@Override
	public int getMaxTemperature()
	{
		return this.m_maxTemperature;
	} // end getCapacity()
	
	@Override
	public void setTemperature(int amount) 
	{
		this.m_temperature = Math.min(amount, this.m_maxTemperature);
		float temperaturePercentageOfMax = (this.m_maxTemperature == 0) ? 0 : (((float)this.m_temperature) / ((float)this.m_maxTemperature));
		this.m_pixelsToDrawOverCount = (int)(((float)VerticalTemperatureWidget.HEIGHT) * temperaturePercentageOfMax);
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void updateTooltip() 
	{
		this.setTooltip(Tooltip.create(Component.literal(("(" + (this.m_temperature + "K/" + this.m_maxTemperature)+ "K)"))));
	} // end updateTooltip()
	

	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		graphics.blit(VerticalTemperatureWidget.WIDGET_THINGS, this.getX(), this.getY(), 0, 142, VerticalTemperatureWidget.WIDTH, VerticalTemperatureWidget.HEIGHT);
		if(this.m_temperature > 0) 
		{			
			graphics.blit(VerticalTemperatureWidget.WIDGET_THINGS, this.getX(), this.getY() + (VerticalTemperatureWidget.HEIGHT - this.m_pixelsToDrawOverCount), 11, 142 + (VerticalTemperatureWidget.HEIGHT - this.m_pixelsToDrawOverCount), VerticalTemperatureWidget.WIDTH, this.m_pixelsToDrawOverCount);
		}		
	} // end renderWidget()
	
} // end class