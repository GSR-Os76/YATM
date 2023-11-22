package com.gsr.gsr_yatm.block.device.crucible;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.ITemperatureWidget;
import com.gsr.gsr_yatm.gui.VerticalStoredFluidWidget;
import com.gsr.gsr_yatm.gui.VerticalTemperatureWidget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrucibleScreen extends AbstractContainerScreen<CrucibleMenu>
{
	// TODO, everything
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/crucible.png");
	
	private VerticalStoredFluidWidget m_resultTankWidget;
	private ITemperatureWidget m_temperatureWidget;
	
	
	
	public CrucibleScreen(CrucibleMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
		
		int yDownShift = 36;
		this.imageHeight = 166 + yDownShift;
		this.inventoryLabelY = this.inventoryLabelY + yDownShift;
	} // end constructor
	
	
	
	@Override
	protected void init()
	{
		super.init();
		this.setResultTankWidget();
		this.setTemperatureWidget();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateResultTankWidget();
		this.updateTemperatureWidget();
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(CrucibleScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		float cP = this.menu.craftProgress();
		if(cP > 0) 
		{			
			graphics.blit(CrucibleScreen.BACKGROUND, this.leftPos + 63, this.topPos + 51, 176, 20, (int)(50f * cP), 14);	
		}
		
		float bP = this.menu.burnProgress();
		if (bP > 0)
		{
			int pixelsToDraw = ((int) (14f * bP));
			graphics.blit(CrucibleScreen.BACKGROUND, this.leftPos + 44, this.topPos + (70 + (14 - pixelsToDraw)), 176, 0 + (14 - pixelsToDraw), 14, pixelsToDraw);
		}

		float rDP = this.menu.resultTankDrainProgress();
		if (rDP > 0)
		{
			graphics.blit(CrucibleScreen.BACKGROUND, this.leftPos + 120, this.topPos + 79, 176, 14, 8, ((int) (6f * rDP)));
		}
	} // end renderBg
	
	
	
	public void updateResultTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_resultTankWidget == null || this.menu.getFluidCapacity() != this.m_resultTankWidget.getCapacity()) 
		{
			this.setResultTankWidget();
		}
		if(this.m_resultTankWidget.getFluid() != this.menu.getFluid()) 
		{
			this.m_resultTankWidget.setStoredFluid(this.menu.getFluid());
		}
		this.m_resultTankWidget.setStoredAmount(this.menu.getFluidAmount());
	} // end updateResultTankWidget()
	
	public void setResultTankWidget() 
	{
		if(this.m_resultTankWidget != null) 
		{			
			this.removeWidget(this.m_resultTankWidget);
		}
		this.m_resultTankWidget = new VerticalStoredFluidWidget(this.leftPos + 115, this.topPos + 20, this.menu.getFluidCapacity(), this.menu.getFluid());
		this.addRenderableWidget(this.m_resultTankWidget);
	} // end setResultTankWidget()

	
	
	public void updateTemperatureWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_temperatureWidget == null || this.menu.getMaxTemperature() != this.m_temperatureWidget.getMaxTemperature()) 
		{
			this.setTemperatureWidget();
		}

		this.m_temperatureWidget.setTemperature(this.menu.getTemperature());
	} // end updateTemperatureWidget()
	
	public void setTemperatureWidget() 
	{
		if(this.m_temperatureWidget != null) 
		{			
			this.removeWidget(this.m_temperatureWidget.getThis());
		}
		
		this.m_temperatureWidget = new VerticalTemperatureWidget(this.leftPos + 20, this.topPos + 20, this.menu.getMaxTemperature());
		this.addRenderableWidget(this.m_temperatureWidget.getThis());
	} // end setTemperatureWidget()
} // end class