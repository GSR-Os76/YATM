package com.gsr.gsr_yatm.api;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.DataPacket;

import net.minecraft.nbt.CompoundTag;

public class Task
{
	public static final String DATA_TAG_NAME = "data";
	public static final String TIME_TAG_NAME = "time";
	
	public static final String TASKS_COUNT_TAG_NAME = "taskCount";
	
	private final DataPacket m_data;
	private int m_processingTime;
	
	
	
	public Task(@NotNull DataPacket data, int processingTime)
	{
		this.m_data = data;
		this.m_processingTime = processingTime;
	} // end constructor()



	public @NotNull DataPacket data()
	{
		return this.m_data;
	} // end getData

	public int shrinkTime()
	{
		return --this.m_processingTime;
	} // end shrinkTime()
	
	
	
	public CompoundTag serializeNBT()
	{
		CompoundTag res = new CompoundTag();
		res.put(DATA_TAG_NAME, this.m_data.serializeNBT());
		res.putInt(TIME_TAG_NAME, this.m_processingTime);
		return res;
	} // end serializeNBT()
	
	
	public static @Nullable Task deserializeNBT(@NotNull CompoundTag tag)
	{
		DataPacket dp = null;
		if(tag.contains(DATA_TAG_NAME)) 
		{
			dp = DataPacket.deserializeNBT(tag.getCompound(DATA_TAG_NAME));
		}
		if(dp != null && tag.contains(TIME_TAG_NAME)) 
		{
			return new Task(dp, tag.getInt(TIME_TAG_NAME));
		}
		return null;
	} // end deserializeNBT()
	
	
	
	// use TagUtility version
	@Deprecated()
	public static @NotNull CompoundTag serializeCollection(@NotNull Collection<Task> tasks)
	{
		CompoundTag res = new CompoundTag();
		res.putInt(TASKS_COUNT_TAG_NAME, tasks.size());
		
		Integer i = 0;
		for(Task task : tasks) 
		{
			res.put(i.toString(), task.serializeNBT());
			i++;
		}
		return res;
	} // end serializeCollection()
	
	// use TagUtility version
	@Deprecated()
	public static<T extends Collection<Task>> @NotNull T deserializeCollection(@NotNull CompoundTag tag, @NotNull T toFill) 
	{
		for(Integer i = 0; i < tag.getInt(TASKS_COUNT_TAG_NAME); i++) 
		{
			toFill.add(Task.deserializeNBT(tag.getCompound(i.toString())));
		}
		return toFill;
	} // end deserializeCollection()
	
} // end inner class