package com.gsr.gsr_yatm.gui;

import net.minecraft.client.gui.GuiGraphics;

public interface IScreenRenderable
{
	void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);
} // end interface