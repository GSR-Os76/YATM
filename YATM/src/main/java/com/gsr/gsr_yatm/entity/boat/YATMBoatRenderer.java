package com.gsr.gsr_yatm.entity.boat;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.gsr.gsr_yatm.utilities.YATMModelLayers;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YATMBoatRenderer extends BoatRenderer
{
	private final Map<YATMBoatType, Pair<ResourceLocation, ListModel<Boat>>> m_yatmBoatResources;

	
	
	public YATMBoatRenderer(Context context, boolean hasChest)
	{
		super(context, hasChest);
		this.shadowRadius = 0.8F;
		this.m_yatmBoatResources = Stream.of(YATMBoatType.values()).collect(ImmutableMap.toImmutableMap(
				(key) -> key, 
				(value) -> Pair.of(YATMBoatRenderer.getTextureLocation(value, hasChest), this.createBoatModel(context, value, hasChest))));
	} // end constructor

	

	private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, YATMBoatType type, boolean hasChest)
	{
		ModelPart modelpart = context.bakeLayer(hasChest ? YATMModelLayers.createYATMChestBoatModelName(type) : YATMModelLayers.createYATMBoatModelName(type));
		return (ListModel<Boat>) (hasChest ? new ChestBoatModel(modelpart) : new BoatModel(modelpart));
	} // end createBoatModel()

	private static ResourceLocation getTextureLocation(YATMBoatType type, boolean hasChest)
	{
		ResourceLocation location = new ResourceLocation(type.getIdentifier());
		return new ResourceLocation(location.getNamespace(), ((hasChest ? "textures/entity/chest_boat/" : "textures/entity/boat/") + location.getPath() + ".png"));
	} // end getTextureLocation()


	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat)
	{
		return boat instanceof IYATMBoat yb ? this.m_yatmBoatResources.get(yb.getVariantType()) : super.getModelWithLocation(boat);
	} // end getModelWithLoaction()

} // end class