package com.gsr.gsr_yatm.block.device.crucible;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
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

public class CrucibleBlock extends DeviceBlock
{
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;
	
	
	
	public CrucibleBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.CRUCIBLE::get);
		
		this.registerDefaultState(this.defaultBlockState().setValue(CrucibleBlock.LIT, false));
	} // end constructor


	
	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(CrucibleBlock.LIT));
	} // end createBlockStateDefinition()



	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull RandomSource random)
	{
		if (state.getValue(CrucibleBlock.LIT))
		{
			double centX = (double) position.getX() + 0.5d;
			double y = (double) position.getY();
			double centZ = (double) position.getZ() + 0.5d;
			if (random.nextDouble() < 0.1d)
			{
				level.playLocalSound(centX, y, centZ, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
			}
			Direction facing = state.getValue(CrucibleBlock.FACING);
			Direction.Axis fAxis = facing.getAxis();
			double facingCentDeviation = 0.53d;
			double mainPerpOffset = (random.nextDouble() * 0.4d) - 0.2d;
			double minorPerpOffset1 = (random.nextDouble() * 0.55d) - 0.275d;
			double minorPerpOffset2 = (random.nextDouble() * 0.55d) - 0.275d;
			double minorPerpOffset3 = (random.nextDouble() * 0.55d) - 0.275d;
			
			double mainYOfs = ((random.nextDouble() * 6.0d) + 2.5d) / 16.0d;
			double minorYOfs1 = ((random.nextDouble() * 2.0d) + 1.5d) / 16.0d;
			double minorYOfs2 = ((random.nextDouble() * 2.0d) + 1.5d) / 16.0d;
			double minorYOfs3 = ((random.nextDouble() * 2.0d) + 1.5d) / 16.0d;

			boolean isXFacing = fAxis == Direction.Axis.X;
			double xStep = (double) facing.getStepX();
			double zStep = (double) facing.getStepZ();
			double mainXOfs = isXFacing ? (xStep * facingCentDeviation) : mainPerpOffset;
			double mainZOfs = !isXFacing ? (zStep * facingCentDeviation) : mainPerpOffset;
			double minorXOfs1 = isXFacing ? (xStep * -facingCentDeviation) : minorPerpOffset1;
			double minorZOfs1 = !isXFacing ? (zStep * -facingCentDeviation) : minorPerpOffset1;
			
			double minorXOfs2 = isXFacing ? (xStep * minorPerpOffset2) : -facingCentDeviation;
			double minorZOfs2 = !isXFacing ? (zStep * minorPerpOffset2) : -facingCentDeviation;
			double minorXOfs3 = isXFacing ? (xStep * minorPerpOffset3) : facingCentDeviation;
			double minorZOfs3 = !isXFacing ? (zStep * minorPerpOffset3) : facingCentDeviation;
			
			level.addParticle(ParticleTypes.SMOKE, centX + mainXOfs, y + mainYOfs, centZ + mainZOfs, 0.0d, 0.0d, 0.0d);
			level.addParticle(ParticleTypes.FLAME, centX + mainXOfs, y + mainYOfs, centZ + mainZOfs, 0.0d, 0.0d, 0.0d);

			// back
			level.addParticle(ParticleTypes.SMOKE, centX + minorXOfs1, y + minorYOfs1, centZ + minorZOfs1, 0.0d, 0.0d, 0.0d);
			level.addParticle(ParticleTypes.FLAME, centX + minorXOfs1, y + minorYOfs1, centZ + minorZOfs1, 0.0d, 0.0d, 0.0d);
			
			// l and r
			level.addParticle(ParticleTypes.SMOKE, centX + minorXOfs2, y + minorYOfs2, centZ + minorZOfs2, 0.0d, 0.0d, 0.0d);
			level.addParticle(ParticleTypes.FLAME, centX + minorXOfs2, y + minorYOfs2, centZ + minorZOfs2, 0.0d, 0.0d, 0.0d);
			level.addParticle(ParticleTypes.SMOKE, centX + minorXOfs3, y + minorYOfs3, centZ + minorZOfs3, 0.0d, 0.0d, 0.0d);
			level.addParticle(ParticleTypes.FLAME, centX + minorXOfs3, y + minorYOfs3, centZ + minorZOfs3, 0.0d, 0.0d, 0.0d);

		}
		super.animateTick(state, level, position, random);
	} // end animateTick()

	@Override
	public int getLightEmission(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos position)
	{
		if (state.getValue(CrucibleBlock.LIT)) 
		{
			return 15;
		}
		return super.getLightEmission(state, level, position);
	} // end getLightEmission()
	
	

	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new CrucibleBlockEntity(position, state);
	} // end newBlockEntity()
	
	@Override
	public @NotNull MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position)
	{
		CrucibleBlockEntity blockEntity = (CrucibleBlockEntity) level.getBlockEntity(position);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CrucibleMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, position), 
				state.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.translatableFor(YATMMenuTypes.CRUCIBLE.get()));
	} // end getMenuProvider()
	
} // end class