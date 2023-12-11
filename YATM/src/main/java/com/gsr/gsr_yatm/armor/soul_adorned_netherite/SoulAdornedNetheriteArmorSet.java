package com.gsr.gsr_yatm.armor.soul_adorned_netherite;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.armor.IArmorSet;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class SoulAdornedNetheriteArmorSet implements IArmorSet
{

	@Override
	public boolean isMember(@NotNull ItemStack item)
	{
		return item.is(YATMItemTags.SOUL_ADORNED_NETHERITE_ARMOR_PIECES_KEY);
	} // end isMember()

	@Override
	public void subscribeEffects(@NotNull IEventBus eventBus)
	{
		eventBus.addListener(this::onEntityTick);
	} // end subscribeEffects()

	
	
	private void onEntityTick(@NotNull LivingTickEvent event) 
	{
		LivingEntity entity  = event.getEntity();
		Level level = entity.level();
		RandomSource random = level.random;
		for (ItemStack i : entity.getArmorSlots())
		{
			if(this.isMember(i)) 
			{
				if(random.nextInt(YATMConfigs.SOUL_ADORNED_NETHERITE_SELF_REPAIR_RARITY.get()) == 0) 
				{
					i.setDamageValue(Math.max(0, (i.getDamageValue() - YATMConfigs.SOUL_ADORNED_NETHERITE_ITEM_DAMAGE_REDUCTION.get())));
				}
				if(random.nextInt(YATMConfigs.SOUL_ADORNED_NETHERITE_WEARER_HEAL_RARITY.get()) == 0) 
				{
					event.getEntity().heal(YATMConfigs.SOUL_ADORNED_NETHERITE_WEARER_HEAL_AMOUNT.get());
				}
			}
		}
	} // end onEntityTick()
	
} // end class