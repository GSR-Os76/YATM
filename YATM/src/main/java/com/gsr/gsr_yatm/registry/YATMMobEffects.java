package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.mob_effect.InstantTeleportationMobEffect;
import com.gsr.gsr_yatm.mob_effect.MoltFatigueMobEffect;
import com.gsr.gsr_yatm.mob_effect.SoulAfflictionMobEffect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMMobEffects
{
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, YetAnotherTechMod.MODID);
	
	

	public static final RegistryObject<InstantTeleportationMobEffect> CHORUS_INSTANT_TELEPORTATION = MOB_EFFECTS.register("chorus_instant_teleportation", () -> new InstantTeleportationMobEffect(MobEffectCategory.NEUTRAL, 0x78_59_78_FF, SoundEvents.CHORUS_FRUIT_TELEPORT));
	public static final RegistryObject<InstantTeleportationMobEffect> ENDER_INSTANT_TELEPORTATION = MOB_EFFECTS.register("ender_instant_teleportation", () -> new InstantTeleportationMobEffect(MobEffectCategory.NEUTRAL, 0x10_5E_51_FF, SoundEvents.SHULKER_TELEPORT));
	public static final RegistryObject<MoltFatigueMobEffect> MOLT_FATIGUE = MOB_EFFECTS.register("molt_fatigue", () -> (MoltFatigueMobEffect)(new MoltFatigueMobEffect(MobEffectCategory.HARMFUL, 0xC4_C3_AF_FF).addAttributeModifier(Attributes.ARMOR, MoltFatigueMobEffect.FRESH_ARMOR_MODIFIER_UUID.toString(), 0.0D, AttributeModifier.Operation.ADDITION)));
	public static final RegistryObject<SoulAfflictionMobEffect> SOUL_AFFLICTION = MOB_EFFECTS.register("soul_affliction", () -> new SoulAfflictionMobEffect(MobEffectCategory.NEUTRAL, 0x39_EA_F0_FF));

} // end class