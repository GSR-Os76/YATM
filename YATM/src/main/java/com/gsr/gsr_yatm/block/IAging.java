package com.gsr.gsr_yatm.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface IAging
{
	private Block self() 
	{
		return (Block)this;
	} // end self()
	
	
	
	public default int getAge(@NotNull BlockState state)
	{
		return state.getValue(this.getAgeProperty());
	} // end getAge()
	
	public @NotNull IntegerProperty getAgeProperty();
	
	public default int getMaxAge()
	{
		return this.getAgeProperty().getAllValues().mapToInt((v) -> v.value()).max().getAsInt();// .max(Comparators::min).get();
	} // end getMaxAge()

	public default @NotNull BlockState getStateForAge(@Nullable BlockState state, int age)
	{
		return (state != null ? state : this.self().defaultBlockState())
				.setValue(this.getAgeProperty(), age);
	} // end getStateForAge()
	
} // end IAging