package com.gsr.gsr_yatm;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import net.minecraftforge.client.model.generators.ConfiguredModel;

public class YATMBlockStateProvider extends BlockStateProvider
{

	public YATMBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper)
	{
		super(output, modid, exFileHelper);
	} // end constructor



	@Override
	protected void registerStatesAndModels()
	{
//		this.models().cubeAll("block/plant/rubber/soul_afflicted_rubber_leaves_flowering", new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_flowering"));
//		ModelFile SoulAfflictedRubberLeavesFlowering = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_flowering"));
//		this.simpleBlockItem(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), SoulAfflictedRubberLeavesFlowering);
//		this.getVariantBuilder(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get())
//		.forAllStates((blockState) -> new ConfiguredModel[] {new ConfiguredModel(SoulAfflictedRubberLeavesFlowering)});
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_flowering"), "blocks/soul_afflicted_rubber_leaves_flowering");

		ResourceLocation rubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side");
		ResourceLocation rubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_top");
		ResourceLocation strippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_stripped");
		ResourceLocation partiallyStrippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped");
		ResourceLocation partiallyStrippedRubberLogSideTextureMirrored = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_mirrored");
		ResourceLocation partiallyStrippedLeakingRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_latex");
		ResourceLocation partiallyStrippedLeakingRubberLogSideTextureMirrored = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_latex_mirrored");
		ResourceLocation strippedRubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_top_stripped");
		this.createPillar(YATMBlocks.RUBBER_WOOD.get(), rubberLogSideTexture, rubberLogSideTexture, "blocks/rubber_wood");
		this.createPillar(YATMBlocks.STRIPPED_RUBBER_LOG.get(), strippedRubberLogSideTexture, strippedRubberLogTopTexture, "blocks/stripped_rubber_log");
		this.createPillar(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), strippedRubberLogSideTexture, strippedRubberLogSideTexture, "blocks/stripped_rubber_wood");
		this.createPartiallyStrippedLog(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), partiallyStrippedRubberLogSideTexture, partiallyStrippedRubberLogSideTextureMirrored, partiallyStrippedLeakingRubberLogSideTexture, partiallyStrippedLeakingRubberLogSideTextureMirrored, rubberLogSideTexture, rubberLogTopTexture, "blocks/partially_stripped_rubber_log", true);
	} // end registerStatesAndModels



	private void createPillar(Block block, ResourceLocation side, ResourceLocation topBottom, String nameForModel)
	{
		this.models().cubeBottomTop(nameForModel, side, topBottom, topBottom);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, nameForModel));
		this.simpleBlockItem(block, model);
		this.getVariantBuilder(block).forAllStates((bs) -> forPillarAxis(bs, model));
	} // end createLog()

	private void createAllBlock(Block block, ResourceLocation texture, String name)
	{
		this.models().cubeAll(name, texture);
		ModelFile model = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name));
		this.simpleBlockItem(block, model);
		this.getVariantBuilder(block).forAllStates((blockState) -> new ConfiguredModel[]
		{ new ConfiguredModel(model) });
	} // end createAllBlock()

	private void createPartiallyStrippedLog(@NotNull Block block, ResourceLocation strippedSide, ResourceLocation strippedSideMirrored, ResourceLocation strippedSideLeaking, ResourceLocation strippedSideLeakingMirrored, ResourceLocation plainSide, ResourceLocation topBottom, String name, boolean leakingForItem)
	{
		String dryName = name + "_dry";
		String leakingName = name + "_leaking";
		this.models().cube(dryName, topBottom, topBottom, strippedSideMirrored, plainSide, strippedSide, plainSide);
		this.models().cube(leakingName, topBottom, topBottom, strippedSideLeakingMirrored, plainSide, strippedSideLeaking, plainSide);
		ModelFile dryModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, dryName));
		ModelFile leakingModel = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, leakingName));
		this.simpleBlockItem(block, leakingForItem ? leakingModel : dryModel);
		this.getVariantBuilder(block).forAllStates((bs) -> forPartiallyStrippedBlock(bs, dryModel, leakingModel));
	} // end createPartiallyStrippedLog

	public static ConfiguredModel[] forPillarAxis(BlockState bs, ModelFile model)
	{
		switch (bs.getValue(RotatedPillarBlock.AXIS))
		{
			case X:
			{
				return new ConfiguredModel[]
				{ new ConfiguredModel(model, 90, 90, false) };
			}
			case Y:
			{
				return new ConfiguredModel[]
				{ new ConfiguredModel(model) };
			}
			case Z:
			{
				return new ConfiguredModel[]
				{ new ConfiguredModel(model, 90, 0, false) };
			}
		}
		return null;
	} // end forPillarAxis()

	public static ConfiguredModel[] forPartiallyStrippedBlock(BlockState bs, ModelFile dryModel, ModelFile leakingModel)
	{
		Direction.Axis axis = bs.getValue(RotatedPillarBlock.AXIS);
		ModelFile f = bs.getValue(StrippedSapLogBlock.FLOWING) ? leakingModel : dryModel;
		int xRot = axis == Direction.Axis.Y ? 0 : 90;
		int yRot = getRotForYPartiallyStrippedFromFacing(bs.getValue(StrippedSapLogBlock.FACING)) + (axis == Direction.Axis.X ? 90 : 0);
		return new ConfiguredModel[]
		{ new ConfiguredModel(f, xRot, yRot, false) };
	} // end forPillarAxis()

	private static int getRotForYPartiallyStrippedFromFacing(Direction dir)
	{
		switch (dir)
		{
			case DOWN:
			case NORTH:
				return 0;
			case EAST:
				return 90;
			case SOUTH:
				return 180;
			case WEST:
				return 270;
			case UP:
			default:
				throw new IllegalArgumentException("the provied direction: " + dir + ", it isn't supported for this particular operation");

		}
	} // end getRotForYPartiallyStrippedFromFacing()

} // end class