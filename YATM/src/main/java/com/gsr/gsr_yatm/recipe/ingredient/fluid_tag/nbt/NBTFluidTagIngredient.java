package com.gsr.gsr_yatm.recipe.ingredient.fluid_tag.nbt;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.gsr.gsr_yatm.recipe.ingredient.IIngredient;
import com.gsr.gsr_yatm.registry.custom.YATMIngredientDeserializers;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.recipe.IngredientUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class NBTFluidTagIngredient implements IIngredient<FluidStack>
{
	private final @NotNull TagKey<Fluid> m_tagKey;
	private final @NotNull ITag<Fluid> m_tag;
	private final @NotNegative int m_amount;
	private final @Nullable CompoundTag m_nbt;
	
	
	
	public NBTFluidTagIngredient(@NotNull TagKey<Fluid> tag, @NotNegative int amount, @Nullable CompoundTag nbt) 
	{
		this.m_tagKey = Objects.requireNonNull(tag);
		this.m_tag = ForgeRegistries.FLUIDS.tags().getTag(tag);
		this.m_amount = Contract.notNegative(amount);
		this.m_nbt = nbt;
	} // end constructor()
	
	public @NotNull TagKey<Fluid> getTag()
	{
		return this.m_tagKey;
	} // end getTag()
	
	public @NotNegative int getAmount()
	{
		return this.m_amount;
	} // end getAmount()
	
	public @Nullable CompoundTag getNBT()
	{
		return this.m_nbt;
	} // end getNBT()
	
	
	
	@Override
	public boolean test(@Nullable FluidStack fluidStack)
	{
		return this.m_tag.contains(fluidStack.getFluid()) 
				&& this.m_amount <= fluidStack.getAmount() 
				&& ((this.m_nbt == null && !fluidStack.hasTag()) ||
						this.m_nbt.equals(fluidStack.getTag()));
	} // end test()

	@Override
	public @NotNull JsonObject serialize()
	{
		JsonObject obj = new JsonObject();
		obj.addProperty(IngredientUtil.TAG_KEY, this.m_tagKey.location().toString());
		obj.addProperty(IngredientUtil.AMOUNT_KEY, this.m_amount);
		if(this.m_nbt != null) 
		{
			obj.addProperty(IngredientUtil.NBT_KEY, this.m_nbt.toString());
		}
		return obj;
	} // end serialize()

	@Override
	public @NotNull List<FluidStack> getValues()
	{
		return this.m_tag.stream().map((f) -> new FluidStack(f, this.m_amount, this.m_nbt.copy())).toList();
	} // end getValues()
	
	
	
	@Override
	public @NotNull NBTFluidTagIngredientDeserializer deserializer()
	{
		return YATMIngredientDeserializers.NBT_FLUID_TAG_INGREDIENT.get();
	} // end deserializer()

} // end class