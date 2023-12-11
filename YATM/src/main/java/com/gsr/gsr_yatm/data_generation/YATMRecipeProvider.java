package com.gsr.gsr_yatm.data_generation;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.bioreaction.BioreactionRecipeBuilder;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange.ToolPartExchangeRecipeBuilder;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.fluid_stack.FluidStackIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.fluid_tag.FluidTagIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.item_stack.ItemStackIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.item_tag.ItemTagIngredient;
import com.gsr.gsr_yatm.recipe.injecting.InjectingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipeBuilder;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipeBuilder;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;

public class YATMRecipeProvider extends RecipeProvider
{
	// TODO, investigate melting and spinning recipes unknown category log messages.
	public YATMRecipeProvider(@NotNull PackOutput output)
	{
		super(Objects.requireNonNull(output));
	} // end constructor
	
	

	@Override
	protected void buildRecipes(@NotNull RecipeOutput output)
	{	
		this.addBlastingRecipes(output);
		this.addCraftingRecipes(output);
		this.addSmeltingRecipes(output);
		
		this.addBioreactionRecipes(output);
		this.addBoilingRecipes(output);
		this.addCrystallizationRecipes(output);
		this.addExtractionRecipes(output);
		this.addGrindingRecipes(output);
		this.addInjectingRecipes(output);
		this.addMeltingRecipes(output);
		this.addSpinningRecipes(output);
		
		// TODO, maybe silicon for making advanced solar panels as well
		
		// TODO, maybe, could add a higher yielding fancy sugar extracting route
		// TODO, wood pulp to paper route
		// TODO, phantasmal shelf fungus -> phantom membrane somehwo
		// TODO, add rubbber decomposition into slime. possibly biol into slime(fluid) -> bucket and place in world, turns instantly into a ball of slime, or maybe block. related. maybe make slime blocks animatable into slimes
		// TODO, add tank and channel vine craftin recipes.
		// TODO, add dirt from biofluid infection to sand or gravel or silt
		// TODO, finish the soul essence chain of processing
		// TODO, finish adding the chorus to ender sequence.
		// this.addLargeHeatSink(output, YetAnotherTechMod.MODID + ":large_copper_heat_sink_from_shape_crafting", YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), Tags.Items.INGOTS_COPPER, YATMItemTags.UNOXIDIXED_COPPER_BLOCKS_KEY);
		
		
	} // end buildRecipes()
	
	
	
	private void addBlastingRecipes(@NotNull RecipeOutput output) 
	{
		this.addBlasting(output, YATMItemTags.FOLIAR_STEEL_ORES_KEY, YATMItems.FOLIAR_STEEL.get(), .1f, 100, YetAnotherTechMod.MODID + ":foliar_steel_from_ore_blasting");
		
	} // end addBlastingRecipes()
	
	
	
