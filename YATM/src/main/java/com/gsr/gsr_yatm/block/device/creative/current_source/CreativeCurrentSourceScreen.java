package com.gsr.gsr_yatm.block.device.creative.current_source;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CreativeCurrentSourceScreen extends AbstractContainerScreen<CreativeCurrentSourceMenu>
{
	public static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/creative_current_source.png");

	private @NotNull EditBox m_outputBox;
	
	
	public CreativeCurrentSourceScreen(CreativeCurrentSourceMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
	} // end constructor

	
	
	@Override
	protected void init()
	{
		super.init();
		// TODO, literal isn't working, and get alwats give 0
		// this.m_outputBox = new EditBox(minecraft.font, this.leftPos + 48, this.topPos + 26, 80, 12, Component.literal("" + menu.getOutput()));
		// this.m_outputBox.setResponder(this::tryUpdateOutput);
		// this.addRenderableWidget(this.m_outputBox);
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		// this.m_outputBox.render(graphics, mouseX, mouseY, partialTick);
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(CreativeCurrentSourceScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	} // end renderBg()

} // end class