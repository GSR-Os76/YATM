package com.gsr.gsr_yatm.item;

import static java.util.Map.entry;

import java.util.Map;

import com.gsr.gsr_yatm.YATMBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class InsulatingItem extends Item
{
	public static final Map<Block, Block> WAX_BIT_WAXING_TABLE = Map.ofEntries(
			entry(YATMBlocks.ONE_CU_WIRE.get(), YATMBlocks.ENAMELED_ONE_CU_WIRE.get()),
			entry(YATMBlocks.EIGHT_CU_WIRE.get(), YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get()),
			entry(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get()),
			entry(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get()),
			entry(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()));
	
	public static final Map<Block, Block> RUBBER_SCRAP_WAXING_TABLE = Map.ofEntries(
			entry(YATMBlocks.ONE_CU_WIRE.get(), YATMBlocks.INSULATED_ONE_CU_WIRE.get()),
			entry(YATMBlocks.EIGHT_CU_WIRE.get(), YATMBlocks.INSULATED_EIGHT_CU_WIRE.get()),
			entry(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get()),
			entry(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get()),
			entry(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get()));
	
	private final Map<Block, Block> m_waxingTable;
	
	
	
	public InsulatingItem(Properties properties, Map<Block, Block> waxingTable)
	{
		super(properties);
		this.m_waxingTable = waxingTable;
	} // end constructor



	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Block interacted = level.getBlockState(pos).getBlock();
		
		if(this.m_waxingTable.containsKey(interacted)) 
		{
			// I'm assuming we can trust the stack in hand to be a stack of this, otherwise why and how would this be being called: update: seems to work
			ItemStack i = context.getItemInHand();
			
			//TODO: maybe add particles and stuff on application
			i.shrink(1);
			level.setBlockAndUpdate(pos, this.m_waxingTable.get(interacted).getStateForPlacement(new BlockPlaceContext(context)));
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end onUse()

} // end class