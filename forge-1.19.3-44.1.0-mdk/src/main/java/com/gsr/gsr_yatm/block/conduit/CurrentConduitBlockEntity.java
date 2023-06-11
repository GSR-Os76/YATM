package com.gsr.gsr_yatm.block.conduit;

import java.util.EnumMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.conduit.network_manager.CurrentNetworkManager;
import com.gsr.gsr_yatm.block.conduit.network_manager.IConduitNetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class CurrentConduitBlockEntity extends BlockEntity implements IConduit<ICurrentHandler>
{
	private final ConductorProperties m_conductorProperties;
	
	// kelvin
	private int m_temperature = 300;
	
	private IConduitNetwork<ICurrentHandler> m_network = new CurrentNetworkManager();
	
	
	
	private final EnumMap<Direction, LazyOptional<ICurrentHandler>> m_capabilitiesOfNeighbors = new EnumMap<>(Direction.class);

	private static int s_recheckNeighborRate = 20;
	private int m_timeSenseRecheck = s_recheckNeighborRate;
	private boolean m_stopped = false;
	
	
	
	public CurrentConduitBlockEntity(BlockPos pos, BlockState blockState)
	{
		this(pos, blockState, ConductorProperties.EMPTY);
	} // end constructor
	
	public CurrentConduitBlockEntity(BlockPos pos, BlockState blockState, ConductorProperties conductorProperties)
	{
		super(YATMBlockEntityTypes.CURRENT_CONDUIT_BLOCK_ENTITY.get(), pos, blockState);
		this.m_conductorProperties = conductorProperties;
		for (int i = 0; i < ALL_DIRECTIONS.length; i++)
		{
			this.m_capabilitiesOfNeighbors.put(ALL_DIRECTIONS[i], null);
		}
	} // end constructor

	
	
	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CurrentConduitBlockEntity currentConduitBlockEntity)
	{
		if (!level.isClientSide)
		{
			currentConduitBlockEntity.serverTick(level, blockPos, blockState);
		}
	} // end tick()

	private void serverTick(Level level, BlockPos blockPos, BlockState blockState)
	{
		// TODO, add in the networking transfering
		if (this.m_stopped)
		{
			return;
		}
		
		// does x++ work like in c# vs like ++x?
		if (this.m_timeSenseRecheck++ >= s_recheckNeighborRate)
		{
			for (int i = 0; i < ALL_DIRECTIONS.length; i++)
			{
				Direction dir = ALL_DIRECTIONS[i];
				BlockEntity be = level.getBlockEntity(blockPos.relative(dir));
				if (be != null)
				{
					LazyOptional<ICurrentHandler> cap = be.getCapability(YATMCapabilities.CURRENT, dir.getOpposite());
					if (cap != null && cap.isPresent())
					{
						
						this.setCapabilityFor(dir, blockPos, blockState, cap);
					}
					else if (this.m_capabilitiesOfNeighbors.get(dir) != null)
					{
						// if the cap is null, or not present, but is cached, clear that out
						this.clearCapabilityFrom(dir, blockPos, blockState);
					}
				}
				// no neighbor blockentity
				else if (this.m_capabilitiesOfNeighbors.get(dir) != null)
				{
					// if there isn't a blockentity, but a capability is cached for it, clear that out
					this.clearCapabilityFrom(dir, blockPos, blockState);
				}
			}
			this.m_timeSenseRecheck = 0;
		} // end neighbor check

	} // end serveTick

	private void clearCapabilityFrom(Direction dir, BlockPos blockPos, BlockState blockState)
	{
		this.m_capabilitiesOfNeighbors.put(dir, null);
		this.setBlockStateFor(dir, blockPos, false);
	} // end capInvalidated()

	private void setCapabilityFor(Direction dir, BlockPos blockPos, BlockState blockState, LazyOptional<ICurrentHandler> capability)
	{
		this.m_capabilitiesOfNeighbors.put(dir, capability);
		capability.addListener((c) -> this.clearCapabilityFrom(dir, blockPos, blockState));
		this.setBlockStateFor(dir, blockPos, true);
	} // end setCapabilityFor

	private void setBlockStateFor(Direction direction, BlockPos blockPos, Boolean to)
	{
		BlockState atPos = level.getBlockState(blockPos);
		if (atPos.getBlock() instanceof IConduitBlock && 
				IConduit.getValueForDirection(direction, atPos) != to)
		{
			super.level.setBlockAndUpdate(blockPos, IConduit.setValueForDirection(direction, atPos, to));
		}
	} // setBlockStateFor()



	public void blockRemoved()
	{
		this.m_stopped = true;
	} // end blockRemoved

	public void neighborChanged() 
	{
		this.m_timeSenseRecheck = s_recheckNeighborRate;
	} // end neighborChanged()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
		{
			return this.m_network.getCapability().cast();
		}
		
		return super.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		// TODO wrap networks capability, such that it won't be invalidated but we can invalidate this block individually
		super.invalidateCaps();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		// TODO Auto-generated method stub
		super.reviveCaps();
	} // end reviveCaps()

	
	
	
	
	
	
	// TODO, implement this stuff
	
	
	@Override
	public void recieveLoad(int current)
	{
		if(this.m_temperature <= this.m_conductorProperties.getSuperConductingCriticalTemperature() && current < this.m_conductorProperties.getSuperConductingBreakDownPastCurrent()) 
		{
			return;	
		}
		
		if(current > this.m_conductorProperties.getExplodesPastCurrent()) {
			// TODO, Explode
		}
		else if(current > this.m_conductorProperties.getOverheatsPastCurrent()) 
		{
			// TODO, interpolate to find the temperature, set temperature to, make wire glow if relevant, make wire burn, make wire strip material if past material melting point, strip slowly and with particles
		}
	} // end recieveLoad()

	
	@Override
	public IConduitNetwork<ICurrentHandler> getNetwork()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addData(String name, Tag data)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public CompoundTag getData()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICurrentHandler[] getDevices()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void propose(IConduitNetwork<ICurrentHandler> propositioner)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
	
} // end class