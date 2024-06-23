package com.gsr.gsr_yatm.armor.empty;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class EmptyArmorMaterial
{
	public static final Map<ArmorItem.Type, Integer> DEFENSE_FOR_TYPE = Stream.of(Type.values()).map((t) -> Map.entry(t, 0)) .collect(ImmutableMap.toImmutableMap((e) -> e.getKey(), (e) -> e.getValue()));
	public static final int ENCHANTMENT_VALUE = 0;
	public static final Holder<SoundEvent> EQUIP_SOUND = Holder.direct(SoundEvents.EMPTY);
	public static final Supplier<Ingredient> REPAIR_INGREDIENT = () -> Ingredient.EMPTY;
	public static final List<ArmorMaterial.Layer> LAYERS =  List.of();
	public static final float TOUGHNESS = 0f;
	public static final float KNOCKBACK_RESISTANCE = 0f;
	
	public static final ArmorMaterial MATERIAL = new ArmorMaterial(
			DEFENSE_FOR_TYPE,
			ENCHANTMENT_VALUE,
			EQUIP_SOUND,
			REPAIR_INGREDIENT,
			LAYERS,
			TOUGHNESS,
			KNOCKBACK_RESISTANCE);

} // end class