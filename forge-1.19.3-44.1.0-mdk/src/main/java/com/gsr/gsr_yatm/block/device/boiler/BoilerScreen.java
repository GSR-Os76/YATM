package com.gsr.gsr_yatm.block.device.boiler;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.gui.StoredFluidWidget;
import com.gsr.gsr_yatm.gui.TemperatureWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BoilerScreen extends AbstractContainerScreen<BoilerMenu>
{
	private static final ResourceLocation BACKGROUND = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/container/boiler.png");

	private StoredFluidWidget m_inputTankWidget;
	private StoredFluidWidget m_resultTankWidget;
	private TemperatureWidget m_temperatureWidget;
	// add face configuration, and

	public BoilerScreen(BoilerMenu boilerMenu, Inventory inventory, Component titleComponentMaybe)
	{
		super(boilerMenu, inventory, titleComponentMaybe);

		int newYDownShift = 36;
		this.imageHeight = 166 + newYDownShift;
		this.inventoryLabelY = this.inventoryLabelY + newYDownShift;
		
//		this.menu.maximumTemperatureChanged((mt) -> 
//		{
//			YetAnotherTechMod.LOGGER.info("trying to set the max temperature to: " + mt);
//			BoilerScreen.this.setTemperatureWidget(new TemperatureWidget(BoilerScreen.this.leftPos + 37, BoilerScreen.this.topPos + 43, mt));
//		});
//		this.menu.temperatureChanged((t) -> 
//		{
//			YetAnotherTechMod.LOGGER.info("trying to set temperature to: " + t);
//			TemperatureWidget tw = BoilerScreen.this.getTemperatureWidget();
//			if(tw != null) 
//			{
//				tw.setTemperature(t);	
//			}
//		});
	} // end constructor

	// TODO, add marker for required heat, add a boil progress marker
	
	@Override
	protected void init()
	{
		super.init();
		this.setInputTankWidget();
		this.setResultTankWidget();
		this.setTemperatureWidget();
//		this.m_temperatureWidget = new TemperatureWidget(this.leftPos + 37, this.topPos + 43, this.menu.getMaxTemperature());
//		this.addRenderableWidget(this.m_temperatureWidget);
		//this.setTemperatureWidget();
		
	} // end init()

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		super.renderBackground(poseStack);
		this.renderBg(poseStack, partialTick, mouseX, mouseY);
		this.updateInputTankWidget();
		this.updateResultTankWidget();
		this.updateTemperatureWidget();
		super.render(poseStack, mouseX, mouseY, partialTick);
		this.renderTooltip(poseStack, mouseX, mouseY);
		;
	} // end render()

	@Override
	protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, BACKGROUND);

		blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		
		
		float bP = this.menu.boilProgress();
		if(bP > 0) 
		{
			blit(poseStack, this.leftPos + 37, this.topPos + 23, 0, 202, (int)(102f * bP), 16);
		}
		
		
		// burn square is 14x14, at 177 0
		// draw it to 80 70
		float burnFractionRemaining = this.menu.burnFractionRemaining();
		if (burnFractionRemaining > 0f)
		{
			int renderDownSet = 14 - ((int) (14 * burnFractionRemaining));
			blit(poseStack, this.leftPos + 80, (this.topPos + 70) + renderDownSet, 176, 0 + renderDownSet, 14, 14 - renderDownSet);
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

	
	
	
	public void updateInputTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_inputTankWidget == null || this.menu.getInputTankCapacity() != this.m_inputTankWidget.getCapacity()) 
		{
			this.setInputTankWidget();
		}
		if(this.m_inputTankWidget.getFluid() != this.menu.getInputTankContents().getFluid()) 
		{
			this.m_inputTankWidget.setStoredFluid(this.menu.getInputTankContents().getFluid());
		}
		this.m_inputTankWidget.setStoredAmount(this.menu.getInputTankContents().getAmount());
	} // end updateResultTankWidget()
	
	public void setInputTankWidget() 
	{
		if(this.m_inputTankWidget != null) 
		{			
			this.removeWidget(this.m_inputTankWidget);
		}
		this.m_inputTankWidget = new StoredFluidWidget(this.leftPos + 7, this.topPos + 20, this.menu.getInputTankCapacity(), this.menu.getInputTankContents().getFluid());
		this.addRenderableWidget(this.m_inputTankWidget);
	} // end setWidget()

	
	
	public void updateResultTankWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_resultTankWidget == null || this.menu.getOutputTankCapacity() != this.m_resultTankWidget.getCapacity()) 
		{
			this.setResultTankWidget();
		}
		if(this.m_resultTankWidget.getFluid() != this.menu.getOutputTankContents().getFluid()) 
		{
			this.m_resultTankWidget.setStoredFluid(this.menu.getOutputTankContents().getFluid());
		}
		this.m_resultTankWidget.setStoredAmount(this.menu.getOutputTankContents().getAmount());
	} // end updateResultTankWidget()
	
	public void setResultTankWidget() 
	{
		if(this.m_resultTankWidget != null) 
		{			
			this.removeWidget(this.m_resultTankWidget);
		}
		
		this.m_resultTankWidget = new StoredFluidWidget(this.leftPos + 151, this.topPos + 20, this.menu.getOutputTankCapacity(), this.menu.getOutputTankContents().getFluid());
		this.addRenderableWidget(this.m_resultTankWidget);
	} // end setWidget()
	
	
	
	public void updateTemperatureWidget() 
	{
		// TODO, ideally we could have the menu tell us when that value has changed, rather than constantly having to recheck it to see if it's been set. see if this is possible.
		if(this.m_temperatureWidget == null || this.menu.getMaxTemperature() != this.m_temperatureWidget.getMaxTemperature()) 
		{
			this.setTemperatureWidget();
		}

		this.m_temperatureWidget.setTemperature(this.menu.getTemperature());
	} // end updateResultTankWidget()
	
	public void setTemperatureWidget() 
	{
		if(this.m_temperatureWidget != null) 
		{			
			this.removeWidget(this.m_temperatureWidget);
		}
		
		this.m_temperatureWidget = new TemperatureWidget(this.leftPos + 37, this.topPos + 43, this.menu.getMaxTemperature());
		this.addRenderableWidget(this.m_temperatureWidget);
	} // end setWidget()

