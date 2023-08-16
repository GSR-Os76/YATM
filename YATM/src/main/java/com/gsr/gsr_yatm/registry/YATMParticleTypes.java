package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMParticleTypes
{
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, YetAnotherTechMod.MODID);
	

	
	public static final RegistryObject<SimpleParticleType> DRIPPING_TAPPED_LOG_LATEX = PARTICLE_TYPES.register("dripping_tapped_log_latex", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> DRIPPING_TAPPED_LOG_SOUL_SAP = PARTICLE_TYPES.register("dripping_tapped_log_soul_sap", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> FALLING_LATEX = PARTICLE_TYPES.register("falling_latex", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> FALLING_SOUL_SAP = PARTICLE_TYPES.register("falling_soul_sap", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> LANDING_LATEX = PARTICLE_TYPES.register("landing_latex", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> LANDING_SOUL_SAP = PARTICLE_TYPES.register("landing_soul_sap", () -> new SimpleParticleType(false));

} // end class