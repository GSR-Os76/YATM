package com.gsr.gsr_yatm.gui;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

public interface IScreenPositionHandler
{

	@NotNegative int getTopPos();

	@NotNegative int getLeftPos();

} // interface