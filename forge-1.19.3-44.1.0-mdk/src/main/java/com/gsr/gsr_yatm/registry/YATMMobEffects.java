package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.mob_effect.InstantTeleportationMobEffect;
import com.gsr.gsr_yatm.mob_effect.SoulAfflictionMobEffect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMMobEffects
{
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, YetAnotherTechMod.MODID);
	
	

	public static final RegistryObject<MobEffect> CHORUS_INSTANT_TELEPORTATION = MOB_EFFECTS.register("chorus_instant_teleportation", () -> new InstantTeleportationMobEffect(MobEffectCategory.NEUTRAL, 0, SoundEvents.CHORUS_FRUIT_TELEPORT));
	public static final RegistryObject<MobEffect> ENDER_INSTANT_TELEPORTATION = MOB_EFFECTS.register("ender_instant_teleportation", () -> new InstantTeleportationMobEffect(MobEffectCategory.NEUTRAL, 0, SoundEvents.SHULKER_TELEPORT));
	public static final RegistryObject<MobEffect> SOUL_AFFLICTION = MOB_EFFECTS.register("soul_affliction", () -> new SoulAfflictionMobEffect(MobEffectCategory.NEUTRAL, 0));

} // end class