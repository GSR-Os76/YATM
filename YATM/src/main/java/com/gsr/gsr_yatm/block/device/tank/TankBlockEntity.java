package com.gsr.gsr_yatm.block.device.tank;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TankBlockEntity extends BlockEntity 
{	
	private static final String TANK_TAG_NAME = "tank";
	
	private static final int DRAIN_RECHECK_PERIOD = YATMConfigs.TANK_DRAIN_RECHECK_PERIOD.get();	
	private @NotNegative int m_drainRecheckCounter = 0;
	
	private final @NotNegative int m_maxFluidTransferRate = YATMConfigs.TANK_MAX_FLUID_TRANSFER_RATE.get();
	private final @NotNull FluidTank m_rawTank = new FluidTank(YATMConfigs.TANK_CAPACITY.get());
	private final @NotNull TankWrapper m_tank = TankWrapper.Builder.of(this.m_rawTank).onContentsChanged((f) -> 
			{
				this.setChanged();
				this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
			}).maxTransfer(this.m_maxFluidTransferRate).build();	
	private @NotNull LazyOptional<IFluidHandler> m_tankLazyOptional = LazyOptional.of(() -> TankBlockEntity.this.m_tank);
	
	private LazyOptional<IFluidHandler> m_attachedCap;
	private IFluidHandler m_drainAttachment;
	
	
	
	public TankBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.TANK.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor
	
	
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
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
		super.onDataPacket(net, pkt);
		CompoundTag tag = pkt.getTag();
		if(tag.contains(TankBlockEntity.TANK_TAG_NAME)) 
		{
			this.m_rawTank.readFromNBT(tag.getCompound(TankBlockEntity.TANK_TAG_NAME));
		}		
	} // end onDataPacket()
	
	
	
	public @Nullable Fluid getFluid()
	{
		FluidStack f = this.m_tank.getFluid();
		return (f == null || f.isEmpty()) ? null : f.getFluid();
	} // end getFluid()
	
	public float getPercentageFilled() 
	{
		return ((float)this.m_tank.getFluidAmount()) / ((float)this.m_tank.getCapacity());
	} // end getFillPercentage()
	
	

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
	
	private void tryRemoveAttachment(@NotNull LazyOptional<IFluidHandler> la) 
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
		
		if (this.m_rawTank.getFluidAmount() > 0)
		{
			tag.put(TankBlockEntity.TANK_TAG_NAME, this.m_rawTank.writeToNBT(new CompoundTag()));
		}
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
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