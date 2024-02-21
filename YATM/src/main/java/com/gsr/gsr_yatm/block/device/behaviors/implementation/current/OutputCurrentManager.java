package com.gsr.gsr_yatm.block.device.behaviors.implementation.current;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.utility.NeighborCapabilityAttacher;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;

public class OutputCurrentManager implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()
	
	private final @NotNull NeighborCapabilityAttacher m_attacher;
	private final @NotNull ICurrentHandler m_from;
	private final @NotNegative int m_maxCurrentTransfer;
	
	
	public OutputCurrentManager(@NotNull Supplier<@NotNull List<@NotNull Direction>> attachmentDirections, @NotNegative int recheckPeriod, @NotNull ICurrentHandler from, @NotNegative int maxCurrentTransfer) 
	{
		List<Capability<?>> l = List.of(YATMCapabilities.CURRENT);
		this.m_attacher = new NeighborCapabilityAttacher(attachmentDirections, () -> l, recheckPeriod);
		this.m_from = Objects.requireNonNull(from);
		this.m_maxCurrentTransfer = Contract.notNegative(maxCurrentTransfer);
	} // end constructor
	
	
	
	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position) 
	{
		this.m_attacher.tick(level, position);
		if(this.m_attacher.getAttachments().size() > 0) 
		{
			int distibution = this.m_from.stored() / this.m_attacher.getAttachments().size();
			this.m_attacher.getAttachments().stream().map((c) -> (ICurrentHandler)c).forEach((c) -> c.receiveCurrent(this.m_from.extractCurrent(c.receiveCurrent(this.m_from.extractCurrent(Math.min(distibution, this.m_maxCurrentTransfer), true), true), false), false));
		}
		return false;
	} // end tick()
	
} // end class