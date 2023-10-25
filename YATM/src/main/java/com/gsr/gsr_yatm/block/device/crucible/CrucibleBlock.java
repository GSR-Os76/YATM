package com.gsr.gsr_yatm.block.device.crucible;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.DeviceBlock;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.data_generation.YATMLanguageProvider;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMMenuTypes;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
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
	private final @NotNull DeviceTierConstants m_constants;
	
	
	
	public CrucibleBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape, @NotNull DeviceTierConstants constants)
	{
		super(Objects.requireNonNull(properties), YATMBlockEntityTypes.CRUCIBLE::get, Objects.requireNonNull(shape));
		
		this.registerDefaultState(this.defaultBlockState().setValue(CrucibleBlock.LIT, false));
		
		this.m_constants = Objects.requireNonNull(constants);
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
			double x = (double) position.getX() + 0.5d;
			double y = (double) position.getY() + (5.0d / 16.0d);
			double z = (double) position.getZ() + 0.5d;
			if (random.nextDouble() < 0.1d)
			{
				level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
			}
			if (random.nextDouble() < 0.1d)
			{
				level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0d, 0.0d, 0.0d);
			}
			level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0d, 0.0d, 0.0d);
		}
		super.animateTick(state, level, position, random);
	} // end animateTick()
	
	
	





	@Override
	public DeviceBlockEntity newDeviceBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new CrucibleBlockEntity(blockPos, blockState, this.m_constants);
	} // end newBlockEntity()
	
	@Override
	public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos)
	{
		CrucibleBlockEntity blockEntity = (CrucibleBlockEntity) level.getBlockEntity(blockPos);
		return new SimpleMenuProvider((containerId, playerInventory, player) -> new CrucibleMenu(
				containerId, 
				playerInventory, 
				ContainerLevelAccess.create(level, blockPos), 
				blockState.getBlock(), 
				blockEntity.getInventory(), 
				blockEntity.getDataAccessor()), 
		YATMLanguageProvider.getTranslatableTitleNameFor(YATMMenuTypes.CRUCIBLE.get()));
	} // end getMenuProvider()
	
} // end class