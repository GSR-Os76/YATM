package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.*;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipe;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipe;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipe;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipe;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.extruding.ExtrudingRecipe;
import com.gsr.gsr_yatm.recipe.extruding.ExtrudingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipe;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipeSerializer;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeSerializer<SpinningRecipe>> SPINNING = RECIPE_SERIALIZERS.register("spinning", () -> new SpinningRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<BiolingRecipe>> BIOLING = RECIPE_SERIALIZERS.register("bioling", () -> new BiolingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<BoilingRecipe>> BOILING = RECIPE_SERIALIZERS.register("boiling", () -> new BoilingRecipeSerializer());
	// TODO, for consistency make this "crystallizing"
	public static final RegistryObject<RecipeSerializer<CrystallizingRecipe>> CRYSTALLIZATION = RECIPE_SERIALIZERS.register("crystallization", () -> new CrystallizingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<ExtractingRecipe>> EXTRACTION = RECIPE_SERIALIZERS.register("extracting", () -> new ExtractingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<ExtrudingRecipe>> EXTRUSION = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<GrindingRecipe>> GRINDING = RECIPE_SERIALIZERS.register("grinding", () -> new GrindingRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<FluidInjectionRecipe>> FLUID_INJECTION = RECIPE_SERIALIZERS.register("fluid_injection", () -> new FluidInjectionRecipeSerializer());
	
} // end class