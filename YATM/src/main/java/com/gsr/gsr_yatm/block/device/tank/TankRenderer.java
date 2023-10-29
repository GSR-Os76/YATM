package com.gsr.gsr_yatm.block.device.tank;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(Dist.CLIENT)
public class TankRenderer implements BlockEntityRenderer<TankBlockEntity>
{
	private final @NotNull BlockRenderDispatcher m_blockRenderer;
	
	private final @NotNull Map<Fluid, BlockState> m_fluidRenderLookup = new HashMap<>();

	
	
	public TankRenderer(@NotNull BlockEntityRendererProvider.Context rendererContext) 
	{
		this.m_blockRenderer = Objects.requireNonNull(rendererContext.getBlockRenderDispatcher());
	} // end constructor
	
	
	
	@Override
	public void render(@NotNull TankBlockEntity tank, float unknown, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int p_112311_, int something)
	{
		Fluid contained = tank.getFluid();
		if(contained != null) 
		{
			Level level = tank.getLevel();
			BlockPos position = tank.getBlockPos();
			float heightPercentage = tank.getPercentageFilled();
			// TODO, textures are rendered as missing or blank
			// TODO, textures that're usually rendered missing aren't rendered while next to fluids from YATM
			BlockState fluidToRender = this.m_fluidRenderLookup.computeIfAbsent(contained, (f) -> f.defaultFluidState().createLegacyBlock());
			// BakedModel model = this.m_blockRenderer.getBlockModel(fluidToRender);			

			poseStack.pushPose();
			poseStack.translate((2d/16d) + .001d, .001d + 1d/16d, (2d/16d) + .001d);
			poseStack.scale(12f/16f - .002f, (heightPercentage - .002f) * (14f/16f), 12f/16f - .002f);
			// TextureAtlasSprite[] atextureatlassprite = ForgeHooksClient.getFluidSprites(level, position, Fluids.WATER.defaultFluidState());
		    
			// LiquidBlockRenderer
			// TODO, figure out how to add YATM fluids to texture atlas, currently they're not found and it is throwing an exception
			// TODO, manual write vertices to buffer after texture atlas situation is resolved, this'll prevent stretching as well
			
			// this.m_blockRenderer.renderLiquid(tank.getBlockPos(), tank.getLevel(), bufferSource.getBuffer(RenderType.translucent()), Fluids.WATER.defaultFluidState().createLegacyBlock(), Fluids.WATER.defaultFluidState());
//			this.m_blockRenderer.getModelRenderer().tesselateWithoutAO(
//					tank.getLevel(), 
//	 				model, fluidToRender, 
//	 				tank.getBlockPos(),
//	 				poseStack, 
//	 				bufferSource.getBuffer(RenderType.translucent()), 
//	 				true,
//	 				RandomSource.create(), 
//	 				349343948l, OverlayTexture.NO_OVERLAY,
//	 				ModelData.EMPTY, RenderType.translucent());
			poseStack.popPose();
		}    
	} // end render()
	
} // end client