package com.gsr.gsr_yatm;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class YATMBlockStateProvider extends BlockStateProvider
{

	public YATMBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper)
	{
		super(output, modid, exFileHelper);
	} // end constructor



	@Override
	protected void registerStatesAndModels()
	{
		this.createAllBlock(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/soul_afflicted_rubber_leaves_flowering"), "blocks/soul_afflicted_rubber_leaves_flowering");

		ResourceLocation rubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side");
		ResourceLocation rubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_top");
		ResourceLocation strippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_stripped");
		ResourceLocation partiallyStrippedRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped");
		//ResourceLocation partiallyStrippedRubberLogSideTextureMirrored = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_mirrored");
		ResourceLocation partiallyStrippedLeakingRubberLogSideTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_latex");
		//ResourceLocation partiallyStrippedLeakingRubberLogSideTextureMirrored = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_side_partially_stripped_latex_mirrored");
		ResourceLocation strippedRubberLogTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_log_top_stripped");
		this.createPillar(YATMBlocks.RUBBER_WOOD.get(), rubberLogSideTexture, rubberLogSideTexture, "block/rubber_wood");
		this.createPillar(YATMBlocks.STRIPPED_RUBBER_LOG.get(), strippedRubberLogSideTexture, strippedRubberLogTopTexture, "block/stripped_rubber_log");
		this.createPillar(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), strippedRubberLogSideTexture, strippedRubberLogSideTexture, "block/stripped_rubber_wood");
		this.createPartiallyStrippedLog(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), partiallyStrippedRubberLogSideTexture, partiallyStrippedLeakingRubberLogSideTexture, rubberLogSideTexture, rubberLogTopTexture, "blocks/partially_stripped_rubber_log", true);
		ResourceLocation rubberPlanksTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_planks");
		this.createAllBlock(YATMBlocks.RUBBER_PLANKS.get(), rubberPlanksTexture, "block/rubber_planks");
		ResourceLocation fancyRubberPlanksTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/fancy_rubber_planks");
		this.createPillar(YATMBlocks.FANCY_RUBBER_PLANKS.get(), fancyRubberPlanksTexture, fancyRubberPlanksTexture, "block/fancy_rubber_planks");
		
		this.createSlab(YATMBlocks.RUBBER_SLAB.get(), "block/rubber_slab", rubberPlanksTexture, new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, "block/rubber_planks")));
		this.createStair(YATMBlocks.RUBBER_STAIRS.get(), "block/rubber", rubberPlanksTexture);
		this.createFence(YATMBlocks.RUBBER_FENCE.get(), "block/rubber", "item/rubber_fence", rubberPlanksTexture);
		this.createFenceGate(YATMBlocks.RUBBER_FENCE_GATE.get(), "item/rubber_fence_gate", rubberPlanksTexture);
		ResourceLocation rubberDoorTopTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_door_top");
		ResourceLocation rubberDoorBottomTexture = new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_door_bottom");
		this.createDoor(YATMBlocks.RUBBER_DOOR.get(), YATMItems.RUBBER_DOOR_ITEM.get(), rubberDoorBottomTexture, rubberDoorTopTexture);
		this.createTrapDoor(YATMBlocks.RUBBER_TRAPDOOR.get(), "item/rubber_trapdoor", new ResourceLocation(YetAnotherTechMod.MODID, "block/plant/rubber/rubber_trapdoor_top"));
		this.createPressurePlate(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), "item/rubber_pressure_plate", rubberPlanksTexture);
		this.createButton(YATMBlocks.RUBBER_BUTTON.get(),"item/rubber_button", rubberPlanksTexture);
		
		// sign
		// the newer and fancier sign
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

	private void createStair(Block block, String name, ResourceLocation texture) 
	{ 
		this.stairsBlock((StairBlock)block, name, texture, texture, texture);
		this.simpleBlockItem(block, new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, name + "_stairs")));
		//facing, half, shape, waterlogged
		//west, bottom, straight, false
	} // end createStair()
	
	private void createSlab(Block block, String name, ResourceLocation texture, ModelFile doubleSlab) 
	{
		String bottomName = name + "_bottom";
		String topName = name + "_top";
		this.models().slab(bottomName, texture, texture, texture);
		this.models().slabTop(topName, texture, texture, texture);
		ModelFile bottomSlab = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, bottomName));
		ModelFile topSlab = new ModelFile.UncheckedModelFile(new ResourceLocation(YetAnotherTechMod.MODID, topName));
		this.slabBlock((SlabBlock)block, bottomSlab, topSlab, doubleSlab);		
		this.simpleBlockItem(block, bottomSlab);
		//this.getVariantBuilder(block).forAllStates((bs) -> forSlab(bs, bottomSlab, topSlab, doubleSlab));
	} // end createSlab()
	
	private void createFence(Block block, String name, String itemName, ResourceLocation texture) 
	{
		this.fenceBlock((FenceBlock)block, name, texture);
		this.itemModels().fenceInventory(itemName, texture);
	} // end createFence()
	
	private void createFenceGate(Block block, String itemName, ResourceLocation texture) 
	{
		this.fenceGateBlock((FenceGateBlock)block, texture);
		this.itemModels().fenceGate(itemName, texture);
	} // end createFenceGate()
	
	private void createDoor(Block block, Item item, ResourceLocation bottomTexture, ResourceLocation topTexture) 
	{
		this.doorBlock((DoorBlock)block, bottomTexture, topTexture);
		this.itemModels().basicItem(item);
	} // end createDoor()
	
	private void createTrapDoor(Block block, String itemName, ResourceLocation texture) 
	{
		this.trapdoorBlock((TrapDoorBlock)block, texture, true);
		this.itemModels().trapdoorBottom(itemName, texture);
	}
	
	private void createPressurePlate(Block block, String itemName, ResourceLocation texture) 
	{
		this.pressurePlateBlock((PressurePlateBlock)block, texture);
		this.itemModels().pressurePlate(itemName, texture);
	}
	
 	private void createButton(Block block, String itemName, ResourceLocation texture) 
	{
		this.buttonBlock((ButtonBlock)block, texture);
		this.itemModels().buttonInventory(itemName, texture);
	} // end createButton()
	
	
	private void createPartiallyStrippedLog(@NotNull Block block, ResourceLocation strippedSide, ResourceLocation strippedSideLeaking, ResourceLocation plainSide, ResourceLocation topBottom, String name, boolean leakingForItem)
	{
		String dryName = name + "_dry";
		String leakingName = name + "_leaking";
		this.models().cube(dryName, topBottom, topBottom, strippedSide, plainSide, plainSide, plainSide).texture("particle", strippedSide);
		this.models().cube(leakingName, topBottom, topBottom, strippedSideLeaking, plainSide, plainSide, plainSide).texture("particle", strippedSideLeaking);
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

	
	public static ConfiguredModel[] forSlab(BlockState bs, ModelFile bottomSlab, ModelFile topSlab, ModelFile doubleSlab)
	{
		// waterlogged: true/false

		ModelFile f = switch (bs.getValue(SlabBlock.TYPE))
		{
			case BOTTOM -> bottomSlab;
			case TOP -> topSlab;
			case DOUBLE -> doubleSlab;
			default -> throw new IllegalArgumentException("unexpected arguement for a SlabType BlockState Property: " + bs.getValue(SlabBlock.TYPE));

		};

		return new ConfiguredModel[] { new ConfiguredModel(f, 0, 0, true) };
	} // end forSlab()

	
	
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