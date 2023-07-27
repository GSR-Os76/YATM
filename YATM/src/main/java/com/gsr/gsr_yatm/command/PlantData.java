package com.gsr.gsr_yatm.command;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

public class PlantData extends SavedData
{
	public static final String UNBOUND_HORIZONTAL_GROWTH_KEY = "unboundHorizontalGrowth";

	private boolean m_unboundHorizontalGrowth = false;
	
	
	
	@Override
	public CompoundTag save(CompoundTag tag)
	{
		tag.putBoolean(PlantData.UNBOUND_HORIZONTAL_GROWTH_KEY, this.m_unboundHorizontalGrowth);
		return tag;
	} // end save()

	
	
	public @NotNull PlantData unboundHorizontalGrow(boolean value) 
	{
		boolean changed = this.m_unboundHorizontalGrowth != value;
		if(changed) 
		{
			this.m_unboundHorizontalGrowth = value;
			this.setDirty();
		}
		return this;
	} // end unboundHorizontalGrow()
	
	public boolean isHorizontalGrowthBound() 
	{
		return this.m_unboundHorizontalGrowth;
	} // end isHorizontalGrowthBound()


	
	public static boolean isHorizontalGrowthUnbound(ServerLevel level) 
	{
		return level.getDataStorage().computeIfAbsent((t) -> PlantData.from(t), () -> new PlantData(), YATMRuleCommand.PLANT_DATA_SAVE_KEY).isHorizontalGrowthBound();
	} // end isHorizontalGrowthUnbound()

	
	
	public static @NotNull PlantData from(@NotNull CompoundTag t)
	{
		PlantData p = new PlantData();
		if(t.contains(PlantData.UNBOUND_HORIZONTAL_GROWTH_KEY)) 
		{
			p.m_unboundHorizontalGrowth = t.getBoolean(PlantData.UNBOUND_HORIZONTAL_GROWTH_KEY);
		}
		return p;
	} // end from()
	
} // end class