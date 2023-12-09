package com.gsr.gsr_yatm.utilities.recipe;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredientDeserializer;
import com.gsr.gsr_yatm.registry.custom.YATMRegistries;
import com.mojang.serialization.Codec;

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
	public static final String AMOUNT_KEY = "amount";
	public static final String CATEGORY_KEY = "category";
	public static final String CONSUME_SEED_KEY = "consume_seed";
	public static final String CURRENT_PER_TICK_KEY = "cost";
	public static final String DIE_KEY = "die";
	public static final String STACK_KEY = "stack";
	public static final String GROUP_KEY = "group";
	public static final String INPUT_KEY = "input";
	public static final String INPUT_REMAINDER_STACK_KEY = "input_remainder";
	public static final String NBT_KEY = "nbt";
	public static final String PART_KEY = "part";
	public static final String REMAINDER_STACK_KEY = "remainder";
	public static final String RESULT_KEY = "result";
	public static final String SEED_KEY = "seed";
	public static final String SUBSTRATE_KEY = "substrate";
	public static final String TAG_KEY = "tag";	
	public static final String TEMPERATURE_KEY = "temperature";
	public static final String TIME_IN_TICKS_KEY = "time";
	public static final String TOOL_KEY = "tool";
	
	// internal
	private static final String TYPE_KEY = "type";
	
	// ItemStack
	private static final String ITEM_KEY = "item";
	private static final String COUNT_KEY = "count";
	
	// FluidStack
	private static final String FLUID_STACK_FLUID_KEY = "FluidName";
	private static final String FLUID_STACK_AMOUNT_KEY = "Amount";
	private static final String FLUID_STACK_NBT_KEY = "Tag";
	
	
	
	// should really read the key to get the deserializer, than read the 
	public static final Codec<IIngredient<?>> INGREDIENT_CODEC = YATMRegistries.INGREDIENT_DESERIALIZERS.get().getCodec().dispatch(IIngredient::deserializer, IIngredientDeserializer::codec);
	
	public static Codec<IIngredient<?>> ingredientCodec() 
	{
		return IngredientUtil.INGREDIENT_CODEC;
	} // end ingredientCodec()
	
	
	
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
	
	
	
	
	// TODO, I'd suspect these already exist, if found prefer to them and deprecate these one
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
		result.addProperty(IngredientUtil.FLUID_STACK_FLUID_KEY, ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
		result.addProperty(IngredientUtil.FLUID_STACK_AMOUNT_KEY, fluidStack.getAmount());
		return result;
	} // end fluidStackToJson()
	
	public static @NotNull JsonObject nbtFluidStackToJson(@NotNull FluidStack fluidStack) 
	{
		JsonObject result = new JsonObject();
		result.addProperty(IngredientUtil.FLUID_STACK_FLUID_KEY, ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
		result.addProperty(IngredientUtil.FLUID_STACK_AMOUNT_KEY, fluidStack.getAmount());
		CompoundTag t = fluidStack.getTag();
		if(t != null) 
		{
			result.addProperty(IngredientUtil.FLUID_STACK_NBT_KEY, t.toString());
		}
		return result;
	} // end fluidStackToJson()

	

	public static @NotNull FluidStack fluidStackFromJson(@NotNull JsonObject jsonObject)
	{
		ResourceLocation fluidKey = new ResourceLocation(jsonObject.get(IngredientUtil.FLUID_STACK_FLUID_KEY).getAsString());
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidKey);
        if (fluid == null)
        {
            return FluidStack.EMPTY;
        }
        int amount = 1;
        if(jsonObject.has(IngredientUtil.FLUID_STACK_AMOUNT_KEY)) 
        {
        	amount = jsonObject.get(IngredientUtil.FLUID_STACK_AMOUNT_KEY).getAsInt();
        }
		return new FluidStack(fluid, amount);
	} // end fluidStackFromJson()
	
	public static @NotNull FluidStack nbtFluidStackFromJson(@NotNull JsonObject jsonObject)
	{
		ResourceLocation fluidKey = new ResourceLocation(jsonObject.get(IngredientUtil.FLUID_STACK_FLUID_KEY).getAsString());
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidKey);
        if (fluid == null)
        {
            return FluidStack.EMPTY;
        }
        int amount = 1;
        if(jsonObject.has(IngredientUtil.FLUID_STACK_AMOUNT_KEY)) 
        {
        	amount = jsonObject.get(IngredientUtil.FLUID_STACK_AMOUNT_KEY).getAsInt();
        }
        if(jsonObject.has(IngredientUtil.FLUID_STACK_NBT_KEY)) 
        {
        	return new FluidStack(fluid, amount, CraftingHelper.getNBT(jsonObject.get(IngredientUtil.FLUID_STACK_NBT_KEY)));
        }
		return new FluidStack(fluid, amount);
	} // end fluidStackFromJson()
	
} // end class