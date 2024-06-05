package com.gsr.gsr_yatm.gui.behavior.fluid;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.level.material.Fluid;

public interface IFluidStackInfoProvider
{
	@NotNull Fluid getFluid();

	@NotNegative int getAmount();

	@NotNegative int getCapacity();
} // end interface