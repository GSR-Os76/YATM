package com.gsr.gsr_yatm.utilities;

@FunctionalInterface
public interface ItemWithdrawalEventHandler
{

	public void onItemWithdrawal(int slot, int amount);
	
} // end interface