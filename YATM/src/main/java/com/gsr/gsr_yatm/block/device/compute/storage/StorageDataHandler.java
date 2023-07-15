package com.gsr.gsr_yatm.block.device.compute.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.api.Task;
import com.gsr.gsr_yatm.api.capability.DataPacket;
import com.gsr.gsr_yatm.api.capability.IDataHandler;
import com.gsr.gsr_yatm.block.device.compute.IDataProcessingTicker;
import com.gsr.gsr_yatm.utilities.tag.TagUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

public class StorageDataHandler implements IDataHandler, INBTSerializable<CompoundTag>
{
	public static final String STORAGE_ADDRESS_KEY = "storageAddress";
	
	public static final String READ_DELAY_TAG_NAME = "readDelay";
	public static final String WRITE_DELAY_TAG_NAME = "writeDelay";
	public static final String TASKS_COUNT_TAG_NAME = "taskCount";
	public static final String DATA_COUNT_TAG_NAME = "dataCount";
	public static final String TASKS_TAG_NAME = "task";
	public static final String DATA_TAG_NAME = "data";
	
	
	private final List<CompoundTag> m_data = new ArrayList<>();
	private final Queue<Task> m_tasks = new LinkedList<>();

	private int m_readDelay;
	private int m_writeDelay;
	
	
	
	public StorageDataHandler(Consumer<IDataProcessingTicker> processingTickerAccepter, int readDelay, int writeDelay)
	{
		processingTickerAccepter.accept(this::processTasks);
		this.m_readDelay = readDelay;
		this.m_writeDelay = writeDelay;
	} // end constructor
	
	

	@Override
	public @Nullable String getAddress()
	{
		return "storage";
	} // end getAddress()

	@Override
	public void receive(@NotNull DataPacket packet)
	{
		if(packet.data().contains(STORAGE_ADDRESS_KEY)) 
		{	
			this.m_tasks.add(new Task(packet, packet.isRequest() ? this.m_readDelay : this.m_writeDelay));
		}
	} // end receive()

	@Override
	public boolean isReciever()
	{
		return true;
	} // end isReciever()

	
	
	private @Nullable List<DataPacket> processTasks(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(this.m_tasks.size() > 0 && this.m_tasks.peek().shrinkTime() <= 0) 
		{
			List<DataPacket> p =  this.processTask(this.m_tasks.poll().data());
			return (p == null || p.isEmpty()) ? null: ImmutableList.copyOf(p);
		}
		return null	;
	} // end processTasks()

	private @Nullable List<DataPacket> processTask(DataPacket task) 
	{
		String tskAddr = task.data().getString(STORAGE_ADDRESS_KEY);
		if(task.isRequest()) 
		{
			String recipient = task.getReturnAddress();
			List<DataPacket> returns = new ArrayList<>();
			
			int lookAtIndex = 0;
			for(int i = 0; i < this.m_data.size(); i++) 
			{
				CompoundTag t = this.m_data.get(lookAtIndex);
				if(t.getString(STORAGE_ADDRESS_KEY).equals(tskAddr)) 
				{
					this.m_data.remove(lookAtIndex);
					lookAtIndex--;
					t.remove(STORAGE_ADDRESS_KEY);
					returns.add(new DataPacket(this.getAddress(), recipient, t));
				}
				lookAtIndex++;
			}
			return returns.isEmpty() ? null : returns;
		}
		else 
		{
			this.m_data.add(task.data());
			return null;
		}
	} // end processTask()
	
	
	
	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag tag = new CompoundTag();
		
		tag.putInt(READ_DELAY_TAG_NAME, this.m_readDelay);
		tag.putInt(WRITE_DELAY_TAG_NAME, this.m_writeDelay);

		tag.put(TASKS_TAG_NAME, TagUtilities.serializeCollection(this.m_tasks, (t) -> t.serializeNBT()));
		tag.put(DATA_TAG_NAME, TagUtilities.serializeCollection(this.m_data));

		return tag;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(CompoundTag tag)
	{
		if(tag.contains(READ_DELAY_TAG_NAME)) 
		{
			this.m_readDelay = tag.getInt(READ_DELAY_TAG_NAME);
		}
		if(tag.contains(WRITE_DELAY_TAG_NAME)) 
		{
			this.m_writeDelay = tag.getInt(WRITE_DELAY_TAG_NAME);
		}
		if(tag.contains(TASKS_TAG_NAME)) 
		{
			this.m_data.clear();
			TagUtilities.deserializeCollection(tag.getCompound(TASKS_TAG_NAME), this.m_tasks, (t) -> Task.deserializeNBT(t));
		}
		if(tag.contains(DATA_TAG_NAME)) 
		{
			this.m_data.clear();
			TagUtilities.deserializeCollection(tag.getCompound(DATA_TAG_NAME), this.m_data);
		}
	} // end deserializeNBT()
	
	
	
	

} // end class