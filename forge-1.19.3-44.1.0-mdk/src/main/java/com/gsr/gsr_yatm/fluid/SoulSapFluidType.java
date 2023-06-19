package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class SoulSapFluidType extends FluidType
{

	public SoulSapFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation SOUL_SAP_STILL = new ResourceLocation("gsr_yatm:block/soul_sap_still");
			private static final ResourceLocation SOUL_SAP_FLOW = new ResourceLocation("gsr_yatm:block/soul_sap_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return SOUL_SAP_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return SOUL_SAP_FLOW;
            }
		}
		);
	} // end initializeClient()

} // end class