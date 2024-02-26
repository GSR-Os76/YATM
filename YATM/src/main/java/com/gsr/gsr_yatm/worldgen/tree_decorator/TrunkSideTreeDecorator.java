package com.gsr.gsr_yatm.worldgen.tree_decorator;

import com.gsr.gsr_yatm.registry.YATMTreeDecoratorTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TrunkSideTreeDecorator extends TreeDecorator
{
	public static final Codec<TrunkSideTreeDecorator> CODEC = RecordCodecBuilder.create((inst) -> TrunkSideTreeDecorator.thissParts(inst).apply(inst, TrunkSideTreeDecorator::new));

	protected static final <P extends TrunkSideTreeDecorator> P2<Mu<P>, BlockStateProvider, Float> thissParts(RecordCodecBuilder.Instance<P> instance) 
	{
		return instance.group(
				BlockStateProvider.CODEC.fieldOf("decoration_provider").forGetter((i) -> i.m_decorationProvider),
				Codec.floatRange(0f, 1f).fieldOf("chance_per_face").forGetter((i) -> i.m_chancePerFace)
				);
	} // end thissParts()
	
	
	
	protected final BlockStateProvider m_decorationProvider;
	protected final float m_chancePerFace;

	public TrunkSideTreeDecorator(BlockStateProvider decorationProvider, float chancePerFace)
	{
		this.m_decorationProvider = decorationProvider;
		this.m_chancePerFace = chancePerFace;
	} // end constructor



	@Override
	protected TreeDecoratorType<?> type()
	{
		return YATMTreeDecoratorTypes.TRUNK_SIDE_TREE_DECORATOR.get();
	} // end type()

	@Override
	public void place(Context context)
	{
		RandomSource randomsource = context.random();
		for (BlockPos pos : context.logs())
		{
			for(Direction dir: Direction.Plane.HORIZONTAL)
			if (randomsource.nextFloat() < this.m_chancePerFace)
			{
				BlockPos considering = pos.relative(dir);
				if (context.isAir(considering))
				{
					this.tryAddDecoration(context, considering, dir);
				}
			}
		}
	} // end place()

	
	
	private boolean tryAddDecoration(Context context, BlockPos at, Direction facing)
	{
		if (!context.isAir(at))
		{
			return false;
		}
		else
		{
			BlockState blockState = this.m_decorationProvider.getState(context.random(), at);
			if(blockState.hasProperty(YATMBlockStateProperties.FACING_HORIZONTAL)) 
			{
				blockState = blockState.setValue(YATMBlockStateProperties.FACING_HORIZONTAL, facing);
			}
			else if(blockState.hasProperty(YATMBlockStateProperties.FACING)) 
			{
				blockState = blockState.setValue(YATMBlockStateProperties.FACING, facing);
			}
			context.setBlock(at, blockState);
			return true;
		}
	} // end tryPlaceLitter()
	
} // end class