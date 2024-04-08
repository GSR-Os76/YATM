package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.worldgen.root_placer.LayeredRandomSpreadRootPlacer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMRootPlacerTypes
{
	public static final DeferredRegister<RootPlacerType<?>>  ROOT_PLACER_TYPES = DeferredRegister.create(Registries.ROOT_PLACER_TYPE, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<RootPlacerType<LayeredRandomSpreadRootPlacer>> LAYERED_RANDOM_SPREAD_ROOT_PLACER = ROOT_PLACER_TYPES.register("layered_random_spread_root_placer", () -> new RootPlacerType<>(LayeredRandomSpreadRootPlacer.CODEC));	

} // end class