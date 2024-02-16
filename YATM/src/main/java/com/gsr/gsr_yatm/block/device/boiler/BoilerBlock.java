package com.gsr.gsr_yatm.block.device.boiler;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.block.device.HorizontalDeviceBlock;
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

public class BoilerBlock extends HorizontalDeviceBlock
{
	public static final BooleanProperty LIT = YATMBlockStateProperties.LIT;


	
	public BoilerBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.BOILER::get);
		this.registerDefaultState(this.defaultBlockState().setValue(BoilerBlock.LIT, false));
	
	} // end constructor



	@Override
	protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(BoilerBlock.LIT));
	} // createBlockStateDefinition()


	
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
	         Direction.Axis axis = direction.getAxis();
	         double facingCentDeviation = 0.53d;
	         double facingPerpOffset = (random.nextDouble() * 0.4d) - 0.2d;
	         double xOfs = axis == Direction.Axis.X ? ((double)direction.getStepX() * facingCentDeviation) : facingPerpOffset;
	         double yOfs = ((random.nextDouble() * 4.0d) + 6.5d) / 16.0d;
	         double zOfs = axis == Direction.Axis.Z ? ((double)direction.getStepZ() * facingCentDeviation) : facingPerpOffset;
	         level.addParticle(ParticleTypes.SMOKE, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	         level.addParticle(ParticleTypes.FLAME, centX + xOfs, y + yOfs, centZ + zOfs, 0.0d, 0.0d, 0.0d);
	      }
	} // end animateTick()
	
	@Override
	public int getLightEmission(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos position)
	{
		if (state.getValue(BoilerBlock.LIT)) 
		{
			return 15;
		}
		return super.getLightEmission(state, level, position);
	} // end getLightEmission()



	@Override
	public @NotNull DeviceBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new BoilerBlockEntity(position, state);
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