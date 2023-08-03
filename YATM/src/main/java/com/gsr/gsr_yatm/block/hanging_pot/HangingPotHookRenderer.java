package com.gsr.gsr_yatm.block.hanging_pot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(Dist.CLIENT)
public class HangingPotHookRenderer implements BlockEntityRenderer<HangingPotHookBlockEntity>
{
	public static final ResourceLocation DEFAULT_HANGING_POT_SUPPORT_CHAINS_LOCATION = new ResourceLocation(YetAnotherTechMod.MODID, "default_hanging_pot_support_chains");
	private final BlockRenderDispatcher m_blockRenderer;
	private final Map<@NotNull FlowerPotBlock, Supplier<BlockState>/* Pair<@NotNull Model, @NotNull Material> */> m_customSupportChainLookup = new HashMap<>();
	private final Supplier<BlockState> /* Pair<@NotNull Model, @NotNull Material> */ m_defaultSupportChains;
	// private final  Pair<@NotNull Model, @NotNull Material> m_testDefaultSupportChains;
	
	
	
	public HangingPotHookRenderer(BlockEntityRendererProvider.Context rendererContext) 
	{
		this.m_blockRenderer = rendererContext.getBlockRenderDispatcher();
		this.m_defaultSupportChains = () -> YATMBlocks.DEFAULT_HANGING_POT_SUPPORT_CHAINS.get().defaultBlockState();
//		this.m_testDefaultSupportChains = Pair.of(
//				new DefaultHangingPotSupportChainModel(rendererContext.bakeLayer(YATMModelLayers.DEAFULT_HANGING_POT_SUPPORT_CHAIN_MODEL_NAME))
//				, YATMSheets.getBlock(DEFAULT_HANGING_POT_SUPPORT_CHAINS_LOCATION));
	} // end constructor
	
	
	
	@Override
	public void render(HangingPotHookBlockEntity hangingPotHook, float unknown, PoseStack poseStack, MultiBufferSource bufferSource, int p_112311_, int something)
	{
		FlowerPotBlock pot = hangingPotHook.getPot();
		if(pot == null) 
		{
			return;
		}
		BlockState stateToRender = pot.defaultBlockState();
		BakedModel model = this.m_blockRenderer.getBlockModel(stateToRender);			

		this.m_blockRenderer
		.getModelRenderer()
		.tesselateWithoutAO(
				hangingPotHook.getLevel(), 
				model, stateToRender, 
				hangingPotHook.getBlockPos(),
				poseStack, 
				bufferSource.getBuffer(RenderType.cutout()), 
				true, // related to always face rendering, if false every face is always rendered
				RandomSource.create(), 
				349343948l, OverlayTexture.NO_OVERLAY, // seed, overlayCoords  
				ModelData.EMPTY, RenderType.cutout());

//		Pair<Model, Material> supportChains = this.m_customSupportChainLookup.containsKey(pot) ? this.m_customSupportChainLookup.get(pot) : this.m_defaultSupportChains;
//		
//		supportChains.first()
//		.renderToBuffer(poseStack,  supportChains.second().buffer(bufferSource, supportChains.first()::renderType), // bufferSource.getBuffer(RenderType.cutout()), bufferSource.getBuffer(RenderType.cutout()), // 
//				something, OverlayTexture.NO_OVERLAY, 
//				1.0f, 1.0f, 1.0f, 1.0f);

		BlockState supportChainState = (this.m_customSupportChainLookup.containsKey(pot) ? this.m_customSupportChainLookup.get(pot) : this.m_defaultSupportChains).get();
		BakedModel supportChainModel = this.m_blockRenderer.getBlockModel(supportChainState);			

		this.m_blockRenderer
		.getModelRenderer()
		.tesselateWithoutAO(
				hangingPotHook.getLevel(), 
				supportChainModel, supportChainState, 
				hangingPotHook.getBlockPos(),
				poseStack, 
				bufferSource.getBuffer(RenderType.cutout()), 
				true, 
				RandomSource.create(), 
				349343948l, OverlayTexture.NO_OVERLAY, 
				ModelData.EMPTY, RenderType.cutout());
		
	} // end render()

	
	
	// TODO, probably once learned how move this system to data pack based
	public void registerCustomSupportChain(@NotNull FlowerPotBlock forPot, @Nullable Supplier<BlockState> stateSupplier) //Pair<@NotNull Model, @NotNull Material> model) 
	{
		this.m_customSupportChainLookup.put(Objects.requireNonNull(forPot), stateSupplier == null 
				? this.m_defaultSupportChains 
				: stateSupplier); //Pair.of(Objects.requireNonNull(model.first()), Objects.requireNonNull(model.second())));
	} // end registerCustomSupportChain()
	
} // end client