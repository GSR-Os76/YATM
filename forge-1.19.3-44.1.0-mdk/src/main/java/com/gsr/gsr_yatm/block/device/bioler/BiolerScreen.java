package com.gsr.gsr_yatm.block.device.bioler;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.StoredFluidWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BiolerScreen extends AbstractContainerScreen<BiolerMenu>
{
	// TODO, change or raname
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/extractor.png");

	private StoredFluidWidget m_resultTankWidget;
	
	

	public BiolerScreen(BiolerMenu menu, Inventory inventory, Component title)
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
		this.setResultTankWidget();
	} // end init()

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(poseStack);
		this.renderBg(poseStack, partialTick, mouseX, mouseY);
		this.updateResultTankWidget();
		super.render(poseStack, mouseX, mouseY, partialTick);
		this.renderTooltip(poseStack, mouseX, mouseY);
	} // end render()

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem._setShaderTexture(0, BACKGROUND);
		blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		float cP = this.menu.getCraftProgress();
		if(cP > 0f) 
		{
			// from 176 0 to 80 63 size 16 8
			int horizontalWidth = (int)(16f * cP);
			blit(poseStack, this.leftPos + 80, this.topPos + 63, 176, 0, horizontalWidth, 8);
			
			// if past threshold of 6 pixels along, and remainder is to exist, start to draw down arrow
			if(horizontalWidth > 6 && this.menu.recipeHasRemainder()) 
			{
				// from 176 8 to 84 68 size 8 17
				float startingAtPercentage = .375f;
				float normalizedLocalPercentage = ((cP - (startingAtPercentage)) / (1f - startingAtPercentage));
				blit(poseStack, this.leftPos + 84, this.topPos + 68, 176, 8, 8, (int)(17f * normalizedLocalPercentage));
			}
		}

		float dP = this.menu.getResultTankDrainProgress();
		if(dP > 0) 
		{
			blit(poseStack, this.leftPos + 102, this.topPos + 79, 176, 25, 8, (int)(6f * dP));
		}
		
	} // end renderBg()

	
	
	public void updateResultTankWidget() 
	{
		if(this.m_resultTankWidget == null || this.menu.getResultTankCapacity() != this.m_resultTankWidget.getCapacity()) 
		{
			this.setResultTankWidget();
		}
		if(this.m_resultTankWidget.getFluid() != this.menu.getResultTankContents().getFluid()) 
		{
			this.m_resultTankWidget.setStoredFluid(this.menu.getResultTankContents().getFluid());
		}
		this.m_resultTankWidget.setStoredAmount(this.menu.getResultTankContents().getAmount());
	} // end updateResultTankWidget()
	
	public void setResultTankWidget() 
	{
		if(this.m_resultTankWidget != null) 
		{			
			this.removeWidget(this.m_resultTankWidget);
		}
		this.m_resultTankWidget = new StoredFluidWidget(this.leftPos + 7, this.topPos + 20, this.menu.getResultTankCapacity(), this.menu.getResultTankContents().getFluid());
		this.addRenderableWidget(this.m_resultTankWidget);
	} // end setWidget()
	
} // end class