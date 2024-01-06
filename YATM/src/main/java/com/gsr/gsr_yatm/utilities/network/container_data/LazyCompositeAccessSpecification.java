package com.gsr.gsr_yatm.utilities.network.container_data;

import java.util.function.Supplier;

public class LazyCompositeAccessSpecification implements ICompositeAccessSpecification
{
	private final Supplier<ICompositeAccessSpecification> m_supplier;
	private ICompositeAccessSpecification m_wrapped;
	
	
	
	public LazyCompositeAccessSpecification(Supplier<ICompositeAccessSpecification> supplier)
	{
		this.m_supplier = supplier;
	} // end constructor

	
	
	@Override
	public AccessSpecification get(String key)
	{
		if(this.m_wrapped == null) 
		{
			this.m_wrapped = this.m_supplier.get();
			if(this.m_wrapped == null) 
			{
				throw new IllegalStateException("CompositeAccessSpecification hasn't been initialize yet.");
			}
		}
		return this.m_wrapped.get(key);
	} // end get()

	@Override
	public int getCount()
	{
		if(this.m_wrapped == null) 
		{
			this.m_wrapped = this.m_supplier.get();
			if(this.m_wrapped == null) 
			{
				throw new IllegalStateException("CompositeAccessSpecification hasn't been initialize yet.");
			}
		}
		return this.m_wrapped.getCount();
	} // end getCount()

} // end class