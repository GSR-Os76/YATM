package com.gsr.gsr_yatm.gui;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class HorizontalCurrentWidget extends FillBarWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	public static final int WIDTH = 90;
	public static final int HEIGHT = 18;
	private final @NotNegative int m_capacity;
	private @NotNegative int m_stored = 0;
	private @NotNegative int m_pixelsToDrawOverCount = 0;
	
	
	public HorizontalCurrentWidget(int toX, int toY, @NotNegative int capacity) 
	{
		super(toX, toY, HorizontalCurrentWidget.WIDTH, HorizontalCurrentWidget.HEIGHT, Component.empty());
		this.m_capacity = Contract.notNegative(capacity);
		this.setUpdatedTooltip();
	} // end constructor()
	
	

	public @NotNegative int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()
	
	
	
	public void setStored(@NotNegative int amount) 
	{
		this.m_stored = Math.min(Contract.notNegative(amount), this.m_capacity);
		float storedPercentageOfMax = (this.m_capacity == 0) ? 0 : (((float)this.m_stored) / ((float)this.m_capacity));
		this.m_pixelsToDrawOverCount = (int)(((float)VerticalCurrentWidget.WIDTH) * storedPercentageOfMax);
		this.setUpdatedTooltip();
	} // end setStoredAmount()
	
	public void setUpdatedTooltip() 
	{
		this.setTooltip(Tooltip.create(Component.literal(("(" + (this.m_stored + "cu/" + this.m_capacity) + "cu)"))));
	} // end updateTooltip()
	

	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		graphics.blit(HorizontalCurrentWidget.WIDGET_THINGS, this.getX(), this.getY(), 0, 106, HorizontalCurrentWidget.WIDTH, HorizontalCurrentWidget.HEIGHT);
		if(this.m_stored > 0) 
		{			
			graphics.blit(HorizontalCurrentWidget.WIDGET_THINGS, this.getX(), this.getY(), 0, 124, this.m_pixelsToDrawOverCount, HorizontalCurrentWidget.HEIGHT);
		}		
	} // end renderWidget()
	
} // end class