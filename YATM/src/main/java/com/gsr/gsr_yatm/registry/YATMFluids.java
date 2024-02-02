package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.fluid.BioFluid;
import com.gsr.gsr_yatm.fluid.CelestialLightFluid;
import com.gsr.gsr_yatm.fluid.ChorusBioFluid;
import com.gsr.gsr_yatm.fluid.ChorusFluid;
import com.gsr.gsr_yatm.fluid.EnderFluid;
import com.gsr.gsr_yatm.fluid.EssenceOfDecayFluid;
import com.gsr.gsr_yatm.fluid.EssenceOfSoulsFluid;
import com.gsr.gsr_yatm.fluid.LatexFluid;
import com.gsr.gsr_yatm.fluid.LunarLightFluid;
import com.gsr.gsr_yatm.fluid.SiliconOxideFluid;
import com.gsr.gsr_yatm.fluid.SolarLightFluid;
import com.gsr.gsr_yatm.fluid.SoulSapFluid;
import com.gsr.gsr_yatm.fluid.SoulSyrupFluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class YATMFluids
{
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, YetAnotherTechMod.MODID);


	
	// TODO, add create source rules like with water and lava
	// TODO, add drip particles
	// TODO, add fog sort of effect while submerged.
	// TODO, add effects when entities inside
	// liquid latex that burns you if you're in the sun light
	// TODO, placed soul fluids should be corruptible too	
	public static final RegistryObject<BioFluid.Source> BIO = FLUIDS.register("bio", () -> new BioFluid.Source());
	public static final RegistryObject<BioFluid.Flowing> FLOWING_BIO = FLUIDS.register("flowing_bio", () -> new BioFluid.Flowing());

	public static final RegistryObject<ChorusFluid.Source> CHORUS = FLUIDS.register("chorus", () -> new ChorusFluid.Source());
	public static final RegistryObject<ChorusFluid.Flowing> FLOWING_CHORUS = FLUIDS.register("flowing_chorus", () -> new ChorusFluid.Flowing());

	public static final RegistryObject<ChorusBioFluid.Source> CHORUS_BIO = FLUIDS.register("chorus_bio", () -> new ChorusBioFluid.Source());
	public static final RegistryObject<ChorusBioFluid.Flowing> FLOWING_CHORUS_BIO = FLUIDS.register("flowing_chorus_bio", () -> new ChorusBioFluid.Flowing());

	public static final RegistryObject<EnderFluid.Source> ENDER = FLUIDS.register("ender", () -> new EnderFluid.Source());
	public static final RegistryObject<EnderFluid.Flowing> FLOWING_ENDER = FLUIDS.register("flowing_ender", () -> new EnderFluid.Flowing());
	
	public static final RegistryObject<EssenceOfDecayFluid.Source> ESSENCE_OF_DECAY = FLUIDS.register("essence_of_decay", () -> new EssenceOfDecayFluid.Source());
	public static final RegistryObject<EssenceOfDecayFluid.Flowing> FLOWING_ESSENCE_OF_DECAY = FLUIDS.register("flowing_essence_of_decay", () -> new EssenceOfDecayFluid.Flowing());

	public static final RegistryObject<EssenceOfSoulsFluid.Source> ESSENCE_OF_SOULS = FLUIDS.register("essence_of_souls", () -> new EssenceOfSoulsFluid.Source());
	public static final RegistryObject<EssenceOfSoulsFluid.Flowing> FLOWING_ESSENCE_OF_SOULS = FLUIDS.register("flowing_essence_of_souls", () -> new EssenceOfSoulsFluid.Flowing());

	public static final RegistryObject<LatexFluid.Source> LATEX = FLUIDS.register("latex", () -> new LatexFluid.Source());
	public static final RegistryObject<LatexFluid.Flowing> FLOWING_LATEX = FLUIDS.register("flowing_latex", () -> new LatexFluid.Flowing());

	public static final RegistryObject<SiliconOxideFluid.Source> SILICON_OXIDE = FLUIDS.register("silicon_oxide", () -> new SiliconOxideFluid.Source());
	public static final RegistryObject<SiliconOxideFluid.Flowing> FLOWING_SILICON_OXIDE = FLUIDS.register("flowing_silicon_oxide", () -> new SiliconOxideFluid.Flowing());

	public static final RegistryObject<SoulSapFluid.Source> SOUL_SAP = FLUIDS.register("soul_sap", () -> new SoulSapFluid.Source());
	public static final RegistryObject<SoulSapFluid.Flowing> FLOWING_SOUL_SAP = FLUIDS.register("flowing_soul_sap", () -> new SoulSapFluid.Flowing());

	public static final RegistryObject<SoulSyrupFluid.Source> SOUL_SYRUP = FLUIDS.register("soul_syrup", () -> new SoulSyrupFluid.Source());
	public static final RegistryObject<SoulSyrupFluid.Flowing> FLOWING_SOUL_SYRUP = FLUIDS.register("flowing_soul_syrup", () -> new SoulSyrupFluid.Flowing());
	
	
	
	// TODO, shouldn't really be FlowingFluid probably.
	public static final RegistryObject<CelestialLightFluid.Source> CELESTIAL_LIGHT = FLUIDS.register("celestial_light", () -> new CelestialLightFluid.Source());
	public static final RegistryObject<CelestialLightFluid.Flowing> FLOWING_CELESTIAL_LIGHT = FLUIDS.register("flowing_celestial_light", () -> new CelestialLightFluid.Flowing());

	public static final RegistryObject<LunarLightFluid.Source> LUNAR_LIGHT = FLUIDS.register("lunar_light", () -> new LunarLightFluid.Source());
	public static final RegistryObject<LunarLightFluid.Flowing> FLOWING_LUNAR_LIGHT = FLUIDS.register("flowing_lunar_light", () -> new LunarLightFluid.Flowing());

	public static final RegistryObject<SolarLightFluid.Source> SOLAR_LIGHT = FLUIDS.register("solar_light", () -> new SolarLightFluid.Source());
	public static final RegistryObject<SolarLightFluid.Flowing> FLOWING_SOLAR_LIGHT = FLUIDS.register("flowing_solar_light", () -> new SolarLightFluid.Flowing());

} // end class