package com.gsr.gsr_yatm.item.fluid;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.InventoryUtilities;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class DrinkableFluidBottleItem extends FluidBottleItem
{
	private final int m_useDuration;
	
	
	
	public DrinkableFluidBottleItem(Properties properties, Supplier<? extends Fluid> fluid, int useDuration)
	{
		super(properties, fluid);
		this.m_useDuration = useDuration;
	} // end constructor

	
	
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
				InventoryUtilities.drop(level, entity.blockPosition(), itemStack);
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
	}

	public SoundEvent getDrinkingSound()
	{
		return SoundEvents.GENERIC_DRINK;
	} // end getDrinkingSound()

	public SoundEvent getEatingSound()
	{
		return SoundEvents.GENERIC_DRINK;
	} // end getEatingSound()

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		return ItemUtils.startUsingInstantly(level, player, hand);
	}
} // end class