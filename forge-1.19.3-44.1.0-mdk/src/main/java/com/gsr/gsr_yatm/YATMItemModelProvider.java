package com.gsr.gsr_yatm;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class YATMItemModelProvider extends ItemModelProvider
{

	public YATMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, YetAnotherTechMod.MODID, existingFileHelper);
	} // end YATMItemModelProvider()

	
	
	@Override
	protected void registerModels()
	{
		this.basicItem(YATMItems.BIO_BUCKET.get());
		this.basicItem(YATMItems.CHORUS_BUCKET.get());
		this.basicItem(YATMItems.CHORUS_BIO_BUCKET.get());
		this.basicItem(YATMItems.ENDER_BUCKET.get());		
		this.basicItem(YATMItems.ESSENCE_OF_DECAY_BUCKET.get());
		this.basicItem(YATMItems.ESSENCE_OF_SOULS_BUCKET.get());
		this.basicItem(YATMItems.LATEX_BUCKET.get());
		this.basicItem(YATMItems.SOUL_SAP_BUCKET.get());
		this.basicItem(YATMItems.SOUL_SYRUP_BUCKET.get());
		
		this.basicItem(YATMItems.BIO_BOTTLE.get());
		this.basicItem(YATMItems.CHORUS_BOTTLE.get());
		this.basicItem(YATMItems.CHORUS_BIO_BOTTLE.get());
		this.basicItem(YATMItems.ENDER_BOTTLE.get());		
		this.basicItem(YATMItems.ESSENCE_OF_DECAY_BOTTLE.get());
		this.basicItem(YATMItems.ESSENCE_OF_SOULS_BOTTLE.get());
		this.basicItem(YATMItems.LATEX_BOTTLE.get());
		this.basicItem(YATMItems.SOUL_SAP_BOTTLE.get());
		this.basicItem(YATMItems.SOUL_SYRUP_BOTTLE.get());
		
		
		this.basicItem(YATMItems.IRON_WIRE_DIE.get());
		
		this.basicItem(YATMItems.RUBBER_SCRAP_BALL.get());
		
		this.basicItem(YATMItems.CREATIVE_FLUID_VOID.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_STORER.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_SOURCE.get());
	} // end registerModels()
	

} // end class