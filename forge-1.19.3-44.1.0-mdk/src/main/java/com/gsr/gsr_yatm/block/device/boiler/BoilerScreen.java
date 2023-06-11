package com.gsr.gsr_yatm.block.device.boiler;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.FluidStack;

public class BoilerScreen extends AbstractContainerScreen<BoilerMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/boiler.png");
	// 18 by 57
	// private static final ResourceLocation TANK = new
	// ResourceLocation(YetAnotherTechMod.MODID,
	// "textures/gui/container/tank_widget.png");

	private BoilerMenu m_menu;

	// add face configuration, and

	public BoilerScreen(BoilerMenu boilerMenu, Inventory inventory, Component titleComponentMaybe)
	{
		super(boilerMenu, inventory, titleComponentMaybe);

		this.m_menu = boilerMenu;

		int newYDownShift = 36;
		this.imageHeight = 166 + newYDownShift;
		this.inventoryLabelY = this.inventoryLabelY + newYDownShift;
	} // end constructor

	
	
	@Override
	protected void init()
	{
		// TODO Auto-generated method stub
		super.init();

		// this.addRenderableWidget(Button);
		// ImageButton faceConfigurationButton;
		// ImageButton redstoneConfigurationButton;
		// AbstractFurnaceScreen l;

	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(poseStack);
		this.renderBg(poseStack, partialTick, mouseX, mouseY);
		super.render(poseStack, mouseX, mouseY, partialTick);
		this.renderTooltip(poseStack, mouseX, mouseY);
		;
	} // end render()

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, BACKGROUND);

		blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		// burn square is 14x14, at 177 0
		// draw it to 80 70
		float burnFractionRemaining = this.m_menu.burnFractionRemaining();
		if (burnFractionRemaining > 0f)
		{
			int renderDownSet = 14 - ((int) (14 * burnFractionRemaining));
			blit(poseStack, this.leftPos + 80, (this.topPos + 70) + renderDownSet, 176, 0 + renderDownSet, 14, 14 - renderDownSet);
		}

		FluidStack iTCs = this.m_menu.getInputTankContents();
		if (!iTCs.isEmpty())
		{
			// figure out how to get the texture from fluid
			RenderSystem.setShaderTexture(0, new ResourceLocation(""));
			// iTCs.getRawFluid().defaultFluidState()
			// ForgeHooksClient.getFluidSprites(new BlockAndTintGetter() {}, BlockPos.ZERO,
			// iTCs.getRawFluid().defaultFluidState());

			int renderDownSet = 51 - ((int) (51 * ((float) iTCs.getAmount() / (float) this.m_menu.getInputTankCapacity())));
			blit(poseStack, this.leftPos + 10, (this.topPos + 23) + renderDownSet, 0, renderDownSet, 12, 51 - renderDownSet);
			// render over fluid with the level indicators
			RenderSystem.setShaderTexture(0, BACKGROUND);
			blit(poseStack, this.leftPos + 10, this.topPos + 23, 176, 35, 12, 51);
		}

		FluidStack oTCs = this.m_menu.getOutputTankContents();
		if (!oTCs.isEmpty())
		{
			// figure out how to get the texture from fluid
			RenderSystem.setShaderTexture(0, new ResourceLocation(""));

			int renderDownSet = 51 - ((int) (51 * ((float) oTCs.getAmount() / (float) this.m_menu.getOutputTankCapacity())));
			blit(poseStack, this.leftPos + 154, (this.topPos + 23) + renderDownSet, 0, renderDownSet, 12, 51 - renderDownSet);

			RenderSystem.setShaderTexture(0, BACKGROUND);
			blit(poseStack, this.leftPos + 154, this.topPos + 23, 176, 35, 12, 51);
		}


		// draw input tank fill progress
		float iFP = this.menu.fillInputTankTransferProgress();
		if (iFP > 0)
		{
			// 176, 14. 9x8 to 11, 78
			int renderDownSet = 8 - ((int) (8f * iFP));
			blit(poseStack, this.leftPos + 11, (this.topPos + 78) + renderDownSet, 176, 14 + renderDownSet, 9, 8 - renderDownSet);
		}

		// draw input tank drain progress
		float iDP = this.menu.drainInputTankTransferProgress();
		if (iDP > 0)
		{
			// 186, 14. 9x4 to 27, 65
			// 189, 18. 8x16 to 29, 69
			// 25 steps along, first line's 9, next is 16
			float hP = iDP >= .36f ? 1f : (iDP / .36f);
			float vP = iDP < .36f ? 0f : (iDP - .36f) / .64f;
			int renderRightSet = 9 - ((int) (9f * hP));
			blit(poseStack, (this.leftPos + 27), this.topPos + 65, 185, 14, 9 - renderRightSet, 4);
			int renderDownSet = 16 - ((int) (16f * vP));
			blit(poseStack, this.leftPos + 29, this.topPos + 69, 188, 18, 9, 16 - renderDownSet);
		}

		// draw result tank drain progress
		float rDP = this.menu.drainResultTankTransferProgress();
		if (rDP > 0)
		{
			// 197, 14. 9x8 to 155, 78
			int renderDownSet = 8 - ((int) (8f * rDP));
			blit(poseStack, this.leftPos + 155, this.topPos + 78, 197, 14, 9, 8 - renderDownSet);
		}

	} // end renderBg



} // end class