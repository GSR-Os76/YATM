package com.gsr.gsr_yatm.block.device.conduit.channel_vine;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.conduit.ConduitLikeBlock;
import com.gsr.gsr_yatm.block.device.conduit.IConduitBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ChannelVineBlock extends ConduitLikeBlock
{
	public ChannelVineBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.CHANNEL_VINES::get);
	} // end constructor



	@Override
	public @NotNull IConduitBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new ChannelVinesBlockEntity(position, state);
	} // end newDeviceBlockEntity()

} // end class