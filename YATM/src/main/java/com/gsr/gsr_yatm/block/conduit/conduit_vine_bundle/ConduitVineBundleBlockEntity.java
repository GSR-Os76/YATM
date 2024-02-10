package com.gsr.gsr_yatm.block.conduit.conduit_vine_bundle;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.conduit.ConduitLikeBlockEntity;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

public class ConduitVineBundleBlockEntity extends ConduitLikeBlockEntity<ICurrentHandler>
{
	public ConduitVineBundleBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CONDUIT_VINE_BUNDLE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state), 
				YATMCapabilities.CURRENT, 
				() -> LazyOptional.of(() -> new CurrentHandler(0)), 
				(bs) -> bs.is(YATMBlockTags.CONDUIT_NEUTRAL_ONLY_ATTACHMENT_KEY) ? AttachmentState.NEUTRAL : AttachmentState.PUSH);
	} // end constructor
	
} // end class