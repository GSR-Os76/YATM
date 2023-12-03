package com.gsr.gsr_yatm.block.device.crystallizer;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.VerticalCurrentWidget;
import com.gsr.gsr_yatm.gui.VerticalStoredFluidWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrystallizerScreen extends AbstractContainerScreen<CrystallizerMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/crystallizer.png");
	
	private VerticalStoredFluidWidget m_inputTankWidget;
	private VerticalCurrentWidget m_currentWidget;
	
	
	
	public CrystallizerScreen(CrystallizerMenu menu, Inventory inventory, Component title)
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
		this.setCurrentWidget();
		this.setInputTankWidget();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateCurrentWidget();
		this.updateInputTankWidget();		
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		float cP = this.menu.getCraftProgress();
		if(cP > 0) 
		{
			int pixels = 27 + 7 + 15;
			int firstStagePixelsEnd = 27;
			int secondStagePixelsEnd = firstStagePixelsEnd + 7;
			
			int pixelsToDraw = ((int) (pixels * cP));
			int firstStagePixelsToDraw = pixelsToDraw > firstStagePixelsEnd ? firstStagePixelsEnd : pixelsToDraw;
			int secondStagePixelsToDraw = 
					pixelsToDraw <= firstStagePixelsEnd 
					? 0 
					: pixelsToDraw > secondStagePixelsEnd 
						? secondStagePixelsEnd  - firstStagePixelsEnd
						: pixelsToDraw - firstStagePixelsEnd;

			if(pixelsToDraw > 0) 
			{
				graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos + (86 + (firstStagePixelsEnd - firstStagePixelsToDraw)), this.topPos + 38, 176 + (firstStagePixelsEnd - firstStagePixelsToDraw), 26, firstStagePixelsToDraw, 4);
			}
			if (pixelsToDraw > firstStagePixelsEnd) 
			{
				graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos + 84, this.topPos + 42, 176, 30, 8, secondStagePixelsToDraw);
			}
			if(pixelsToDraw > secondStagePixelsEnd)
			{
				graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos + 84, this.topPos + 70, 176, 37, 8, pixelsToDraw - secondStagePixelsEnd);
			}			
		}
		
		float iFP = this.menu.getInputTankFillProgress();
		if (iFP > 0)
		{
			// 176, 14. 9x8 to 11, 78
			int pixelsToDraw = ((int) (6f * iFP));
			graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos + 119, this.topPos + (79 + (6 - pixelsToDraw)), 176, 0 + (6 - pixelsToDraw), 9, pixelsToDraw);
		}

		float iDP = this.menu.getInputTankDrainProgress();
		if (iDP > 0)
		{
			float hP = iDP >= .36f ? 1f : (iDP / .36f);
			float vP = iDP < .36f ? 0f : (iDP - .36f) / .64f;
			graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos + 135, this.topPos + 65, 176, 6, ((int) (9f * hP)), 4);
			graphics.blit(CrystallizerScreen.BACKGROUND, this.leftPos + 138, this.topPos + 69, 176, 10, 8, ((int) (16f * vP)));
		}
	} // end renderBg
	
	
	public void updateCurrentWidget() 
	{
		if(this.m_currentWidget == null || this.menu.getCurrentCapacity() != this.m_currentWidget.getCapacity()) 
		{
			this.setCurrentWidget();
		}

		this.m_currentWidget.setStored(this.menu.getCurrentStored());
	} // end updateCurrentWidget()
	
	public void setCurrentWidget() 
	{
		if(this.m_currentWidget != null) 
		{			
			this.removeWidget(this.m_currentWidget);
		}
		
		this.m_currentWidget = new VerticalCurrentWidget(this.leftPos + 7, this.topPos + 20, this.getMenu().getCurrentCapacity());
		this.addRenderableWidget(this.m_currentWidget);
	} // end setCurrentWidget()
	
	
	
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
	} // end updateResultTankWidget()
	
	public void setInputTankWidget() 
	{
		if(this.m_inputTankWidget != null) 
		{			
			this.removeWidget(this.m_inputTankWidget);
		}
		this.m_inputTankWidget = new VerticalStoredFluidWidget(this.leftPos + 115, this.topPos + 20, this.menu.getInputTankCapacity(), this.menu.getInputTankContents().getFluid());
		this.addRenderableWidget(this.m_inputTankWidget);
	} // end setWidget()

} // end class