package com.gsr.gsr_yatm.block.conduit.network_manager;

import java.util.Collection;

import com.gsr.gsr_yatm.block.conduit.IConduit;

import net.minecraftforge.common.util.LazyOptional;

public interface IConduitNetwork<T>
{
	public LazyOptional<T> getCapability();
	
	/** 
	 * @param canidate The conduit to potentially add on to the network.
	 * @return The capability the conduit can use to access the network if 
	 * it's accepted, or an empty if rejected.
	 */
	public LazyOptional<T> join(IConduit<T> canidate);
	
	public void join (IConduitNetwork<T> canidate);
	
	public Collection<IConduit<T>> getMembers();
	
} // end interface