package com.gsr.gsr_yatm.utilities.tag;

import java.util.Collection;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class TagUtilities
{
	public static final String COUNT_TAG_NAME = "count";
	
	
	
	public static @NotNull CompoundTag serializeCollection(@NotNull Collection<CompoundTag> collection)
	{
		return TagUtilities.serializeCollection(collection, (t) -> t);
	} // end serializeCollection()
	
	public static<T extends Collection<CompoundTag>> @NotNull T deserializeCollection(@NotNull CompoundTag tag, @NotNull T toFill) 
	{
		return TagUtilities.deserializeCollection(tag, toFill, (t) -> t);
	} // end deserializeCollection()
	
	
	
	public static <T> @NotNull CompoundTag serializeCollection(@NotNull Collection<T> collection, @NotNull Function<T, Tag> memberSerializer)
	{
		CompoundTag res = new CompoundTag();
		res.putInt(COUNT_TAG_NAME, collection.size());
		
		Integer i = 0;
		for(T member : collection) 
		{
			res.put(i.toString(), memberSerializer.apply(member));
			i++;
		}
		return res;
	} // end serializeCollection()
	
	public static<T, C extends Collection<T>> @NotNull C deserializeCollection(@NotNull CompoundTag tag, @NotNull C toFill,@NotNull Function<CompoundTag, T> memberDeserializer) 
	{
		for(Integer i = 0; i < tag.getInt(COUNT_TAG_NAME); i++) 
		{
			toFill.add(memberDeserializer.apply(tag.getCompound(i.toString())));
		}
		return toFill;
	} // end deserializeCollection()
	
} // end class