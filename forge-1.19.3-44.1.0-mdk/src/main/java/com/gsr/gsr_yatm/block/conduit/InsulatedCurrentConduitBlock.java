package com.gsr.gsr_yatm.block.conduit;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.utilities.VoxelShapeGetter;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.Lazy;

public class InsulatedCurrentConduitBlock extends CurrentConduitBlock
{
	private final Lazy<Block> m_strippedBlock;
	private final Lazy<Item> m_strippedMaterial;

	public InsulatedCurrentConduitBlock(Properties properties, VoxelShapeGetter shape, ConductorProperties conductorProperties, Supplier<Block> strippedBlock, Supplier<Item> strippedMaterial)
	{
		super(properties, shape, conductorProperties);
		this.m_strippedBlock = Lazy.of(strippedBlock);
		this.m_strippedMaterial = Lazy.of(strippedMaterial);
	} // end constructor



	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
	{
		if (toolAction == ToolActions.AXE_WAX_OFF)
		{
			if (!simulate && this.m_strippedMaterial.get() != null)
			{
				NonNullList<ItemStack> l = NonNullList.create();
				l.add(new ItemStack(this.m_strippedMaterial.get()));
				Containers.dropContents(context.getLevel(), context.getClickedPos(), l);
			}
			return this.m_strippedBlock.get().getStateForPlacement(new BlockPlaceContext(context));
		}
		return null;
	} // end getToolModifiedState()

} // end class