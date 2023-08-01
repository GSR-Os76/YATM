package com.gsr.gsr_yatm.entity.boat;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.NonNullList;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.Lazy;

public enum YATMBoatType implements StringRepresentable
{
	RUBBER("gsr_yatm:rubber", () -> YATMItems.RUBBER_BOAT_ITEM.get(), () -> YATMItems.RUBBER_CHEST_BOAT_ITEM.get(), NonNullList.<Supplier<Item>>of(() -> Items.AIR, () -> YATMItems.RUBBER_PLANKS_ITEM.get(), () -> YATMItems.RUBBER_PLANKS_ITEM.get(), () -> YATMItems.RUBBER_PLANKS_ITEM.get(), () -> Items.STICK, () -> Items.STICK)), SOUL_AFFLICTED_RUBBER("gsr_yatm:soul_afflicted_rubber", () -> YATMItems.SOUL_AFFLICTED_RUBBER_BOAT_ITEM.get(), () -> YATMItems.SOUL_AFFLICTED_RUBBER_CHEST_BOAT_ITEM.get(), NonNullList.of(() -> Items.AIR, () -> YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS_ITEM.get(), () -> YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS_ITEM.get(), () -> YATMItems.SOUL_AFFLICTED_RUBBER_PLANKS_ITEM.get(), () -> Items.STICK, () -> Items.STICK));



	public static final BiMap<String, YATMBoatType> TYPES_BY_IDENTIFIER = YATMBoatType.createTypeByIdentifierMap();

	private final @NotNull Supplier<Item> m_boat;
	private final @NotNull Supplier<Item> m_chestBoat;
	private final @NotNull Lazy<List<Item>> m_drops;
	private final @NotNull String m_identifier;

	private YATMBoatType(@NotNull String identifier, @NotNull Supplier<Item> boat, @NotNull Supplier<Item> chestBoat, @NotNull NonNullList<Supplier<Item>> drops)
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_boat = Objects.requireNonNull(boat);
		this.m_chestBoat = Objects.requireNonNull(chestBoat);
		Objects.requireNonNull(drops).forEach(Objects::requireNonNull);
		this.m_drops = Lazy.of(() -> ImmutableList.copyOf(drops.stream().map((s) -> s.get()).toList()));
	} // end constructor



	public String getIdentifier()
	{
		return this.m_identifier;
	} // end getIdentifier()

	@Override
	public String getSerializedName()
	{
		return this.getIdentifier();
	} // end getSerializedName()



	public static @NotNull YATMBoatType byName(@NotNull String string)
	{
		return TYPES_BY_IDENTIFIER.get(string);
	} // end byName()

	public static @NotNull YATMBoatType fromId(int i)
	{
		return YATMBoatType.values()[i];
	} // end fromId()



	public @NotNull Item getBoat()
	{
		return this.m_boat.get();
	} // end getBoat()

	public @NotNull Item getChestBoat()
	{
		return this.m_chestBoat.get();
	} // end getBoat()

	public @NotNull List<Item> getDrops()
	{
		return this.m_drops.get();
	} // end getDrops()



	private static BiMap<String, YATMBoatType> createTypeByIdentifierMap()
	{
		ImmutableBiMap.Builder<String, YATMBoatType> builder = new ImmutableBiMap.Builder<>();
		Stream.of(YATMBoatType.values()).map((v) -> Map.entry(v.getSerializedName(), v)).forEach(builder::put);
		return builder.build();
	} // end createTypeByIdentifierMap()

} // end enum