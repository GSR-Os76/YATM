package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class SoulSyrupFluidType extends FluidType
{

	public SoulSyrupFluidType(Properties properties)
	{
		super(properties);
	} // end constructor


	
	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation SOUL_SYRUP_STILL = new ResourceLocation("gsr_yatm:block/soul_syrup_still");
			private static final ResourceLocation SOUL_SYRUP_FLOW = new ResourceLocation("gsr_yatm:block/soul_syrup_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return SOUL_SYRUP_STILL;
            } // end getStillTexture()

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return SOUL_SYRUP_FLOW;
            } // end getFlowingTexture()
		}
		);
	} // end initializeClient()

} // end class