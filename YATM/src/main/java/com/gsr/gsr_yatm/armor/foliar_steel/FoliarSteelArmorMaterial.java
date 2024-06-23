package com.gsr.gsr_yatm.armor.foliar_steel;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;

public class FoliarSteelArmorMaterial
{
	public static final ResourceLocation LAYER = new ResourceLocation(YetAnotherTechMod.MODID, "foliar_steel");
	
	public static final Map<ArmorItem.Type, Integer> DEFENSE_FOR_TYPE = ImmutableMap.of(
			ArmorItem.Type.BOOTS, YATMConfigs.FOLIAR_STEEL_ARMOR_BOOTS_DEFENSE.get(),
			ArmorItem.Type.LEGGINGS, YATMConfigs.FOLIAR_STEEL_ARMOR_LEGGINGS_DEFENSE.get(),
			ArmorItem.Type.CHESTPLATE, YATMConfigs.FOLIAR_STEEL_ARMOR_CHESTPLATE_DEFENSE.get(),
			ArmorItem.Type.HELMET, YATMConfigs.FOLIAR_STEEL_ARMOR_HELMET_DEFENSE.get(),
			ArmorItem.Type.BODY, YATMConfigs.FOLIAR_STEEL_ARMOR_BODY_DEFENSE.get());
				
	public static final int ENCHANTMENT_VALUE = YATMConfigs.FOLIAR_STEEL_ARMOR_ENCHANTMENT_VALUE.get();
	public static final Holder<SoundEvent> EQUIP_SOUND = ArmorMaterials.IRON.get().equipSound();
	public static final Supplier<Ingredient> REPAIR_INGREDIENT = () -> Ingredient.of(YATMItems.FOLIAR_STEEL.get());
	public static final List<ArmorMaterial.Layer> LAYERS =  List.of(new ArmorMaterial.Layer(FoliarSteelArmorMaterial.LAYER));
	public static final float TOUGHNESS = YATMConfigs.FOLIAR_STEEL_ARMOR_TOUGHNESS.get();
	public static final float KNOCKBACK_RESISTANCE = YATMConfigs.FOLIAR_STEEL_ARMOR_KNOCKBACK_RESISTANCE.get();
	
	public static final ArmorMaterial MATERIAL = new ArmorMaterial(
			FoliarSteelArmorMaterial.DEFENSE_FOR_TYPE,
			FoliarSteelArmorMaterial.ENCHANTMENT_VALUE,
			FoliarSteelArmorMaterial.EQUIP_SOUND,
			FoliarSteelArmorMaterial.REPAIR_INGREDIENT,
			FoliarSteelArmorMaterial.LAYERS,
			FoliarSteelArmorMaterial.TOUGHNESS,
			FoliarSteelArmorMaterial.KNOCKBACK_RESISTANCE);
	
} // end class