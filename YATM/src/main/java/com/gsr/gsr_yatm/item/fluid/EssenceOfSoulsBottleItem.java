package com.gsr.gsr_yatm.item.fluid;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.data_generation.YATMItemTags;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.utilities.InventoryUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class EssenceOfSoulsBottleItem extends DrinkableFluidBottleItem
{
	private static final List<Function<UseOnContext, InteractionResult>> FORM_ATTEMPTERS = List.of(
			(c) -> EssenceOfSoulsBottleItem.tryFormVerticalThreeBlock(c, YATMBlockTags.FORMS_ADAMUM_TOP_KEY, YATMBlockTags.FORMS_ADAMUM_MIDDLE_KEY, YATMBlockTags.FORMS_ADAMUM_BOTTOM_KEY, YATMItems.ADAMUM_MERISTEM.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormVerticalTwoBlock(c, YATMBlockTags.FORMS_AURUM_HIGHER_KEY, YATMBlockTags.FORMS_AURUM_LOWER_KEY, YATMItems.AURUM_DEMINUTUS_FIDDLE_HEAD.get()), // EssenceOfSoulsBottleItem::tryFormAurum, 
			(c) -> EssenceOfSoulsBottleItem.tryFormSingleBlockInPlaceBlock(c, YATMBlockTags.FORMS_BUDDING_AMETHYST_KEY, Blocks.BUDDING_AMETHYST.defaultBlockState()),
			(c) -> EssenceOfSoulsBottleItem.tryFormSingleBlock(c, YATMBlockTags.FORMS_CARBUM_KEY, YATMItems.CARBUM_MERISTEM.get()),// EssenceOfSoulsBottleItem::tryFormCarbum,
			(c) -> EssenceOfSoulsBottleItem.tryFormVerticalTwoBlock(c, YATMBlockTags.FORMS_CUPRUM_HIGHER_KEY, YATMBlockTags.FORMS_CUPRUM_LOWER_KEY, YATMItems.CUPRUM_BULB.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormSingleBlock(c, YATMBlockTags.FORMS_FERRUM_KEY, YATMItems.FERRUM_ROOTSTOCK.get()),// EssenceOfSoulsBottleItem::tryFormFerrum,
			(c) -> EssenceOfSoulsBottleItem.tryFormVerticalTwoBlock(c, YATMBlockTags.FORMS_FOLIUM_HIGHER_KEY, YATMBlockTags.FORMS_FOLIUM_LOWER_KEY, YATMItems.FOLIUM_RHIZOME.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormVerticalThreeBlock(c, YATMBlockTags.FORMS_INFERNALUM_TOP_KEY, YATMBlockTags.FORMS_INFERNALUM_MIDDLE_KEY, YATMBlockTags.FORMS_INFERNALUM_BOTTOM_KEY, YATMItems.INFERNALUM_RHIZOME.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormSurroundedBlock(c, YATMBlockTags.FORMS_LAPUM_INNER_KEY, YATMBlockTags.FORMS_LAPUM_OUTER_KEY, YATMItems.LAPUM_MERISTEM.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormSingleBlock(c, YATMBlockTags.FORMS_RUBERUM_KEY, YATMItems.RUBERUM_CORM.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormSurroundedBlock(c, YATMBlockTags.FORMS_SAMARAGDUM_INNER_KEY, YATMBlockTags.FORMS_SAMARAGDUM_OUTER_KEY, YATMItems.SAMARAGDUM_NODULE.get()),
			(c) -> EssenceOfSoulsBottleItem.tryFormSurroundedBlock(c, YATMBlockTags.FORMS_VICUM_INNER_KEY, YATMBlockTags.FORMS_VICUM_OUTER_KEY, YATMItems.VICUM_MERISTEM.get()) // EssenceOfSoulsBottleItem::tryFormVicum
			);
	
	public EssenceOfSoulsBottleItem(@NotNull Properties properties, @NotNull Supplier<? extends Fluid> fluid, int useDuration)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(fluid), useDuration);
	} // end constructor

	
	
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
	

	
	/*
	public static InteractionResult tryFormAurum(@NotNull UseOnContext context) 
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
		
		// TODO, could make these less repetitive
		if(state.is(YATMBlockTags.FORMS_AURUM_HIGHER_KEY)) 
		{
			BlockState below = level.getBlockState(position.below());
			if(below.is(YATMBlockTags.FORMS_AURUM_LOWER_KEY)) 
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
		if(!succeeded && state.is(YATMBlockTags.FORMS_AURUM_LOWER_KEY)) 
		{
			BlockState above = level.getBlockState(position.above());
			if(above.is(YATMBlockTags.FORMS_AURUM_HIGHER_KEY)) 
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
				// TODO, maybe play particles
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
				// TODO, maybe play particles
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
				// TODO, maybe play particles
				InventoryUtilities.drop(level, position, new ItemStack(YATMItems.CARBUM_MERISTEM.get()));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		
		return InteractionResult.PASS;
	} // end tryFormCarbum()
	
	public static InteractionResult tryFormVicum(@NotNull UseOnContext context) 
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
		
		if(state.is(YATMBlockTags.FORMS_VICUM_OUTER_KEY)) 
		{
			BlockPos.MutableBlockPos mbpos = new BlockPos.MutableBlockPos();
			for(Direction d : Direction.allShuffled(level.random)) 
			{
				if(level.getBlockState(mbpos.set(position.relative(d))).is(YATMBlockTags.FORMS_VICUM_INNER_KEY)) 
				{
					succeeded = true;
					for(Direction d2 : Direction.allShuffled(level.random)) 
					{
						if(!level.getBlockState(mbpos.relative(d2)).is(YATMBlockTags.FORMS_VICUM_OUTER_KEY)) 
						{
							succeeded = false;
							break;
						}
					}
					if(succeeded) 
					{
						if(!level.isClientSide) 
						{
							level.setBlock(mbpos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
							level.playSound((Entity) null, mbpos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
							for(Direction d2 : Direction.allShuffled(level.random)) 
							{
								level.setBlock(mbpos.relative(d2), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
								level.playSound((Entity) null, mbpos.relative(d2), SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
							}
						}
						break;
					}
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
				// TODO, maybe play particles
				InventoryUtilities.drop(level, position, new ItemStack(YATMItems.VICUM_MERISTEM.get()));
			}
			
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		
		return InteractionResult.PASS;
	} // end tryFormVicum()
	*/
	
	public static InteractionResult tryFormSingleBlock(@NotNull UseOnContext context, @NotNull TagKey<Block> tag, @NotNull Item result) 
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
		
		if(state.is(tag)) 
		{
			succeeded = true;
			if(!level.isClientSide) 
			{
				level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
				level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
			}
		}
		
		return succeeded ? EssenceOfSoulsBottleItem.formSucceeded(context, held, result) : InteractionResult.PASS;
	} // end tryFormSingleBlock()
	
	public static InteractionResult tryFormSingleBlockInPlaceBlock(@NotNull UseOnContext context, @NotNull TagKey<Block> tag, @NotNull BlockState result) 
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
		
		if(state.is(tag)) 
		{
			succeeded = true;
			if(!level.isClientSide) 
			{
				level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
				level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
			}
		}
		
		return succeeded ? EssenceOfSoulsBottleItem.formSucceeded(context, held, result) : InteractionResult.PASS;
	} // end tryFormSingleBlockInPlaceBlock()
	
	public static InteractionResult tryFormSurroundedBlock(@NotNull UseOnContext context, @NotNull TagKey<Block> inner, @NotNull TagKey<Block> outer, @NotNull Item result) 
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
		
		if(state.is(outer)) 
		{
			BlockPos.MutableBlockPos mbpos = new BlockPos.MutableBlockPos();
			for(Direction d : Direction.allShuffled(level.random)) 
			{
				if(level.getBlockState(mbpos.set(position.relative(d))).is(inner)) 
				{
					succeeded = true;
					for(Direction d2 : Direction.allShuffled(level.random)) 
					{
						if(!level.getBlockState(mbpos.relative(d2)).is(outer)) 
						{
							succeeded = false;
							break;
						}
					}
					if(succeeded) 
					{
						if(!level.isClientSide) 
						{
							level.setBlock(mbpos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
							level.playSound((Entity) null, mbpos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
							for(Direction d2 : Direction.allShuffled(level.random)) 
							{
								level.setBlock(mbpos.relative(d2), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
								level.playSound((Entity) null, mbpos.relative(d2), SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
							}
						}
						break;
					}
				}
			}
		}
		
		return succeeded ? EssenceOfSoulsBottleItem.formSucceeded(context, held, result) : InteractionResult.PASS;
	} // end tryFormSurroundedBlock()
	
	public static InteractionResult tryFormVerticalTwoBlock(@NotNull UseOnContext context, @NotNull TagKey<Block> higher, @NotNull TagKey<Block> lower, @NotNull Item result) 
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
		
		if(state.is(higher)) 
		{
			BlockState below = level.getBlockState(position.below());
			if(below.is(lower)) 
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
		if(!succeeded && state.is(lower)) 
		{
			BlockState above = level.getBlockState(position.above());
			if(above.is(higher)) 
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
		
		return succeeded ? EssenceOfSoulsBottleItem.formSucceeded(context, held, result) : InteractionResult.PASS;
	} // end tryFormVerticalTwoBlock()
	
	public static InteractionResult tryFormVerticalThreeBlock(@NotNull UseOnContext context, @NotNull TagKey<Block> top, @NotNull TagKey<Block> middle, @NotNull TagKey<Block> bottom, @NotNull Item result) 
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
		
		if(state.is(top)) 
		{
			BlockState below = level.getBlockState(position.below());
			if(below.is(middle)) 
			{
				BlockState belowBelow = level.getBlockState(position.below().below());
				if(belowBelow.is(bottom)) 
				{
					succeeded = true;
					if(!level.isClientSide) 
					{
						level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
						level.setBlock(position.below(), Blocks.AIR.defaultBlockState(), 3);
						level.setBlock(position.below().below(), Blocks.AIR.defaultBlockState(), 3);
						level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
					}
				}
			}
		}
		if(!succeeded && state.is(middle)) 
		{
			BlockState above = level.getBlockState(position.above());
			if(above.is(top)) 
			{
				BlockState below = level.getBlockState(position.below());
				if(below.is(bottom)) 
				{
					succeeded = true;
					if(!level.isClientSide) 
					{
						level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
						level.setBlock(position.above(), Blocks.AIR.defaultBlockState(), 3);
						level.setBlock(position.below(), Blocks.AIR.defaultBlockState(), 3);
						level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
					}
				}
			}
		}
		if(state.is(bottom)) 
		{
			BlockState above = level.getBlockState(position.above());
			if(above.is(middle)) 
			{
				BlockState aboveAbove = level.getBlockState(position.above().above());
				if(aboveAbove.is(top)) 
				{
					succeeded = true;
					if(!level.isClientSide) 
					{
						level.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
						level.setBlock(position.above(), Blocks.AIR.defaultBlockState(), 3);
						level.setBlock(position.above().above(), Blocks.AIR.defaultBlockState(), 3);
						level.playSound((Entity) null, position, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 6.0f, 6.0f);
					}
				}
			}
		}
		
		return succeeded ? EssenceOfSoulsBottleItem.formSucceeded(context, held, result) : InteractionResult.PASS;
	} // end tryFormVerticalThreeBlock()
	
	
	private static InteractionResult formSucceeded(@NotNull UseOnContext context, @NotNull ItemStack held, @NotNull BlockState result) 
	{
		// TODO, maybe play particles			
		return EssenceOfSoulsBottleItem.formSucceeded(context, held, (l, bp) -> l.setBlock(bp, result, Block.UPDATE_ALL));
	} // end formSucceeded()
	
	private static InteractionResult formSucceeded(@NotNull UseOnContext context, @NotNull ItemStack held, @NotNull Item result) 
	{
		// TODO, maybe play particles			
		return EssenceOfSoulsBottleItem.formSucceeded(context, held, (l, bp) -> InventoryUtil.drop(l, bp, new ItemStack(result)));
	} // end formSucceeded()
	
	private static InteractionResult formSucceeded(@NotNull UseOnContext context, @NotNull ItemStack held, @NotNull BiConsumer<Level, BlockPos> f) 
	{
		Level level = context.getLevel();
		BlockPos position = context.getClickedPos();
		if (!level.isClientSide)
		{
			Player player = context.getPlayer();
			
			if(player == null || !player.getAbilities().instabuild) 
			{
				if(held.hasCraftingRemainingItem())
				{
					if (!(player != null && player.getInventory().add(held.getCraftingRemainingItem())))
					{
						InventoryUtil.drop(level, position, held.getCraftingRemainingItem());
					}
				}
				held.shrink(1);
			}
			// TODO, maybe play particles
			f.accept(level, position);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	} // end formSucceeded()

} // end class