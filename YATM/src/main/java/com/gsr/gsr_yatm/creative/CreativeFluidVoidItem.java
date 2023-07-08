package com.gsr.gsr_yatm.creative;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CreativeFluidVoidItem extends Item
{

	public CreativeFluidVoidItem(Properties properties)
	{
		super(properties);
	} // end constructor

//	@Override
//	public InteractionResult useOn(UseOnContext context)
//	{
//		// TODO Auto-generated method stub
//		// maybe drain fluid from world
//		return super.useOn(context);
//	}

//	@Override
//	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
//	{
//		// TODO Auto-generated method stub
//		// set the fluid to empty
//		if(level.isClientSide()) 
//		{
//			
//		}
//		return super.use(level, player, hand);
//	}

	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		if(this.getClass() == CreativeFluidVoidItem.class) 
		{
			return new CreativeFluidVoidItemStack(stack);
		}
	
		return super.initCapabilities(stack, nbt);
	} // end initCapabilities()
} // end class
