package com.gsr.gsr_yatm.item.tool;

import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class SawItem extends PoweredToolItem
{

	public SawItem(float baseDamage, float attackSpeed, @NotNull Tier tier, @NotNull Properties properties,	@NotNegative int actionCost, @NotNegative int attackCost, @NotNegative int mineCost)
	{
		super(baseDamage, attackSpeed, Objects.requireNonNull(tier), BlockTags.MINEABLE_WITH_AXE, Objects.requireNonNull(properties), Contract.notNegative(actionCost), Contract.notNegative(attackCost), Contract.notNegative(mineCost));
	} // end constructor
	
	public SawItem(@NotNull Tier tier, @NotNull Properties properties, @NotNegative int actionCost, @NotNegative int attackCost, @NotNegative int mineCost)
	{
		this(5.0f, -3.0f, Objects.requireNonNull(tier), Objects.requireNonNull(properties), Contract.notNegative(actionCost), Contract.notNegative(attackCost), Contract.notNegative(mineCost));
	} // end constructor
	
	
	

	@Override
	public Set<ToolAction> getActions()
	{
		return ToolActions.DEFAULT_AXE_ACTIONS;
	} // end getActions()

	@Override
	protected void performingAction(@NotNull UseOnContext context, ToolAction action)
	{
		super.performingAction(context, action);
		Level level = context.getLevel();
		Player player = context.getPlayer();
		BlockPos position = context.getClickedPos();
		if (action == ToolActions.AXE_STRIP)
		{
			level.playSound(player, position, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1f, 1f);
		}
		else if (action == ToolActions.AXE_SCRAPE)
		{
			level.playSound(player, position, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1f, 1f);
			level.levelEvent(player, 3005, position, 0);
		}
		else if (action == ToolActions.AXE_WAX_OFF)
		{
			level.playSound(player, position, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1f, 1f);
			level.levelEvent(player, 3004, position, 0);
		}
	} // end performingAction()
	
} // end class