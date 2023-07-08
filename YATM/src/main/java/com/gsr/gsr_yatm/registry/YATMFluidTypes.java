package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.fluid.BioFluidType;
import com.gsr.gsr_yatm.fluid.CelestialLightFluidType;
import com.gsr.gsr_yatm.fluid.ChorusBioFluidType;
import com.gsr.gsr_yatm.fluid.ChorusFluidType;
import com.gsr.gsr_yatm.fluid.EnderFluidType;
import com.gsr.gsr_yatm.fluid.EssenceOfDecayFluidType;
import com.gsr.gsr_yatm.fluid.EssenceOfSoulsFluidType;
import com.gsr.gsr_yatm.fluid.LatexFluidType;
import com.gsr.gsr_yatm.fluid.LunarLightFluidType;
import com.gsr.gsr_yatm.fluid.SolarLightFluidType;
import com.gsr.gsr_yatm.fluid.SoulSapFluidType;
import com.gsr.gsr_yatm.fluid.SoulSyrupFluidType;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidType.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMFluidTypes
{
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, YetAnotherTechMod.MODID);

	public static final RegistryObject<FluidType> BIO = FLUID_TYPES.register("bio", () -> new BioFluidType(Properties.create().viscosity((int)(4600))));
	public static final RegistryObject<FluidType> CHORUS = FLUID_TYPES.register("chorus", () -> new ChorusFluidType(Properties.create().viscosity((int)(4600))));
	public static final RegistryObject<FluidType> CHORUS_BIO = FLUID_TYPES.register("chorus_bio", () -> new ChorusBioFluidType(Properties.create().viscosity((int)(4600))));
	public static final RegistryObject<FluidType> ENDER = FLUID_TYPES.register("ender", () -> new EnderFluidType(Properties.create().viscosity((int)(4600))));
	public static final RegistryObject<FluidType> ESSENCE_OF_DECAY = FLUID_TYPES.register("essence_of_decay", () -> new EssenceOfDecayFluidType(Properties.create().viscosity((int)(200))));
	public static final RegistryObject<FluidType> ESSENCE_OF_SOULS = FLUID_TYPES.register("essence_of_souls", () -> new EssenceOfSoulsFluidType(Properties.create().viscosity((int)(200))));
	public static final RegistryObject<FluidType> LATEX = FLUID_TYPES.register("latex", () -> new LatexFluidType(Properties.create().viscosity((int)(4600))));
	public static final RegistryObject<FluidType> SOUL_SAP = FLUID_TYPES.register("soul_sap", () -> new SoulSapFluidType(Properties.create().viscosity((int)(3600))));
	public static final RegistryObject<FluidType> SOUL_SYRUP = FLUID_TYPES.register("soul_syrup", () -> new SoulSyrupFluidType(Properties.create().viscosity((int)(21000))));

	public static final RegistryObject<FluidType> CELESTIAL_LIGHT = FLUID_TYPES.register("celestial_light", () -> new CelestialLightFluidType(Properties.create().viscosity((int)(0))));
	public static final RegistryObject<FluidType> LUNAR_LIGHT = FLUID_TYPES.register("lunar_light", () -> new LunarLightFluidType(Properties.create().viscosity((int)(0))));
	public static final RegistryObject<FluidType> SOLAR_LIGHT = FLUID_TYPES.register("solar_light", () -> new SolarLightFluidType(Properties.create().viscosity((int)(0))));
	
	
	// TODO, maybe maybe add refinement process for soul sap
	// boil: syrup
	// dehydrate: crystals of resin
	// dissolve in something: something
	// distill: EssenceOfTheSoul
} // end class