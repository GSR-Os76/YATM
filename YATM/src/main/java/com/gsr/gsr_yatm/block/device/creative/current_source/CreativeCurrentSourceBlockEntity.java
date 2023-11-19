package com.gsr.gsr_yatm.block.device.creative.current_source;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentSource;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.PropertyContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CreativeCurrentSourceBlockEntity extends BlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 1;
	public static final int CHARGE_SLOT = 0;
	
	public static final String OUTPUT_SPEC_KEY = "output";
	
	
	public static final String INVENTORY_TAG_NAME = "inventory";
	private static final String OUTPUT_TAG_NAME = "current_output";
	
	private static final int RECHECK_ATTACHMENTS_PERIOD = 80;	
	private @NotNegative int m_recheckAttachmentsCounter = CreativeCurrentSourceBlockEntity.RECHECK_ATTACHMENTS_PERIOD;
	
	private final CurrentSource m_source;
	private LazyOptional<ICurrentHandler> m_sourceCap;
	private LazyOptional<ICurrentHandler> m_sterileCap = SlotUtil.createSterileCapability(YATMCapabilities.CURRENT);
	private final Map<Direction, LazyOptional<ICurrentHandler>> m_neighborCaps = new EnumMap<>(Direction.class);
	
	private final ItemStackHandler m_rawInventory;
	private final InventoryWrapper m_inventory;
	 
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CreativeCurrentSourceBlockEntity.OUTPUT_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
			));
	protected final @NotNull ContainerData m_data;
	
	
	
	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
		this.m_rawInventory = new ItemStackHandler(CreativeCurrentSourceBlockEntity.INVENTORY_SLOT_COUNT);
		this.m_inventory = InventoryWrapper.Builder.of(this.m_rawInventory)
				.onInsertion((s, is) -> this.setChanged())
				.onWithdrawal((s, is) -> this.setChanged())
				.slotValidator((s, is, sim) -> SlotUtil.isValidPowerSlotInsert(is)).build();
		
		this.m_source = new CurrentSource();
		this.m_sourceCap = LazyOptional.of(() -> this.m_source);
		
		ContainerDataBuilder cdb = new ContainerDataBuilder();
		cdb.addProperty(() -> this.m_source.capacity(), (i) -> this.m_source.setOutput(i));
		this.m_data = cdb.build();
	} // end constructor()
	
	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state, int output)
	{
		this(Objects.requireNonNull(position), Objects.requireNonNull(state));
		
		this.m_source.setOutput(output);
	} // end constructor()

	
	
	protected @NotNull IItemHandler getInventory() 
	{
		return this.m_inventory;
	} // end getInventory()
	
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
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
	
	
	
	public void blockBroken() 
	{
		InventoryUtil.drop(this.level, this.worldPosition, this.m_rawInventory);
	} // end blockBroken()
	
	
	
	@Override
	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put(DeviceBlockEntity.INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
		tag.putInt(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME, this.m_source.capacity());
		
	} // end saveAdditional()
	
	@Override
	public void load(@NotNull CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(DeviceBlockEntity.INVENTORY_TAG_NAME)) 
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(DeviceBlockEntity.INVENTORY_TAG_NAME));
		}
		if(tag.contains(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME)) 
		{
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