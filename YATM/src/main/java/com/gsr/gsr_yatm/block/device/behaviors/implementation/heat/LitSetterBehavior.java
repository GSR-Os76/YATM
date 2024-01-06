package com.gsr.gsr_yatm.block.device.behaviors.implementation.heat;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LitSetterBehavior implements ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ITickableBehavior.class);
	} // end behaviorTypes()

	private final @NotNull IHeatHandler m_heatHandler;
	private final @NotNull Supplier<@NotNull Integer> m_litThreshold;
	
	
	
	public LitSetterBehavior(@NotNull IHeatHandler heatHandler, @NotNull Supplier<@NotNull Integer> litThreshold)
	{
		this.m_heatHandler = Objects.requireNonNull(heatHandler);
		this.m_litThreshold = Objects.requireNonNull(litThreshold);
	} // end constructor()
	
	
	
	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		boolean shouldBeLit = this.m_heatHandler.getTemperature() > this.m_litThreshold.get();
		BlockState state = level.getBlockState(position);
		if(state.getValue(YATMBlockStateProperties.LIT) ^ shouldBeLit) 
		{
			level.setBlock(position, state.setValue(YATMBlockStateProperties.LIT, shouldBeLit), Block.UPDATE_CLIENTS);
		}
		return false;
	} // end tick()

} // end class