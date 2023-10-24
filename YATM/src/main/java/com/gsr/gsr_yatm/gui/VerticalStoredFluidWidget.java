package com.gsr.gsr_yatm.gui;

import org.lwjgl.opengl.GL11;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class VerticalStoredFluidWidget extends ImageWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	// tank starts at 0 0, is 18 57 in dimensions
	// overlay lines at 0 57, is 6 27 in dimensions, goes to coordinate 9 15
	private final int m_capacity;
	private int m_stored = 0;
	private Fluid m_fluid = Fluids.EMPTY;
	private ResourceLocation m_fluidStillTexture = TextureManager.INTENTIONAL_MISSING_TEXTURE; //new ResourceLocation("minecraft:textures/block_marker.png"); 
	private int m_fluidTextureWidth = 16;
	private int m_fluidTextureHeight = 16;
	private int m_toCenterTileDeviation = 2;
	// the desired number of pixels out of the full pixel size based on the percentage of fullness
	private int m_desiredHeight = 0;

	
	
	public VerticalStoredFluidWidget(int toX, int toY, int capacity, Fluid fluid) 
	{
		super(toX, toY, 18, 57, WIDGET_THINGS);
		this.m_capacity = capacity;
		this.m_fluid = fluid;
		this.grabFluidTexture();
		this.updateTooltip();
	} // end constructor()
	
	
	

	public int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()
	
	public Fluid getFluid()
	{
		return this.m_fluid;
	} // end getFluid()
	
	
	
	public void setStoredAmount(int amount) 
	{
		this.m_stored = Math.min(amount, this.m_capacity);
		this.m_desiredHeight = (this.m_capacity > 0 && this.m_stored > 0)
				? (int) (51f * (((float) this.m_stored) / ((float) this.m_capacity)))
				: 0;
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void setStoredFluid(Fluid fluid) 
	{
		this.m_fluid = fluid;
		this.grabFluidTexture();
		this.updateTooltip();
	} // end setStoredFluid
	
	private void grabFluidTexture() 
	{
		if(this.m_fluid.getFluidType().getRenderPropertiesInternal()instanceof IClientFluidTypeExtensions clientFluidTypeExtensions)
			
		{
			this.m_fluidStillTexture = clientFluidTypeExtensions.getStillTexture(new FluidStack(this.m_fluid, this.m_stored)).withPrefix("textures/").withSuffix(".png");
			Minecraft minecraft = Minecraft.getInstance();
			

			minecraft.textureManager.bindForSetup(this.m_fluidStillTexture);

			
			// YetAnotherTechMod.LOGGER.info("bound texture index: 0, with the texture: " + this.m_fluidStillTexture);

			this.m_fluidTextureWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);//16;
			this.m_fluidTextureHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);//320;//16, 320)
			// YetAnotherTechMod.LOGGER.info("got width from open gl as value: " + this.m_textureWidth + ", got height from open gl as value: " + this.m_textureHeight);
			this.m_toCenterTileDeviation = (this.m_fluidTextureWidth - 12) / 2;
		}
	} // end grabFluidTexture()
	
	public void updateTooltip() 
	{
		String fluidLevel = (" (" + (this.m_stored + "/" + this.m_capacity)+ ")");
		MutableComponent tt = this.m_stored == 0 ? Component.literal("Empty") : Component.translatable(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(this.m_fluid)).toString());
		tt.append(fluidLevel);
		this.setTooltip(Tooltip.create(tt));
	} // end updateTooltip()
	
	
	
	@Override
	public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		graphics.blit(WIDGET_THINGS, this.getX(), this.getY(), 0, 0, 18, 57);
		if(this.m_desiredHeight > 0 && this.m_fluidTextureWidth > 0) 
		{
			this.renderFluidLayer(graphics);
			
			// render the level markers over top the fluid display
			graphics.blit(WIDGET_THINGS, this.getX() + 9, this.getY() + 15, 0, 57, 6, 27);
		}		
	} // end renderWidget()
	
	private void renderFluidLayer(GuiGraphics graphics)
	{
		if(this.m_desiredHeight <= 0 && this.m_fluidTextureWidth <= 0) 
		{
			return;
		}
		// maybe if possible scale the texture so it can tile the tank nicely without needing any cutting, at least for the width that is
		// TODO, do biome tinting for fluids with overlay mask like water has
		// TODO, animation
				
		// inner tank pos is at 3 3, is 12 by 51 area
		// RenderSystem.setShaderTexture(0, this.m_fluidStillTexture);
		
		int finalFraction = this.m_desiredHeight % this.m_fluidTextureWidth;
		int fullTiles = this.m_desiredHeight / this.m_fluidTextureWidth;
		
		int steppedHeight = 0;
		while(steppedHeight < this.m_desiredHeight) 
		{
			int thisStepsHeight = fullTiles == 0 
					? finalFraction
					: steppedHeight / fullTiles == this.m_fluidTextureWidth 
						? finalFraction 
						: this.m_fluidTextureWidth;
			steppedHeight += thisStepsHeight; 
			graphics.blit(this.m_fluidStillTexture, 
				this.getX() + 3, this.getY() + 3 + (51 - steppedHeight),//this.m_desiredHeight), 
				0,
				this.m_toCenterTileDeviation, this.m_fluidTextureWidth - thisStepsHeight, 
				12, thisStepsHeight,
				this.m_fluidTextureWidth, this.m_fluidTextureHeight);
		}		
		
	} // end renderfluidlayer()
	
} // end class
//package com.gsr.gsr_yatm.gui;
//
//import org.lwjgl.opengl.GL11;
//
//import com.gsr.gsr_yatm.YetAnotherTechMod;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//
//import net.minecraft.Util;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.ImageWidget;
//import net.minecraft.client.gui.components.Tooltip;
//import net.minecraft.client.renderer.texture.TextureManager;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.MutableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.material.Fluid;
//import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class StoredFluidWidget extends ImageWidget
//{
//	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
//	// tank starts at 0 0, is 18 57 in dimensions
//	// overlay lines at 0 57, is 6 27 in dimensions, goes to coordinate 9 15
//	private int m_stored = 0;
//	private final int m_capacity;
//	private Fluid m_fluid;
//	private ResourceLocation m_fluidStillTexture = TextureManager.INTENTIONAL_MISSING_TEXTURE; //new ResourceLocation("minecraft:textures/block_marker.png"); 
//	private int m_textureWidth = 16;
//	private int m_textureHeight = 16;
//	private int m_toCenterTileDeviation = 2;
//	private float m_percentFull = 0f;
//	// the desired number of pixels out of the full pixel size based on the percentage of fullness
//	private int m_desiredHeight = 0;
//
//	
//	
//	public StoredFluidWidget(int toX, int toY, int capacity, Fluid fluid) 
//	{
//		super(toX, toY, 18, 57, WIDGET_THINGS);
//		this.m_capacity = capacity;
//		this.m_fluid = fluid;
//		this.grabFluidTexture();
//		this.updateTooltip();
//	} // end constructor()
//	
//	
//	
//
//	public int getCapacity()
//	{
//		return this.m_capacity;
//	} // end getCapacity()
//	
//	public Fluid getFluid()
//	{
//		return this.m_fluid;
//	} // end getFluid()
//	
//	
//	
//	public void setStoredAmount(int amount) 
//	{
//		this.m_stored = amount;
//		this.m_percentFull = (((float) this.m_stored) / ((float) this.m_capacity)); 
//		this.m_desiredHeight = (int) (51f * m_percentFull);
//		this.updateTooltip();
//	} // end setStoredAmount()
//	
//	public void setStoredFluid(Fluid fluid) 
//	{
//		this.m_fluid = fluid;
//		this.grabFluidTexture();
//		this.updateTooltip();
//	} // end setStoredFluid
//	
//	private void grabFluidTexture() 
//	{
//		if(this.m_fluid.getFluidType().getRenderPropertiesInternal()instanceof IClientFluidTypeExtensions clientFluidTypeExtensions)
//			
//		{
//			this.m_fluidStillTexture = clientFluidTypeExtensions.getStillTexture(new FluidStack(this.m_fluid, this.m_stored)).withPrefix("textures/").withSuffix(".png");
//			Minecraft minecraft = Minecraft.getInstance();
//			minecraft.textureManager.bindForSetup(this.m_fluidStillTexture);
//			// YetAnotherTechMod.LOGGER.info("bound texture index: 0, with the texture: " + this.m_fluidStillTexture);
//
//			this.m_textureWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);//16;
//			this.m_textureHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);//320;//16, 320)
//			// YetAnotherTechMod.LOGGER.info("got width from open gl as value: " + this.m_textureWidth + ", got height from open gl as value: " + this.m_textureHeight);
//			this.m_toCenterTileDeviation = (this.m_textureWidth - 12) / 2;
//		}
//	} // end grabFluidTexture()
//	
//	public void updateTooltip() 
//	{
//		String fluidLevel = (" (" + (this.m_stored + "/" + this.m_capacity)+ ")");
//		// TODO, get actual translated/translatable name some how
//		MutableComponent tt = Component.translatable(Util.makeDescriptionId("fluid", ForgeRegistries.FLUIDS.getKey(this.m_fluid)).toString());
//		tt.append(fluidLevel);
//		this.setTooltip(Tooltip.create(tt));
//	} // end updateTooltip()
//	
//	
//	
//	@Override
//	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
//	{
//		RenderSystem.setShaderTexture(0, WIDGET_THINGS);
//		blit(poseStack, this.getX(), this.getY(), 0, 0, 18, 57);
//		if(this.m_stored > 0) 
//		{
//			this.renderFluidLayer(poseStack);
//			
//			// render the level markers over top the fluid display
//			RenderSystem.setShaderTexture(0, WIDGET_THINGS);
//			blit(poseStack, this.getX() + 9, this.getY() + 15, 0, 57, 6, 27);
//		}		
//	} // end renderWidget()
//	
//	private void renderFluidLayer(PoseStack poseStack)
//	{
//		// maybe scale image so the texture tiles the tank nicely without cutting, at least for the width
//		// TODO, do biome tinting for fluids with overlay mask like water has
//		// TODO, animation
//				
//		// inner tank pos is at 3 3, is 12 by 51 area
//		RenderSystem.setShaderTexture(0, this.m_fluidStillTexture);
//		// AbstractTexture abs = Minecraft.getInstance().getTextureManager().getTexture(this.m_fluidStillTexture);
//		
//		for(int i = 0; i < 51;)
//		blit(poseStack, 
//				this.getX() + 3, this.getY() + 3 + (51 - this.m_desiredHeight), 
//				0,
//				this.m_toCenterTileDeviation, 0, 
//				12, this.m_desiredHeight,
//				this.m_textureWidth, this.m_textureHeight);
//		
//	} // end renderfluidlayer()
//	
//} // end class