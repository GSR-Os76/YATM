package com.gsr.gsr_yatm.block.device.tank;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.OutputFluidManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.utility.TickableBehaviorConditioner;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.capability_provider.CapabilityProviderBuilderL;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.DirectionUtil;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class TankBlockEntity extends BuiltDeviceBlockEntity 
{	
	public static final int INVENTORY_SLOT_COUNT = 2;
	
	public static final int FILL_TANK_SLOT = 0;
	public static final int DRAIN_TANK_SLOT = 1;

	public static final int LAST_INVENTORY_SLOT = TankBlockEntity.DRAIN_TANK_SLOT;
	
	public static final String TANK_DATA_SPEC_KEY = "tank";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT)
			));
	
	
	
	public TankBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.TANK.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		Supplier<List<Direction>> activeDrainTo = () -> this.getBlockState().getValue(TankBlock.DRAINING) ? List.of(Direction.DOWN): List.of();
		
		
		
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> i);
		FluidTank t = this.m_helpers.newTank(YATMConfigs.TANK_CAPACITY.get());
		IFluidHandler drainT = this.m_helpers.noFill(t);
		IFluidHandler fillT = this.m_helpers.noDrain(t);
		
		BackedFunction<IItemHandler, FillTankManager> tFM = new BackedFunction<>((i) -> new FillTankManager(i, TankBlockEntity.FILL_TANK_SLOT, t, YATMConfigs.TANK_FILL_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, InputComponentManager<IFluidHandler>> tFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, TankBlockEntity.FILL_TANK_SLOT, TankWrapper.Builder.of(t).canDrain(() -> false).build(), ForgeCapabilities.FLUID_HANDLER));
		BackedFunction<IItemHandler, DrainTankManager> tDM = new BackedFunction<>((i) -> new DrainTankManager(i, TankBlockEntity.DRAIN_TANK_SLOT, t, YATMConfigs.TANK_DRAIN_MAX_FLUID_TRANSFER_RATE.get()));
		BackedFunction<IItemHandler, OutputComponentManager> tDCM = new BackedFunction<>((i) -> new OutputComponentManager(i, TankBlockEntity.DRAIN_TANK_SLOT, activeDrainTo, YATMConfigs.TANK_DRAIN_RECHECK_PERIOD.get()));
		
		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
			.slot().insertionValidator(SlotUtil.TANK_FILL_SLOT_INSERTION_VALIDATOR).end()
			.slot().insertionValidator(SlotUtil.TANK_DRAIN_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(() -> t.writeToNBT(new CompoundTag()), t::readFromNBT, "tank")).allDefaults().end()
		.behavior(tFM::apply).allDefaults().end()
		.behavior(tFCM::apply).allDefaults().end()
		.behavior(tDM::apply).allDefaults().end()
		.behavior(tDCM::apply).allDefaults().end()
		.behavior(new TickableBehaviorConditioner(() -> !tDCM.get().hasComponent(), new OutputFluidManager(activeDrainTo, YATMConfigs.TANK_DRAIN_RECHECK_PERIOD.get(), t, YATMConfigs.TANK_DRAIN_MAX_FLUID_TRANSFER_RATE.get()))).allDefaults().end()
		
		.containerData()
			.addContainerData(new FluidHandlerContainerData(t))
		.end()
		
		.getInventory(inv::apply)
		
		// TODO, make the tank able to face in any direction.
		.capabilityProvider()
			.face((Direction)null)
			.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
			.elifReturnsWhen(ForgeCapabilities.FLUID_HANDLER, t)
			.end()
			.face(Direction.UP)
			.returns(CapabilityUtil.conditionProvider(tFCM.get()::hasComponent, tFCM.get(), 
					new CapabilityProviderBuilderL()
					.face(() -> DirectionUtil.ALL_AND_NULL)
					.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), TankBlockEntity.FILL_TANK_SLOT))
					.elifReturnsWhen(ForgeCapabilities.FLUID_HANDLER, fillT).end().last(defaultCapabilityProvider)
					))
			.end()
			.face(Direction.DOWN)
			.returns(CapabilityUtil.conditionProvider(tDCM.get()::hasComponent, tDCM.get(), 
					new CapabilityProviderBuilderL()
					.face(() -> DirectionUtil.ALL_AND_NULL)
					.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), TankBlockEntity.DRAIN_TANK_SLOT))
					.elifReturnsWhen(ForgeCapabilities.FLUID_HANDLER, drainT).end().last(defaultCapabilityProvider)
					))
			.end()
		.end()
		
		.end();
	} // end define()
	
} // end class