	private void addCraftingRecipes(@NotNull RecipeOutput output) 
	{
		this.addStorageBlockNuggetLikeRecipes(output);
		
		this.addRubberWoodCoreRecipes(output);
		this.addSoulAfflictedRubberWoodCoreRecipes(output);
		
		this.addCandleLanterns(output);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.HANGING_POT_HOOK.get(), 1).pattern("c ").pattern("p ").define('c', Items.CHAIN).define('p', Items.FLOWER_POT).unlockedBy("has_pot", has(Items.FLOWER_POT)).save(output, YetAnotherTechMod.MODID + ":hanging_pot_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.GRAFTING_TABLE.get(), 1).pattern("f ").pattern("t ").define('f', YATMItems.FOLIAR_STEEL.get()).define('t', Items.CRAFTING_TABLE).unlockedBy("has_item", has(Items.CRAFTING_TABLE)).save(output, YetAnotherTechMod.MODID + ":grafting_table_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.SAP_COLLECTOR.get(), 1).pattern("   ").pattern("p p").pattern("sss").define('p', ItemTags.PLANKS).define('s', ItemTags.SLABS).unlockedBy("has_item", has(ItemTags.SLABS)).save(output, YetAnotherTechMod.MODID + ":sap_collector_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.SPINNING_WHEEL.get(), 1).pattern(" sw").pattern("www").pattern("w w").define('w', ItemTags.PLANKS).define('s', Items.STRING).unlockedBy("has_item", has(ItemTags.PLANKS)).save(output, YetAnotherTechMod.MODID + ":spinning_wheel_shaped_crafting");
		
		
		this.addOneToX(output, YATMItems.COTTON_BOLLS.get(), YATMItems.RAW_COTTON_FIBER.get(), 1, YetAnotherTechMod.MODID + ":raw_cotton_fiber_from_cotton_bolls_shapeless_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING, 3).pattern("   ").pattern("www").pattern("   ").define('w', YATMItems.RAW_COTTON_FIBER.get()).unlockedBy("has_item", has(Items.STRING)).save(output, YetAnotherTechMod.MODID + ":string_from_raw_cotton_fiber_shaped_crafting");
		
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.DIRT, 8).requires(Ingredient.of(YATMItems.BIO_BUCKET.get()), 1).requires(Ingredient.of(YATMItemTags.SOIL_MINERALS_KEY), 8).unlockedBy("criteria", has(YATMItems.BIO_BUCKET.get())).save(output, YetAnotherTechMod.MODID + ":dirt_from_minerals_and_biofluid_shapeless_crafting");
		// TO BECOME GRAFTING \\
		
		// foliar steel block ingot-like nugget-like mutual interchange
		this.addOneToNine(output, YATMItems.FOLIAR_STEEL_BLOCK.get(), YATMItems.FOLIAR_STEEL.get(), YetAnotherTechMod.MODID + ":foliar_steel_from_block_shapeless_crafting");
		this.addNineToOne(output, YATMItems.FOLIAR_STEEL.get(), YATMItems.FOLIAR_STEEL_BLOCK.get(), YetAnotherTechMod.MODID + ":foliar_steel_block_from_bar_shapeless_crafting");
		this.addOneToNine(output, YATMItems.FOLIAR_STEEL.get(), YATMItems.FOLIAR_STEEL_SHRED.get(), YetAnotherTechMod.MODID + ":foliar_steel_shred_from_bar_shapeless_crafting");
		this.addNineToOne(output, YATMItems.FOLIAR_STEEL_SHRED.get(), YATMItems.FOLIAR_STEEL.get(), YetAnotherTechMod.MODID + ":foliar_steel_from_shred_shapeless_crafting");

		// TODO, make spider vines sometimes drop eyes directly. make fruit extract to vitreus humor and leave some kind of ocular tissue, which can be grafted back together to make eyes of spiders
		this.addOneToX(output, YATMItems.SPIDER_VINE_FRUITS.get(), Items.SPIDER_EYE, 3, YetAnotherTechMod.MODID + ":spider_eyes_from_spider_vine_fruits_shapeless_crafting");

		// TODO, add all devices
		this.addDeviceRecipes(output);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.STEEL_TANK.get(), 1).pattern("fff").pattern("fgf").pattern("fff").define('f', YATMItems.FOLIAR_STEEL.get()).define('g', Tags.Items.GLASS).unlockedBy("has_item", has(YATMItems.FOLIAR_STEEL.get())).save(output, YetAnotherTechMod.MODID + ":steel_tank_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.CHANNEL_VINES.get(), 9).pattern("   ").pattern("fff").pattern("   ").define('f', YATMItems.FOLIAR_STEEL.get()).unlockedBy("has_item", has(YATMItems.FOLIAR_STEEL.get())).save(output, YetAnotherTechMod.MODID + ":channel_vines_from_foliar_steel_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.CHANNEL_VINES.get(), 1).pattern("   ").pattern("fff").pattern("   ").define('f', YATMItems.FOLIAR_STEEL_SHRED.get()).unlockedBy("has_item", has(YATMItems.FOLIAR_STEEL_SHRED.get())).save(output, YetAnotherTechMod.MODID + ":channel_vines_from_foliar_steel_shred_shaped_crafting");
		this.addOneToX(output, YATMItems.CHANNEL_VINES.get(), YATMItems.FOLIAR_STEEL_SHRED.get(), 3, YetAnotherTechMod.MODID + ":foliar_steel_shreds_from_channel_vine_shapeless_crafting");

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.KINETIC_DRIVER.get(), 1).pattern(" r ").pattern("fpf").pattern(" v ").define('r', Tags.Items.DUSTS_REDSTONE).define('p', YATMItemTags.FORGE_PISTONS_KEY).define('f', YATMItems.FOLIAR_STEEL.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItemTags.FORGE_PISTONS_KEY)).save(output, YetAnotherTechMod.MODID + ":kinetic_driver_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.KINETIC_DRIVER.get(), 1).pattern(" v ").pattern("fpf").pattern(" r ").define('r', Tags.Items.DUSTS_REDSTONE).define('p', YATMItemTags.FORGE_PISTONS_KEY).define('f', YATMItems.FOLIAR_STEEL.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItemTags.FORGE_PISTONS_KEY)).save(output, YetAnotherTechMod.MODID + ":fkinetic_driver_from_shaped_crafting");
		
		// TODO, torchflower or candlelily -> raw exothermal gland
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.EMBER_GLAND.get(), 1).pattern("g ").pattern("v ").define('g', YATMItems.RAW_EXOTHEMIC_GLAND.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItems.RAW_EXOTHEMIC_GLAND.get())).save(output, YetAnotherTechMod.MODID + ":ember_gland_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.FLAME_GLAND.get(), 1).pattern("gg").pattern("vv").define('g', YATMItems.EMBER_GLAND.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItems.EMBER_GLAND.get())).save(output, YetAnotherTechMod.MODID + ":flame_gland_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.TORCH_GLAND.get(), 1).pattern("ggg").pattern("vvv").pattern("   ").define('g', YATMItems.FLAME_GLAND.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItems.FLAME_GLAND.get())).save(output, YetAnotherTechMod.MODID + ":torch_gland_from_shaped_crafting");
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.CURRENT_BATTERY.get(), 1).pattern(" v ").pattern("fbf").pattern("fbf").define('b', YATMItems.CURRENT_TUBER.get()).define('f', YATMItems.FOLIAR_STEEL.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItems.CURRENT_TUBER.get())).save(output, YetAnotherTechMod.MODID + ":current_battery_from_shaped_crafting");
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.ADVANCED_CURRENT_BATTERY.get(), 1).pattern("vbv").pattern("fbf").pattern("fbf").define('b', YATMItems.CURRENT_BATTERY.get()).define('f', YATMItems.FOLIAR_STEEL.get()).define('v', YATMItems.CONDUIT_VINES.get()).unlockedBy("has_item", has(YATMItems.CURRENT_BATTERY.get())).save(output, YetAnotherTechMod.MODID + ":advanced_current_battery_from_shaped_crafting");

		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.SPEED_UPGRADE.get(), 1).pattern(" f ").pattern("fgf").pattern(" f ").define('f', YATMItems.FOLIAR_STEEL.get()).define('g', Tags.Items.INGOTS_GOLD).unlockedBy("has_item", has(YATMItems.FOLIAR_STEEL.get())).save(output, YetAnotherTechMod.MODID + ":speed_upgrade_from_shaped_crafting");
		// energy upgrade similar to speed, but with something current related maybe perhaps
		
		this.addPoweredToolRecipes(output);
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, YATMItems.STEEL_WRENCH.get(), 1).pattern("f f").pattern("fff").pattern(" f ").define('f', YATMItems.FOLIAR_STEEL.get()).unlockedBy("has_item", has(YATMItems.FOLIAR_STEEL.get())).save(output, YetAnotherTechMod.MODID + ":wrench_from_shaped_crafting");
		
	}// end addCraftingRecipes()
	
	private void addStorageBlockNuggetLikeRecipes(@NotNull RecipeOutput output) 
	{
		// netherite ingot nugget mutual interchange
		this.addNineToOne(output, YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, Items.NETHERITE_INGOT, YetAnotherTechMod.MODID + ":netherite_ingot_from_shapeless_crafting");
		this.addOneToNine(output, Tags.Items.INGOTS_NETHERITE, YATMItems.NETHERITE_NUGGET.get(), YetAnotherTechMod.MODID + ":netherite_nuggets_from_shapeless_crafting");

		// rubber block bar mutual interchange
		this.addOneToNine(output, YATMItemTags.FORGE_STORAGE_BLOCKS_RUBBER_KEY, YATMItems.RUBBER_BAR.get(), YetAnotherTechMod.MODID + ":rubber_bar_from_block_shapeless_crafting");
		this.addNineToOne(output, YATMItemTags.FORGE_RUBBER_INGOTS_KEY, YATMItems.RUBBER_BLOCK.get(), YetAnotherTechMod.MODID + ":rubber_block_from_bar_shapeless_crafting");

	} // end addStorageBlockNuggetLikeRecipes()
	
	private void addRubberWoodCoreRecipes(@NotNull RecipeOutput output) 
	{
		this.addTwoByTwoToX(output, YATMItems.RUBBER_LOG.get(), YATMItems.RUBBER_WOOD.get(), 3, YetAnotherTechMod.MODID + ":rubber_wood_from_shaped_crafting");
		this.addTwoByTwoToX(output, YATMItems.STRIPPED_RUBBER_LOG.get(), YATMItems.STRIPPED_RUBBER_WOOD.get(), 3, YetAnotherTechMod.MODID + ":stripped_rubber_wood_from_shaped_crafting");
		this.addOneToFour(output, YATMItemTags.RUBBER_TREE_LOGS_KEY, YATMItems.RUBBER_PLANKS.get(), YetAnotherTechMod.MODID + ":rubber_planks_from_shapeless_crafting");
		this.addFancyPlanks(output, YATMItems.RUBBER_PLANKS.get(), YATMItems.RUBBER_SLAB.get(), YATMItems.FANCY_RUBBER_PLANKS.get(), YetAnotherTechMod.MODID + ":fancy_rubber_planks_from_shaped_crafting");
		this.addStairs(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_STAIRS.get(), YetAnotherTechMod.MODID + ":rubber_stairs_from_shaped_crafting");
		this.addSlabs(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_SLAB.get(), YetAnotherTechMod.MODID + ":rubber_slab_from_shaped_crafting");
		this.addFence(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_FENCE.get(), YetAnotherTechMod.MODID + ":rubber_fence_from_shaped_crafting");
		this.addFenceGate(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_FENCE_GATE.get(), YetAnotherTechMod.MODID + ":rubber_fence_gate_from_shaped_crafting");
		this.addDoor(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_DOOR.get(), YetAnotherTechMod.MODID + ":rubber_door_from_shaped_crafting");
		this.addTrapdoor(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_TRAPDOOR.get(), YetAnotherTechMod.MODID + ":rubber_trapdoor_from_shaped_crafting");
		this.addPressurePlate(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_PRESSURE_PLATE.get(), YetAnotherTechMod.MODID + ":rubber_pressure_plate_from_shaped_crafting");
		this.addButton(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_BUTTON.get(), YetAnotherTechMod.MODID + ":rubber_button_from_shaped_crafting");
		
		this.addSign(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_SIGN.get(), YetAnotherTechMod.MODID + ":rubber_sign_from_shaped_crafting");
		this.addHangingSign(output, YATMItems.STRIPPED_RUBBER_LOG.get(), YATMItems.RUBBER_HANGING_SIGN.get(), YetAnotherTechMod.MODID + ":rubber_hanging_sign_from_shaped_crafting");
		this.addBoat(output, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_BOAT.get(), "rubber_boat_from_shaped_crafting");
		super.chestBoat(output, YATMItems.RUBBER_CHEST_BOAT.get(), YATMItems.RUBBER_BOAT.get());

	} // end addRubberWoodCoreRecipes()
	
	private void addSoulAfflictedRubberWoodCoreRecipes(@NotNull RecipeOutput output) 
	{
		this.addTwoByTwoToX(output, YATMItems.SOUL_AFFLICTED_RUBBER_LOG.get(), YATMItems.SOUL_AFFLICTED_RUBBER_WOOD.get(), 3, YetAnotherTechMod.MODID + ":soul_afflicted_rubber_wood_from_shaped_crafting");
		this.addTwoByTwoToX(output, YATMItems.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), YATMItems.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), 3, YetAnotherTechMod.MODID + ":soul_afflicted_stripped_rubber_wood_from_shaped_crafting");
		this.addOneToFour(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_LOGS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_planks_from_shapeless_crafting");
		this.addFancyPlanks(output, YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS.get(), YATMItems.SOUL_AFFLICTED_RUBBER_SLAB.get(), YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), YetAnotherTechMod.MODID + ":soul_afflicted_fancy_rubber_planks_tiled_from_shaped_crafting");

		// TODO, make better obtainable, such as not conflicting with the button crafting recipe
		this.addOneToX(output, YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), 1, YetAnotherTechMod.MODID + ":soul_afflicted_fancy_rubber_planks_from_tiled_from_shapeless_crafting");
		this.addOneToX(output, YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), YATMItems.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), 1, YetAnotherTechMod.MODID + ":soul_afflicted_fancy_rubber_planks_tiled_from_untiled_from_shapeless_crafting");
		
		this.addStairs(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_STAIRS.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_stairs_from_shaped_crafting");
		this.addSlabs(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_SLAB.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_slab_from_shaped_crafting");
		this.addFence(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_FENCE.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_fence_from_shaped_crafting");
		this.addFenceGate(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_fence_gate_from_shaped_crafting");
		this.addDoor(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_DOOR.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_door_from_shaped_crafting");
		this.addTrapdoor(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_trapdoor_from_shaped_crafting");
		this.addPressurePlate(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_pressure_plate_from_shaped_crafting");
		this.addButton(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_BUTTON.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_button_from_shaped_crafting");
		
		this.addSign(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_SIGN.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_sign_from_shaped_crafting");
		this.addHangingSign(output, YATMItems.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), YATMItems.SOUL_AFFLICTED_RUBBER_HANGING_SIGN.get(), YetAnotherTechMod.MODID + ":soul_afflicted_rubber_hanging_sign_from_shaped_crafting");
		this.addBoat(output, YATMItemTags.SOUL_AFFLICTED_RUBBER_TREE_PLANKS_KEY, YATMItems.SOUL_AFFLICTED_RUBBER_BOAT.get(), "soul_afflicted_rubber_boat_from_shaped_crafting");
		super.chestBoat(output, YATMItems.SOUL_AFFLICTED_RUBBER_CHEST_BOAT.get(), YATMItems.SOUL_AFFLICTED_RUBBER_BOAT.get());

	} // end addSoulAfflictedRubberWoodCoreRecipes()

	private void addCandleLanterns(@NotNull RecipeOutput output) 
	{
		TriConsumer<Item, String, Item> forCandle = (candle, color, lantern) -> ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, lantern, 1)
		.pattern("iii")
		.pattern("ici")
		.pattern("iii")
		.define('i', Tags.Items.NUGGETS_IRON)
		.define('c', candle)
		.unlockedBy("has_item", has(candle))
		.save(output, YetAnotherTechMod.MODID + ":" + color + "candle_lantern_from_shaped_crafting");
		
		forCandle.accept(Items.CANDLE, "", YATMItems.CANDLE_LANTERN.get());
		forCandle.accept(Items.WHITE_CANDLE, "white_", YATMItems.WHITE_CANDLE_LANTERN.get());
		forCandle.accept(Items.ORANGE_CANDLE, "orange_", YATMItems.ORANGE_CANDLE_LANTERN.get());
		forCandle.accept(Items.MAGENTA_CANDLE, "magenta_", YATMItems.MAGENTA_CANDLE_LANTERN.get());
		forCandle.accept(Items.LIGHT_BLUE_CANDLE, "light_blue_", YATMItems.LIGHT_BLUE_CANDLE_LANTERN.get());
		forCandle.accept(Items.YELLOW_CANDLE, "yellow_", YATMItems.YELLOW_CANDLE_LANTERN.get());
		forCandle.accept(Items.LIME_CANDLE, "lime_", YATMItems.LIME_CANDLE_LANTERN.get());
		forCandle.accept(Items.PINK_CANDLE, "pink_", YATMItems.PINK_CANDLE_LANTERN.get());
		forCandle.accept(Items.GRAY_CANDLE, "gray_", YATMItems.GRAY_CANDLE_LANTERN.get());
		forCandle.accept(Items.LIGHT_GRAY_CANDLE, "light_gray_", YATMItems.LIGHT_GRAY_CANDLE_LANTERN.get());
		forCandle.accept(Items.CYAN_CANDLE, "cyan_", YATMItems.CYAN_CANDLE_LANTERN.get());
		forCandle.accept(Items.PURPLE_CANDLE, "purple_", YATMItems.PURPLE_CANDLE_LANTERN.get());
		forCandle.accept(Items.BLUE_CANDLE, "blue_", YATMItems.BLUE_CANDLE_LANTERN.get());
		forCandle.accept(Items.BROWN_CANDLE, "brown_", YATMItems.BROWN_CANDLE_LANTERN.get());
		forCandle.accept(Items.GREEN_CANDLE, "green_", YATMItems.GREEN_CANDLE_LANTERN.get());
		forCandle.accept(Items.RED_CANDLE, "red_", YATMItems.RED_CANDLE_LANTERN.get());
		forCandle.accept(Items.BLACK_CANDLE, "black_", YATMItems.BLACK_CANDLE_LANTERN.get());		
	} // end addCandleLanterns()
	
	private void addPoweredToolRecipes(@NotNull RecipeOutput output) 
	{
//		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.WOODEN_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_WOOD.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_wood_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.STONE_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_STONE.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_stone_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.IRON_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_IRON.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_iron_from_part_exchange");
		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.STEEL_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_STEEL.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_steel_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.GOLD_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_GOLD.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_gold_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.DIAMOND_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_DIAMOND.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_diamond_from_part_exchange");
		this.addPartExchange(output, YATMItemTags.DRILLS_KEY, new ItemStack(YATMItems.NETHERITE_DRILL_BIT.get()), new ItemStack(YATMItems.STEEL_DRILL_NETHERITE.get()), "drill_tip_adjustment", YetAnotherTechMod.MODID + ":steel_drill_netherite_from_part_exchange");
		
//		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.WOODEN_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_WOOD.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_wood_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.STONE_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_STONE.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_stone_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.IRON_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_IRON.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_iron_from_part_exchange");
		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.STEEL_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_STEEL.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_steel_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.GOLD_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_GOLD.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_gold_from_part_exchange");
//		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.DIAMOND_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_DIAMOND.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_diamond_from_part_exchange");
		this.addPartExchange(output, YATMItemTags.SAWS_KEY, new ItemStack(YATMItems.NETHERITE_SAW_BLADE.get()), new ItemStack(YATMItems.STEEL_SAW_NETHERITE.get()), "saw_blade_adjustment", YetAnotherTechMod.MODID + ":steel_saw_netherite_from_part_exchange");
		
		
		TriConsumer<Item, Item, Item> addDrillBit = (part, ingot, nugget) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, part, 1).pattern("  n").pattern(" n ").pattern("i  ").define('i', ingot).define('n', nugget).unlockedBy("has_item", has(part)).save(output, YetAnotherTechMod.MODID + ":" + part.getDescriptionId() + "_from_shaped_crafting");
		TriConsumer<Item, Item, Item> addSawBlade = (part, ingot, nugget) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, part, 1).pattern(" nn").pattern("nin").pattern("in ").define('i', ingot).define('n', nugget).unlockedBy("has_item", has(part)).save(output, YetAnotherTechMod.MODID + ":" + part.getDescriptionId() + "_from_shaped_crafting");
		
		addDrillBit.accept(YATMItems.STEEL_DRILL_BIT.get(), YATMItems.FOLIAR_STEEL.get(), YATMItems.FOLIAR_STEEL_SHRED.get());
		addDrillBit.accept(YATMItems.NETHERITE_DRILL_BIT.get(), Items.NETHERITE_INGOT, YATMItems.NETHERITE_NUGGET.get());
		
		addSawBlade.accept(YATMItems.STEEL_SAW_BLADE.get(), YATMItems.FOLIAR_STEEL.get(), YATMItems.FOLIAR_STEEL_SHRED.get());
		addSawBlade.accept(YATMItems.NETHERITE_SAW_BLADE.get(), Items.NETHERITE_INGOT, YATMItems.NETHERITE_NUGGET.get());
		
		BiConsumer<Item, Item> addTool = (tool, part) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, tool, 1).pattern(" lb").pattern("fdf").pattern("cf ").define('l', Items.LEVER) // TODO, eventually probably replace with a current switch
		.define('b', part).define('c', YATMItems.CURRENT_BATTERY.get()) // TODO, make copy current over to final device
		.define('d', YATMItems.KINETIC_DRIVER.get()).define('f', YATMItems.FOLIAR_STEEL.get()).unlockedBy("has_item", has(part)).save(output, YetAnotherTechMod.MODID + ":" + tool.getDescriptionId() + "_from_shaped_crafting");
		
		addTool.accept(YATMItems.STEEL_DRILL_STEEL.get(), YATMItems.STEEL_DRILL_BIT.get());
		addTool.accept(YATMItems.STEEL_DRILL_NETHERITE.get(), YATMItems.NETHERITE_DRILL_BIT.get());
		
		addTool.accept(YATMItems.STEEL_SAW_STEEL.get(), YATMItems.STEEL_SAW_BLADE.get());
		addTool.accept(YATMItems.STEEL_SAW_NETHERITE.get(), YATMItems.NETHERITE_SAW_BLADE.get());
	} // end addPoweredToolRecipes()
	
	private void addDeviceRecipes(@NotNull RecipeOutput output) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.STEEL_BOILER.get(), 1).pattern("fff").pattern("g g").pattern("fbf").define('g', Tags.Items.GLASS).define('b', Items.BLAST_FURNACE).define('f', YATMItems.FOLIAR_STEEL.get()).unlockedBy("has_item", has(Items.BLAST_FURNACE)).save(output, YetAnotherTechMod.MODID + ":steel_boiler_from_shaped_crafting");
		
	} // end addDeviceRecipes()
	
	
	
	private void addSmeltingRecipes(@NotNull RecipeOutput output) 
	{
		this.addSmelting(output, YATMItems.LATEX_BUCKET.get(), YATMItems.RUBBER_BLOCK.get(), .3f, 60, YetAnotherTechMod.MODID + ":rubber_block_from_latex_smelting");
		
		this.addSmelting(output, YATMItemTags.FOLIAR_STEEL_ORES_KEY, YATMItems.FOLIAR_STEEL.get(), .1f, 200, YetAnotherTechMod.MODID + ":foliar_steel_from_ore_smelting");
		
		this.addSmelting(output, YATMItems.VARIEGATED_CACTUS.get(), Items.GREEN_DYE, 1.0f, 100, YetAnotherTechMod.MODID + ":green_dye_from_variegated_cactus_smelting");
		
		this.addSmelting(output, YATMItems.DILUTED_TEAR_BOTTLE.get(), Items.GHAST_TEAR, 1.0f, 100, YetAnotherTechMod.MODID + ":ghast_tear_from_diluted_tear_smelting");
		
		this.addSmelting(output, YATMItems.BIO_BUCKET.get(), Items.BONE_MEAL, 1.0f, 100, YetAnotherTechMod.MODID + ":bonemeal_from_bio_bucket_smelting");
		
	} // end addSmeltingRecipes()
	
	
	
	
	
	private void addBioreactionRecipes(@NotNull RecipeOutput output) 
	{
		this.addChorusBioreactions(output);
		this.addBiolingRecipe(output, Items.POISONOUS_POTATO, new FluidStack(YATMFluids.BIO.get(), 200), 2, 20, YetAnotherTechMod.MODID + ":biofluid_from_poisonous_potato_bioling");
		// TODO, add biofuel from wood or sugar or such thigns.
		
	} // end addBioreactionRecipes()
	
	private void addChorusBioreactions(@NotNull RecipeOutput output) 
	{
		this.addBiolingRecipe(output, Items.CHORUS_PLANT, new FluidStack(YATMFluids.CHORUS_BIO.get(), 120), 6, 120, YetAnotherTechMod.MODID + ":chorus_biofluid_from_chorus_stem_bioling");
		this.addBiolingRecipe(output, Items.CHORUS_FLOWER, new FluidStack(YATMFluids.CHORUS_BIO.get(), 1000), 4, 60, YetAnotherTechMod.MODID + ":chorus_biofluid_from_chorus_flower_bioling");
		this.addBiolingRecipe(output, Items.CHORUS_FRUIT, new FluidStack(YATMFluids.CHORUS_BIO.get(), 200), 2, 20, YetAnotherTechMod.MODID + ":chorus_biofluid_from_chorus_fruit_bioling");
		
		this.addBiolingRecipe(output, Items.POPPED_CHORUS_FRUIT, new FluidStack(YATMFluids.CHORUS_BIO.get(), 240), 6, 48, YetAnotherTechMod.MODID + ":chorus_biofluid_from_popped_chorus_fruit_bioling");
		this.addBiolingRecipe(output, Items.PURPUR_BLOCK, new FluidStack(YATMFluids.CHORUS_BIO.get(), 960), 6, 192, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_block_bioling");
		this.addBiolingRecipe(output, Items.PURPUR_PILLAR, new FluidStack(YATMFluids.CHORUS_BIO.get(), 960), 6, 192, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_pillar_bioling");
		this.addBiolingRecipe(output, Items.PURPUR_SLAB, new FluidStack(YATMFluids.CHORUS_BIO.get(), 480), 6, 96, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_slab_bioling");
		this.addBiolingRecipe(output, Items.PURPUR_STAIRS, new FluidStack(YATMFluids.CHORUS_BIO.get(), 720), 6, 144, YetAnotherTechMod.MODID + ":chorus_biofluid_from_purpur_stairs_bioling");

	} // end addChorusBioreactions()
	
	
	
	private void addBoilingRecipes(@NotNull RecipeOutput output) 
	{
		this.addBoilingRecipe(output, new FluidStack(YATMFluids.SOUL_SAP.get(), 300), new FluidStack(YATMFluids.SOUL_SYRUP.get(), 60), 722, 900, YetAnotherTechMod.MODID + ":soul_syrup_from_soul_sap_boiling");
		// TODO, steam as a power option
	} // end addBoilingRecipes()
	
	
	
	private void addCrystallizationRecipes(@NotNull RecipeOutput output) 
	{
		this.addCrystallizationRecipe(output, YATMFluidTags.FORGE_ENDER_KEY, 250, Tags.Items.SAND, new ItemStack(Items.ENDER_PEARL), false, 64, 300, YetAnotherTechMod.MODID + ":ender_pearl_from_crystallization");
		this.addNetherStarCrystallizationProgression(output);
		this.addSiliconOxideCrystallizations(output);
		
	} // end addCrystallizationRecipes()
	
	private void addNetherStarCrystallizationProgression(@NotNull RecipeOutput output) 
	{
		this.addCrystallizationRecipe(output, new FluidStack(YATMFluids.ESSENCE_OF_DECAY.get(), 1000), YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, new ItemStack(YATMItems.STAR_SEED.get()), true, 256, 300, YetAnotherTechMod.MODID + ":star_seed_from_crystallization");
		this.addCrystallizationRecipe(output, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 1000), YATMItems.STAR_SEED.get(), new ItemStack(YATMItems.STAR_GERMLING.get()), true, 256, 200, YetAnotherTechMod.MODID + ":star_germling_from_crystallization");
		this.addCrystallizationRecipe(output, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 2000), YATMItems.STAR_GERMLING.get(), new ItemStack(YATMItems.STAR_SPROUT.get()), true, 512, 400, YetAnotherTechMod.MODID + ":star_sprout_from_crystallization");
		this.addCrystallizationRecipe(output, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 4000), YATMItems.STAR_SPROUT.get(), new ItemStack(YATMItems.STAR_ADOLESCENT.get()), true, 512, 800, YetAnotherTechMod.MODID + ":star_adolescent_from_crystallization");
		this.addCrystallizationRecipe(output, new FluidStack(YATMFluids.ESSENCE_OF_SOULS.get(), 8000), YATMItems.STAR_ADOLESCENT.get(), new ItemStack(Items.NETHER_STAR), true, 1024, 1600, YetAnotherTechMod.MODID + ":nether_star_from_crystallization");
	} // end addNetherStarCrystallizationProgression()
	
	private void addSiliconOxideCrystallizations(@NotNull RecipeOutput output) 
	{
		this.addCrystallizationRecipe(output, YATMFluidTags.FORGE_SILICON_OXIDE_KEY, 1000, Tags.Items.GLASS_SILICA, new ItemStack(Items.GLASS, 1), false, 36, 300, YetAnotherTechMod.MODID + ":glass_from_silicon_oxide_crystallization");
		this.addCrystallizationRecipe(output, YATMFluidTags.FORGE_SILICON_OXIDE_KEY, 250, Tags.Items.GEMS_AMETHYST, new ItemStack(Items.AMETHYST_SHARD, 1), false, 36, 300, YetAnotherTechMod.MODID + ":amethyst_shards_from_silicon_oxide_crystallization");
		this.addCrystallizationRecipe(output, YATMFluidTags.FORGE_SILICON_OXIDE_KEY, 250, Tags.Items.GEMS_QUARTZ, new ItemStack(Items.QUARTZ, 1), false, 36, 300, YetAnotherTechMod.MODID + ":quartz_from_silicon_oxide_crystallization");
	} // end addSiliconOxideCrystallizations()
	
	
	
	private void addExtractionRecipes(@NotNull RecipeOutput output)
	{
		this.addExtraction(output, YATMItemTags.LATEX_EXTRACTABLE_LOGS_KEY, new ItemStack(YATMItems.WOOD_PULP.get(), 6), new FluidStack(YATMFluids.LATEX.get(), 120), 12, 246, YetAnotherTechMod.MODID + ":latex_from_log_extraction");
		this.addExtraction(output, YATMItems.RUBBER_LEAVES_OLD.get(), new ItemStack(YATMItems.WOOD_PULP.get(), 1), new FluidStack(YATMFluids.LATEX.get(), 40), 4, 62, YetAnotherTechMod.MODID + ":latex_from_old_leaves_extraction");
		this.addExtraction(output, YATMItems.RUBBER_LEAVES_FLOWERING.get(), new ItemStack(YATMItems.LEAF_MULCH.get(), 2), new FluidStack(YATMFluids.LATEX.get(), 30), 4, 46, YetAnotherTechMod.MODID + ":latex_from_flowering_leaves_extraction");
		this.addExtraction(output, YATMItems.RUBBER_LEAVES_YOUNG.get(), new ItemStack(YATMItems.LEAF_MULCH.get(), 1), new FluidStack(YATMFluids.LATEX.get(), 20), 4, 34, YetAnotherTechMod.MODID + ":latex_from_young_leaves_extraction");
		this.addExtraction(output, YATMItems.RUBBER_MERISTEM.get(), new FluidStack(YATMFluids.LATEX.get(), 2), 1, 12, YetAnotherTechMod.MODID + ":latex_from_meristem_extraction");
		
		// TODO, maybe something with soul sand and soil
		this.addExtraction(output, YATMItemTags.SOUL_SAP_EXTRACTABLE_LOGS_KEY, new ItemStack(YATMItems.WOOD_PULP.get(), 6), new FluidStack(YATMFluids.SOUL_SAP.get(), 120), 12, 246, YetAnotherTechMod.MODID + ":soul_sap_from_log_extraction");
		this.addExtraction(output, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new ItemStack(YATMItems.WOOD_PULP.get(), 1), new FluidStack(YATMFluids.SOUL_SAP.get(), 40), 4, 62, YetAnotherTechMod.MODID + ":soul_sap_from_old_leaves_extraction");
		this.addExtraction(output, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new ItemStack(YATMItems.LEAF_MULCH.get(), 2), new FluidStack(YATMFluids.SOUL_SAP.get(), 40), 4, 46, YetAnotherTechMod.MODID + ":soul_sap_from_flowering_leaves_extraction");
		this.addExtraction(output, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new ItemStack(YATMItems.LEAF_MULCH.get(), 1), new FluidStack(YATMFluids.SOUL_SAP.get(), 20), 4, 34, YetAnotherTechMod.MODID + ":soul_sap_from_young_leaves_extraction");
		this.addExtraction(output, YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), new FluidStack(YATMFluids.SOUL_SAP.get(), 2), 1, 12, YetAnotherTechMod.MODID + ":soul_sap_from_meristem_extraction");
	
	} // end addExtractionRecipes()
	
	
	
	private void addGrindingRecipes(@NotNull RecipeOutput output) 
	{
		// review wood pulp related things, and add more
		this.addGrindingRecipe(output, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), new ItemStack(YATMItems.SOUL_AFFLICTED_LEAF_MULCH.get()), 3, 40, YetAnotherTechMod.MODID + ":soul_leaf_mulch_from_old_leaf_grinding");
		this.addGrindingRecipe(output, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), new ItemStack(YATMItems.SOUL_AFFLICTED_LEAF_MULCH.get()), 2, 26, YetAnotherTechMod.MODID + ":soul_leaf_mulch_from_flowering_leaf_grinding");
		this.addGrindingRecipe(output, YATMItems.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), new ItemStack(YATMItems.SOUL_AFFLICTED_LEAF_MULCH.get()), 1, 20, YetAnotherTechMod.MODID + ":soul_leaf_mulch_from_young_leaf_grinding");
		
		this.addGrindingRecipe(output, YATMItems.RUBBER_LEAVES_OLD.get(), new ItemStack(YATMItems.LEAF_MULCH.get()), 3, 40, YetAnotherTechMod.MODID + ":leaf_mulch_from_old_leaf_grinding");
		this.addGrindingRecipe(output, YATMItems.RUBBER_LEAVES_FLOWERING.get(), new ItemStack(YATMItems.LEAF_MULCH.get()), 2, 26, YetAnotherTechMod.MODID + ":leaf_mulch_from_flowering_leaf_grinding");
		this.addGrindingRecipe(output, YATMItems.RUBBER_LEAVES_YOUNG.get(), new ItemStack(YATMItems.LEAF_MULCH.get()), 1, 20, YetAnotherTechMod.MODID + ":leaf_mulch_from_young_leaf_grinding");
		
		
		// TODO, make grinding recipes time proportional to hardness, and current proportional to the tool required.
		this.addRootedSoilReversions(output);
		this.addGrinderBrickCrackings(output);
		this.addGrinderStoneTowardsCobblestone(output);
		this.addGrindingRecipe(output, Items.COBBLESTONE, new ItemStack(Items.GRAVEL), 3, 80, YetAnotherTechMod.MODID + ":gravel_from_cobblestone_grinding");
		this.addGrindToSand(output);
		this.addGrindPurpur(output);
		
	} // end addGrindingRecipes()
	

	private void addRootedSoilReversions(@NotNull RecipeOutput output) 
	{
		this.addGrindingRecipe(output, YATMItemTags.FORGE_ROOTED_DIRT_KEY, new ItemStack(Items.DIRT), 2, 40, YetAnotherTechMod.MODID + ":dirt_from_rooted_grinding");
		this.addGrindingRecipe(output, YATMItems.ROOTED_SOUL_SOIL.get(), new ItemStack(Items.SOUL_SOIL), 2, 40, YetAnotherTechMod.MODID + ":soul_soil_from_rooted_grinding");
	} // end addRootedSoilReversions()
	
	private void addGrinderBrickCrackings(@NotNull RecipeOutput output) 
	{
		this.addGrindingRecipe(output, Items.STONE_BRICKS, new ItemStack(Items.CRACKED_STONE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_stone_bricks_from_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.DEEPSLATE_BRICKS, new ItemStack(Items.CRACKED_DEEPSLATE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_deepslate_bricks_from_deepslate_bricks_grinding");
		this.addGrindingRecipe(output, Items.DEEPSLATE_TILES, new ItemStack(Items.CRACKED_DEEPSLATE_TILES), 1, 20, YetAnotherTechMod.MODID + ":cracked_deepslate_tiles_from_deepslate_tiles_grinding");
		this.addGrindingRecipe(output, Items.NETHER_BRICKS, new ItemStack(Items.CRACKED_NETHER_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_nether_bricks_from_nether_brick_grinding");
		this.addGrindingRecipe(output, Items.POLISHED_BLACKSTONE_BRICKS, new ItemStack(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":cracked_polished_blackstone_bricks_from_polished_blackstone_brick_grinding");
		this.addGrindingRecipe(output, Items.INFESTED_STONE_BRICKS, new ItemStack(Items.INFESTED_CRACKED_STONE_BRICKS), 1, 20, YetAnotherTechMod.MODID + ":infested_cracked_stone_bricks_from_infested_stone_bricks_grinding");
	} // end addGrinderBrickCrackings()
	
	private void addGrinderStoneTowardsCobblestone(@NotNull RecipeOutput output) 
	{
		this.addGrindingRecipe(output, Items.STONE, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_grinding");
		this.addGrindingRecipe(output, Items.INFESTED_STONE, new ItemStack(Items.INFESTED_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_infested_stone_grinding");
		this.addGrindingRecipe(output, Items.CRACKED_STONE_BRICKS, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_cracked_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.INFESTED_CRACKED_STONE_BRICKS, new ItemStack(Items.INFESTED_COBBLESTONE), 1, 20, YetAnotherTechMod.MODID + ":infested_cobblestone_from_infested_cracked_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.CHISELED_STONE_BRICKS, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_chiseled_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.INFESTED_CHISELED_STONE_BRICKS, new ItemStack(Items.INFESTED_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":infested_cobblestone_from_infested_chiseled_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.MOSSY_STONE_BRICKS, new ItemStack(Items.MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.INFESTED_MOSSY_STONE_BRICKS, new ItemStack(Items./* INFESTED_ */MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_infested_mossy_stone_bricks_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_STONE, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_smooth_stone_grinding");

		this.addGrindingRecipe(output, Items.STONE_STAIRS, 4, new ItemStack(Items.COBBLESTONE, 3), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_stairs_grinding");
		this.addGrindingRecipe(output, Items.STONE_BRICK_STAIRS, 4, new ItemStack(Items.COBBLESTONE, 3), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_brick_stairs_grinding");
		this.addGrindingRecipe(output, Items.MOSSY_STONE_BRICK_STAIRS, 4, new ItemStack(Items.MOSSY_COBBLESTONE, 3), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_brick_stairs_grinding");
		
		this.addGrindingRecipe(output, Items.STONE_SLAB, 2, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_slab_grinding");
		this.addGrindingRecipe(output, Items.STONE_BRICK_SLAB, 2, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_brick_slab_grinding");
		this.addGrindingRecipe(output, Items.MOSSY_STONE_BRICK_SLAB, 2, new ItemStack(Items.MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_brick_slab_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_STONE_SLAB, 2, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_smooth_stone_slab_grinding");
		
		this.addGrindingRecipe(output, Items.STONE_BRICK_WALL, new ItemStack(Items.COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":cobblestone_from_stone_brick_wall_grinding");
		this.addGrindingRecipe(output, Items.MOSSY_STONE_BRICK_WALL, new ItemStack(Items.MOSSY_COBBLESTONE), 3, 80, YetAnotherTechMod.MODID + ":mossy_cobblestone_from_mossy_stone_brick_wall_grinding");
		
	} // end addGrinderBrickCrackings()
	
	private void addGrindToSand(@NotNull RecipeOutput output) 
	{
		this.addGrindingRecipe(output, Items.GRAVEL, new ItemStack(Items.SAND), 3, 80, YetAnotherTechMod.MODID + ":sand_from_gravel_grinding");

		this.addGrindingRecipe(output, Tags.Items.GLASS, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_glass_grinding");
		this.addGrindingRecipe(output, Tags.Items.GLASS_PANES, 16, new ItemStack(Items.SAND, 6), 1, 20, YetAnotherTechMod.MODID + ":sand_from_glass_pane_grinding");
		this.addGrindingRecipe(output, Items.GLASS_BOTTLE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_glass_bottle_grinding");
		

		
		this.addGrindingRecipe(output, Items.SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_grinding");
		this.addGrindingRecipe(output, Items.CHISELED_SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_chiseled_sandstone_grinding");
		this.addGrindingRecipe(output, Items.CUT_SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_cut_sandstone_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_SANDSTONE, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_smooth_sandstone_grinding");

		this.addGrindingRecipe(output, Items.SANDSTONE_STAIRS, 4, new ItemStack(Items.SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_stair_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_SANDSTONE_STAIRS, 4, new ItemStack(Items.SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":sand_from_smooth_sandstone_stair_grinding");

		this.addGrindingRecipe(output, Items.SANDSTONE_SLAB, 2, new ItemStack(Items.SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_slab_grinding");
		this.addGrindingRecipe(output, Items.CUT_STANDSTONE_SLAB, 2, new ItemStack(Items.SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_cut_sandstone_slab_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_SANDSTONE_SLAB, 2, new ItemStack(Items.SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_smooth_sandstone_slab_grinding");

		this.addGrindingRecipe(output, Items.SANDSTONE_WALL, new ItemStack(Items.SAND), 1, 20, YetAnotherTechMod.MODID + ":sand_from_sandstone_wall_grinding");

		
		
		
		this.addGrindingRecipe(output, Items.RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_sandstone_grinding");
		this.addGrindingRecipe(output, Items.CHISELED_RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_chiseled_sandstone_grinding");
		this.addGrindingRecipe(output, Items.CUT_RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_cut_sandstone_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_RED_SANDSTONE, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_smooth_sandstone_grinding");

		this.addGrindingRecipe(output, Items.RED_SANDSTONE_STAIRS, 4, new ItemStack(Items.RED_SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_red_sandstone_stair_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_RED_SANDSTONE_STAIRS, 4, new ItemStack(Items.RED_SAND, 3), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_smooth_red_sandstone_stair_grinding");

		this.addGrindingRecipe(output, Items.RED_SANDSTONE_SLAB, 2, new ItemStack(Items.RED_SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_red_sandstone_slab_grinding");
		this.addGrindingRecipe(output, Items.CUT_RED_SANDSTONE_SLAB, 2, new ItemStack(Items.RED_SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sand_from_cut_red_sandstone_slab_grinding");
		this.addGrindingRecipe(output, Items.SMOOTH_RED_SANDSTONE_SLAB, 2, new ItemStack(Items.RED_SAND, 1), 1, 20, YetAnotherTechMod.MODID + ":sred_and_from_smooth_red_sandstone_slab_grinding");

		this.addGrindingRecipe(output, Items.RED_SANDSTONE_WALL, new ItemStack(Items.RED_SAND), 1, 20, YetAnotherTechMod.MODID + ":red_sand_from_red_sandstone_wall_grinding");
		
	} // end addGrinderBrickCrackings()
	
	private void addGrindPurpur(@NotNull RecipeOutput output) 
	{
		
		this.addGrindingRecipe(output, Items.PURPUR_BLOCK, new ItemStack(Items.POPPED_CHORUS_FRUIT, 4), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_block_grinding");
		this.addGrindingRecipe(output, Items.PURPUR_PILLAR, new ItemStack(Items.POPPED_CHORUS_FRUIT, 4), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_pillar_grinding");

		this.addGrindingRecipe(output, Items.PURPUR_SLAB, 2, new ItemStack(Items.POPPED_CHORUS_FRUIT, 4), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_slab_grinding");
		this.addGrindingRecipe(output, Items.PURPUR_STAIRS, new ItemStack(Items.POPPED_CHORUS_FRUIT, 3), 4, 80, YetAnotherTechMod.MODID + ":popped_chorus_fruit_from_purpur_stairs_grinding");

	} // end addGrinderBrickCrackings()
	
	
	
	private void addInjectingRecipes(@NotNull RecipeOutput output)
	{
		// TODO, should the biofluid be a fluid tag?
		new InjectingRecipeBuilder().input(new FluidStackIngredient(new FluidStack(YATMFluids.BIO.get(), 80))).substrate(new ItemTagIngredient(YATMItemTags.SOIL_MINERALS_KEY)).result(new ItemStack(Items.DIRT)).currentPerTick(18).timeInTicks(60).unlockedBy("has_ingredient", has(YATMItemTags.SOIL_MINERALS_KEY)).save(output, new ResourceLocation(YetAnotherTechMod.MODID, "dirt_from_biofluid_mineral_injecting"));
	} // end addInjectingRecipes()

	
	
	private void addMeltingRecipes(@NotNull RecipeOutput output) 
	{
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.ICE)), new FluidStack(Fluids.WATER, 1000), 273, 360, RecipeProvider.has(Items.ICE), YetAnotherTechMod.MODID + ":water_from_ice_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.ENDER_PEARL)), new FluidStack(YATMFluids.ENDER.get(), 1000), 2400, 520, RecipeProvider.has(Items.ENDER_PEARL), YetAnotherTechMod.MODID + ":ender_from_ender_pearl_melting");
		this.addSiliconOxideMeltings(output);
				
	} // end addMeltingRecipes()
	
	private void addSiliconOxideMeltings(@NotNull RecipeOutput output) 
	{
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.STORAGE_BLOCKS_AMETHYST), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 360, RecipeProvider.has(Tags.Items.STORAGE_BLOCKS_AMETHYST), YetAnotherTechMod.MODID + ":silicon_oxide_from_amethyst_storage_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.BUDDING_AMETHYST, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 360, RecipeProvider.has(Items.BUDDING_AMETHYST), YetAnotherTechMod.MODID + ":silicon_oxide_from_budding_amethyst_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.AMETHYST_CLUSTER, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 3000), 1989, 720, RecipeProvider.has(Items.AMETHYST_CLUSTER), YetAnotherTechMod.MODID + ":silicon_oxide_from_amethyst_cluster_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.LARGE_AMETHYST_BUD, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 240, RecipeProvider.has(Items.LARGE_AMETHYST_BUD), YetAnotherTechMod.MODID + ":silicon_oxide_from_large_amethyst_bud_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.MEDIUM_AMETHYST_BUD, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 500), 1989, 200, RecipeProvider.has(Items.MEDIUM_AMETHYST_BUD), YetAnotherTechMod.MODID + ":silicon_oxide_from_medium_amethyst_bud_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.SMALL_AMETHYST_BUD, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 250), 1989, 160, RecipeProvider.has(Items.SMALL_AMETHYST_BUD), YetAnotherTechMod.MODID + ":silicon_oxide_from_small_ameythyst_bud_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.GEMS_AMETHYST), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 250), 1989, 160, RecipeProvider.has(Tags.Items.GEMS_AMETHYST), YetAnotherTechMod.MODID + ":silicon_oxide_from_amethyst_gem_melting");
		
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.GLASS_SILICA), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 360, RecipeProvider.has(Tags.Items.GLASS_SILICA), YetAnotherTechMod.MODID + ":silicon_oxide_from_glass_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.GLASS_PANES), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 375), 1989, 135, RecipeProvider.has(Tags.Items.GLASS_PANES), YetAnotherTechMod.MODID + ":silicon_oxide_from_glass_pane_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.GLASS_BOTTLE, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 400, RecipeProvider.has(Items.GLASS_BOTTLE), YetAnotherTechMod.MODID + ":silicon_oxide_from_glass_bottle_melting");
		this.addMeltingRecipe(output, new ItemStackIngredient(new ItemStack(Items.TINTED_GLASS, 1)), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 360, RecipeProvider.has(Items.TINTED_GLASS), YetAnotherTechMod.MODID + ":silicon_oxide_from_tinted_glass_melting");
		
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.SAND), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 330, RecipeProvider.has(Tags.Items.SAND), YetAnotherTechMod.MODID + ":silicon_oxide_from_sand_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.SANDSTONE), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 4000), 1989, 1440, RecipeProvider.has(Tags.Items.SANDSTONE), YetAnotherTechMod.MODID + ":silicon_oxide_from_sandstone_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(YATMItemTags.FORGE_SLABS_SANDSTONE), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 2000), 1989, 720, RecipeProvider.has(YATMItemTags.FORGE_SLABS_SANDSTONE), YetAnotherTechMod.MODID + ":silicon_oxide_from_sandstone_slab_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(YATMItemTags.FORGE_STAIRS_SANDSTONE), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 3000), 1989, 1080, RecipeProvider.has(YATMItemTags.FORGE_STAIRS_SANDSTONE), YetAnotherTechMod.MODID + ":silicon_oxide_from_sandstone_stairs_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(YATMItemTags.FORGE_WALLS_SANDSTONE), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 4000), 1989, 1440, RecipeProvider.has(YATMItemTags.FORGE_WALLS_SANDSTONE), YetAnotherTechMod.MODID + ":silicon_oxide_from_sandstone_wall_melting");
		
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.STORAGE_BLOCKS_QUARTZ), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 1000), 1989, 360, RecipeProvider.has(Tags.Items.STORAGE_BLOCKS_QUARTZ), YetAnotherTechMod.MODID + ":silicon_oxide_from_quartz_storage_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(YATMItemTags.FORGE_SLABS_QUARTZ), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 750), 1989, 360, RecipeProvider.has(YATMItemTags.FORGE_SLABS_QUARTZ), YetAnotherTechMod.MODID + ":silicon_oxide_from_quartz_slab_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(YATMItemTags.FORGE_STAIRS_QUARTZ), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 750), 1989, 360, RecipeProvider.has(YATMItemTags.FORGE_STAIRS_QUARTZ), YetAnotherTechMod.MODID + ":silicon_oxide_from_quartz_stair_melting");
		this.addMeltingRecipe(output, new ItemTagIngredient(Tags.Items.GEMS_QUARTZ), new FluidStack(YATMFluids.SILICON_OXIDE.get(), 250), 1989, 160, RecipeProvider.has(Tags.Items.GEMS_QUARTZ), YetAnotherTechMod.MODID + ":silicon_oxide_from_quartz_gem_melting");
		
		
	} // end addSiliconOxideMeltings()
	
	
	
	private void addSpinningRecipes(@NotNull RecipeOutput output) 
	{
		this.addSpinningRecipe(output, YATMItems.RAW_COTTON_FIBER.get(), new ItemStack(Items.STRING, 2), YetAnotherTechMod.MODID + ":string_from_raw_cotton_fiber_spinning");
		this.addSpinningRecipe(output, ItemTags.WOOL, new ItemStack(Items.STRING, 4), YetAnotherTechMod.MODID + ":string_from_wool_spinning");
		/*TODO, perhaps make this better proportional to the input cost, or perhaps make a cheap way to make carpets so we can pretend giving two string is appropriate and not just because 8 doesn't divide without remainder into 3*/
		this.addSpinningRecipe(output, ItemTags.WOOL_CARPETS, new ItemStack(Items.STRING, 2), YetAnotherTechMod.MODID + ":string_from_wool_carpet_spinning");
	} // end addSpinningRecipes()

	
	
	
	
	
	
	
	protected void addNineToOne(@NotNull RecipeOutput output, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(ingredient), 9)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addNineToOne()
	
	protected void addNineToOne(@NotNull RecipeOutput output, ItemLike ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
		.requires(Ingredient.of(ingredient), 9)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addNineToOne()

	protected void addOneToNine(@NotNull RecipeOutput output, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9)
		.requires(ingredient)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addCompressed()
	
	protected void addOneToNine(@NotNull RecipeOutput output, ItemLike ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 9)
		.requires(ingredient)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addCompressed()

	protected void addOneToFour(@NotNull RecipeOutput output, TagKey<Item> ingredient, ItemLike result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 4)
		.requires(ingredient)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addCompressed()
	
	protected void addOneToX(@NotNull RecipeOutput output, ItemLike ingredient, ItemLike result, int count, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, count)
		.requires(ingredient)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addCompressed()
	
	protected void addTwoByTwoToX(@NotNull RecipeOutput output, ItemLike ingredient, ItemLike result, int count, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, count)
		.pattern("aa")
		.pattern("aa")
		.define('a', ingredient)
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addTwoByTwoToOne()
	
	protected void addXToX(@NotNull RecipeOutput output, ItemLike ingredient, ItemLike result, int ingredientCount, int resultCount, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, resultCount)
		.requires(ingredient, ingredientCount)
		.unlockedBy("has_item", has(ingredient))
		.save(output, key);
	} // end addTwoByTwoToOne()
	/*
	protected void addLargeHeatSink(@NotNull RecipeOutput output, String key,  ItemLike result, TagKey<Item> finIngredient, TagKey<Item> baseIngredient) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
		.pattern("fff")
		.pattern("fbf")
		.pattern("bbb")
		.define('f', finIngredient)
		.define('b', baseIngredient)
		.unlockedBy("criteria", has(finIngredient).build(), ItemPredicate.Builder.item().of(baseIngredient).build()))
		.save(output, key);
	} // end addLargeHeatSink
	*/
	protected void addFancyPlanks(@NotNull RecipeOutput output, ItemLike regularPlank, ItemLike slab, ItemLike result, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result, 3)
		.pattern("bs")
		.pattern("sb")
		.define('b', regularPlank)
		.define('s', slab)
		.unlockedBy("criteria", has(regularPlank))
		.unlockedBy("criteria", has(slab))
		.save(output, key);
	}
	
	protected void addStairs(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.stairBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);		
	} // end addStairs()
	
	protected void addSlabs(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.slabBuilder(RecipeCategory.BUILDING_BLOCKS, result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);	
	} // end addSlabs()
	
	protected void addFence(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.fenceBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);			
	} // endFence()
	
	protected void addFenceGate(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		fenceGateBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);			
	} // endFence()
	
	protected void addDoor(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.doorBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);			
	} // addDoor()
	
	protected void addTrapdoor(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.trapdoorBuilder(result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);			
	} // addTrapdoor()
	
	protected void addPressurePlate(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.pressurePlateBuilder(RecipeCategory.REDSTONE, result, Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);			
	} // addPressurePlate()
	
	protected void addButton(@NotNull RecipeOutput output, TagKey<Item> ingredient, @NotNull Item result, String key)
	{
		RecipeProvider.buttonBuilder(result,Ingredient.of(ingredient))
		.unlockedBy("criteria", has(ingredient))
		.save(output, key);
	} // end addButton()
	
	protected void addSign(@NotNull RecipeOutput output, TagKey<Item> planks, Item sign, String key) 
	{
		RecipeProvider.signBuilder(sign, Ingredient.of(planks))
		.unlockedBy("has_planks", has(planks))
		.save(output);
	} // end addSign()
	
	protected void addHangingSign(@NotNull RecipeOutput output, Item strippedLog, Item sign, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6)
		.group("hanging_sign")		
		.pattern("C C")
		.pattern("PPP")
		.pattern("PPP")
		.define('C', Items.CHAIN)
		.define('P', strippedLog)
		.unlockedBy("has_stripped_logs", has(strippedLog))
		.save(output);
	} // end addHangingSign()
	
	protected void addBoat(@NotNull RecipeOutput output, TagKey<Item> planks, Item boat, String key) 
	{
		ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat)
		.group("boat")
		.pattern("   ")
		.pattern("P P")
		.pattern("PPP")
		.define('P', planks)
		.unlockedBy("in_water", CriteriaTriggers.ENTER_BLOCK.createCriterion(new EnterBlockTrigger.TriggerInstance(Optional.empty(), Blocks.WATER, Optional.empty())))
		.save(output);
	} // end addBoat()
	
	

	protected void addSmelting(@NotNull RecipeOutput output, ItemLike ingredient, ItemLike result, float experience, int time, String key) 
	{
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, time)
		.unlockedBy("has_item", has(ingredient))
		.save(output, key);
	} // end addSmelting
	
	protected void addSmelting(@NotNull RecipeOutput output, TagKey<Item> ingredient, ItemLike result, float experience, int time, String key) 
	{
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, time)
		.unlockedBy("has_item", has(ingredient))
		.save(output, key);
	} // end addSmelting
	
	protected void addBlasting(@NotNull RecipeOutput output, TagKey<Item> ingredient, ItemLike result, float experience, int time, String key) 
	{
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, time)
		.unlockedBy("has_item", has(ingredient))
		.save(output, key);
	} // end addSmelting
	
	
	
	protected void addExtraction(@NotNull RecipeOutput output, ItemLike ingredient, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractingRecipeBuilder()
		.input(new ItemStackIngredient(ingredient.asItem()))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(ingredient))
		.save(output, key);
	} // end addExtraction()
	
	protected void addExtraction(@NotNull RecipeOutput output, ItemLike ingredient, ItemStack remainder, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractingRecipeBuilder()
		.input(new ItemStackIngredient(ingredient.asItem()))
		.inputRemainder(remainder)
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(ingredient))
		.save(output, key);
	} // end addExtraction()
	
	protected void addExtraction(@NotNull RecipeOutput output, TagKey<Item> ingredient, ItemStack remainder, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new ExtractingRecipeBuilder()
		.input(new ItemTagIngredient(ingredient))
		.inputRemainder(remainder)
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(ingredient))
		.save(output, key);
	} // end addExtraction()
	
	
	
	protected void addBoilingRecipe(@NotNull RecipeOutput output, FluidStack input, FluidStack result, int temperature, int timeInTicks, String key) 
	{
		new BoilingRecipeBuilder()
		.input(new FluidStackIngredient(input))
		.result(result)
		.temperature(temperature)
		.timeInTicks(timeInTicks)
		// TODO, make use tag containing all the boilers
		.unlockedBy("has_device", has(YATMItems.STEEL_BOILER.get()))
		.save(output, key);
	} // end addBoilingRecipe()
	
	
	
	protected void addCrystallizationRecipe(@NotNull RecipeOutput output, FluidStack input, TagKey<Item> seed, ItemStack result, boolean consumeSeed, int currentPerTick, int timeInTicks, String key) 
	{
		new CrystallizingRecipeBuilder()
		.input(new FluidStackIngredient(input))
		.seed(new ItemTagIngredient(seed))
		.result(result)
		.consumeSeed(consumeSeed)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(seed))
		.save(output, key);
	} // end addCrystallizationRecipe()
	
	protected void addCrystallizationRecipe(@NotNull RecipeOutput output, @NotNull TagKey<Fluid> input, @NotNegative int inputAmount, @NotNull TagKey<Item> seed, @NotNull ItemStack result, boolean consumeSeed, @NotNegative int currentPerTick, @NotNegative int timeInTicks, @NotNull String key) 
	{
		new CrystallizingRecipeBuilder()
		.input(new FluidTagIngredient(input, inputAmount))
		.seed(new ItemTagIngredient(seed))
		.result(result)
		.consumeSeed(consumeSeed)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(seed))
		.save(output, key);
	} // end addCrystallizationRecipe()
	
	protected void addCrystallizationRecipe(@NotNull RecipeOutput output, FluidStack input, Item seed, ItemStack result, boolean consumeSeed, int currentPerTick, int timeInTicks, String key) 
	{
		new CrystallizingRecipeBuilder()
		.input(new FluidStackIngredient(input))
		.seed(new ItemStackIngredient(seed))
		.result(result)
		.consumeSeed(consumeSeed)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(seed))
		.save(output, key);
	} // end addCrystallizationRecipe()
	
	
	
	protected void addGrindingRecipe(@NotNull RecipeOutput output, Item input, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemStackIngredient(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindingRecipe()
	
	protected void addGrindingRecipe(@NotNull RecipeOutput output, Item input, int inputCount, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemStackIngredient(new ItemStack(input, inputCount)))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindingRecipe()
	
	protected void addGrindingRecipe(@NotNull RecipeOutput output, TagKey<Item> input, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemTagIngredient(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindingRecipe()
	
	protected void addGrindingRecipe(@NotNull RecipeOutput output, TagKey<Item> input, int inputCount, ItemStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new GrindingRecipeBuilder()
		.input(new ItemTagIngredient(input, inputCount))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindingRecipe()
	
	
	
	protected void addBiolingRecipe(@NotNull RecipeOutput output, Item input, FluidStack result, int currentPerTick, int timeInTicks, String key) 
	{
		new BioreactionRecipeBuilder()
		.input(new ItemStackIngredient(input))
		.result(result)
		.currentPerTick(currentPerTick)
		.timeInTicks(timeInTicks)
		//.unlockedBy("has_device", has(YATMItems.STEEL_BIOLER_ITEM.get()).build()))
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindRecipe()
	
	
	
	protected void addSpinningRecipe(@NotNull RecipeOutput output, Item input, ItemStack result, String key) 
	{
		new SpinningRecipeBuilder()
		.input(new ItemStackIngredient(input))
		.result(result)
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindRecipe()
	
	protected void addSpinningRecipe(@NotNull RecipeOutput output, TagKey<Item> input, ItemStack result, String key) 
	{
		new SpinningRecipeBuilder()
		.input(new ItemTagIngredient(input))
		.result(result)
		.unlockedBy("has_ingredient", has(input))
		.save(output, key);
	} // end addGrindRecipe()
	
	
	protected void addMeltingRecipe(@NotNull RecipeOutput output, @NotNull IIngredient<ItemStack> input, @NotNull FluidStack result, @NotNegative int temperature, @NotNegative int ticks, Criterion<?> unlockedBy, String key)
	{
		new MeltingRecipeBuilder()
		.result(result)
		.input(input)
		.temperature(Contract.notNegative(temperature))
		.timeInTicks(Contract.notNegative(ticks))
		.unlockedBy("has_ingredient", unlockedBy)
		.save(output, key);
	} // end addMeltingRecipe()
	
	
	
	protected void addPartExchange(@NotNull RecipeOutput output, TagKey<Item> tool, ItemStack part, ItemStack result, String group, String key) 
	{
		new ToolPartExchangeRecipeBuilder()
		.category(CraftingBookCategory.EQUIPMENT)
		.tool(new ItemTagIngredient(tool))
		.part(new ItemStackIngredient(part))
		.result(result)
		.group(group)
		.unlockedBy("has_ingredient", has(part.getItem()))
		.save(output, key);
	} // end addPartExchange()
	
} // end class