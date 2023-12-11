package com.gsr.gsr_yatm.armor.empty;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class EmptyArmorMaterial implements ArmorMaterial
{

	@Override
	public int getDurabilityForType(Type p_266807_)
	{
		return 0;
	}

	@Override
	public int getDefenseForType(Type p_267168_)
	{
		return 0;
	}

	@Override
	public int getEnchantmentValue()
	{
		return 0;
	}

	@Override
	public SoundEvent getEquipSound()
	{
		return SoundEvents.EMPTY;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return Ingredient.EMPTY;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public float getToughness()
	{
		return 0;
	}

	@Override
	public float getKnockbackResistance()
	{
		return 0;
	}

} // end class