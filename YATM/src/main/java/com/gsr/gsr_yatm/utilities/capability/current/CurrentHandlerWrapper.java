package com.gsr.gsr_yatm.utilities.capability.current;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;

public class CurrentHandlerWrapper implements ICurrentHandler
{
	private final @NotNull ICurrentHandler m_wrapped;
	private Consumer<Integer> m_onCurrentRecieved;
	private Consumer<Integer> m_onCurrentExtracted;
	private boolean m_canRecieve = true;
	private boolean m_canExtract = true;
	
	
	
	public CurrentHandlerWrapper(@NotNull ICurrentHandler wrapped) 
	{
		this.m_wrapped = Objects.requireNonNull(wrapped);
	} // end constructor
	
	
	
	@Override
	public int receiveCurrent(int amount, boolean simulate)
	{
		if(!this.m_canRecieve) 
		{
			
			return 0;
		}
		int r = this.m_wrapped.receiveCurrent(amount, simulate);
		if(!simulate && this.m_onCurrentRecieved != null) 
		{
				this.m_onCurrentRecieved.accept(r);
		}
		return r;
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		if(!this.m_canExtract) 
		{
			return 0;
		}
		
		int e = this.m_wrapped.extractCurrent(amount, simulate);
		if(!simulate && this.m_onCurrentExtracted != null) 
		{
			this.m_onCurrentExtracted.accept(e);			
		}
		return e;
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_wrapped.capacity();
	} // end capacity()

	@Override
	public int stored()
	{
		return this.m_wrapped.stored();
	} // end stored()

	
	
	public static class Builder 
	{
		private @NotNull ICurrentHandler m_wrapped;
		private Consumer<Integer> m_onCurrentRecieved;
		private Consumer<Integer> m_onCurrentExtracted;
		private boolean m_canRecieve = true;
		private boolean m_canExtract = true;
		
		
		
		public static Builder of(@NotNull ICurrentHandler wrapped) 
		{
			Builder b = new Builder();
			b.m_wrapped = Objects.requireNonNull(wrapped);
			return b;
		} // end capacity()
		
		public Builder onCurrentRecieved(Consumer<Integer> onCurrentRecieved) 
		{
			this.m_onCurrentRecieved = onCurrentRecieved;
			return this;
		} // end onCurrentRecieved()
		
		public Builder onCurrentExtracted(Consumer<Integer> onCurrentExtracted) 
		{
			this.m_onCurrentExtracted = onCurrentExtracted;
			return this;
		} // end onCurrentExtracted()
		
		public Builder canRecieve(boolean can) 
		{
			this.m_canRecieve = can;
			return this;
		} // end canRecieve()
		
		public Builder canExtract(boolean can) 
		{
			this.m_canExtract = can;
			return this;
		} // end canExtract()
		
		
		
		public CurrentHandlerWrapper build() 
		{
			CurrentHandlerWrapper temp = new CurrentHandlerWrapper(this.m_wrapped);
			temp.m_onCurrentRecieved = this.m_onCurrentRecieved;
			temp.m_onCurrentExtracted = this.m_onCurrentExtracted;
			temp.m_canRecieve = this.m_canRecieve;
			temp.m_canExtract = this.m_canExtract;
			return temp;
		} // end build()
		
	} // end inner class	
} // end outer class