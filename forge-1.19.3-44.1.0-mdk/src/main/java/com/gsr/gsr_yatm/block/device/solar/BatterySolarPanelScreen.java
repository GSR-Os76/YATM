package com.gsr.gsr_yatm.block.device.solar;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.CurrentWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BatterySolarPanelScreen extends AbstractContainerScreen<BatterySolarPanelMenu>
{
	public static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/battery_solar_panel.png");
	
	private CurrentWidget m_currentWidget;
	
	
	
	public BatterySolarPanelScreen(BatterySolarPanelMenu menu, Inventory inventory, Component title)
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
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(poseStack);
		this.renderBg(poseStack, partialTick, mouseX, mouseY);
		this.updateCurrentWidget();
		super.render(poseStack, mouseX, mouseY, partialTick);
		this.renderTooltip(poseStack, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, BACKGROUND);
		blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	} // end renderBg()

	
	
	public void updateCurrentWidget() 
	{
		if(this.m_currentWidget == null || this.menu.currentCapacity() != this.m_currentWidget.getCapacity()) 
		{
			this.setCurrentWidget();
		}

		this.m_currentWidget.setStored(this.menu.currentStored());
	} // end updateResultTankWidget()
	
	public void setCurrentWidget() 
	{
		if(this.m_currentWidget != null) 
		{			
			this.removeWidget(this.m_currentWidget);
		}
		
		this.m_currentWidget = new CurrentWidget(this.leftPos + 43, this.topPos + 21, this.getMenu().currentCapacity());
		this.addRenderableWidget(this.m_currentWidget);
	} // end setWidget()
	
} // end class