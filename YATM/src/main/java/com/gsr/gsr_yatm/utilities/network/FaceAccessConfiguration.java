package com.gsr.gsr_yatm.utilities.network;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;

public class FaceAccessConfiguration implements ContainerData
{
	// TODO, WIP
	public static final int CONFIGURABLE_FACES_FLAGS_INDEX = 0;
	public static final int CONFIGURABLE_SLOT_INDICE_INDEX = 1;
	
	
	private static final int UP_INDEX = 0;
	private static final int DOWN_INDEX = 1;
	private static final int NORTH_INDEX = 2;
	private static final int SOUTH_INDEX = 3;
	private static final int EAST_INDEX = 4;
	private static final int WEST_INDEX = 5;

	public static final Map<Direction, Integer> DIRECTION_INDICES_BY_DIRECTION = ImmutableMap.of(
			Direction.UP, FaceAccessConfiguration.UP_INDEX, 
			Direction.DOWN, FaceAccessConfiguration.DOWN_INDEX, 
			Direction.NORTH, FaceAccessConfiguration.NORTH_INDEX, 
			Direction.SOUTH, FaceAccessConfiguration.SOUTH_INDEX, 
			Direction.EAST, FaceAccessConfiguration.EAST_INDEX, 
			Direction.WEST, FaceAccessConfiguration.WEST_INDEX);
	
	// private final AccessSpecification m_facesSpec
	
	
	// TODO, possibly pull out an interface for boolean flag handling, and rename this to clarify it's 32 entry limit
	private final BooleanFlagHandler m_allowedFaces;
	private final BooleanFlagHandler m_slots;
	//private final Map<Integer, Direction> m_containerIndexFaceMap;
	private final Map<Direction, BooleanFlagHandler> m_slotFaceMap;
	
	
	
	private final ContainerData m_data;

	
	
	public FaceAccessConfiguration(List<Direction> configurableFaces, List<Integer> slots) 
	{
		// int count = 0;
		ContainerDataBuilder builder = new ContainerDataBuilder();
		
		this.m_allowedFaces = new BooleanFlagHandler();
		this.m_allowedFaces.setContainerDataSettableMask(0b1111_1111_1111_1111_1111_1111_1111_1111);
		FaceAccessConfiguration.DIRECTION_INDICES_BY_DIRECTION.forEach((d, i) -> FaceAccessConfiguration.this.m_allowedFaces.setValue(i, configurableFaces.contains(d)));
		builder.addContainerDataS(this.m_allowedFaces.getData());
		// count += this.m_allowedFaces.getData().getCount();
		
		this.m_slots = new BooleanFlagHandler();
		this.m_slots.setContainerDataSettableMask(0b1111_1111_1111_1111_1111_1111_1111_1111);
		slots.forEach((si) -> this.m_slots.setValue(si, true));
		builder.addContainerDataS(this.m_slots.getData());
		// count += this.m_slots.getData().getCount();
		
		// Map<Integer, Direction> indexFaceLookup = new HashMap<>();
		this.m_slotFaceMap = new EnumMap<>(Direction.class);
		for(Map.Entry<Direction, Integer> e : DIRECTION_INDICES_BY_DIRECTION.entrySet()) 
		{
			if(configurableFaces.contains(e.getKey())) 
			{
				// indexFaceLookup.put(count, e.getKey());
				BooleanFlagHandler n = new BooleanFlagHandler();
				n.setContainerDataSettableMask(0b1111_1111_1111_1111_1111_1111_1111_1111);
				this.m_slotFaceMap.put(e.getKey(), n);
				builder.addContainerDataS(n.getData());
				// count++;
			}
		}
		
		this.m_data = builder.build();
		//this.m_containerIndexFaceMap = indexFaceLookup;
		//this.m_count = count;
	} // end constructor
	
	
	
	@Override
	public int get(int index)
	{
		return this.m_data.get(index);
	} // end get()

	@Override
	public void set(int index, int value)
	{
		if(value > 1) 
		{
			this.m_data.set(index, value);
		}
	} // end set()

	@Override
	public int getCount()
	{
		return this.m_data.getCount();
	}
} // end getCount()