package com.gsr.gsr_yatm.item;

import java.util.Objects;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableMultimap;
import com.gsr.gsr_yatm.armor.YATMArmorMaterials;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class LazyArmorItem extends ArmorItem
{
	private static Runnable LOADER = null;
	
	
	
	public LazyArmorItem(@NotNull ArmorMaterial material, @NotNull Type type, @NotNull Properties properties)
	{
		super(YATMArmorMaterials.EMPTY, Objects.requireNonNull(type), properties.defaultDurability(1));
		this.material = Objects.requireNonNull(material);
		Runnable l = LOADER;
		LOADER = l == null 
				? () -> LazyArmorItem.this.load(material) 
				: () -> { l.run(); LazyArmorItem.this.load(material);};
	} // end constructor

	public static @NotNull Runnable loader() 
	{
		return () -> {LOADER.run(); LazyArmorItem.LOADER = null;};
	} // end loader()
	
	
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return this.material.getDurabilityForType(this.type);
	} // end getMaxDamager



	private void load(@NotNull ArmorMaterial material)
	{
		this.defense = material.getDefenseForType(this.type);
		this.toughness = material.getToughness();
		this.knockbackResistance = material.getKnockbackResistance();
		DispenserBlock.registerBehavior(this, LazyArmorItem.DISPENSE_ITEM_BEHAVIOR);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
		builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double) this.defense, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double) this.toughness, AttributeModifier.Operation.ADDITION));
		if (this.knockbackResistance > 0)
		{
			builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double) this.knockbackResistance, AttributeModifier.Operation.ADDITION));
		}

		this.defaultModifiers = builder.build();
	} // end load()
} // end class