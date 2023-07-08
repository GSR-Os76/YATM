package com.gsr.gsr_yatm.recipe.grinding;

import com.gsr.gsr_yatm.block.device.grinder.GrinderBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

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

public class GrindingRecipe implements ITimedRecipe<Container>
{
	private final ResourceLocation m_identifier;
	private final ItemStack m_result;
	private final Ingredient m_input;
	int m_currentPerTick = 8;
	int m_timeInTicks = 20;
	
	String m_group = "";



	public GrindingRecipe(ResourceLocation identifier, Ingredient input, ItemStack result)
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

	
	
	public boolean canBeUsedOn(IItemHandler inventory)
	{
		return RecipeUtilities.testIngredientAgainst(inventory.getStackInSlot(GrinderBlockEntity.INPUT_SLOT), this.m_input) && 
				inventory.insertItem(GrinderBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty();
	} // end canBeUsedOn()

	public void startRecipe(IItemHandler inventory)
	{
		inventory.extractItem(GrinderBlockEntity.INPUT_SLOT, 
				RecipeUtilities.getReqiuredCountFor(inventory.getStackInSlot(GrinderBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
	} // end startRecipe()

	public void setResults(IItemHandler inventory)
	{
		inventory.insertItem(GrinderBlockEntity.RESULT_SLOT, this.m_result.copy(), false);
	} // end setResults()



	@Override
	public boolean matches(Container container, Level level)
	{
		return RecipeUtilities.testIngredientAgainst(container.getItem(GrinderBlockEntity.INPUT_SLOT), this.m_input);
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
	public NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return ITimedRecipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(null, this.m_input);
	} // end getIngredients()

	@Override
	public String getGroup()
	{
		return this.m_group;
	} // end getGroup()

} // end outer class