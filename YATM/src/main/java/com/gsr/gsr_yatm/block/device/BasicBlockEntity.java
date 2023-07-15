package com.gsr.gsr_yatm.block.device;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BasicBlockEntity extends BlockEntity 
{	
	public static final String SETUP_TAG_NAME = "setup";
	
	
	
	public BasicBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState)
	{
		super(type, blockPos, blockState);
	} // end constructor
	
	protected abstract @NotNull CompoundTag setupToNBT();
	protected abstract void setupFromNBT(@NotNull CompoundTag tag);
	
	
	
	
	
	public static void tick(Level level, BlockPos pos, BlockState blockState, BasicBlockEntity blockEntity)
	{
		if (level.isClientSide)
		{
			blockEntity.clientTick(level, pos, blockState);
		}
		else
		{
			blockEntity.serverTick(level, pos, blockState);
		}
	} // end tick()

	public void clientTick(Level level, BlockPos pos, BlockState blockState)
	{
		
	} // end clientTick()
	
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{

	} // end serverTick()
	
	
	
	
	
	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.put(BasicBlockEntity.SETUP_TAG_NAME, this.setupToNBT());
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(BasicBlockEntity.SETUP_TAG_NAME)) 
		{
			this.setupFromNBT(tag.getCompound(BasicBlockEntity.SETUP_TAG_NAME));
		}
	} // end load()
	
} // end class