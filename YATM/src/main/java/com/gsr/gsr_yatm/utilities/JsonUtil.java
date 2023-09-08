package com.gsr.gsr_yatm.utilities;

import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtil
{
	// ugh, no extension methods
	public static void ifPresent(JsonObject object, String elementKey, Consumer<JsonElement> ifPresent) 
	{
		if(object.has(elementKey)) 
		{
			ifPresent.accept(object.get(elementKey));
		}
	} // end ifPresent
	
} // end class