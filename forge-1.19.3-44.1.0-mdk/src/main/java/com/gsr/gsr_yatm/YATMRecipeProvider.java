package com.gsr.gsr_yatm;

import java.util.function.Consumer;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
		//ShapedRecipeBuilder builder = 
//				ShapedRecipeBuilder.shaped(RecipeCategory.MISC, YATMItems.RUBBER_MERISTEM_ITEM.get())
//				.pattern("XX ")
//				.pattern("X  ")
//				.pattern("  X")
//				.define('X', YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get())
//				.unlockedBy("criteria", inventoryTrigger(ItemPredicate.Builder.item().of(YATMItems.SOUL_AFFLICTED_RUBBER_MERISTEM_ITEM.get()).build()))
//				.save(recipeConsumer);
		this.addNineToOne(writer, YATMItemTags.FORGE_COPPER_NUGGETS_KEY, new ItemStack(Items.COPPER_INGOT), YetAnotherTechMod.MODID +":copper_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_NETHERITE_NUGGETS_KEY, new ItemStack(Items.NETHERITE_INGOT), YetAnotherTechMod.MODID +":netherite_ingot_from_shapeless_crafting");
		this.addNineToOne(writer, YATMItemTags.FORGE_SILVER_NUGGETS_KEY, new ItemStack(YATMItems.SILVER_INGOT.get()), YetAnotherTechMod.MODID +":silver_ingot_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_COPPER_INGOTS_KEY, YATMItems.COPPER_NUGGET.get(),  YetAnotherTechMod.MODID +":copper_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_NETHERITE_INGOTS_KEY, YATMItems.NETHERITE_NUGGET.get(),  YetAnotherTechMod.MODID +":netherite_nuggets_from_shapeless_crafting");
		this.addOneToNine(writer, YATMItemTags.FORGE_SILVER_INGOTS_KEY, YATMItems.SILVER_NUGGET.get(),  YetAnotherTechMod.MODID +":silver_nuggets_from_shapeless_crafting");
		
		this.addLargeHeatSink(writer, "gsr_yatm:", YATMItems.LARGE_COPPER_HEAT_SINK_ITEM.get(), YATMItemTags.FORGE_COPPER_INGOTS_KEY, YATMItemTags.UNOXIDIXED_COPPER_BLOCKS_KEY);
		
	} // end buildRecipes()
	
	private void addNineToOne(Consumer<FinishedRecipe> writer, TagKey<Item> ingredient, ItemStack result, String key) 
	{
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result.getItem(), result.getCount())
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
	
} // end class