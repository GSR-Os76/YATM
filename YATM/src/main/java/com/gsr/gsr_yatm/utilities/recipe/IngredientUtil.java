package com.gsr.gsr_yatm.utilities.recipe;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.registry.custom.YATMRegistries;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class IngredientUtil
{
	public static final String GROUP_KEY = "group";

	public static final String INPUT_KEY = "input";
	public static final String RESULT_KEY = "result";
	
	// unique secondary keys
	public static final String CONSUME_SEED_KEY = "consume";
	public static final String CURRENT_PER_TICK_KEY = "cost";
	public static final String DIE_KEY = "die";
	public static final String PART_KEY = "part";
	public static final String SEED_KEY = "seed";
	public static final String SUBSTRATE_KEY = "substrate";
	public static final String TIME_IN_TICKS_KEY = "time";
	public static final String TOOL_KEY = "tool";
	
	public static final String AMOUNT_KEY = "amount";
	public static final String CATEGORY_KEY = "category";
	public static final String COUNT_KEY = "count";
	public static final String FLUID_KEY = "fluid";
	public static final String INGREDIENT_KEY = "ingedient";
	public static final String ITEM_KEY = "item";
	public static final String REMAINDER_STACK_KEY = "remainder";
	public static final String TAG_KEY = "tag";	
	public static final String NBT_KEY = "nbt";
	// NOTE: it's in kelvin
	public static final String TEMPERATURE_KEY = "temperature";
	
	public static final String TYPE_KEY = "type";

	
	
	public static @NotNull IIngredient<?> readIngredient(@NotNull JsonObject object)
	{
		IIngredientDeserializer<?> deserializer = YATMRegistries.INGREDIENT_DESERIALIZERS.get().getValue(new ResourceLocation(object.get(IngredientUtil.TYPE_KEY).getAsString()));
		return deserializer.deserialize(object);
	} // end readIngredient()
	
	public static @NotNull JsonObject writeIngredient(@NotNull IIngredient<?> ingredient)
	{
		JsonObject jsObj = ingredient.serialize();
		jsObj.addProperty(IngredientUtil.TYPE_KEY, YATMRegistries.INGREDIENT_DESERIALIZERS.get().getKey(ingredient.deserializer()).toString());
		return jsObj;
	} // end writeIngredient()
	
	
	
	public static <T> @NotNull IIngredient<T> fromNetwork(@NotNull FriendlyByteBuf buffer) 
	{
		return YATMRegistries.INGREDIENT_DESERIALIZERS.get().getValue(new ResourceLocation(buffer.readUtf())).fromNetwork(buffer).cast();
	} // end toNetwork()
	
	@SuppressWarnings("unchecked")
	public static <T> void toNetwork(@NotNull IIngredient<T> ingredient, @NotNull FriendlyByteBuf buffer) 
	{
		buffer.writeUtf(YATMRegistries.INGREDIENT_DESERIALIZERS.get().getKey(ingredient.deserializer()).toString());
		((IIngredientDeserializer<IIngredient<T>>)ingredient.deserializer()).toNetwork(ingredient, buffer);
	} // end toNetwork()
	
	
	
	public static int getReqiuredCountFor(@NotNull Item item, @NotNull IIngredient<ItemStack> in)
	{
		for(ItemStack i : in.getValues()) 
		{
			if(i.getItem() == item) 
			{
				return i.getCount();
				
			}
		}
		return -1;
	} // end getReqiuredCountFor()
	
	public static int getRequiredAmountFor(@NotNull Fluid fluid, @NotNull IIngredient<FluidStack> in)
	{
		for(FluidStack f : in.getValues()) 
		{
			if(f.getFluid() == fluid) 
			{
				return f.getAmount();
				
			}
		}
		return -1;
	} // end getRequiredAmountFor()
	
	public static @NotNull Ingredient toMinecraftIngredient(@NotNull IIngredient<ItemStack> ingredient)
	{
		return Ingredient.of(ingredient.getValues().stream());
	} // end toMinecraftIngredient()
	
	
	
	// possibly make internally call getTag and getTagKey respectively for greater reuse and consistency and maintainability
	public static @NotNull ITag<Item> getItemTag(@NotNull String location)
	{
		ITagManager<Item> i = ForgeRegistries.ITEMS.tags();
		TagKey<Item> tk = i.createTagKey(new ResourceLocation(location));
		return i.getTag(tk);
	} // end getTag()
	
	public static @NotNull TagKey<Item> getItemTagKey(@NotNull String location)
	{
		ITagManager<Item> i = ForgeRegistries.ITEMS.tags();
		return i.createTagKey(new ResourceLocation(location));
	} // end getTagKey()

	
	
	public static <T> @NotNull ITag<T> getTag(@NotNull String location, @NotNull IForgeRegistry<T> registry)
	{
		ITagManager<T> i = registry.tags();
		TagKey<T> tk = i.createTagKey(new ResourceLocation(location));
		return i.getTag(tk);
	} // end getTag()
	
	public static <T> @NotNull TagKey<T> getTagKey(@NotNull String location, @NotNull IForgeRegistry<T> registry)
	{
		ITagManager<T> i = registry.tags();
		return i.createTagKey(new ResourceLocation(location));
	} // end getTagKey()

	// getFluidTagKey
	
	
	
	public static void fluidStackToNetwork(@NotNull FluidStack stack, @NotNull FriendlyByteBuf buffer) 
	{
		buffer.writeUtf(ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString());
		buffer.writeVarInt(stack.getAmount());
	} // end fluidStackToNetwork()
	
	public static void itemStackToNetwork(@NotNull ItemStack stack, @NotNull FriendlyByteBuf buffer) 
	{
		buffer.writeUtf(ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
		buffer.writeVarInt(stack.getCount());
	} // end fluidStackToNetwork()
	
	public static void nbtFluidStackToNetwork(@NotNull FluidStack stack, @NotNull FriendlyByteBuf buffer) 
	{
		buffer.writeUtf(ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString());
		buffer.writeVarInt(stack.getAmount());
		buffer.writeBoolean(stack.hasTag());
		if(stack.hasTag()) 
		{
			buffer.writeNbt(stack.getTag());
		}
	} // end nbtFluidStackToNetwork()
	
	
	
	public static @NotNull FluidStack fluidStackFromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(buffer.readUtf())), buffer.readVarInt());
	} // end fluidStackFromNetwork()
	
	public static @NotNull ItemStack itemStackFromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(buffer.readUtf())), buffer.readVarInt());
	} // end fluidStackFromNetwork()
	
	public static @NotNull FluidStack nbtFluidStackFromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(buffer.readUtf())), buffer.readVarInt(), buffer.readBoolean() ? buffer.readNbt() : null);
	} // end nbtFluidStackFromNetwork()
	
	
	
	
	
	public static @NotNull JsonObject itemStackToJson(@NotNull ItemStack itemStack)
	{
		JsonObject result = new JsonObject();
		result.addProperty(IngredientUtil.ITEM_KEY, ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
		result.addProperty(IngredientUtil.COUNT_KEY, itemStack.getCount());
		return result;
	} // end itemStackToJson()
	
	public static @NotNull JsonObject nbtItemStackToJson(@NotNull ItemStack itemStack)
	{
		JsonObject result = new JsonObject();
		result.addProperty(IngredientUtil.ITEM_KEY, ForgeRegistries.ITEMS.getKey(itemStack.getItem()).toString());
		result.addProperty(IngredientUtil.COUNT_KEY, itemStack.getCount());
		CompoundTag t = itemStack.getTag();
		if(t != null) 
		{
			result.addProperty(IngredientUtil.NBT_KEY, t.toString());
		}
		return result;
	} // end itemStackToJson()
	
	public static @NotNull JsonObject fluidStackToJson(@NotNull FluidStack fluidStack) 
	{
		JsonObject result = new JsonObject();
		result.addProperty(IngredientUtil.FLUID_KEY, ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
		result.addProperty(IngredientUtil.COUNT_KEY, fluidStack.getAmount());
		return result;
	} // end fluidStackToJson()
	
	public static @NotNull JsonObject nbtFluidStackToJson(@NotNull FluidStack fluidStack) 
	{
		JsonObject result = new JsonObject();
		result.addProperty(IngredientUtil.FLUID_KEY, ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
		result.addProperty(IngredientUtil.COUNT_KEY, fluidStack.getAmount());
		CompoundTag t = fluidStack.getTag();
		if(t != null) 
		{
			result.addProperty(IngredientUtil.NBT_KEY, t.toString());
		}
		return result;
	} // end fluidStackToJson()

	

	public static @NotNull FluidStack fluidStackFromJson(@NotNull JsonObject jsonObject)
	{
		ResourceLocation fluidKey = new ResourceLocation(jsonObject.get(IngredientUtil.FLUID_KEY).getAsString());
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidKey);
        if (fluid == null)
        {
            return FluidStack.EMPTY;
        }
        int amount = 1;
        if(jsonObject.has(IngredientUtil.COUNT_KEY)) 
        {
        	amount = jsonObject.get(IngredientUtil.COUNT_KEY).getAsInt();
        }
		return new FluidStack(fluid, amount);
	} // end fluidStackFromJson()
	
	public static @NotNull FluidStack nbtFluidStackFromJson(@NotNull JsonObject jsonObject)
	{
		ResourceLocation fluidKey = new ResourceLocation(jsonObject.get(IngredientUtil.FLUID_KEY).getAsString());
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidKey);
        if (fluid == null)
        {
            return FluidStack.EMPTY;
        }
        int amount = 1;
        if(jsonObject.has(IngredientUtil.COUNT_KEY)) 
        {
        	amount = jsonObject.get(IngredientUtil.COUNT_KEY).getAsInt();
        }
        if(jsonObject.has(IngredientUtil.NBT_KEY)) 
        {
        	return new FluidStack(fluid, amount, CraftingHelper.getNBT(jsonObject.get(IngredientUtil.NBT_KEY)));
        }
		return new FluidStack(fluid, amount);
	} // end fluidStackFromJson()
	
	
	
	/** use IIngredient&lt;T&gt; system*/
	@Deprecated
	public static int getReqiuredCountFor(@NotNull Item countFor, @NotNull Ingredient in)
	{
		for(ItemStack i : in.getItems()) 
		{
			if(i.getItem() == countFor) 
			{
				return i.getCount();
			}
		}
		return -1;
	} // end getReqiuredCountFor()

	// respects item and count but not nbt
	/** use IIngredient&lt;T&gt; system*/
	@Deprecated
	public static boolean testIngredientAgainst(ItemStack against, Ingredient ingredient)
	{
		for(ItemStack i : ingredient.getItems()) 
		{
			if(i.getItem() == against.getItem() && i.getCount() <= against.getCount()) 
			{
				return true;
			}
		}
		return false;
	} // end testIngredientAgainst()
	
} // end class