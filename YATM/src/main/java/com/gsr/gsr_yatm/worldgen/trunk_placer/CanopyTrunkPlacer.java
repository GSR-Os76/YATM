package com.gsr.gsr_yatm.worldgen.trunk_placer;

import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.Lists;
import com.gsr.gsr_yatm.registry.YATMTrunkPlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class CanopyTrunkPlacer extends TrunkPlacer
{
	public static final Codec<CanopyTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
		TrunkPlacer.trunkPlacerParts(instance)
		.and(instance.group(
				Codec.BOOL.fieldOf("should_make_grown_on_block_dirt").orElse(false).forGetter((p) -> p.m_shouldMakeGrownOnBlockDirt), 
				Codec.floatRange(0.0F, 8.0F).fieldOf("max_curve").forGetter((p) -> p.m_maxCurve),
				IntProvider.NON_NEGATIVE_CODEC.fieldOf("branching_trunk_length").forGetter((p) -> p.m_branchingTrunkLength),
				Codec.floatRange(0.0F, 1.0F).fieldOf("branch_likelyhood").forGetter((p) -> p.m_branchLikelyhood),
				IntProvider.NON_NEGATIVE_CODEC.fieldOf("branch_length").forGetter((p) -> p.m_branchLength)
				))
			
			.apply(instance, CanopyTrunkPlacer::new));
	

	
	private final boolean m_shouldMakeGrownOnBlockDirt;
	// a random value is picked from this, and then for each block of trunk, a random float number is picked between zero and this amount...
	// that amount is then used to shift the trunk towards a specific direction, every time the total shift increases by one the trunk moves that much in a once picked random direction...
	// the trunk then continue from the new position until it's all done building.
	private final float m_maxCurve;
	//private final float m_branchMaxVerticalCurve;
	//private final float m_branchMaxHorizontalCurve;
	
	
	// a height block count that branch can occur through, after initial trunk
	private final IntProvider m_branchingTrunkLength;
	// 0 to 1, consider for all horizontal faces
	private final float m_branchLikelyhood;
	private final IntProvider m_branchLength;
	// forking branchs form main branch.
	
	
	
	
	public CanopyTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, 
			boolean shouldMakeGrownOnBlockDirt, 
			float maxCurve, 
			//float branchMaxVerticalCurve, 
			//float branchMaxHorizontalCurve,
			IntProvider branchingTrunkLength,
			float branchLikelyhood,
			IntProvider branchLength)
	{
		super(baseHeight, heightRandA, heightRandB);
		this.m_shouldMakeGrownOnBlockDirt = shouldMakeGrownOnBlockDirt;
		this.m_maxCurve = maxCurve;
		//this.m_branchMaxVerticalCurve = branchMaxVerticalCurve;
		//this.m_branchMaxHorizontalCurve = branchMaxHorizontalCurve;
		this.m_branchingTrunkLength = branchingTrunkLength;
		this.m_branchLikelyhood = branchLikelyhood;
		this.m_branchLength = branchLength;
	} // end constructor
	
	

	@Override
	protected TrunkPlacerType<CanopyTrunkPlacer> type()
	{
		return YATMTrunkPlacerTypes.CANOPY_TRUNK_PLACER.get();
	} // end type()

	@Override
	public List<FoliageAttachment> placeTrunk(LevelSimulatedReader levelSimReader, BiConsumer<BlockPos, BlockState> consumer, RandomSource random, int height, BlockPos growingPosition, TreeConfiguration treeConfig)
	{
		// replace block below with the provided dirt only if a boolean is set, default to false
		// generate straight to slight curved branch according to the height
		// then start branching out horizontally as defined
		if(this.m_shouldMakeGrownOnBlockDirt) 
		{
			setDirtAt(levelSimReader, consumer, random, growingPosition.below(), treeConfig);
		}
		
		List<FoliagePlacer.FoliageAttachment> attachmentPoints = Lists.newArrayList();
	    
		// grow base trunk
		Direction centeralTrunkCurvesIn = Direction.Plane.HORIZONTAL.getRandomDirection(random);
		float curve = 0f;
		int lastCurvedStep = 0;
		BlockPos lastGrownOn = growingPosition.below();
		for(int i = 0; i < height; i++) 
		{
			BlockPos toGrowOn = lastGrownOn.above();
			curve += random.nextFloat() * this.m_maxCurve;
			if(curve - (float)lastCurvedStep > 1) 
			{
				toGrowOn = toGrowOn.relative(centeralTrunkCurvesIn);
				lastCurvedStep = (int)curve;
			}
			
			this.placeLog(levelSimReader, consumer, random, toGrowOn, treeConfig);
			lastGrownOn = toGrowOn;
		}
		attachmentPoints.add(new FoliagePlacer.FoliageAttachment(lastGrownOn.above(), 0, false));
		// grow canopy branches
		int branchingTrunkLength = this.m_branchingTrunkLength.sample(random);
		for(int i = 0; i < branchingTrunkLength; i++) 
		{
			BlockPos toGrowOn = lastGrownOn.above();
			curve += random.nextFloat() * this.m_maxCurve;
			if(curve - (float)lastCurvedStep > 1) 
			{
				toGrowOn = toGrowOn.relative(centeralTrunkCurvesIn);
				lastCurvedStep = (int)curve;
			}
			
			this.placeLog(levelSimReader, consumer, random, toGrowOn, treeConfig);
			lastGrownOn = toGrowOn;
			
			for(Direction dir : Direction.Plane.HORIZONTAL) 
			{
				if(random.nextFloat() <= this.m_branchLikelyhood) 
				{
					this.branch(levelSimReader, consumer, random, this.m_branchLength.sample(random), lastGrownOn.relative(dir), treeConfig, dir, attachmentPoints, true);
				}
			}
		}
		
		return attachmentPoints;
	} // end placeTrunk()

	private List<FoliageAttachment> branch(LevelSimulatedReader levelSimReader, BiConsumer<BlockPos, BlockState> consumer, RandomSource random, int length, BlockPos growingPosition, TreeConfiguration treeConfig, Direction growingIn, List<FoliagePlacer.FoliageAttachment> attachmentPoints, boolean subBranch)
	{
		// virtically can wiggle either direction
		float verticalCurve = 0f;
		float lastVerticalCurveStep = 0;
		Direction horizontalCurvesIn = random.nextFloat() > 0.5f ? growingIn.getClockWise(Direction.Axis.Y) : growingIn.getCounterClockWise(Axis.Y);
		float horizontalCurve = 0f;
		float lastHorizontalCurveStep = 0;
		BlockPos lastGrownOn = growingPosition.relative(growingIn.getOpposite());
		for(int i = 0; i < length; i++) 
		{
			BlockPos toGrowOn = lastGrownOn.relative(growingIn);
			horizontalCurve += random.nextFloat() * this.m_maxCurve;
			if(horizontalCurve - lastHorizontalCurveStep > 1f) 
			{
				toGrowOn = toGrowOn.relative(horizontalCurvesIn);
				lastHorizontalCurveStep = horizontalCurve;
			}
			verticalCurve += random.nextFloat() * this.m_maxCurve 
					* (Direction.Plane.VERTICAL.getRandomDirection(random) == Direction.UP ? 1f : -1f);
			if(differenceExceeds(verticalCurve, lastVerticalCurveStep, 1f)) 
			{
				toGrowOn = toGrowOn.relative(differenceMagnitude(verticalCurve, lastVerticalCurveStep) > 0 ? Direction.UP : Direction.DOWN);
				lastVerticalCurveStep = verticalCurve;
			}
			
			
			this.placeLog(levelSimReader, consumer, random, toGrowOn, treeConfig);
			lastGrownOn = toGrowOn;
			
			// if it's allowed: chance branch out
			if(subBranch) 
			{
				Direction dir = growingIn.getClockWise(Direction.Axis.Y);
				if(random.nextFloat() <= (this.m_branchLikelyhood / 4f)) 
				{
					attachmentPoints.add(new FoliagePlacer.FoliageAttachment(lastGrownOn.above(), 0, false));
					this.branch(levelSimReader, consumer, random, (int)((float)length / 2f), lastGrownOn.relative(dir), treeConfig, dir, attachmentPoints, false);
				}
				dir = growingIn.getCounterClockWise(Direction.Axis.Y);
				if(random.nextFloat() <= (this.m_branchLikelyhood / 4f)) 
				{
					attachmentPoints.add(new FoliagePlacer.FoliageAttachment(lastGrownOn.above(), 0, false));
					this.branch(levelSimReader, consumer, random, (int)((float)length / 2f), lastGrownOn.relative(dir), treeConfig, dir, attachmentPoints, false);
				}
			}
		}
		attachmentPoints.add(new FoliagePlacer.FoliageAttachment(lastGrownOn.relative(growingIn), 0, false));
		return attachmentPoints;
	} // end branch()
	
	private static boolean differenceExceeds(float a, float b, float dif) 
	{
		return Math.abs(a) - Math.abs(b) > 1f;
	} // end differenceExceeds
	
	private static int differenceMagnitude(float a, float b) 
	{
		return a == b ? 0 : a > b ? 1 : -1;
	} // end differenceMagnitude()
	
} // end class

