package com.gsr.gsr_yatm.item.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public class BiometricAuthenticationSlipItem extends Item implements INBTSerializable<CompoundTag>
{
	private Component m_playerName = null;
	
	

	public BiometricAuthenticationSlipItem(Properties p_41383_)
	{
		super(p_41383_);
	} // end constructor

	
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
	{
		if(!level.isClientSide && m_playerName == null) 
		{
			m_playerName = player.getName();
		}
		
		return new InteractionResultHolder<ItemStack>(InteractionResult.sidedSuccess(level.isClientSide), player.getInventory().getSelected());
	} // end use



	@Override
	public CompoundTag serializeNBT()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
	

} // end class