package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipe;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipe;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipe;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipe;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
import com.gsr.gsr_yatm.recipe.injecting.InjectingRecipe;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipe;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipe;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipe;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeTypes
{
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeType<SpinningRecipe>> SPINNING = RECIPE_TYPES.register("spinning", () -> new RecipeType<SpinningRecipe>(){});
	
	public static final RegistryObject<RecipeType<BiolingRecipe>> BIOLING = RECIPE_TYPES.register("bioling", () -> new RecipeType<BiolingRecipe>(){});
	public static final RegistryObject<RecipeType<BoilingRecipe>> BOILING = RECIPE_TYPES.register("boiling", () -> new RecipeType<BoilingRecipe>(){});
	public static final RegistryObject<RecipeType<CrystallizingRecipe>> CRYSTALLIZING = RECIPE_TYPES.register("crystallizating", () -> new RecipeType<CrystallizingRecipe>(){});
	public static final RegistryObject<RecipeType<ExtractingRecipe>> EXTRACTING = RECIPE_TYPES.register("extracting", () -> new RecipeType<ExtractingRecipe>(){});
//	public static final RegistryObject<RecipeType<ExtrudingRecipe>> EXTRUDING = RECIPE_TYPES.register("extruding", () -> new RecipeType<ExtrudingRecipe>(){});
	public static final RegistryObject<RecipeType<GrindingRecipe>> GRINDING = RECIPE_TYPES.register("grinding", () -> new RecipeType<GrindingRecipe>(){});
	public static final RegistryObject<RecipeType<InjectingRecipe>> INJECTING = RECIPE_TYPES.register("fluid_injecting", () -> new RecipeType<InjectingRecipe>(){});
	public static final RegistryObject<RecipeType<MeltingRecipe>> MELTING = RECIPE_TYPES.register("melting_injecting", () -> new RecipeType<MeltingRecipe>(){});

	public static final RegistryObject<RecipeType<WrappedSmeltingRecipe>> SMELTING_PLUS = RECIPE_TYPES.register("smelting_plus", () -> new RecipeType<WrappedSmeltingRecipe>(){});

} // end class