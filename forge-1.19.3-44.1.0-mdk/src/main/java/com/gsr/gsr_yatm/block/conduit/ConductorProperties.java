package com.gsr.gsr_yatm.block.conduit;

public class ConductorProperties
{
	public static final ConductorProperties EMPTY = new ConductorProperties();
	
	public static final ConductorProperties ONE_CU_WIRE_CONDUCTOR_PROPERTIES = (new ConductorProperties.Builder()).setOverheatPastCurrent(1).setExplosionPastCurrent(2).setOverheatTemperature(0).setExplosionTemperature(0).build();
	public static final ConductorProperties EIGHT_CU_WIRE_CONDUCTOR_PROPERTIES = (new ConductorProperties.Builder()).setOverheatPastCurrent(8).setExplosionPastCurrent(16).setOverheatTemperature(0).setExplosionTemperature(0).build();
	public static final ConductorProperties SIXTYFOUR_CU_WIRE_CONDUCTOR_PROPERTIES = (new ConductorProperties.Builder()).setOverheatPastCurrent(64).setExplosionPastCurrent(128).setOverheatTemperature(0).setExplosionTemperature(0).build();
	public static final ConductorProperties FIVEHUNDREDTWELVE_CU_WIRE_CONDUCTOR_PROPERTIES = (new ConductorProperties.Builder()).setOverheatPastCurrent(512).setExplosionPastCurrent(1024).setOverheatTemperature(0).setExplosionTemperature(0).build();
	public static final ConductorProperties FOURTHOUSANDNINTYSIX_CU_WIRE_CONDUCTOR_PROPERTIES = (new ConductorProperties.Builder()).setOverheatPastCurrent(4096).setExplosionPastCurrent(8192).setOverheatTemperature(0).setExplosionTemperature(0).build();
	
	
	private int m_superConductingCriticalPoint = -1;
	private int m_superConductingBreakDownCurrent = Integer.MAX_VALUE;
	
	private int m_overheatsPastCurrent = 0;
	private int m_explodesPastCurrent = 0;
	private int m_temperatureAtOverheat = 0;
	private int m_temperatureAtExplosion = 0;
	
	
	
	private ConductorProperties() 
	{
		
	}
	
	
	
	public int getSuperConductingCriticalTemperature() 
	{
		return this.m_superConductingCriticalPoint;
	} // end getSuperConductingCriticalTemperature()
	
	public int getSuperConductingBreakDownPastCurrent() 
	{
		return this.m_superConductingBreakDownCurrent;
	} // end getSuperConductingBreakDownCurrent()
	
	
	
	public int getOverheatsPastCurrent() 
	{
		return this.m_overheatsPastCurrent;
	} // end getOverheatsAtCurrent()
	
	public int getExplodesPastCurrent() 
	{
		return this.m_explodesPastCurrent;
	} // end getExplodesAtCurrent()
	
	public int getTemperatureAtOverheat() 
	{
		return this.m_temperatureAtOverheat;
	} // end getTemperatureAtOverheat()
	
	public int getTemperatureAtExplosion() 
	{
		return this.m_temperatureAtExplosion;
	} // end getTemperatureAtExplosion()

	public static class Builder
	{
		private ConductorProperties m_building;
		
		
		
		public Builder() 
		{
			this.m_building = new ConductorProperties();
		} // end constructor
		
		public Builder(ConductorProperties conductorProperties) 
		{
			this.m_building = new ConductorProperties();
			this.copyPropertiesFrom(conductorProperties);
		} // end constructor
		
		
		
		public Builder setSuperConductingCriticalPoint(int current) 
		{
			this.m_building.m_superConductingCriticalPoint = current;	
			return this;
		} // end setSuperConductingCriticalPoint()
		
		public Builder setSuperConductorBreakDownCurrent(int current) 
		{
			this.m_building.m_superConductingBreakDownCurrent = current;	
			return this;
		} // end setSuperConductorBreakDownCurrent()
		
		
		
		
		public Builder setOverheatPastCurrent(int current) 
		{
			this.m_building.m_overheatsPastCurrent = current;	
			return this;
		} // end setOverheatCurrent()
		
		public Builder setExplosionPastCurrent(int current) 
		{
			this.m_building.m_explodesPastCurrent = current;	
			return this;
		} // end setExplosionCurrent()

		public Builder setOverheatTemperature(int temperature) 
		{
			this.m_building.m_temperatureAtOverheat = temperature;	
			return this;
		} // end setOverheatTemperature()
		
		public Builder setExplosionTemperature(int temperature) 
		{
			this.m_building.m_temperatureAtExplosion = temperature;	
			return this;
		} // end setExplosionTemperature()
		
		
		
		public ConductorProperties build() 
		{
			ConductorProperties l = this.m_building;
			this.m_building = new ConductorProperties();
			this.copyPropertiesFrom(l);
			return l;
		} // end build()
		
		private void copyPropertiesFrom(ConductorProperties cp) 
		{
			this.m_building.m_superConductingCriticalPoint = cp.m_superConductingCriticalPoint;
			this.m_building.m_superConductingBreakDownCurrent = cp.m_superConductingBreakDownCurrent;
			this.m_building.m_overheatsPastCurrent = cp.m_overheatsPastCurrent;
			this.m_building.m_explodesPastCurrent = cp.m_explodesPastCurrent;
			this.m_building.m_temperatureAtOverheat = cp.m_temperatureAtOverheat;
			this.m_building.m_temperatureAtExplosion = cp.m_temperatureAtExplosion;
		}
		
	} // end inner class
} // end outer class