//	private static final String TANK_TAG_NAME = "tank";
//	
//	private static final int DRAIN_RECHECK_PERIOD = YATMConfigs.TANK_DRAIN_RECHECK_PERIOD.get();	
//	private @NotNegative int m_drainRecheckCounter = 0;
//	
//	private final @NotNegative int m_maxFluidTransferRate = YATMConfigs.TANK_MAX_FLUID_TRANSFER_RATE.get();
//	private final @NotNull FluidTank m_rawTank = new FluidTank(YATMConfigs.TANK_CAPACITY.get());
//	private final @NotNull TankWrapper m_tank = TankWrapper.Builder.of(this.m_rawTank).onContentsChanged((f) -> 
//			{
//				this.setChanged();
//				this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
//			}).maxTransfer(this.m_maxFluidTransferRate).build();	
//	private @NotNull LazyOptional<IFluidHandler> m_tankLazyOptional = LazyOptional.of(() -> TankBlockEntity.this.m_tank);
//	
//	private LazyOptional<IFluidHandler> m_attachedCap;
//	private IFluidHandler m_drainAttachment;
//	
//	
//	
//	public TankBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
//	{
//		super(YATMBlockEntityTypes.TANK.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
//	} // end constructor
//	
//	
//	
//	@Override
//	public Packet<ClientGamePacketListener> getUpdatePacket()
//	{
//		return ClientboundBlockEntityDataPacket.create(this);		
//	} // end getUpdatePacket()
//	
//	@Override
//	public CompoundTag getUpdateTag()
//	{
//		CompoundTag tag = new CompoundTag();
//		tag.put(TankBlockEntity.TANK_TAG_NAME, this.m_rawTank.writeToNBT(new CompoundTag()));		
//		return tag;
//	} // end getUpdateTag()
//
//	@Override
//	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
//	{
//		super.onDataPacket(net, pkt);
//		CompoundTag tag = pkt.getTag();
//		if(tag.contains(TankBlockEntity.TANK_TAG_NAME)) 
//		{
//			this.m_rawTank.readFromNBT(tag.getCompound(TankBlockEntity.TANK_TAG_NAME));
//		}		
//	} // end onDataPacket()
//	
//	
//	
//	public @Nullable Fluid getFluid()
//	{
//		FluidStack f = this.m_tank.getFluid();
//		return (f == null || f.isEmpty()) ? null : f.getFluid();
//	} // end getFluid()
//	
//	public float getPercentageFilled() 
//	{
//		return ((float)this.m_tank.getFluidAmount()) / ((float)this.m_tank.getCapacity());
//	} // end getFillPercentage()
//	
//	
//
//	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull TankBlockEntity blockEntity)
//	{
//		if (level.isClientSide)
//		{
//			blockEntity.clientTick(level, position, state);
//		}
//		else
//		{
//			blockEntity.serverTick(level, position, state);
//		}
//	} // end tick()
//
//	public void clientTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
//	{
//		
//	} // end clientTick()
//	
//	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
//	{
//		
//		if(state.getValue(TankBlock.DRAINING)) 
//		{
//			if(++this.m_drainRecheckCounter >= TankBlockEntity.DRAIN_RECHECK_PERIOD) 
//			{
//				this.recheckDrainAttachment(level, position, state);
//			}
//			
//			if(this.m_drainAttachment != null) 
//			{
//				this.m_drainAttachment.fill(this.m_tank.drain(this.m_drainAttachment.fill(this.m_tank.drain(this.m_maxFluidTransferRate, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
//			}
//		}
//	} // end serverTick()
//	
//	public void recheckDrainAttachment(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) 
//	{
//		if(state.getValue(TankBlock.DRAINING))
//		{
//			BlockEntity be = level.getBlockEntity(position.below());
//			if(be != null) 
//			{
//				LazyOptional<IFluidHandler> la = be.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP);
//				IFluidHandler t = la.orElse(null);
//				if(t != null) 
//				{
//					this.m_attachedCap = la;
//					this.m_drainAttachment = t;
//					la.addListener((l) -> this.tryRemoveAttachment(l));
//				}
//			}
//			this.m_drainRecheckCounter = 0;
//		}
//	} // end recheckDrainAttachment()
//	
//	private void tryRemoveAttachment(@NotNull LazyOptional<IFluidHandler> la) 
//	{
//		if(la == this.m_attachedCap) 
//		{
//			this.m_attachedCap = null;
//			this.m_drainAttachment = null;
//		}
//	} // end tryRemoveAttachment()
//	
//	
//	
//	@Override
//	protected void saveAdditional(CompoundTag tag)
//	{
//		super.saveAdditional(tag);
//		
//		if (this.m_rawTank.getFluidAmount() > 0)
//		{
//			tag.put(TankBlockEntity.TANK_TAG_NAME, this.m_rawTank.writeToNBT(new CompoundTag()));
//		}
//	} // end saveAdditional()
//	
//	@Override
//	public void load(CompoundTag tag)
//	{
//		super.load(tag);
//		
//		if(tag.contains(TankBlockEntity.TANK_TAG_NAME)) 
//		{
//			this.m_rawTank.readFromNBT(tag.getCompound(TankBlockEntity.TANK_TAG_NAME));
//		}
//	} // end load()
//
//	
//
//	@Override
//	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
//	{
//		if((side == null || Direction.Plane.VERTICAL.test(side)) && cap == ForgeCapabilities.FLUID_HANDLER) 
//		{
//			return this.m_tankLazyOptional.cast();
//		}
//		return super.getCapability(cap, side);
//	} // end getCapability
//	
//	@Override
//	public void invalidateCaps()
//	{
//		super.invalidateCaps();
//		this.m_tankLazyOptional.invalidate();
//	} // end invalidateCaps()
//
//	@Override
//	public void reviveCaps()
//	{
//		super.reviveCaps();
//		this.m_tankLazyOptional = LazyOptional.of(() -> TankBlockEntity.this.m_tank);
//	} // end reviveCaps()
//
//} // end class