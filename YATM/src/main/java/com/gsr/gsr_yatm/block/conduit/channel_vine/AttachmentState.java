package com.gsr.gsr_yatm.block.conduit.channel_vine;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.util.StringRepresentable;

public enum AttachmentState implements StringRepresentable
{
	NONE("none"),
	NEUTRAL("neutral"),
	PUSH("pushing"),
	PULL("pulling");

	
	
	private final @NotNull String m_name;
	
	AttachmentState(@NotNull String name) 
	{
		this.m_name = Objects.requireNonNull(name);
	} // end constructor
	
	
	
	@Override
	public String getSerializedName()
	{
		return this.m_name;
	} // end getSerializedName()
	
} // end enum