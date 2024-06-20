package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.utilities.YATMMobEffectHolders;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class YATMFoods
{
	
	public static final FoodProperties PERSIMMON = new FoodProperties.Builder().nutrition(3).saturationModifier(3f).build();
	public static final FoodProperties TEAR_LEAF = new FoodProperties.Builder().nutrition(1).saturationModifier(.1f).build();
	
	
	public static final FoodProperties BIOFLUID = new FoodProperties.Builder().nutrition(1).saturationModifier(.1f).effect(new MobEffectInstance(MobEffects.POISON, 50, 0), .6f).build();
	public static final FoodProperties CHORUS = new FoodProperties.Builder().nutrition(1).saturationModifier(.1f).effect(new MobEffectInstance(YATMMobEffectHolders.CHORUS_INSTANT_TELEPORTATION, 1, 16), 1f).build();
	public static final FoodProperties CHORUS_BIOFLUID = new FoodProperties.Builder().nutrition(1).saturationModifier(.1f).effect(new MobEffectInstance(MobEffects.POISON, 200, 0), .6f).effect(new MobEffectInstance(YATMMobEffectHolders.CHORUS_INSTANT_TELEPORTATION, 1, 8), 1f).build();
	public static final FoodProperties DILUTED_TEAR = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0), 1f).build();
	public static final FoodProperties ENDER = new FoodProperties.Builder().effect(new MobEffectInstance(YATMMobEffectHolders.ENDER_INSTANT_TELEPORTATION, 1, 32), 1f).build();
	public static final FoodProperties ESSENCE_OF_DECAY = new FoodProperties.Builder().effect(new MobEffectInstance(MobEffects.WITHER, 2400, 4), 1f).build();
	public static final FoodProperties ESSENCE_OF_SOULS = new FoodProperties.Builder().effect(new MobEffectInstance(YATMMobEffectHolders.SOUL_AFFLICTION, 2400, 4), 1f).build();	
	public static final FoodProperties LATEX = new FoodProperties.Builder().nutrition(-1).saturationModifier(.1f).effect(new MobEffectInstance(MobEffects.POISON, 1200, 0), 1).build();
	public static final FoodProperties SOUL_SAP = new FoodProperties.Builder().nutrition(1).saturationModifier(1f).effect(new MobEffectInstance(YATMMobEffectHolders.SOUL_AFFLICTION, 300, 0), 1f).build();
	public static final FoodProperties SOUL_SYRUP = new FoodProperties.Builder().nutrition(3).saturationModifier(1.2f).effect(new MobEffectInstance(YATMMobEffectHolders.SOUL_AFFLICTION, 600, 1), 1f).build();

} // end class