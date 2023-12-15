package com.gsr.gsr_yatm.block.device.behaviors.implementation;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class SerializableBehavior implements ISerializableBehavior
{
	private final @NotNull Supplier<CompoundTag> m_serializer;
	private final @NotNull Consumer<CompoundTag> m_deserializer;
	private final @NotNull String m_key;
	
	
	public SerializableBehavior(@NotNull Supplier<CompoundTag> serializer, @NotNull Consumer<CompoundTag> deserializer, @NotNull String key) 
	{
		this.m_serializer = Objects.requireNonNull(serializer);
		this.m_deserializer = Objects.requireNonNull(deserializer);
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
	public @Nullable CompoundTag serializeNBT()
	{
		return this.m_serializer.get();
	} // end 

	@Override
	public void deserializeNBT(@NotNull CompoundTag nbt)
	{
		this.m_deserializer.accept(nbt);
	} // end deserializeNBT();

} // end class