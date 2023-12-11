package com.gsr.gsr_yatm.armor.decay_netherite;

import com.gsr.gsr_yatm.YATMConfigs;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.crafting.Ingredient;

public class DecayNetheriteArmorMaterial implements ArmorMaterial
{
	
	@Override
	public int getDurabilityForType(Type type)
	{
		return (int)((YATMConfigs.DECAY_NETHERITE_NETHERITE_RELATIVE_DURABILITY_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getDurabilityForType(type)));
	} // end getDurabilityForType()

	@Override
	public int getDefenseForType(Type type)
	{
		return (int)((YATMConfigs.DECAY_NETHERITE_NETHERITE_RELATIVE_DEFENSE_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getDefenseForType(type)));
	} // end getDefenseForType()

	@Override
	public int getEnchantmentValue()
	{
		return (int)((YATMConfigs.DECAY_NETHERITE_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getEnchantmentValue()));
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
		return "gsr_yatm:decay_netherite";
	} // end getName()

	@Override
	public float getToughness()
	{
		return (int)((YATMConfigs.DECAY_NETHERITE_NETHERITE_RELATIVE_TOUGHNESS_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getToughness()));
	} // end getToughness()

	@Override
	public float getKnockbackResistance()
	{
		return (float)((YATMConfigs.DECAY_NETHERITE_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getKnockbackResistance()));
	} // end getKnockbackResistance()
	
} // end class