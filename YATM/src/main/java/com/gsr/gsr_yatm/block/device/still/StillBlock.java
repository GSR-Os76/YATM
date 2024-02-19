package com.gsr.gsr_yatm.block.device.still;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.HorizontalDeviceBlock;
import com.gsr.gsr_yatm.block.device.IDeviceBlockEntity;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class StillBlock extends HorizontalDeviceBlock
{
	public static final DirectionProperty FACING = YATMBlockStateProperties.FACING_HORIZONTAL;
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;
	
	
	
	public StillBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.STILL::get);
		this.registerDefaultState(this.defaultBlockState().setValue(StillBlock.LIT, false));
	} // end constructor


	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(StillBlock.LIT));
	} // end createBlockStateDefinition()
	
	

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if (state.getValue(StillBlock.LIT))
		{
			double centX = (double) position.getX() + 0.5d;
			double y = (double) position.getY();
			double centZ = (double) position.getZ() + 0.5d;
			if (random.nextDouble() < 0.1d)
			{
				level.playLocalSound(centX, y, centZ, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
			}
			Direction facing = state.getValue(StillBlock.FACING);
			Direction.Axis fAxis = facing.getAxis();
			double facingCentDeviation = 0.53d;
			double perpOffset = (random.nextDouble() * 0.4d) - 0.2d;
			
			double mainYOfs = ((random.nextDouble() * 1.0d) + 1.5d) / 16.0d;
			
			boolean isXFacing = fAxis == Direction.Axis.X;
			double xStep = (double) facing.getStepX();
			double zStep = (double) facing.getStepZ();
			double xOfs = isXFacing ? (xStep * facingCentDeviation) : perpOffset;
			double zOfs = !isXFacing ? (zStep * facingCentDeviation) : perpOffset;
		
			level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + mainYOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
			level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + mainYOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);

		}
		super.animateTick(state, level, position, random);
	} // end animateTick()

	@Override
	public int getLightEmission(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos position)
	{
		if (state.getValue(StillBlock.LIT)) 
		{
			return 15;
		}
		return super.getLightEmission(state, level, position);
	} // end getLightEmission()
 
	
	
	@Override
	public @NotNull IDeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new StillBlockEntity(position, state);
	} // end newDeviceBlockEntity()
	



	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		StillBlockEntity ebe = (StillBlockEntity)level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInv, player) -> new StillMenu(
				containerId,
				playerInv, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				ebe.getInventory(), 
				ebe.getDataAccessor()),
		YATMLanguageProvider.translatableForMenu(YATMMenuTypes.STILL.get()));
	} // end getMenuProvider()
} // end class