package com.gsr.gsr_yatm.block.plant.tree.soul_afflicted_rubber_bush;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ApicalSoulAfflictedRubberBushGrower extends AbstractTreeGrower
{

	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource tandomSource, boolean hasBees)
	{
		return FeatureUtils.createKey("gsr_yatm:soul_afflicted_rubber_bush_apical");
	} // end getCondifugredFeature()
} // end class