package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class ChorusFluidType extends FluidType
{

	public ChorusFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation CHORUS_STILL = new ResourceLocation("gsr_yatm:block/chorus_still");
			private static final ResourceLocation CHORUS_FLOW = new ResourceLocation("gsr_yatm:block/chorus_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return CHORUS_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return CHORUS_FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class