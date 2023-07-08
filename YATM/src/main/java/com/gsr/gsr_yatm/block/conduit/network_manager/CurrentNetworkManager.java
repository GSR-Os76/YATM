package com.gsr.gsr_yatm.block.conduit.network_manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.block.conduit.IConduit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Pair;
//import oshi.util.tuples.Triplet;

public class CurrentNetworkManager implements ICurrentHandler, IConduitNetwork<ICurrentHandler>
{
	// producer/consumer index = index in ArrayList + (m_node.size - 1)
	
	//private ... all non conduits attached to the network, every face if it's
	private ArrayList<Pair<Pair<ICurrentHandler, BlockPos>, Direction[]>> m_devices = new ArrayList<>();
	// private ... all conduits composing the network
	private ArrayList<Pair<ICurrentHandler, BlockPos>> m_conduits = new ArrayList<>();

	// indices of conduits that have three connections
	private ArrayList<Integer> m_nodes = new ArrayList<>();
	// indices of nodes that connect to at least three devices
	private ArrayList<Integer> m_liveNodes = new ArrayList<>(); 
	// index of a, index of b.
	private ArrayList<Pair<Integer, Integer>> m_paths = new ArrayList<>();

	// cache of average route and their weights
	// key: from, to. value: {key: index of path. value: load fraction path}
	private HashMap<Pair<Integer, Integer>, HashMap<Integer, Float>> m_averageRouteCache = new HashMap<>();

	
	
	// recalculate composing members
	
	private void clearNetworking() 
	{		
		m_nodes = new ArrayList<>();
		m_paths = new ArrayList<>();
		m_liveNodes = new ArrayList<>();
		
		clearRoutesCache();
	}
	
	public void RecalculateNetworking() 
	{
		clearNetworking();
		
		calculateNodes();
		calculatePaths();
		calculateLiveNodes();
	} // end RecalculateNetworking
	
	private void clearRoutesCache()
	{
		// clear old caches of values
		m_averageRouteCache = new HashMap<>();
	} // end markRoutesDirty()



	private void calculateNodes()
	{
		// look at each composing conduit
		// if they connect to three or more things thats a node
		for (int i = 0; i < m_conduits.size(); i++)
			//if (getValidNeighbors(m_conduits.get(i).getB()).length() >= 3)
				m_nodes.add(i);
	} // end calculateNodes();
	
	private void calculateLiveNodes() 
	{
		// select those nodes which have three paths
	} // end calculateLiveNodes

	private void calculatePaths()
	{
		// look at each composing conduit
		// if they connect two nodes, 
		// or a node and a (producer or a consumer), 
		// or a producer and consumer, 
		// thats a path
		
		for(int i = 0; i < m_nodes.size(); i++)
		{
			Direction[] validDirections = getValidNeighbors(m_conduits.get(m_nodes.get(i)).getB());// get direction of all attachments
			for(int j = 0; j < validDirections.length; j++)
			{
				int result = tracePath(i, validDirections[j]);
			}
		}
	}

	// return index of device or node at end of path, or negative one if none are found
	private int tracePath(int indexFrom, Direction goingInDirection) 
	{
		// TODO this is not right, just preventing error
		return 0;
	}
	
	
	private Direction[] getValidNeighbors(BlockPos position) 
	{
		Direction[] neighbors;
		return new Direction[1];
	}
	
	
	
	private BlockPos getPositionFromIndex(int index) 
	{
		return index < (m_nodes.size() - 1) 
				? m_conduits.get(m_nodes.get(index)).getB() 
				: m_devices.get(index - (m_nodes.size() - 1)).getA().getB();
	} // end getPositionFromIndex()
	
	private BlockPos getPositionFromThing(ICurrentHandler thing) 
	{
		for(int i = 0; i < m_conduits.size(); i++)
			if(m_conduits.get(i).getA() == thing)
				return m_conduits.get(i).getB();
		
		for(int i = 0; i < m_devices.size(); i++)
			if(m_conduits.get(i).getA() == thing)
				return m_conduits.get(i).getB();
		
		return null;
	} // getPositionFromThing()
	


