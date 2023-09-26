package com.gsr.gsr_yatm.block.device.sap_collector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.IDripFillableBlock;
import com.gsr.gsr_yatm.utilities.shape.BlockShapesProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class SapCollectorBlock extends AbstractSapCollectorBlock implements IDripFillableBlock, ISapCollector, EntityBlock
{
	private final @NotNull Map<@NotNull Fluid, @NotNull BlockState> m_variantLookup = new HashMap<>();

	
	
	public SapCollectorBlock(@NotNull Properties properties, @NotNull BlockShapesProvider shapes)
	{
		super(Objects.requireNonNull(properties), shapes);
	} // end constructor
	
	
	
	@Override
	public BlockEntity newBlockEntity(BlockPos position, BlockState state)
	{
		return new SapCollectorBlockEntity(position, state);
	} // end newBlockEntity()
	
	
	
	@Override
	public boolean canRecieveFluid(@NotNull Fluid fluid) 
	{
		return this.m_variantLookup.containsKey(fluid);
	} // end canRecieveFluid()
	
	@Override
	public void recieveFluid(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull Fluid fluid) 
	{
		if(this.m_variantLookup.containsKey(fluid)) 
		{
			level.setBlock(position, this.m_variantLookup.get(fluid), Block.UPDATE_ALL);
		}
	} // end recieveFluid

	
	
	@Override
	public void onFluidFilled(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos position, @NotNull FluidStack fluid)
	{
		this.recieveFluid(level, state, position, fluid.getFluid());
	} // end onFluidFilled()

	@Override
	public @NotNull Collection<Fluid> recievableFluids()
	{
		return this.m_variantLookup.keySet();
	} // end recievableFluids()
	

	
	
	
	@Override
	protected boolean canReceiveStalactiteDrip(Fluid fluid)
	{
		return this.canRecieveFluid(fluid);
	} // end canReceiveStalactiteDrip()

	@Override
	protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos position, Fluid fluid)
	{
		this.recieveFluid(level, state, position, fluid);
	} // end recieveStalactiteDrip()



	@Override
	public boolean isFull(BlockState statee)
	{
		return false;
	} // end isFull()

	
	
	public boolean hasVariantFor(@NotNull Fluid fluid) 
	{
		return this.m_variantLookup.containsKey(fluid);
	} // end hasVariantFor()
	
	public void addVariant(@NotNull Fluid fluid, @NotNull BlockState state) 
	{
		this.m_variantLookup.put(Objects.requireNonNull(fluid), Objects.requireNonNull(state));
	} // end addVariant()
	
} // end class