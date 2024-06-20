package com.gsr.gsr_yatm.utilities;

import com.gsr.gsr_yatm.registry.YATMMobEffects;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMMobEffectHolders
{

	public static final Holder<MobEffect> CHORUS_INSTANT_TELEPORTATION = ForgeRegistries.MOB_EFFECTS.getHolder(YATMMobEffects.CHORUS_INSTANT_TELEPORTATION.get()).orElseThrow();
	public static final Holder<MobEffect> ENDER_INSTANT_TELEPORTATION = ForgeRegistries.MOB_EFFECTS.getHolder(YATMMobEffects.ENDER_INSTANT_TELEPORTATION.get()).orElseThrow();
	public static final Holder<MobEffect> SOUL_AFFLICTION = ForgeRegistries.MOB_EFFECTS.getHolder(YATMMobEffects.SOUL_AFFLICTION.get()).orElseThrow();
	
} // end class