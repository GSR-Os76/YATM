package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.registry.YATMMobEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class YATMFoods
{
	public static final FoodProperties BIOFLUID = new FoodProperties.Builder().nutrition(1).saturationMod(.1f).effect(() -> new MobEffectInstance(MobEffects.POISON, 50, 0), .6f).build();
	public static final FoodProperties CHORUS = new FoodProperties.Builder().nutrition(1).saturationMod(.1f).effect(() -> new MobEffectInstance(YATMMobEffects.CHORUS_INSTANT_TELEPORTATION.get(), 1, 16), 1f).build();
	public static final FoodProperties CHORUS_BIOFLUID = new FoodProperties.Builder().nutrition(1).saturationMod(.1f).effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 0), .6f).effect(() -> new MobEffectInstance(YATMMobEffects.CHORUS_INSTANT_TELEPORTATION.get(), 1, 8), 1f).build();
	public static final FoodProperties ENDER = new FoodProperties.Builder().effect(() -> new MobEffectInstance(YATMMobEffects.ENDER_INSTANT_TELEPORTATION.get(), 1, 32), 1f).build();
	public static final FoodProperties ESSENCE_OF_DECAY = new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.WITHER, 2400, 4), 1).build();
	public static final FoodProperties ESSENCE_OF_SOULS = new FoodProperties.Builder().effect(() -> new MobEffectInstance(YATMMobEffects.SOUL_AFFLICTION.get(), 2400, 4), 1).build();	
	// latex
	public static final FoodProperties SOUL_SAP = new FoodProperties.Builder().nutrition(1).saturationMod(1f).effect(() -> new MobEffectInstance(YATMMobEffects.SOUL_AFFLICTION.get(), 300, 0), 1).build();
	public static final FoodProperties SOUL_SYRUP = new FoodProperties.Builder().nutrition(3).saturationMod(1.2f).effect(() -> new MobEffectInstance(YATMMobEffects.SOUL_AFFLICTION.get(), 600, 1), 1).build();

} // end class