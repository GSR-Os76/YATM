package com.gsr.gsr_yatm.item.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentRegulatorItemStack extends DamagableItemStack implements ICurrentHandler, ICapabilityProvider//, IDamageableRepairableItemStack
{
	private static final String FORWARDING_BUFFER_TAG_NAME = "fBuffer";

	private int m_targetCurrent;
	private int m_overloadThreshold;


	
	private LazyOptional<ICurrentHandler> m_currentHolder = LazyOptional.of(() -> this);


	public CurrentRegulatorItemStack(ItemStack container, int targetCurrent, int overloadThreshold)
	{
		super(container);
		
		this.m_targetCurrent = targetCurrent;
		this.m_overloadThreshold = overloadThreshold;
	} // end constructor



	private int getForwardingBuffer()
	{
		CompoundTag tag = this.m_container.getTag();
		if (tag == null || !tag.contains(FORWARDING_BUFFER_TAG_NAME))
		{
			return 0;
		}
		return tag.getInt(FORWARDING_BUFFER_TAG_NAME);
	} // end getForwardingBuffer()

	private void setForwardingBuffer(int value)
	{
		if (!m_container.hasTag())
		{
			m_container.setTag(new CompoundTag());
		}
		this.m_container.getTag().putInt(FORWARDING_BUFFER_TAG_NAME, value);
	} // end setForwardingBuffer()
	
	



	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		if (this.isDamaged())
		{
			return 0;
		}

		int recieveAmount = amount > m_overloadThreshold ? amount : Math.min(amount, this.m_targetCurrent);
		if (!simulate)
		{
			this.setForwardingBuffer(recieveAmount);
		}

		return recieveAmount;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		int returnAmount = Math.min(amount, this.getForwardingBuffer());
		if (!simulate)
		{
			this.setForwardingBuffer(this.getForwardingBuffer() - returnAmount);
			if (returnAmount > this.m_overloadThreshold)
			{
				this.damageComponent();
			}
		}

		return returnAmount;
	} // end sendCurrent()



	// TODO, consider changing around, could be forwarding buffer related
	@Override
	public int capacity()
	{
		return 0;
	} // end storageCapacity()

	@Override
	public int stored()
	{
		return 0;
	} // end storedCapacity()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if (!this.isDamaged())
		{
			if (cap == YATMCapabilities.CURRENT)
			{
				return this.m_currentHolder.cast();
			}
		}
		return LazyOptional.empty();
	} // end getCapability()

} // end class