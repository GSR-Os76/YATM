package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.fluid.LatexFluidType;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidType.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMFluidTypes
{
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<FluidType> LATEX = FLUID_TYPES.register("latex", () -> new LatexFluidType(Properties.create().viscosity((int)(4600))));

} // end class