package com.gsr.gsr_yatm.data_generation;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.BoilingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.CrystallizationRecipeBuilder;
import com.gsr.gsr_yatm.recipe.ExtractionRecipeBuilder;
import com.gsr.gsr_yatm.recipe.ExtrusionRecipeBuilder;
import com.gsr.gsr_yatm.recipe.GrindingRecipeBuilder;
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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class YATMRecipeProvider extends RecipeProvider
{

	public YATMRecipeProvider(PackOutput output)
	{
		super(output);
	} // end constructor
	
	

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> writer)
	{
		this.addNineToOne(writer, YATMItemTags.FORGE_COPPER_NUGGETS_KEY, Items.COPPER_INGOT, YetAnotherTechMod.MODID + ":copper_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, Items.NETHERITE_INGOT, YetAnotherTechMod.MODID + ":netherite_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_SILVER_NUGGETS_KEY, YATMItems.SILVER_INGOT.get(), YetAnotherTechMod.MODID + ":silver_ingot_from_shapeless_crafting");
		this.addOneToNine(writer, Tags.Items.INGOTS_COPPER, YATMItems.COPPER_NUGGET.get(), YetAnotherTechMod.MODID + ":copper_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, Tags.Items.INGOTS_NETHERITE, YATMItems.NETHERITE_NUGGET.get(), YetAnotherTechMod.MODID + ":netherite_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_SILVER_INGOTS_KEY, YATMItems.SILVER_NUGGET.get(), YetAnotherTechMod.MODID + ":silver_nuggets_from_shapeless_crafting");

		this.addOneToX(writer, Items.HONEYCOMB, YATMItems.WAX_BIT_ITEM.get(), 8, YetAnotherTechMod.MODID + ":wax_bit_from_shapeless_crafting");
		this.addXToX(writer, YATMItems.WAX_BIT_ITEM.get(), Items.HONEYCOMB, 8, 1, YetAnotherTechMod.MODID + ":honeycomb_from_shapeless_crafting");
		
		this.addLargeHeatSink(writer, YetAnotherTechMod.MODID + ":large_copper_heat_sink_from_shape_crafting", YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), Tags.Items.INGOTS_COPPER, YATMItemTags.UNOXIDIXED_COPPER_BLOCKS_KEY);

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

		this.addOneToX(writer, YATMItems.RUBBER_BAR.get(), YATMItems.RUBBER_SCRAP.get(), 4, YetAnotherTechMod.MODID + ":rubber_scrap_from_ingot_shapeless_crafting");
		this.addOneToX(writer, YATMItems.RUBBER_SCRAP_BALL.get(), YATMItems.RUBBER_SCRAP.get(), 4, YetAnotherTechMod.MODID + ":rubber_scrap_from_scrap_ball_shapeless_crafting");
		this.addTwoByTwoToOne(writer, YATMItems.RUBBER_SCRAP.get(), YATMItems.RUBBER_SCRAP_BALL.get(), YetAnotherTechMod.MODID + ":rubber_scrap_ball_from_shaped_crafting");
		this.addSmelting(writer, new ItemLike[] {YATMItems.LATEX_BUCKET.get()}, YATMItems.RUBBER_BLOCK_ITEM.get(), .3f, 20, YetAnotherTechMod.MODID + ":rubber_block_from_latex_smelting");
		this.addSmelting(writer, new ItemLike[] {YATMItems.RUBBER_SCRAP_BALL.get()}, YATMItems.RUBBER_BAR.get(), new ItemLike[] {YATMItems.RUBBER_SCRAP_BALL.get()}, .3f, 20, YetAnotherTechMod.MODID + ":rubber_bar_from_scrap_ball_smelting");
		this.addOneToNine(writer, YATMItemTags.FORGE_RUBBER_STORAGE_BLOCK_KEY, YATMItems.RUBBER_BAR.get(), YetAnotherTechMod.MODID + ":rubber_bar_from_block_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_RUBBER_INGOTS_KEY, YATMItems.RUBBER_BLOCK_ITEM.get(), YetAnotherTechMod.MODID + ":rubber_block_from_bar_shapeless_crafting");
		
		
		
		
		// TODO, add way to seperate the rootsa dn dirt back out of rooted dirts and like
		
		this.addExtraction(writer, new ItemLike[] {YATMItems.RUBBER_LOG_ITEM.get(), YATMItems.RUBBER_WOOD_ITEM.get()}, new ItemStack(YATMItems.WOOD_PULP.get(), 6), new FluidStack(YATMFluids.LATEX.get(), 120), 12, 246, YetAnotherTechMod.MODID + ":latex_from_log_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.RUBBER_LEAVES_OLD_ITEM.get()}, new ItemStack(YATMItems.WOOD_PULP.get(), 1), new FluidStack(YATMFluids.LATEX.get(), 40), 4, 62, YetAnotherTechMod.MODID + ":latex_from_old_leaves_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.RUBBER_LEAVES_LEAVES_FLOWERING_ITEM.get()}, new ItemStack(YATMItems.LEAF_MULCH.get(), 2), new FluidStack(YATMFluids.LATEX.get(), 30), 4, 46, YetAnotherTechMod.MODID + ":latex_from_flowering_leaves_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.RUBBER_LEAVES_YOUNG_ITEM.get()}, new ItemStack(YATMItems.LEAF_MULCH.get(), 1), new FluidStack(YATMFluids.LATEX.get(), 20), 4, 34, YetAnotherTechMod.MODID + ":latex_from_young_leaves_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.RUBBER_MERISTEM_ITEM.get()}, new FluidStack(YATMFluids.LATEX.get(), 2), 1, 12, YetAnotherTechMod.MODID + ":latex_from_meristem_extraction");
		
		this.addExtraction(writer, new ItemLike[] {YATMItems.SOUL_AFFLICTED_RUBBER_LOG_ITEM.get(), YATMItems.SOUL_AFFLICTED_RUBBER_WOOD_ITEM.get()}, new ItemStack(YATMItems.WOOD_PULP.get(), 6), new FluidStack(YATMFluids.SOUL_SAP.get(), 120), 12, 246, YetAnotherTechMod.MODID + ":soul_sap_from_log_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_OLD_ITEM.get()}, new ItemStack(YATMItems.WOOD_PULP.get(), 1), new FluidStack(YATMFluids.SOUL_SAP.get(), 40), 4, 62, YetAnotherTechMod.MODID + ":soul_sap_from_old_leaves_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_LEAVES_FLOWERING_ITEM.get()}, new ItemStack(YATMItems.LEAF_MULCH.get(), 2), new FluidStack(YATMFluids.SOUL_SAP.get(), 30), 4, 46, YetAnotherTechMod.MODID + ":soul_sap_from_flowering_leaves_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG_ITEM.get()}, new ItemStack(YATMItems.LEAF_MULCH.get(), 1), new FluidStack(YATMFluids.SOUL_SAP.get(), 20), 4, 34, YetAnotherTechMod.MODID + ":soul_sap_from_young_leaves_extraction");
		this.addExtraction(writer, new ItemLike[] {YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get()}, new FluidStack(YATMFluids.SOUL_SAP.get(), 2), 1, 12, YetAnotherTechMod.MODID + ":soul_sap_from_meristem_extraction");
	
		
		
		this.addBoilingRecipe(writer, new FluidStack(YATMFluids.SOUL_SAP.get(), 3), new FluidStack(YATMFluids.SOUL_SYRUP.get(), 1), 722, 3, YetAnotherTechMod.MODID + ":soul_syrup_from_soul_sap_boiling");
	
		
		
		this.addWireRecipes(writer);
		
		
		
		// TODO, create a fluidIngredient, so we can use tags or specifics
		this.addCrystallizationRecipe(writer, new FluidStack(YATMFluids.ENDER.get(), 250), Tags.Items.SAND, new ItemStack(Items.ENDER_PEARL), false, 0, 300, YetAnotherTechMod.MODID + ":ender_pearl_from_crystallization");
		
		
		
		this.addGrindingRecipe(writer, Items.STONE, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_grinding");
		this.addGrindingRecipe(writer, Items.COBBLESTONE, new ItemStack(Items.GRAVEL), 3, 80, YetAnotherTechMod.MODID + ":gravel_from_cobblestone_grinding");
		this.addGrindingRecipe(writer, Items.GRAVEL, new ItemStack(Items.SAND), 3, 80, YetAnotherTechMod.MODID + ":sand_from_gravel_grinding");
		
	} // end buildRecipes()
	
	private void addWireRecipes(Consumer<FinishedRecipe> writer) 
	{
		this.addWire(writer, Tags.Items.NUGGETS_IRON, YATMItems.ONE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":one_cu_wire_from_shaped_crafting");
		this.addWire(writer, YATMItemTags.FORGE_COPPER_NUGGETS_KEY, YATMItems.EIGHT_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":eight_cu_wire_from_shaped_crafting");
		this.addWire(writer, Tags.Items.NUGGETS_GOLD, YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":sixtyfour_cu_wire_from_shaped_crafting");
		this.addWire(writer, YATMItemTags.FORGE_SILVER_NUGGETS_KEY, YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":fivehundredtwelve_cu_wire_from_shaped_crafting");
		this.addWire(writer, YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":fourthousandnintysix_cu_wire_from_shaped_crafting");
		
		this.addManyEnameledWire(writer, YATMItems.ONE_CU_WIRE_ITEM.get(), Items.HONEYCOMB, YATMItems.ENAMELED_ONE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_enameled_one_cu_wire_from_shapeless_crafting");
		this.addManyEnameledWire(writer, YATMItems.EIGHT_CU_WIRE_ITEM.get(), Items.HONEYCOMB, YATMItems.ENAMELED_EIGHT_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_enameled_eight_cu_wire_from_shapeless_crafting");
		this.addManyEnameledWire(writer, YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), Items.HONEYCOMB, YATMItems.ENAMELED_SIXTYFOUR_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_enameled_sixtyfour_cu_wire_from_shapeless_crafting");
		this.addManyEnameledWire(writer, YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), Items.HONEYCOMB, YATMItems.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_enameled_fivehundredtwelve_cu_wire_from_shapeless_crafting");
		this.addManyEnameledWire(writer, YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), Items.HONEYCOMB, YATMItems.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_enameled_fourthousandnintysix_cu_wire_from_shapeless_crafting");
		
		this.addSingleEnameledWire(writer, YATMItems.ONE_CU_WIRE_ITEM.get(),  YATMItems.WAX_BIT_ITEM.get(), YATMItems.ENAMELED_ONE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_enameled_one_cu_wire_from_shapeless_crafting");
		this.addSingleEnameledWire(writer, YATMItems.EIGHT_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItems.ENAMELED_EIGHT_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_enameled_eight_cu_wire_from_shapeless_crafting");
		this.addSingleEnameledWire(writer, YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItems.ENAMELED_SIXTYFOUR_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_enameled_sixtyfour_cu_wire_from_shapeless_crafting");
		this.addSingleEnameledWire(writer, YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItems.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_enameled_fivehundredtwelve_cu_wire_from_shapeless_crafting");
		this.addSingleEnameledWire(writer, YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItems.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_enameled_fourthousandnintysix_cu_wire_from_shapeless_crafting");
		
		this.addManyInsulatedWire(writer, YATMItems.ONE_CU_WIRE_ITEM.get(), YATMItems.RUBBER_BAR.get(), YATMItems.INSULATED_ONE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_insulated_one_cu_wire_from_shapeless_crafting");
		this.addManyInsulatedWire(writer, YATMItems.EIGHT_CU_WIRE_ITEM.get(), YATMItems.RUBBER_BAR.get(), YATMItems.INSULATED_EIGHT_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_insulated_eight_cu_wire_from_shapeless_crafting");
		this.addManyInsulatedWire(writer, YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), YATMItems.RUBBER_BAR.get(), YATMItems.INSULATED_SIXTYFOUR_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_insulated_sixtyfour_cu_wire_from_shapeless_crafting");
		this.addManyInsulatedWire(writer, YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YATMItems.RUBBER_BAR.get(), YATMItems.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_insulated_fivehundredtwelve_cu_wire_from_shapeless_crafting");
		this.addManyInsulatedWire(writer, YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YATMItems.RUBBER_BAR.get(), YATMItems.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":many_insulated_fourthousandnintysix_cu_wire_from_shapeless_crafting");
		
		this.addSingleInsulatedWire(writer, YATMItems.ONE_CU_WIRE_ITEM.get(),  YATMItems.RUBBER_SCRAP.get(), YATMItems.INSULATED_ONE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_insulated_one_cu_wire_from_shapeless_crafting");
		this.addSingleInsulatedWire(writer, YATMItems.EIGHT_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItems.INSULATED_EIGHT_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_insulated_eight_cu_wire_from_shapeless_crafting");
		this.addSingleInsulatedWire(writer, YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItems.INSULATED_SIXTYFOUR_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_insulated_sixtyfour_cu_wire_from_shapeless_crafting");
		this.addSingleInsulatedWire(writer, YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItems.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_insulated_fivehundredtwelve_cu_wire_from_shapeless_crafting");
		this.addSingleInsulatedWire(writer, YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItems.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YetAnotherTechMod.MODID + ":single_insulated_fourthousandnintysix_cu_wire_from_shapeless_crafting");
		
		// limit which dies can do what with, to reflect strength of materials
		this.addExtrusion(writer, Tags.Items.INGOTS_IRON, YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.ONE_CU_WIRE_ITEM.get(), 6), 12, 56, YetAnotherTechMod.MODID + ":one_cu_wire_from_ingot_extrusion");
		this.addExtrusion(writer, Tags.Items.INGOTS_COPPER, YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.EIGHT_CU_WIRE_ITEM.get(), 6), 8, 44, YetAnotherTechMod.MODID + ":eight_cu_wire_from_ingot_extrusion");
		this.addExtrusion(writer, Tags.Items.INGOTS_GOLD, YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), 6), 4, 34, YetAnotherTechMod.MODID + ":sixtyfour_cu_wire_from_ingot_extrusion");
		this.addExtrusion(writer, YATMItemTags.FORGE_SILVER_INGOTS_KEY, YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), 6), 6, 49, YetAnotherTechMod.MODID + ":fivehundredtwelve_cu_wire_from_ingot_extrusion");
		this.addExtrusion(writer, Tags.Items.INGOTS_NETHERITE, YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), 6), 16, 89, YetAnotherTechMod.MODID + ":fourthousandnintysix_cu_wire_from_ingot_extrusion");
		
		this.addExtrusion(writer, YATMItems.ENAMELED_ONE_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.ONE_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":one_cu_wire_from_enameled_wire_extrusion");
		this.addExtrusion(writer, YATMItems.ENAMELED_EIGHT_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.EIGHT_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":eight_cu_wire_from_enameled_wire_extrusion");
		this.addExtrusion(writer, YATMItems.ENAMELED_SIXTYFOUR_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":sixtyfour_cu_wire_from_enameled_wire_extrusion");
		this.addExtrusion(writer, YATMItems.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":fivehundredtwelve_cu_wire_from_enameled_wire_extrusion");
		this.addExtrusion(writer, YATMItems.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YATMItems.WAX_BIT_ITEM.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":fourthousandnintysix_cu_wire_from_enameled_wire_extrusion");
		
		this.addExtrusion(writer, YATMItems.INSULATED_ONE_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.ONE_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":one_cu_wire_from_insulated_wire_extrusion");
		this.addExtrusion(writer, YATMItems.INSULATED_EIGHT_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.EIGHT_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":eight_cu_wire_from_insulated_wire_extrusion");
		this.addExtrusion(writer, YATMItems.INSULATED_SIXTYFOUR_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.SIXTYFOUR_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":sixtyfour_cu_wire_from_insulated_wire_extrusion");
		this.addExtrusion(writer, YATMItems.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.FIVEHUNDREDTWELVE_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":fivehundredtwelve_cu_wire_from_insulated_wire_extrusion");
		this.addExtrusion(writer, YATMItems.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), YATMItems.RUBBER_SCRAP.get(), YATMItemTags.WIRE_DIES_KEY, new ItemStack(YATMItems.FOURTHOUSANDNINTYSIX_CU_WIRE_ITEM.get(), 1), 1, 12, YetAnotherTechMod.MODID + ":fourthousandnintysix_cu_wire_from_insulated_wire_extrusion");
	} // end addWireRecipes()

	
	


	private void addNineToOne(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(ingredient), 9)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addCompressed()

	private void addOneToNine(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
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
	
	@SuppressWarnings("unused")
	private void addTwoByTwoToOne(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, String key) 
	{
//		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
//		.pattern("aa")
//		.pattern("aa")
//		.define('a', ingredient)
//		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
//		.save(writer, key);
		this.addTwoByTwoToX(writer, ingredient, result, 1, key);
	} // end addTwoByTwoToOne()
	
	private void addTwoByTwoToX(Consumer<FinishedRecipe> writer, ItemLike ingredient, ItemLike result, int count, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, count)
		.pattern("aa")
		.pattern("aa")
		.define('a', ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addTwoByTwoToOne()
	
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
		stairBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);		
	} // end addStairs()
	
	private void addSlabs(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		slabBuilder(RecipeCategory.BUILDING_BLOCKS, result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);	
	} // end addSlabs()
	
	private void addFence(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		fenceBuilder(result, Ingredient.of(ingredient))
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
		doorBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // addDoor()
	
	private void addTrapdoor(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		trapdoorBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // addTrapdoor()
	
	private void addPressurePlate(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		pressurePlateBuilder(RecipeCategory.REDSTONE, result, Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);			
	} // addPressurePlate()
	
	private void addButton(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		buttonBuilder(result,Ingredient.of(ingredient))
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addButton()

	private void addWire(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
		.pattern("   ")
		.pattern("www")
		.pattern("   ")
		.define('w', ingredient)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addWire()
	
	private void addManyEnameledWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike enameler, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 8)
		.requires(Ingredient.of(wire), 8)
		.requires(enameler)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(enameler).build()))
		.save(writer, key);
	} // end addManyEnameledWire()
	
	private void addSingleEnameledWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike enameler, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(wire))
		.requires(enameler)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(enameler).build()))
		.save(writer, key);
	} // end addSingleEnameledWire()
	
	private void addManyInsulatedWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike insulater, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 4)
		.requires(Ingredient.of(wire), 4)
		.requires(insulater)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(insulater).build()))
		.save(writer, key);
	} // end addManyInsulatedWire()
	
	private void addSingleInsulatedWire(Consumer<FinishedRecipe> writer, ItemLike wire, ItemLike insulater, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(wire))
		.requires(insulater)
		.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(wire).build(), ItemPredicate.Builder.item().of(insulater).build()))
		.save(writer, key);
	} // end addSingleInsulatedWire()
	
	
	
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
	
	
	
	private void addExtraction(Consumer<FinishedRecipe> writer, ItemLike[] ingredient, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractionRecipeBuilder()
		.input(Ingredient.of(ingredient))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addExtraction()
	
	private void addExtraction(Consumer<FinishedRecipe> writer, ItemLike[] ingredient, ItemStack remainder, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractionRecipeBuilder()
		.input(Ingredient.of(ingredient))
		.inputRemainder(remainder)
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(ingredient).build()))
		.save(writer, key);
	} // end addExtractionWithRemainder()
	
	
	
	private void addExtrusion(Consumer<FinishedRecipe> writer, TagKey<Item> input, TagKey<Item> die, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtrusionRecipeBuilder()
		.input(Ingredient.of(input))
		.die(Ingredient.of(die))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(die).build()))
		.save(writer, key);
	} // end addExtrusion()
	
	private void addExtrusion(Consumer<FinishedRecipe> writer, ItemLike input, ItemLike inputRemainder, TagKey<Item> die, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtrusionRecipeBuilder()
		.input(Ingredient.of(input))
		.inputRemainder(new ItemStack(inputRemainder))
		.die(Ingredient.of(die))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(die).build()))
		.save(writer, key);
	} // end addExtrusion()
	
	
	
	private void addBoilingRecipe(Consumer<FinishedRecipe> writer, FluidStack input, FluidStack result, int temperature, int timeInTicks, String key) 
	{
		new BoilingRecipeBuilder()
		.input(input)
		.result(result)
		.temperature(temperature)
		.timeInTicks(timeInTicks)
		// TODO, make use tag containing all the boilers
		.unlockedBy("has_device", inventoryTrigger(ItemPredicate.Builder.item().of(YATMItems.STEEL_BOILER_ITEM.get()).build()))
		.save(writer, key);
	} // end addBoilingRecipe()
	
	
	
	private void addCrystallizationRecipe(Consumer<FinishedRecipe> writer, FluidStack input, TagKey<Item> seed, ItemStack result, boolean consumeSeed, int currentPerTick, int timeInTicks, String key) 
	{
		new CrystallizationRecipeBuilder()
		.input(input)
		.seed(Ingredient.of(seed))
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
		.input(Ingredient.of(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", inventoryTrigger(ItemPredicate.Builder.item().of(input).build()))
		.save(writer, key);
	} // end addGrindRecipe()
	
} // end class