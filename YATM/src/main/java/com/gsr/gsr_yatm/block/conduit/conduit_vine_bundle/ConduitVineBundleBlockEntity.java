package com.gsr.gsr_yatm.block.conduit.conduit_vine_bundle;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.conduit.IConduitBlockEntity;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.PeriodTracker;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class ConduitVineBundleBlockEntity extends BlockEntity implements IConduitBlockEntity
{
	private final @NotNull PeriodTracker m_recheckAttachmentsCounter = new PeriodTracker(YATMConfigs.CONDUIT_LIKE_RECHECK_ATTACHMENTS_PERIOD.get());
	
	private final Map<Direction, LazyOptional<ICurrentHandler>> m_neighborCaps = new EnumMap<>(Direction.class);
	private LazyOptional<ICurrentHandler> m_temp = LazyOptional.of(() -> new CurrentHandler(0));
	
	
	
	public ConduitVineBundleBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CONDUIT_VINE_BUNDLE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor
	
	

	@Override
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(this.m_recheckAttachmentsCounter.tick()) 
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
			LazyOptional<ICurrentHandler> cap = be == null ? null : be.getCapability(YATMCapabilities.CURRENT, face.getOpposite());
			AttachmentState intended = null;
			EnumProperty<AttachmentState> prop = ConduitVineBundleBlock.BRANCHES_BY_DIRECTION.get(face);
			if(cap != null && cap.isPresent()) 
			{
				if(cap != this.m_neighborCaps.get(face)) 
				{
					cap.addListener((la) -> this.removeAttachment(level, position, face, la));
					this.m_neighborCaps.put(face, cap);
					intended = level.getBlockState(neighborPos).is(YATMBlockTags.CONDUIT_NEUTRAL_ONLY_ATTACHMENT_KEY) ? AttachmentState.NEUTRAL : AttachmentState.PUSH;
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
	
	private void removeAttachment(@NotNull Level level, @NotNull BlockPos position, Direction face, LazyOptional<ICurrentHandler> la) 
	{
		if(this.isRemoved() || !level.isLoaded(position) || !this.getType().isValid(level.getBlockState(position))) 
		{
			return;
		}
		
		if(la == this.m_neighborCaps.get(face)) 
		{
			this.m_neighborCaps.put(face, null);
			BlockState b = level.getBlockState(position);
			EnumProperty<AttachmentState> prop = ConduitVineBundleBlock.BRANCHES_BY_DIRECTION.get(face);
			if(b.getValue(prop) != AttachmentState.NONE) 
			{
				level.setBlock(position, b.setValue(prop, AttachmentState.NONE), Block.UPDATE_CLIENTS);
			}
		}
	} // end tryRemoveAttachment()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
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
		this.m_temp = LazyOptional.of(() -> new CurrentHandler(0));
	} // end reviveCaps()
} // end class