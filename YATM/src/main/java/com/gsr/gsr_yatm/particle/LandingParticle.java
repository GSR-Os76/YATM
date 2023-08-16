package com.gsr.gsr_yatm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public class LandingParticle extends TextureSheetParticle
{

	public LandingParticle(ClientLevel level, double d1, double d2, double d3, double d4, double d5, double d6)
	{
		super(level, d1, d2, d3, d4, d5, d6);
	} // end constructor

	
	
	@Override
	public ParticleRenderType getRenderType()
	{
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	} // end getRenderType()

} // end class