package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.plant.tree.CanopyTrunkPlacer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMTrunkPlacerTypes
{
	public static final DeferredRegister<TrunkPlacerType<?>>  TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<TrunkPlacerType<CanopyTrunkPlacer>> CANOPY_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("canopy_trunk_placer", () -> new TrunkPlacerType<>(CanopyTrunkPlacer.CODEC));	

}
