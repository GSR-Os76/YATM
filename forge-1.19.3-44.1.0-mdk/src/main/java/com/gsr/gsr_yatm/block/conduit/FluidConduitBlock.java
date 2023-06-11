package com.gsr.gsr_yatm.block.conduit;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.VoxelShapeGetter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidConduitBlock extends Block implements EntityBlock, IConduitBlock<IFluidHandler>
{
	private final VoxelShapeGetter m_voxelShapeGetter;
	
	

	public FluidConduitBlock(Properties properties, VoxelShapeGetter voxelShapeGetter)
	{
		super(properties);
		this.m_voxelShapeGetter = voxelShapeGetter;
	} // end constructor


	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		IConduit.createBlockStateDefinition(builder);
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return IConduit.getStateForPlacement(super.getStateForPlacement(context), context);
	} // end getStateForPlacement()



	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState blockState, Level level, BlockPos thissPos, Block p_60512_, BlockPos p_60513_, boolean p_60514_)
	{
		BlockEntity be = level.getBlockEntity(thissPos);
		if (be instanceof FluidConduitBlockEntity fdbe)
		{
			fdbe.neighborChanged();
		}
		super.neighborChanged(blockState, level, thissPos, p_60512_, p_60513_, p_60514_);
	} // end neighborChanged()

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState fromMaybe, Level level, BlockPos blockPos, BlockState toMaybe, boolean dunno)
	{
		if (!fromMaybe.getBlock().equals(toMaybe.getBlock()))
		{
			BlockEntity be = level.getBlockEntity(blockPos);
			if (be instanceof FluidConduitBlockEntity fdbe)
			{
				fdbe.blockRemoved();
			}
		}
		super.onRemove(fromMaybe, level, blockPos, toMaybe, dunno);
	} // end onRemove()



	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new FluidConduitBlockEntity(blockPos, blockState);
	} // end newBlockEntity()

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		return blockEntityType == YATMBlockEntityTypes.FLUID_CONDUIT_BLOCK_ENTITY.get() ? (l, bp, bs, be) -> FluidConduitBlockEntity.tick(l, bp, bs, (FluidConduitBlockEntity) be) : null;
	} // end getTicker()



	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
	{
		return this.m_voxelShapeGetter.getShape(blockState, blockGetter, blockPos, collisionContext);
	} // end getShape()


} // end class