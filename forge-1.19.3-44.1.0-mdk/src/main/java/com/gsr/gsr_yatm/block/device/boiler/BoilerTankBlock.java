package com.gsr.gsr_yatm.block.device.boiler;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class BoilerTankBlock extends Block implements EntityBlock// implements IForgeBlockState
{
	public static final BooleanProperty HAS_BOILER = BooleanProperty.create("has_boiler");

	private static final VoxelShape SHAPE = Block.box(1d, 0d, 1d, 15d, 16d, 15d);

	
	
	public BoilerTankBlock(Properties properties, int maxTransferRate, int volume)
	{
		super(properties);
		this.defaultBlockState().setValue(HAS_BOILER, Boolean.FALSE);
		// FenceBlock l;
	} // end constructor

	

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
	{
		ItemStack held = player.getItemInHand(interactionHand);
		LazyOptional<IFluidHandlerItem> cap = held.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
		if((!level.isClientSide) && cap.isPresent() && level.getBlockEntity(blockPos) instanceof BoilerTankBlockEntity btbe)
		{
			LazyOptional<IFluidHandler> btcap = btbe.getCapability(ForgeCapabilities.FLUID_HANDLER);
			IFluidHandler f = btcap.orElse(null);
			IFluidHandlerItem fi = cap.orElse(null);
			
//			YetAnotherTechMod.LOGGER.info("f is holding: " + f.getFluidInTank(0).getFluid() + ", " + f.getFluidInTank(0).getAmount());
//			YetAnotherTechMod.LOGGER.info("fi is holding: " + fi.getFluidInTank(0).getFluid() + ", " + fi.getFluidInTank(0).getAmount());
		
			f.fill(fi.drain(f.fill(fi.drain(1000, FluidAction.SIMULATE), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
			
//			YetAnotherTechMod.LOGGER.info("f is holding: " + f.getFluidInTank(0).getFluid() + ", " + f.getFluidInTank(0).getAmount());
//			YetAnotherTechMod.LOGGER.info("fi is holding: " + fi.getFluidInTank(0).getFluid() + ", " + fi.getFluidInTank(0).getAmount());
			player.setItemInHand(interactionHand, fi.getContainer());
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	} // end use



	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(HAS_BOILER);
	} // end createBlockStateDefinition()

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
	{
		return this.defaultBlockState().setValue(HAS_BOILER, blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().below()).getBlock() instanceof BoilerBlock);
	} // getStateForPlacement()

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(BlockState blockState, Level level, BlockPos thissPos, Block p_60512_, BlockPos changePos, boolean p_60514_)
	{
		if (thissPos.below().equals(changePos))
		{
			if (level.getBlockState(thissPos.below()).getBlock() instanceof BoilerBlock boiler)
			{
				if (!blockState.getValue(HAS_BOILER))
				{
					level.setBlockAndUpdate(thissPos, blockState.setValue(HAS_BOILER, true));
				}
			}
			else if (blockState.getValue(HAS_BOILER))
			{			
				level.setBlockAndUpdate(thissPos, blockState.setValue(HAS_BOILER, false));
			}
		}
		super.neighborChanged(blockState, level, thissPos, p_60512_, changePos, p_60514_);
	} // end neighbotChanged()



	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_)
	{
		return SHAPE;
	} // end getShape();



	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new BoilerTankBlockEntity(blockPos, blockState);
	} // end newBlockEntity()



	
		@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		// TODO Auto-generated method stub
		return blockEntityType == YATMBlockEntityTypes.BOILER_TANK.get() ? (l, bp, bs, be) -> BoilerTankBlockEntity.tick(l, bp, bs, (BoilerTankBlockEntity)be): null;
	} // end getTicker()
	
//	
//	Blocks
//	ObsidianBlock l;
		
} // end class