package com.gsr.gsr_yatm.block.device.sap_collector;

import java.util.HashMap;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.UseUtilities;
import com.gsr.gsr_yatm.utilities.shape.BlockShapesProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractSapCollectorBlock extends AbstractCauldronBlock
{
	public static final int FLUID_VOLUME = 333;
	private final @NotNull BlockShapesProvider m_shapes;
	
	
	
	public AbstractSapCollectorBlock(@NotNull Properties properties, @NotNull BlockShapesProvider shapes)
	{
		super(Objects.requireNonNull(properties), new HashMap<>());
		this.m_shapes = Objects.requireNonNull(shapes);
	} // end constructor

	
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		return UseUtilities.tryFillOrDrainFromHeld(state, level, position, player, hand, hitResult);
	} // end use()

	
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext context)
	{
		return this.m_shapes.getShape(state, blockGetter, position, context);
	} // end getShape()

	@Override
	public VoxelShape getInteractionShape(BlockState state, BlockGetter blockGetter, BlockPos position)
	{
		return this.m_shapes.getInteractionShape(state, blockGetter, position);
	} // end getInteractionShape()
	
} // end class