//	private TemperatureWidget getTemperatureWidget()
//	{
//		return this.m_temperatureWidget;
//	} // end getTemperatureWidget()
//	
//	private void setTemperatureWidget(TemperatureWidget tw) 
//	{
//		if(this.m_temperatureWidget != null) 
//		{			
//			this.removeWidget(this.m_temperatureWidget);
//		}
//		
//		this.m_temperatureWidget = tw;
//		this.addRenderableWidget(this.m_temperatureWidget);
//	} // end setTemperatureWidget()
	
} // end class

//FluidStack iTCs = this.menu.getInputTankContents();
//if (!iTCs.isEmpty())
//{
//	// figure out how to get the texture from fluid
//	RenderSystem.setShaderTexture(0, new ResourceLocation(""));
//	// iTCs.getRawFluid().defaultFluidState()
//	// ForgeHooksClient.getFluidSprites(new BlockAndTintGetter() {}, BlockPos.ZERO,
//	// iTCs.getRawFluid().defaultFluidState());
//
//	int renderDownSet = 51 - ((int) (51 * ((float) iTCs.getAmount() / (float) this.menu.getInputTankCapacity())));
//	blit(poseStack, this.leftPos + 10, (this.topPos + 23) + renderDownSet, 0, renderDownSet, 12, 51 - renderDownSet);
//	// render over fluid with the level indicators
//	RenderSystem.setShaderTexture(0, BACKGROUND);
//	blit(poseStack, this.leftPos + 10, this.topPos + 23, 176, 35, 12, 51);
//}
//
//FluidStack oTCs = this.menu.getOutputTankContents();
//if (!oTCs.isEmpty())
//{
//	// figure out how to get the texture from fluid
//	RenderSystem.setShaderTexture(0, new ResourceLocation(""));
//
//	int renderDownSet = 51 - ((int) (51 * ((float) oTCs.getAmount() / (float) this.menu.getOutputTankCapacity())));
//	blit(poseStack, this.leftPos + 154, (this.topPos + 23) + renderDownSet, 0, renderDownSet, 12, 51 - renderDownSet);
//
//	RenderSystem.setShaderTexture(0, BACKGROUND);
//	blit(poseStack, this.leftPos + 154, this.topPos + 23, 176, 35, 12, 51);
//}
