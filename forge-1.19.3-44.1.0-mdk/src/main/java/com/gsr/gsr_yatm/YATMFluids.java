package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.fluid.LatexFluid;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.material.FlowingFluid;


public class YATMFluids
{
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, YetAnotherTechMod.MODID);


	
	public static final RegistryObject<FlowingFluid> LATEX = FLUIDS.register("latex", () -> new LatexFluid.Source());
	public static final RegistryObject<FlowingFluid> LATEX_FLOWING = FLUIDS.register("latex_flowing", () -> new LatexFluid.Flowing());

} // end class