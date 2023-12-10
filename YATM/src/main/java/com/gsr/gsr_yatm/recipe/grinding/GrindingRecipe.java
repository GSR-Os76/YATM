package com.gsr.gsr_yatm.recipe.grinding;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.grinder.GrinderBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.RecipeBase;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class GrindingRecipe extends RecipeBase<Container> implements ITimedRecipe<Container>
{
	private final @NotNull ItemStack m_result;
	private final @NotNull IIngredient<ItemStack> m_input;
	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;



	public GrindingRecipe(@NotNull String group, @NotNull IIngredient<ItemStack> input, @NotNull ItemStack result, @NotNegative int currentPerTick, @NotNegative int timeInTicks)
	{
		super(Objects.requireNonNull(group));
		this.m_input = Objects.requireNonNull(input);
		this.m_result = result.copy();
		this.m_currentPerTick = Contract.notNegative(currentPerTick);
		this.m_timeInTicks = Contract.notNegative(timeInTicks);
	} // end constructor



	public @NotNegative int getCurrentPerTick()
	{
		return this.m_currentPerTick;
	} // end getCurrentPerTick()

	@Override
	public @NotNegative int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()

	public @NotNull IIngredient<ItemStack> input()
	{
		return this.m_input;
	} // end input()

	public @NotNull ItemStack result() 
	{
		return this.m_result.copy();
	} // end result()
	
	
	
	public boolean canBeUsedOn(@NotNull IItemHandler inventory)
	{
		return this.m_input.test(inventory.getStackInSlot(GrinderBlockEntity.INPUT_SLOT)) &&
				inventory.insertItem(GrinderBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty();
	} // end canBeUsedOn()

	public void startRecipe(@NotNull IItemHandler inventory)
	{
		inventory.extractItem(GrinderBlockEntity.INPUT_SLOT, 
				IngredientUtil.getReqiuredCountFor(inventory.getStackInSlot(GrinderBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
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
		return new ItemStack(YATMItems.STEEL_GRINDER.get());
	} // end getToastSymbol()
	
} // end outer class