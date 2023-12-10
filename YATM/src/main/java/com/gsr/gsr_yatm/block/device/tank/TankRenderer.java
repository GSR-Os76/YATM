package com.gsr.gsr_yatm.block.device.tank;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.registry.YATMFluids;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

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
		// TODO, this, still todo this. what am i missing here
		Fluid contained = tank.getFluid();
		if(contained != null) 
		{
//			YetAnotherTechMod.LOGGER.info("about to try to render");
//			YetAnotherTechMod.LOGGER.info("about to try to render");
//			YetAnotherTechMod.LOGGER.info("about to try to render");
			Level level = tank.getLevel();
			BlockPos position = tank.getBlockPos();
			// float heightPercentage = tank.getPercentageFilled();
			
			// BlockState fluidToRender = this.m_fluidRenderLookup.computeIfAbsent(contained, (f) -> f.defaultFluidState().createLegacyBlock());
			// BakedModel model = this.m_blockRenderer.getBlockModel(fluidToRender);			
//
//			poseStack.pushPose();
//			poseStack.translate((2d/16d) + .001d, .001d + 1d/16d, (2d/16d) + .001d);
//			poseStack.scale(12f/16f - .002f, (heightPercentage - .002f) * (14f/16f), 12f/16f - .002f);
			FluidState fs = contained.defaultFluidState();
			TextureAtlasSprite[] fluidSprites = ForgeHooksClient.getFluidSprites(level, position, contained.defaultFluidState());
			// LiquidBlockRenderer
			// TODO, manual write vertices to buffer after texture atlas situation is resolved, this'll prevent stretching as well
			
			this.m_blockRenderer.renderLiquid(tank.getBlockPos(), tank.getLevel(), bufferSource.getBuffer(RenderType.solid()), Fluids.WATER.defaultFluidState().createLegacyBlock(), Fluids.WATER.defaultFluidState());
			this.m_blockRenderer.renderLiquid(tank.getBlockPos(), tank.getLevel(), bufferSource.getBuffer(RenderType.solid()), YATMFluids.BIO.get().defaultFluidState().createLegacyBlock(), YATMFluids.BIO.get().defaultFluidState());
			
			/*
			 this.m_blockRenderer.getModelRenderer().tesselateWithoutAO(
					tank.getLevel(), 
	 				model, fluidToRender, 
	 				tank.getBlockPos(),
	 				poseStack, 
	 				bufferSource.getBuffer(RenderType.translucent()), 
	 				true,
	 				RandomSource.create(), 
	 				349343948l, OverlayTexture.NO_OVERLAY,
	 				ModelData.EMPTY, RenderType.translucent());
			*/
			// poseStack.popPose();
			double x = (double)(position.getX());// & 15);
			double y = (double)(position.getY());// & 15);
			double z = (double)(position.getZ());// & 15);
			double hOffset = (2d/16d) + .001d;
			double vOffset = (1d/16d) + .001d;
			double width = (16d - (hOffset * 2));
			double height = (16d - (vOffset * 2));
			double ofX = x + hOffset;
			double ofY = y + vOffset;
			double ofZ = z + hOffset;
			
			int tc = IClientFluidTypeExtensions.of(fs).getTintColor(fs, level, position);
			float r = (float)(tc & 0b1111_1111) / 255.0F;
			float g = (float)(tc >> 8 & 0b1111_1111) / 255.0F;
		    float b = (float)(tc >> 16 & 0b1111_1111) / 255.0F;
		    float a = (float)(tc >> 24 & 0b1111_1111) / 255.0F;
		    
			float u0 = fluidSprites[0].getU0();
			float v0 = fluidSprites[0].getV0();
			float u1 = fluidSprites[0].getU1();
			float v1 = fluidSprites[0].getV1();
			
			int l = 100000;//this.getLightColor(level, position);
			VertexConsumer buffer = bufferSource.getBuffer(RenderType.solid());
			
			buffer.vertex(ofX, ofY, ofZ).color(r, g, b, a).uv(u0, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(0f, 1f, 0f).endVertex();
			buffer.vertex(ofX + width, ofY, ofZ).color(r, g, b, a).uv(u1, v0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(0f, 1f, 0f).endVertex();
			buffer.vertex(ofX + width, ofY + height, ofZ).color(r, g, b, a).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(0f, 1f, 0f).endVertex();
			buffer.vertex(ofX, ofY + height, ofZ).color(r, g, b, a).uv(u0, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(0f, 1f, 0f).endVertex();
			
			
			// BEGIN INLINE TESTS \\
			/*BlockAndTintGetter p_234370_ = level;
			BlockPos p_234371_ = position;
			VertexConsumer p_234372_= buffer; 
			BlockState p_234373_ = fluidToRender;
			FluidState p_234374_ = fs;
			
			
			
			boolean flag = p_234374_.is(FluidTags.LAVA);
		      TextureAtlasSprite[] atextureatlassprite = net.minecraftforge.client.ForgeHooksClient.getFluidSprites(p_234370_, p_234371_, p_234374_);
		      int i = net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions.of(p_234374_).getTintColor(p_234374_, p_234370_, p_234371_);
		      float alpha = (float)(i >> 24 & 255) / 255.0F;
		      float f = (float)(i >> 16 & 255) / 255.0F;
		      float f1 = (float)(i >> 8 & 255) / 255.0F;
		      float f2 = (float)(i & 255) / 255.0F;
		      BlockState blockstate = p_234370_.getBlockState(p_234371_.relative(Direction.DOWN));
		      FluidState fluidstate = blockstate.getFluidState();
		      BlockState blockstate1 = p_234370_.getBlockState(p_234371_.relative(Direction.UP));
		      FluidState fluidstate1 = blockstate1.getFluidState();
		      BlockState blockstate2 = p_234370_.getBlockState(p_234371_.relative(Direction.NORTH));
		      FluidState fluidstate2 = blockstate2.getFluidState();
		      BlockState blockstate3 = p_234370_.getBlockState(p_234371_.relative(Direction.SOUTH));
		      FluidState fluidstate3 = blockstate3.getFluidState();
		      BlockState blockstate4 = p_234370_.getBlockState(p_234371_.relative(Direction.WEST));
		      FluidState fluidstate4 = blockstate4.getFluidState();
		      BlockState blockstate5 = p_234370_.getBlockState(p_234371_.relative(Direction.EAST));
		      FluidState fluidstate5 = blockstate5.getFluidState();
		      boolean flag1 = true;//!isNeighborSameFluid(p_234374_, fluidstate1);
		      boolean flag2 = true;//shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.DOWN, fluidstate) && !isFaceOccludedByNeighbor(p_234370_, p_234371_, Direction.DOWN, 0.8888889F, blockstate);
		      boolean flag3 = true;//shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.NORTH, fluidstate2);
		      boolean flag4 = true;//shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.SOUTH, fluidstate3);
		      boolean flag5 = true;//shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.WEST, fluidstate4);
		      boolean flag6 = true;//shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.EAST, fluidstate5);
		      if (flag1 || flag2 || flag6 || flag5 || flag3 || flag4) {
		         float f3 = p_234370_.getShade(Direction.DOWN, true);
		         float f4 = p_234370_.getShade(Direction.UP, true);
		         float f5 = p_234370_.getShade(Direction.NORTH, true);
		         float f6 = p_234370_.getShade(Direction.WEST, true);
		         Fluid fluid = p_234374_.getType();
		         float f11 = 1f;//this.getHeight(p_234370_, fluid, p_234371_, p_234373_, p_234374_);
		         float f7;
		         float f8;
		         float f9;
		         float f10;
		         if (f11 >= 1.0F) {
		            f7 = 1.0F;
		            f8 = 1.0F;
		            f9 = 1.0F;
		            f10 = 1.0F;
		         } else {
		            float f12 = 1f;//this.getHeight(p_234370_, fluid, p_234371_.north(), blockstate2, fluidstate2);
		            float f13 = 1f;//this.getHeight(p_234370_, fluid, p_234371_.south(), blockstate3, fluidstate3);
		            float f14 = 1f;//this.getHeight(p_234370_, fluid, p_234371_.east(), blockstate5, fluidstate5);
		            float f15 = 1f;//this.getHeight(p_234370_, fluid, p_234371_.west(), blockstate4, fluidstate4);
		            f7 = 1f;//this.calculateAverageHeight(p_234370_, fluid, f11, f12, f14, p_234371_.relative(Direction.NORTH).relative(Direction.EAST));
		            f8 = 1f;//this.calculateAverageHeight(p_234370_, fluid, f11, f12, f15, p_234371_.relative(Direction.NORTH).relative(Direction.WEST));
		            f9 = 1f;//this.calculateAverageHeight(p_234370_, fluid, f11, f13, f14, p_234371_.relative(Direction.SOUTH).relative(Direction.EAST));
		            f10 = 1f;//this.calculateAverageHeight(p_234370_, fluid, f11, f13, f15, p_234371_.relative(Direction.SOUTH).relative(Direction.WEST));
		         }

		         double d1 = (double)(p_234371_.getX() & 15);
		         double d2 = (double)(p_234371_.getY() & 15);
		         double d0 = (double)(p_234371_.getZ() & 15);
		         float f16 = 0.001F;
		         float f17 = flag2 ? 0.001F : 0.0F;
		         if (flag1 && true)//!isFaceOccludedByNeighbor(p_234370_, p_234371_, Direction.UP, Math.min(Math.min(f8, f10), Math.min(f9, f7)), blockstate1)) 
		        	 {
		            f8 -= 0.001F;
		            f10 -= 0.001F;
		            f9 -= 0.001F;
		            f7 -= 0.001F;
		            Vec3 vec3 = p_234374_.getFlow(p_234370_, p_234371_);
		            float f18;
		            float f19;
		            float f20;
		            float f21;
		            float f22;
		            float f23;
		            float f24;
		            float f25;
		            if (vec3.x == 0.0D && vec3.z == 0.0D) {
		               TextureAtlasSprite textureatlassprite1 = atextureatlassprite[0];
		               f18 = textureatlassprite1.getU(0.0D);
		               f22 = textureatlassprite1.getV(0.0D);
		               f19 = f18;
		               f23 = textureatlassprite1.getV(16.0D);
		               f20 = textureatlassprite1.getU(16.0D);
		               f24 = f23;
		               f21 = f20;
		               f25 = f22;
		            } else {
		               TextureAtlasSprite textureatlassprite = atextureatlassprite[1];
		               float f26 = (float)Mth.atan2(vec3.z, vec3.x) - ((float)Math.PI / 2F);
		               float f27 = Mth.sin(f26) * 0.25F;
		               float f28 = Mth.cos(f26) * 0.25F;
		               float f29 = 8.0F;
		               f18 = textureatlassprite.getU((double)(8.0F + (-f28 - f27) * 16.0F));
		               f22 = textureatlassprite.getV((double)(8.0F + (-f28 + f27) * 16.0F));
		               f19 = textureatlassprite.getU((double)(8.0F + (-f28 + f27) * 16.0F));
		               f23 = textureatlassprite.getV((double)(8.0F + (f28 + f27) * 16.0F));
		               f20 = textureatlassprite.getU((double)(8.0F + (f28 + f27) * 16.0F));
		               f24 = textureatlassprite.getV((double)(8.0F + (f28 - f27) * 16.0F));
		               f21 = textureatlassprite.getU((double)(8.0F + (f28 - f27) * 16.0F));
		               f25 = textureatlassprite.getV((double)(8.0F + (-f28 - f27) * 16.0F));
		            }

		            float f49 = (f18 + f19 + f20 + f21) / 4.0F;
		            float f50 = (f22 + f23 + f24 + f25) / 4.0F;
		            float f51 = atextureatlassprite[0].uvShrinkRatio();
		            f18 = Mth.lerp(f51, f18, f49);
		            f19 = Mth.lerp(f51, f19, f49);
		            f20 = Mth.lerp(f51, f20, f49);
		            f21 = Mth.lerp(f51, f21, f49);
		            f22 = Mth.lerp(f51, f22, f50);
		            f23 = Mth.lerp(f51, f23, f50);
		            f24 = Mth.lerp(f51, f24, f50);
		            f25 = Mth.lerp(f51, f25, f50);
		            int l = this.getLightColor(p_234370_, p_234371_);
		            float f52 = f4 * f;
		            float f30 = f4 * f1;
		            float f31 = f4 * f2;
		            this.vertex(p_234372_, d1 + 0.0D, d2 + (double)f8, d0 + 0.0D, f52, f30, f31, alpha, f18, f22, l);
		            this.vertex(p_234372_, d1 + 0.0D, d2 + (double)f10, d0 + 1.0D, f52, f30, f31, alpha, f19, f23, l);
		            this.vertex(p_234372_, d1 + 1.0D, d2 + (double)f9, d0 + 1.0D, f52, f30, f31, alpha, f20, f24, l);
		            this.vertex(p_234372_, d1 + 1.0D, d2 + (double)f7, d0 + 0.0D, f52, f30, f31, alpha, f21, f25, l);
		            if (p_234374_.shouldRenderBackwardUpFace(p_234370_, p_234371_.above())) {
		               this.vertex(p_234372_, d1 + 0.0D, d2 + (double)f8, d0 + 0.0D, f52, f30, f31, alpha, f18, f22, l);
		               this.vertex(p_234372_, d1 + 1.0D, d2 + (double)f7, d0 + 0.0D, f52, f30, f31, alpha, f21, f25, l);
		               this.vertex(p_234372_, d1 + 1.0D, d2 + (double)f9, d0 + 1.0D, f52, f30, f31, alpha, f20, f24, l);
		               this.vertex(p_234372_, d1 + 0.0D, d2 + (double)f10, d0 + 1.0D, f52, f30, f31, alpha, f19, f23, l);
		            }
		         }

		         if (flag2) {
		            float f40 = atextureatlassprite[0].getU0();
		            float f41 = atextureatlassprite[0].getU1();
		            float f42 = atextureatlassprite[0].getV0();
		            float f43 = atextureatlassprite[0].getV1();
		            int k = this.getLightColor(p_234370_, p_234371_.below());
		            float f46 = f3 * f;
		            float f47 = f3 * f1;
		            float f48 = f3 * f2;
		            this.vertex(p_234372_, d1, d2 + (double)f17, d0 + 1.0D, f46, f47, f48, alpha, f40, f43, k);
		            this.vertex(p_234372_, d1, d2 + (double)f17, d0, f46, f47, f48, alpha, f40, f42, k);
		            this.vertex(p_234372_, d1 + 1.0D, d2 + (double)f17, d0, f46, f47, f48, alpha, f41, f42, k);
		            this.vertex(p_234372_, d1 + 1.0D, d2 + (double)f17, d0 + 1.0D, f46, f47, f48, alpha, f41, f43, k);
		         }

		         int j = this.getLightColor(p_234370_, p_234371_);

		         for(Direction direction : Direction.Plane.HORIZONTAL) {
		            float f44;
		            float f45;
		            double d3;
		            double d4;
		            double d5;
		            double d6;
		            boolean flag7;
		            switch (direction) {
		               case NORTH:
		                  f44 = f8;
		                  f45 = f7;
		                  d3 = d1;
		                  d5 = d1 + 1.0D;
		                  d4 = d0 + (double)0.001F;
		                  d6 = d0 + (double)0.001F;
		                  flag7 = flag3;
		                  break;
		               case SOUTH:
		                  f44 = f9;
		                  f45 = f10;
		                  d3 = d1 + 1.0D;
		                  d5 = d1;
		                  d4 = d0 + 1.0D - (double)0.001F;
		                  d6 = d0 + 1.0D - (double)0.001F;
		                  flag7 = flag4;
		                  break;
		               case WEST:
		                  f44 = f10;
		                  f45 = f8;
		                  d3 = d1 + (double)0.001F;
		                  d5 = d1 + (double)0.001F;
		                  d4 = d0 + 1.0D;
		                  d6 = d0;
		                  flag7 = flag5;
		                  break;
		               default:
		                  f44 = f7;
		                  f45 = f9;
		                  d3 = d1 + 1.0D - (double)0.001F;
		                  d5 = d1 + 1.0D - (double)0.001F;
		                  d4 = d0;
		                  d6 = d0 + 1.0D;
		                  flag7 = flag6;
		            }

		            if (flag7 && true) {//!isFaceOccludedByNeighbor(p_234370_, p_234371_, direction, Math.max(f44, f45), p_234370_.getBlockState(p_234371_.relative(direction)))) {
		               BlockPos blockpos = p_234371_.relative(direction);
		               TextureAtlasSprite textureatlassprite2 = atextureatlassprite[1];
		               if (atextureatlassprite[2] != null) {
		                  if (p_234370_.getBlockState(blockpos).shouldDisplayFluidOverlay(p_234370_, blockpos, p_234374_)) {
		                     textureatlassprite2 = atextureatlassprite[2];
		                  }
		               }

		               float f53 = textureatlassprite2.getU(0.0D);
		               float f32 = textureatlassprite2.getU(8.0D);
		               float f33 = textureatlassprite2.getV((double)((1.0F - f44) * 16.0F * 0.5F));
		               float f34 = textureatlassprite2.getV((double)((1.0F - f45) * 16.0F * 0.5F));
		               float f35 = textureatlassprite2.getV(8.0D);
		               float f36 = direction.getAxis() == Direction.Axis.Z ? f5 : f6;
		               float f37 = f4 * f36 * f;
		               float f38 = f4 * f36 * f1;
		               float f39 = f4 * f36 * f2;
		               this.vertex(p_234372_, d3, d2 + (double)f44, d4, f37, f38, f39, alpha, f53, f33, j);
		               this.vertex(p_234372_, d5, d2 + (double)f45, d6, f37, f38, f39, alpha, f32, f34, j);
		               this.vertex(p_234372_, d5, d2 + (double)f17, d6, f37, f38, f39, alpha, f32, f35, j);
		               this.vertex(p_234372_, d3, d2 + (double)f17, d4, f37, f38, f39, alpha, f53, f35, j);
		               if (true)//textureatlassprite2 != this.waterOverlay) {
		                  this.vertex(p_234372_, d3, d2 + (double)f17, d4, f37, f38, f39, alpha, f53, f35, j);
		                  this.vertex(p_234372_, d5, d2 + (double)f17, d6, f37, f38, f39, alpha, f32, f35, j);
		                  this.vertex(p_234372_, d5, d2 + (double)f45, d6, f37, f38, f39, alpha, f32, f34, j);
		                  this.vertex(p_234372_, d3, d2 + (double)f44, d4, f37, f38, f39, alpha, f53, f33, j);
		               }
		            }
		         }*/

		      }
//		YetAnotherTechMod.LOGGER.info("reached the end of render");
		
	} // end render()



} // end client