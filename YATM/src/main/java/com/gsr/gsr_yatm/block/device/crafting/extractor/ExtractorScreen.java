package com.gsr.gsr_yatm.block.device.crafting.extractor;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.VerticalCurrentWidget;
import com.gsr.gsr_yatm.gui.VerticalStoredFluidWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorScreen extends AbstractContainerScreen<ExtractorMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/extractor.png");
	
	private VerticalCurrentWidget m_currentWidget;
	private VerticalStoredFluidWidget m_fluidTankWidget;
	
	
	
	public ExtractorScreen(ExtractorMenu menu, Inventory inventory, Component title)
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
		this.setResultTankWidget();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateCurrentWidget();
		this.updateResultTankWidget();
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		float cP = this.menu.craftProgress();
		if(cP > 0f) 
		{
			// from 176 0 to 80 63 size 16 8
			int horizontalWidth = (int)(16f * cP);
			graphics.blit(BACKGROUND, this.leftPos + 80, this.topPos + 63, 176, 0, horizontalWidth, 8);
			
			// if past threshold of 6 pixels along, and remainder is to exist, start to draw down arrow
			if(horizontalWidth > 6 && this.menu.recipeHasRemainder()) 
			{
				// from 176 8 to 84 68 size 8 17
				float startingAtPercentage = .375f;
				float normalizedLocalPercentage = ((cP - (startingAtPercentage)) / (1f - startingAtPercentage));
				graphics.blit(BACKGROUND, this.leftPos + 84, this.topPos + 68, 176, 8, 8, (int)(17f * normalizedLocalPercentage));
			}
		}

		float dP = this.menu.resultTankDrainProgress();
		if(dP > 0) 
		{
			graphics.blit(BACKGROUND, this.leftPos + 102, this.topPos + 79, 176, 25, 8, (int)(6f * dP));
		}
		
	} // end renderBg()

	
	
	public void updateResultTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_fluidTankWidget == null || this.menu.getFluidCapacity() != this.m_fluidTankWidget.getCapacity()) 
		{
			this.setResultTankWidget();
		}
		if(this.m_fluidTankWidget.getFluid() != this.menu.getFluid()) 
		{
			this.m_fluidTankWidget.setStoredFluid(this.menu.getFluid());
		}
		this.m_fluidTankWidget.setStoredAmount(this.menu.getFluidAmount());
	} // end updateResultTankWidget()
	
	public void setResultTankWidget() 
	{
		if(this.m_fluidTankWidget != null) 
		{			
			this.removeWidget(this.m_fluidTankWidget);
		}
		this.m_fluidTankWidget = new VerticalStoredFluidWidget(this.leftPos + 97, this.topPos + 20, this.menu.getFluidCapacity(), this.menu.getFluid());
		this.addRenderableWidget(this.m_fluidTankWidget);
	} // end setWidget()
	
	
	
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
	
} // end class