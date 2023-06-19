package com.gsr.gsr_yatm.fluid;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;

import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class LatexFluidType extends FluidType
{

	public LatexFluidType(Properties properties)
	{
		super(properties);
	} // end constructor

	

	public boolean isVaporizedOnPlacement(Level level, BlockPos pos, FluidStack stack)
	{
		return level.dimensionType().ultraWarm();
	} // end isVaporizedOnPlacement()

	@Override
	public void onVaporize(@Nullable Player player, Level level, BlockPos pos, FluidStack stack)
	{
		super.onVaporize(player, level, pos, stack);
		if(stack.getAmount() >= 1000) 
		{
			level.setBlockAndUpdate(pos, YATMBlocks.RUBBER_BLOCK.get().defaultBlockState());
		}
	} // end onVaporize()	




	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
	{
		consumer.accept(new IClientFluidTypeExtensions() 
		{
			private static final ResourceLocation LATEX_STILL = new ResourceLocation("gsr_yatm:block/latex_still");
			private static final ResourceLocation LATEX_FLOW = new ResourceLocation("gsr_yatm:block/latex_flow");

            @Override
            public ResourceLocation getStillTexture()
            {
                return LATEX_STILL;
            }

            @Override
            public ResourceLocation getFlowingTexture()
            {
                return LATEX_FLOW;
            }
			@Override
			public @Nullable ResourceLocation getOverlayTexture()
			{
				return new ResourceLocation("minecraft:block/water_overlay");//IClientFluidTypeExtensions.super.getOverlayTexture();
			} // end getOverlayTexture()
			
		}
		);
	} // end initializeClient()




} // end class