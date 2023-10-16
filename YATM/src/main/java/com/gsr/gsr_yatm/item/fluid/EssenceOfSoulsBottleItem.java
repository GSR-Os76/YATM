package com.gsr.gsr_yatm.item.fluid;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class EssenceOfSoulsBottleItem extends DrinkableFluidBottleItem
{
	private static final List<Function<UseOnContext, InteractionResult>> FORM_ATTEMPTERS = List.of(EssenceOfSoulsBottleItem::tryFormAurumDeminutus, EssenceOfSoulsBottleItem::tryFormFerrum, EssenceOfSoulsBottleItem::tryFormCarbum);
	
	public EssenceOfSoulsBottleItem(@NotNull Properties properties, @NotNull  Supplier<? extends Fluid> fluid, int useDuration)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(fluid), useDuration);
	} // end constuctor

	
	
	@Override
	public InteractionResult useOn(@NotNull UseOnContext context)
	{
		for(Function<UseOnContext, InteractionResult> fa : EssenceOfSoulsBottleItem.FORM_ATTEMPTERS) 
		{
			InteractionResult far = fa.apply(context);
			if(far.consumesAction()) 
			{
				return far;
			}
		}
		return super.useOn(context);
	} // end useOn()

	
	
	public static InteractionResult tryFormAurumDeminutus(@NotNull UseOnContext context) 
	{
		ItemStack held = context.getItemInHand();
		if (!held.is(YATMItemTags.GOLEM_LIKE_PLANT_FORMERS))
		{
			return InteractionResult.PASS;
		}
		
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		BlockState state = level.getBlockState(position);
//		RandomSource random = level.getRandom();
		boolean succeeded = false;
		
		// TODO, could make these less repetitive
		if(state.is(YATMBlockTags.FORMS_AURUM_DEMINUTUS_HIGHER_KEY)) 
		{
			BlockState below = level.getBlockState(position.below());
			if(below.is(YATMBlockTags.FORMS_AURUM_DEMINUTUS_LOWER_KEY)) 
			{
				succeeded = true;
				if(!level.isClientSide) 
				{
					level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
					level.setBlock(position.below(), Blocks.AIR.defaultBlockState(), 3);
					level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
				}
			}
		}
		if(!succeeded && state.is(YATMBlockTags.FORMS_AURUM_DEMINUTUS_LOWER_KEY)) 
		{
			BlockState above = level.getBlockState(position.above());
			if(above.is(YATMBlockTags.FORMS_AURUM_DEMINUTUS_HIGHER_KEY)) 
			{
				succeeded = true;
				if(!level.isClientSide) 
				{
					level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
					level.setBlock(position.above(), Blocks.AIR.defaultBlockState(), 3);
					level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
				}
			}
		}
		
		if (succeeded)
		{
			if (!level.isClientSide)
			{
				Player player = context.getPlayer();
				
				if(player == null || !player.getAbilities().instabuild) 
				{
					if(held.hasCraftingRemainingItem())
					{
						if (!(player != null && player.getInventory().add(held.getCraftingRemainingItem())))
						{
							InventoryUtilities.drop(level, context.getClickedPos(), held.getCraftingRemainingItem());
						}
					}
					held.shrink(1);
				}
				// TODO, probably play particles and sounds
				InventoryUtilities.drop(level, position, new ItemStack(YATMItems.AURUM_DEMINUTUS_FIDDLE_HEAD.get()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		
		return InteractionResult.PASS;
	} // end tryFormAurumDeminutus()

	public static InteractionResult tryFormFerrum(@NotNull UseOnContext context) 
	{
		ItemStack held = context.getItemInHand();
		if (!held.is(YATMItemTags.GOLEM_LIKE_PLANT_FORMERS))
		{
			return InteractionResult.PASS;
		}
		
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		BlockState state = level.getBlockState(position);
		boolean succeeded = false;
		
		if(state.is(YATMBlockTags.FORMS_FERRUM_KEY)) 
		{
			succeeded = true;
			if(!level.isClientSide) 
			{
				level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
				level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
			}
		}
		
		if (succeeded)
		{
			if (!level.isClientSide)
			{
				Player player = context.getPlayer();
				
				if(player == null || !player.getAbilities().instabuild) 
				{
					if(held.hasCraftingRemainingItem())
					{
						if (!(player != null && player.getInventory().add(held.getCraftingRemainingItem())))
						{
							InventoryUtilities.drop(level, context.getClickedPos(), held.getCraftingRemainingItem());
						}
					}
					held.shrink(1);
				}
				// TODO, probably play particles and sounds
				InventoryUtilities.drop(level, position, new ItemStack(YATMItems.FERRUM_ROOTSTOCK.get()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		
		return InteractionResult.PASS;
	} // end tryFormFerrum()

	public static InteractionResult tryFormCarbum(@NotNull UseOnContext context) 
	{
		ItemStack held = context.getItemInHand();
		if (!held.is(YATMItemTags.GOLEM_LIKE_PLANT_FORMERS))
		{
			return InteractionResult.PASS;
		}
		
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		BlockState state = level.getBlockState(position);
		boolean succeeded = false;
		
		if(state.is(YATMBlockTags.FORMS_CARBUM_KEY)) 
		{
			succeeded = true;
			if(!level.isClientSide) 
			{
				level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
				level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
			}
		}
		
		if (succeeded)
		{
			if (!level.isClientSide)
			{
				Player player = context.getPlayer();
				
				if(player == null || !player.getAbilities().instabuild) 
				{
					if(held.hasCraftingRemainingItem())
					{
						if (!(player != null && player.getInventory().add(held.getCraftingRemainingItem())))
						{
							InventoryUtilities.drop(level, context.getClickedPos(), held.getCraftingRemainingItem());
						}
					}
					held.shrink(1);
				}
				// TODO, probably play particles and sounds
				InventoryUtilities.drop(level, position, new ItemStack(YATMItems.CARBUM_MERISTEM.get()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		
		return InteractionResult.PASS;
	} // end tryFormFerrum()
	
} // end class