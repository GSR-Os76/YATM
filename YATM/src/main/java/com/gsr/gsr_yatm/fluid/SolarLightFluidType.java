package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class SolarLightFluidType extends FluidType
{

	public SolarLightFluidType(Properties properties)
	{
		super(properties);
	} // end constructor

	

	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation SOLAR_LIGHT_STILL = new ResourceLocation("gsr_yatm:block/solar_light_still");
			private static final ResourceLocation SOLAR_LIGHT_FLOW = new ResourceLocation("gsr_yatm:block/solar_light_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return SOLAR_LIGHT_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return SOLAR_LIGHT_FLOW;
            } // end getFlowingTexture()			
		}
		);
	} // end initializeClient()




} // end class