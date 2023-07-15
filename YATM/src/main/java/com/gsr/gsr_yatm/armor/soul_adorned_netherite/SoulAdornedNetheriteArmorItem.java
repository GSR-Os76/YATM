package com.gsr.gsr_yatm.armor.soul_adorned_netherite;

import java.util.function.Consumer;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulAdornedNetheriteArmorItem extends ArmorItem
{
	public static final String HEAL_ITEM_COUNT_DOWN_TAG_NAME = "healItemCountDown";
	public static final int HEAL_ITEM_PERIOD = 20;
	
	public static final String HEAL_WEARER_COUNT_DOWN_TAG_NAME = "healWearerCountDown";
	public static final int HEAL_WEARER_PERIOD = 20;
	
	
	
	public SoulAdornedNetheriteArmorItem(ArmorMaterial armorMaterial, Type pieceType, Properties properties)
	{
		super(armorMaterial, pieceType, properties);
	} // end constructor
	
	
	
	
	
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken)
	{
		// TODO Auto-generated method stub
		return super.damageItem(stack, amount, entity, onBroken);
	}





	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int dunno, boolean unknown)
	{
		YetAnotherTechMod.LOGGER.info("invTick, dunno: " + dunno);
		CompoundTag tag = stack.getOrCreateTag();

		int periodCountDown = !tag.contains(HEAL_ITEM_COUNT_DOWN_TAG_NAME) ? HEAL_ITEM_PERIOD : tag.getInt(HEAL_ITEM_COUNT_DOWN_TAG_NAME) - 1;
		if(periodCountDown <= 0) 
		{
			stack.setDamageValue(stack.getDamageValue() + 1);
			periodCountDown = HEAL_ITEM_PERIOD;
		}
		tag.putInt(HEAL_ITEM_COUNT_DOWN_TAG_NAME, periodCountDown);
		
		super.inventoryTick(stack, level, entity, dunno, unknown);
	} // end inventoryTick()





	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player)
	{
		YetAnotherTechMod.LOGGER.info("armorTick");
		CompoundTag tag = stack.getOrCreateTag();

		int periodCountDown = !tag.contains(HEAL_WEARER_COUNT_DOWN_TAG_NAME) ? HEAL_WEARER_PERIOD : tag.getInt(HEAL_WEARER_COUNT_DOWN_TAG_NAME) - 1;
		if(periodCountDown <= 0) 
		{
			//stack.setDamageValue(stack.getDamageValue() + 1);
			player.heal(1f);
			periodCountDown = HEAL_WEARER_PERIOD;
		}
		tag.putInt(HEAL_WEARER_COUNT_DOWN_TAG_NAME, periodCountDown);
		super.onArmorTick(stack, level, player);
	} // end onArmorTick()
	
} // end class