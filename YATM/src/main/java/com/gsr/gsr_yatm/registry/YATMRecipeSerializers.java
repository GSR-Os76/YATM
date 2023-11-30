package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.bioreacting.BioreactingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.bioreacting.BioreactingRecipe;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipe;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange.ToolPartExchangeRecipe;
import com.gsr.gsr_yatm.recipe.crafting.tool_part_exchange.ToolPartExchangeRecipeSerializer;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipe;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipe;
import com.gsr.gsr_yatm.recipe.extracting.ExtractingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.injecting.InjectingRecipe;
import com.gsr.gsr_yatm.recipe.injecting.InjectingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipe;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipeSerializer;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipe;
import com.gsr.gsr_yatm.recipe.spinning.SpinningRecipeSerializer;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<RecipeSerializer<BioreactingRecipe>> BIOREACTING = RECIPE_SERIALIZERS.register("bioling", () -> new BioreactingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<BoilingRecipe>> BOILING = RECIPE_SERIALIZERS.register("boiling", () -> new BoilingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<CrystallizingRecipe>> CRYSTALLIZING = RECIPE_SERIALIZERS.register("crystallizing", () -> new CrystallizingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<ExtractingRecipe>> EXTRACTING = RECIPE_SERIALIZERS.register("extracting", () -> new ExtractingRecipeSerializer());
//	Spublic static final RegistryObject<RecipeSerializer<ExtrudingRecipe>> EXTRUDING = RECIPE_SERIALIZERS.register("extruding", () -> new ExtrudingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<GrindingRecipe>> GRINDING = RECIPE_SERIALIZERS.register("grinding", () -> new GrindingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<InjectingRecipe>> INJECTING = RECIPE_SERIALIZERS.register("fluid_injecting", () -> new InjectingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<MeltingRecipe>> MELTING = RECIPE_SERIALIZERS.register("melting", () -> new MeltingRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<SpinningRecipe>> SPINNING = RECIPE_SERIALIZERS.register("spinning", () -> new SpinningRecipeSerializer());
	public static final RegistryObject<RecipeSerializer<ToolPartExchangeRecipe>> TOOL_PART_EXCHANGE = RECIPE_SERIALIZERS.register("tool_part_exchange", () -> new ToolPartExchangeRecipeSerializer());
	
} // end class