package com.gsr.gsr_yatm.block.device.behaviors.implementation.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.PeriodTracker;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class NeighborCapabilityAttacher implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()

	protected final @NotNull Supplier<@NotNull List<@NotNull Direction>> m_attachmentDirections;
	protected final @NotNull Supplier<@NotNull List<@NotNull Capability<?>>> m_searchFor;
	protected final @NotNull PeriodTracker m_recheckCounter;

	private @NotNull List<LazyOptional<?>> m_attachments = new ArrayList<>();



	public NeighborCapabilityAttacher(@NotNull Supplier<@NotNull List<@NotNull Direction>> attachmentDirections, @NotNull Supplier<@NotNull List<@NotNull Capability<?>>> searchFor, @NotNegative int recheckPeriod)
	{
		this.m_attachmentDirections = Objects.requireNonNull(attachmentDirections);
		this.m_searchFor = Objects.requireNonNull(searchFor);
		this.m_recheckCounter = new PeriodTracker(Contract.notNegative(recheckPeriod), 0);
	} // end constructor



	public List<?> getAttachments()
	{
		return this.m_attachments.stream().map((l) -> l.orElse(null)).toList();
	} // end getAttachments()



	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		if (this.m_recheckCounter.tick())
		{
			this.m_attachmentDirections.get().forEach((d) -> this.tryAttach(level, position, d));
		}
		return false;
	} // end tick()



	public void tryAttach(@NotNull Level level, @NotNull BlockPos position, @NotNull Direction neighbor)
	{
		BlockEntity be = level.getBlockEntity(position.relative(neighbor));
		if (be == null)
		{
			return;
		}

		for (Capability<?> cap : this.m_searchFor.get())
		{
			this.captureAttachment(be, cap, neighbor.getOpposite());
		}
	} // end tryAttach()

	private <T> void captureAttachment(@NotNull BlockEntity be, @NotNull Capability<T> cap, @NotNull Direction face)
	{
		LazyOptional<T> l = be.getCapability(cap, face);
		if (l.isPresent() && !this.m_attachments.contains(l))
		{
			this.addAttachment(cap, l);
		}
	} // end captureAttachment()

	private <T> void addAttachment(@NotNull Capability<T> cap, @NotNull LazyOptional<T> lazyOptional)
	{
		this.m_attachments.add(lazyOptional);
		lazyOptional.addListener(this::removeInvalidatedAttachment);
	} // end addAttachment()



	private void removeInvalidatedAttachment(@NotNull LazyOptional<?> lazyOptional)
	{
		if (this.m_attachments.contains(lazyOptional))
		{
			this.m_attachments.remove(lazyOptional);
		}
	} // end removeInvalidatedAttachment()

} // end class