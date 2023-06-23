package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class BioFluidType extends FluidType
{

	public BioFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation BIO_STILL = new ResourceLocation("gsr_yatm:block/bio_still");
			private static final ResourceLocation BIO_FLOW = new ResourceLocation("gsr_yatm:block/bio_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return BIO_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return BIO_FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class