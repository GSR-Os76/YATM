package com.gsr.gsr_yatm.block.device.solar;

import net.minecraft.nbt.CompoundTag;

public record SolarPanelSettings(int currentPerTick, float percentWithoutSky, float percentDuringDay, float percentDuringNight, float percentDuringPrecipitation)
{
	private static final String CURRENT_PER_TICK_TAG_NAME = "current";
	private static final String DAY_REDUCTION_TAG_NAME = "day";
	private static final String NIGHT_REDUCTION_TAG_NAME = "night";
	private static final String PRECIPITATION_REDUCTION_TAG_NAME = "precip";
	private static final String SKY_MISSING_REDUCTION_TAG_NAME = "sky";
	
	public static final SolarPanelSettings EMPTY = new SolarPanelSettings(0, 0.0f, 0.0f, 0.0f, 0.0f);
	public static final SolarPanelSettings CRUDE = new SolarPanelSettings(1, 0.0f, 1.0f, 0.0f, 0.0f);
	public static final SolarPanelSettings ADVANCED = new SolarPanelSettings(8, 0.0f, 1.0f, 0.0f, 0.5f);
	public static final SolarPanelSettings SUNS_COMPLEMENT = new SolarPanelSettings(64, 0.0f, 1.0f, 0.125f, 0.875f);

	
	
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		
		tag.putInt(CURRENT_PER_TICK_TAG_NAME, this.currentPerTick);		
		tag.putFloat(SKY_MISSING_REDUCTION_TAG_NAME, this.percentWithoutSky);
		tag.putFloat(DAY_REDUCTION_TAG_NAME, this.percentDuringDay);
		tag.putFloat(NIGHT_REDUCTION_TAG_NAME, this.percentDuringNight);
		tag.putFloat(PRECIPITATION_REDUCTION_TAG_NAME, this.percentDuringPrecipitation);
		
		return tag;
	} // end serializeNBT()
	
	public static SolarPanelSettings deserializeNBT(CompoundTag tag)
	{
		int currentPerTick = 0;
		float reductionWithoutSky = 0f;
		float reductionDuringDay = 0f;
		float reductionDuringNight = 0f;
		float reductionDuringPrecipitation = 0f;
		
		if(tag.contains(CURRENT_PER_TICK_TAG_NAME)) 
		{
			currentPerTick = tag.getInt(CURRENT_PER_TICK_TAG_NAME);
		}
		if(tag.contains(SKY_MISSING_REDUCTION_TAG_NAME)) 
		{
			reductionWithoutSky = tag.getFloat(SKY_MISSING_REDUCTION_TAG_NAME);
		}
		if(tag.contains(DAY_REDUCTION_TAG_NAME)) 
		{
			reductionDuringDay = tag.getFloat(DAY_REDUCTION_TAG_NAME);
		}
		if(tag.contains(NIGHT_REDUCTION_TAG_NAME)) 
		{
			reductionDuringNight = tag.getFloat(NIGHT_REDUCTION_TAG_NAME);
		}
		if(tag.contains(PRECIPITATION_REDUCTION_TAG_NAME)) 
		{
			reductionDuringPrecipitation = tag.getFloat(PRECIPITATION_REDUCTION_TAG_NAME);
		}
		return new SolarPanelSettings(currentPerTick, reductionWithoutSky, reductionDuringDay, reductionDuringNight, reductionDuringPrecipitation);
	} // end deserializeNBT

} // end record