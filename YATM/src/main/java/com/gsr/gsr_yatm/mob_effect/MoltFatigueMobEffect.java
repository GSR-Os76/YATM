package com.gsr.gsr_yatm.mob_effect;

import java.util.UUID;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MoltFatigueMobEffect extends MobEffect
{
	public static final UUID FRESH_ARMOR_MODIFIER_UUID = UUID.fromString("8335efd5-4616-4b0f-af34-cc39d370c685");



	public MoltFatigueMobEffect(MobEffectCategory category, int color)
	{
		super(category, color);
	} // end constructor


	@Override
	public double getAttributeModifierValue(int multiplier, AttributeModifier attributeMod)
	{
		return -3 * ((double) (multiplier + 1));
	}
//	@Override
//	public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int multiplier)
//	{
//		AttributeInstance a = entity.getAttribute(Attributes.ARMOR);
//		if (a != null)
//		{
//			// TODO, seemingly no combination will take away from the armor, all increase value
//			MobEffects l;.
//			a.addPermanentModifier(new AttributeModifier(FRESH_ARMOR_MODIFIER_UUID, "Fresh armor weakness", -(2D / ((double) multiplier)), AttributeModifier.Operation.ADDITION));
//		}
//		super.addAttributeModifiers(entity, attributes, multiplier);
//	} // end addAttributeModifiers()
//
//	@Override
//	public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int multiplier)
//	{
//		AttributeInstance a = entity.getAttribute(Attributes.ARMOR);
//		if (a != null)
//		{
//			a.removeModifier(FRESH_ARMOR_MODIFIER_UUID);
//		}
//		super.removeAttributeModifiers(entity, attributes, multiplier);
//	} // end removeAttributeModifiers()

} // end class