package com.gsr.gsr_yatm.recipe.grinding;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.grinder.GrinderBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtilities;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class GrindingRecipe implements ITimedRecipe<Container>
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_input;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	
	@NotNull String m_group = "";



	public GrindingRecipe(@NotNull ResourceLocation identifier, @NotNull IIngredient<ItemStack> input, @NotNull ItemStack result)
	{
		this.m_identifier = identifier;
		this.m_input = input;
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
		return this.m_input.test(inventory.getStackInSlot(GrinderBlockEntity.INPUT_SLOT)) &&
				inventory.insertItem(GrinderBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty();
	} // end canBeUsedOn()

	public void startRecipe(@NotNull IItemHandler inventory)
	{
		inventory.extractItem(GrinderBlockEntity.INPUT_SLOT, 
				IngredientUtilities.getReqiuredCountFor(inventory.getStackInSlot(GrinderBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
	} // end startRecipe()

	public void setResults(@NotNull IItemHandler inventory)
	{
		inventory.insertItem(GrinderBlockEntity.RESULT_SLOT, this.m_result.copy(), false);
	} // end setResults()



	@Override
	public boolean matches(Container container, Level level)
	{
		return this.m_input.test(container.getItem(GrinderBlockEntity.INPUT_SLOT));
	} // end matches()

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end getResultItem

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<GrindingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.GRINDING.get();
	} // end getSerializer()

	@Override
	public RecipeType<GrindingRecipe> getType()
	{
		return YATMRecipeTypes.GRINDING.get();
	} // end getType()



	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_GRINDER_ITEM.get());
	} // end getToastSymbol()	

	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()

} // end outer class