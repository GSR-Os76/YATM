package com.gsr.gsr_yatm.armor.decay_netherite;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.armor.IArmorSet;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class DecayNetheriteArmorSet implements IArmorSet
{

	@Override
	public boolean isMember(@NotNull Item item)
	{
		return YATMItemTags.DECAY_NETHERITE_ARMOR_PIECES.contains(item);
	} // end isMember()

	@Override
	public void subscribeEffects(IEventBus eventBus)
	{
		eventBus.addListener(this::onEntityDamaged);
	} // end subscribeEffects()
	
	
	
	private void onEntityDamaged(LivingDamageEvent event) 
	{
		if (event.getSource().is(DamageTypes.WITHER))
		{
			LivingEntity entity  = event.getEntity();
			RandomSource random = RandomSource.createNewThreadLocalInstance();
			int amountToHeal = (int)(event.getAmount() * 6f);
			int setCount = 0;
			for(ItemStack i : entity.getArmorSlots()) 
			{
				if(this.isMember(i.getItem())) 
				{
					i.setDamageValue(Math.max(0, i.getDamageValue() - amountToHeal));
					++setCount;
				}
			}
			if(setCount >= 4 || (random.nextFloat() < (((float) setCount) / 4.0f))) 
			{
				event.setCanceled(true);
				entity.removeEffect(MobEffects.WITHER);
			}			
		}
	} // end onEntityDamaged()

} // end class