//protected static <P extends RubberTrunkPlacer> Products.P10<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, Boolean, Float, Float, Float, IntProvider, Float, IntProvider> addThisFields(RecordCodecBuilder.Instance<P> instance)
//{
//	return TrunkPlacer.trunkPlacerParts(instance).and(
//			Codec.BOOL.fieldOf("shouldMakeGrownOnBlockDirt").orElse(false).forGetter((p) -> p.m_shouldMakeGrownOnBlockDirt)
//)
//			.and(
//			Codec.floatRange(0.0F, 8.0F).fieldOf("trunkMaxCurve").forGetter((p) -> p.m_trunkMaxCurve)
//			).and(
//			Codec.floatRange(0.0F, 8.0F).fieldOf("branchMaxVerticalCurve").forGetter((p) -> p.m_branchMaxVerticalCurve)
//			).and(
//			Codec.floatRange(0.0F, 8.0F).fieldOf("branchMaxHorizontalCurve").forGetter((p) -> p.m_branchMaxHorizontalCurve)
//			).and(
//			IntProvider.NON_NEGATIVE_CODEC.fieldOf("branchingTrunkLength").forGetter((p) -> p.m_branchingTrunkLength)
//			).and(
//			Codec.floatRange(0.0F, 1.0F).fieldOf("branchLikelyhood").forGetter((p) -> p.m_branchLikelyhood)
//			).and(
//			IntProvider.NON_NEGATIVE_CODEC.fieldOf("branchLength").forGetter((p) -> p.m_branchLength)
//			) // end and
//			;
//} // end addThisFields()
//
//