package com.gsr.gsr_yatm.item.tool;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.HorizontalCurrentWidget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoweredToolScreen extends AbstractContainerScreen<PoweredToolMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/powered_tool.png");
	
	private HorizontalCurrentWidget m_batteryWidget;
	
	
	
	public PoweredToolScreen(PoweredToolMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);		
	} // end constructor
	
	
	
	@Override
	protected void init()
	{
		super.init();
		this.setBatteryWidget();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.updateBatteryWidget();
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(PoweredToolScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	} // end renderBg()

	
	
	public void updateBatteryWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_batteryWidget == null || this.menu.getCurrentCapacity() != this.m_batteryWidget.getCapacity()) 
		{
			this.setBatteryWidget();
		}
		this.m_batteryWidget.setStored(this.menu.getCurrentStored());
	} // end updateResultTankWidget()
	
	public void setBatteryWidget() 
	{
		if(this.m_batteryWidget != null) 
		{			
			this.removeWidget(this.m_batteryWidget);
		}
		this.m_batteryWidget = new HorizontalCurrentWidget(this.leftPos + 43, this.topPos + 50, this.menu.getCurrentCapacity());
		this.addRenderableWidget(this.m_batteryWidget);
	} // end setWidget()
	
} // end class