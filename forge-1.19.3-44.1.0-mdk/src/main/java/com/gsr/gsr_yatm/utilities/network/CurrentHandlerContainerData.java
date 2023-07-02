package com.gsr.gsr_yatm.utilities.network;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

import net.minecraft.world.inventory.ContainerData;

public class CurrentHandlerContainerData implements ContainerData
{
	public static final int AMOUNT_INDEX = 0;
	public static final int CAPACITY_INDEX = 1;
	
	private final ICurrentHandler m_currentHandler;
	private final boolean m_allowSetting;
	
	
	
	public CurrentHandlerContainerData(ICurrentHandler currentHandler) 
	{
		this(currentHandler, false);
	} // end constructor
	
	public CurrentHandlerContainerData(ICurrentHandler currentHandler, boolean allowSetting) 
	{
		this.m_currentHandler = currentHandler;
		this.m_allowSetting = allowSetting;
	} // end constructor
	
	
	
	@Override
	public int get(int index)
	{
		return switch(index) 
		{
			case AMOUNT_INDEX -> this.m_currentHandler.stored();
			case CAPACITY_INDEX -> this.m_currentHandler.capacity();
			default -> throw new IllegalArgumentException("Unexpected value of: " + index);
		};
	} // end get()

	@Override
	public void set(int index, int value)
	{
		if(this.m_allowSetting) 
		{
			throw new IllegalArgumentException("not implemented yet");
		}
	} // end set()

	@Override
	public int getCount()
	{
		return 2;
	} // end getCount()
	
} // end class