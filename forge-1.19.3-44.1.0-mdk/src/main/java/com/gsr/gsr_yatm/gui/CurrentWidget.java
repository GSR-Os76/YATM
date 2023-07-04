package com.gsr.gsr_yatm.gui;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CurrentWidget extends ImageWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	public static final int WIDTH = 90;
	public static final int HEIGHT = 18;
	private final int m_capacity;
	private int m_stored = 0;
	private int m_pixelsToDrawOverCount = 0;
	
	
	public CurrentWidget(int toX, int toY, int capacity) 
	{
		super(toX, toY, WIDTH, HEIGHT, WIDGET_THINGS);
		this.m_capacity = capacity;
		this.updateTooltip();
	} // end constructor()
	
	
	

	public int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()
	
	
	
	public void setStored(int amount) 
	{
		this.m_stored = Math.min(amount, this.m_capacity);
		float storedPercentageOfMax = (this.m_capacity == 0) ? 0 : (((float)this.m_stored) / ((float)this.m_capacity));
		this.m_pixelsToDrawOverCount = (int)(((float)WIDTH) * storedPercentageOfMax);
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void updateTooltip() 
	{
		this.setTooltip(Tooltip.create(Component.literal(("(" + (this.m_stored + "cu/" + this.m_capacity) + "cu)"))));
	} // end updateTooltip()
	

	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		RenderSystem.setShaderTexture(0, WIDGET_THINGS);
		blit(poseStack, this.getX(), this.getY(), 0, 106, WIDTH, HEIGHT);
		if(this.m_stored > 0) 
		{			
			blit(poseStack, this.getX(), this.getY(), 0, 124, this.m_pixelsToDrawOverCount, HEIGHT);
		}		
	} // end renderWidget()
	
} // end class