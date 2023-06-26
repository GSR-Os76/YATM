package com.gsr.gsr_yatm.block.device.crystallizer;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.StoredFluidWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrystallizerScreen extends AbstractContainerScreen<CrystallizerMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/crystallizer.png");
	
	private StoredFluidWidget m_inputTankWidget;
	
	
	
	public CrystallizerScreen(CrystallizerMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
		
		int yDownShift = 36;
		this.imageHeight = 166 + yDownShift;
		this.inventoryLabelY = this.inventoryLabelY + yDownShift;
	} // end constructor
	
	
	
	@Override
	protected void init()
	{
		super.init();
		this.setInputTankWidget();
		
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(poseStack);
		this.renderBg(poseStack, partialTick, mouseX, mouseY);
		this.updateInputTankWidget();		
		super.render(poseStack, mouseX, mouseY, partialTick);
		this.renderTooltip(poseStack, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem._setShaderTexture(0, BACKGROUND);
		blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		float cP = this.menu.getCrystallizationProgress();
		if(cP > 0) 
		{
			// 27 to left
			// 7 to down
			// cut off point
			// 15 to down
			int pixels = 27 + 7 + 15;
			int firstStagePixelsEnd = 27;
			int secondStagePixelsEnd = firstStagePixelsEnd + 7;
			
			int pixelsToDraw = ((int) (pixels * cP));
			int firstStagePixelsToDraw = pixelsToDraw > firstStagePixelsEnd ? firstStagePixelsEnd : pixelsToDraw;
			int secondStagePixelsToDraw = 
					pixelsToDraw <= firstStagePixelsEnd 
					? 0 
					: pixelsToDraw > secondStagePixelsEnd 
						? secondStagePixelsEnd  - firstStagePixelsEnd
						: pixelsToDraw - firstStagePixelsEnd;
//			int thirdStagePixelsToDraw = pixelsToDraw <= secondStagePixelsEnd 
//					? 0 
//					: pixelsToDraw - secondStagePixelsEnd;
//			
			if(pixelsToDraw > 0) 
			{
				blit(poseStack, this.leftPos + (86 + (firstStagePixelsEnd - firstStagePixelsToDraw)), this.topPos + 38, 176 + (firstStagePixelsEnd - firstStagePixelsToDraw), 26, firstStagePixelsToDraw, 4);
			}
			if (pixelsToDraw > firstStagePixelsEnd) 
			{
				blit(poseStack, this.leftPos + 84, this.topPos + 42, 176, 30, 8, secondStagePixelsToDraw);
			}
			if(pixelsToDraw > secondStagePixelsEnd)
			{
				blit(poseStack, this.leftPos + 84, this.topPos + 70, 176, 37, 8, pixelsToDraw - secondStagePixelsEnd);
			}			
		}
		
		float iFP = this.menu.inputTankFillProgress();
		if (iFP > 0)
		{
			// 176, 14. 9x8 to 11, 78
			int pixelsToDraw = ((int) (6f * iFP));
			blit(poseStack, this.leftPos + 119, this.topPos + (79 + (6 - pixelsToDraw)), 176, 0 + (6 - pixelsToDraw), 9, pixelsToDraw);
		}

		float iDP = this.menu.inputTankDrainProgress();
		if (iDP > 0)
		{
			float hP = iDP >= .36f ? 1f : (iDP / .36f);
			float vP = iDP < .36f ? 0f : (iDP - .36f) / .64f;
			blit(poseStack, this.leftPos + 135, this.topPos + 65, 176, 6, ((int) (9f * hP)), 4);
			blit(poseStack, this.leftPos + 138, this.topPos + 69, 176, 10, 8, ((int) (16f * vP)));
		}
	} // end renderBg
	
	
	
	public void updateInputTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_inputTankWidget == null || this.menu.getFluidCapacity() != this.m_inputTankWidget.getCapacity()) 
		{
			this.setInputTankWidget();
		}
		if(this.m_inputTankWidget.getFluid() != this.menu.getFluid()) 
		{
			this.m_inputTankWidget.setStoredFluid(this.menu.getFluid());
		}
		this.m_inputTankWidget.setStoredAmount(this.menu.getFluidAmount());
	} // end updateResultTankWidget()
	
	public void setInputTankWidget() 
	{
		if(this.m_inputTankWidget != null) 
		{			
			this.removeWidget(this.m_inputTankWidget);
		}
		this.m_inputTankWidget = new StoredFluidWidget(this.leftPos + 115, this.topPos + 20, this.menu.getFluidCapacity(), this.menu.getFluid());
		this.addRenderableWidget(this.m_inputTankWidget);
	} // end setWidget()

} // end class

//if(pixelsToDraw > 0 && pixelsToDraw <= firstStagePixelsEnd) 
//{
//	blit(poseStack, this.leftPos + (86 + (firstStagePixelsEnd - pixelsToDraw)), this.topPos + 38, 176 + (firstStagePixelsEnd - pixelsToDraw), 26, pixelsToDraw, 4);
//}
//else if (pixelsToDraw <= secondStagePixelsEnd) 
//{
//	blit(poseStack, this.leftPos + 84, this.topPos + 42, 176, 30, 8, pixelsToDraw - firstStagePixelsEnd);
//}
//else 
//{
//	blit(poseStack, this.leftPos + 84, this.topPos + 70, 176, 37, 8, pixelsToDraw - secondStagePixelsEnd);
//}	