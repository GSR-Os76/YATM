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
	public static final RegistryObject<BioFluid.Flowing> BIO_FLOWING = FLUIDS.register("bio_flowing", () -> new BioFluid.Flowing());

	public static final RegistryObject<ChorusFluid.Source> CHORUS = FLUIDS.register("chorus", () -> new ChorusFluid.Source());
	public static final RegistryObject<ChorusFluid.Flowing> CHORUS_FLOWING = FLUIDS.register("chorus_flowing", () -> new ChorusFluid.Flowing());

	public static final RegistryObject<ChorusBioFluid.Source> CHORUS_BIO = FLUIDS.register("chorus_bio", () -> new ChorusBioFluid.Source());
	public static final RegistryObject<ChorusBioFluid.Flowing> CHORUS_BIO_FLOWING = FLUIDS.register("chorus_bio_flowing", () -> new ChorusBioFluid.Flowing());

	public static final RegistryObject<EnderFluid.Source> ENDER = FLUIDS.register("ender", () -> new EnderFluid.Source());
	public static final RegistryObject<EnderFluid.Flowing> ENDER_FLOWING = FLUIDS.register("ender_flowing", () -> new EnderFluid.Flowing());
	
	public static final RegistryObject<EssenceOfDecayFluid.Source> ESSENCE_OF_DECAY = FLUIDS.register("essence_of_decay", () -> new EssenceOfDecayFluid.Source());
	public static final RegistryObject<EssenceOfDecayFluid.Flowing> ESSENCE_OF_DECAY_FLOWING = FLUIDS.register("essence_of_decay_flowing", () -> new EssenceOfDecayFluid.Flowing());

	public static final RegistryObject<EssenceOfSoulsFluid.Source> ESSENCE_OF_SOULS = FLUIDS.register("essence_of_souls", () -> new EssenceOfSoulsFluid.Source());
	public static final RegistryObject<EssenceOfSoulsFluid.Flowing> ESSENCE_OF_SOULS_FLOWING = FLUIDS.register("essence_of_souls_flowing", () -> new EssenceOfSoulsFluid.Flowing());

	public static final RegistryObject<LatexFluid.Source> LATEX = FLUIDS.register("latex", () -> new LatexFluid.Source());
	public static final RegistryObject<LatexFluid.Flowing> LATEX_FLOWING = FLUIDS.register("latex_flowing", () -> new LatexFluid.Flowing());

	public static final RegistryObject<SiliconOxideFluid.Source> SILICON_OXIDE = FLUIDS.register("silicon_oxide", () -> new SiliconOxideFluid.Source());
	public static final RegistryObject<SiliconOxideFluid.Flowing> SILICON_OXIDE_FLOWING = FLUIDS.register("silicon_oxide_flowing", () -> new SiliconOxideFluid.Flowing());

	public static final RegistryObject<SoulSapFluid.Source> SOUL_SAP = FLUIDS.register("soul_sap", () -> new SoulSapFluid.Source());
	public static final RegistryObject<SoulSapFluid.Flowing> SOUL_SAP_FLOWING = FLUIDS.register("soul_sap_flowing", () -> new SoulSapFluid.Flowing());

	public static final RegistryObject<SoulSyrupFluid.Source> SOUL_SYRUP = FLUIDS.register("soul_syrup", () -> new SoulSyrupFluid.Source());
	public static final RegistryObject<SoulSyrupFluid.Flowing> SOUL_SYRUP_FLOWING = FLUIDS.register("soul_syrup_flowing", () -> new SoulSyrupFluid.Flowing());
	
	
	
	// TODO, shouldn't really be FlowingFluid probably.
	public static final RegistryObject<CelestialLightFluid.Source> CELESTIAL_LIGHT = FLUIDS.register("celestial_light", () -> new CelestialLightFluid.Source());
	public static final RegistryObject<CelestialLightFluid.Flowing> CELESTIAL_LIGHT_FLOWING = FLUIDS.register("celestial_light_flowing", () -> new CelestialLightFluid.Flowing());

	public static final RegistryObject<LunarLightFluid.Source> LUNAR_LIGHT = FLUIDS.register("lunar_light", () -> new LunarLightFluid.Source());
	public static final RegistryObject<LunarLightFluid.Flowing> LUNAR_LIGHT_FLOWING = FLUIDS.register("lunar_light_flowing", () -> new LunarLightFluid.Flowing());

	public static final RegistryObject<SolarLightFluid.Source> SOLAR_LIGHT = FLUIDS.register("solar_light", () -> new SolarLightFluid.Source());
	public static final RegistryObject<SolarLightFluid.Flowing> SOLAR_LIGHT_FLOWING = FLUIDS.register("solar_light_flowing", () -> new SolarLightFluid.Flowing());

} // end class