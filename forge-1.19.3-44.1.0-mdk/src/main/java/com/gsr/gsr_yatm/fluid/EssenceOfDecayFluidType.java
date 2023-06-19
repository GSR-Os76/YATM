package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class EssenceOfDecayFluidType extends FluidType
{

	public EssenceOfDecayFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation ESSENCE_OF_DECAY_STILL = new ResourceLocation("gsr_yatm:block/essence_of_decay_still");
			private static final ResourceLocation ESSENCE_OF_DECAY_FLOW = new ResourceLocation("gsr_yatm:block/essence_of_decay_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return ESSENCE_OF_DECAY_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return ESSENCE_OF_DECAY_FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class