package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.*;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipe;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipe;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizationRecipe;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizationRecipeSerializer;
import com.gsr.gsr_yatm.recipe.extracting.ExtractionRecipe;
import com.gsr.gsr_yatm.recipe.extracting.ExtractionRecipeSerializer;
import com.gsr.gsr_yatm.recipe.extruding.ExtrusionRecipe;
import com.gsr.gsr_yatm.recipe.extruding.ExtrusionRecipeSerializer;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipeSerializer;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeSerializer<BiolingRecipe>> BIOLING = RECIPE_SERIALIZERS.register("bioling", () -> new BiolingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<BoilingRecipe>> BOILING = RECIPE_SERIALIZERS.register("boiling", () -> new BoilingRecipeSerializer());
	// TODO, for consistency make this "crystallizing"
	public static final RegistryObject<RecipeSerializer<CrystallizationRecipe>> CRYSTALLIZATION = RECIPE_SERIALIZERS.register("crystallization", () -> new CrystallizationRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<ExtractionRecipe>> EXTRACTION = RECIPE_SERIALIZERS.register("extracting", () -> new ExtractionRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<ExtrusionRecipe>> EXTRUSION = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrusionRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<GrindingRecipe>> GRINDING = RECIPE_SERIALIZERS.register("grinding", () -> new GrindingRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<FluidInjectionRecipe>> FLUID_INJECTION = RECIPE_SERIALIZERS.register("fluid_injection", () -> new FluidInjectionRecipeSerializer());
	
} // end class