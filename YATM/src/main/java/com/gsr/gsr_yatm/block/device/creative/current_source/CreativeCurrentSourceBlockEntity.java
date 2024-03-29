package com.gsr.gsr_yatm.block.device.creative.current_source;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.joml.Math;

import com.google.common.collect.ImmutableSet;
import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentDrainManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.OutputCurrentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.utility.TickableBehaviorConditioner;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

public class CreativeCurrentSourceBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 1;
	public static final int POWER_SLOT = 0;
	
	private final @NotNegative int m_output = Integer.MAX_VALUE;
	
	
	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor



	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> i);
		ICurrentHandler c = new ICurrentHandler() 
		{

			@Override
			public @NotNegative int receiveCurrent(@NotNegative int amount, boolean simulate)
			{
				return 0;
			} // end receiveCurrent()

			@Override
			public @NotNegative int extractCurrent(@NotNegative int amount, boolean simulate)
			{
				return Math.min(amount, CreativeCurrentSourceBlockEntity.this.m_output);
			} // end extractCurrent()

			@Override
			public @NotNegative int capacity()
			{
				return CreativeCurrentSourceBlockEntity.this.m_output;
			} // end capacity()

			@Override
			public @NotNegative int stored()
			{
				return CreativeCurrentSourceBlockEntity.this.m_output;
			} // end stored()
			
		};
		
		BackedFunction<IItemHandler, CurrentDrainManager> cDM = new BackedFunction<>((i) -> new CurrentDrainManager(i, CreativeCurrentSourceBlockEntity.POWER_SLOT, c, Integer.MAX_VALUE));
		// TODO, not sure how well multiple faces works with components, most likely doesn't
		BackedFunction<IItemHandler, OutputComponentManager> cDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, CreativeCurrentSourceBlockEntity.POWER_SLOT, this::getOutputFaces, this.getOutputRecheckPeriod()));

		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().insertionValidator(SlotUtil.POWER_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(cDM::apply).allDefaults().end()
		.behavior(cDCM::apply).allDefaults().end()
		.behavior(new TickableBehaviorConditioner(() -> !cDCM.get().hasComponent(), new OutputCurrentManager(this::getOutputFaces, this.getOutputRecheckPeriod(), c, Integer.MAX_VALUE))).allDefaults().end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(YATMCapabilities.CURRENT, c).end()
		
		.face(this::getAttachableFaces)
		.returns(CapabilityUtil.providerOrCapabiltyOrDefault(cDCM.get()::hasComponent, cDCM.get(), YATMCapabilities.CURRENT, c, defaultCapabilityProvider))
		.end()
		
		.last(defaultCapabilityProvider)
		.end();
	} // end define
	
	
	
	protected @NotNull Set<Direction> getAttachableFaces()
	{
		return Direction.stream().filter((d) -> this.getBlockState().getValue(CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(d)) != AttachmentState.NONE).collect(ImmutableSet.toImmutableSet());
	} // end getOutputFaces()
	
	protected @NotNull List<Direction> getOutputFaces()
	{
		return Direction.stream().filter((d) -> this.getBlockState().getValue(CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(d)) == AttachmentState.PUSH).toList();
	} // end getOutputFaces()
	
	
	
	protected @NotNegative int getOutputRecheckPeriod()
	{
		return YATMConfigs.CREATIVE_CURRENT_SOURCE_OUTPUT_RECHECK_PERIOD.get();
	} // end getOutputRecheckPeriod()
	
} // end class
//	public static final int INVENTORY_SLOT_COUNT = 1;
//	public static final int CHARGE_SLOT = 0;
//	
//	public static final String OUTPUT_SPEC_KEY = "output";
//	
//	
//	public static final String INVENTORY_TAG_NAME = "inventory";
//	private static final String OUTPUT_TAG_NAME = "current_output";
//	
//	private static final int RECHECK_ATTACHMENTS_PERIOD = 80;	
//	private @NotNegative int m_recheckAttachmentsCounter = CreativeCurrentSourceBlockEntity.RECHECK_ATTACHMENTS_PERIOD;
//	
//	private final CurrentSource m_source;
//	private LazyOptional<ICurrentHandler> m_sourceCap;
//	private LazyOptional<ICurrentHandler> m_sterileCap = SlotUtil.createSterileCapability(YATMCapabilities.CURRENT);
//	private final Map<Direction, LazyOptional<ICurrentHandler>> m_neighborCaps = new EnumMap<>(Direction.class);
//	
//	private final ItemStackHandler m_rawInventory;
//	private final InventoryWrapper m_inventory;
//	 
//	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
//			Map.entry(CreativeCurrentSourceBlockEntity.OUTPUT_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
//			));
//	protected final @NotNull ContainerData m_data;
//	
//	
//	
//	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
//	{
//		super(YATMBlockEntityTypes.CREATIVE_CURRENT_SOURCE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
//		this.m_rawInventory = new ItemStackHandler(CreativeCurrentSourceBlockEntity.INVENTORY_SLOT_COUNT);
//		this.m_inventory = InventoryWrapper.Builder.of(this.m_rawInventory)
//				.onInsertion((s, is) -> this.setChanged())
//				.onWithdrawal((s, is) -> this.setChanged())
//				.slotValidator((s, is, sim) -> SlotUtil.isValidPowerSlotInsert(is)).build();
//		
//		this.m_source = new CurrentSource();
//		this.m_sourceCap = LazyOptional.of(() -> this.m_source);
//		
//		ContainerDataBuilder cdb = new ContainerDataBuilder();
//		cdb.addPropertyS(() -> this.m_source.capacity(), (i) -> this.m_source.setOutput(i));
//		this.m_data = cdb.build();
//	} // end constructor()
//	
//	public CreativeCurrentSourceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state, int output)
//	{
//		this(Objects.requireNonNull(position), Objects.requireNonNull(state));
//		
//		this.m_source.setOutput(output);
//	} // end constructor()
//
//	
//	
//	protected @NotNull IItemHandler getInventory() 
//	{
//		return this.m_inventory;
//	} // end getInventory()
//	
//	public @NotNull ContainerData getDataAccessor()
//	{
//		return this.m_data;
//	} // end getDataAccessor()
//	
//	
//	
//	@Override
//	public void serverTick(@NotNull Level level, @NotNull BlockPos position,  @NotNull BlockState state)
//	{
//		if(++this.m_recheckAttachmentsCounter >= CreativeCurrentSourceBlockEntity.RECHECK_ATTACHMENTS_PERIOD) 
//		{
//			this.recheckNeighborAttachments(level, position, state);
//			this.m_recheckAttachmentsCounter = 0;
//		}
//		ItemStack s = this.m_inventory.getStackInSlot(CreativeCurrentSourceBlockEntity.CHARGE_SLOT);
//		ICurrentHandler ch = s.getCapability(YATMCapabilities.CURRENT).orElse((ICurrentHandler)null);
//		if(ch != null) 
//		{
//			ch.receiveCurrent(this.m_source.extractCurrent(Integer.MAX_VALUE, false), false);
//		}
//		for(Direction d : Direction.values()) 
//		{
//			if(this.m_source != null && this.m_neighborCaps.containsKey(d) && state.getValue(CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(d)) == AttachmentState.PUSH) 
//			{
//				ICurrentHandler n = this.m_neighborCaps.get(d).orElse((ICurrentHandler)null);
//				n.receiveCurrent(this.m_source.extractCurrent(Integer.MAX_VALUE, false), false);
//			}
//		}			
//	} // end serverTick()
//	
//	public void recheckNeighborAttachments(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) 
//	{
//		if(!this.getType().isValid(level.getBlockState(position))) 
//		{
//			return;
//		}
//		
//		for(Direction face : Direction.values())
//		{
//			BlockPos neighborPos = position.relative(face);
//			if(!level.isLoaded(neighborPos)) 
//			{
//				this.m_neighborCaps.put(face, null);
//				continue;
//			}				
//			BlockEntity be = level.getBlockEntity(neighborPos);
//			LazyOptional<ICurrentHandler> cap = be == null ? null : be.getCapability(YATMCapabilities.CURRENT, face.getOpposite());
//			if(cap != null && cap.isPresent()) 
//			{
//				if(cap != this.m_neighborCaps.get(face)) 
//				{
//					cap.addListener((la) -> this.removeAttachment(level, position, face, la));
//					this.m_neighborCaps.put(face, cap);
//				}
//			}
//		}
//	} // end recheckDrainAttachment()
//	
//	private void removeAttachment(@NotNull Level level, @NotNull BlockPos position, Direction face, LazyOptional<ICurrentHandler> la) 
//	{
//		BlockState b = level.getBlockState(position);
//		if(this.isRemoved() || !level.isLoaded(position) || !this.getType().isValid(b)) 
//		{
//			return;
//		}
//		
//		if(la == this.m_neighborCaps.get(face)) 
//		{
//			this.m_neighborCaps.put(face, null);
//			EnumProperty<AttachmentState> prop = CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(face);
//			if(b.getValue(prop) != AttachmentState.NONE) 
//			{
//				level.setBlock(position, b.setValue(prop, AttachmentState.NONE), Block.UPDATE_CLIENTS);
//			}
//		}
//	} // end tryRemoveAttachment()
//	/*
//	@Override
//	public @NotNull AttachmentState cycleAttachment(@NotNull AttachmentState attachmentState, @NotNull Direction side) 
//	{
//		return switch(attachmentState) 
//		{
//			case NONE -> AttachmentState.NEUTRAL;
//			case NEUTRAL -> AttachmentState.PUSH;
//			case PULL -> AttachmentState.NONE;
//			case PUSH -> AttachmentState.NONE;
//			default -> throw new IllegalArgumentException("Unexpected value of: " + attachmentState);
//		};
//	} // end cycleAttachment()
//	*/
//	
//	
//
//	@Override
//	public @NotNull NonNullList<ItemStack> getDropInventory()
//	{
//		return InventoryUtil.toNNList(this.m_rawInventory);
//	} // end getDropInventory()
//	
//	
//	
//	@Override
//	protected void saveAdditional(@NotNull CompoundTag tag)
//	{
//		super.saveAdditional(tag);
//		tag.put(DeviceBlockEntity.INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
//		tag.putInt(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME, this.m_source.capacity());
//		
//	} // end saveAdditional()
//	
//	@Override
//	public void load(@NotNull CompoundTag tag)
//	{
//		super.load(tag);
//		
//		if(tag.contains(DeviceBlockEntity.INVENTORY_TAG_NAME)) 
//		{
//			this.m_rawInventory.deserializeNBT(tag.getCompound(DeviceBlockEntity.INVENTORY_TAG_NAME));
//		}
//		if(tag.contains(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME)) 
//		{
//			this.m_source.setOutput(tag.getInt(CreativeCurrentSourceBlockEntity.OUTPUT_TAG_NAME));
//			this.m_sourceCap = LazyOptional.of(() -> this.m_source);
//		}
//	} // end load()
//
//	
//
//	@Override
//	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
//	{
//		if(cap == YATMCapabilities.CURRENT) 
//		{
//			if(side == null) 
//			{
//				return this.m_sourceCap.cast();				
//			}
//			else 
//			{
//				AttachmentState as = this.getBlockState().getValue(CreativeCurrentSourceBlock.ATTACHMENT_STATE_BY_FACE.get(side));
//				if(as != AttachmentState.NONE) 
//				{
//					return (as == AttachmentState.NEUTRAL ? this.m_sourceCap : this.m_sterileCap).cast();
//				}
//			}
//		}
//		return super.getCapability(cap, side);
//	} // end getCapability()
//	
//	@Override
//	public void invalidateCaps()
//	{
//		super.invalidateCaps();		
//		this.m_sourceCap.invalidate();
//		this.m_sterileCap.invalidate();
//	} // end invalidateCaps()
//
//	@Override
//	public void reviveCaps()
//	{
//		super.reviveCaps();
//		this.m_sourceCap = LazyOptional.of(() -> this.m_source);
//		this.m_sterileCap = SlotUtil.createSterileCapability(YATMCapabilities.CURRENT);
//	} // end reviveCaps()
//	
//} // end class