package com.gsr.gsr_yatm.data_generation;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.data_generation.generic.configured_feature.ConfiguredFeatureProvider;
import com.gsr.gsr_yatm.registry.YATMBlocks;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import net.minecraftforge.common.data.ExistingFileHelper;


public class YATMConfiguredFeatureProvider extends ConfiguredFeatureProvider
{
	public YATMConfiguredFeatureProvider(@NotNull PackOutput packOutput, @NotNull ExistingFileHelper existingFileHelper)
	{
		super(YetAnotherTechMod.MODID, Objects.requireNonNull(packOutput), Objects.requireNonNull(existingFileHelper));
	} // end constructor

	
	@Override
	public void addConfiguredFeatures()
	{
		this.add(new ResourceLocation(YetAnotherTechMod.MODID, "test"), new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(YATMBlocks.ICE_CORAL.get().defaultBlockState().setValue(YATMBlocks.ICE_CORAL.get().getAgeProperty(), YATMBlocks.ICE_CORAL.get().getMaxAge())))));;
	} // end addConfiguredFeatures()

} // end class