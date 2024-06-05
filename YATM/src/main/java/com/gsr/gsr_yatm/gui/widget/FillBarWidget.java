package com.gsr.gsr_yatm.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public abstract class FillBarWidget extends AbstractWidget
{
	protected boolean m_clickable = false;
	
	
	
	public FillBarWidget(int x, int y, int width, int height, Component message)
	{
		super(x, y, width, height, message);
	} // end constructor

	@Override
	protected void updateWidgetNarration(NarrationElementOutput output)
	{
		// TODO, understand and add
	} // end updateWidgetNarration()
	
	
	
	@Override
	public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_)
	{
		// TODO test if prevents being clicked
		return this.m_clickable ? super.mouseClicked(p_93641_, p_93642_, p_93643_) : false;
	} // end mouseClicked()
	
} // end class