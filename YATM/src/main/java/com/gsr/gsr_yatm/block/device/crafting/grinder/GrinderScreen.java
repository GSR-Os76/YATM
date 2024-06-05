package com.gsr.gsr_yatm.block.device.crafting.grinder;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.widget.HorizontalCurrentWidget;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GrinderScreen extends AbstractContainerScreen<GrinderMenu>
{
	public static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/grinder.png");

	private HorizontalCurrentWidget m_currentWidget;
	
	
	
	public GrinderScreen(GrinderMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
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
		RenderSystem.setShaderTexture(0, GrinderScreen.BACKGROUND);
		graphics.blit(GrinderScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		float cP = this.menu.getCraftProgress();
		if(cP > 0) 
		{
			graphics.blit(GrinderScreen.BACKGROUND, this.leftPos + 74, this.topPos + 29, 176, 0, (int)(28f * cP), 16);
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

		this.m_currentWidget = new HorizontalCurrentWidget(this.leftPos + 43, this.topPos + 50, this.getMenu().getCurrentCapacity());
		this.addRenderableWidget(this.m_currentWidget);
	} // end setCurrentWidget()

} // end class