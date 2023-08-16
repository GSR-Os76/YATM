package com.gsr.gsr_yatm.utilities;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.particle.LandingParticle;
import com.gsr.gsr_yatm.particle.TappedLogDripParticle;
import com.gsr.gsr_yatm.particle.YATMFallAndLandParticle;
import com.gsr.gsr_yatm.registry.YATMFluids;
import com.gsr.gsr_yatm.registry.YATMParticleTypes;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YATMParticleProviders
{
	public static final ParticleProvider.Sprite<SimpleParticleType> DRIPPING_TAPPED_LOG_LATEX = (pt, cl, d1, d2, d3, d4, d5, d6) -> YATMParticleProviders.createTappedLogDripParticle(pt, cl, d1, d2, d3, d4, d5, d6, 0.01f, YATMFluids.LATEX.get(), YATMParticleTypes.FALLING_LATEX.get());
	public static final ParticleProvider.Sprite<SimpleParticleType> DRIPPING_TAPPED_LOG_SOUL_SAP = (pt, cl, d1, d2, d3, d4, d5, d6) -> YATMParticleProviders.createTappedLogDripParticle(pt, cl, d1, d2, d3, d4, d5, d6, 0.01f, YATMFluids.SOUL_SAP.get(), YATMParticleTypes.FALLING_SOUL_SAP.get());
	public static final ParticleProvider.Sprite<SimpleParticleType> FALLING_TAPPED_LOG_LATEX = (pt, cl, d1, d2, d3, d4, d5, d6) -> YATMParticleProviders.createFallingDripParticle(pt, cl, d1, d2, d3, d4, d5, d6, 0.06f, YATMFluids.SOUL_SAP.get(), YATMParticleTypes.LANDING_LATEX.get());
	public static final ParticleProvider.Sprite<SimpleParticleType> FALLING_TAPPED_LOG_SOUL_SAP = (pt, cl, d1, d2, d3, d4, d5, d6) -> YATMParticleProviders.createFallingDripParticle(pt, cl, d1, d2, d3, d4, d5, d6, 0.06f, YATMFluids.SOUL_SAP.get(), YATMParticleTypes.LANDING_SOUL_SAP.get());
	public static final ParticleProvider.Sprite<SimpleParticleType> LANDING_LATEX = (pt, cl, d1, d2, d3, d4, d5, d6) -> YATMParticleProviders.createLandingParticle(pt, cl, d1, d2, d3, d4, d5, d6);
	public static final ParticleProvider.Sprite<SimpleParticleType> LANDING_SOUL_SAP = (pt, cl, d1, d2, d3, d4, d5, d6) -> YATMParticleProviders.createLandingParticle(pt, cl, d1, d2, d3, d4, d5, d6);
	
	
	
	public static YATMFallAndLandParticle createFallingDripParticle(@Nullable SimpleParticleType particleType, @NotNull ClientLevel level, double d1, double d2, double d3, double d4, double d5, double d6, float gravity, @NotNull Fluid fluid, @NotNull ParticleOptions fallingParticle)
	{
		YATMFallAndLandParticle p = new YATMFallAndLandParticle(Objects.requireNonNull(level), d1, d2, d3, gravity, Objects.requireNonNull(fluid), Objects.requireNonNull(fallingParticle));
		return p;
	} // end createFallingDripParticle()
	
	public static LandingParticle createLandingParticle(@Nullable SimpleParticleType particleType, @NotNull ClientLevel level, double d1, double d2, double d3, double d4, double d5, double d6)
	{
		LandingParticle p = new LandingParticle(Objects.requireNonNull(level), d1, d2, d3, d4, d5, d6);
		return p;
	} // end createLandingParticle()
	
	public static TappedLogDripParticle createTappedLogDripParticle(@Nullable SimpleParticleType particleType, @NotNull ClientLevel level, double d1, double d2, double d3, double d4, double d5, double d6, float gravity, @NotNull Fluid fluid, @NotNull ParticleOptions fallingParticle)
	{
		TappedLogDripParticle p = new TappedLogDripParticle(Objects.requireNonNull(level), d1, d2, d3, gravity, Objects.requireNonNull(fluid), Objects.requireNonNull(fallingParticle));
		return p;
	} // end createTappedLogDripParticle()
	
} // end class