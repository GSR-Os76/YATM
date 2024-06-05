package com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.utility.NeighborCapabilityAttacher;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class OutputFluidManager implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()
	
	private final @NotNull NeighborCapabilityAttacher m_attacher;
	private final @NotNull IFluidHandler m_from;
	private final @NotNegative int m_maxTransfer;
	
	
	public OutputFluidManager(@NotNull Supplier<@NotNull List<@NotNull Direction>> attachmentDirections, @NotNegative int recheckPeriod, @NotNull IFluidHandler from, @NotNegative int maxTransfer) 
	{
		List<Capability<?>> l = List.of(ForgeCapabilities.FLUID_HANDLER);
		this.m_attacher = new NeighborCapabilityAttacher(attachmentDirections, () -> l, recheckPeriod);
		this.m_from = Objects.requireNonNull(from);
		this.m_maxTransfer = Contract.notNegative(maxTransfer);
	} // end constructor
	
	
	
	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position) 
	{
		this.m_attacher.tick(level, position);
		if(this.m_attacher.getAttachments().size() > 0) 
		{
			int distibution = this.m_from.getFluidInTank(0).getAmount() / this.m_attacher.getAttachments().size();
			this.m_attacher.getAttachments().stream()
			.map((f) -> (IFluidHandler)f)
			.forEach((f) -> f.fill(this.m_from.drain(f.fill(this.m_from.drain(new FluidStack(this.m_from.getFluidInTank(0), Math.min(distibution, this.m_maxTransfer)), FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE));
		}
		return false;
	} // end tick()
	
} // end class