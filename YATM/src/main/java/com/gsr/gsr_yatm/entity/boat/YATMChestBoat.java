package com.gsr.gsr_yatm.entity.boat;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.registry.YATMEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class YATMChestBoat extends ChestBoat implements IYATMBoat
{

	public YATMChestBoat(EntityType<? extends YATMChestBoat> type, Level level)
	{
		super(type, level);
	} // end constructor

	public YATMChestBoat(Level level, double x, double y, double z)
	{
		this(YATMEntityTypes.YATM_CHEST_BOAT.get(), level);
		this.setPos(x, y, z);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	} // end constructor



	@Override
	protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder)
	{
		super.defineSynchedData(Objects.requireNonNull(builder));
		builder.define(IYATMBoat.DATA_ID_TYPE, YATMBoatType.RUBBER.ordinal());
	} // end defineSynchedData()



//	@Override
//	public double getPassengersRidingOffset()
//	{
//		return -0.1D;
//	} // end getPassengersRidingOffset()

	@Override
	public Item getDropItem()
	{
		return this.getVariantType().getChestBoat();
	} // end getDropItem()

	protected void addAdditionalSaveData(CompoundTag tag)
	{
		tag.putString(IYATMBoat.TYPE_TAG_KEY_NAME, this.getVariantType().getSerializedName());
	} // end addAdditionalSaveData()

	@Override
	protected void readAdditionalSaveData(CompoundTag tag)
	{
		if (tag.contains(IYATMBoat.TYPE_TAG_KEY_NAME))
		{
			this.setVariantType(YATMBoatType.byName(tag.getString(IYATMBoat.TYPE_TAG_KEY_NAME)));
		}
	} // end readAdditionalSaveData()



	protected void checkFallDamage(double p_38307_, boolean p_38308_, BlockState state, BlockPos position)
	{
		this.lastYd = this.getDeltaMovement().y;
		if (!this.isPassenger())
		{
			if (p_38308_)
			{
				if (this.fallDistance > 3.0F)
				{
					if (this.status != Boat.Status.ON_LAND)
					{
						this.resetFallDistance();
						return;
					}

					this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
					Level level = this.level();
					if (!level.isClientSide && !this.isRemoved())
					{
						this.kill();
						this.getVariantType().getDrops().forEach(this::spawnAtLocation);
					}
				}

				this.resetFallDistance();
			}
			else if (!this.canBoatInFluid(this.level().getFluidState(this.blockPosition().below())) && p_38307_ < 0.0D)
			{
				this.fallDistance -= (float) p_38307_;
			}
		}
	} // end checkFallDamage()

} // end class