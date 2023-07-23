package com.gsr.gsr_yatm.registry.custom;

import java.util.function.Supplier;

import com.gsr.gsr_yatm.armor.IArmorSet;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class YATMRegistries
{
	private static IEventBus GAME_EVENT_BUS;
	
	public static final Supplier<IForgeRegistry<IArmorSet>> ARMOR_SETS = YATMArmorSets.ARMOR_SETS.makeRegistry(() -> new RegistryBuilder<IArmorSet>().add((owner, stage, id, key,obj, oldObj) -> obj.subscribeEffects(GAME_EVENT_BUS)));
	public static final Supplier<IForgeRegistry<IIngredientDeserializer<?>>> INGREDIENT_DESERIALIZERS = YATMIngredientDeserializers.INGREDIENT_DESERIALIZERS.makeRegistry(() -> new RegistryBuilder<IIngredientDeserializer<?>>());
	
	
	
	public static void classInitializer() 
	{
		
	} // end classInitializer()

	public static void register(IEventBus eventBus)
	{
		YATMRegistries.GAME_EVENT_BUS = eventBus;
	} // end register()
	
} // end class