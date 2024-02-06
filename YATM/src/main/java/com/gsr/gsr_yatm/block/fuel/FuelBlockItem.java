package com.gsr.gsr_yatm.block.fuel;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.capability.HeatUtil;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class FuelBlockItem extends BlockItem
{
	private final int m_smeltingBurnTime;
	
	
	
	public FuelBlockItem(@NotNull Block block, @NotNull Properties properties, int smeltingBurnTime)
	{
		super(Objects.requireNonNull(block), Objects.requireNonNull(properties));
		this.m_smeltingBurnTime = smeltingBurnTime;
	} // end constructor

	
	
	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType)
	{
		return HeatUtil.fuelTimeByType(this.m_smeltingBurnTime, recipeType);
	} // end getBurnTime()
	
} // end class