package com.gsr.gsr_yatm.block.device.tank;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.IScreenPositionHandler;
import com.gsr.gsr_yatm.gui.IWidgetHandler;
import com.gsr.gsr_yatm.gui.behavior.fluid.vertical.VerticalFluidWidgetBehavior;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TankScreen extends AbstractContainerScreen<TankMenu> implements IScreenPositionHandler, IWidgetHandler
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/tank.png");
	
	private final @NotNull VerticalFluidWidgetBehavior m_resultTankWidgetHandler;
	
	
	
	public TankScreen(@NotNull TankMenu menu, Inventory inventory, Component title)
	{
		super(Objects.requireNonNull(menu), inventory, title);
		
		int yShift = 9;
		this.imageHeight = 166 + yShift;
		this.inventoryLabelY = this.inventoryLabelY + yShift;
		this.m_resultTankWidgetHandler = new VerticalFluidWidgetBehavior(this, this, this.menu.getTankContentsGetter(), 79, 20);
	} // end constructor
	
	
	
	@Override
	protected void init()
	{
		super.init();
		this.m_resultTankWidgetHandler.init();
	} // end init()

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(graphics, mouseX, mouseY, partialTick);
		this.renderBg(graphics, partialTick, mouseX, mouseY);
		this.m_resultTankWidgetHandler.render(graphics, mouseX, mouseY, partialTick);
		super.render(graphics, mouseX, mouseY, partialTick);
		this.renderTooltip(graphics, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
	{
		graphics.blit(TankScreen.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	} // end renderBg



	@Override
	public @NotNegative int getTopPos()
	{
		return this.topPos;
	} // end getTopPos()

	@Override
	public @NotNegative int getLeftPos()
	{
		return this.leftPos;
	} // end getLeftPos()
	
	public void removeWidget(@NotNull GuiEventListener widget) 
	{
		super.removeWidget(widget);
	} // end removeWidget()

	public <T extends GuiEventListener & Renderable & NarratableEntry> @NotNull T addRenderableWidget(@NotNull T widget) 
	{
		return super.addRenderableWidget(widget);
	} // end addRenderableWidget()

} // end class