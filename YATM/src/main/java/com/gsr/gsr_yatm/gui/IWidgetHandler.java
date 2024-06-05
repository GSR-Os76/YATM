package com.gsr.gsr_yatm.gui;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public interface IWidgetHandler
{

	void removeWidget(@NotNull GuiEventListener widget);

	<T extends GuiEventListener & Renderable & NarratableEntry> @NotNull T addRenderableWidget(@NotNull T widget);

} // end interface