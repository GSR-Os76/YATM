package com.gsr.gsr_yatm.block.device.crucible;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.boiler.BoilerBlock;
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
			/* TODO, maybe still do the particles, or maybe do them when it's actually burning something, if so do that with boiler maybe etc too
	         Direction direction = state.getValue(CrucibleBlock.FACING);
	         Direction.Axis direction$axis = direction.getAxis();
	         double facingCentDeviation = 0.53d;
	         double facingPerpOffset = (random.nextDouble() * 0.4d) - 0.2d;
	         double xOfs = direction$axis == Direction.Axis.X ? ((double)direction.getStepX() * facingCentDeviation) : facingPerpOffset;
	         double mainYOfs = ((random.nextDouble() * 6.0d) + 2.5d) / 16.0d;
	         double zOfs = direction$axis == Direction.Axis.Z ? ((double)direction.getStepZ() * facingCentDeviation) : facingPerpOffset;
	         level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + mainYOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + mainYOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         
	         // todo, the nonmain faces
	         level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         */
		}
		super.animateTick(state, level, position, random);
	} // end animateTick()



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