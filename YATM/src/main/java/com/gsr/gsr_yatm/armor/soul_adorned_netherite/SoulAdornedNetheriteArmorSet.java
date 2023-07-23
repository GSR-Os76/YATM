package com.gsr.gsr_yatm.armor.soul_adorned_netherite;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.armor.IArmorSet;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class SoulAdornedNetheriteArmorSet implements IArmorSet
{

	@Override
	public boolean isMember(@NotNull Item item)
	{
		return YATMItemTags.SOUL_ADORNED_NETHERITE_ARMOR_PIECES.contains(item);
	} // end isMember()

	@Override
	public void subscribeEffects(IEventBus eventBus)
	{
		eventBus.addListener(this::onEntityTick);
	} // end subscribeEffects()

	
	
	private void onEntityTick(LivingTickEvent event) 
	{
		RandomSource random = RandomSource.createNewThreadLocalInstance();
		for (ItemStack i : event.getEntity().getArmorSlots())
		{
			if(this.isMember(i.getItem())) 
			{
				if(random.nextInt(20) == 0) 
				{
					i.setDamageValue(Math.max(0, (i.getDamageValue() - 1)));
				}
				if(random.nextInt(60) == 0) 
				{
					event.getEntity().heal(1.0f);
				}
			}
		}
	} // end soulArmorTick()
	
} // end class