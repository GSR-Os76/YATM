package com.gsr.gsr_yatm.block.device.crafting.bioreactor;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.widget.VerticalCurrentWidget;
import com.gsr.gsr_yatm.gui.widget.VerticalStoredFluidWidget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BioreactorScreen extends AbstractContainerScreen<BioreactorMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/bioreactor.png");

	private VerticalStoredFluidWidget m_resultTankWidget;
	private VerticalCurrentWidget m_currentWidget;
	

	
	public BioreactorScreen(BioreactorMenu menu, Inventory inventory, Component title)
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
		graphics.blit(BioreactorScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		float cP = this.menu.getCraftProgress();
		if(cP > 0f) 
		{
			// from 176 0 to 80 63 size 16 8
			int horizontalWidth = (int)(16f * cP);
			graphics.blit(BioreactorScreen.BACKGROUND, this.leftPos + 80, this.topPos + 63, 176, 0, horizontalWidth, 8);
		}

		float dP = this.menu.getResultTankDrainProgress();
		if(dP > 0) 
		{
			graphics.blit(BioreactorScreen.BACKGROUND,  this.leftPos + 102, this.topPos + 79, 176, 8, 8, (int)(6f * dP));
		}
		
	} // end renderBg()

	
	
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
	
	
	
	public void updateResultTankWidget() 
	{
		if(this.m_resultTankWidget == null || this.menu.getResultTankCapacity() != this.m_resultTankWidget.getCapacity()) 
		{
			this.setResultTankWidget();
		}
		if(this.m_resultTankWidget.getFluid() != this.menu.getResultTankContents().getFluid()) 
		{
			this.m_resultTankWidget.setStoredFluid(this.menu.getResultTankContents().getFluid());
		}
		this.m_resultTankWidget.setStoredAmount(this.menu.getResultTankContents().getAmount());
	} // end updateResultTankWidget()
	
	public void setResultTankWidget() 
	{
		if(this.m_resultTankWidget != null) 
		{			
			this.removeWidget(this.m_resultTankWidget);
		}

		this.m_resultTankWidget = new VerticalStoredFluidWidget(this.leftPos + 97, this.topPos + 20, this.menu.getResultTankCapacity(), this.menu.getResultTankContents().getFluid());//= new StoredFluidWidget(this.leftPos + 7, this.topPos + 20, this.menu.getResultTankCapacity(), this.menu.getResultTankContents().getFluid());
		this.addRenderableWidget(this.m_resultTankWidget);
	} // end setResultTankWidget()
	
} // end class