package com.gsr.gsr_yatm.gui;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class StoredFluidWidget extends ImageWidget
{
	public static final ResourceLocation WIDGET_THINGS = new ResourceLocation(YetAnotherTechMod.MODID, "textures/gui/widget_things.png");
	// tank starts at 0 0, is 18 57 in dimensions
	// overlay lines at 0 57, is 6 27 in dimensions, goes to coordinate 9 15
	private int m_stored = 0;
	private final int m_capacity;
	private Fluid m_fluid;
	
	
	public StoredFluidWidget(int toX, int toY, int capacity, Fluid fluid) 
	{
		super(toX, toY, 18, 57, WIDGET_THINGS);
		this.m_capacity = capacity;
		this.m_fluid = fluid;
		this.updateTooltip();
	} // end constructor()
	
	
	

	public int getCapacity()
	{
		return this.m_capacity;
	} // end getCapacity()
	
	public void setStoredAmount(int amount) 
	{
		this.m_stored = amount;
		this.updateTooltip();
	} // end setStoredAmount()
	
	public void setStoredFluid(Fluid fluid) 
	{
		this.m_fluid = fluid;
		this.updateTooltip();
	} // end setStoredFluid
	
	public void updateTooltip() 
	{
		String fluidLevel = (" (" + (this.m_stored + "/" + this.m_capacity)+ ")");
		// TODO, get actual translated/translatable name some how
		MutableComponent tt = Component.translatable(ForgeRegistries.FLUIDS.getKey(this.m_fluid).toString());
		tt.append(fluidLevel);
		this.setTooltip(Tooltip.create(tt));
	} // end updateTooltip()
	
	
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
	{
		RenderSystem.setShaderTexture(0, WIDGET_THINGS);
		blit(poseStack, this.getX(), this.getY(), 0, 0, 18, 57);
		if(this.m_stored > 0) 
		{
			float sP = (((float)this.m_stored) / ((float)this.m_capacity));
			int desHeight = (int)(51f * sP);
			
			// inner tank pos is at 3 3, is 12 by 51 area
			RenderSystem.setShaderTexture(0, new ResourceLocation(""));
			blit(poseStack, this.getX() + 3, this.getY() + 3 + (51 - desHeight), 0, 0, 12, desHeight);
			
			// render the level markers over top the fluid display
			RenderSystem.setShaderTexture(0, WIDGET_THINGS);
			blit(poseStack, this.getX() + 9, this.getY() + 15, 0, 57, 6, 27);
		}		
	} // end renderWidget()
	// TODO, add in tooltips for various different things, add in inventory content dropping on break	

} // end class