package com.gsr.gsr_yatm.worldgen.tree_decorator;

import com.gsr.gsr_yatm.registry.YATMTreeDecoratorTypes;
import com.mojang.datafixers.Products.P5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class LeafLitterTreeDecorator extends TreeDecorator
{
	public static final Codec<LeafLitterTreeDecorator> CODEC = RecordCodecBuilder.create((inst) -> LeafLitterTreeDecorator.thissParts(inst).apply(inst, LeafLitterTreeDecorator::new));// BuiltInRegistries.TREE_DECORATOR_TYPE.byNameCodec().dispatch(TreeDecorator::type,
																	// TreeDecoratorType::codec);
	protected static final <P extends LeafLitterTreeDecorator> P5<Mu<P>, HolderSet<Block>, BlockStateProvider, IntProvider, IntProvider, Integer> thissParts(RecordCodecBuilder.Instance<P> instance) 
	{
		return instance.group(
				RegistryCodecs.homogeneousList(ForgeRegistries.BLOCKS.getRegistryKey()).fieldOf("can_litter_on").forGetter((i) -> i.m_canLitterOn),
				BlockStateProvider.CODEC.fieldOf("litter_provider").forGetter((i) -> i.m_litterProvider),
				IntProvider.codec(0, 16).fieldOf("radius").forGetter((i) -> i.m_radius),
				IntProvider.codec(0, 16).fieldOf("vertical_scan_range").forGetter((i) -> i.m_verticalScanRange),
				Codec.intRange(0, 256).fieldOf("litter_placement_attempts").forGetter((i) -> i.m_litterPlacementAttempts)
				);
	} // end thissParts()
	
	
	
	protected final HolderSet<Block> m_canLitterOn;
	protected final BlockStateProvider m_litterProvider;
	protected final IntProvider m_radius;
	protected final IntProvider m_verticalScanRange;
	protected final int m_litterPlacementAttempts;



	public LeafLitterTreeDecorator(HolderSet<Block> canLitterOn, BlockStateProvider litterProvider, IntProvider radius, IntProvider verticalScanRange, int litterPlacementAttempts)
	{
		this.m_canLitterOn = canLitterOn;
		this.m_litterProvider = litterProvider;
		this.m_radius = radius;
		this.m_verticalScanRange = verticalScanRange;
		this.m_litterPlacementAttempts = litterPlacementAttempts;
	} // end constructor



	@Override
	protected TreeDecoratorType<?> type()
	{
		return YATMTreeDecoratorTypes.LEAF_LITTER_TREE_DECORATOR.get();
	} // end type()

	@Override
	public void place(Context context)
	{
		// TODO Auto-generated method stub
		BlockPos lwstPs = context.logs().get(0);
		for (BlockPos pos : context.logs())
		{
			if (pos.getY() < lwstPs.getY())
			{
				lwstPs = pos;
			}
		}

		RandomSource random = context.random();
		int radius = this.m_radius.sample(context.random());
		int vrtScnRng = this.m_verticalScanRange.sample(context.random());
		for (int i = 0; i < this.m_litterPlacementAttempts; i++)
		{
			this.tryPlaceLitter(context, lwstPs.offset(random.nextInt(radius) - random.nextInt(radius), random.nextInt(vrtScnRng) - random.nextInt(vrtScnRng), random.nextInt(radius) - random.nextInt(radius)));
		}
	} // end place()

	private boolean tryPlaceLitter(Context context, BlockPos at)
	{
//		if (!TreeFeature.validTreePos(context.level(), at))
//		{
//			return false;
//		}
//		else 
		if (context.isAir(at) &&  context.level().isStateAtPosition(at.below(), (bs) -> placeableOn(bs)))
		{
			BlockState blockstate = this.m_litterProvider.getState(context.random(), at);
			if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED))
			{
				blockstate = blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(context.level().isFluidAtPosition(at, (fs) -> fs.isSourceOfType(Fluids.WATER))));
			}
			context.setBlock(at, blockstate);
			return true;
		}
		return false;
	} // end tryPlaceLitter()
	
	private boolean placeableOn(BlockState bs) 
	{
		for (Holder<Block> bh : this.m_canLitterOn)
		{
			if (bs.getBlock() == bh.get())
			{
				return true;
			}
		}
		return false;
	} // end placeable()
	
} // end class