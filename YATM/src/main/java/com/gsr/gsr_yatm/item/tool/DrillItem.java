package com.gsr.gsr_yatm.item.tool;

import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class DrillItem extends PoweredToolItem
{

	public DrillItem(float baseDamage, float attackSpeed, @NotNull Tier tier, @NotNull Properties properties, @NotNegative int actionCost, @NotNegative int attackCost, @NotNegative int mineCost)
	{
		super(baseDamage, attackSpeed, Objects.requireNonNull(tier), BlockTags.MINEABLE_WITH_PICKAXE, Objects.requireNonNull(properties), Contract.notNegative(actionCost), Contract.notNegative(attackCost), Contract.notNegative(mineCost));
	} // end constructor
	
	public DrillItem(@NotNull Tier tier, @NotNull Properties properties, @NotNegative int actionCost, @NotNegative int attackCost, @NotNegative int mineCost)
	{
		this(5.0f, -3.0f, Objects.requireNonNull(tier), Objects.requireNonNull(properties), Contract.notNegative(actionCost), Contract.notNegative(attackCost), Contract.notNegative(mineCost));
	} // end constructor
	
	
	
	@Override
	public Set<ToolAction> getActions()
	{
		return ToolActions.DEFAULT_PICKAXE_ACTIONS;
	} // end getActions()
	
} // end class