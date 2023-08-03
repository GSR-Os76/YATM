package com.gsr.gsr_yatm.utilities;

import java.util.HashMap;
import java.util.Map;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YATMSheets
{
	public static final ResourceLocation BLOCKS_SHEET = new ResourceLocation("textures/atlas/blocks.png");// new ResourceLocation("blocks");
	
	public static final ResourceLocation HANGING_POT_SUPPORT_CHAINS_SHEET = new ResourceLocation(YetAnotherTechMod.MODID, "textures/atlas/hanging_pot_support_chains.png");

	protected static final Map<ResourceLocation, Material> BLOCKS = new HashMap<>();
	protected static final Map<ResourceLocation, Material> HANGING_POT_SUPPORT_CHAINS = new HashMap<>();
	

	
	public static void addBlock(ResourceLocation location)
	{
		YATMSheets.BLOCKS.put(location, YATMSheets.createBlock(location));
	} // end addHangingPotSupportChains()

	private static Material createBlock(ResourceLocation location)
	{
		return new Material(Sheets.DECORATED_POT_SHEET, new ResourceLocation(location.getNamespace(), "block/" + location.getPath()));
	} // end createHangingPotSupportChains()
	
	public static Material getBlock(ResourceLocation location) 
	{
		return YATMSheets.BLOCKS.get(location);
	} // end getHangingPotSupportChains()
	
	
	
	public static void addHangingPotSupportChains(ResourceLocation location)
	{
		YATMSheets.HANGING_POT_SUPPORT_CHAINS.put(location, YATMSheets.createHangingPotSupportChains(location));
	} // end addHangingPotSupportChains()

	private static Material createHangingPotSupportChains(ResourceLocation location)
	{
		return new Material(YATMSheets.HANGING_POT_SUPPORT_CHAINS_SHEET, new ResourceLocation(location.getNamespace(), "entity/hanging_pot_support_chains/" + location.getPath()));
	} // end createHangingPotSupportChains()
	
	public static Material getHangingPotSupportChains(ResourceLocation location) 
	{
		return YATMSheets.HANGING_POT_SUPPORT_CHAINS.get(location);
	} // end getHangingPotSupportChains()
	
} // end class