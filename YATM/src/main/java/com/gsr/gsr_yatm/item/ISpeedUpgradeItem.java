package com.gsr.gsr_yatm.item;

import org.jetbrains.annotations.NotNull;

public interface ISpeedUpgradeItem
{

	public float getBonus();
	
	public @NotNull BonusType getBonusType();
} // end interface