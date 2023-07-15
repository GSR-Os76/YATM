package com.gsr.gsr_yatm.block.device.compute.storage;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.DataPacket;
import com.gsr.gsr_yatm.api.capability.IDataHandler;
import com.gsr.gsr_yatm.block.device.BasicBlockEntity;
import com.gsr.gsr_yatm.block.device.compute.IDataProcessingTicker;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

public class DataStorageBlockEntity extends BasicBlockEntity
{
	public static final int REQUEST_LOAD_DELAY = 20;

	public static final String DATA_STORAGE_KEY = "data";
	
	private IDataProcessingTicker m_dataTicker;
	private final StorageDataHandler m_dataHandler = new StorageDataHandler((d) -> this.m_dataTicker = d, REQUEST_LOAD_DELAY, REQUEST_LOAD_DELAY * 2);
	private final LazyOptional<IDataHandler> m_cap = LazyOptional.of(() -> this.m_dataHandler);
	
	
	
	public DataStorageBlockEntity(BlockPos position, BlockState state)
	{
		super(YATMBlockEntityTypes.DATA_STORAGE.get(), position, state);
	} // end constructor

	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		return new CompoundTag();
	}

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		
	}
	

	
	@Override
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		this.broadcastPackets(this.m_dataTicker.process(level, pos, blockState));
	} // end serverTick()

	protected void broadcastPackets(@Nullable List<DataPacket> packets) 
	{
		
	}
	
	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.put(DATA_STORAGE_KEY, this.m_dataHandler.serializeNBT());
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(DATA_STORAGE_KEY)) 
		{
			this.m_dataHandler.deserializeNBT(tag.getCompound(DATA_STORAGE_KEY));
		}
	} // end load()

	
	
	
	
} // end class