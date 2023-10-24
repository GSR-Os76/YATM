package com.gsr.gsr_yatm.recipe.melting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.crucible.CrucibleBlockEntity;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMRecipeSerializers;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class MeltingRecipe implements ITimedRecipe<Container>
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull FluidStack m_result;

	private final @NotNull IIngredient<ItemStack> m_input;

	private final @NotNegative int m_temperature;
	private final @NotNegative int m_timeInTicks;

	private final @NotNull String m_group;



	public MeltingRecipe(@NotNull ResourceLocation identifier, 
			@NotNull IIngredient<ItemStack> input, 
			@NotNull FluidStack result,
			@NotNegative int temperature,
			@NotNegative int timeInTicks,
			@NotNull String group
			)
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_input = Objects.requireNonNull(input);
		this.m_result = Objects.requireNonNull(result.copy());
		this.m_temperature = Contract.NotNegative(temperature);
		this.m_timeInTicks = Contract.NotNegative(timeInTicks);
		this.m_group = Objects.requireNonNull(group);
	} // end constructor



	public @NotNegative int getTemperature()
	{
		return this.m_temperature;
	} // end getCurrentPerTick()

	@Override
	public @NotNegative int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()



	public boolean canBeUsedOn(@NotNull IItemHandler inventory, @NotNull IFluidHandler tank)
	{
		return this.m_input.test(inventory.getStackInSlot(CrucibleBlockEntity.INPUT_SLOT)) 
				&& tank.fill(this.m_result.copy(), FluidAction.SIMULATE) == this.m_result.getAmount();
	} // end canBeUsedOn()

	public void startRecipe(@NotNull IItemHandler inventory)
	{
		inventory.extractItem(CrucibleBlockEntity.INPUT_SLOT, 
				IngredientUtil.getReqiuredCountFor(inventory.getStackInSlot(CrucibleBlockEntity.INPUT_SLOT).getItem(), this.m_input), false);
	} // end startRecipe()

	public void setResults(@NotNull IFluidHandler tank)
	{
		tank.fill(this.m_result.copy(), FluidAction.EXECUTE);
	} // end setResults()



	@Override
	public boolean matches(Container container, Level level)
	{
		return false;
	} // end matches()

	@Override
	public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return ItemStack.EMPTY;
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public @NotNull ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return ItemStack.EMPTY;
	} // end getResultItem

	@Override
	public @NotNull ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public @NotNull RecipeSerializer<MeltingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.MELTING.get();
	} // end getSerializer()

	@Override
	public @NotNull RecipeType<MeltingRecipe> getType()
	{
		return YATMRecipeTypes.MELTING.get();
	} // end getType()



	@Override
	public @NotNull ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_CRUCIBLE_ITEM.get());
	} // end getToastSymbol()

	@Override
	public @NotNull NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return ITimedRecipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, IngredientUtil.toMinecraftIngredient(this.m_input));
	} // end getIngredients()

	@Override
	public @NotNull String getGroup()
	{
		return this.m_group;
	} // end getGroup()

} // end class