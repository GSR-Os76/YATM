package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.worldgen.foliage_placer.AgedFoliagePlacer;

import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMFoliagePlacerTypes
{
	public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<FoliagePlacerType<AgedFoliagePlacer>> RUBBER_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("rubber_foliage_placer", () -> new FoliagePlacerType<>(AgedFoliagePlacer.CODEC));	

} // end class