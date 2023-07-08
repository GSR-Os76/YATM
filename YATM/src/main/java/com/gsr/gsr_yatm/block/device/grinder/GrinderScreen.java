package com.gsr.gsr_yatm.block.device.grinder;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

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
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		// this.menu.showTestNumber();
		super.renderBackground(poseStack);
		this.renderBg(poseStack, partialTick, mouseX, mouseY);
		super.render(poseStack, mouseX, mouseY, partialTick);
		this.renderTooltip(poseStack, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, BACKGROUND);
		blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		float gProg = this.menu.getGrindProgress();
		if(gProg > 0) 
		{
			blit(poseStack, this.leftPos + 74, this.topPos + 29, 176, 0, (int)(28f * gProg), 16);
		}
	} // end renderBg()

} // end class