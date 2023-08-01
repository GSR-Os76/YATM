package com.gsr.gsr_yatm.item;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.entity.boat.YATMBoat;
import com.gsr.gsr_yatm.entity.boat.YATMBoatType;
import com.gsr.gsr_yatm.entity.boat.YATMChestBoat;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class YATMBoatItem extends BoatItem
{
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

	private final boolean m_hasChest;
	private final @NotNull YATMBoatType m_type;



	// TODO, debate marking properties as @NotNull, as it's part of minecrafts contracts and not ours
	public YATMBoatItem(boolean hasChest, @NotNull YATMBoatType type, @NotNull Properties properties)
	{
		super(hasChest, (Boat.Type) null, Objects.requireNonNull(properties));

		this.m_hasChest = hasChest;
		this.m_type = Objects.requireNonNull(type);
	} // end constructor


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack held = player.getItemInHand(hand);
		HitResult hitresult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (hitresult.getType() == HitResult.Type.MISS)
		{
			return InteractionResultHolder.pass(held);
		}
		else
		{
			List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(player.getViewVector(1.0F).scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
			if (!list.isEmpty())
			{
				Vec3 eyePos = player.getEyePosition();
				for (Entity entity : list)
				{
					AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
					if (aabb.contains(eyePos))
					{
						return InteractionResultHolder.pass(held);
					}
				}
			}

			if (hitresult.getType() == HitResult.Type.BLOCK)
			{
				Boat boat = this.getBoat(level, hitresult);
				boat.setYRot(player.getYRot());
				if (!level.noCollision(boat, boat.getBoundingBox()))
				{
					return InteractionResultHolder.fail(held);
				}
				else
				{
					if (!level.isClientSide)
					{
						level.addFreshEntity(boat);
						level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
						if (!player.getAbilities().instabuild)
						{
							held.shrink(1);
						}
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(held, level.isClientSide());
				}
			}
			else
			{
				return InteractionResultHolder.pass(held);
			}
		}
	} // end use()

	private Boat getBoat(Level level, HitResult hitResult)
	{
		Vec3 hitLoc = hitResult.getLocation();
		return (Boat) (this.m_hasChest 
				? new YATMChestBoat(level, hitLoc.x, hitLoc.y, hitLoc.z).setVariantType(this.m_type)
				: new YATMBoat(level, hitLoc.x, hitLoc.y, hitLoc.z).setVariantType(this.m_type));
	} // end getBoat()

} // end class