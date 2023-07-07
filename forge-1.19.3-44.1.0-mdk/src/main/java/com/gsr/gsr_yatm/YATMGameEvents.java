package com.gsr.gsr_yatm;

import java.util.Collection;

import com.gsr.gsr_yatm.fluid.item.GlassBottleItemStack;
import com.gsr.gsr_yatm.registry.YATMMobEffects;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class YATMGameEvents
{
	public static void register(IEventBus eventBus) 
	{
		eventBus.addListener(YATMGameEvents::onEntityDamaged);
		eventBus.addListener(YATMGameEvents::recipesUpdated);
		eventBus.addGenericListener(ItemStack.class, YATMGameEvents::attachItemStackCapabilities);
	} // end register()
	
	
	
	
	
	private static void onEntityDamaged(LivingDamageEvent event) 
	{
		if(event.getSource().is(DamageTypes.WITHER)) 
		{
			LivingEntity entity = event.getEntity();
			Collection<MobEffectInstance> effects = entity.getActiveEffects();
			for(MobEffectInstance effect : effects) 
			{
				if(effect.getEffect() == YATMMobEffects.SOUL_AFFLICTION.get()) 
				{
					entity.removeEffect(YATMMobEffects.SOUL_AFFLICTION.get());
					entity.addEffect(new MobEffectInstance(
							MobEffects.WITHER, 
							effect.getDuration(), 
							effect.getAmplifier(), 
							effect.isAmbient(), 
							effect.isVisible(),
							effect.showIcon()));
					break;
				}
			}
		}
	} // end onEntityDamaged()

	private static void recipesUpdated(RecipesUpdatedEvent event) 
	{
		RecipeUtilities.recipesUpdated();
	} // end recipesUpdated()
	
	private static void attachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) 
	{
		if(event.getObject().getItem() == Items.GLASS_BOTTLE) 
		{
			event.addCapability(new ResourceLocation(YetAnotherTechMod.MODID, "glass_bottle_fluid_handler"), new GlassBottleItemStack(event.getObject()));
		}
	} // end attachItemStackCapabilities()
} // end class