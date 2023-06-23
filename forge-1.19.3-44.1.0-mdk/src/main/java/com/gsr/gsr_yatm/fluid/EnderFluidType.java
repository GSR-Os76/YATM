package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class EnderFluidType extends FluidType
{

	public EnderFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation ENDER_STILL = new ResourceLocation("gsr_yatm:block/ender_still");
			private static final ResourceLocation ENDER_FLOW = new ResourceLocation("gsr_yatm:block/ender_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return ENDER_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return ENDER_FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class