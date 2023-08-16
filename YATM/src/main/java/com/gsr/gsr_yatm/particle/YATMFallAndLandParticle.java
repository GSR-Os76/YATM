package com.gsr.gsr_yatm.particle;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class YATMFallAndLandParticle extends TextureSheetParticle 
{
	
	private final @NotNull ParticleOptions m_fallingParticle;
	private final @NotNull Fluid m_fluid;
	
	
	
	public YATMFallAndLandParticle(@NotNull ClientLevel level, double d1, double d2, double d3, float gravity, @NotNull Fluid fluid, @NotNull ParticleOptions fallingParticle)
	{
		super(Objects.requireNonNull(level), d1, d2, d3);
		this.lifetime = 60;
		this.gravity = gravity;		
		this.m_fluid = Objects.requireNonNull(fluid);
		this.m_fallingParticle = Objects.requireNonNull(fallingParticle);
		
	} // end constructor()

	
	
	public ParticleRenderType getRenderType()
	{
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	} // end getRenderType()


	
	public void tick()
	{
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.lifetime-- <= 0)
		{
			this.remove();
		}		
		if(this.removed) 
		{
			return;
		}

		this.yd -= (double) this.gravity;
		this.move(this.xd, this.yd, this.zd);
		this.xd *= 0.02d;
		this.yd *= 0.02d;
		this.zd *= 0.02d;
		this.xd *= 0.98d;
		this.yd *= 0.98d;
		this.zd *= 0.98d;
		if (this.m_fluid != Fluids.EMPTY)
		{
			BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
			FluidState fluidstate = this.level.getFluidState(blockPos);
			if (fluidstate.getType() == this.m_fluid && this.y < (double) ((float) blockPos.getY() + fluidstate.getHeight(this.level, blockPos)))
			{
				this.remove();
			}
		}
	} // end tick()

} // end class