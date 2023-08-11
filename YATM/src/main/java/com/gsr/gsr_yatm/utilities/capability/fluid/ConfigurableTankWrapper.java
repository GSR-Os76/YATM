package com.gsr.gsr_yatm.utilities.capability.fluid;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ConfigurableTankWrapper implements IFluidHandler, IFluidTank
{
	private final @NotNull IFluidHandler m_fluidHandler;
	private final @NotNull Supplier<Boolean> m_canDrain;
	private final @NotNull Predicate<FluidStack> m_fillValidator;
	private final @NotNull Consumer<FluidStack> m_onContentsDrain;
	private final @NotNull Consumer<FluidStack> m_onContentsFill;


	
	public ConfigurableTankWrapper(@NotNull IFluidHandler fluidHandler, @NotNull Consumer<FluidStack> contentsChangedHandler) 
	{
		this(fluidHandler, contentsChangedHandler, contentsChangedHandler, () -> true, (f) -> true);
	} // end constructor

	public ConfigurableTankWrapper(@NotNull IFluidHandler fluidHandler, @NotNull Consumer<FluidStack> onContentsDrain, @NotNull Consumer<FluidStack> onContentsFill, @NotNull Supplier<Boolean> canDrain, @NotNull Predicate<FluidStack> fillValidator) 
	{
		this.m_fluidHandler = Objects.requireNonNull(fluidHandler);
		this.m_tank = Lazy.of(() -> (IFluidTank)this.m_fluidHandler);
		
		this.m_canDrain = Objects.requireNonNull(canDrain);
		this.m_fillValidator = Objects.requireNonNull(fillValidator);
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
		FluidStack result = this.m_fluidHandler.drain(maxDrain, action);
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
		private IFluidHandler m_fluidHandler;
		private Supplier<Boolean> m_canDrain = () -> true;
		private Predicate<FluidStack> m_fillValidator = (f) -> true;
		private Consumer<FluidStack> m_onContentsDrain = (f) -> {};
		private Consumer<FluidStack> m_onContentsFill = (f) -> {};

		
		
		protected Builder() 
		{
			
		} // end constructor
		
		public static Builder of() 
		{
			return new Builder();
		} // end of()

		public static Builder of(IFluidHandler fluidHandler) 
		{
			Builder b = new Builder();
			b.m_fluidHandler = fluidHandler;
			return b;
		} // end of()
		
		
		
		public Builder canDrain(Supplier<Boolean> canDrain) 
		{
			this.m_canDrain = canDrain;
			return this;
		} // end drainValidator()
		
		public Builder fillValidator(Predicate<FluidStack> fillValidator) 
		{
			this.m_fillValidator = fillValidator;
			return this;
		} // end fillValidator()
		
		public Builder onContentsDrain(Consumer<FluidStack> onContentsDrain) 
		{
			this.m_onContentsDrain = onContentsDrain;
			return this;
		} // end onContentsDrain()
		
		public Builder onContentsFill(Consumer<FluidStack> onContentsFill) 
		{
			this.m_onContentsFill = onContentsFill;
			return this;
		} // end onContentsFill()
		
		
		
		public ConfigurableTankWrapper build() 
		{
			return new ConfigurableTankWrapper(this.m_fluidHandler, this.m_onContentsDrain, this.m_onContentsFill, this.m_canDrain, this.m_fillValidator);
		} // end builder()
		
	} // end inner class
} // end class