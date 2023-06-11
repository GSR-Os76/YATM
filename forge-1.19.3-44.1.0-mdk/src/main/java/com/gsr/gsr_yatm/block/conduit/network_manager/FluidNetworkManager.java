package com.gsr.gsr_yatm.block.conduit.network_manager;

import java.util.ArrayList;
import java.util.Collection;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.conduit.IConduit;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidNetworkManager implements IConduitNetwork<IFluidHandler>, IFluidHandler
{
	private static final String FLUID_STACK_IN_MEMBERS_TAG_NAME = "fluid";
	private static final int CONDUIT_CAPACITY = 200;
	
	private final ArrayList<IConduit<IFluidHandler>> m_conduits = new ArrayList<>();
	private FluidStack m_contents = FluidStack.EMPTY;
	
	private final LazyOptional<IFluidHandler> m_cap = LazyOptional.of(() -> this);
	
	
	
	private void setChanged() 
	{
		float rawFraction = (float)this.m_contents.getAmount() / (float)this.m_conduits.size();
		int fractionRoundedDown = (int)rawFraction;
		float remainder  = rawFraction % 1;
		Tag highTag = (rawFraction % 1 > 0 ? new FluidStack(this.m_contents.getFluid(), fractionRoundedDown + 1) : new FluidStack(this.m_contents.getFluid(), fractionRoundedDown)).writeToNBT(new CompoundTag());
		Tag lowTag = (new FluidStack(this.m_contents.getFluid(), fractionRoundedDown)).writeToNBT(new CompoundTag());
		int dividingLine = (int)(remainder * this.m_conduits.size());
		for(int i = 0; i < this.m_conduits.size(); i++) 
		{			
				this.m_conduits.get(i).addData(FLUID_STACK_IN_MEMBERS_TAG_NAME, (i + 1) > dividingLine ? lowTag: highTag);
		}
	} // end setChanged
	
	
	
	@Override
	public LazyOptional<IFluidHandler> getCapability()
	{
		return this.m_cap;
	} // end getCapability()

	@Override
	public LazyOptional<IFluidHandler> join(IConduit<IFluidHandler> canidate)
	{
		CompoundTag data = canidate.getData();
		FluidStack canidatesHolding = (data.contains(FLUID_STACK_IN_MEMBERS_TAG_NAME)) 
				? FluidStack.loadFluidStackFromNBT(data.getCompound(FLUID_STACK_IN_MEMBERS_TAG_NAME)) 
				: FluidStack.EMPTY;
		
		if(this.canCurrentlyAccept(canidatesHolding) && !this.m_conduits.contains(canidate)) 
		{
			this.fill(canidatesHolding, FluidAction.EXECUTE);
			this.m_conduits.add(canidate);		
			LazyOptional<IFluidHandler> l = LazyOptional.of(() -> this);
			l.addListener((cap) -> this.m_conduits.remove(canidate));
			return l;
		}
		else 
		{
			return LazyOptional.empty();
		}
		
	} // end join()
	
	@Override
	public void join (IConduitNetwork<IFluidHandler> canidate) 
	{
		Collection<IConduit<IFluidHandler>> canidateMembers = new ArrayList<>(canidate.getMembers());
		canidateMembers.forEach((c) -> c.propose(this));
	} // end join
	
	
	@Override
	public Collection<IConduit<IFluidHandler>> getMembers()
	{
		return this.m_conduits;
	} // end getMembers()
	
	
	
	private int getDeviceCount() 
	{
		int count = 0;
		for(int i = 0; i < this.m_conduits.size(); i++) 
		{			
			count += this.m_conduits.get(i).getDevices().length;
		}
		return count;
	} // end getDeviceCount()
	
	private void tryDistributeFluid() 
	{
		// TODO, could not use this.fill or this.drain and wait until end to call setChanged()
		float rawFraction = (float)this.m_contents.getAmount() / (float)this.getDeviceCount();
		int fractionRoundedDown = (int)rawFraction;
		float remainder  = rawFraction % 1;
		// handlers better not change these
		FluidStack lowStack = (new FluidStack(this.m_contents.getFluid(), fractionRoundedDown));
		FluidStack highStack = (remainder > 0 ? new FluidStack(this.m_contents.getFluid(), fractionRoundedDown + 1) : lowStack);
		int dividingLine = (int)(remainder * this.m_conduits.size());
		
		int devicesPassed = 0;
		int numberSkipped = 0;
		for(int i = 0; i < this.m_conduits.size(); i++) 
		{			
			IFluidHandler[] devices = this.m_conduits.get(i).getDevices();
			for(int j = 0; j < devices.length; j++) 
			{			
				FluidStack stackToTry = (devicesPassed - numberSkipped) > dividingLine ? lowStack : highStack;
				int simRun = devices[j].fill(this.drain(stackToTry, FluidAction.SIMULATE), FluidAction.SIMULATE); 
				
				if(simRun > 0) 
				{
					devices[j].fill(this.drain(simRun, FluidAction.EXECUTE), FluidAction.EXECUTE);
				}
				else 
				{
					numberSkipped++;
				}
				
				devicesPassed++;
			}
		}
	} // end tryDistribute
	
	
	
	@Override
	public int getTanks()
	{
		return 1;
	} // end getTanks()

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return this.m_contents;
	} // end get FluidInTank

	@Override
	public int getTankCapacity(int tank)
	{
		return this.m_conduits.size() * CONDUIT_CAPACITY;
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return true;
	} // end isFluidValid()

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		if(resource.getFluid() != this.m_contents.getFluid() || resource.isEmpty()) 
		{
			return 0;
		}
		
		int maxFillAmount = Math.min(resource.getAmount(), this.getTankCapacity(0));
		if(action == FluidAction.EXECUTE) 
		{
			if(this.m_contents.isEmpty()) 
			{
				this.m_contents = resource.copy();
			}
			else 
			{
				this.m_contents.grow(maxFillAmount);
			
			}
			this.setChanged();
			this.tryDistributeFluid();
		}
		
		return maxFillAmount;
	}

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		if(resource.getFluid() != this.m_contents.getFluid()) 
		{
			return FluidStack.EMPTY;
		}
		return this.drain(resource.getAmount(), action);
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		if(this.m_contents.isEmpty() || maxDrain <= 0) 
		{
			return FluidStack.EMPTY;
		}
		
		int drainAmount = Math.min(maxDrain, this.m_contents.getAmount());
		FluidStack retStack = new FluidStack(this.m_contents.getFluid(), drainAmount);
		
		if(action == FluidAction.EXECUTE) 
		{
			this.m_contents.shrink(drainAmount);
			this.setChanged();
		}
		
		return retStack;
	} // end drain()

	
	
	public boolean canCurrentlyAccept(FluidStack stack) 
	{
		return stack.isEmpty() || stack.getFluid() == this.m_contents.getFluid();
	} // end canCurrentAccept()




	
	
} // end class