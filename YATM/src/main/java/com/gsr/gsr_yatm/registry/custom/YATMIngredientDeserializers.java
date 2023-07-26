package com.gsr.gsr_yatm.registry.custom;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.ingredient.ItemStackIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.ItemTagIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.NBTFluidStackIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.NBTFluidTagIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.NBTItemStackIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.NBTItemTagIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.FluidStackIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.FluidTagIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMIngredientDeserializers
{
	public static final DeferredRegister<IIngredientDeserializer<?>> INGREDIENT_DESERIALIZERS = DeferredRegister.create(new ResourceLocation(YetAnotherTechMod.MODID, "ingredient_deserializers"), YetAnotherTechMod.MODID);
	
		
	
	public static final RegistryObject<FluidStackIngredientDeserializer> FLUID_STACK_INGREDIENT = INGREDIENT_DESERIALIZERS.register("fluid_stack_ingredient", () -> new FluidStackIngredientDeserializer());	
	public static final RegistryObject<FluidTagIngredientDeserializer> FLUID_TAG_INGREDIENT = INGREDIENT_DESERIALIZERS.register("fluid_tag_ingredient", () -> new FluidTagIngredientDeserializer());	
	public static final RegistryObject<ItemStackIngredientDeserializer> ITEM_STACK_INGREDIENT = INGREDIENT_DESERIALIZERS.register("item_stack_ingredient", () -> new ItemStackIngredientDeserializer());	
	public static final RegistryObject<ItemTagIngredientDeserializer> ITEM_TAG_INGREDIENT = INGREDIENT_DESERIALIZERS.register("item_tag_ingredient", () -> new ItemTagIngredientDeserializer());

	public static final RegistryObject<NBTFluidStackIngredientDeserializer> NBT_FLUID_STACK_INGREDIENT = INGREDIENT_DESERIALIZERS.register("nbt_fluid_stack_ingredient", () -> new NBTFluidStackIngredientDeserializer());
	public static final RegistryObject<NBTFluidTagIngredientDeserializer> NBT_FLUID_TAG_INGREDIENT = INGREDIENT_DESERIALIZERS.register("nbt_fluid_tag_ingredient", () -> new NBTFluidTagIngredientDeserializer());
	public static final RegistryObject<NBTItemStackIngredientDeserializer> NBT_ITEM_STACK_INGREDIENT = INGREDIENT_DESERIALIZERS.register("nbt_item_stack_ingredient", () -> new NBTItemStackIngredientDeserializer());
	public static final RegistryObject<NBTItemTagIngredientDeserializer> NBT_ITEM_TAG_INGREDIENT = INGREDIENT_DESERIALIZERS.register("nbt_item_tag_ingredient", () -> new NBTItemTagIngredientDeserializer());
	
} // end class