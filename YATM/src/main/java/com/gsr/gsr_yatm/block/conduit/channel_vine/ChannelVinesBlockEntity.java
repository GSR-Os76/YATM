package com.gsr.gsr_yatm.block.conduit.channel_vine;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ChannelVinesBlockEntity extends BlockEntity
{
	private static final int RECHECK_ATTACHMENTS_PERIOD = 80;	
	private @NotNegative int m_recheckAttachmentsCounter = ChannelVinesBlockEntity.RECHECK_ATTACHMENTS_PERIOD;
	
	private final Map<Direction, LazyOptional<IFluidHandler>> m_neighborCaps = new EnumMap<>(Direction.class);
	
	private LazyOptional<IFluidHandler> m_temp = LazyOptional.of(() -> new FluidTank(0));
	/*
	public ChannelVinesBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		this(Objects.requireNonNull(position), Objects.requireNonNull(state), DeviceTierConstants.EMPTY);
	} // end constuctor
	*/
	public ChannelVinesBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)//, @NotNull DeviceTierConstants constants)
	{
		super(YATMBlockEntityTypes.CHANNEL_VINES.get(), Objects.requireNonNull(blockPos), Objects.requireNonNull(blockState));
	} // end constructor
	
	

	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull ChannelVinesBlockEntity blockEntity)
	{
		if(!blockEntity.isRemoved() && level.isLoaded(position)) 
		{
			if (level.isClientSide)
			{
				blockEntity.clientTick(level, position, state);
			}
			else
			{
				blockEntity.serverTick(level, position, state);
			}
		}
	} // end tick()

	public void clientTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		
	} // end clientTick()
	
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(++this.m_recheckAttachmentsCounter >= ChannelVinesBlockEntity.RECHECK_ATTACHMENTS_PERIOD) 
		{
			this.recheckNeighborAttachments(level, position, state);
		}
			
	} // end serverTick()
	
	public void recheckNeighborAttachments(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) 
	{
		if(!this.getType().isValid(level.getBlockState(position))) 
		{
			return;
		}
		
		BlockState toSet = null;
		for(Direction face : Direction.values())
		{
			BlockPos neighborPos = position.relative(face);
			// TODO, might be unnecessary to check with this, or might not
			if(!level.isLoaded(neighborPos)) 
			{
				this.m_neighborCaps.put(face, null);
				continue;
			}				
			BlockEntity be = level.getBlockEntity(neighborPos);
			LazyOptional<IFluidHandler> cap = be == null ? null : be.getCapability(ForgeCapabilities.FLUID_HANDLER, face.getOpposite());
			AttachmentState intended = null;
			EnumProperty<AttachmentState> prop = ChannelVineBlock.BRANCHES_BY_DIRECTION.get(face);
			if(cap != null && cap.isPresent()) 
			{
				if(cap != this.m_neighborCaps.get(face)) 
				{
					cap.addListener((la) -> this.removeAttachment(level, position, face, la));
					this.m_neighborCaps.put(face, cap);
					intended = level.getBlockState(neighborPos).is(YATMBlockTags.CHANNEL_NEUTRAL_ONLY_ATTACHMENT_KEY) ? AttachmentState.NEUTRAL : AttachmentState.PUSH;
				}
			}
			else 
			{
				intended = AttachmentState.NONE;				
			}			
			// update state if is necessary
			if(intended != null && state.getValue(prop) != intended) 
			{
				toSet = (toSet == null ? state : toSet).setValue(prop, intended);				
			}
		}
		if(toSet != null) 
		{
			level.setBlock(position, toSet, Block.UPDATE_CLIENTS);
		}
	} // end recheckDrainAttachment()
	
	private void removeAttachment(@NotNull Level level, @NotNull BlockPos position, Direction face, LazyOptional<IFluidHandler> la) 
	{
		if(this.isRemoved() || !level.isLoaded(position) || !this.getType().isValid(level.getBlockState(position))) 
		{
			return;
		}
		
		if(la == this.m_neighborCaps.get(face)) 
		{
			this.m_neighborCaps.put(face, null);
			BlockState b = level.getBlockState(position);
			EnumProperty<AttachmentState> prop = ChannelVineBlock.BRANCHES_BY_DIRECTION.get(face);
			if(b.getValue(prop) != AttachmentState.NONE) 
			{
				level.setBlock(position, b.setValue(prop, AttachmentState.NONE), Block.UPDATE_CLIENTS);
			}
		}
	} // end tryRemoveAttachment()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == ForgeCapabilities.FLUID_HANDLER) 
		{
			return this.m_temp.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_temp.invalidate();
	} // end invalidateCaps()



	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_temp = LazyOptional.of(() -> new FluidTank(0));
	} // end reviveCaps()
} // end class