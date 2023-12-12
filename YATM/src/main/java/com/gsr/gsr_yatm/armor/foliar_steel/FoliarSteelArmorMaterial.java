package com.gsr.gsr_yatm.armor.foliar_steel;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class FoliarSteelArmorMaterial implements ArmorMaterial
{
	@Override
	public int getDurabilityForType(Type type)
	{
		return (int)((YATMConfigs.FOLIAR_STEEL_NETHERITE_RELATIVE_DURABILITY_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getDurabilityForType(type)));
	} // end getDurabilityForType()

	@Override
	public int getDefenseForType(Type type)
	{
		return (int)((YATMConfigs.FOLIAR_STEEL_NETHERITE_RELATIVE_DEFENSE_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getDefenseForType(type)));
	} // end getDefenseForType()

	@Override
	public int getEnchantmentValue()
	{
		return (int)((YATMConfigs.FOLIAR_STEEL_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getEnchantmentValue()));
	} // end getEnchantmentValue()

	@Override
	public SoundEvent getEquipSound()
	{
		return ArmorMaterials.IRON.getEquipSound();
	} // end getEquipSound()

	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.of(YATMItems.FOLIAR_STEEL.get());
	} // end getRepairIngredient()

	@Override
	public String getName()
	{
		return "gsr_yatm:foliar_steel";
	} // end getName()

	@Override
	public float getToughness()
	{
		return (float)((YATMConfigs.FOLIAR_STEEL_NETHERITE_RELATIVE_TOUGHNESS_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getToughness()));
	} // end getToughness()

	@Override
	public float getKnockbackResistance()
	{
		return (float)((YATMConfigs.FOLIAR_STEEL_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR.get() * (float)ArmorMaterials.NETHERITE.getKnockbackResistance()));
	} // end getKnockbackResistance()
	
} // end class