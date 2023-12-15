package com.gsr.gsr_yatm;

import java.util.Collection;

import com.gsr.gsr_yatm.block.IHarvestableBlock;
import com.gsr.gsr_yatm.block.plant.tree.TappedLogBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.item.fluid.GlassBottleItemStack;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMMobEffects;
import com.gsr.gsr_yatm.registry.custom.YATMRegistries;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.event.level.BlockEvent.CropGrowEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.IEventBus;

public class YATMGameEvents
{
	public static void register(IEventBus eventBus) 
	{
		YATMRegistries.register(eventBus);
		eventBus.addListener(YATMGameEvents::blockToolModification);
		eventBus.addListener(YATMGameEvents::entityDamaged);
		eventBus.addListener(YATMGameEvents::cropsGrowPre);
		eventBus.addListener(YATMGameEvents::recipesUpdated);
		eventBus.addGenericListener(ItemStack.class, YATMGameEvents::attachItemStackCapabilities);
	} // end register()
	

	
	private static void blockToolModification(BlockToolModificationEvent event) 
	{
		if(event.getToolAction() == ToolActions.AXE_STRIP) 
		{
			BlockState on = event.getState();
			BlockState resSta = stripLogs(on, event.getContext());
			
			event.setFinalState(resSta);
		}
		YATMGameEvents.tryHarvest(event);
	} // end blockToolModification
	
	private static void entityDamaged(LivingDamageEvent event) 
	{
		YATMGameEvents.tryWitherSoulAffliction(event);
		YATMGameEvents.tryWitherSoulAdornedNetheriteArmorPieces(event);
	} // end onEntityDamaged()
	
	private static void cropsGrowPre(CropGrowEvent.Pre event)
	{
		if(YATMGameEvents.tryCactusHeightRestriction(event)) 
		{
			return;
		}
		YATMGameEvents.tryVariegateCactus(event);
	} // end cropsGrowPree
	
	private static void recipesUpdated(RecipesUpdatedEvent event) 
	{
		RecipeUtil.recipesUpdated();
	} // end recipesUpdated()
	
	private static void attachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) 
	{
		if(event.getObject().getItem() == Items.GLASS_BOTTLE) 
		{
			event.addCapability(new ResourceLocation(YetAnotherTechMod.MODID, "glass_bottle_fluid_handler"), new GlassBottleItemStack(event.getObject()));
		}
	} // end attachItemStackCapabilities()
	
	
	
	private static BlockState stripLogs(BlockState toolUsedOn, UseOnContext context) 
	{
		BlockState resSta = null;
		if(toolUsedOn.getBlock() == YATMBlocks.RUBBER_WOOD.get()) 
		{
			resSta = YATMBlocks.STRIPPED_RUBBER_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, toolUsedOn.getValue(RotatedPillarBlock.AXIS));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get()) 
		{
			resSta = YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, toolUsedOn.getValue(RotatedPillarBlock.AXIS));
		}
		
		else if(toolUsedOn.getBlock() == YATMBlocks.RUBBER_LOG.get()) 
		{
			Direction.Axis axis = toolUsedOn.getValue(RotatedPillarBlock.AXIS);
			Direction clickFaceDir = context.getClickedFace();
			Direction facing = axis != Axis.Y ? Direction.DOWN : (clickFaceDir.getAxis() == Axis.Y ? context.getHorizontalDirection().getOpposite() : clickFaceDir);
			resSta = YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis).setValue(TappedLogBlock.FACING, facing);
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get()) 
		{
			Direction.Axis axis = toolUsedOn.getValue(RotatedPillarBlock.AXIS);
			Direction clickFaceDir = context.getClickedFace();
			Direction facing = axis != Axis.Y ? Direction.DOWN : (clickFaceDir.getAxis() == Axis.Y ? context.getHorizontalDirection().getOpposite() : clickFaceDir);
			resSta = YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis).setValue(TappedLogBlock.FACING, facing);
		}
		
		else if(toolUsedOn.getBlock() == YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get()) 
		{
			resSta = YATMBlocks.STRIPPED_RUBBER_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, toolUsedOn.getValue(RotatedPillarBlock.AXIS));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get()) 
		{
			resSta = YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, toolUsedOn.getValue(RotatedPillarBlock.AXIS));
		}
		return resSta;
	} // end stripLogs()
	
