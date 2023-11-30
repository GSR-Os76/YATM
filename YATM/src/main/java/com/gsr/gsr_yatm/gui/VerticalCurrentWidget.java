package com.gsr.gsr_yatm.gui;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class VerticalCurrentWidget extends ImageWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	public static final int WIDTH = 18;
	public static final int HEIGHT = 54;
	private final @NotNegative int m_capacity;
	private @NotNegative int m_stored = 0;
	private @NotNegative int m_pixelsToDrawOverCount = 0;
	
	
	public VerticalCurrentWidget(int toX, int toY, @NotNegative int capacity) 
	{
		super(toX, toY, VerticalCurrentWidget.WIDTH, VerticalCurrentWidget.HEIGHT, VerticalCurrentWidget.WIDGET_THINGS);
		this.m_capacity = Contract.notNegative(capacity);
		this.updateTooltip();
	} // end constructor()
	
	
	

	public @NotNegative int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()
	
	
	
	public void setStored(@NotNegative int amount) 
	{
		this.m_stored = Math.min(Contract.notNegative(amount), this.m_capacity);
		float storedPercentageOfMax = (this.m_capacity == 0) ? 0 : (((float)this.m_stored) / ((float)this.m_capacity));
		this.m_pixelsToDrawOverCount = (int)(((float)VerticalCurrentWidget.HEIGHT) * storedPercentageOfMax);
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void updateTooltip() 
	{
		this.setTooltip(Tooltip.create(Component.literal(("(" + (this.m_stored + "cu/" + this.m_capacity) + "cu)"))));
	} // end updateTooltip()
	

	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		graphics.blit(VerticalCurrentWidget.WIDGET_THINGS, this.getX(), this.getY(), 22, 142, VerticalCurrentWidget.WIDTH, VerticalCurrentWidget.HEIGHT);
		if(this.m_stored > 0) 
		{			
			graphics.blit(VerticalCurrentWidget.WIDGET_THINGS, this.getX(), this.getY() + (VerticalCurrentWidget.HEIGHT - this.m_pixelsToDrawOverCount), 40, 142 + (VerticalCurrentWidget.HEIGHT - this.m_pixelsToDrawOverCount), VerticalCurrentWidget.WIDTH, this.m_pixelsToDrawOverCount);
		}		
	} // end renderWidget()
	
} // end class