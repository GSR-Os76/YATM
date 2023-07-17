package com.gsr.gsr_yatm.block.plant;

import java.util.function.Supplier;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class CustomSeedCropBlock extends CropBlock
{
	private final Supplier<ItemLike> m_seed;



	public CustomSeedCropBlock(Properties properties, Supplier<ItemLike> seed)
	{
		super(properties);
		this.m_seed = seed;
	} // end constructor



	@Override
	protected ItemLike getBaseSeedId()
	{
		return this.m_seed.get();
	} // end getBaseSeedId()

} // end class()