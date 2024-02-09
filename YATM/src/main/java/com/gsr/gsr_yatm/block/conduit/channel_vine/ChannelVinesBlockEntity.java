package com.gsr.gsr_yatm.block.conduit.channel_vine;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.block.conduit.ConduitLikeBlockEntity;
import com.gsr.gsr_yatm.block.device.AttachmentState;
import com.gsr.gsr_yatm.data_generation.YATMBlockTags;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ChannelVinesBlockEntity extends ConduitLikeBlockEntity<IFluidHandler>
{
	public ChannelVinesBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CHANNEL_VINES.get(), Objects.requireNonNull(position), Objects.requireNonNull(state),
				ForgeCapabilities.FLUID_HANDLER,
				() -> LazyOptional.of(() -> new FluidTank(0)),
				(bs) -> bs.is(YATMBlockTags.CHANNEL_NEUTRAL_ONLY_ATTACHMENT_KEY) ? AttachmentState.NEUTRAL : AttachmentState.PUSH);
	} // end constructor
	
} // end class