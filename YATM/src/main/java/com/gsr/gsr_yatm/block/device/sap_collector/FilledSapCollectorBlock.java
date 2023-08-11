package com.gsr.gsr_yatm.block.device.sap_collector;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.shape.BlockShapesProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FilledSapCollectorBlock extends AbstractSapCollectorBlock implements ISapCollector, EntityBlock
{
	private final Supplier<BlockState> m_drainedState;
	
	
	
	public FilledSapCollectorBlock(@NotNull Properties properties, @NotNull BlockShapesProvider shapes, @NotNull Supplier<BlockState> drainedState)
	{
		super(properties, shapes);
		this.m_drainedState = Objects.requireNonNull(drainedState);
	} // end constructor

	
	
	@Override
	public BlockEntity newBlockEntity(BlockPos position, BlockState state)
	{
		return new SapCollectorBlockEntity(position, state);
	} // end newBlockEntity()
	
	
	
	@Override
	public void onFluidDrained(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull FluidStack fluid)
	{
		level.setBlock(position, this.m_drainedState.get(), Block.UPDATE_ALL);
	} // end onFluidDrained()
	
	
	
	@Override
	protected double getContentHeight(BlockState state)
	{
		// TODO, implement
		return super.getContentHeight(state);
	} // end getContentHeight()



	@Override
	protected boolean canReceiveStalactiteDrip(Fluid fluid)
	{
		return false;
	} // end canRecieveStalactiteDrip(

	@Override
	protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos position, Fluid fluid)
	{
		
	} // end recieveStalactiteDrip()

	@Override
	public boolean isFull(BlockState state)
	{
		return true;
	} // end isFull()




} // end class