package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.entity.boat.YATMBoat;
import com.gsr.gsr_yatm.entity.boat.YATMChestBoat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMEntityTypes
{	
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<EntityType<YATMBoat>> YATM_BOAT = YATMEntityTypes.register("yatm_boat", EntityType.Builder.<YATMBoat>of(YATMBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
	public static final RegistryObject<EntityType<YATMChestBoat>> YATM_CHEST_BOAT = YATMEntityTypes.register("yatm_chest_boat", EntityType.Builder.<YATMChestBoat>of(YATMChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10));
	
	
	
	private static <T extends Entity> RegistryObject<EntityType<T>> register(String key, EntityType.Builder<T> builder) 
	{
		return ENTITY_TYPES.register(key, () -> builder.build(key));
	} // end register
	
} // end class