package com.gsr.gsr_yatm.block.conduit.conduit_vine_bundle;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.conduit.ConduitLikeBlock;
import com.gsr.gsr_yatm.block.conduit.IConduitBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ConduitVineBundleBlock extends ConduitLikeBlock
{
	public ConduitVineBundleBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties), Objects.requireNonNull(shape), YATMBlockEntityTypes.CONDUIT_VINE_BUNDLE::get);
	} // end constructor

	
	
	@Override
	public @NotNull IConduitBlockEntity newDeviceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new ConduitVineBundleBlockEntity(position, state);
	} // end newDeviceBlockEntity

} // end class