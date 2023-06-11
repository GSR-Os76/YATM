package com.gsr.gsr_yatm.block.conduit;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.block.conduit.network_manager.FluidNetworkManager;
import com.gsr.gsr_yatm.block.conduit.network_manager.IConduitNetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidConduitBlockEntity extends BlockEntity implements IConduit<IFluidHandler>
{
	private final EnumMap<Direction, LazyOptional<IFluidHandler>> m_capabilitiesOfNeighbors = new EnumMap<>(Direction.class);
	private final ArrayList<IFluidHandler> m_deviceNeighbors = new ArrayList<>();
	
	private static int s_recheckNeighborRate = 20;
	private int m_timeSenseRecheck = s_recheckNeighborRate;
	private boolean m_stopped = false;
	
	private IConduitNetwork<IFluidHandler> m_network = new FluidNetworkManager();
	private LazyOptional<IFluidHandler> m_networkCap;// = LazyOptional.of(() -> m_temp);
	
	private HashMap<String, Tag> m_dataToSave = new HashMap<>();
	private CompoundTag m_loadedTag;
	


	public FluidConduitBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(YATMBlockEntityTypes.FLUID_CONDUIT_BLOCK_ENTITY.get(), blockPos, blockState);
		for (int i = 0; i < ALL_DIRECTIONS.length; i++)
		{
			this.m_capabilitiesOfNeighbors.put(ALL_DIRECTIONS[i], null);
		}
		this.m_networkCap = this.m_network.join(this);
	} // end constructor
	
	
	



	public static void tick(Level level, BlockPos blockPos, BlockState blockState, FluidConduitBlockEntity fluidConduitBlockEntity)
	{
		if (!level.isClientSide)
		{
			fluidConduitBlockEntity.serverTick(level, blockPos, blockState);
		}
	} // end tick()
	

	private void serverTick(Level level, BlockPos blockPos, BlockState blockState)
	{
		// TODO, add in the networking transfering
		if (this.m_stopped)
		{
			return;
		}
		
		//m_capabilitiesOfNeighbors.values().for
		
		// does x++ work like in c# vs like ++x?
		if (this.m_timeSenseRecheck++ >= s_recheckNeighborRate)
		{
			for (int i = 0; i < ALL_DIRECTIONS.length; i++)
			{
				Direction dir = ALL_DIRECTIONS[i];
				BlockEntity be = level.getBlockEntity(blockPos.relative(dir));
				if (be != null)
				{
					LazyOptional<IFluidHandler> cap = be.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite());
					if (cap != null && cap.isPresent())
					{
						this.testIfConduitAndTryThings(dir, blockPos, blockState, cap, be);
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
	
	
	
	@SuppressWarnings("unchecked")
	private void testIfConduitAndTryThings(Direction from, BlockPos blockPos, BlockState blockState, LazyOptional<IFluidHandler> l, BlockEntity be) 
	{
		boolean isValidConduit = false;
		IConduit<IFluidHandler> c;
		try 
		{
			c = (IConduit<IFluidHandler>)be;
			isValidConduit = true;
		}
		catch(Exception e)
		{
			c = null;
		}
		
		if(isValidConduit)
		{
			//if not already same manager
			if(this.m_network != c.getNetwork()) 
			{
				//try merge logic	
				this.m_network.join(c.getNetwork());
			}
			
			//if merge fail
			if(this.m_network == c.getNetwork()) 
			{
				this.setCapabilityFor(from, blockPos, blockState, l);
			}
			
		}
		else 
		{
			this.setCapabilityFor(from, blockPos, blockState, l);
			this.m_deviceNeighbors.add(l.orElse(null));
		}
	} // end testIfConduitAndTryThings()
	
	
	
	private void clearCapabilityFrom(Direction dir, BlockPos blockPos, BlockState blockState)
	{
		this.m_capabilitiesOfNeighbors.put(dir, null);
		this.setBlockStateFor(dir, blockPos, false);
	} // end capInvalidated()
	

	private void setCapabilityFor(Direction dir, BlockPos blockPos, BlockState blockState, LazyOptional<IFluidHandler> capability)
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
	protected void saveAdditional(CompoundTag tag)
	{
		this.m_dataToSave.keySet().forEach((s) -> tag.put(s, this.m_dataToSave.get(s)));		
		super.saveAdditional(tag);
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		this.m_loadedTag = tag;
		super.load(tag);
	} // end load()




	
	



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == ForgeCapabilities.FLUID_HANDLER) 
		{
			return this.m_networkCap.cast();
		}
		return super.getCapability(cap, side);
	} // end getCapability();
	

	
	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		//this.m_networkCap;// = LazyOptional.of(() -> m_temp);
	} // end reviveCaps()
	
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_networkCap.invalidate();
	} // end invalidateCaps()



	
	
	
	
	
	
	public IConduitNetwork<IFluidHandler> getNetwork()
	{
		return this.m_network;
	} // end getNetwork()
	
	@Override
	public void recieveLoad(int load)
	{
		// TODO, Anything?
	} // end recieveLoad()
	
	@Override
	public void addData(String name, Tag data)
	{
		this.m_dataToSave.put(name, data);
	} // end addData()
	
	@Override
	public CompoundTag getData()
	{
		return this.m_loadedTag == null ? new CompoundTag() : this.m_loadedTag;
	} // end getData();

	@Override
	public IFluidHandler[] getDevices()
	{
		IFluidHandler[] a = new IFluidHandler[this.m_deviceNeighbors.size()];
		for(int i = 0; i < a.length; i++) 
		{
			a[i] = this.m_deviceNeighbors.get(i);
		}
		return a;
	}// end getDevices()

	@Override
	public void propose(IConduitNetwork<IFluidHandler> propositioner) 
	{
		LazyOptional<IFluidHandler> l = propositioner.join(this);
		if(l.isPresent()) 
		{
			this.m_networkCap.invalidate();
			this.m_networkCap = l;
			this.m_network = propositioner;
		}
	} // end propose()
	
} // end class