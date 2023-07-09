package com.gsr.gsr_yatm;

import java.util.Collection;

import com.gsr.gsr_yatm.block.conduit.IConduit;
import com.gsr.gsr_yatm.block.plant.tree.StrippedSapLogBlock;
import com.gsr.gsr_yatm.fluid.item.GlassBottleItemStack;
import com.gsr.gsr_yatm.registry.YATMBlocks;
import com.gsr.gsr_yatm.registry.YATMItems;
import com.gsr.gsr_yatm.registry.YATMMobEffects;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class YATMGameEvents
{
//	private static final Map<Block, Pair<BlockState, Item>> 
	
	public static void register(IEventBus eventBus) 
	{
		eventBus.addListener(YATMGameEvents::blockToolModification);
		eventBus.addListener(YATMGameEvents::entityDamaged);
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
		else if(event.getToolAction() == ToolActions.AXE_WAX_OFF) 
		{
			BlockState on = event.getState();
			BlockState resSta = stripWires(on, event.getContext());
			
			event.setFinalState(resSta);
		}
	} // end blockToolModification
	
	private static void entityDamaged(LivingDamageEvent event) 
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
	} // end onEntityDamaged()

	private static void recipesUpdated(RecipesUpdatedEvent event) 
	{
		RecipeUtilities.recipesUpdated();
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
			resSta = YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis).setValue(StrippedSapLogBlock.FACING, facing);
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get()) 
		{
			Direction.Axis axis = toolUsedOn.getValue(RotatedPillarBlock.AXIS);
			Direction clickFaceDir = context.getClickedFace();
			Direction facing = axis != Axis.Y ? Direction.DOWN : (clickFaceDir.getAxis() == Axis.Y ? context.getHorizontalDirection().getOpposite() : clickFaceDir);
			resSta = YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis).setValue(StrippedSapLogBlock.FACING, facing);
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
	
	private static BlockState stripWires(BlockState toolUsedOn, UseOnContext context) 
	{
		BlockState resSta = null;
		if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_ONE_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.ONE_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.EIGHT_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.WAX_BIT_ITEM.get()));
		}
		
		
		
		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_ONE_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.ONE_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_EIGHT_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.EIGHT_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
		}
		else if(toolUsedOn.getBlock() == YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()) 
		{
			resSta = copyWireInfo(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), toolUsedOn);
			InventoryUtilities.drop(context.getLevel(), context.getClickedPos(), new ItemStack(YATMItems.RUBBER_SCRAP.get()));
		}
		return resSta;
	} // end stripLogs()
	
	private static BlockState copyWireInfo(Block to, BlockState from) 
	{
		return to.defaultBlockState()
				.setValue(IConduit.UP, from.getValue(IConduit.UP))
				.setValue(IConduit.DOWN, from.getValue(IConduit.DOWN))
				.setValue(IConduit.NORTH, from.getValue(IConduit.NORTH))
				.setValue(IConduit.SOUTH, from.getValue(IConduit.SOUTH))
				.setValue(IConduit.EAST, from.getValue(IConduit.EAST))
				.setValue(IConduit.WEST, from.getValue(IConduit.WEST));
	} // end copyWireInfo()
	
} // end class