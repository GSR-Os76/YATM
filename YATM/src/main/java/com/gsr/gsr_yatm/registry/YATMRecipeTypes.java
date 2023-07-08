package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.FluidInjectionRecipe;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipe;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipe;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizationRecipe;
import com.gsr.gsr_yatm.recipe.extracting.ExtractionRecipe;
import com.gsr.gsr_yatm.recipe.extruding.ExtrusionRecipe;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipe;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeTypes
{
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeType<BiolingRecipe>> BIOLING = RECIPE_TYPES.register("bioling", () -> new RecipeType<BiolingRecipe>(){});
	public static final RegistryObject<RecipeType<BoilingRecipe>> BOILING = RECIPE_TYPES.register("boiling", () -> new RecipeType<BoilingRecipe>(){});
	// TODO, for consistency make this "crystallizing"
	public static final RegistryObject<RecipeType<CrystallizationRecipe>> CRYSTALLIZATION = RECIPE_TYPES.register("crystallization", () -> new RecipeType<CrystallizationRecipe>(){});
	public static final RegistryObject<RecipeType<ExtractionRecipe>> EXTRACTION = RECIPE_TYPES.register("extracting", () -> new RecipeType<ExtractionRecipe>(){});
	public static final RegistryObject<RecipeType<ExtrusionRecipe>> EXTRUSION = RECIPE_TYPES.register("extruding", () -> new RecipeType<ExtrusionRecipe>(){});
	public static final RegistryObject<RecipeType<GrindingRecipe>> GRINDING = RECIPE_TYPES.register("grinding", () -> new RecipeType<GrindingRecipe>(){});
	
	public static final RegistryObject<RecipeType<FluidInjectionRecipe>> FLUID_INJECTION = RECIPE_TYPES.register("fluid_injecting", () -> new RecipeType<FluidInjectionRecipe>(){});

	public static final RegistryObject<RecipeType<WrappedSmeltingRecipe>> SMELTING_PLUS = RECIPE_TYPES.register("smelting_plus", () -> new RecipeType<WrappedSmeltingRecipe>(){});

} // end class