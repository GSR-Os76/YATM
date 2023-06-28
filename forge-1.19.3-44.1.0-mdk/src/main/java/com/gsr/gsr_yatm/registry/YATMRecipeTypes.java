package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.BoilingRecipe;
import com.gsr.gsr_yatm.recipe.CrystallizationRecipe;
import com.gsr.gsr_yatm.recipe.ExtractionRecipe;
import com.gsr.gsr_yatm.recipe.ExtrusionRecipe;
import com.gsr.gsr_yatm.recipe.FluidInjectionRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeTypes
{
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeType<BoilingRecipe>> BOILING_RECIPE_TYPE = RECIPE_TYPES.register("boiling", () -> new RecipeType<BoilingRecipe>(){});
	
	public static final RegistryObject<RecipeType<CrystallizationRecipe>> CRYSTALLIZATION_RECIPE_TYPE = RECIPE_TYPES.register("crystallization", () -> new RecipeType<CrystallizationRecipe>(){});
	
	public static final RegistryObject<RecipeType<ExtractionRecipe>> EXTRACTION_RECIPE_TYPE = RECIPE_TYPES.register("extracting", () -> new RecipeType<ExtractionRecipe>(){});
	
	public static final RegistryObject<RecipeType<ExtrusionRecipe>> EXTRUSION_RECIPE_TYPE = RECIPE_TYPES.register("extruding", () -> new RecipeType<ExtrusionRecipe>(){});
	
	public static final RegistryObject<RecipeType<FluidInjectionRecipe>> FLUID_INJECTION_RECIPE_TYPE = RECIPE_TYPES.register("fluid_injection", () -> new RecipeType<FluidInjectionRecipe>(){});

} // end class