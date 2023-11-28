package com.gsr.gsr_yatm.item.tool;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

public abstract class PoweredToolItem extends DiggerItem
{
	private final @NotNull TagKey<Block> m_mineables;
	
	private final @NotNegative int m_actionCost;
	private final @NotNegative int m_attackCost;
	private final @NotNegative int m_mineCost;
	
	
	
	public PoweredToolItem(float baseDamage, float attackSpeed, @NotNull Tier tier, @NotNull TagKey<Block> mineables, @NotNull Properties properties,
			@NotNegative int actionCost, @NotNegative int attackCost, @NotNegative int mineCost)
	{
		super(baseDamage, attackSpeed, Objects.requireNonNull(tier), Objects.requireNonNull(mineables), Objects.requireNonNull(properties));
	
		this.m_mineables = Objects.requireNonNull(mineables);
		
		this.m_actionCost = Contract.notNegative(actionCost);
		this.m_attackCost = Contract.notNegative(attackCost);
		this.m_mineCost = Contract.notNegative(mineCost);
	} // end constructor

	
	
	// TODO, no known itemstack sensitive version of getAttackDamage()
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state)
	{		
		return this.hasPowerToMine(stack, state) ? (super.getDestroySpeed(stack, state) + 1f) * this.getSpeedMultiplier(stack) : 1f;
	} // end getDestroySpeed()
	
	@Override
	public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
	{
		return this.hasPowerToMine(stack, state) && super.isCorrectToolForDrops(stack, state);
	} // end isCorrectToolForDrops()
	
	@Override
	public int getEnchantmentValue()
	{
		return 0;
	} // end getEnchantmentValue();

	

	public boolean hurtEnemy(ItemStack stack, LivingEntity hurt, LivingEntity user)
	{
		if(this.hasPowerToAttack(stack, hurt)) 
		{
			this.setPower(stack, this.getPower(stack) - this.attackCost(stack, hurt));
		}
		return true;
	} // end hurtEnemy()

	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos position, LivingEntity user)
	{
		if(this.hasPowerToMine(stack, state)) 
		{
			this.setPower(stack, this.getPower(stack) - this.mineCost(stack, state));
		}
		return true;
	} // end mineBlock()
	
	
	
	@Override
	public InteractionResult useOn(@NotNull UseOnContext context)
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		BlockState state = level.getBlockState(position);
		ItemStack held = context.getItemInHand();
		
		for(ToolAction ta : this.getActions()) 
		{
			if(!this.canPerformAction(held, ta)) 
			{
				continue;
			}
			Optional<BlockState> res = Optional.ofNullable(state.getToolModifiedState(context, ta, false));
			if(res.isPresent()) 
			{
				Player player = context.getPlayer();
				this.performingAction(context, ta);
				if (player instanceof ServerPlayer)
				{
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, position, held);
				}

				level.setBlock(position, res.get(), Block.UPDATE_ALL_IMMEDIATE);
				level.gameEvent(GameEvent.BLOCK_CHANGE, position, GameEvent.Context.of(player, res.get()));

				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		if(!level.isClientSide && context.getPlayer() instanceof ServerPlayer serverPlayer && serverPlayer.isCrouching()) 
		{
			NetworkHooks.openScreen(serverPlayer, this.getMenuProvider(held));
		}
		return InteractionResult.PASS;
	} // end useOn()
	
	

//	@Override
//	public Component getName(@NotNull ItemStack stack)
//	{
//		return Component.translatable(this.getDescriptionId(stack))
//				.append(Component.literal("(" + this.getPower(stack) + "cu/" + this.getMaxPower(stack)+ "cu)"));
//	} // end getName()
	
	

	@Override
	public boolean canPerformAction(@NotNull ItemStack stack, ToolAction toolAction)
	{
		return this.hasPowerForAction(stack, toolAction) ? this.getActions().contains(toolAction) : false;
	} // end canPerformAction()
	
	public abstract Set<ToolAction> getActions();
	
	protected void performingAction(@NotNull UseOnContext context, ToolAction action) 
	{
		ItemStack held = context.getItemInHand();
		this.setPower(held, this.getPower(held) - this.actionCost(held, action));
	} // end performingAction()
	
	
	
	public int getPower(@NotNull ItemStack stack) 
	{
		return this.getToolProvider(stack).stored();
	} // end getPower()
	
	public void setPower(@NotNull ItemStack stack, @NotNegative int power) 
	{
		this.getToolProvider(stack).setStoredCurrent(power);
	} // end setPower()
	
	public float getEfficiency(@NotNull ItemStack stack) 
	{
		return this.getToolProvider(stack).getEfficiencyMultiplier();
	} // end getEfficiency()
	
	public float getSpeedMultiplier(@NotNull ItemStack stack) 
	{
		return this.getToolProvider(stack).getSpeedMultiplier();
	} // end getSpeedMultiplier()
	
	private @NotNull PoweredToolItemStack getToolProvider(@NotNull ItemStack stack) 
	{
		return (PoweredToolItemStack)stack.getCapability(YATMCapabilities.CURRENT).orElse(null);
	} // end getToolProvider()
	
	
	
	public @NotNegative int actionCost(@NotNull ItemStack stack, ToolAction action) 
	{
		return (int)(((float)this.m_actionCost) / this.getEfficiency(stack));
	} // end getCostForAction()
	
	public @NotNegative int attackCost(@NotNull ItemStack stack, @NotNull LivingEntity target)
	{
		return (int)(Math.max(1, ((float)this.m_attackCost) / this.getEfficiency(stack)));
	} // end getCostForAction()
	
	public @NotNegative int mineCost(@NotNull ItemStack stack, @NotNull BlockState target)
	{
		return ((int)(Math.max(1, ((float)this.m_mineCost) / this.getEfficiency(stack)))) * (target.is(this.m_mineables) ? 1 : 2);
	} // end getCostForAction()
	
	
	
	public boolean hasPowerForAction(@NotNull ItemStack stack, ToolAction action) 
	{
		int c = this.getPower(stack);
		return c > 0 ? c > this.actionCost(stack, action) : false;
	} // end hasPowerForAction()
	
	public boolean hasPowerToAttack(@NotNull ItemStack stack, @NotNull LivingEntity target) 
	{
		int c = this.getPower(stack);
		return c > 0 ? c > this.attackCost(stack, target) : false;
	} // end hasPowerForAttacking()
	
	public boolean hasPowerToMine(@NotNull ItemStack stack, @NotNull BlockState target) 
	{
		int c = this.getPower(stack);
		return c > 0 ? c > this.mineCost(stack, target) : false;
	} // end hasPowerForMining()

	
	private @NotNull MenuProvider getMenuProvider(@NotNull ItemStack stack) 
	{
		PoweredToolItemStack p = this.getToolProvider(stack);
		return new SimpleMenuProvider((containerId, playerInv, player) -> new PoweredToolMenu(
				containerId,
				playerInv, 
				p.getInventory(), 
				p.getDataAccessor()),
		stack.getDisplayName());
	} // end getMenuProvider()
	

	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new PoweredToolItemStack(stack);
	} // end initCapabilities()
	
} // end class