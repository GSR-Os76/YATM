package com.gsr.gsr_yatm.data_generation;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.registry.YATMItems;

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
		// this.basicItem(YATMItems.STEEL_FLUID_EXCHANGER.get());

		this.basicItem(YATMItems.ONE_CU_CURRENT_REGULATOR.get());
		this.basicItem(YATMItems.EIGHT_CU_CURRENT_REGULATOR.get());
		this.basicItem(YATMItems.SIXTYFOUR_CU_CURRENT_REGULATOR.get());
		this.basicItem(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_REGULATOR.get());
		this.basicItem(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_REGULATOR.get());

		this.basicItem(YATMItems.ONE_CU_CURRENT_FUSE.get());
		this.basicItem(YATMItems.EIGHT_CU_CURRENT_FUSE.get());
		this.basicItem(YATMItems.SIXTYFOUR_CU_CURRENT_FUSE.get());
		this.basicItem(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_FUSE.get());
		this.basicItem(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_FUSE.get());

		this.basicItem(YATMItems.ONE_CU_CURRENT_BREAKER.get());
		this.basicItem(YATMItems.EIGHT_CU_CURRENT_BREAKER.get());
		this.basicItem(YATMItems.SIXTYFOUR_CU_CURRENT_BREAKER.get());
		this.basicItem(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_BREAKER.get());
		this.basicItem(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_BREAKER.get());

		
		
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
		
		
		
		this.basicItem(YATMItems.STEEL_WIRE_DIE.get());
		this.basicItem(YATMItems.IRON_WIRE_DIE.get());
		
		
		
		this.basicItem(YATMItems.SILVER_INGOT.get());
		this.basicItem(YATMItems.STEEL_INGOT.get());
		this.basicItem(YATMItems.RUBBER_BAR.get());
		this.basicItem(YATMItems.RUBBER_SCRAP_BALL.get());
		
		this.basicItem(YATMItems.SILVER_NUGGET.get());
		this.basicItem(YATMItems.COPPER_NUGGET.get());
		this.basicItem(YATMItems.NETHERITE_NUGGET.get());
		
		this.basicItem(YATMItems.WAX_BIT_ITEM.get());
		this.basicItem(YATMItems.RUBBER_SCRAP.get());
		
		this.basicItem(YATMItems.STAR_SEED.get());
		this.basicItem(YATMItems.STAR_GERMLING.get());
		this.basicItem(YATMItems.STAR_SPROUT.get());
		this.basicItem(YATMItems.STAR_ADOLESCENT.get());
		
		
		
		this.basicItem(YATMItems.WOOD_PULP.get());
		
		// 
		// LEAF_MULCH
		

		
		this.basicItem(YATMItems.CREATIVE_FLUID_VOID.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_STORER.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_SOURCE.get());
	} // end registerModels()
	

} // end class