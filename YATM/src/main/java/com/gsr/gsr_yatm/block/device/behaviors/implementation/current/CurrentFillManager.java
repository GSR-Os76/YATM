package com.gsr.gsr_yatm.block.device.behaviors.implementation.current;

import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class CurrentFillManager implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()
	
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull ICurrentHandler m_currentHandler;
	private final @NotNegative int m_maxTransfer;
	
	
	
	public CurrentFillManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull ICurrentHandler currentHandler, @NotNegative int maxTransfer) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		this.m_currentHandler = Objects.requireNonNull(currentHandler);
		this.m_maxTransfer = Contract.notNegative(maxTransfer);
	} // end constructor


	
	// TODO, note, maybe make better compatible with slot min size > than m_maxTransfer, like what we do for draining and filling liquid
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		ICurrentHandler slot = this.m_inventory.getStackInSlot(this.m_slot).getCapability(YATMCapabilities.CURRENT).orElse((ICurrentHandler)null);
		if(slot != null) 
		{
			return this.m_currentHandler.receiveCurrent(slot.extractCurrent(this.m_currentHandler.receiveCurrent(slot.extractCurrent(this.m_maxTransfer, true), true), false), false) > 0;
		}
		return false;
	} // end tick()
	
} // end class