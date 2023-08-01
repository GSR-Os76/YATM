package com.gsr.gsr_yatm.entity.boat;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.vehicle.Boat;

public interface IYATMBoat
{
	public static final String TYPE_TAG_KEY_NAME = "type";
	public static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

	
	
	private Boat self()
	{
		return (Boat) this;
	} // end self()

	
	
	public default Boat setVariantType(@NotNull YATMBoatType type)
	{
		this.self().getEntityData().set(IYATMBoat.DATA_ID_TYPE, type.ordinal());
		return this.self();
	} // end setVariant()


	public default YATMBoatType getVariantType() 
	{
		return YATMBoatType.fromId(this.self().getEntityData().get(IYATMBoat.DATA_ID_TYPE));
	} // end getVariantType()

} // end class