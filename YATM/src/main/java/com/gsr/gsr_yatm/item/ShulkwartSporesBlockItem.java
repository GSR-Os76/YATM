package com.gsr.gsr_yatm.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;

public class ShulkwartSporesBlockItem extends Item
{
	private final List<Block> m_blocks;

	
	
	public ShulkwartSporesBlockItem(Properties properties, Block... blocks)
	{
		super(properties);
		this.m_blocks = ImmutableList.copyOf(blocks);
	} // end constructor



	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		Level level = context.getLevel();
		
		Block picked = this.pickBlock(level.random);
		BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
		if (picked instanceof BushBlock bb)
		{
			if(bb.canSurvive(level.getBlockState(pos), level, pos))
			{
				if (!level.isClientSide)
				{
					level.setBlock(pos, picked.getStateForPlacement(new BlockPlaceContext(context)), 3);
				}
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		else
		{
			if (!level.isClientSide)
			{
				level.setBlock(pos, picked.getStateForPlacement(new BlockPlaceContext(context)), 3);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return InteractionResult.PASS;
	} // end useOn()

	protected @Nullable Block pickBlock(RandomSource random) 
	{
		return m_blocks.get(random.nextInt(this.m_blocks.size()));
	} // end pickBlock()
	
//	@Override
//	public InteractionResult useOn(UseOnContext context)
//	{
//		;
//		List<Integer> unattemptedIndices = new ArrayList<Integer>(List.of(ConfigurableInventoryWrapper.createDefaultTranslationTable(this.m_blocks.size())));
//		Block b 
//		if()
//		return super.useOn(p_41427_);
//	} // end useOn
//
//	
//	
//	protected @Nullable Block pickBlock(RandomSource random, List<Integer> includedIndices) 
//	{
//		return m_blocks.get(EAT_DURATION)
//	}
	
} // end class