package com.gsr.gsr_yatm.item.fluid;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.data_generation.YATMEntityTypeTags;
import com.gsr.gsr_yatm.registry.YATMItems;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class EssenceOfSoulsBucketItem extends BucketItem
{

	public EssenceOfSoulsBucketItem(@NotNull Supplier<? extends Fluid> fluid, @NotNull Properties properties)
	{
		super(Objects.requireNonNull(fluid), Objects.requireNonNull(properties));
	} // end constructor

	
	
	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand)
	{
		List<Entity> withers = level.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(2.0D), (e) -> YATMEntityTypeTags.FORGE_WITHER_TAG.contains(e.getType()));
		if (!withers.isEmpty())
		{
			Inventory inventory = player.getInventory();
			for (ItemStack i : inventory.items)
			{
				if (i.getItem() == Items.GLASS_BOTTLE)
				{
					i.shrink(1);
					// slot or drop the item
					ItemStack result = new ItemStack(YATMItems.ESSENCE_OF_DECAY_BOTTLE.get());
					if(inventory.getFreeSlot() != -1)
					{
						inventory.add(result);
					}
					else
					{
						Containers.dropContents(level, player.blockPosition(), NonNullList.of(null, result));
					}
					return InteractionResultHolder.sidedSuccess(new ItemStack(Items.BUCKET), level.isClientSide);
				}
			}
		}
		return super.use(level, player, hand);
	} // end use()




	@Override
	public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new FluidBucketWrapper(Objects.requireNonNull(stack));
	} // end initCapabilities()
	
} // end class