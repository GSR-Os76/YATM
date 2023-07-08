package com.gsr.gsr_yatm.mob_effect;

import com.gsr.gsr_yatm.YetAnotherTechMod;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class InstantTeleportationMobEffect extends MobEffect
{
	public static final int RETRY_COUNT = 16;
	private final SoundEvent m_sound;
	
	
	
	public InstantTeleportationMobEffect(MobEffectCategory category, int color, SoundEvent sound)
	{
		super(category, color);
		this.m_sound = sound;
	} // end category

	

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier)
	{
		YetAnotherTechMod.LOGGER.info("applyEffectTick");
		Level level = entity.level();
		if (!level.isClientSide)
		{
			YetAnotherTechMod.LOGGER.info("was not client");
			double sourceX = entity.getX();
			double sourceY = entity.getY();
			double sourceZ = entity.getZ();
			double maxRange = (double)(amplifier + 1);
			for (int i = 0; i < RETRY_COUNT; ++i)
			{
				// center random double on zero and scale according to amplitude, never exceeding the levels bounds
				double destinationX = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * maxRange;
				double destinationY = Mth.clamp(entity.getY() + ((double) (entity.getRandom().nextInt(amplifier + 1)) - (maxRange / 2d)), 
						(double) level.getMinBuildHeight(), 
						(double) (level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1));
				double destinationZ = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * maxRange;
				if (entity.isPassenger())
				{
					entity.stopRiding();
				}

				Vec3 sourcePos = entity.position();
				level.gameEvent(GameEvent.TELEPORT, sourcePos, GameEvent.Context.of(entity));

				if (entity.randomTeleport(destinationX, destinationY, destinationZ, true))
				{
					if (this.m_sound != null)
					{
						level.playSound((Player) null, sourceX, sourceY, sourceZ, this.m_sound, SoundSource.PLAYERS, 1.0F, 1.0F);
						entity.playSound(this.m_sound, 1.0F, 1.0F);
						break;
					}
				}
			}
		}
	} // end applyEffectTick()

	@Override
	public boolean isDurationEffectTick(int p_19455_, int p_19456_)
	{
		return true;
	}
	
} // end class