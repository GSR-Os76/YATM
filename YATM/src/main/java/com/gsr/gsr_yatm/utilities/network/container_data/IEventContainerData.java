package com.gsr.gsr_yatm.utilities.network.container_data;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.inventory.ContainerData;

public interface IEventContainerData extends ContainerData
{
	@Override
	public int get(@NotNegative int index);
	
	@Override
	public void set(@NotNegative int index, int value);

	@Override
	public @NotNegative int getCount();
	
	
	
	public void subscribe(@NotNegative int index, @NotNull Consumer<Integer> subscriber);
	
	/** Removes the first occurrence of the specified subscriber from the event for the specific index.*/
	public void unsubscribe(@NotNegative int index, @NotNull Consumer<Integer> subscriber);

} // end class