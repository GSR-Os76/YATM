package com.gsr.gsr_yatm.item.component.current_pass_through;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CurrentPassThroughBlockItem extends BlockItem
{

	public CurrentPassThroughBlockItem(@NotNull Block block, @NotNull Properties properties)
	{
		super(Objects.requireNonNull(block), Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new CurrentPassThroughItemStack();
	} // end initCapabilities()
	
} // end class