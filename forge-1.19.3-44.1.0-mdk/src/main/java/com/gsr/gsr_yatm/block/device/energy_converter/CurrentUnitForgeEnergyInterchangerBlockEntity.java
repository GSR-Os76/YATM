package com.gsr.gsr_yatm.block.device.energy_converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.api.implementation.CurrentUnitForgeEnergyInterchanger;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class CurrentUnitForgeEnergyInterchangerBlockEntity extends BlockEntity
{
	private int m_storageCapacity = (int)Math.pow(2d, 8d);
	
	private CurrentUnitForgeEnergyInterchanger m_interchanger = new CurrentUnitForgeEnergyInterchanger(this.m_storageCapacity);
	private LazyOptional<ICurrentHandler> m_storageHolderCUs = LazyOptional.of(() -> this.m_interchanger);
	private LazyOptional<IEnergyStorage> m_storageHolderFEs = LazyOptional.of(() -> this.m_interchanger);
	
	
	public CurrentUnitForgeEnergyInterchangerBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(YATMBlockEntityTypes.C_U_F_E_I_BLOCK_ENTITY.get(), blockPos, blockState);
	} // end CurrentUnitForgeEnergyInterchangerBlockEntity()

	
	
	
	


	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
		{
			return this.m_storageHolderCUs.cast();
		}
		if(cap == ForgeCapabilities.ENERGY) 
		{
			return this.m_storageHolderFEs.cast();
		}
		
		return super.getCapability(cap, side);
	} // end getCapability()
	
	@Override
	public void invalidateCaps()
	{
		this.m_storageHolderCUs.invalidate();
		this.m_storageHolderFEs.invalidate();
		super.invalidateCaps();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_storageHolderCUs = LazyOptional.of(() -> this.m_interchanger);
		this.m_storageHolderFEs = LazyOptional.of(() -> this.m_interchanger);
	} // end reviveCaps()
	
} // end class