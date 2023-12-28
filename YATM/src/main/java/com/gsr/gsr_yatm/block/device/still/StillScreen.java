package com.gsr.gsr_yatm.block.device.still;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.VerticalStoredFluidWidget;
import com.gsr.gsr_yatm.gui.VerticalTemperatureWidget;
import com.gsr.gsr_yatm.gui.HorizontalStoredFluidWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StillScreen extends AbstractContainerScreen<StillMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/still.png");

	private VerticalStoredFluidWidget m_inputTankWidget;
	private HorizontalStoredFluidWidget m_remainderTankWidget;
	private HorizontalStoredFluidWidget m_distillateTankWidget;
	private VerticalTemperatureWidget/*ITemperatureWidget*/ m_temperatureWidget;

	
	
	public StillScreen(StillMenu boilerMenu, Inventory inventory, Component titleComponentMaybe)
	{
		super(boilerMenu, inventory, titleComponentMaybe);

		this.imageHeight = 202;
		this.inventoryLabelY = this.inventoryLabelY + 36;
	} // end constructor

	
	
	@Override
	protected void init()
	{
		super.init();
		this.setInputTankWidget();
		this.setRemainderTankWidget();
		this.setDistillateTankWidget();
		this.setTemperatureWidget();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateInputTankWidget();
		this.updateRemainderTankWidget();
		this.updateDistillateTankWidget();
		this.updateTemperatureWidget();
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(StillScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		float bP = this.menu.craftProgress();
		if(bP > 0) 
		{
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 37, this.topPos + 23, 0, 202, (int)(102f * bP), 16);
		}
		/*
		
		// burn square is 14x14, at 177 0
		// draw it to 80 70
		float burnFractionRemaining = this.menu.burnProgress();
		if (burnFractionRemaining > 0f)
		{
			int renderDownSet = 14 - ((int) (14 * burnFractionRemaining));
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 80, (this.topPos + 70) + renderDownSet, 176, 0 + renderDownSet, 14, 14 - renderDownSet);
		}



		// draw input tank fill progress
		float iFP = this.menu.fillInputTankTransferProgress();
		if (iFP > 0)
		{
			// 176, 14. 9x8 to 11, 78
			int renderDownSet = 8 - ((int) (8f * iFP));
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 11, (this.topPos + 78) + renderDownSet, 176, 14 + renderDownSet, 9, 8 - renderDownSet);
		}

		// draw input tank drain progress
		float iDP = this.menu.drainInputTankTransferProgress();
		if (iDP > 0)
		{
			// 186, 14. 9x4 to 27, 65
			// 189, 18. 8x16 to 29, 69
			// 25 steps along, first line's 9, next is 16
			float hP = iDP >= .36f ? 1f : (iDP / .36f);
			float vP = iDP < .36f ? 0f : (iDP - .36f) / .64f;
			int renderRightSet = 9 - ((int) (9f * hP));
			graphics.blit(StillScreen.BACKGROUND, (this.leftPos + 27), this.topPos + 65, 185, 14, 9 - renderRightSet, 4);
			int renderDownSet = 16 - ((int) (16f * vP));
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 29, this.topPos + 69, 188, 18, 9, 16 - renderDownSet);
		}

		// draw result tank drain progress
		float rDP = this.menu.drainResultTankTransferProgress();
		if (rDP > 0)
		{
			// 197, 14. 9x8 to 155, 78
			int renderDownSet = 8 - ((int) (8f * rDP));
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 155, this.topPos + 78, 197, 14, 9, 8 - renderDownSet);
		}
		 */
	} // end renderBg

	
	
	
	public void updateInputTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_inputTankWidget == null || this.menu.getInputTankCapacity() != this.m_inputTankWidget.getCapacity()) 
		{
			this.setInputTankWidget();
		}
		if(this.m_inputTankWidget.getFluid() != this.menu.getInputTankContents().getFluid()) 
		{
			this.m_inputTankWidget.setStoredFluid(this.menu.getInputTankContents().getFluid());
		}
		this.m_inputTankWidget.setStoredAmount(this.menu.getInputTankContents().getAmount());
	} // end updateInputTankWidget()
	
	public void setInputTankWidget() 
	{
		if(this.m_inputTankWidget != null) 
		{			
			this.removeWidget(this.m_inputTankWidget);
		}
		this.m_inputTankWidget = new VerticalStoredFluidWidget(this.leftPos + 21, this.topPos + 20, this.menu.getInputTankCapacity(), this.menu.getInputTankContents().getFluid());
		this.addRenderableWidget(this.m_inputTankWidget);
	} // end setInputTankWidget()

	
	
	public void updateRemainderTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_remainderTankWidget == null || this.menu.getRemainderTankCapacity() != this.m_remainderTankWidget.getCapacity()) 
		{
			this.setRemainderTankWidget();
		}
		if(this.m_remainderTankWidget.getFluid() != this.menu.getRemainderTankContents().getFluid()) 
		{
			this.m_remainderTankWidget.setStoredFluid(this.menu.getRemainderTankContents().getFluid());
		}
		this.m_remainderTankWidget.setStoredAmount(this.menu.getRemainderTankContents().getAmount());
	} // end updateRemainderTankWidget()
	
	public void setRemainderTankWidget() 
	{
		if(this.m_remainderTankWidget != null) 
		{			
			this.removeWidget(this.m_remainderTankWidget);
		}
		
		this.m_remainderTankWidget = new HorizontalStoredFluidWidget(this.leftPos + 85, this.topPos + 86, this.menu.getRemainderTankCapacity(), this.menu.getRemainderTankContents().getFluid());
		this.addRenderableWidget(this.m_remainderTankWidget);
	} // end setRemainderTankWidget()
	
	public void updateDistillateTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_distillateTankWidget == null || this.menu.getDistillateTankCapacity() != this.m_distillateTankWidget.getCapacity()) 
		{
			this.setDistillateTankWidget();
		}
		if(this.m_distillateTankWidget.getFluid() != this.menu.getDistillateTankContents().getFluid()) 
		{
			this.m_distillateTankWidget.setStoredFluid(this.menu.getDistillateTankContents().getFluid());
		}
		this.m_distillateTankWidget.setStoredAmount(this.menu.getDistillateTankContents().getAmount());
	} // end updateDistillateTankWidget()
	
	public void setDistillateTankWidget() 
	{
		if(this.m_distillateTankWidget != null) 
		{			
			this.removeWidget(this.m_distillateTankWidget);
		}
		
		this.m_distillateTankWidget = new HorizontalStoredFluidWidget(this.leftPos + 85, this.topPos + 20, this.menu.getDistillateTankCapacity(), this.menu.getDistillateTankContents().getFluid());
		this.addRenderableWidget(this.m_distillateTankWidget);
	} // end setRemainderTankWidget()
	
	
	
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
			this.removeWidget(this.m_temperatureWidget);
		}

		this.m_temperatureWidget = new VerticalTemperatureWidget(this.leftPos + 7, this.topPos + 20, this.menu.getMaxTemperature());
		this.addRenderableWidget(this.m_temperatureWidget);
	} // end setTemperatureWidget()
	
} // end class