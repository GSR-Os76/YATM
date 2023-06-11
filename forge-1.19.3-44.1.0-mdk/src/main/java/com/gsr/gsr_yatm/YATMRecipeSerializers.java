package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.recipe.ExtrusionRecipe;
import com.gsr.gsr_yatm.recipe.ExtrusionRecipeSerializer;
import com.gsr.gsr_yatm.recipe.FluidInjectionRecipe;
import com.gsr.gsr_yatm.recipe.FluidInjectionRecipeSerializer;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeSerializer<FluidInjectionRecipe>> FLUID_INJECTION_SERIALIZER = RECIPE_SERIALIZERS.register("fluid_injection", () -> new FluidInjectionRecipeSerializer());//RECIPE_TYPES.register("fluid_injection", () -> RecipeType.register("fluid_injection"));
	public static final RegistryObject<RecipeSerializer<ExtrusionRecipe>> EXTRUSION_SERIALIZER = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrusionRecipeSerializer());

} // end class