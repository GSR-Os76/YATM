package com.gsr.gsr_yatm.utilities.network.container_data.implementation;

import net.minecraft.world.inventory.ContainerData;

public class BooleanFlagHandler
{
	private static final int S = 0b0000_0000_0000_0000_0000_0000_0000_0001;
	private int m_value = 0;
	// only those masked bits can be set though the container data;
	private int m_containerDataSettingMask = 0;
	
	private ContainerData m_data = new ContainerData() 
	{

		@Override
		public int get(int index)
		{
			return BooleanFlagHandler.this.m_value;
		} // end get()

		@Override
		public void set(int index, int value)
		{
			// TODO, unchecked
			BooleanFlagHandler.this.m_value = value & BooleanFlagHandler.this.m_containerDataSettingMask | (BooleanFlagHandler.this.m_value & ~BooleanFlagHandler.this.m_containerDataSettingMask);
		} // end set()

		@Override
		public int getCount()
		{
			return 1;
		} // end getCount()
	};

	
	
	public BooleanFlagHandler() 
	{
		
	}
	
	public BooleanFlagHandler(int value) 
	{
		this.m_value = value;
	} // end constructor
	
	public BooleanFlagHandler(ContainerData data) 
	{
		// TODO, implement
		this.m_data = data;
		//this.m_value = 
		this.m_containerDataSettingMask = 0b1111_1111_1111_1111_1111_1111_1111_1111;
	} // end constructor
	
	
	
	public void setValue(int index, boolean value) 
	{
		int mask = BooleanFlagHandler.getIndexMask(index);
		this.setValueM(mask, value);
	} // end setValue()
	
	public boolean getValue(int index) 
	{
		int mask = BooleanFlagHandler.getIndexMask(index);
		return (this.m_value & mask) == mask;
	} // end getValue()
	
	public int getValue() 
	{
		return this.m_value;
	} // end getValue()
	
	
	
	public ContainerData getData() 
	{
		return this.m_data;
	} // end getData()
	
	public void setContainerDataSettableMask(int mask) 
	{
		this.m_containerDataSettingMask = mask;
	} // end setContainerDataSettableMask()
	
	
	
	private void setValueM(int mask, boolean value) 
	{
		this.m_value = value ? this.m_value | mask : this.m_value & ~mask;
	} // end setValueM
	
	private static int getIndexMask(int index) 
	{
		return BooleanFlagHandler.S << index > 31 ? 31 : index < 0 ? 0 : index;
	} // end getIndexMask()
	
} // end class