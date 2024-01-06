package com.gsr.gsr_yatm.gui;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

public class HorizontalStoredFluidWidget extends FillBarWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	public static final int WIDTH = 57;
	public static final int HEIGHT = 18;
	public static final int INNER_WIDTH = 51;
	
	private final @NotNegative int m_capacity;
	private int m_stored = 0;
	private @NotNull Fluid m_fluid = Fluids.EMPTY;
	private ResourceLocation m_fluidStillTexture = TextureManager.INTENTIONAL_MISSING_TEXTURE;
	private int m_fluidTextureWidth = 16;
	private int m_fluidTextureHeight = 16;
	private int m_toCenterTileDeviation = 2;
	// the desired number of pixels out of the full pixel size based on the percentage of fullness
	private int m_desiredWidth = 0;

	
	
	public HorizontalStoredFluidWidget(int toX, int toY, @NotNegative int capacity, @NotNull Fluid fluid) 
	{
		super(toX, toY, HorizontalStoredFluidWidget.WIDTH, HorizontalStoredFluidWidget.HEIGHT, Component.translatable(null));
		this.m_capacity = Contract.notNegative(capacity);
		this.m_fluid = Objects.requireNonNull(fluid);
		this.grabFluidTexture();
		this.updateTooltip();
	} // end constructor()
	
	
	

	public @NotNegative int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()
	
	public @NotNull Fluid getFluid()
	{
		return this.m_fluid;
	} // end getFluid()
	
	
	
	public void setStoredAmount(@NotNegative int amount) 
	{
		this.m_stored = Math.min(Contract.notNegative(amount), this.m_capacity);
		this.m_desiredWidth = (this.m_capacity > 0 && this.m_stored > 0)
				? (int) (((float)HorizontalStoredFluidWidget.INNER_WIDTH) * (((float) this.m_stored) / ((float) this.m_capacity)))
				: 0;
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void setStoredFluid(@NotNull Fluid fluid) 
	{
		this.m_fluid = Objects.requireNonNull(fluid);
		this.grabFluidTexture();
		this.updateTooltip();
	} // end setStoredFluid
	
	private void grabFluidTexture() 
	{
		if(this.m_fluid.getFluidType().getRenderPropertiesInternal() instanceof IClientFluidTypeExtensions clientFluidTypeExtensions)
			
		{
			this.m_fluidStillTexture = clientFluidTypeExtensions.getStillTexture(new FluidStack(this.m_fluid, this.m_stored)).withPrefix("textures/").withSuffix(".png");
			Minecraft minecraft = Minecraft.getInstance();
			

			minecraft.textureManager.bindForSetup(this.m_fluidStillTexture);

			
			this.m_fluidTextureWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
			this.m_fluidTextureHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
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
		graphics.blit(HorizontalStoredFluidWidget.WIDGET_THINGS, this.getX(), this.getY(), 18, 0, HorizontalStoredFluidWidget.WIDTH, HorizontalStoredFluidWidget.HEIGHT);
		if(this.m_desiredWidth > 0 && this.m_fluidTextureHeight > 0) 
		{
			this.renderFluidLayer(graphics);
			
			// render the level markers over top the fluid display
			graphics.blit(HorizontalStoredFluidWidget.WIDGET_THINGS, this.getX() + 15, this.getY() + 9, 75, 0, 27, 6);
		}		
	} // end renderWidget()
	
	private void renderFluidLayer(GuiGraphics graphics)
	{
		if(this.m_desiredWidth <= 0 && this.m_fluidTextureWidth <= 0) 
		{
			return;
		}
		// maybe if possible scale the texture so it can tile the tank nicely without needing any cutting, at least for the width that is
		// TODO, do biome tinting for fluids with overlay mask like water has
		// TODO, animation
				
		// inner tank pos is at 3 3, is 12 by 51 area
		// RenderSystem.setShaderTexture(0, this.m_fluidStillTexture);
		
		int finalFraction = this.m_desiredWidth % this.m_fluidTextureHeight;
		int fullTiles = this.m_desiredWidth / this.m_fluidTextureHeight;
		
		int steppedWidth = 0;
		while(steppedWidth < this.m_desiredWidth) 
		{
			int thisStepsWidth = fullTiles == 0 
					? finalFraction
					: steppedWidth / fullTiles == this.m_fluidTextureHeight 
						? finalFraction 
						: this.m_fluidTextureHeight;
			steppedWidth += thisStepsWidth;
			
			graphics.blit(this.m_fluidStillTexture, 
				this.getX() + 3 + (51 - steppedWidth), this.getY() + 3,
				0,
				this.m_fluidTextureHeight - thisStepsWidth, this.m_toCenterTileDeviation, 
				thisStepsWidth, 12, 
				this.m_fluidTextureWidth, this.m_fluidTextureHeight);
		}		
		
	} // end renderfluidlayer()
	
} // end class