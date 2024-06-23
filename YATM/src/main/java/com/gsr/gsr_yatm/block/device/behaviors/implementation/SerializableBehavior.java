package com.gsr.gsr_yatm.block.device.behaviors.implementation;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class SerializableBehavior implements ISerializableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ISerializableBehavior.class);
	} // end behaviorTypes()
	
	private final @NotNull Function<HolderLookup.Provider, CompoundTag> m_serializer;
	private final @NotNull BiConsumer<HolderLookup.Provider, CompoundTag> m_deserializer;
	private final @NotNull String m_key;
	
	
	public SerializableBehavior(@NotNull Supplier<CompoundTag> serializer, @NotNull Consumer<CompoundTag> deserializer, @NotNull String key) 
	{
		this.m_serializer = (x) -> Objects.requireNonNull(serializer).get();
		this.m_deserializer = (x, y) -> Objects.requireNonNull(deserializer).accept(y);
		this.m_key = Objects.requireNonNull(key);
	} // end constructor()
	
	public SerializableBehavior(INBTSerializable<CompoundTag> serializer, @NotNull String key) 
	{
		this(serializer::serializeNBT, serializer::deserializeNBT, Objects.requireNonNull(key));
	} // end constructor()
	
	
	@Override
	public @NotNull String key()
	{
		return this.m_key;
	} // end key()

	@Override
	public @Nullable CompoundTag serializeNBT(@NotNull HolderLookup.Provider registryAccess)
	{
		return this.m_serializer.apply(registryAccess);
	} // end 

	@Override
	public void deserializeNBT(@NotNull HolderLookup.Provider registryAccess, @NotNull CompoundTag nbt)
	{
		this.m_deserializer.accept(registryAccess, nbt);
	} // end deserializeNBT();

} // end class