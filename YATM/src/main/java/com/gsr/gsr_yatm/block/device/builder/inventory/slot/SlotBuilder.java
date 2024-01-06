package com.gsr.gsr_yatm.block.device.builder.inventory.slot;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.world.item.ItemStack;

public class SlotBuilder<T> implements ISlotBuilder<T>
{
	private final @Nullable T m_parent;
	private final @NotNegative int m_index;
	private final @NotNull Consumer<SlotDefinition> m_buildReceiver;
	private @NotNull Predicate<ItemStack> m_insertionValidator = (is) -> true; 
	private @NotNull Predicate<ItemStack> m_extractionValidator = (is) -> true;
	
	
	
	public SlotBuilder(@Nullable T parent, @NotNegative int index, @NotNull Consumer<SlotDefinition> buildReceiver) 
	{
		this.m_parent = parent;
		this.m_index = Contract.notNegative(index);
		this.m_buildReceiver = Objects.requireNonNull(buildReceiver);
	} // end constructor
	
	
	
	@Override
	public @NotNull ISlotBuilder<T> insertionValidator(@NotNull Predicate<@NotNull ItemStack> validator) 
	{
		this.m_insertionValidator = Objects.requireNonNull(validator);
		return this;
	} // end insertionValidator()
	
	@Override
	public @NotNull ISlotBuilder<T> extractionValidator(@NotNull Predicate<@NotNull ItemStack> validator) 
	{
		this.m_extractionValidator = Objects.requireNonNull(validator);
		return this;
	} // end extractionValidator()
	
	@Override
	public @Nullable T end() 
	{
		this.m_buildReceiver.accept(new SlotDefinition(this.m_index, this.m_insertionValidator, this.m_extractionValidator));
		return this.m_parent;
	} // end end()
	
} // end class