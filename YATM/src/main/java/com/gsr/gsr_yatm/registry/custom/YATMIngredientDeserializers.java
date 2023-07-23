package com.gsr.gsr_yatm.registry.custom;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.recipe.ingredient.ItemStackIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.ItemTagIngredientDeserializer;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMIngredientDeserializers
{
	public static final DeferredRegister<IIngredientDeserializer<?>> INGREDIENT_DESERIALIZERS = DeferredRegister.create(new ResourceLocation(YetAnotherTechMod.MODID, "ingredient_deserializers"), YetAnotherTechMod.MODID);
	
		
	
	public static final RegistryObject<ItemStackIngredientDeserializer> ITEM_STACK_INGREDIENT = INGREDIENT_DESERIALIZERS.register("item_stack_ingredient", () -> new ItemStackIngredientDeserializer());	
	public static final RegistryObject<ItemTagIngredientDeserializer> ITEM_TAG_INGREDIENT = INGREDIENT_DESERIALIZERS.register("item_tag_ingredient", () -> new ItemTagIngredientDeserializer());	
	
} // end class