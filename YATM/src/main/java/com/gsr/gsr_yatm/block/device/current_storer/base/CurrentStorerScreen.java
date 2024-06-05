package com.gsr.gsr_yatm.block.device.current_storer.base;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.widget.VerticalCurrentWidget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CurrentStorerScreen extends AbstractContainerScreen<CurrentStorerMenu>
{
	public static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/current_storer.png");

	private VerticalCurrentWidget m_currentWidget;
	
	
	
	public CurrentStorerScreen(CurrentStorerMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
		int yShift = 9;
		this.imageHeight = 166 + yShift;
		this.inventoryLabelY = this.inventoryLabelY + yShift;
	} // end constructor


	
	@Override
	protected void init()
	{
		super.init();
		this.setCurrentWidget();
	} // end init()
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateCurrentWidget();
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(CurrentStorerScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

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

		this.m_currentWidget = new VerticalCurrentWidget(this.leftPos + 79, this.topPos + 22, this.getMenu().getCurrentCapacity());
		this.addRenderableWidget(this.m_currentWidget);
	} // end setCurrentWidget()

} // end class