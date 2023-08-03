package com.gsr.gsr_yatm.utilities;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.entity.boat.YATMBoatType;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YATMModelLayers
{
	public static final ModelLayerLocation DEAFULT_HANGING_POT_SUPPORT_CHAIN_MODEL_NAME = new ModelLayerLocation(new ResourceLocation(YetAnotherTechMod.MODID, "default_hanging_pot_support_chains"), "main");
	
	
	
	public static ModelLayerLocation createYATMBoatModelName(YATMBoatType type)
	{
		ResourceLocation location = new ResourceLocation(type.getIdentifier());
		return new ModelLayerLocation(new ResourceLocation(location.getNamespace(), "boat/" + location.getPath()), "main");
	} // end createBoatModelName()

	public static ModelLayerLocation createYATMChestBoatModelName(YATMBoatType type)
	{
		ResourceLocation location = new ResourceLocation(type.getIdentifier());
		return new ModelLayerLocation(new ResourceLocation(location.getNamespace(), "chest_boat/" + location.getPath()), "main");
	} // end createChestBoatModelName()

} // end class