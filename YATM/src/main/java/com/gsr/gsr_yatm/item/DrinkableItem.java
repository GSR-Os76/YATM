package com.gsr.gsr_yatm.item;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class DrinkableItem extends Item
{
	private final @NotNegative int m_useDuration;
	
	
	
	public DrinkableItem(@NotNull Properties properties, @NotNegative int useDuration)
	{
		super(Objects.requireNonNull(properties));
		this.m_useDuration = Contract.notNegative(useDuration);
	} // end constructor

	
	
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		return ItemUtils.startUsingInstantly(level, player, hand);
	} // end use()
	
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity)
	{
		super.finishUsingItem(itemStack, level, entity);

		ItemStack rem = this.getCraftingRemainingItem(itemStack);
		if (itemStack.isEmpty())
		{
			return rem;
		}
		else
		{
			if (!(entity instanceof Player player && player.getInventory().add(rem)))
			{
				InventoryUtil.drop(level, entity.blockPosition(), itemStack);
			}

			return itemStack;
		}
	} // end finishUsingItem()

	
	
	public int getUseDuration(ItemStack itemStack)
	{
		return this.m_useDuration;
	} // end getUseDuration()

	public UseAnim getUseAnimation(ItemStack itemStack)
	{
		return UseAnim.DRINK;
	} // end getUseAnimation()

	
	
	public SoundEvent getDrinkingSound()
	{
		return SoundEvents.GENERIC_DRINK;
	} // end getDrinkingSound()

	public SoundEvent getEatingSound()
	{
		return SoundEvents.GENERIC_DRINK;
	} // end getEatingSound()	
	
} // end class