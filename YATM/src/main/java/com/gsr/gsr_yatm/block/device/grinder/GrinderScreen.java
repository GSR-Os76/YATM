package com.gsr.gsr_yatm.block.device.grinder;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GrinderScreen extends AbstractContainerScreen<GrinderMenu>
{
	public static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/grinder.png");

	
	
	public GrinderScreen(GrinderMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
	} // end constructor

	
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, BACKGROUND);
		graphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		float gProg = this.menu.getGrindProgress();
		if(gProg > 0) 
		{
			graphics.blit(BACKGROUND, this.leftPos + 74, this.topPos + 29, 176, 0, (int)(28f * gProg), 16);
		}
	} // end renderBg()

} // end class