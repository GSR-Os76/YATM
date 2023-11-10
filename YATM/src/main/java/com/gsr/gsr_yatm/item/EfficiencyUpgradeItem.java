package com.gsr.gsr_yatm.item;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.Item;

public class EfficiencyUpgradeItem extends Item implements IEfficiencyUpgradeItem
{
	private final float m_bonus;
	private final @NotNull BonusType m_type;
	
	
	
	public EfficiencyUpgradeItem(@NotNull Properties properties, float bonus, @NotNull BonusType type)
	{
		super(Objects.requireNonNull(properties));
		this.m_bonus = bonus;
		this.m_type = type;
	} // end constructor

	
	
	@Override
	public float getBonus()
	{
		return this.m_bonus;
	} // end getBonus()

	@Override
	public @NotNull BonusType getBonusType()
	{
		return this.m_type;
	} // end getBonusType()

} // end class