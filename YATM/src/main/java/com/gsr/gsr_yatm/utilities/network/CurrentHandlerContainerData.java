package com.gsr.gsr_yatm.utilities.network;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.inventory.ContainerData;

public class CurrentHandlerContainerData implements ContainerData
{
	public static final int SLOT_COUNT = 2;
	
	public static final int STORED_INDEX = 0;
	public static final int CAPACITY_INDEX = 1;
	
	private final @NotNull ICurrentHandler m_currentHandler;
	private final boolean m_allowSetting;
	
	
	
	public CurrentHandlerContainerData(@NotNull ICurrentHandler currentHandler) 
	{
		this(Objects.requireNonNull(currentHandler), false);
	} // end constructor
	
	public CurrentHandlerContainerData(@NotNull ICurrentHandler currentHandler, boolean allowSetting) 
	{
		this.m_currentHandler = Objects.requireNonNull(currentHandler);
		this.m_allowSetting = allowSetting;
	} // end constructor
	
	
	
	@Override
	public @NotNegative int get(@NotNegative int index)
	{
		return switch(index) 
		{
			case CurrentHandlerContainerData.STORED_INDEX -> this.m_currentHandler.stored();
			case CurrentHandlerContainerData.CAPACITY_INDEX -> this.m_currentHandler.capacity();
			default -> throw new IllegalArgumentException("Unexpected value of: " + index);
		};
	} // end get()

	@Override
	public void set(@NotNegative int index, @NotNegative int value)
	{
		if(this.m_allowSetting) 
		{
			throw new IllegalArgumentException("not implemented yet");
		}
	} // end set()

	@Override
	public @NotNegative int getCount()
	{
		return CurrentHandlerContainerData.SLOT_COUNT;
	} // end getCount()
	
} // end class