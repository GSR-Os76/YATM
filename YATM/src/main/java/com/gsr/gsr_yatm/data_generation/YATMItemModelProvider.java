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
		this.basicItem(YATMItems.RUBBER_SIGN_ITEM.get());
		this.basicItem(YATMItems.RUBBER_HANGING_SIGN_ITEM.get());
		this.basicItem(YATMItems.RUBBER_BOAT_ITEM.get());
		this.basicItem(YATMItems.RUBBER_CHEST_BOAT_ITEM.get());
		
		this.basicItem(YATMItems.SOUL_AFFLICTED_RUBBER_SIGN_ITEM.get());
		this.basicItem(YATMItems.SOUL_AFFLICTED_RUBBER_HANGING_SIGN_ITEM.get());
		this.basicItem(YATMItems.SOUL_AFFLICTED_RUBBER_BOAT_ITEM.get());
		this.basicItem(YATMItems.SOUL_AFFLICTED_RUBBER_CHEST_BOAT_ITEM.get());
		
		this.basicItem(YATMItems.HANGING_POT_HOOK_ITEM.get());
		
		
		this.basicItem(YATMItems.CONDUIT_VINES_ITEM.get());
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
		
		// wheel goes here
		this.basicItem(YATMItems.FOLIAR_STEEL.get());
		this.basicItem(YATMItems.SILVER_INGOT.get());
		this.basicItem(YATMItems.STEEL_INGOT.get());
		this.basicItem(YATMItems.RUBBER_BAR.get());
		this.basicItem(YATMItems.RUBBER_SCRAP_BALL.get());
		
		this.basicItem(YATMItems.SILVER_NUGGET.get());
		this.basicItem(YATMItems.COPPER_NUGGET.get());
		this.basicItem(YATMItems.NETHERITE_NUGGET.get());
		
		this.basicItem(YATMItems.WAX_BIT_ITEM.get());
		this.basicItem(YATMItems.RUBBER_SCRAP.get());
		
		
		
		this.basicItem(YATMItems.WOOD_PULP.get());
		
		this.basicItem(YATMItems.AURUM_DEMINUTUS_FIDDLE_HEAD.get());
		this.basicItem(YATMItems.AURUM_DEMINUTUS_FROND.get());
		this.basicItem(YATMItems.CARCASS_ROOT_CUTTING.get());
		this.basicItem(YATMItems.COTTON_BOLLS.get());
		this.basicItem(YATMItems.RAW_COTTON_FIBER.get());
		this.basicItem(YATMItems.COTTON_SEEDS.get());
		this.basicItem(YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get());
		this.basicItem(YATMItems.SHULKWART_SPORES.get());
		this.basicItem(YATMItems.SPIDER_VINE_FRUITS.get());
		
		
		
		this.basicItem(YATMItems.STAR_SEED.get());
		this.basicItem(YATMItems.STAR_GERMLING.get());
		this.basicItem(YATMItems.STAR_SPROUT.get());
		this.basicItem(YATMItems.STAR_ADOLESCENT.get());
		
		
		
		this.basicItem(YATMItems.AURUM_FAN.get());
		
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_HELMET.get());
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_CHESTPLATE.get());
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_LEGGINGS.get());
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_BOOTS.get());
		
		this.basicItem(YATMItems.DECAY_NETHERITE_HELMET.get());
		this.basicItem(YATMItems.DECAY_NETHERITE_CHESTPLATE.get());
		this.basicItem(YATMItems.DECAY_NETHERITE_LEGGINGS.get());
		this.basicItem(YATMItems.DECAY_NETHERITE_BOOTS.get());
		
		this.basicItem(YATMItems.CREATIVE_FLUID_VOID.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_STORER.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_SOURCE.get());
	} // end registerModels()
	

} // end class