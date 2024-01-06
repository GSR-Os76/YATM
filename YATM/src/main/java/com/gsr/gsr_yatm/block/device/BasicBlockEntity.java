package com.gsr.gsr_yatm.block.device;

import java.util.Objects;

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
	
	
	
	public BasicBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state)
	{
		super(Objects.requireNonNull(type), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor
	
	protected abstract @NotNull CompoundTag setupToNBT();
	protected abstract void setupFromNBT(@NotNull CompoundTag tag);
	
	
	
	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull BasicBlockEntity blockEntity)
	{
		if (level.isClientSide)
		{
			blockEntity.clientTick(level, position, state);
		}
		else
		{
			blockEntity.serverTick(level, position, state);
		}
	} // end tick()

	public void clientTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		
	} // end clientTick()
	
	public void serverTick(@NotNull Level level, @NotNull BlockPos position,  @NotNull BlockState state)
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