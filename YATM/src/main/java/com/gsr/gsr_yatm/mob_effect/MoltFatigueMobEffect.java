package com.gsr.gsr_yatm.mob_effect;

import java.util.UUID;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

// TODO, might should be removed now.
public class MoltFatigueMobEffect extends MobEffect
{
	public static final UUID FRESH_ARMOR_MODIFIER_UUID = UUID.fromString("8335efd5-4616-4b0f-af34-cc39d370c685");



	public MoltFatigueMobEffect(MobEffectCategory category, int color)
	{
//		MobEffects l;
//		YATMMobEffects rl;
		super(category, color);
	} // end constructor

//
//	@Override
//	public double getAttributeModifierValue(int multiplier, AttributeModifier attributeMod)
//	{
//		return -3 * ((double) (multiplier + 1));
//	}

} // end class