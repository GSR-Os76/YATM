package com.gsr.gsr_yatm.block.plant.tree;

import com.gsr.gsr_yatm.registry.YATMFoliagePlacerTypes;
import com.mojang.datafixers.Products.P6;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class AgedFoliagePlacer extends RandomSpreadFoliagePlacer
{
	public static final Codec<AgedFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) 
			-> (FoliagePlacer.foliagePlacerParts(instance).and(AgedFoliagePlacer.thisParts(instance))).apply(instance, AgedFoliagePlacer::new));

	private static <P extends AgedFoliagePlacer> P6<Mu<P>, IntProvider, Integer, BlockStateProvider, BlockStateProvider, HolderSet<Block>, HolderSet<Block>> thisParts(RecordCodecBuilder.Instance<P> instance)
	{
		return instance.group
				(
				IntProvider.codec(1, 512).fieldOf("foliage_height").forGetter((f) -> f.m_foliageHeight),
			    Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter((f) -> f.m_leafPlacementAttempts),  
			    // unfortunately can't add enough options for this, or maybe I'm missing something: Codec.floatRange(0f, 1f).fieldOf("old_under_threshold").forGetter((f) -> f.m_oldUnderThreshold),
			    BlockStateProvider.CODEC.fieldOf("old_leaves_provider").orElse(BlockStateProvider.simple(Blocks.AIR)).forGetter((f) -> f.m_oldLeavesProvider),
				BlockStateProvider.CODEC.fieldOf("aerial_root_provider").orElse(BlockStateProvider.simple(Blocks.AIR)).forGetter((f) -> f.m_aerialRootProvider),
				RegistryCodecs.homogeneousList(ForgeRegistries.BLOCKS.getRegistryKey()).fieldOf("roots_grow_from").forGetter((f) -> f.m_rootsGrowFrom),
				RegistryCodecs.homogeneousList(ForgeRegistries.BLOCKS.getRegistryKey()).fieldOf("roots_grow_through").forGetter((f) -> f.m_rootsGrowThrough)
				)
				;
	} // end thisParts
	
	
	
	protected final IntProvider m_foliageHeight;
	protected final int m_leafPlacementAttempts;
	protected final float m_oldUnderThreshold = 0.5f;
	protected final BlockStateProvider m_oldLeavesProvider;
	protected final BlockStateProvider m_aerialRootProvider;
	protected final HolderSet<Block> m_rootsGrowFrom;
	protected final HolderSet<Block> m_rootsGrowThrough;
	
	
	
	public AgedFoliagePlacer(IntProvider radius, IntProvider offset, 
			IntProvider foliageHeight, 
			int leafPlacementAttempts, 
			// float oldUnderThreshold,
			BlockStateProvider oldLeavesProvider,
			BlockStateProvider aerialRootProvider,
			HolderSet<Block> rootsGrowFrom,
			HolderSet<Block> rootsGrowThrough)
	{
		super(radius, offset, foliageHeight, leafPlacementAttempts);
		this.m_foliageHeight = foliageHeight;
		this.m_leafPlacementAttempts = leafPlacementAttempts;
		// this.m_oldUnderThreshold = oldUnderThreshold;
		this.m_oldLeavesProvider = oldLeavesProvider;
		this.m_aerialRootProvider = aerialRootProvider;
		this.m_rootsGrowFrom = rootsGrowFrom;
		this.m_rootsGrowThrough = rootsGrowThrough;
	} // end constructor



	@Override
	protected FoliagePlacerType<?> type()
	{
		return YATMFoliagePlacerTypes.RUBBER_FOLIAGE_PLACER.get();
	} // end type()

	
	@Override
	protected void createFoliage(LevelSimulatedReader levelSimReader, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfig, int trunkHeight, FoliageAttachment attachment, int foliageHeight, int radius, int offsetMaybe)
	{
		this.putOutFoliage(levelSimReader, foliageSetter, random, treeConfig, trunkHeight, attachment, foliageHeight, radius, offsetMaybe);
		this.putDownRoots(levelSimReader, foliageSetter, random, treeConfig, trunkHeight, attachment, foliageHeight, radius, offsetMaybe);
	} // end createFoliage()

	private void putOutFoliage(LevelSimulatedReader levelSimReader, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfig, int trunkHeight, FoliageAttachment attachment, int foliageHeight, int radius, int offsetMaybe)
	{
	      for(int i = 0; i < this.m_leafPlacementAttempts; ++i) 
	      {
	    	  BlockPos picked = attachment.pos().offset( 
	        		 random.nextInt(radius) - random.nextInt(radius), 
	        		 random.nextInt(foliageHeight) - random.nextInt(foliageHeight), 
	        		 random.nextInt(radius) - random.nextInt(radius));
	         this.tryPlaceAgeConsideredLeaf(levelSimReader, foliageSetter, random, treeConfig, picked, attachment.pos(), foliageHeight, radius);
	      }
	} // end putOutFoliage()
	
	private void putDownRoots(LevelSimulatedReader levelSimReader, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfig, int trunkHeight, FoliageAttachment attachment, int foliageHeight, int radius, int offsetMaybe) 
	{
		BlockPos toLookAt = attachment.pos();
		boolean foundRootable = false;
		boolean sentDownRoots = false;
		boolean complete = false;
		for(int i = 0; i < (random.nextFloat() > .5f ? random.nextInt((trunkHeight * 2) + 1) : ((trunkHeight * 2) + 1)); i++) 
		{		
			// scan down until you find something rootable
			// continue the scan. if it's still rootable just keep going.
			// if it's something roots can grow through, start growing roots, and stop considering the rootability
			// once something roots can't grow through's found after starting, terminate the scan
			if(foundRootable) 
			{
				boolean grewARootThisCycle = false;
				//for (Holder<Block> block : this.m_rootsGrowThrough)
				//{
					//if (levelSimReader.isStateAtPosition(toLookAt, (bs) -> bs.getBlock() == block.get()))// block.get() == currentlyOnBlock)
					//{
				if(this.canRootThrough(levelSimReader, toLookAt)) 
				{
						foliageSetter.set(toLookAt, this.m_aerialRootProvider.getState(random, toLookAt));
						sentDownRoots = true;
						grewARootThisCycle = true;
				}
						//break;
					//}
				//}
				if(!grewARootThisCycle) 
				{
					complete = true;
				}
			}
			if(!sentDownRoots) 
			{
				boolean foundThisSearch = false;
				levelSimReader.isStateAtPosition(toLookAt, (bs) -> 
				{
					return false;
				});
				for (Holder<Block> block : this.m_rootsGrowFrom)
				{
					if (levelSimReader.isStateAtPosition(toLookAt, (bs) -> bs.getBlock() == block.get()))// block.get() == currentlyOnBlock)
					{
						foundThisSearch = true;
						foundRootable = true;
						break;
					}
				}
				if(foundRootable && !foundThisSearch) 
				{
					complete = true;
				}
				foundRootable = foundThisSearch;
			}
			if(sentDownRoots && complete) 
			{
				// since we're on a failed to aerial root block, move back up to the the last placed root. 
				// this is to match the fact the trunk's first pos is what's seemingly provided to the automatic root placer call
				BlockPos toRootAt = toLookAt.above();
				treeConfig.rootPlacer.ifPresent((rp) -> 
				{
					rp.placeRoots(levelSimReader, (bp, bs) -> foliageSetter.set(bp, bs), random, toRootAt, toRootAt, treeConfig);
				});
			}
			if(complete) 
			{
				break;
			}
			
			// move scan down for the next step.
			toLookAt = toLookAt.below();
		}
	} // end putDownRoots()
	
	
	
	
	@Override
	public int foliageHeight(RandomSource random, int p_225602_, TreeConfiguration treeConfiguration)
	{
		return this.m_foliageHeight.sample(random);
	} // end foliageHeight()
	
	
	@Override
	protected boolean shouldSkipLocation(RandomSource random, int p_225596_, int p_225597_, int p_225598_, int p_225599_, boolean p_225600_)
	{
		return false;
	} // end shouldSkipLocation()

	
	
	
	protected boolean tryPlaceAgeConsideredLeaf(LevelSimulatedReader levelSimReader, FoliagePlacer.FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfig, BlockPos at, BlockPos attachmentPos, int foliageHeight, int radius)
	{
		if (!TreeFeature.validTreePos(levelSimReader, at))
		{
			return false;
		}
		else
		{
			// TODO, consider, could calculate the angle relative to horizontal, and use that to determine the distance to divide the distance between attachment and this's pos by, to get the percentage out, to pick age. but is it worth it, worth the costs?
			//float distanceProportion = (((float)Math.sqrt(Math.abs(attachmentPos.distSqr(at)))) / ((float)radius));
			float distanceProportion = (((float)Math.sqrt(Math.abs(attachmentPos.distSqr(at)))) / ((float)foliageHeight));
			boolean useOld = distanceProportion <= this.m_oldUnderThreshold;
			BlockState blockstate = useOld
					? this.m_oldLeavesProvider.getState(random, at)
					: treeConfig.foliageProvider.getState(random, at);
			if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED))
			{
				blockstate = blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(levelSimReader.isFluidAtPosition(at, (fs) -> fs.isSourceOfType(Fluids.WATER))));
			}

			foliageSetter.set(at, blockstate);
			return true;
		}
	} // end tryPlaceAgeConsideredLeaf()

	private boolean canRootThrough(LevelSimulatedReader levelSimReader, BlockPos position) 
	{
		boolean canRootThrough = false;
		for (Holder<Block> block : this.m_rootsGrowThrough)
		{
			if (levelSimReader.isStateAtPosition(position, (bs) -> bs.getBlock() == block.get()))
			{
				canRootThrough = true;
				break;
			}
		}
		return TreeFeature.validTreePos(levelSimReader, position) || canRootThrough;
	} // end canRootThrough()	
} // end class