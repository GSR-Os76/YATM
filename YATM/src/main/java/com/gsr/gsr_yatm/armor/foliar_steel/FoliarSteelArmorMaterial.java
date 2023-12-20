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
		return switch(type) 
		{
			case HELMET -> YATMConfigs.FOLIAR_STEEL_HELMET_DURABILITY.get();
			case CHESTPLATE -> YATMConfigs.FOLIAR_STEEL_CHESTPLATE_DURABILITY.get();
			case LEGGINGS -> YATMConfigs.FOLIAR_STEEL_LEGGINGS_DURABILITY.get();
			case BOOTS -> YATMConfigs.FOLIAR_STEEL_BOOTS_DURABILITY.get();
			default -> throw new IllegalArgumentException("Unexpected value of: " + type);
		};
	} // end getDurabilityForType()

	@Override
	public int getDefenseForType(Type type)
	{
		return switch(type) 
		{
			case HELMET -> YATMConfigs.FOLIAR_STEEL_HELMET_DEFENSE.get();
			case CHESTPLATE -> YATMConfigs.FOLIAR_STEEL_CHESTPLATE_DEFENSE.get();
			case LEGGINGS -> YATMConfigs.FOLIAR_STEEL_LEGGINGS_DEFENSE.get();
			case BOOTS -> YATMConfigs.FOLIAR_STEEL_BOOTS_DEFENSE.get();
			default -> throw new IllegalArgumentException("Unexpected value of: " + type);
		};
	} // end getDefenseForType()

	@Override
	public int getEnchantmentValue()
	{
		return YATMConfigs.FOLIAR_STEEL_ENCHANTMENT_VALUE.get();
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
		double d = YATMConfigs.FOLIAR_STEEL_TOUGHNESS.get();
		return (float)d;
	} // end getToughness()

	@Override
	public float getKnockbackResistance()
	{
		double d = YATMConfigs.FOLIAR_STEEL_KNOCKBACK_RESISTANCE.get();
		return (float)d;
	} // end getKnockbackResistance()
	
} // end class