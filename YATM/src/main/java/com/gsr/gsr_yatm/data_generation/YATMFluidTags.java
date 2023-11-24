package com.gsr.gsr_yatm.data_generation;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMFluids;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMFluidTags extends FluidTagsProvider
{
	private static final String FORGE_ID = "forge";
	
	private static final ITagManager<Fluid> TM = ForgeRegistries.FLUIDS.tags();
	
	
	
	public static final TagKey<Fluid> FORGE_ENDER_KEY = TM.createTagKey(new ResourceLocation(YATMFluidTags.FORGE_ID, "ender"));
	public static final TagKey<Fluid> FORGE_SILICON_OXIDE_KEY = TM.createTagKey(new ResourceLocation(YATMFluidTags.FORGE_ID, "silicon_oxide"));
	
	
	
	public YATMFluidTags(@NotNull PackOutput output, @NotNull CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(Objects.requireNonNull(output), Objects.requireNonNull(lookupProvider), YetAnotherTechMod.MODID, existingFileHelper);
	} // end constructor


	
	@Override
	protected void addTags(Provider provider)
	{
		this.tag(YATMFluidTags.FORGE_ENDER_KEY).add(YATMFluids.ENDER.get()).add(YATMFluids.ENDER_FLOWING.get());
		this.tag(YATMFluidTags.FORGE_SILICON_OXIDE_KEY).add(YATMFluids.SILICON_OXIDE.get()).add(YATMFluids.SILICON_OXIDE_FLOWING.get());
	} // end addTags()
	
} // end class