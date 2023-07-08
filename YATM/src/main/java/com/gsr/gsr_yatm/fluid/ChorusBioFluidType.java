package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class ChorusBioFluidType extends FluidType
{

	public ChorusBioFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation CHORUS_BIO_STILL = new ResourceLocation("gsr_yatm:block/chorus_bio_still");
			private static final ResourceLocation CHORUS_BIO_FLOW = new ResourceLocation("gsr_yatm:block/chorus_bio_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return CHORUS_BIO_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return CHORUS_BIO_FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class