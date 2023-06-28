package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.*;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, YetAnotherTechMod.MODID);
	
	public static final RegistryObject<RecipeSerializer<BoilingRecipe>> BOILING_SERIALIZER = RECIPE_SERIALIZERS.register("boiling", () -> new BoilingRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<CrystallizationRecipe>> CRYSTALLIZATION_SERIALIZER = RECIPE_SERIALIZERS.register("crystallization", () -> new CrystallizationRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<ExtractionRecipe>> EXTRACTION_SERIALIZER = RECIPE_SERIALIZERS.register("extracting", () -> new ExtractionRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<ExtrusionRecipe>> EXTRUSION_SERIALIZER = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrusionRecipeSerializer());
	
	public static final RegistryObject<RecipeSerializer<FluidInjectionRecipe>> FLUID_INJECTION_SERIALIZER = RECIPE_SERIALIZERS.register("fluid_injection", () -> new FluidInjectionRecipeSerializer());
	
} // end class