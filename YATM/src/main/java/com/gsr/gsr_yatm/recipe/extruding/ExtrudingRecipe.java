package com.gsr.gsr_yatm.recipe.extruding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.extruder.ExtruderBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class ExtrudingRecipe implements ITimedRecipe<Container>
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;

	private final @NotNull IIngredient<ItemStack> m_input;
	private final @NotNull IIngredient<ItemStack> m_die;

	@NotNull ItemStack m_inputRemainder = ItemStack.EMPTY;
	@Nullable ItemStack m_dieRemainder = null;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	
	@NotNull String m_group = "";



	public ExtrudingRecipe(@NotNull ResourceLocation identifier, @NotNull IIngredient<ItemStack> input, @NotNull IIngredient<ItemStack> die, @NotNull ItemStack result)
	{
		this.m_identifier = identifier;
		this.m_input = input;
		this.m_die = die;
		this.m_result = result.copy();
	} // end constructor



	public int getCurrentPerTick()
	{
		return this.m_currentPerTick;
	} // end getCurrentPerTick()

	@Override
	public int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	
	
	public boolean canBeUsedOn(@NotNull IItemHandler inventory)
	{
		return this.m_input.test(inventory.getStackInSlot(ExtruderBlockEntity.INPUT_SLOT)) 
				&& this.m_die.test(inventory.getStackInSlot(ExtruderBlockEntity.DIE_SLOT)) 
				&& inventory.insertItem(ExtruderBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty() 
				&& inventory.insertItem(ExtruderBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainder.copy(), true).isEmpty();
	} // end canBeUsedOn()

	public void startRecipe(@NotNull IItemHandler inventory)
	{
		inventory.extractItem(ExtruderBlockEntity.INPUT_SLOT, 
				IngredientUtil.getReqiuredCountFor(inventory.getStackInSlot(ExtruderBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
	} // end startRecipe()

	public void setResults(@NotNull IItemHandler inventory)
	{
		if (this.m_dieRemainder != null)
		{
			inventory.extractItem(ExtruderBlockEntity.DIE_SLOT, inventory.getSlotLimit(ExtruderBlockEntity.DIE_SLOT), false);
			inventory.insertItem(ExtruderBlockEntity.DIE_SLOT, this.m_dieRemainder.copy(), false);
		}
		inventory.insertItem(ExtruderBlockEntity.RESULT_SLOT, this.m_result.copy(), false);

		inventory.insertItem(ExtruderBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainder.copy(), false);
	} // end setResults()



	@Override
	public boolean matches(Container container, Level level)
	{
		return this.m_input.test(container.getItem(ExtruderBlockEntity.INPUT_SLOT))
				&& this.m_die.test(container.getItem(ExtruderBlockEntity.DIE_SLOT));
	} // end matches()

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		// something about the recipe book
		// TODO, understand this better, currently basically guessing at it
		return true;
	} // end canCraftInDimensions()

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return this.m_result;
	} // end getResultItem

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<ExtrudingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.EXTRUDING.get();
	} // end getSerializer()

	@Override
	public RecipeType<ExtrudingRecipe> getType()
	{
		return YATMRecipeTypes.EXTRUDING.get();
	} // end getType()



	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_EXTRUDER_ITEM.get());
	} // end getToastSymbol()	
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return ITimedRecipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, IngredientUtil.toMinecraftIngredient(this.m_input), IngredientUtil.toMinecraftIngredient(this.m_die));
	} // end getIngredients()

	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()


} // end class