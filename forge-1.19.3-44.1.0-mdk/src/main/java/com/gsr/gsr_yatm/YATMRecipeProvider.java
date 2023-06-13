package com.gsr.gsr_yatm;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class YATMRecipeProvider extends RecipeProvider
{

	public YATMRecipeProvider(PackOutput output)
	{
		super(output);
	} // end constructor
	
	

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> writer)
	{
		this.addNineToOne(writer, YATMItemTags.FORGE_COPPER_NUGGETS_KEY, Items.COPPER_INGOT, YetAnotherTechMod.MODID +":copper_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, Items.NETHERITE_INGOT, YetAnotherTechMod.MODID +":netherite_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_SILVER_NUGGETS_KEY, YATMItems.SILVER_INGOT.get(), YetAnotherTechMod.MODID +":silver_ingot_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_COPPER_INGOTS_KEY, YATMItems.COPPER_NUGGET.get(),  YetAnotherTechMod.MODID +":copper_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_NETHERITE_INGOTS_KEY, YATMItems.NETHERITE_NUGGET.get(),  YetAnotherTechMod.MODID +":netherite_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_SILVER_INGOTS_KEY, YATMItems.SILVER_NUGGET.get(),  YetAnotherTechMod.MODID +":silver_nuggets_from_shapeless_crafting");
		
		this.addLargeHeatSink(writer, "gsr_yatm:", YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), YATMItemTags.FORGE_COPPER_INGOTS_KEY, YATMItemTags.UNOXIDIXED_COPPER_BLOCKS_KEY);
		
		this.addTwoByTwoToX(writer, YATMItems.RUBBER_LOG_ITEM.get(), YATMItems.RUBBER_WOOD_ITEM.get(), 3, YetAnotherTechMod.MODID +":rubber_wood_from_shaped_crafting");
		this.addTwoByTwoToX(writer, YATMItems.STRIPPED_RUBBER_LOG_ITEM.get(), YATMItems.STRIPPED_RUBBER_WOOD_ITEM.get(), 3, YetAnotherTechMod.MODID +":stripped_rubber_wood_from_shaped_crafting");
		this.addOneToFour(writer, YATMItemTags.RUBBER_TREE_LOGS_KEY, YATMItems.RUBBER_PLANKS_ITEM.get(), YetAnotherTechMod.MODID +":rubber_planks_from_shapeless_crafting");
		this.addFancyPlanks(writer, YATMItems.RUBBER_PLANKS_ITEM.get(), YATMItems.RUBBER_SLAB_ITEM.get(), YATMItems.FANCY_RUBBER_PLANKS_ITEM.get(), YetAnotherTechMod.MODID +":fancy_rubber_planks_from_shaped_crafting");
		this.addStairs(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_STAIRS_ITEM.get(), YetAnotherTechMod.MODID +":rubber_stairs_from_shaped_crafting");
		this.addSlabs(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_SLAB_ITEM.get(), YetAnotherTechMod.MODID +":rubber_slab_from_shaped_crafting");
		this.addFence(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_FENCE_ITEM.get(), YetAnotherTechMod.MODID +":rubber_fence_from_shaped_crafting");
		this.addFenceGate(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_FENCE_GATE_ITEM.get(), YetAnotherTechMod.MODID +":rubber_fence_gate_from_shaped_crafting");
		this.addDoor(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_DOOR_ITEM.get(), YetAnotherTechMod.MODID +":rubber_door_from_shaped_crafting");
		this.addTrapdoor(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_TRAPDOOR_ITEM.get(), YetAnotherTechMod.MODID +":rubber_trapdoor_from_shaped_crafting");
		this.addPressurePlate(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_PRESSURE_PLATE_ITEM.get(), YetAnotherTechMod.MODID +":rubber_pressure_plate_from_shaped_crafting");
		this.addButton(writer, YATMItemTags.RUBBER_TREE_PLANKS_KEY, YATMItems.RUBBER_BUTTON_ITEM.get(), YetAnotherTechMod.MODID +":rubber_button_from_shaped_crafting");
		
	} // end buildRecipes()
	
	



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
} // end class