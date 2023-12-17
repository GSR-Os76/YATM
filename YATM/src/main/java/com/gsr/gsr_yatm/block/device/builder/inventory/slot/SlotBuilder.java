package com.gsr.gsr_yatm.block.device.builder;

import java.util.Objects;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.item.ItemStack;

public class SlotBuilder<T extends @NotNull IInventoryBuilder>
{
	private final @NotNull T m_inventory;
	private final @NotNegative int m_index;
	private @NotNull Predicate<ItemStack> m_insertionValidator = (is) -> true; 
	private @NotNull Predicate<ItemStack> m_extractionValidator = (is) -> true;
	
	
	
	public SlotBuilder(@NotNull T inventory) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_index = this.m_inventory.nextIndex();
	} // end constructor
	
	
	
	public SlotBuilder<T> insertionValidator(@NotNull Predicate<@NotNull ItemStack> insertionValidator) 
	{
		this.m_insertionValidator = Objects.requireNonNull(insertionValidator);
		return this;
	} // end insertionValidator()
	
	public SlotBuilder<T> extractionValidator(@NotNull Predicate<@NotNull ItemStack> extractionValidator) 
	{
		this.m_extractionValidator = Objects.requireNonNull(extractionValidator);
		return this;
	} // end extractionValidator()
	
	public @NotNull T end() 
	{
		this.m_inventory.addSlot(new SlotDefinition(this.m_index, this.m_insertionValidator, this.m_extractionValidator));
		return this.m_inventory;
	} // end end()
	
} // end class