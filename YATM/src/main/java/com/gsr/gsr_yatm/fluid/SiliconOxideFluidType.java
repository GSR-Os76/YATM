package com.gsr.gsr_yatm.fluid;

import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class SiliconOxideFluidType extends FluidType
{

	public SiliconOxideFluidType(@NotNull Properties properties)
	{
		super(Objects.requireNonNull(properties));
	} // end constructor


	
	@Override
	public void initializeClient(@NotNull Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation STILL = new ResourceLocation("gsr_yatm:block/silicon_oxide_still");
			private static final ResourceLocation FLOW = new ResourceLocation("gsr_yatm:block/silicon_oxide_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class