package com.gsr.gsr_yatm;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YATMFeatures
{
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, YetAnotherTechMod.MODID);

	
	//public static final RegistryObject<Feature<NoneFeatureConfiguration>> G = FEATURES.register("rubber_bush_apical", () -> new TreeConfiguration.TreeConfigurationBuilder(null, null, null, null, null).build());


} // end class