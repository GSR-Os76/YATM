package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.fluid.EssenceOfDecayFluidType;
import com.gsr.gsr_yatm.fluid.LatexFluidType;
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

	
	public static final RegistryObject<FluidType> ESSENCE_OF_DECAY = FLUID_TYPES.register("essence_of_decay", () -> new EssenceOfDecayFluidType(Properties.create().viscosity((int)(200))));
	public static final RegistryObject<FluidType> LATEX = FLUID_TYPES.register("latex", () -> new LatexFluidType(Properties.create().viscosity((int)(4600))));
	public static final RegistryObject<FluidType> SOUL_SAP = FLUID_TYPES.register("soul_sap", () -> new SoulSapFluidType(Properties.create().viscosity((int)(3600))));
	public static final RegistryObject<FluidType> SOUL_SYRUP = FLUID_TYPES.register("soul_syrup", () -> new SoulSyrupFluidType(Properties.create().viscosity((int)(21000))));


	
	// TODO, maybe maybe add refinement process for soul sap
	// boil: syrup
	// dehydrate: crystals of resin
	// dissolve in something: something
	// distill: EssenceOfTheSoul
} // end class