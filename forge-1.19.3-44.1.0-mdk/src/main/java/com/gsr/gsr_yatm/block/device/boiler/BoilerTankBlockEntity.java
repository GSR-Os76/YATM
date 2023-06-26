package com.gsr.gsr_yatm.block.device.boiler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.ConfigurableTankWrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

// microDimension coupler/aggregator/generator/stabilizer/blah
// TODO verify the pumping/filling/capability/tank functionality of this actually works
public class BoilerTankBlockEntity extends BlockEntity
{
	private static final String CAPACITY_TAG_NAME = "capacity";
	private static final String TANK_TAG_NAME = "tank";	
	private static final String MAX_TRANSFER_RATE_TAG_NAME = "rate";
	
	private static final int RECHECK_ACCEPTER_RATE = 12;
	
	private int m_maxTransferRate;
	private FluidTank m_rawContents= new FluidTank(32000);
	private ConfigurableTankWrapper m_contents= new ConfigurableTankWrapper(this.m_rawContents, () -> this.setChanged());;
	private LazyOptional<IFluidHandler> m_contentsLazyOptional = LazyOptional.of(() -> this.m_contents);;
	
	private boolean m_paired = false;
	private IFluidHandler m_pairedCap;
	
	private int m_tick = RECHECK_ACCEPTER_RATE;
	
	
	
	public BoilerTankBlockEntity(BlockPos blockPos, BlockState blockState) 
	{
		this(blockPos, blockState, 512, 32000);
	} // end constructor

	// add this place(s)
	public BoilerTankBlockEntity(BlockPos blockPos, BlockState blockState, int outputRate, int tankCapacity)
	{
		super(YATMBlockEntityTypes.BOILER_TANK.get(), blockPos, blockState);

		this.m_maxTransferRate = outputRate;
		//this.m_rawContents = new FluidTank(tankCapacity);
		//this.m_contents = new ConfigurableTankWrapper(this.m_rawContents, () -> this.setChanged());
		
		//this.m_contentsLazyOptional = LazyOptional.of(() -> this.m_contents);
	} // end constructor
	
	
	
	public static void tick(Level level, BlockPos pos, BlockState blockState, BoilerTankBlockEntity blockEntity) 
	{
		if(!level.isClientSide()) 
		{
			blockEntity.serverTick(level, pos, blockState, blockEntity);
		}
	} // end tick()
	
	private void serverTick(Level level, BlockPos pos, BlockState blockState, BoilerTankBlockEntity blockEntity) 
	{
		if(this.m_paired) 
		{
//			YetAnotherTechMod.LOGGER.info("paired");
			if(this.m_pairedCap == null) 
			{
				this.acquireBelowCapability(level, pos);
			}
			else 
			{
				// YetAnotherTechMod.LOGGER.info("try to fill below enity");
				this.m_pairedCap.fill(this.m_contents.drain(this.m_pairedCap.fill(this.m_contents.drain(this.m_maxTransferRate, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
			}
		}
		if ((this.m_tick++ > RECHECK_ACCEPTER_RATE)) 
		{
			// YetAnotherTechMod.LOGGER.info("recheck: " + this.m_contents.getFluid() + ", " + this.m_contents.getFluidAmount());
			
			this.m_tick = 0;
			this.acquireBelowCapability(level, pos);
		}		
	} // end serverTick()

	
	
	private void acquireBelowCapability(Level level, BlockPos pos) 
	{
		BlockEntity below = level.getBlockEntity(pos.below());
		if(below != null) 
		{
			LazyOptional<IFluidHandler> c = below instanceof BoilerBlockEntity bbe ? bbe.getInputTank() : below.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP);
			IFluidHandler f = null;			
			
			if(c != null && c.isPresent()) 
			{					
				f = c.orElse(null);				
			}
			
			if(f != null) 
			{
				// YetAnotherTechMod.LOGGER.info("pairing");
				this.m_paired = true;
				this.m_pairedCap = f;
				c.addListener(s -> unpair());
			}
		}
	} // end acquireBelowCapability
	
	private void unpair() 
	{
		this.m_paired = false;
		this.m_pairedCap = null;
	} // end unpair()
	
	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.putInt(CAPACITY_TAG_NAME, this.m_contents.getCapacity());
		tag.put(TANK_TAG_NAME, this.m_rawContents.writeToNBT(new CompoundTag()));
		tag.putInt(MAX_TRANSFER_RATE_TAG_NAME, this.m_maxTransferRate);
		
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
//		if(tag.contains(CAPACITY_TAG_NAME)) 
//		{
//			YetAnotherTechMod.LOGGER.info("capacity tag being loaded from");
//			this.m_rawContents = new FluidTank(tag.getInt(CAPACITY_TAG_NAME));
//			this.m_contents = new ConfigurableTankWrapper(this.m_rawContents, () -> this.setChanged());
//			this.m_contentsLazyOptional = LazyOptional.of(() -> this.m_contents);
//		}
		if(tag.contains(TANK_TAG_NAME)) 
		{
			this.m_rawContents.readFromNBT(tag.getCompound(TANK_TAG_NAME));
		}
		if(tag.contains(MAX_TRANSFER_RATE_TAG_NAME)) 
		{
			this.m_maxTransferRate = tag.getInt(MAX_TRANSFER_RATE_TAG_NAME);
		}
	} // end load()



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == ForgeCapabilities.FLUID_HANDLER) 
		{
			if(side == null || side == Direction.UP || side == Direction.DOWN) 
			{
				return this.m_contentsLazyOptional.cast();
			}
//			if(side == null) 
//			{
//				return this.m_contentsLazyOptional.cast();
//			}
//			else if(side == Direction.UP) 
//			{
//				return this.m_contentsLazyOptional.cast();
//			}
//			else if(side == Direction.DOWN && !this.m_paired) 
//			{
//				return this.m_contentsLazyOptional.cast();
//			}
		}
		return super.getCapability(cap, side);
	} // end getCapability()
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_contentsLazyOptional.invalidate();
	} // end invalidateCaps()
	
	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_contentsLazyOptional = LazyOptional.of(() -> this.m_contents);
	} // end reviveCaps()
	
	
} // end class
	
	//TryPairToTank();
//	
//	
//	private void TryPairToTank() 
//	{
//		if(this.canPairToTank()) 
//		{
//			this.pairToTank();
//		}
//	}
//	
//	private boolean canPairToTank() 
//	{
//		return MergingTankWrapper.canMerge(this.m_contents, this.m_boilerInputTank);
//	} // end canPairToTank()
//	
//	private void pairToTank() 
//	{
//		this.m_paired = true;
//		LazyOptional<IFluidHandler> reserve = this.m_contentsLazyOptional;
//		this.m_contentsLazyOptional = LazyOptional.of(() -> new MergingTankWrapper(this.m_contents, this.m_boilerInputTank));
//		reserve.invalidate();
//	} // end pairToTank()
//	
//	public void loseBoiler() 
//	{
//		this.m_paired = false;
//		LazyOptional<IFluidHandler> reserve = this.m_contentsLazyOptional;
//		this.m_contentsLazyOptional = LazyOptional.of(() -> this.m_contents);
//		reserve.invalidate();
//	} // end loseBoiler()