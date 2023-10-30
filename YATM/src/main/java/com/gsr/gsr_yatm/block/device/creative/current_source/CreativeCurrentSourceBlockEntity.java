package com.gsr.gsr_yatm.block.device.creative.current_source;

import java.util.EnumMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentSource;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class CreativeCurrentSourceBlockEntity extends BlockEntity
{
	private static final String OUTPUT_TAG_NAME = "current_output";
	
	private static final int RECHECK_ATTACHMENTS_PERIOD = 80;	
	private @NotNegative int m_recheckAttachmentsCounter = CreativeCurrentSourceBlockEntity.RECHECK_ATTACHMENTS_PERIOD;
	
	private CurrentSource m_source;
	private LazyOptional<ICurrentHandler> m_sourceCap;
	private LazyOptional<ICurrentHandler> m_sterileCap = SlotUtil.createSterileCapability(YATMCapabilities.CURRENT);
	private final Map<Direction, LazyOptional<ICurrentHandler>> m_neighborCaps = new EnumMap<>(Direction.class);
	
	
	
	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE.get(), position, state);
	} // end constructor()
	
	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state, int output)
	{
		super(YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE.get(), position, state);
		
		this.m_source = new CurrentSource(output);
		this.m_sourceCap = LazyOptional.of(() -> this.m_source);
	} // end constructor()

	
	
	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull CreativeCurrentSourceBlockEntity blockEntity)
	{
		if (!blockEntity.isRemoved() && !level.isClientSide)
		{
			blockEntity.serverTick(level, position, state);
		}
	} // end tick()
	
	public void serverTick(@NotNull Level level, @NotNull BlockPos position,  @NotNull BlockState state)
	{
		if(++this.m_recheckAttachmentsCounter >= CreativeCurrentSourceBlockEntity.RECHECK_ATTACHMENTS_PERIOD) 
		{
			this.recheckNeighborAttachments(level, position, state);
			this.m_recheckAttachmentsCounter = 0;
		}
		
		for(Direction d : Direction.values()) 
		{
			if(this.m_source != null && this.m_neighborCaps.containsKey(d) && state.getValue(CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(d)) == AttachmentState.PUSH) 
			{
				ICurrentHandler n = this.m_neighborCaps.get(d).orElse((ICurrentHandler)null);
				n.recieveCurrent(this.m_source.extractCurrent(n.recieveCurrent(this.m_source.extractCurrent(Integer.MAX_VALUE, true), true), false), false);
			}
		}			
	} // end serverTick()
	
	public void recheckNeighborAttachments(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) 
	{
		if(!this.getType().isValid(level.getBlockState(position))) 
		{
			return;
		}
		
		for(Direction face : Direction.values())
		{
			BlockPos neighborPos = position.relative(face);
			if(!level.isLoaded(neighborPos)) 
			{
				this.m_neighborCaps.put(face, null);
				continue;
			}				
			BlockEntity be = level.getBlockEntity(neighborPos);
			LazyOptional<ICurrentHandler> cap = be == null ? null : be.getCapability(YATMCapabilities.CURRENT, face.getOpposite());
			if(cap != null && cap.isPresent()) 
			{
				if(cap != this.m_neighborCaps.get(face)) 
				{
					cap.addListener((la) -> this.removeAttachment(level, position, face, la));
					this.m_neighborCaps.put(face, cap);
				}
			}
		}
	} // end recheckDrainAttachment()
	
	private void removeAttachment(@NotNull Level level, @NotNull BlockPos position, Direction face, LazyOptional<ICurrentHandler> la) 
	{
		BlockState b = level.getBlockState(position);
		if(this.isRemoved() || !level.isLoaded(position) || !this.getType().isValid(b)) 
		{
			return;
		}
		
		if(la == this.m_neighborCaps.get(face)) 
		{
			this.m_neighborCaps.put(face, null);
			EnumProperty<AttachmentState> prop = CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(face);
			if(b.getValue(prop) != AttachmentState.NONE) 
			{
				level.setBlock(position, b.setValue(prop, AttachmentState.NONE), Block.UPDATE_CLIENTS);
			}
		}
	} // end tryRemoveAttachment()
	/*
	@Override
	public @NotNull AttachmentState cycleAttachment(@NotNull AttachmentState attachmentState, @NotNull Direction side) 
	{
		return switch(attachmentState) 
		{
			case NONE -> AttachmentState.NEUTRAL;
			case NEUTRAL -> AttachmentState.PUSH;
			case PULL -> AttachmentState.NONE;
			case PUSH -> AttachmentState.NONE;
			default -> throw new IllegalArgumentException("Unexpected value of: " + attachmentState);
		};
	} // end cycleAttachment()
	*/
	
	
	@Override
	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		if(this.m_source != null) 
		{	
			tag.putInt(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME, this.m_source.capacity());
		}
	} // end saveAdditional()
	
	@Override
	public void load(@NotNull CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME)) 
		{
			if(this.m_source == null) 
			{
				this.m_source = new CurrentSource();
			}
			this.m_source.setOutput(tag.getInt(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME));
			this.m_sourceCap = LazyOptional.of(() -> this.m_source);
		}
	} // end load()

	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
		{
			if(side == null) 
			{
				return this.m_sourceCap.cast();				
			}
			else 
			{
				AttachmentState as = this.getBlockState().getValue(CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(side));
				if(as != AttachmentState.NONE) 
				{
					return (as == AttachmentState.NEUTRAL ? this.m_sourceCap : this.m_sterileCap).cast();
				}
			}
		}
		return super.getCapability(cap, side);
	} // end getCapability()
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();		
		this.m_sourceCap.invalidate();
		this.m_sterileCap.invalidate();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_sourceCap = LazyOptional.of(() -> this.m_source);
		this.m_sterileCap = SlotUtil.createSterileCapability(YATMCapabilities.CURRENT);
	} // end reviveCaps()
	
} // end class