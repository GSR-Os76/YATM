package com.gsr.gsr_yatm.block.device.tank;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.fluid.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TankBlockEntity extends BlockEntity 
{	
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";
	private static final String SETUP_TAG_NAME = "setup";
	private static final String TANK_TAG_NAME = "tank";
	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	
	private static final int DRAIN_RECHECK_PERIOD = 40;	
	private @NotNegative int m_drainRecheckCounter = 0;
	
	private @NotNegative int m_maxFluidTransferRate;
	private @NotNull FluidTank m_rawTank;
	private @NotNull ConfigurableTankWrapper m_tank;	
	private @NotNull LazyOptional<IFluidHandler> m_tankLazyOptional = LazyOptional.of(() -> TankBlockEntity.this.m_tank);
	
	private LazyOptional<IFluidHandler> m_attachedCap;
	private IFluidHandler m_drainAttachment;
	
	
	
	public TankBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		this(Objects.requireNonNull(position), Objects.requireNonNull(state), DeviceTierConstants.EMPTY);
	} // end constuctor
	
	public TankBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull DeviceTierConstants constants)
	{
		super(YATMBlockEntityTypes.TANK.get(), Objects.requireNonNull(blockPos), Objects.requireNonNull(blockState));
		this.setup(constants.maxFluidTransferRate(), constants.tankCapacity());
	} // end constructor
	
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(TankBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		tag.putInt(TankBlockEntity.TANK_CAPACITY_TAG_NAME, this.m_rawTank.getCapacity());
		return tag;
	} // end setupToNBT()

	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		int tankCapacity = 0;
		int maxFluidTransferRate = 0;
		
		if (tag.contains(TankBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME))
		{
			maxFluidTransferRate = tag.getInt(TankBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		if (tag.contains(TankBlockEntity.TANK_CAPACITY_TAG_NAME))
		{
			tankCapacity = tag.getInt(TankBlockEntity.TANK_CAPACITY_TAG_NAME);
		}
		
		this.setup(maxFluidTransferRate, tankCapacity);
	} // end setupFromNBT()

	private void setup(@NotNegative int maxFluidTransferRate, @NotNegative int tankCapacity)
	{
		this.m_maxFluidTransferRate = Contract.NotNegative(maxFluidTransferRate);
		this.m_rawTank = new FluidTank(Contract.NotNegative(tankCapacity));
		this.m_tank = ConfigurableTankWrapper.Builder.of(this.m_rawTank)
				.onContentsChanged((f) -> 
				{
					this.setChanged();
					this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
				})
				.maxTransfer(this.m_maxFluidTransferRate).build();
		
		this.m_tankLazyOptional.invalidate();
		this.m_tankLazyOptional = LazyOptional.of(() -> TankBlockEntity.this.m_tank);
	} // end setup()
	
	
	
	
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
		YetAnotherTechMod.LOGGER.info("getting a packet");
		return ClientboundBlockEntityDataPacket.create(this);		
	} // end getUpdatePacket()
	
	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag tag = new CompoundTag();
		tag.put(TankBlockEntity.TANK_TAG_NAME, this.m_rawTank.writeToNBT(new CompoundTag()));		
		return tag;
	} // end getUpdateTag()

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
	{
		YetAnotherTechMod.LOGGER.info("recieving a packet");
		super.onDataPacket(net, pkt);
		CompoundTag tag = pkt.getTag();
		if(tag.contains(TankBlockEntity.TANK_TAG_NAME)) 
		{
			this.m_rawTank.readFromNBT(tag.getCompound(TankBlockEntity.TANK_TAG_NAME));
		}		
	} // end onDataPacket()
	
	

	public static void tick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull TankBlockEntity blockEntity)
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
	
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		
		if(state.getValue(TankBlock.DRAINING)) 
		{
			if(++this.m_drainRecheckCounter >= TankBlockEntity.DRAIN_RECHECK_PERIOD) 
			{
				this.recheckDrainAttachment(level, position, state);
			}
			
			if(this.m_drainAttachment != null) 
			{
				this.m_drainAttachment.fill(this.m_tank.drain(this.m_drainAttachment.fill(this.m_tank.drain(this.m_maxFluidTransferRate, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
			}
		}
	} // end serverTick()
	
	public void recheckDrainAttachment(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state) 
	{
		if(state.getValue(TankBlock.DRAINING))
		{
			BlockEntity be = level.getBlockEntity(position.below());
			if(be != null) 
			{
				LazyOptional<IFluidHandler> la = be.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP);
				IFluidHandler t = la.orElse(null);
				if(t != null) 
				{
					this.m_attachedCap = la;
					this.m_drainAttachment = t;
					la.addListener((l) -> this.tryRemoveAttachment(l));
				}
			}
			this.m_drainRecheckCounter = 0;
		}
	} // end recheckDrainAttachment()
	
	private void tryRemoveAttachment(LazyOptional<IFluidHandler> la) 
	{
		if(la == this.m_attachedCap) 
		{
			this.m_attachedCap = null;
			this.m_drainAttachment = null;
		}
	} // end tryRemoveAttachment()
	
	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.put(TankBlockEntity.SETUP_TAG_NAME, this.setupToNBT());
		if (this.m_rawTank.getFluidAmount() > 0)
		{
			tag.put(TankBlockEntity.TANK_TAG_NAME, this.m_rawTank.writeToNBT(new CompoundTag()));
		}
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(TankBlockEntity.SETUP_TAG_NAME)) 
		{
			this.setupFromNBT(tag.getCompound(TankBlockEntity.SETUP_TAG_NAME));
		}
		if(tag.contains(TankBlockEntity.TANK_TAG_NAME)) 
		{
			this.m_rawTank.readFromNBT(tag.getCompound(TankBlockEntity.TANK_TAG_NAME));
		}
	} // end load()

	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if((side == null || Direction.Plane.VERTICAL.test(side)) && cap == ForgeCapabilities.FLUID_HANDLER) 
		{
			return this.m_tankLazyOptional.cast();
		}
		return super.getCapability(cap, side);
	} // end getCapability
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_tankLazyOptional.invalidate();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_tankLazyOptional = LazyOptional.of(() -> TankBlockEntity.this.m_tank);
	} // end reviveCaps()

} // end class