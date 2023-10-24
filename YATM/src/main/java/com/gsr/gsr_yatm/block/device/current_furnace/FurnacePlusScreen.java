package com.gsr.gsr_yatm.block.device.current_furnace;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.HorizontalTemperatureWidget;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FurnacePlusScreen extends AbstractContainerScreen<FurnacePlusMenu>
{
	// TODO, this
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/boiler.png");

	private HorizontalTemperatureWidget m_temperatureWidget;

	
	
	public FurnacePlusScreen(FurnacePlusMenu menu, Inventory inventory, Component titleComponentMaybe)
	{
		super(menu, inventory, titleComponentMaybe);

		int newYDownShift = 36;
		this.imageHeight = 166 + newYDownShift;
		this.inventoryLabelY = this.inventoryLabelY + newYDownShift;
	} // end constructor
	
	
	
	@Override
	protected void init()
	{
		super.init();
		this.setTemperatureWidget();		
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateTemperatureWidget();
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
		;
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, BACKGROUND);
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		
		float bP = this.menu.craftProgress();
		if(bP > 0) 
		{
			graphics.blit(BACKGROUND, this.leftPos + 37, this.topPos + 23, 0, 202, (int)(102f * bP), 16);
		}
		
		
		// burn square is 14x14, at 177 0
		// draw it to 80 70
		float burnFractionRemaining = this.menu.burnRemaining();
		if (burnFractionRemaining > 0f)
		{
			int renderDownSet = 14 - ((int) (14 * burnFractionRemaining));
			graphics.blit(BACKGROUND, this.leftPos + 80, (this.topPos + 70) + renderDownSet, 176, 0 + renderDownSet, 14, 14 - renderDownSet);
		}

	} // end renderBg
	
	
	
	public void updateTemperatureWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_temperatureWidget == null || this.menu.getMaxTemperature() != this.m_temperatureWidget.getMaxTemperature()) 
		{
			this.setTemperatureWidget();
		}

		this.m_temperatureWidget.setTemperature(this.menu.getTemperature());
	} // end updateResultTankWidget()
	
	public void setTemperatureWidget() 
	{
		if(this.m_temperatureWidget != null) 
		{			
			this.removeWidget(this.m_temperatureWidget);
		}
		
		this.m_temperatureWidget = new HorizontalTemperatureWidget(this.leftPos + 37, this.topPos + 43, this.menu.getMaxTemperature());
		this.addRenderableWidget(this.m_temperatureWidget);
	} // end setWidget()
} // end class