package com.gsr.gsr_yatm.mob_effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SoulAfflictionMobEffect extends MobEffect
{

	public SoulAfflictionMobEffect(MobEffectCategory category, int color)
	{
		super(category, color);
	} // end constructor

	
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier)
	{
		if (entity.getHealth() < entity.getMaxHealth()) 
		{
            entity.heal(1 + (amplifier / 3));
        }		
	} // end applyEffectTick()

	@Override
	public boolean shouldApplyEffectTickThisTick(int ticksRemaining, int amplifier)
	{
		int scaledFrequency = 16 >> amplifier;
		return scaledFrequency == 0 ? true : ticksRemaining % scaledFrequency == 0;
	} // end isDurationEffectTick()
	
} // end applyEffectTick()