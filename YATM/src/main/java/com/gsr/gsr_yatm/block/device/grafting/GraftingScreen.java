package com.gsr.gsr_yatm.block.device.grafting;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GraftingScreen extends AbstractContainerScreen<GraftingMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
	
	
	
	public GraftingScreen(GraftingMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
	} // end constructor
	
	
	
	@Override
	protected void init()
	{
		super.init();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(GraftingScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	} // end renderBg()
} // end class