	private HashMap<Integer, Float> getRouteWeights(int indexOfFrom, int indexOfTo)
	{
		Pair<Integer, Integer> key = new Pair<Integer, Integer>(indexOfFrom, indexOfTo);
		if(!m_averageRouteCache.containsKey(key))
			calculateRouteWeights(indexOfFrom, indexOfTo);
		
		return m_averageRouteCache.get(key);
	} // end getRouteWeights()
	
	private void calculateRouteWeights(int indexOfFrom, int indexOfTo)
	{
		//TODO
	} // end calculateRouteWeights()
	
	
	
	public void addDevice(ICurrentHandler device, BlockPos worldPosition)//, Direction[] interfacingFacesOnDevice) 
	{
		// TODO
	} // end addDevice()

	private void addConduit(ICurrentHandler conduit, BlockPos worldPosition) 
	{
		// TODO
	} // end addConduit()

	
	
	public int recieveCurrent(int amount, Direction from, boolean simulate, ICurrentHandler recipientConduit)
	{

		// calculate where from
		BlockPos positionOfSource = getPositionFromThing(recipientConduit).offset(from.getNormal());
		
		//int sourceIndex = m_devices.indexOf(new );

		// calculate where to(s)
		//Pair<ICurrentHandler, Face face>[] recipients = getActiveRecipients();

		// divide current by count of where to(s), this is their destined power
		//int destinedCurrent = amount / recipients.Size();
		//for(int i = 0; i < recipients.size(); i++)
			//deliverLoad(getRouteWeights(sourceIndex, recipients.get(i)), destinedCurrent);

		// check for precalculated average routes
		// if not found: generate potential routes

		// average load across potential routes

		// inform conduits of their load if changed

		// deliver to the where to(s)

		return 0;
	}
	/*
	 * public void recieveCurrent(int amount, Face from, ActionMode mode, BlockPos
	 * recipientConduit) { // calculate where from
	 * 
	 * // calculate where to(s)
	 * 
	 * // divide current by count of where to(s), this is their destined power
	 * 
	 * // check for precalculated average routes // if not found: generate potential
	 * routes
	 * 
	 * // average load across potential routes
	 * 
	 * // inform conduits of their load if changed
	 * 
	 * // deliver to the where to(s)
	 * 
	 * }
	 */



	public void join(CurrentNetworkManager applicant)
	{
		// unless neither node nor path
		//clearRoutesCache();
		//applicant.m_conduits.size();
		
		//applicant.deathMark();
		for(int i = 0; i < applicant.m_devices.size(); i++) 
		{	
			// Pair<Pair<ICurrentHandler, BlockPos>, Face[]> deviceData = applicant.m_devices.get(i);
			// addDevice(deviceData.getA().getA(), deviceData.getA().getB(), deviceData.getB());
		} // end loop
		
		for(int i = 0; i < applicant.m_conduits.size(); i++) 
		{
			Pair<ICurrentHandler, BlockPos> conduitData = applicant.m_conduits.get(i);
			addConduit(conduitData.getA(), conduitData.getB());
		} // end loop
	} // end join()

	
	
	private final LazyOptional<ICurrentHandler> m_cap =  LazyOptional.of(() -> this);
	
	public LazyOptional<ICurrentHandler> getCapability() 
	{
		return this.m_cap;
	} // end getCapability
	
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int capacity()
	{
		return 0;
	} // end storageCapacity()

	@Override
	public int stored()
	{
		return 0;
	} // end storedCapacity()

	
	
	
	
	
	@Override
	public LazyOptional<ICurrentHandler> join(IConduit<ICurrentHandler> canidate)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void join(IConduitNetwork<ICurrentHandler> canidate)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<IConduit<ICurrentHandler>> getMembers()
	{
		// TODO Auto-generated method stub
		return null;
	}



	// listen for non conduits who change their face configurations.

} // end class