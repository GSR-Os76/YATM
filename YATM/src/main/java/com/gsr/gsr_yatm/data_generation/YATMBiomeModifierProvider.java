package com.gsr.gsr_yatm.data_generation;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.data_generation.generic.biome_modifier.BiomeModifierProvider;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class YATMBiomeModifierProvider extends BiomeModifierProvider
{

	public YATMBiomeModifierProvider(@NotNull PackOutput packOutput, @NotNull ExistingFileHelper existingFileHelper)
	{
		super(YetAnotherTechMod.MODID, Objects.requireNonNull(packOutput), Objects.requireNonNull(existingFileHelper));
	} // end constructor

	
	
	@Override
	public void addBiomeModifiers()
	{
//		this.modify(BiomeTags.IS_OVERWORLD, new ResourceLocation(YetAnotherTechMod.MODID, "feature_to_overworld_test"))
//		.addFeature(null)
//		.step(Decoration.VEGETAL_DECORATION)
//		.end(); //TreePlacements.MEGA_SPRUCE_CHECKED;
	}
} // end class