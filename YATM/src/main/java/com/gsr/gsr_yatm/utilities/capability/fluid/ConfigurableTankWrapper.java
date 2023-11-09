package com.gsr.gsr_yatm.utilities.capability.fluid;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ConfigurableTankWrapper implements IFluidHandler, IFluidTank
{
	private final @NotNull IFluidHandler m_fluidHandler;
	// TODO, look over this's functionality and it's usage.
	private final @NotNull Supplier<Boolean> m_canDrain;
	private final @NotNull Predicate<FluidStack> m_fillValidator;
	private final @NotNegative int m_maxTransfer;
	private final @NotNull Consumer<FluidStack> m_onContentsDrain;
	private final @NotNull Consumer<FluidStack> m_onContentsFill;


	
	public ConfigurableTankWrapper(@NotNull IFluidHandler fluidHandler, @NotNull Consumer<FluidStack> contentsChangedHandler) 
	{
		this(fluidHandler, contentsChangedHandler, contentsChangedHandler, () -> true, (f) -> true, Integer.MAX_VALUE);
	} // end constructor

	public ConfigurableTankWrapper(@NotNull IFluidHandler fluidHandler, @NotNull Consumer<FluidStack> onContentsDrain, @NotNull Consumer<FluidStack> onContentsFill, @NotNull Supplier<Boolean> canDrain, @NotNull Predicate<FluidStack> fillValidator, @NotNegative int maxTransfer) 
	{
		this.m_fluidHandler = Objects.requireNonNull(fluidHandler);
		this.m_tank = Lazy.of(() -> (IFluidTank)this.m_fluidHandler);
		
		this.m_canDrain = Objects.requireNonNull(canDrain);
		this.m_fillValidator = Objects.requireNonNull(fillValidator);
		this.m_maxTransfer = Contract.notNegative(maxTransfer);
		this.m_onContentsDrain = Objects.requireNonNull(onContentsDrain);
		this.m_onContentsFill = Objects.requireNonNull(onContentsFill);		
	} // end constructor
	
	
	
	@Override
	public int getTanks()
	{		
		return this.m_fluidHandler.getTanks();
	} // end getTanks

	@Override
	public @NotNull FluidStack getFluidInTank(int tank)
	{
		return this.m_fluidHandler.getFluidInTank(tank);
	} // end getFluidInTank

	@Override
	public int getTankCapacity(int tank)
	{
		return this.m_fluidHandler.getTankCapacity(tank);
	} // end getTankCapacity()

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack)
	{
		return this.m_fluidHandler.isFluidValid(tank, stack) && this.m_fillValidator.test(stack);
	} // end isFluidValid()

	// TODO, contract is unclear on nullability, possibly handle this?
	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		if(!this.m_fillValidator.test(resource)) 
		{
			return 0;
		}
		
		if(resource.getAmount() > this.m_maxTransfer) 
		{
			resource = resource.copy();
			resource.setAmount(this.m_maxTransfer);
		}
		int result = this.m_fluidHandler.fill(resource, action);
		if(action.execute() && result > 0) 
		{
			FluidStack diff = resource.copy();
			diff.setAmount(result);
			this.m_onContentsFill.accept(diff);
		}
		return result;
	} // end fill()

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action)
	{
		if(action.execute() && ! this.m_canDrain.get()) 
		{
			
		}
		
		if(resource.getAmount() > this.m_maxTransfer) 
		{
			resource = resource.copy();
			resource.setAmount(this.m_maxTransfer);
		}
		FluidStack result = this.m_fluidHandler.drain(resource, action);
		if(action.execute() && !result.isEmpty()) 
		{
			this.m_onContentsDrain.accept(result.copy());
		}
		return result;
	} // end drain()

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action)
	{
		FluidStack result = this.m_fluidHandler.drain(maxDrain > this.m_maxTransfer ? this.m_maxTransfer : maxDrain, action);
		if(action.execute() && !result.isEmpty()) 
		{
			this.m_onContentsDrain.accept(result.copy());
		}
		return result;
	} // end drain()
	
	
	
	
	
	private final @NotNull Lazy<IFluidTank> m_tank;
	
	@Override
	public @NotNull FluidStack getFluid()
	{	
		return this.m_tank.get().getFluid();
	} // end getFluid()
	
	@Override
	public int getFluidAmount()
	{
		return this.m_tank.get().getFluidAmount();
	} // end getFluidAmount()
	
	@Override
	public int getCapacity()
	{
		return this.m_tank.get().getCapacity();
	} // end getCapacity
	
	@Override
	public boolean isFluidValid(FluidStack stack)
	{
		return this.m_tank.get().isFluidValid(stack);
	} // end drain()


	
	
	
	public static class Builder
	{
		private @NotNull IFluidHandler m_fluidHandler;
		private @NotNull Supplier<Boolean> m_canDrain = () -> true;
		private @NotNull Predicate<FluidStack> m_fillValidator = (f) -> true;
		private @NotNegative int m_maxTransfer = Integer.MAX_VALUE;
		private @NotNull Consumer<FluidStack> m_onContentsDrain = (f) -> {};
		private @NotNull Consumer<FluidStack> m_onContentsFill = (f) -> {};

		
		
		protected Builder() 
		{
			
		} // end constructor
		
		public static Builder of() 
		{
			return new Builder();
		} // end of()

		public static Builder of(@NotNull IFluidHandler fluidHandler) 
		{
			Builder b = new Builder();
			b.m_fluidHandler = Objects.requireNonNull(fluidHandler);
			return b;
		} // end of()
		
		
		
		public Builder canDrain(@NotNull Supplier<Boolean> canDrain) 
		{
			this.m_canDrain = Objects.requireNonNull(canDrain);
			return this;
		} // end drainValidator()
		
		public Builder fillValidator(@NotNull Predicate<FluidStack> fillValidator) 
		{
			this.m_fillValidator = Objects.requireNonNull(fillValidator);
			return this;
		} // end fillValidator()
		
		public Builder onContentsDrain(@NotNull Consumer<FluidStack> onContentsDrain) 
		{
			this.m_onContentsDrain = Objects.requireNonNull(onContentsDrain);
			return this;
		} // end onContentsDrain()
		
		public Builder onContentsFill(@NotNull Consumer<FluidStack> onContentsFill) 
		{
			this.m_onContentsFill = Objects.requireNonNull(onContentsFill);
			return this;
		} // end onContentsFill()
		
		public Builder onContentsChanged(@NotNull Consumer<FluidStack> onContentsChange) 
		{
			this.m_onContentsFill = Objects.requireNonNull(onContentsChange);
			this.m_onContentsDrain = Objects.requireNonNull(onContentsChange);
			return this;
		} // end onContentsFill()
		
		public Builder maxTransfer(@NotNegative int maxTransfer) 
		{
			this.m_maxTransfer = Contract.notNegative(maxTransfer);
			return this;
		} // end onContentsFill()
		
		
		
		public ConfigurableTankWrapper build() 
		{
			return new ConfigurableTankWrapper(this.m_fluidHandler, this.m_onContentsDrain, this.m_onContentsFill, this.m_canDrain, this.m_fillValidator, this.m_maxTransfer);
		} // end builder()
		
	} // end inner class
} // end class