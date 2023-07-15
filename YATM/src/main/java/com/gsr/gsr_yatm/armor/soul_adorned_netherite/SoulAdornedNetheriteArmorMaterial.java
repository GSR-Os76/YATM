package com.gsr.gsr_yatm.armor.soul_adorned_netherite;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;

public class SoulAdornedNetheriteArmorMaterial implements ArmorMaterial
{
	public static final float NETHERITE_RELATIVE_DURABILITY_FACTOR = 1.6f;
	public static final float NETHERITE_RELATIVE_DEFENSE_FACTOR = 0.625f;
	public static final float NETHERITE_RELATIVE_ENCHANTMENT_FACTOR = 2.0f;
	public static final float NETHERITE_RELATIVE_TOUGHNESS_FACTOR = 1.33f;
	
	
	
	@Override
	public int getDurabilityForType(Type type)
	{
		return (int)((NETHERITE_RELATIVE_DURABILITY_FACTOR * (float)ArmorMaterials.NETHERITE.getDurabilityForType(type)));
	} // end getDurabilityForType()

	@Override
	public int getDefenseForType(Type type)
	{
		return (int)((NETHERITE_RELATIVE_DEFENSE_FACTOR * (float)ArmorMaterials.NETHERITE.getDefenseForType(type)));
	} // end getDefenseForType()

	@Override
	public int getEnchantmentValue()
	{
		return (int)((NETHERITE_RELATIVE_ENCHANTMENT_FACTOR * (float)ArmorMaterials.NETHERITE.getEnchantmentValue()));
	} // end getEnchantmentValue()

	@Override
	public SoundEvent getEquipSound()
	{
		return ArmorMaterials.NETHERITE.getEquipSound();
	} // end getEquipSound()

	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.EMPTY;
	} // end getRepairIngredient()

	@Override
	public String getName()
	{
		return "gsr_yatm:soul_adorned_netherite";
	} // end getName()

	@Override
	public float getToughness()
	{
		return (int)((NETHERITE_RELATIVE_TOUGHNESS_FACTOR * (float)ArmorMaterials.NETHERITE.getToughness()));
	} // end getToughness()

	@Override
	public float getKnockbackResistance()
	{
		return ArmorMaterials.NETHERITE.getKnockbackResistance();
	} // end getKnockbackResistance()
	
} // end class