package com.gsr.gsr_yatm.armor.soul_adorned_netherite;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;

public class SoulAdornedNetheriteArmorMaterial
{
public static final ResourceLocation LAYER = new ResourceLocation(YetAnotherTechMod.MODID, "soul_adorned_netherite");
	
	public static final Map<ArmorItem.Type, Integer> DEFENSE_FOR_TYPE = ArmorMaterials.NETHERITE.get().defense().entrySet().stream().map((e) -> Map.entry(e.getKey(), (int)(((float)e.getValue()) * YATMConfigs.SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_DEFENSE_FACTOR.get()))).collect(ImmutableMap.toImmutableMap((e) -> e.getKey(), (e) -> e.getValue()));
	public static final int ENCHANTMENT_VALUE = (int)((YATMConfigs.SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR.get() * (float)ArmorMaterials.NETHERITE.get().enchantmentValue()));
	public static final Holder<SoundEvent> EQUIP_SOUND = ArmorMaterials.NETHERITE.get().equipSound();
	public static final Supplier<Ingredient> REPAIR_INGREDIENT = () -> Ingredient.EMPTY;
	public static final List<ArmorMaterial.Layer> LAYERS =  List.of(new ArmorMaterial.Layer(SoulAdornedNetheriteArmorMaterial.LAYER));
	public static final float TOUGHNESS = (float)(YATMConfigs.SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_TOUGHNESS_FACTOR.get() * ArmorMaterials.NETHERITE.get().toughness());
	public static final float KNOCKBACK_RESISTANCE = (float)(YATMConfigs.SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR.get() * ArmorMaterials.NETHERITE.get().knockbackResistance());
	
	public static final ArmorMaterial MATERIAL = new ArmorMaterial(
			SoulAdornedNetheriteArmorMaterial.DEFENSE_FOR_TYPE,
			SoulAdornedNetheriteArmorMaterial.ENCHANTMENT_VALUE,
			SoulAdornedNetheriteArmorMaterial.EQUIP_SOUND,
			SoulAdornedNetheriteArmorMaterial.REPAIR_INGREDIENT,
			SoulAdornedNetheriteArmorMaterial.LAYERS,
			SoulAdornedNetheriteArmorMaterial.TOUGHNESS,
			SoulAdornedNetheriteArmorMaterial.KNOCKBACK_RESISTANCE);
	
} // end class