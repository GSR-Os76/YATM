package com.gsr.gsr_yatm.item;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.InventoryUtilities;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShulkwartHornItem extends Item
{
	private final DyeColor m_color;
	
	
	
	public ShulkwartHornItem(Properties properties, DyeColor color)
	{
		super(properties);
		this.m_color = color;
	} // end constructor
	
	
	
	public @Nullable DyeColor getDyeColor()
	{
		return this.m_color;
	} // end getDyeColor()
	

	
	// TODO, make soul essence->decay essence conversion use this method instead of the use or useOn, whichever it's currently
	@Override
	public InteractionResult interactLivingEntity(ItemStack held, Player player, LivingEntity e, InteractionHand hand)
	{
		if(e instanceof Shulker shulker) 
		{
			shulker.setVariant(this.m_color == null ? Optional.empty() : Optional.of(this.m_color));
			Level level = e.level();
			level.playSound(player, shulker.blockPosition(), SoundEvents.SHULKER_BOX_OPEN, SoundSource.PLAYERS, 1.0f, 1.0f);
			level.playSound(player, shulker.blockPosition(), SoundEvents.SHULKER_BOX_CLOSE, SoundSource.PLAYERS, 1.0f, 1.0f);
			InventoryUtilities.drop(level, shulker.blockPosition(), new ItemStack(Items.SHULKER_SHELL, 2));
			held.shrink(1);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}			
		return super.interactLivingEntity(held, player, e, hand);
	} // end interactLivingEntity()
	
} // end class