//	private static BlockState stripWires(BlockState toolUsedOn, UseOnContext context) 
//	{
//		BlockState resSta = null;
//		if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_ONE_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.ONE_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.EIGHT_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
//		}
//		
//		
//		
//		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_ONE_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.ONE_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_EIGHT_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.EIGHT_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
//		}
//		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()) 
//		{
//			resSta = copyWireInfo(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), toolUsedOn);
//			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
//		}
//		return resSta;
//	} // end stripLogs()
//	
//	private static BlockState copyWireInfo(Block to, BlockState from) 
//	{
//		return to.defaultBlockState()
//				.setValue(IConduit.UP, from.getValue(IConduit.UP))
//				.setValue(IConduit.DOWN, from.getValue(IConduit.DOWN))
//				.setValue(IConduit.NORTH, from.getValue(IConduit.NORTH))
//				.setValue(IConduit.SOUTH, from.getValue(IConduit.SOUTH))
//				.setValue(IConduit.EAST, from.getValue(IConduit.EAST))
//				.setValue(IConduit.WEST, from.getValue(IConduit.WEST));
//	} // end copyWireInfo()
	
	private static boolean tryHarvest(BlockToolModificationEvent event)
	{
		BlockState state = event.getState();
		if(state.getBlock() instanceof IHarvestableBlock harvestable && harvestable.allowEventHandling()) 
		{
			Level level = event.getContext().getLevel();
			BlockPos position = event.getPos();
			ToolAction action = event.getToolAction();
			if(harvestable.validActions(level, state, position).contains(action)) 
			{
				event.setFinalState(harvestable.getResultingState(level, state, position, action));
				if(!event.isSimulated() && level instanceof ServerLevel sl) 
				{
					InventoryUtil.drop(level, position, harvestable.getResults(sl, state, position, action));
					harvestable.onHarvest(level, state, position, action);
				}
				return true;
			}
		}
		return false;
	} // end tryHarvest()
	
	private static void tryWitherSoulAffliction(LivingDamageEvent event) 
	{
		if(event.getSource().is(DamageTypes.WITHER)) 
		{
			LivingEntity entity = event.getEntity();
			Collection<MobEffectInstance> effects = entity.getActiveEffects();
			for(MobEffectInstance effect : effects) 
			{
				if(effect.getEffect() == YATMMobEffects.SOUL_AFFLICTION.get()) 
				{
					entity.removeEffect(YATMMobEffects.SOUL_AFFLICTION.get());
					entity.addEffect(new MobEffectInstance(
							MobEffects.WITHER, 
							effect.getDuration(), 
							effect.getAmplifier(), 
							effect.isAmbient(), 
							effect.isVisible(),
							effect.showIcon()));
					break;
				}
			}
		}
	} // end tryWitherSoulAffliction()
	
	private static void tryWitherSoulAdornedNetheriteArmorPieces(LivingDamageEvent event) 
	{
		if (event.getSource().is(DamageTypes.WITHER) && RandomSource.createNewThreadLocalInstance().nextInt(32) == 0 && event.getEntity() instanceof Player player)
		{
			for (ItemStack i : player.getArmorSlots())
			{
				if (i.getItem() == YATMItems.SOUL_ADORNED_NETHERITE_HELMET.get())
				{
					player.getInventory().setItem(36 + 3, copyDurabilityByProportion(i, new ItemStack(YATMItems.SOUL_ADORNED_NETHERITE_HELMET.get())));
				}
				else if (i.getItem() == YATMItems.SOUL_ADORNED_NETHERITE_CHESTPLATE.get())
				{
					player.getInventory().setItem(36 + 2, copyDurabilityByProportion(i, new ItemStack(YATMItems.DECAY_NETHERITE_CHESTPLATE.get())));
				}
				else if (i.getItem() == YATMItems.SOUL_ADORNED_NETHERITE_LEGGINGS.get())
				{
					player.getInventory().setItem(36 + 1, copyDurabilityByProportion(i, new ItemStack(YATMItems.DECAY_NETHERITE_LEGGINGS.get())));
				}
				else if (i.getItem() == YATMItems.SOUL_ADORNED_NETHERITE_BOOTS.get())
				{
					player.getInventory().setItem(36 + 0, copyDurabilityByProportion(i, new ItemStack(YATMItems.DECAY_NETHERITE_BOOTS.get())));
				}
			}

		}
	} // end tryWitherSoulArmor()
	
	// returns true if growth is being cancelled, otherwise returns false.
	private static boolean tryCactusHeightRestriction(CropGrowEvent.Pre event)
	{
		BlockState state = event.getState();
		if(!state.is(Blocks.CACTUS)) 
		{
			return false;
		}
		
		int cactusMaxHeight = 3;
		LevelAccessor la = event.getLevel();
		BlockPos position = event.getPos();
		int cactusBelow;
		for (cactusBelow = 1; la.getBlockState(position.below(cactusBelow)).is(YATMBlockTags.FORGE_CACTUSES_KEY) && cactusBelow <= cactusMaxHeight; ++cactusBelow);
		
		// TODO, this check is meant to match the vanilla one, how less than three below limits the total height to three is escaping me though.
		if(!(cactusBelow < cactusMaxHeight)) 
		{
			event.setResult(Result.DENY);
			return true;
		}
		return false;
	} // end tryCactusHeightRestriction()
	
	private static boolean tryVariegateCactus(CropGrowEvent.Pre event)
	{
		if(event.getState().is(Blocks.CACTUS) && event.getLevel() instanceof Level l && (l.getRandom().nextInt(622) == 0))
		{
			BlockState goingTo = YATMBlocks.VARIEGATED_CACTUS.get().defaultBlockState();
			BlockPos position = event.getPos();
			if (ForgeHooks.onCropsGrowPre(l, position, goingTo, true)) 
			{
				if(!l.isClientSide()) 
				{
					l.setBlock(position, goingTo, 3);
				}
				ForgeHooks.onCropsGrowPost(l, position, event.getState());
				event.setResult(Result.DENY);
				return true;
			}
		}
		return false;
	} // end tryVariegateCactus()
	
	public static ItemStack copyDurabilityByProportion(ItemStack from, ItemStack to) 
	{
		// TODO, investigate this's not working issues
		if(!to.isDamageableItem()) 
		{
			return to;
		}
		if(!from.isDamaged()) 
		{
			to.setDamageValue(0);
			return to;
		}
		float prop = ((float)to.getDamageValue()) / ((float)to.getMaxDamage());
		to.setDamageValue((int)(((float)to.getMaxDamage()) * (prop)));
		return to;
	} // end copyDurabilityByProportion()
	
} // end class