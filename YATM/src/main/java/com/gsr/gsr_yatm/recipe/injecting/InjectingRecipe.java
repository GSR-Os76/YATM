package com.gsr.gsr_yatm.recipe.injecting;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.block.device.injector.InjectorBlockEntity;
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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.items.IItemHandler;

public class InjectingRecipe implements ITimedRecipe<Container>
{
	private final @NotNull ResourceLocation m_identifier;
	private final @NotNull ItemStack m_result;

	private final @NotNull IIngredient<FluidStack> m_input;
	private final @NotNull IIngredient<ItemStack> m_substrate;

	private final @NotNegative int m_currentPerTick;
	private final @NotNegative int m_timeInTicks;

	private final @NotNull String m_group;



	public InjectingRecipe(@NotNull ResourceLocation identifier, 
			@NotNull IIngredient<FluidStack> input, 
			@NotNull IIngredient<ItemStack> substrate, 
			@NotNull ItemStack result,
			@NotNegative int currentPerTick,
			@NotNegative int timeInTicks,
			@NotNull String group
			)
	{
		this.m_identifier = Objects.requireNonNull(identifier);
		this.m_input = Objects.requireNonNull(input);
		this.m_substrate = Objects.requireNonNull(substrate);
		this.m_result = Objects.requireNonNull(result.copy());
		this.m_currentPerTick = Contract.NotNegative(currentPerTick);
		this.m_timeInTicks = Contract.NotNegative(timeInTicks);
		this.m_group = Objects.requireNonNull(group);
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



	public boolean canBeUsedOn(@NotNull IItemHandler inventory, @NotNull IFluidHandler tank)
	{
		return this.m_input.test(tank.getFluidInTank(0)) 
				&& this.m_substrate.test(inventory.getStackInSlot(InjectorBlockEntity.SUBSTRATE_SLOT)) 
				&& inventory.insertItem(InjectorBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty();
	} // end canBeUsedOn()

	public void startRecipe(@NotNull IItemHandler inventory, @NotNull IFluidHandler tank)
	{
		Fluid f = tank.getFluidInTank(0).getFluid();
		tank.drain(new FluidStack(f, IngredientUtil.getRequiredAmountFor(f, this.m_input)), FluidAction.EXECUTE);
		inventory.extractItem(InjectorBlockEntity.SUBSTRATE_SLOT, 
				IngredientUtil.getReqiuredCountFor(inventory.getStackInSlot(InjectorBlockEntity.SUBSTRATE_SLOT).getItem(), this.m_substrate), false);
	} // end startRecipe()

	public void setResults(@NotNull IItemHandler inventory)
	{
		inventory.insertItem(InjectorBlockEntity.RESULT_SLOT, this.m_result.copy(), false);
	} // end setResults()



	@Override
	public boolean matches(Container container, Level level)
	{
		return false;
	} // end matches()

	@Override
	public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	} // end canCraftInDimensions()

	@Override
	public @NotNull ItemStack getResultItem(RegistryAccess registryAccess)
	{
		return this.m_result.copy();
	} // end getResultItem

	@Override
	public @NotNull ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public @NotNull RecipeSerializer<InjectingRecipe> getSerializer()
	{
		return YATMRecipeSerializers.INJECTING.get();
	} // end getSerializer()

	@Override
	public @NotNull RecipeType<InjectingRecipe> getType()
	{
		return YATMRecipeTypes.INJECTING.get();
	} // end getType()



	@Override
	public @NotNull ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_INJECTOR_ITEM.get());
	} // end getToastSymbol()

	@Override
	public @NotNull NonNullList<ItemStack> getRemainingItems(Container container)
	{
		return ITimedRecipe.super.getRemainingItems(container);
	} // end getRemainingItems

	@Override
	public @NotNull NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, IngredientUtil.toMinecraftIngredient(this.m_substrate)); // TODO, possibly add something like the following in, except this wouldn't be generic, unless ingredients were made to define their conversion to minecraft ingredients. IngredientUtilities.toMinecraftIngredients(this.m_input, this.m_substrate);
	} // end getIngredients()

	@Override
	public @NotNull String getGroup()
	{
		return this.m_group;
	} // end getGroup()

} // end class