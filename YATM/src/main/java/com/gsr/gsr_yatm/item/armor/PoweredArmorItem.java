package com.gsr.gsr_yatm.item.armor;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class PoweredArmorItem extends LazyArmorItem
{
	private final @NotNull Supplier<@NotNull Integer> m_cuPerDurability;
	private final @NotNull Supplier<@NotNull Boolean> m_canBeBroken;
	
	
	public PoweredArmorItem(@NotNull ArmorMaterial material, @NotNull Type type, @NotNull Properties properties, @NotNull Supplier<@NotNull @NotNegative Integer> cuPerDurability, @NotNull Supplier<@NotNull Boolean> canBeBroken)
	{
		super(Objects.requireNonNull(material), Objects.requireNonNull(type), Objects.requireNonNull(properties));
		
		this.m_cuPerDurability = Objects.requireNonNull(cuPerDurability);
		this.m_canBeBroken = canBeBroken;
	} // end constructor

	@Override
	public <T extends LivingEntity> int damageItem(@NotNull ItemStack stack, int amount, T entity, Consumer<T> onBroken)
	{
		int md = this.getMaxDamage(stack);
		int cd = stack.getDamageValue();
		return this.m_canBeBroken.get() 
				? super.damageItem(stack, amount, entity, onBroken)
				: cd + amount >= md ? md - cd - 1 : super.damageItem(stack, amount, entity, onBroken);
	} // end damageItem()
	
	@Override
	public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot, @NotNull ItemStack stack)
	{
		return this.getMaxDamage(stack) - 1 == this.getDamage(stack) ? ImmutableMultimap.of() : super.getAttributeModifiers(slot, stack);
	} // end getAttributeModifiers()

	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new PoweredArmorItemStack(stack, this.m_cuPerDurability.get());
	} // end initCapabilities()
	
} // end class