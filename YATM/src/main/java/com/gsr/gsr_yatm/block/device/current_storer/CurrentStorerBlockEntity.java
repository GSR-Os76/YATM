package com.gsr.gsr_yatm.block.device.current_storer;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CurrentStorerBlockEntity extends BuiltDeviceBlockEntity
{

	public CurrentStorerBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CURRENT_STORER.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor

	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		// TODO
		DeviceBuilder.of(this::createInventory, definitionReceiver).end();
	} // end define()

} // end class