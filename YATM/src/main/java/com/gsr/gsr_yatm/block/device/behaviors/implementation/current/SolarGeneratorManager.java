package com.gsr.gsr_yatm.block.device.behaviors.implementation.current;

import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.block.device.solar.SolarPanelSettings;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SolarGeneratorManager implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()
	
	private final @NotNull ICurrentHandler m_acceptor; 
	private final @NotNull SolarPanelSettings m_settings;
	
	
	
	public SolarGeneratorManager(@NotNull ICurrentHandler acceptor, @NotNull SolarPanelSettings settings) 
	{
		this.m_acceptor = Objects.requireNonNull(acceptor);
		this.m_settings = Objects.requireNonNull(settings);
	} // end constructors


	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		// TODO, SolarGeneratorSettings separated out from SolarPanelSettings.
		// TODO, Durings and Withouts for everything, no assumed 100%s.
		float cptF = (float)this.m_settings.currentPerTick();		
		cptF *= level.isDay() ? this.m_settings.percentDuringDay() : 1f;
		cptF *= level.isNight() ? this.m_settings.percentDuringNight() : 1f;
		cptF *= level.isRainingAt(position) ? this.m_settings.percentDuringPrecipitation() : 1f;
		cptF *= level.canSeeSky(position.above()) ? 1f : this.m_settings.percentWithoutSky();
		
		int cpt = (int)cptF;
		if(cpt > 0) 
		{
			this.m_acceptor.receiveCurrent(cpt, false);
			return true;
		}
		return false;
	} // end tick()
	
} // end class