package com.gsr.gsr_yatm.data_generation;

import java.util.Objects;
import java.util.function.Consumer;

import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange.ToolPartExchangeRecipeBuilder;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.ingredient.FluidStackIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.ItemStackIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.ItemTagIngredient;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipeBuilder;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class YATMRecipeProvider extends RecipeProvider
{

	public YATMRecipeProvider(@NotNull PackOutput output)
	{
		super(Objects.requireNonNull(output));
	} // end constructor
	
	

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> writer)
	{
//		this.addNineToOne(writer, YATMItemTags.FORGE_COPPER_NUGGETS_KEY, Items.COPPER_INGOT, YetAnotherTechMod.MODID + ":copper_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, Items.NETHERITE_INGOT, YetAnotherTechMod.MODID + ":netherite_ingot_from_shapeless_crafting");
//		this.addNineToOne(writer, YATMItemTags.FORGE_SILVER_NUGGETS_KEY, YATMItems.SILVER_INGOT.get(), YetAnotherTechMod.MODID + ":silver_ingot_from_shapeless_crafting");
//		this.addOneToNine(writer, Tags.Items.INGOTS_COPPER, YATMItems.COPPER_NUGGET.get(), YetAnotherTechMod.MODID + ":copper_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, Tags.Items.INGOTS_NETHERITE, YATMItems.NETHERITE_NUGGET.get(), YetAnotherTechMod.MODID + ":netherite_nuggets_from_shapeless_crafting");
//		this.addOneToNine(writer, YATMItemTags.FORGE_SILVER_INGOTS_KEY, YATMItems.SILVER_NUGGET.get(), YetAnotherTechMod.MODID + ":silver_nuggets_from_shapeless_crafting");
//
//		this.addOneToX(writer, Items.HONEYCOMB, YATMItems.WAX_BIT_ITEM.get(), 8, YetAnotherTechMod.MODID + ":wax_bit_from_shapeless_crafting");
//		this.addXToX(writer, YATMItems.WAX_BIT_ITEM.get(), Items.HONEYCOMB, 8, 1, YetAnotherTechMod.MODID + ":honeycomb_from_shapeless_crafting");
//		
		this.addLargeHeatSink(writer, YetAnotherTechMod.MODID + ":large_copper_heat_sink_from_shape_crafting", YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), Tags.Items.INGOTS_COPPER, YATMItemTags.UNOXIDIXED_COPPER_BLOCKS_KEY);

		
		this.addRubberWoodCoreRecipes(writer);
		this.addSoulAfflictedRubberWoodCoreRecipes(writer);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.HANGING_POT_HOOK_ITEM.get(), 1)
		.pattern("c ")
		.pattern("p ")
		.define('c', Items.CHAIN)
		.define('p', Items.FLOWER_POT) // possibly make tag
		.unlockedBy("has_pot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.FLOWER_POT).build()))
		.save(writer, YetAnotherTechMod.MODID + ":hanging_pot_from_shaped_crafting");
		
		this.addCandleLanterns(writer);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.GRAFTING_TABLE_ITEM.get(), 1)
		.pattern("f ")
		.pattern("t ")
		.define('f', YATMItems.FOLIAR_STEEL.get())
		.define('t', Items.CRAFTING_TABLE)
		.unlockedBy("has_pot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CRAFTING_TABLE).build()))
		.save(writer, YetAnotherTechMod.MODID + ":grafting_table_from_shaped_crafting");
		
//		this.addOneToX(writer, YATMItems.RUBBER_BAR.get(), YATMItems.RUBBER_SCRAP.get(), 4, YetAnotherTechMod.MODID + ":rubber_scrap_from_ingot_shapeless_crafting");
//		this.addOneToX(writer, YATMItems.RUBBER_SCRAP_BALL.get(), YATMItems.RUBBER_SCRAP.get(), 4, YetAnotherTechMod.MODID + ":rubber_scrap_from_scrap_ball_shapeless_crafting");
//		this.addTwoByTwoToOne(writer, YATMItems.RUBBER_SCRAP.get(), YATMItems.RUBBER_SCRAP_BALL.get(), YetAnotherTechMod.MODID + ":rubber_scrap_ball_from_shaped_crafting");
//		this.addSmelting(writer, new ItemLike[] {YATMItems.LATEX_BUCKET.get()}, YATMItems.RUBBER_BLOCK_ITEM.get(), .3f, 20, YetAnotherTechMod.MODID + ":rubber_block_from_latex_smelting");
//		this.addSmelting(writer, new ItemLike[] {YATMItems.RUBBER_SCRAP_BALL.get()}, YATMItems.RUBBER_BAR.get(), new ItemLike[] {YATMItems.RUBBER_SCRAP_BALL.get()}, .3f, 20, YetAnotherTechMod.MODID + ":rubber_bar_from_scrap_ball_smelting");
		this.addOneToNine(writer, YATMItemTags.FORGE_STORAGE_BLOCKS_RUBBER_KEY, YATMItems.RUBBER_BAR.get(), YetAnotherTechMod.MODID + ":rubber_bar_from_block_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_RUBBER_INGOTS_KEY, YATMItems.RUBBER_BLOCK_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_block_from_bar_shapeless_crafting");
		
		this.addBlasting(writer, YATMItemTags.FOLIAR_STEEL_ORES_KEY, YATMItems.FOLIAR_STEEL.get(), .1f, 100, YetAnotherTechMod.MODID + ":foliar_steel_from_ore_blasting");
		this.addSmelting(writer, YATMItemTags.FOLIAR_STEEL_ORES_KEY, YATMItems.FOLIAR_STEEL.get(), .1f, 200, YetAnotherTechMod.MODID + ":foliar_steel_from_ore_smelting");
		this.addOneToNine(writer, YATMItems.FOLIAR_STEEL_BLOCK_ITEM.get(), YATMItems.FOLIAR_STEEL.get(), YetAnotherTechMod.MODID + ":foliar_steel_from_block_shapeless_crafting");
		this.addNineToOne(writer, YATMItems.FOLIAR_STEEL.get(), YATMItems.FOLIAR_STEEL_BLOCK_ITEM.get(), YetAnotherTechMod.MODID + ":foliar_steel_block_from_bar_shapeless_crafting");
		
		this.addSmelting(writer, new ItemLike[] {YATMItems.VARIEGATED_CACTUS_ITEM.get()}, Items.GREEN_DYE, 1.0f, 100, YetAnotherTechMod.MODID + ":green_dye_from_variegated_cactus_smelting");
		
		
		
		this.addExtraction(writer, YATMItemTags.LATEX_EXTRACTABLE_LOGS_KEY, new ItemStack(YATMItems.WOOD_PULP.get(), 6), new FluidStack(YATMFluids.LATEX.get(), 120), 12, 246, YetAnotherTechMod.MODID + ":latex_from_log_extraction");
		this.addExtraction(writer, YATMItems.RUBBER_LEAVES_OLD_ITEM.get(), new ItemStack(YATMItems.WOOD_PULP.get(), 1), new FluidStack(YATMFluids.LATEX.get(), 40), 4, 62, YetAnotherTechMod.MODID + ":latex_from_old_leaves_extraction");
		this.addExtraction(writer, YATMItems.RUBBER_LEAVES_FLOWERING_ITEM.get(), new ItemStack(YATMItems.LEAF_MULCH_ITEM.get(), 2), new FluidStack(YATMFluids.LATEX.get(), 30), 4, 46, YetAnotherTechMod.MODID + ":latex_from_flowering_leaves_extraction");
		this.addExtraction(writer, YATMItems.RUBBER_LEAVES_YOUNG_ITEM.get(), new ItemStack(YATMItems.LEAF_MULCH_ITEM.get(), 1), new FluidStack(YATMFluids.LATEX.get(), 20), 4, 34, YetAnotherTechMod.MODID + ":latex_from_young_leaves_extraction");
		this.addExtraction(writer, YATMItems.RUBBER_MERISTEM_ITEM.get(), new FluidStack(YATMFluids.LATEX.get(), 2), 1, 12, YetAnotherTechMod.MODID + ":latex_from_meristem_extraction");
		
		this.addExtraction(writer, YATMItemTags.SOUL_SAP_EXTRACTABLE_LOGS_KEY, new ItemStack(YATMItems.WOOD_PULP.get(), 6), new FluidStack(YATMFluids.SOUL_SAP.get(), 120), 12, 246, YetAnotherTechMod.MODID + ":soul_sap_from_log_extraction");
		this.addExtraction(writer, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_OLD_ITEM.get(), new ItemStack(YATMItems.WOOD_PULP.get(), 1), new FluidStack(YATMFluids.SOUL_SAP.get(), 40), 4, 62, YetAnotherTechMod.MODID + ":soul_sap_from_old_leaves_extraction");
		this.addExtraction(writer, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING_ITEM.get(), new ItemStack(YATMItems.LEAF_MULCH_ITEM.get(), 2), new FluidStack(YATMFluids.SOUL_SAP.get(), 40), 4, 46, YetAnotherTechMod.MODID + ":soul_sap_from_flowering_leaves_extraction");
		this.addExtraction(writer, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG_ITEM.get(), new ItemStack(YATMItems.LEAF_MULCH_ITEM.get(), 1), new FluidStack(YATMFluids.SOUL_SAP.get(), 20), 4, 34, YetAnotherTechMod.MODID + ":soul_sap_from_young_leaves_extraction");
		this.addExtraction(writer, YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get(), new FluidStack(YATMFluids.SOUL_SAP.get(), 2), 1, 12, YetAnotherTechMod.MODID + ":soul_sap_from_meristem_extraction");
	
		
		
		
		this.addBoilingRecipe(writer, new FluidStack(YATMFluids.SOUL_SAP.get(), 3), new FluidStack(YATMFluids.SOUL_SYRUP.get(), 1), 722, 3, YetAnotherTechMod.MODID + ":soul_syrup_from_soul_sap_boiling");
	
		
		
//		this.addWireRecipes(writer);
		
		
		
		// TODO, create a fluidIngredient, so we can use tags or specifics
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ENDER.get(), 250), Tags.Items.SAND, new ItemStack(Items.ENDER_PEARL), false, 0, 300, YetAnotherTechMod.MODID + ":ender_pearl_from_crystallization");
		this.addNetherStarCrystallizationProgression(writer);
		
		// TODO, make grinding recipes time proportional to hardness, and current proportional to the tool required.
		this.addRootedSoilReversions(writer);
		this.addGrinderBrickCrackings(writer);
		this.addGrinderStoneTowardsCobblestone(writer);
		this.addGrindingRecipe(writer, Items.COBBLESTONE, new ItemStack(Items.GRAVEL), 3, 80, YetAnotherTechMod.MODID + ":gravel_from_cobblestone_grinding");
		this.addGrindToSand(writer);
		this.addGrindPurpur(writer);
		
		this.addGrindingRecipe(writer, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING_ITEM.get(), new ItemStack(YATMItems.SOUL_AFFLICTED_LEAF_MULCH_ITEM.get()), 2, 20, YetAnotherTechMod.MODID + ":soul_leaf_mulch_from_flowering_leaf_grinding");
		this.addGrindingRecipe(writer, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG_ITEM.get(), new ItemStack(YATMItems.SOUL_AFFLICTED_LEAF_MULCH_ITEM.get()), 1, 20, YetAnotherTechMod.MODID + ":soul_leaf_mulch_from_young_leaf_grinding");
		
		
		
		
		this.addChorusBiolings(writer);
		this.addBiolingRecipe(writer, Items.POISONOUS_POTATO, new FluidStack(YATMFluids.BIO.get(), 200), 2, 20, YetAnotherTechMod.MODID + ":biofluid_from_poisonous_potato_bioling");
		
		
		
		this.addCottonRecipes(writer);
		this.addOneToX(writer, YATMItems.SPIDER_VINE_FRUITS.get(), Items.SPIDER_EYE, 3, YetAnotherTechMod.MODID + ":spider_eyes_from_spider_vine_fruits_shapeless_crafting");
		
		
		this.addPoweredToolRecipes(writer);
		
	} // end buildRecipes()
	
	private void addRubberWoodCoreRecipes(Consumer<FinishedRecipe> writer) 
	{
		this.addTwoByTwoToX(writer, YATMItems.RUBBER_LOG_ITEM.get(), YATMItems.RUBBER_WOOD_ITEM.get(), 3, YetAnotherTechMod.MODID + ":rubber_wood_from_shaped_crafting");
		this.addTwoByTwoToX(writer, YATMItems.STRIPPED_RUBBER_LOG_ITEM.get(), YATMItems.STRIPPED_RUBBER_WOOD_ITEM.get(), 3, YetAnotherTechMod.MODID + ":stripped_rubber_wood_from_shaped_crafting");
		this.addOneToFour(writer, YATMItemTags.RUBBER_TREE_LOGS_KEY, YATMItems.RUBBER_PLANKS_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_planks_from_shapeless_crafting");
		this.addFancyPlanks(writer, YATMItems.RUBBER_PLANKS_ITEM.get(), YATMItems.RUBBER_SLAB_ITEM.get(), YATMItems.FANCY_RUBBER_PLANKS_ITEM.get(), YetAnotherTechMod.MODID + ":fancy_rubber_planks_from_shaped_crafting");
		this.addStairs(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_STAIRS_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_stairs_from_shaped_crafting");
		this.addSlabs(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_SLAB_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_slab_from_shaped_crafting");
		this.addFence(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_FENCE_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_fence_from_shaped_crafting");
		this.addFenceGate(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_FENCE_GATE_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_fence_gate_from_shaped_crafting");
		this.addDoor(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_DOOR_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_door_from_shaped_crafting");
		this.addTrapdoor(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_TRAPDOOR_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_trapdoor_from_shaped_crafting");
		this.addPressurePlate(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_PRESSURE_PLATE_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_pressure_plate_from_shaped_crafting");
		this.addButton(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_BUTTON_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_button_from_shaped_crafting");
		
		this.addSign(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_SIGN_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_sign_from_shaped_crafting");
		this.addHangingSign(writer, YATMItems.STRIPPED_RUBBER_LOG_ITEM.get(), YATMItems.RUBBER_HANGING_SIGN_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_hanging_sign_from_shaped_crafting");
		this.addBoat(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_BOAT_ITEM.get(), "rubber_boat_from_shaped_crafting");
		this.addChestBoat(writer, YATMItems.RUBBER_BOAT_ITEM.get(), YATMItems.RUBBER_CHEST_BOAT_ITEM.get(), "rubber_chest_boat_from_shapeless_crafting");

	} // end addRubberWoodCoreRecipes()
	
	private void addSoulAfflictedRubberWoodCoreRecipes(Consumer<FinishedRecipe> writer) 
	{
		this.addTwoByTwoToX(writer, YATMItems.SOUL_AFFLICTED_RUBBER_LOG_ITEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_WOOD_ITEM.get(), 3, YetAnotherTechMod.MODID + ":soul_afflicted_rubber_wood_from_shaped_crafting");
		this.addTwoByTwoToX(writer, YATMItems.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG_ITEM.get(), YATMItems.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD_ITEM.get(), 3, YetAnotherTechMod.MODID + ":soul_afflicted_stripped_rubber_wood_from_shaped_crafting");
		this.addOneToFour(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_planks_from_shapeless_crafting");
		this.addFancyPlanks(writer, YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS_ITEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_SLAB_ITEM.get(), YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_fancy_rubber_planks_tiled_from_shaped_crafting");

		// TODO, make better obtainable, such as not conflicting with the button crafting recipe
		this.addOneToX(writer, YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED_ITEM.get(), YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_ITEM.get(), 1, YetAnotherTechMod.MODID + ":soul_afflicted_fancy_rubber_planks_from_tiled_from_shapeless_crafting");
		this.addOneToX(writer, YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_ITEM.get(), YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED_ITEM.get(), 1, YetAnotherTechMod.MODID + ":soul_afflicted_fancy_rubber_planks_tiled_from_untiled_from_shapeless_crafting");
		
		this.addStairs(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_STAIRS_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_stairs_from_shaped_crafting");
		this.addSlabs(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_SLAB_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_slab_from_shaped_crafting");
		this.addFence(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_FENCE_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_fence_from_shaped_crafting");
		this.addFenceGate(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_FENCE_GATE_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_fence_gate_from_shaped_crafting");
		this.addDoor(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_DOOR_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_door_from_shaped_crafting");
		this.addTrapdoor(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_TRAPDOOR_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_trapdoor_from_shaped_crafting");
		this.addPressurePlate(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_pressure_plate_from_shaped_crafting");
		this.addButton(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_BUTTON_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_button_from_shaped_crafting");
		
		this.addSign(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_SIGN_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_sign_from_shaped_crafting");
		this.addHangingSign(writer, YATMItems.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG_ITEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_HANGING_SIGN_ITEM.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_hanging_sign_from_shaped_crafting");
		this.addBoat(writer, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_BOAT_ITEM.get(), "soul_afflicted_rubber_boat_from_shaped_crafting");
		this.addChestBoat(writer, YATMItems.SOUL_AFFLICTED_RUBBER_BOAT_ITEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_CHEST_BOAT_ITEM.get(), "soul_afflicted_rubber_chest_boat_from_shapeless_crafting");

	} // end addSoulAfflictedRubberWoodCoreRecipes()

	private void addCandleLanterns(@NotNull Consumer<FinishedRecipe> writer) 
	{
		TriConsumer<Item, String, Item> forCandle = (candle, color, lantern) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, lantern, 1)
		.pattern("iii")
		.pattern("ici")
		.pattern("iii")
		.define('i', Tags.Items.NUGGETS_IRON)
		.define('c', candle)
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(candle).build()))
		.save(writer, YetAnotherTechMod.MODID + ":" + color + "candle_lantern_from_shaped_crafting");
		
		forCandle.accept(Items.CANDLE, "", YATMItems.CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.WHITE_CANDLE, "white_", YATMItems.WHITE_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.ORANGE_CANDLE, "orange_", YATMItems.ORANGE_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.MAGENTA_CANDLE, "magenta_", YATMItems.MAGENTA_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.LIGHT_BLUE_CANDLE, "light_blue_", YATMItems.LIGHT_BLUE_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.YELLOW_CANDLE, "yellow_", YATMItems.YELLOW_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.LIME_CANDLE, "lime_", YATMItems.LIME_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.PINK_CANDLE, "pink_", YATMItems.PINK_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.GRAY_CANDLE, "gray_", YATMItems.GRAY_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.LIGHT_GRAY_CANDLE, "light_gray_", YATMItems.LIGHT_GRAY_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.CYAN_CANDLE, "cyan_", YATMItems.CYAN_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.PURPLE_CANDLE, "purple_", YATMItems.PURPLE_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.BLUE_CANDLE, "blue_", YATMItems.BLUE_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.BROWN_CANDLE, "brown_", YATMItems.BROWN_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.GREEN_CANDLE, "green_", YATMItems.GREEN_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.RED_CANDLE, "red_", YATMItems.RED_CANDLE_LANTERN_ITEM.get());
		forCandle.accept(Items.BLACK_CANDLE, "black_", YATMItems.BLACK_CANDLE_LANTERN_ITEM.get());		
	} // end addCandleLanterns()
	
	private void addCottonRecipes(Consumer<FinishedRecipe> writer) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.SPINNING_WHEEL_ITEM.get(), 1)
		.pattern(" sw")
		.pattern("www")
		.pattern("w w")
		.define('w', ItemTags.PLANKS)
		.define('s', Items.STRING)
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
		.save(writer, YetAnotherTechMod.MODID + ":spinning_wheel_shaped_crafting");
		
		
		this.addOneToX(writer, YATMItems.COTTON_BOLLS.get(), YATMItems.RAW_COTTON_FIBER.get(), 1, YetAnotherTechMod.MODID + ":raw_cotton_fiber_from_cotton_bolls_shapeless_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING, 3)
		.pattern("   ")
		.pattern("www")
		.pattern("   ")
		.define('w', YATMItems.RAW_COTTON_FIBER.get())
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(Items.STRING).build()))
		.save(writer, YetAnotherTechMod.MODID + ":string_from_raw_cotton_fiber_shaped_crafting");
		
		this.addSpinningRecipe(writer, YATMItems.RAW_COTTON_FIBER.get(), new ItemStack(Items.STRING, 2), YetAnotherTechMod.MODID + ":string_from_raw_cotton_fiber_spinning");
		this.addSpinningRecipe(writer, ItemTags.WOOL, new ItemStack(Items.STRING, 4), YetAnotherTechMod.MODID + ":string_from_wool_spinning");
		/*TODO, perhaps make this better proportional to the input cost, or perhaps make a cheap way to make carpets so we can pretend giving two string is appropriate and not just because 8 doesn't divide without remainder into 3*/
		this.addSpinningRecipe(writer, ItemTags.WOOL_CARPETS, new ItemStack(Items.STRING, 2), YetAnotherTechMod.MODID + ":string_from_wool_carpet_spinning");

	}

	private void addNetherStarCrystallizationProgression(Consumer<FinishedRecipe> writer) 
	{
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ESSENCE_OF_DECAY.get(), 1000), YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, new ItemStack(YATMItems.STAR_SEED.get()), true, 0, 300, YetAnotherTechMod.MODID + ":star_seed_from_crystallization");
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 1000), YATMItems.STAR_SEED.get(), new ItemStack(YATMItems.STAR_GERMLING.get()), true, 0, 200, YetAnotherTechMod.MODID + ":star_germling_from_crystallization");
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 2000), YATMItems.STAR_GERMLING.get(), new ItemStack(YATMItems.STAR_SPROUT.get()), true, 0, 400, YetAnotherTechMod.MODID + ":star_sprout_from_crystallization");
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 4000), YATMItems.STAR_SPROUT.get(), new ItemStack(YATMItems.STAR_ADOLESCENT.get()), true, 0, 800, YetAnotherTechMod.MODID + ":star_adolescent_from_crystallization");
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 8000), YATMItems.STAR_ADOLESCENT.get(), new ItemStack(Items.NETHER_STAR), true, 0, 1600, YetAnotherTechMod.MODID + ":nether_star_from_crystallization");
	} // end addNetherStarCrystallizationProgression()
	
	private void addRootedSoilReversions(Consumer<FinishedRecipe> writer) 
	{
		this.addGrindingRecipe(writer, YATMItemTags.FORGE_ROOTED_DIRT_KEY, new ItemStack(Items.DIRT), 2, 40, YetAnotherTechMod.MODID + ":dirt_from_rooted_grinding");
		this.addGrindingRecipe(writer, YATMItems.ROOTED_SOUL_SOIL_ITEM.get(), new ItemStack(Items.SOUL_SOIL), 2, 40, YetAnotherTechMod.MODID + ":soul_soil_from_rooted_grinding");
	} // end addRootedSoilReversions()
	
	private void addGrinderBrickCrackings(Consumer<FinishedRecipe> writer) 
	{
		this.addGrindingRecipe(writer, Items.STONE_BRICKS, new ItemStack(Items.CRACKED_STONE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_stone_bricks_from_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.DEEPSLATE_BRICKS, new ItemStack(Items.CRACKED_DEEPSLATE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_deepslate_bricks_from_deepslate_bricks_grinding");
		this.addGrindingRecipe(writer, Items.DEEPSLATE_TILES, new ItemStack(Items.CRACKED_DEEPSLATE_TILES), 1, 20, YetAnotherTechMod.MODID + ":cracked_deepslate_tiles_from_deepslate_tiles_grinding");
		this.addGrindingRecipe(writer, Items.NETHER_BRICKS, new ItemStack(Items.CRACKED_NETHER_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_nether_bricks_from_nether_brick_grinding");
		this.addGrindingRecipe(writer, Items.POLISHED_BLACKSTONE_BRICKS, new ItemStack(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_polished_blackstone_bricks_from_polished_blackstone_brick_grinding");
		this.addGrindingRecipe(writer, Items.INFESTED_STONE_BRICKS, new ItemStack(Items.INFESTED_CRACKED_STONE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":infested_cracked_stone_bricks_from_infested_stone_bricks_grinding");
	} // end addGrinderBrickCrackings()
	
	private void addGrinderStoneTowardsCobblestone(Consumer<FinishedRecipe> writer) 
	{
		this.addGrindingRecipe(writer, Items.STONE, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_grinding");
		this.addGrindingRecipe(writer, Items.INFESTED_STONE, new ItemStack(Items.INFESTED_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_infested_stone_grinding");
		this.addGrindingRecipe(writer, Items.CRACKED_STONE_BRICKS, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_cracked_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.INFESTED_CRACKED_STONE_BRICKS, new ItemStack(Items.INFESTED_COBBLESTONE), 1, 20, YetAnotherTechMod.MODID + ":infested_cobblestone_from_infested_cracked_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.CHISELED_STONE_BRICKS, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_chiseled_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.INFESTED_CHISELED_STONE_BRICKS, new ItemStack(Items.INFESTED_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":infested_cobblestone_from_infested_chiseled_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.MOSSY_STONE_BRICKS, new ItemStack(Items.MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.INFESTED_MOSSY_STONE_BRICKS, new ItemStack(Items./* INFESTED_ */MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_infested_mossy_stone_bricks_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_STONE, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_smooth_stone_grinding");

		this.addGrindingRecipe(writer, Items.STONE_STAIRS, 4, new ItemStack(Items.COBBLESTONE, 3), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_stairs_grinding");
		this.addGrindingRecipe(writer, Items.STONE_BRICK_STAIRS, 4, new ItemStack(Items.COBBLESTONE, 3), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_brick_stairs_grinding");
		this.addGrindingRecipe(writer, Items.MOSSY_STONE_BRICK_STAIRS, 4, new ItemStack(Items.MOSSY_COBBLESTONE, 3), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_brick_stairs_grinding");
		
		this.addGrindingRecipe(writer, Items.STONE_SLAB, 2, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_slab_grinding");
		this.addGrindingRecipe(writer, Items.STONE_BRICK_SLAB, 2, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_brick_slab_grinding");
		this.addGrindingRecipe(writer, Items.MOSSY_STONE_BRICK_SLAB, 2, new ItemStack(Items.MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_brick_slab_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_STONE_SLAB, 2, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_smooth_stone_slab_grinding");
		
		this.addGrindingRecipe(writer, Items.STONE_BRICK_WALL, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_brick_wall_grinding");
		this.addGrindingRecipe(writer, Items.MOSSY_STONE_BRICK_WALL, new ItemStack(Items.MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_brick_wall_grinding");
		
	} // end addGrinderBrickCrackings()
	
	private void addGrindToSand(Consumer<FinishedRecipe> writer) 
	{
		this.addGrindingRecipe(writer, Items.GRAVEL, new ItemStack(Items.SAND), 3, 80, YetAnotherTechMod.MODID + ":sand_from_gravel_grinding");

		this.addGrindingRecipe(writer, Tags.Items.GLASS, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_glass_grinding");
		this.addGrindingRecipe(writer, Tags.Items.GLASS_PANES, 16, new ItemStack(Items.SAND, 6), 1, 20, YetAnotherTechMod.MODID + ":sand_from_glass_pane_grinding");
		this.addGrindingRecipe(writer, Items.GLASS_BOTTLE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_glass_bottle_grinding");
		

		
		this.addGrindingRecipe(writer, Items.SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_grinding");
		this.addGrindingRecipe(writer, Items.CHISELED_SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_chiseled_sandstone_grinding");
		this.addGrindingRecipe(writer, Items.CUT_SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_cut_sandstone_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_smooth_sandstone_grinding");

		this.addGrindingRecipe(writer, Items.SANDSTONE_STAIRS, 4, new ItemStack(Items.SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_stair_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_SANDSTONE_STAIRS, 4, new ItemStack(Items.SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":sand_from_smooth_sandstone_stair_grinding");

		this.addGrindingRecipe(writer, Items.SANDSTONE_SLAB, 2, new ItemStack(Items.SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_slab_grinding");
		this.addGrindingRecipe(writer, Items.CUT_STANDSTONE_SLAB, 2, new ItemStack(Items.SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_cut_sandstone_slab_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_SANDSTONE_SLAB, 2, new ItemStack(Items.SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_smooth_sandstone_slab_grinding");

		this.addGrindingRecipe(writer, Items.SANDSTONE_WALL, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_wall_grinding");

		
		
		
		this.addGrindingRecipe(writer, Items.RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_sandstone_grinding");
		this.addGrindingRecipe(writer, Items.CHISELED_RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_chiseled_sandstone_grinding");
		this.addGrindingRecipe(writer, Items.CUT_RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_cut_sandstone_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_smooth_sandstone_grinding");

		this.addGrindingRecipe(writer, Items.RED_SANDSTONE_STAIRS, 4, new ItemStack(Items.RED_SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_red_sandstone_stair_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_RED_SANDSTONE_STAIRS, 4, new ItemStack(Items.RED_SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_smooth_red_sandstone_stair_grinding");

		this.addGrindingRecipe(writer, Items.RED_SANDSTONE_SLAB, 2, new ItemStack(Items.RED_SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_red_sandstone_slab_grinding");
		this.addGrindingRecipe(writer, Items.CUT_RED_SANDSTONE_SLAB, 2, new ItemStack(Items.RED_SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_cut_red_sandstone_slab_grinding");
		this.addGrindingRecipe(writer, Items.SMOOTH_RED_SANDSTONE_SLAB, 2, new ItemStack(Items.RED_SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sred_and_from_smooth_red_sandstone_slab_grinding");

		this.addGrindingRecipe(writer, Items.RED_SANDSTONE_WALL, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_red_sandstone_wall_grinding");
		
	} // end addGrinderBrickCrackings()
	
	private void addGrindPurpur(Consumer<FinishedRecipe> writer) 
	{
		
		this.addGrindingRecipe(writer, Items.PURPUR_BLOCK, new ItemStack(Items.POPPED_CHORUS_FRUIT, 4), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_block_grinding");
		this.addGrindingRecipe(writer, Items.PURPUR_PILLAR, new ItemStack(Items.POPPED_CHORUS_FRUIT, 4), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_pillar_grinding");

		this.addGrindingRecipe(writer, Items.PURPUR_SLAB, 2, new ItemStack(Items.POPPED_CHORUS_FRUIT, 4), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_slab_grinding");
		this.addGrindingRecipe(writer, Items.PURPUR_STAIRS, new ItemStack(Items.POPPED_CHORUS_FRUIT, 3), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_stairs_grinding");

	} // end addGrinderBrickCrackings()
	
	
	
	
	private void addChorusBiolings(Consumer<FinishedRecipe> writer) 
	{
		this.addBiolingRecipe(writer, Items.CHORUS_PLANT, new FluidStack(YATMFluids.CHORUS_BIO.get(), 120), 6, 120, YetAnotherTechMod.MODID + ":chorus_biofluid_from_chorus_stem_bioling");
		this.addBiolingRecipe(writer, Items.CHORUS_FLOWER, new FluidStack(YATMFluids.CHORUS_BIO.get(), 1000), 4, 60, YetAnotherTechMod.MODID + ":chorus_biofluid_from_chorus_flower_bioling");
		this.addBiolingRecipe(writer, Items.CHORUS_FRUIT, new FluidStack(YATMFluids.CHORUS_BIO.get(), 200), 2, 20, YetAnotherTechMod.MODID + ":chorus_biofluid_from_chorus_fruit_bioling");
		
		this.addBiolingRecipe(writer, Items.POPPED_CHORUS_FRUIT, new FluidStack(YATMFluids.CHORUS_BIO.get(), 240), 6, 48, YetAnotherTechMod.MODID + ":chorus_biofluid_from_popped_chorus_fruit_bioling");
		this.addBiolingRecipe(writer, Items.PURPUR_BLOCK, new FluidStack(YATMFluids.CHORUS_BIO.get(), 960), 6, 192, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_block_bioling");
		this.addBiolingRecipe(writer, Items.PURPUR_PILLAR, new FluidStack(YATMFluids.CHORUS_BIO.get(), 960), 6, 192, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_pillar_bioling");
		this.addBiolingRecipe(writer, Items.PURPUR_SLAB, new FluidStack(YATMFluids.CHORUS_BIO.get(), 480), 6, 96, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_slab_bioling");
		this.addBiolingRecipe(writer, Items.PURPUR_STAIRS, new FluidStack(YATMFluids.CHORUS_BIO.get(), 720), 6, 144, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_stairs_bioling");

	} // end addChorusBiolings()
	
	
	
	private void addPoweredToolRecipes(Consumer<FinishedRecipe> writer) 
	{
//		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.WOODEN_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_WOOD.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_wood_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.STONE_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_STONE.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_stone_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.IRON_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_IRON.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_iron_from_part_exchange");
		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.STEEL_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_STEEL.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_steel_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.GOLD_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_GOLD.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_gold_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.DIAMOND_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_DIAMOND.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_diamond_from_part_exchange");
		this.addPartExchange(writer, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.NETHERITE_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_NETHERITE.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_netherite_from_part_exchange");
		
//		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.WOODEN_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_WOOD.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_wood_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.STONE_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_STONE.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_stone_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.IRON_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_IRON.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_iron_from_part_exchange");
		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.STEEL_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_STEEL.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_steel_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.GOLD_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_GOLD.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_gold_from_part_exchange");
//		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.DIAMOND_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_DIAMOND.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_diamond_from_part_exchange");
		this.addPartExchange(writer, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.NETHERITE_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_NETHERITE.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_netherite_from_part_exchange");
		
	} // end addPoweredToolRecipes()
	
	
	
	// TODO, add in silt, for clay making and reverting
	
	
	private void addNineToOne(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(ingredient), 9)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addNineToOne()
	
	private void addNineToOne(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(ingredient), 9)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addNineToOne()

	private void addOneToNine(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9)
		.requires(ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addCompressed()
	
	private void addOneToNine(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9)
		.requires(ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addCompressed()

	private void addOneToFour(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 4)
		.requires(ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addCompressed()
	
	private void addOneToX(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, int count, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
		.requires(ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addCompressed()
	
//	@SuppressWarnings("unused")
//	private void addTwoByTwoToOne(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, String key) 
//	{
////		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
////		.pattern("aa")
////		.pattern("aa")
////		.define('a', ingredient)
////		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
////		.save(writer, key);
//		this.addTwoByTwoToX(writer, ingredient, result, 1, key);
//	} // end addTwoByTwoToOne()
	
	private void addTwoByTwoToX(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, int count, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, count)
		.pattern("aa")
		.pattern("aa")
		.define('a', ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addTwoByTwoToOne()
	
	@SuppressWarnings("unused")
	private void addXToX(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, int ingredientCount, int resultCount, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, resultCount)
		.requires(ingredient, ingredientCount)
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addTwoByTwoToOne()
	
	private void addLargeHeatSink(Consumer<FinishedRecipe> writer, String key,  ItemLike result, TagKey<Item> finIngredient, TagKey<Item> baseIngredient) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
		.pattern("fff")
		.pattern("fbf")
		.pattern("bbb")
		.define('f', finIngredient)
		.define('b', baseIngredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(finIngredient).build(), ItemPredicate.Builder.item().of(baseIngredient).build()))
		.save(writer, key);
	} // end addLargeHeatSink
	
	private void addFancyPlanks(Consumer<FinishedRecipe> writer, ItemLike regularPlank, ItemLike slab, ItemLike result, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 3)
		.pattern("bs")
		.pattern("sb")
		.define('b', regularPlank)
		.define('s', slab)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(regularPlank).build(), ItemPredicate.Builder.item().of(slab).build()))
		.save(writer, key);
	}
	
	private void addStairs(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.stairBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);		
	} // end addStairs()
	
	private void addSlabs(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.slabBuilder(RecipeCategory.BUILDING_BLOCKS, result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);	
	} // end addSlabs()
	
	private void addFence(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.fenceBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // endFence()
	
	private void addFenceGate(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		fenceGateBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // endFence()
	
	private void addDoor(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.doorBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // addDoor()
	
	private void addTrapdoor(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.trapdoorBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // addTrapdoor()
	
	private void addPressurePlate(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.pressurePlateBuilder(RecipeCategory.REDSTONE, result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // addPressurePlate()
	
	private void addButton(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.buttonBuilder(result,Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addButton()
	
	private void addSign(Consumer<FinishedRecipe> writer, TagKey<Item> planks, Item sign, String key) 
	{
		RecipeProvider.signBuilder(sign, Ingredient.of(planks))
		.unlockedBy("has_planks", has(planks))
		.save(writer);
	} // end addSign()
	
	private void addHangingSign(Consumer<FinishedRecipe> writer, Item strippedLog, Item sign, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6)
		.group("hanging_sign")		
		.pattern("C C")
		.pattern("PPP")
		.pattern("PPP")
		.define('C', Items.CHAIN)
		.define('P', strippedLog)
		.unlockedBy("has_stripped_logs", has(strippedLog))
		.save(writer);
	} // end addHangingSign()
	
	private void addBoat(Consumer<FinishedRecipe> writer, TagKey<Item> planks, Item boat, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat)
		.group("boat")
		.pattern("   ")
		.pattern("P P")
		.pattern("PPP")
		.define('P', planks)
		.unlockedBy("in_water", insideOf(Blocks.WATER))
		.save(writer);
	} // end addBoat()
	
	private void addChestBoat(Consumer<FinishedRecipe> writer, Item boat, Item chestBoat, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat)
		.group("chest_boat")
		.requires(Blocks.CHEST)
		.requires(boat)
		.unlockedBy("has_boat", has(ItemTags.BOATS))
		.save(writer);
	} // end addChestBoat()
	
	

//	private void addWire(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
//	{
//		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
//		.pattern("   ")
//		.pattern("www")
//		.pattern("   ")
//		.define('w', ingredient)
//		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
//		.save(writer, key);
//	} // end addWire()
	
//	private void addManyEnameledWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike enameler, ItemLike result, String key) 
//	{
//		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 8)
//		.requires(Ingredient.of(wire), 8)
//		.requires(enameler)
//		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(enameler).build()))
//		.save(writer, key);
//	} // end addManyEnameledWire()
//	
//	private void addSingleEnameledWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike enameler, ItemLike result, String key) 
//	{
//		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
//		.requires(Ingredient.of(wire))
//		.requires(enameler)
//		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(enameler).build()))
//		.save(writer, key);
//	} // end addSingleEnameledWire()
//	
//	private void addManyInsulatedWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike insulater, ItemLike result, String key) 
//	{
//		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 4)
//		.requires(Ingredient.of(wire), 4)
//		.requires(insulater)
//		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(insulater).build()))
//		.save(writer, key);
//	} // end addManyInsulatedWire()
//	
//	private void addSingleInsulatedWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike insulater, ItemLike result, String key) 
//	{
//		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
//		.requires(Ingredient.of(wire))
//		.requires(insulater)
//		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(insulater).build()))
//		.save(writer, key);
//	} // end addSingleInsulatedWire()
	
	
	
	private void addSmelting(Consumer<FinishedRecipe> writer, ItemLike[] ingredient, ItemLike result, float experience, int time, String key) 
	{
		this.addSmelting(writer, ingredient, result, ingredient, experience, time, key);
	} // end addSmelting
	
	private void addSmelting(Consumer<FinishedRecipe> writer, ItemLike[] ingredient, ItemLike result, ItemLike[] trigger, float experience, int time, String key) 
	{
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, time)
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(trigger).build()))
		.save(writer, key);
	} // end addSmelting
	
	private void addSmelting(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, float experience, int time, String key) 
	{
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, time)
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addSmelting
	
	private void addBlasting(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, float experience, int time, String key) 
	{
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, time)
		.unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addSmelting
	
	
	
	private void addExtraction(Consumer<FinishedRecipe> writer, ItemLike ingredient, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractingRecipeBuilder()
		.input(new ItemStackIngredient(ingredient.asItem()))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addExtraction()
	
	private void addExtraction(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemStack remainder, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractingRecipeBuilder()
		.input(new ItemStackIngredient(ingredient.asItem()))
		.inputRemainder(remainder)
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addExtraction()
	
	private void addExtraction(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemStack remainder, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractingRecipeBuilder()
		.input(new ItemTagIngredient(ingredient))
		.inputRemainder(remainder)
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addExtraction()
	
	
	
//	// TODO, probably remove extrusion
//	@SuppressWarnings("unused")
//	private void addExtrusion(Consumer<FinishedRecipe> writer, TagKey<Item> input, TagKey<Item> die, ItemStack result, int currentPerTick, int timeInTicks, String key) 
//	{
//		new ExtrudingRecipeBuilder()
//		.input(new ItemTagIngredient(input))
//		.die(new ItemTagIngredient(die))
//		.result(result)
//		.currentPerTick(currentPerTick)
//		.timeInTicks(timeInTicks)
//		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(die).build()))
//		.save(writer, key);
//	} // end addExtrusion()
//	
//	@SuppressWarnings("unused")
//	private void addExtrusion(Consumer<FinishedRecipe> writer, ItemLike input, ItemLike inputRemainder, TagKey<Item> die, ItemStack result, int currentPerTick, int timeInTicks, String key) 
//	{
//		new ExtrudingRecipeBuilder()
//		.input(new ItemStackIngredient(input.asItem()))
//		.inputRemainder(new ItemStack(inputRemainder))
//		.die(new ItemTagIngredient(die))
//		.result(result)
//		.currentPerTick(currentPerTick)
//		.timeInTicks(timeInTicks)
//		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(die).build()))
//		.save(writer, key);
//	} // end addExtrusion()
	
	
	
	private void addBoilingRecipe(Consumer<FinishedRecipe> writer, FluidStack input, FluidStack result, int temperature, int timeInTicks, String key) 
	{
		new BoilingRecipeBuilder()
		.input(new FluidStackIngredient(input))
		.result(result)
		.temperature(temperature)
		.timeInTicks(timeInTicks)
		// TODO, make use tag containing all the boilers
		.unlockedBy("has_device", inventoryTrigger(ItemPredicate.Builder.item().of(YATMItems.STEEL_BOILER_ITEM.get()).build()))
		.save(writer, key);
	} // end addBoilingRecipe()
	
	
	
	private void addCrystallizationRecipe(Consumer<FinishedRecipe> writer, FluidStack input, TagKey<Item> seed, ItemStack result, boolean consumeSeed, int currentPerTick, int timeInTicks, String key) 
	{
		new CrystallizingRecipeBuilder()
		.input(new FluidStackIngredient(input))
		.seed(new ItemTagIngredient(seed))
		.result(result)
		.consumeSeed(consumeSeed)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		// TODO, make use tag containing all the crystallizers
		.unlockedBy("has_device", inventoryTrigger(ItemPredicate.Builder.item().of(YATMItems.STEEL_CRYSTALLIZER_ITEM.get()).build()))
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(seed).build()))
		.save(writer, key);
	} // end addBoilingRecipe()
	
	private void addCrystallizationRecipe(Consumer<FinishedRecipe> writer, FluidStack input, Item seed, ItemStack result, boolean consumeSeed, int currentPerTick, int timeInTicks, String key) 
	{
		new CrystallizingRecipeBuilder()
		.input(new FluidStackIngredient(input))
		.seed(new ItemStackIngredient(seed))
		.result(result)
		.consumeSeed(consumeSeed)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		// TODO, make use tag containing all the crystallizers
		.unlockedBy("has_device", inventoryTrigger(ItemPredicate.Builder.item().of(YATMItems.STEEL_CRYSTALLIZER_ITEM.get()).build()))
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(seed).build()))
		.save(writer, key);
	} // end addBoilingRecipe()
	
	
	
	private void addGrindingRecipe(Consumer<FinishedRecipe> writer, Item input, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemStackIngredient(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindingRecipe()
	
	private void addGrindingRecipe(Consumer<FinishedRecipe> writer, Item input, int inputCount, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemStackIngredient(new ItemStack(input, inputCount)))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindingRecipe()
	
	private void addGrindingRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> input, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemTagIngredient(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindingRecipe()
	
	private void addGrindingRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> input, int inputCount, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemTagIngredient(input, inputCount))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindingRecipe()
	
	
	
	private void addBiolingRecipe(Consumer<FinishedRecipe> writer, Item input, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new BiolingRecipeBuilder()
		.input(new ItemStackIngredient(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_device", inventoryTrigger(ItemPredicate.Builder.item().of(YATMItems.STEEL_BIOLER_ITEM.get()).build()))
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindRecipe()
	
	
	
	private void addSpinningRecipe(Consumer<FinishedRecipe> writer, Item input, ItemStack result, String key) 
	{
		new SpinningRecipeBuilder()
		.input(new ItemStackIngredient(input))
		.result(result)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindRecipe()
	
	private void addSpinningRecipe(Consumer<FinishedRecipe> writer, TagKey<Item> input, ItemStack result, String key) 
	{
		new SpinningRecipeBuilder()
		.input(new ItemTagIngredient(input))
		.result(result)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindRecipe()
	
	
	
	private void addPartExchange(Consumer<FinishedRecipe> writer, TagKey<Item> tool, ItemStack part, ItemStack result, String group, String key) 
	{
		new ToolPartExchangeRecipeBuilder()
		.category(CraftingBookCategory.EQUIPMENT)
		.tool(new ItemTagIngredient(tool))
		.part(new ItemStackIngredient(part))
		.result(result)
		.group(group)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(part.getItem()).build()))
		.save(writer, key);
	} // end addPartExchange()
	
	
} // end class