package com.gsr.gsr_yatm.block.device.crafting.still;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.widget.HorizontalStoredFluidWidget;
import com.gsr.gsr_yatm.gui.widget.VerticalStoredFluidWidget;
import com.gsr.gsr_yatm.gui.widget.VerticalTemperatureWidget;

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
		
		float cP = this.menu.craftProgress();
		if(cP > 0f) 
		{
			// main branch to 41 57, s59(scl) 3, from 176 22
			// down branch visually start at 94 60, 25dep. 
			// down branch to 90 60, s11 25(scl), from 225 25
			// up branch arguably of 18dep, start when ready shouldn't cause excess acceleration
			// up branch to 94 39, from bottom default to 3, s11 21(scl), from 229 4
			
			// 81 or 80 path length
			int pTD = (int)(((float)77/*81*/) * cP);
			if(pTD > 0) 
			{
				int mBTD = Math.min(59, pTD);
				graphics.blit(StillScreen.BACKGROUND, this.leftPos + 41, this.topPos + 57, 176, 22, mBTD, 3);
				if(mBTD > 54) 
				{
					int dBTD = Math.min(25, pTD - 54);
					graphics.blit(StillScreen.BACKGROUND, this.leftPos + 90, this.topPos + 60, 225, 25, 11, dBTD);
				}
				if(mBTD == 59) 
				{
					int uBTD = Math.min(18, pTD - 59);
					graphics.blit(StillScreen.BACKGROUND, this.leftPos + 94, this.topPos + 39 + (18 - uBTD), 
							229, 4 + (18 - uBTD),  // from
							11, 3 + uBTD);
				}
			}
		}
		
		
		// burn square is 14x14, at 177 0
		// draw it to 80 70
		float bP = this.menu.burnProgress();
		if (bP > 0f)
		{
			int renderDownSet = 14 - ((int) (14 * bP));
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 63, (this.topPos + 70) + renderDownSet, 176, 0 + renderDownSet, 14, 14 - renderDownSet);
		}



		float iFP = this.menu.fillInputTankTransferProgress();
		if (iFP > 0f)
		{
			int pTD = (int)(6f * iFP);
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 25, (this.topPos + 79) + (6 - pTD), 176, 25 + (6 - pTD), 7, pTD);
		}

		float iDP = this.menu.drainInputTankTransferProgress();
		if (iDP > 0f)
		{
			float hP = iDP >= .36f ? 1f : (iDP / .36f);
			float vP = iDP < .36f ? 0f : (iDP - .36f) / .64f;
			int renderRightSet = 9 - ((int) (9f * hP));
			graphics.blit(StillScreen.BACKGROUND, (this.leftPos + 41), this.topPos + 65, 184, 25, 9 - renderRightSet, 4);
			int renderDownSet = 16 - ((int) (16f * vP));
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 44, this.topPos + 69, 187, 29, 9, 16 - renderDownSet);
		}


		
		
		float rDP = this.menu.drainRemainderTankTransferProgress();
		if (rDP > 0f)
		{
			int pTD = (int)(6f * rDP);
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 144, this.topPos + 90, 176, 14, pTD, 7);
		}
		 
		float dDP = this.menu.drainDistillateTankTransferProgress();
		if (dDP > 0f)
		{
			int pTD = (int)(6f * dDP);
			graphics.blit(StillScreen.BACKGROUND, this.leftPos + 144, this.topPos + 24, 176, 14, pTD, 7);
		}
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