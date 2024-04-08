package com.gsr.gsr_yatm.worldgen.root_placer;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.gsr.gsr_yatm.registry.YATMRootPlacerTypes;
import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class LayeredRandomSpreadRootPlacer extends RootPlacer
{
	public static final Codec<LayeredRandomSpreadRootPlacer> CODEC = RecordCodecBuilder.create((instance) ->
	RootPlacer.rootPlacerParts(instance).and(LayeredRandomSpreadRootPlacer.thisParts(instance)).apply(instance, LayeredRandomSpreadRootPlacer::new));
	
	private static <P extends LayeredRandomSpreadRootPlacer> P4<Mu<P>, HolderSet<Block>, IntProvider, IntProvider, Integer> thisParts(RecordCodecBuilder.Instance<P> instance)
	{
		return instance.group
				(
				RegistryCodecs.homogeneousList(ForgeRegistries.BLOCKS.getRegistryKey()).fieldOf("can_root_into").forGetter((f) -> f.m_canRootInto),
				IntProvider.codec(1, 16).fieldOf("radius").forGetter((f) -> f.m_radius),
				IntProvider.codec(1, 512).fieldOf("depth").forGetter((f) -> f.m_depth),
				Codec.intRange(0, 256).fieldOf("attempts_per_layer").forGetter((f) -> f.m_attemptsPerLayer)  
			    );
	} // end thisParts
	
	
	
	protected final HolderSet<Block> m_canRootInto;
	protected final IntProvider m_radius;
	protected final IntProvider m_depth;
	protected final int m_attemptsPerLayer;
	
	public LayeredRandomSpreadRootPlacer(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement,
			HolderSet<Block> canRootInto,
			IntProvider radius,
			IntProvider depth,
			int attemptsPerLayer
			)
	{
		super(trunkOffsetY, rootProvider, aboveRootPlacement);
		this.m_canRootInto = canRootInto;
		this.m_radius = radius;
		this.m_depth = depth;
		this.m_attemptsPerLayer = attemptsPerLayer;
	} // end constructor

	
	
	@Override
	protected RootPlacerType<LayeredRandomSpreadRootPlacer> type()
	{
		return YATMRootPlacerTypes.LAYERED_RANDOM_SPREAD_ROOT_PLACER.get();
	} // end type()

	@Override
	public boolean placeRoots(LevelSimulatedReader levelSimReader, BiConsumer<BlockPos, BlockState> consumer, RandomSource random, BlockPos startOfTrunkIThink, BlockPos trunkWithOffsetIdGuess, TreeConfiguration treeConfiguration)
	{
		int radius = this.m_radius.sample(random);
		int depth = this.m_depth.sample(random);
		BlockPos toLookAt = startOfTrunkIThink.below();
		int scannedDepth = 0;
		if(depth > 0) 
		{
			if(this.canPlaceAt(levelSimReader, toLookAt)) 
			{
				consumer.accept(toLookAt, this.rootProvider.getState(random, toLookAt));
				toLookAt = toLookAt.below();
				scannedDepth++;
				while(scannedDepth++ < depth) 
				{
					if(this.canPlaceAt(levelSimReader, toLookAt)) 
					{
						for(int i = 0; i < this.m_attemptsPerLayer; i++) 
						{
							BlockPos picked = toLookAt.offset( 
					        		 random.nextInt(radius) - random.nextInt(radius), 
					        		 0, 
					        		 random.nextInt(radius) - random.nextInt(radius));
							if(this.canPlaceAt(levelSimReader, picked)) 
							{
								consumer.accept(picked, this.rootProvider.getState(random, picked));
							}
						}
						toLookAt = toLookAt.below();
					}
				}
				return true;
			}
		}
		return false;
	} // end placeRoots()
	
	
	
	private boolean canPlaceAt(LevelSimulatedReader levelSimReader, BlockPos position) 
	{
		for(Holder<Block> block : this.m_canRootInto) 
		{
			if(levelSimReader.isStateAtPosition(position, (bs) -> bs.getBlock() == block.get()))
			{
				return true;
			}
		}
		return false;
	} // end canPlaceAt()	
	
} // end class