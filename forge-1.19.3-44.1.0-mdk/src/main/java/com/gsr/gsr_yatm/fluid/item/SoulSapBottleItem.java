package com.gsr.gsr_yatm.fluid.item;

import java.util.List;
import java.util.function.Supplier;

import com.gsr.gsr_yatm.YATMEntityTypeTags;
import com.gsr.gsr_yatm.YATMItems;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class SoulSapBottleItem extends FluidBottleItem
{

	public SoulSapBottleItem(Properties properties, Supplier<? extends Fluid> fluid)
	{
		super(properties, fluid);
	} // end constructor

	

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		List<Entity> withers = level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(2.0D), (e) -> YATMEntityTypeTags.FORGE_WITHER_TAG.contains(e.getType()));
		if(!withers.isEmpty()) 
		{
			// TODO, maybe some particles
			return InteractionResultHolder.sidedSuccess(new ItemStack(YATMItems.ESSENCE_OF_DECAY_BOTTLE.get()), level.isClientSide);
		}
		return InteractionResultHolder.pass(player.getItemInHand(hand));
	} // end use()
	
} // end class