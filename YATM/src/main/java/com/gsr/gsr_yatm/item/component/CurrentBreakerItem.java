package com.gsr.gsr_yatm.item.component;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CurrentBreakerItem extends CurrentFuseItem
{

	public CurrentBreakerItem(Properties properties, int overloadThreshold)
	{
		super(properties, overloadThreshold);
	} // end constructor

	
	
	// TODO, doesn't works in creative mode
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack held = player.getItemInHand(hand);
		if(player.isCrouching() && held.getItem() instanceof CurrentBreakerItem) 
		{
			if(!level.isClientSide) 
			{
				held.setDamageValue(0);
			}
			return InteractionResultHolder.sidedSuccess(held, level.isClientSide);
		}
		return InteractionResultHolder.pass(held);
	} // end use()
	
} // end class