package com.gsr.gsr_yatm.block.device.boiler;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class BoilerBlock extends DeviceBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	public static final BooleanProperty HAS_TANK = YATMBlockStateProperties.HAS_TANK;
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;

	private final @NotNull DeviceTierConstants m_constants;
	

	
	public BoilerBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull DeviceTierConstants constants)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.BOILER::get);
		
		this.registerDefaultState(this.defaultBlockState().setValue(BoilerBlock.HAS_TANK, false).setValue(BoilerBlock.LIT, false));
		
		this.m_constants = Objects.requireNonNull(constants);
		
	} // end constructor



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(BoilerBlock.HAS_TANK, BoilerBlock.LIT));
	} // createBlockStateDefinition()

	@Override
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
	{
		Block above = context.getLevel().getBlockState(context.getClickedPos().above()).getBlock();
		return super.getStateForPlacement(context).setValue(BoilerBlock.HAS_TANK, above instanceof BoilerTankBlock);
	} // end getStateForPlacement()

	
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Block formerNeighbor, BlockPos neighborPosition, boolean p_60514_)
	{
		boolean sHT = level.getBlockState(position.above()).getBlock() instanceof BoilerTankBlock;
		if (sHT != state.getValue(BoilerBlock.HAS_TANK))
		{
			level.setBlock(position, state.setValue(BoilerBlock.HAS_TANK, sHT), Block.UPDATE_CLIENTS);
		}
		
		super.neighborChanged(state, level, position, formerNeighbor, neighborPosition, p_60514_);
	} // end onNeighborChange()

	
	
	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		 if (state.getValue(BoilerBlock.LIT)) 
		 {
	         double centX = (double)position.getX() + 0.5d;
	         double y = (double)position.getY();
	         double centZ = (double)position.getZ() + 0.5d;
	         if (random.nextDouble() < 0.1d) 
	         {
	            level.playLocalSound(centX, y, centZ, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
	         }

	         Direction direction = state.getValue(BoilerBlock.FACING);
	         Direction.Axis direction$axis = direction.getAxis();
	         double facingCentDeviation = 0.42d;
	         double facingPerpOffset = (random.nextDouble() * 0.4d) - 0.2d;
	         double xOfs = direction$axis == Direction.Axis.X ? ((double)direction.getStepX() * facingCentDeviation) : facingPerpOffset;
	         double yOfs = ((random.nextDouble() * 4.0d) + 1.0d) / 16.0d;
	         double zOfs = direction$axis == Direction.Axis.Z ? ((double)direction.getStepZ() * facingCentDeviation) : facingPerpOffset;
	         level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	      }
	} // end animateTick()
	


	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		// TODO, simplify this to just passing the constants, letting be decide
		return new BoilerBlockEntity(position, state, this.m_constants.maxTemperature(), this.m_constants.tankCapacity(), this.m_constants.maxFluidTransferRate());
	} // end newBlockEntity()

	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{		
		BoilerBlockEntity blockEntity = (BoilerBlockEntity)level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new BoilerMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(),
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableFor(YATMMenuTypes.BOILER.get()));
	} // end getMenuProvider()

} // end class