package com.gsr.gsr_yatm.api.capability;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;

public record DataPacket(@Nullable String authorAddress, @Nullable String recipientAddress, @NotNull CompoundTag data)
{
	public static final String AUTHOR_TAG_NAME = "author";	
	public static final String RECIPIENT_TAG_NAME = "recipient";	
	public static final String DATA_TAG_NAME = "data";	

	public static final String PACKETS_COUNT_TAG_NAME = "packetsCount";	
	
	
	
	public static final String MESSAGE_TYPE_KEY = "type";	
	public static final String REQUEST_MESSAGE_TYPE_VALUE = "request";
	public static final String DATA_FORMAT_KEY = "format";
	
	public static final String RETURN_ADDRESS_KEY = "returnAddress";	
	
	
	
	public boolean isRequest() 
	{
		return this.data().contains(MESSAGE_TYPE_KEY) && 
				this.data().getString(MESSAGE_TYPE_KEY).equals(REQUEST_MESSAGE_TYPE_VALUE);
	} // end isRequest()
	
	public @Nullable String getReturnAddress() 
	{
		return this.data().contains(RETURN_ADDRESS_KEY) ? this.data().getString(RETURN_ADDRESS_KEY) : null;
	} // end getReturnAddress()
	
	
	
	public @NotNull CompoundTag serializeNBT() 
	{
		CompoundTag tag = new CompoundTag();
		if(this.authorAddress() != null) 
		{
			tag.putString(AUTHOR_TAG_NAME, this.authorAddress);
		}
		if(this.recipientAddress() != null) 
		{
			tag.putString(RECIPIENT_TAG_NAME, this.recipientAddress);
		}
		tag.put(DATA_TAG_NAME, this.data());
		return tag;
	} // end serializeNBT()
	
	public static @Nullable DataPacket deserializeNBT(@NotNull CompoundTag tag) 
	{
		String authorAd = null;
		String recipientAd = null;
		if(tag.contains(AUTHOR_TAG_NAME)) 
		{
			authorAd = tag.getString(AUTHOR_TAG_NAME);
		}
		if(tag.contains(RECIPIENT_TAG_NAME)) 
		{
			recipientAd = tag.getString(RECIPIENT_TAG_NAME);
		}
		if(tag.contains(DATA_TAG_NAME)) 
		{
			return new DataPacket(authorAd, recipientAd, tag.getCompound(DATA_TAG_NAME));
		}
		return null;
	} // end deserializeNBT()
	
	
	
	// use TagUtility version
	@Deprecated()
	public static @NotNull CompoundTag serializeCollection(@NotNull Collection<DataPacket> packets)
	{
		CompoundTag res = new CompoundTag();
		res.putInt(PACKETS_COUNT_TAG_NAME, packets.size());
		
		Integer i = 0;
		for(DataPacket packet : packets) 
		{
			res.put(i.toString(), packet.serializeNBT());
			i++;
		}
		return res;
	} // end serializeCollection()
	
	// use TagUtility version
	@Deprecated()
	public static<T extends Collection<DataPacket>> @NotNull T deserializeCollection(@NotNull CompoundTag tag, @NotNull T toFill) 
	{
		for(Integer i = 0; i < tag.getInt(PACKETS_COUNT_TAG_NAME); i++) 
		{
			toFill.add(DataPacket.deserializeNBT(tag.getCompound(i.toString())));
		}
		return toFill;
	} // end deserializeCollection()
	
} // end record