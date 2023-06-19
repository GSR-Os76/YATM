package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.fluid.EssenceOfDecayFluid;
import com.gsr.gsr_yatm.fluid.LatexFluid;
import com.gsr.gsr_yatm.fluid.SoulSapFluid;

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

	public static final RegistryObject<FlowingFluid> SOUL_SAP = FLUIDS.register("soul_sap", () -> new SoulSapFluid.Source());
	public static final RegistryObject<FlowingFluid> SOUL_SAP_FLOWING = FLUIDS.register("soul_sap_flowing", () -> new SoulSapFluid.Flowing());

	public static final RegistryObject<FlowingFluid> ESSENCE_OF_DECAY = FLUIDS.register("essence_of_decay", () -> new EssenceOfDecayFluid.Source());
	public static final RegistryObject<FlowingFluid> ESSENCE_OF_DECAY_FLOWING = FLUIDS.register("essence_of_decay_flowing", () -> new EssenceOfDecayFluid.Flowing());

} // end class