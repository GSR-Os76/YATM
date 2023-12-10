package com.gsr.gsr_yatm.recipe.injecting;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipeCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;

public class InjectingRecipeSerializer implements RecipeSerializer<InjectingRecipe>
{
	private static final @NotNull Codec<InjectingRecipe> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ExtraCodecs.strictOptionalField(Codec.STRING, IngredientUtil.GROUP_KEY, "").forGetter(InjectingRecipe::getGroup),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.INPUT_KEY).forGetter(InjectingRecipe::input),
			IngredientUtil.ingredientCodec().fieldOf(IngredientUtil.SUBSTRATE_KEY).forGetter(InjectingRecipe::substrate),
			CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf(IngredientUtil.RESULT_KEY).forGetter(InjectingRecipe::result),
			Codec.INT.fieldOf(IngredientUtil.CURRENT_PER_TICK_KEY).forGetter(InjectingRecipe::getCurrentPerTick),
			Codec.INT.fieldOf(IngredientUtil.TIME_IN_TICKS_KEY).forGetter(InjectingRecipe::getTimeInTicks)
			).apply(instance, (g, i, s, r, c, t) -> new InjectingRecipe(g, i.cast(), s.cast(), r, c, t)));
	
	@Override
	public Codec<InjectingRecipe> codec()
	{
		return InjectingRecipeSerializer.CODEC;
	} // end codec()

	@Override
	public @NotNull InjectingRecipe fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		String group = buffer.readUtf();
		IIngredient<FluidStack> input = IngredientUtil.fromNetwork(buffer);
		IIngredient<ItemStack> substrate = IngredientUtil.fromNetwork(buffer);
		ItemStack result = buffer.readItem();
		int current = buffer.readVarInt();
		int time = buffer.readVarInt();
		
		return new InjectingRecipe(group, input, substrate, result, current, time);
	} // end fromNetwork()

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull InjectingRecipe recipe)
	{
		buffer.writeUtf(recipe.getGroup());
		IngredientUtil.toNetwork(recipe.input(), buffer);
		IngredientUtil.toNetwork(recipe.substrate(), buffer);
		buffer.writeItem(recipe.result());
		buffer.writeVarInt(recipe.getCurrentPerTick());
		buffer.writeVarInt(recipe.getTimeInTicks());
	} // end toNetwork()

} // end class