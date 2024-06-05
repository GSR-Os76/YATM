package com.gsr.gsr_yatm.gui.behavior.fluid.vertical;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.gui.IScreenInitializable;
import com.gsr.gsr_yatm.gui.IScreenPositionHandler;
import com.gsr.gsr_yatm.gui.IScreenRenderable;
import com.gsr.gsr_yatm.gui.IWidgetHandler;
import com.gsr.gsr_yatm.gui.behavior.fluid.IFluidStackInfoProvider;
import com.gsr.gsr_yatm.gui.widget.VerticalStoredFluidWidget;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.client.gui.GuiGraphics;

public class VerticalFluidWidgetBehavior implements IScreenInitializable, IScreenRenderable
{
	private final @NotNull IFluidStackInfoProvider m_fluidInfo;
	private final @NotNull IScreenPositionHandler m_screenPositionHandler;
	private final @NotNull IWidgetHandler m_widgetHandler;
	private final @NotNegative int m_xPos;
	private final @NotNegative int m_yPos;
	private VerticalStoredFluidWidget m_resultTankWidget;	
	
	
	
	public VerticalFluidWidgetBehavior(@NotNull IScreenPositionHandler screenPositionHandler, @NotNull IWidgetHandler widgetHandler, @NotNull IFluidStackInfoProvider fluidInfo, @NotNegative int xPos, @NotNegative int yPos) 
	{
		this.m_fluidInfo = Objects.requireNonNull(fluidInfo);
		this.m_screenPositionHandler = Objects.requireNonNull(screenPositionHandler);
		this.m_widgetHandler = Objects.requireNonNull(widgetHandler);
		this.m_xPos = Contract.notNegative(xPos);
		this.m_yPos = Contract.notNegative(yPos);
	} // end constructor
	
	
	
	@Override
	public void init()
	{
		if(this.m_resultTankWidget != null) 
		{			
			this.m_widgetHandler.removeWidget(this.m_resultTankWidget);
		}
		this.m_resultTankWidget = new VerticalStoredFluidWidget(this.m_screenPositionHandler.getLeftPos() + this.m_xPos, this.m_screenPositionHandler.getTopPos() + this.m_yPos, this.m_fluidInfo.getCapacity(), this.m_fluidInfo.getFluid());
		this.m_widgetHandler.addRenderableWidget(this.m_resultTankWidget);
	} // end init()
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
	{
		if(this.m_resultTankWidget == null || this.m_fluidInfo.getCapacity() != this.m_resultTankWidget.getCapacity()) 
		{
			this.init();
		}
		if(this.m_resultTankWidget.getFluid() != this.m_fluidInfo.getFluid()) 
		{
			this.m_resultTankWidget.setStoredFluid(this.m_fluidInfo.getFluid());
		}
		this.m_resultTankWidget.setStoredAmount(this.m_fluidInfo.getAmount());
	} // end render()
	
} // end class