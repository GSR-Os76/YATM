package com.gsr.gsr_yatm.block.device.sap_collector;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.item.fluid.FluidBottleItem;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.fluid.BinaryFluidHandler;
import com.gsr.gsr_yatm.utilities.capability.fluid.ConfigurableTankWrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class SapCollectorBlockEntity extends BlockEntity
{
	public static final String FLUID_HANDLER_TAG_NAME = "fluidHandler";
	// TODO, put constant somewhere else better
	public static final int BOTTLE_FLUID_QUANTITY = FluidBottleItem.BOTTLE_CAPACITY;

	
	private final BinaryFluidHandler m_rawFluidHandler = new BinaryFluidHandler(SapCollectorBlockEntity.BOTTLE_FLUID_QUANTITY);
	private IFluidHandler m_fluidHandler;
	private LazyOptional<IFluidHandler> m_cap;
	
	

	public SapCollectorBlockEntity(BlockPos position, BlockState state)
	{
		super(YATMBlockEntityTypes.SAP_COLLECTOR.get(), position, state);
		this.setup();
	} // end constructor
	
	public SapCollectorBlockEntity(BlockPos position, BlockState state, Fluid holding)
	{
		this(position, state);
		this.m_rawFluidHandler.fill(new FluidStack(holding, SapCollectorBlockEntity.BOTTLE_FLUID_QUANTITY), FluidAction.EXECUTE);
	} // end constructor
	
	protected void setup() 
	{
		ISapCollector sc = (ISapCollector)this.getBlockState().getBlock();
		this.m_fluidHandler = ConfigurableTankWrapper.Builder.of(this.m_rawFluidHandler)
				.canDrain(() -> this.getLevel() != null)
				.fillValidator((f) -> this.getLevel() != null && sc.recievableFluids().contains(f.getFluid()))
				.onContentsDrain((f) -> sc.onFluidDrained(this.getLevel(), this.getBlockState(), this.getBlockPos(), f))
				.onContentsFill((f) -> sc.onFluidFilled(this.getLevel(), this.getBlockState(), this.getBlockPos(), f))
				.build();
		this.defineCap();
	} // end setup()
	
	

	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put(SapCollectorBlockEntity.FLUID_HANDLER_TAG_NAME, this.m_rawFluidHandler.serializeNBT());
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(SapCollectorBlockEntity.FLUID_HANDLER_TAG_NAME)) 
		{
			this.m_rawFluidHandler.deserializeNBT(tag.getCompound(SapCollectorBlockEntity.FLUID_HANDLER_TAG_NAME));
		}
		this.setup();
	} // end load()

	

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == ForgeCapabilities.FLUID_HANDLER) 
		{
			if(side == null || side == Direction.DOWN) 
			{
				return this.m_cap.cast();
			}
		}
		return super.getCapability(cap, side);
	} // end getCapability()
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_cap.invalidate();
	}
	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.defineCap();
	} // end reviveCaps()
	
	
	
	protected void defineCap() 
	{
		this.m_cap = LazyOptional.of(() -> this.m_fluidHandler);
	} // end defineCap();
	
} // end class