package com.gsr.gsr_yatm.registry.custom;

import java.util.function.Supplier;

import com.gsr.gsr_yatm.recipe.ingredient.YATMIngredientDeserializer;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class YATMRegistries
{
	public static final Supplier<IForgeRegistry<YATMIngredientDeserializer<?>>> INGREDIENT_DESERIALIZERS 
	= YATMIngredientDeserializers.INGREDIENT_DESERIALIZERS.makeRegistry(() -> new RegistryBuilder<YATMIngredientDeserializer<?>>());

	
	
	public static void classInitializer() 
	{
		
	} // end classInitializer()
	
} // end class