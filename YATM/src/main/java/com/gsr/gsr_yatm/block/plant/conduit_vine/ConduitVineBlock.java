package com.gsr.gsr_yatm.block.plant.conduit_vine;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.FaceBlock;
import com.gsr.gsr_yatm.block.IAgingBlock;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.utilities.BlockUtil;
import com.gsr.gsr_yatm.utilities.DirectionUtil;
import com.gsr.gsr_yatm.utilities.OptionalAxis;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

// axis piece only appears on grow across, no on place, for right now at least
public class ConduitVineBlock extends FaceBlock implements BonemealableBlock, IAgingBlock
{
	public static final EnumProperty<OptionalAxis> AXIS = YATMBlockStateProperties.AXIS_OPTIONAL;
	public static final IntegerProperty GROWTH_AGE = YATMBlockStateProperties.AGE_EIGHT;
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	
	
	public ConduitVineBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		this.m_shape = Objects.requireNonNull(shape);
		this.registerDefaultState(this.defaultBlockState().setValue(ConduitVineBlock.AXIS, OptionalAxis.NONE).setValue(ConduitVineBlock.GROWTH_AGE, 0));
	} // end constructor

	
	
	@Override
	public @NotNull IntegerProperty getAgeProperty()
	{
		return ConduitVineBlock.GROWTH_AGE;
	} // end getAgeProperty()
	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(ConduitVineBlock.AXIS, ConduitVineBlock.GROWTH_AGE));
	} // end createBlockStateDefinition


	
	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state)
	{
		return YATMConfigs.IS_HORIZONTAL_GROWRTH_UNBOUND.get() || this.getAge(state) < this.getMaxAge();
	} // end isRandomlyTicking()

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		// TODO, could make it such that vines in the dark can grow if they're connected to one's in the light, representing the vines that are lit feeding the ones that aren't
		if (BlockUtil.isLightLevelBelow(level, position, YATMConfigs.CONDUIT_VINE_MINIMUM_LIGHT_LEVEL.get())) 
		{
			return;
		}
		List<Tuple2<Direction, BlockPos>> places = this.getSpreadCanidates(level, position, state);
		if(places.size() == 0) 
		{
			return;
		}
		
		Tuple2<Direction, BlockPos> place = places.get(random.nextIntBetweenInclusive(0, places.size() - 1));
		if (ForgeHooks.onCropsGrowPre(level, position, state, random.nextInt(YATMConfigs.CONDUIT_VINE_GROWTH_RARITY.get()) == 0)) 
		{
			this.addFace(level, place.b(), place.a(), this.getAge(state));
			ForgeHooks.onCropsGrowPost(level, position, state);
		}
	} // end randomTick()

	private void addFace(@NotNull ServerLevel level, @NotNull BlockPos position, @NotNull Direction face, @NotNegative int parentAge)
	{
		BlockState state = level.getBlockState(position);
		if(state.is(this)) 
		{
			BlockState s = state.setValue(ConduitVineBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(face), true);
			List<Direction> fs = FaceBlock.containedFaces(s);
			if(fs.stream().allMatch((d) -> d.getAxis() == face.getAxis()))
			{
				s = s.setValue(ConduitVineBlock.AXIS, OptionalAxis.of(face.getAxis()));
			}
			level.setBlock(position, s, Block.UPDATE_CLIENTS);
		}
		else 
		{
			level.setBlock(position, this.getStateForAge(this.defaultBlockState().setValue(ConduitVineBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(face), true), YATMConfigs.IS_HORIZONTAL_GROWRTH_UNBOUND.get() ? 0 : Contract.inInclusiveRange(0, this.getMaxAge() - 1, parentAge, "invalid parent age: " + parentAge) + 1), Block.UPDATE_ALL);
		}
		
	} // end addFace()

	
	
	protected List<Tuple2<Direction, BlockPos>> getSpreadCanidates(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return FaceBlock.containedFaces(state)
				.stream().map((d) -> this.getSpreadCanidates(level, position, state, d))
				.flatMap((l) -> l.stream())
				.toList();
	} // end getSpreadCanidates()
	
	// maybe add in condition for further hanging
	protected List<Tuple2<Direction, BlockPos>> getSpreadCanidates(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state, @NotNull Direction face)
	{
		List<Direction> p = DirectionUtil.perpendicularPlane(face);
		BlockPos r = position.relative(face);
		Stream<Tuple2<Direction, BlockPos>> canidates = p.stream()
		.map((d) -> Tuple.of(d, r.relative(d)))// map to the positions perpendicular to the block behind the face relative to the face
		.map((t) -> level.getBlockState(t.b()).is(YATMBlockTags.CONDUIT_VINES_CAN_GROW_IN_KEY) // map to the directly adjacent parallel faces, or perpendicular face on the block that the face is on; store as face, and block the face is placed on
				? Tuple.of(t.a().getOpposite(), r)
				: Tuple.of(face, t.b()))
		.filter((t) -> this.canPlaceOn(level, t.b(), level.getBlockState(t.b()), t.a().getOpposite())) // drop unplacable mappings
		.map((t) -> Tuple.of(t.a(), t.b().relative(t.a().getOpposite())));// map to faces, and the block they're contained inside of
		
		Stream<Tuple2<Direction, BlockPos>> canidates2 = Stream.of(Direction.values())
		.filter((d) -> this.canPlaceOn(level, position.relative(d), level.getBlockState(position.relative(d)), d.getOpposite())) // get all faces inside the current block that can be filled up potentially, disregarding current occupancy
		.map((d) -> Tuple.of(d, position));
		
		return Stream.concat(canidates, canidates2)
				.map((t) -> Tuple.of(t, level.getBlockState(t.b()))) // get occupied blockstate
				.filter((t) -> t.b().is(this) || t.b().is(YATMBlockTags.CONDUIT_VINES_CAN_GROW_IN_KEY)) // filter to only replaceable blocks
				.map((t) -> Tuple.of(t.a(), !t.b().is(this) ? List.of() : FaceBlock.containedFaces(t.b()))) // get occupied faces if the placing into block's this, otherwise all faces are unoccupied
				.filter((t) -> !t.b().contains(t.a().a())) // filter to all faces not currently occupied
				.map((t) -> t.a()) // drop unused data
				.toList();
	} // end getSpreadCanidates()
	
	

	@Override
	protected boolean canPlaceOn(@NotNull BlockGetter getter, @NotNull BlockPos onPosition, @NotNull BlockState onState, @NotNull Direction cansFace)
	{
		BlockPos placedInPosition = onPosition.relative(cansFace);
		BlockState placedIn = getter.getBlockState(placedInPosition);
		BlockPos abovePlacedInPosition = placedInPosition.above();
		BlockState abovePlaceIn = getter.getBlockState(abovePlacedInPosition);
		return super.canPlaceOn(getter, onPosition, onState, cansFace) 
				|| (
						// if the face's on the horizontal plan it can be supported by another block above it with that face or up, or by the block that it is having up
						Direction.Plane.HORIZONTAL.test(cansFace) 
						&& (
								(
									abovePlaceIn.is(this) 
									&& this.hasFace(abovePlaceIn, cansFace.getOpposite())
								)
								|| 
								(
									placedIn.is(this) 
									&& placedIn.getValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(Direction.UP)) 
									&& this.canPlaceOn(getter, abovePlacedInPosition, abovePlaceIn, Direction.DOWN)
								)
							)
					);
	} // end canPlaceOn()

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos position, Block oldNeighbor, BlockPos neighborPosition, boolean p_60514_)
	{
		this.updateState(level, state, position);
	} // end neighborChanged()
	
	protected void updateState(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos) 
	{
		Boolean changed = false;
		BlockState updateTo = state;
		BlockState drop = this.defaultBlockState();
		
		for(Entry<Direction, BooleanProperty> e : FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.entrySet()) 
		{
			BooleanProperty targetProp = e.getValue();
			if(state.getValue(targetProp)) 
			{
				Direction targetFace = e.getKey();
				BlockPos onPos = pos.relative(targetFace);
				BlockState onState = level.getBlockState(onPos);
				Direction onFace = targetFace.getOpposite();
				if(!this.canPlaceOn(level, onPos, onState, onFace)) 
				{
					changed = true;
					updateTo = updateTo.setValue(targetProp, false);
					drop = drop.setValue(targetProp, true);
				}
			}
		}
		updateTo = this.withValidatedAxis(updateTo);
		
		if(changed) 
		{
			BlockState f = updateTo;
			boolean alive = Direction.stream().anyMatch((d) -> f.getValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d)));
			level.setBlock(
					pos, 
					alive ? updateTo : Blocks.AIR.defaultBlockState(),
					alive ? Block.UPDATE_CLIENTS : Block.UPDATE_ALL);
			Block.dropResources(drop, level, pos);
		}
	} // end updateState()
	
	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context)
	{		
		return this.m_shape.getShape(blockState, blockGetter, blockPos, context);
	} // end getShape()

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos position)
	{
		return true;
	} // end propagatesSkylightDown()

	@Override
	public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity)
	{
		// axis conditions are already implied
		return super.isLadder(state, level, pos, entity) 
				&& Direction.stream().anyMatch((d) -> (d != Direction.DOWN) && state.getValue(FaceBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d)));
	} // end isLadder()
	
	protected @NotNull BlockState withValidatedAxis(@NotNull BlockState state) 
	{
		OptionalAxis axis = state.getValue(ConduitVineBlock.AXIS);
		if(!axis.getDirections().stream().allMatch((d) -> state.getValue(ConduitVineBlock.HAS_FACE_PROPERTIES_BY_DIRECTION.get(d)))) 
		{
			return state.setValue(ConduitVineBlock.AXIS, OptionalAxis.NONE);
		}
		return state;
	} // end withValidatedAxis()






	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return (this.getSpreadCanidates(level, position, state).size() > 0) || (state.getValue(this.getAgeProperty()) == this.getMaxAge());
	} // end isValidBonemealTarget()
	
	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		return random.nextInt(YATMConfigs.CONDUIT_VINE_BONEMEAL_SUCCESS_RARITY.get()) == 0;
	} // end isBonemealSuccess()
	
	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(state.getValue(this.getAgeProperty()) == this.getMaxAge()) 
		{
			level.setBlock(position, this.getStateForAge(state, 0), Block.UPDATE_CLIENTS);
			return;
		}
		
		List<Tuple2<Direction, BlockPos>> places = this.getSpreadCanidates(level, position, state);
		if(places.size() == 0) 
		{
			return;
		}
		
		Tuple2<Direction, BlockPos> place = places.get(random.nextIntBetweenInclusive(0, places.size() - 1));
		this.addFace(level, place.b(), place.a(), this.getAge(state));
	} // end performBonemeal()
	
} // end block