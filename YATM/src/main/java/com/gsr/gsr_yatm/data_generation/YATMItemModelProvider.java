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
		
		this.basicItem(YATMItems.ADAMUM_MERISTEM.get());
		
		this.basicItem(YATMItems.AURUM_DEMINUTUS_FIDDLE_HEAD.get());
		
		this.basicItem(YATMItems.BASIN_OF_TEARS_SEED.get());
		this.basicItem(YATMItems.DILUTED_TEAR_BOTTLE.get());
		
		this.basicItem(YATMItems.CANDLELILY_ITEM.get());
		
		this.basicItem(YATMItems.CARBUM_MERISTEM.get());
		
		this.basicItem(YATMItems.CARCASS_ROOT_CUTTING.get());
		
		this.basicItem(YATMItems.COTTON_BOLLS.get());
		this.basicItem(YATMItems.RAW_COTTON_FIBER.get());
		this.basicItem(YATMItems.COTTON_SEEDS.get());
		
		this.basicItem(YATMItems.CUPRUM_BULB.get());		
		
		this.basicItem(YATMItems.FERRUM_ROOTSTOCK.get());
		
		this.basicItem(YATMItems.FOLIUM_RHIZOME.get());
		
		this.basicItem(YATMItems.FIRE_EATER_LILY_BULB.get());
		this.basicItem(YATMItems.FIRE_EATER_LILY_DECORATIVE.get());
		this.basicItem(YATMItems.FIRE_EATER_LILY_UNLIT_DECORATIVE.get());
		
		this.basicItem(YATMItems.ICE_CORAL_POLYP.get());
		this.basicItem(YATMItems.BLEACHED_ICE_CORAL_OLD_ITEM.get());
		this.basicItem(YATMItems.BLEACHED_ICE_CORAL_ADOLESCENT_ITEM.get());
		this.basicItem(YATMItems.BLEACHED_ICE_CORAL_YOUNG_ITEM.get());
		this.basicItem(YATMItems.BLEACHED_ICE_CORAL_POLYP_ITEM.get());
		
		this.basicItem(YATMItems.INFERNALUM_RHIZOME.get());		
		
		this.basicItem(YATMItems.LAPUM_MERISTEM.get());		
		
		this.basicItem(YATMItems.PRISMARINE_CRYSTAL_MOSS_SPORES.get());
		
		this.basicItem(YATMItems.RUBERUM_CORM.get());		
		
		this.basicItem(YATMItems.SAMARAGDUM_NODULE.get());		
		
		this.basicItem(YATMItems.SHULKWART_SPORES.get());
		
		this.basicItem(YATMItems.SPIDER_VINE_FRUITS.get());	
		
		this.basicItem(YATMItems.VICUM_MERISTEM.get());
		
		this.basicItem(YATMItems.RAW_EXOTHEMIC_GLAND.get());
		
		this.basicItem(YATMItems.HANGING_POT_HOOK_ITEM.get());
		
		
		
		this.basicItem(YATMItems.CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.WHITE_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.ORANGE_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.MAGENTA_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.LIGHT_BLUE_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.YELLOW_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.LIME_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.PINK_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.GRAY_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.LIGHT_GRAY_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.CYAN_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.PURPLE_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.BLUE_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.BROWN_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.GREEN_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.RED_CANDLE_LANTERN_ITEM.get());
		this.basicItem(YATMItems.BLACK_CANDLE_LANTERN_ITEM.get());
		
	
		this.basicItem(YATMItems.CONDUIT_VINES_ITEM.get());
		this.basicItem(YATMItems.CHANNEL_VINES_ITEM.get());
		
		this.basicItem(YATMItems.EMBER_GLAND.get());
		this.basicItem(YATMItems.FLAME_GLAND.get());
		this.basicItem(YATMItems.TORCH_GLAND.get());

		
		
		this.basicItem(YATMItems.BIO_BUCKET.get());
		this.basicItem(YATMItems.CHORUS_BUCKET.get());
		this.basicItem(YATMItems.CHORUS_BIO_BUCKET.get());
		this.basicItem(YATMItems.ENDER_BUCKET.get());		
		this.basicItem(YATMItems.ESSENCE_OF_DECAY_BUCKET.get());
		this.basicItem(YATMItems.ESSENCE_OF_SOULS_BUCKET.get());
		this.basicItem(YATMItems.LATEX_BUCKET.get());
		this.basicItem(YATMItems.SILICON_OXIDE_BUCKET.get());
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
		
		
		
		this.basicItem(YATMItems.FOLIAR_STEEL.get());
		
		this.basicItem(YATMItems.RUBBER_BAR.get());
		this.basicItem(YATMItems.WOOD_PULP.get());
		
		this.basicItem(YATMItems.NETHERITE_NUGGET.get());
		this.basicItem(YATMItems.STAR_SEED.get());
		this.basicItem(YATMItems.STAR_GERMLING.get());
		this.basicItem(YATMItems.STAR_SPROUT.get());
		this.basicItem(YATMItems.STAR_ADOLESCENT.get());
		
		
		
		// this.basicItem(YATMItems.AURUM_FAN.get());
		this.basicItem(YATMItems.SPEED_UPGRADE.get());
		this.basicItem(YATMItems.EFFICIENCY_UPGRADE.get());
		
		this.basicItem(YATMItems.WOODEN_DRILL_BIT.get());
		this.basicItem(YATMItems.STONE_DRILL_BIT.get());
		this.basicItem(YATMItems.IRON_DRILL_BIT.get());
		this.basicItem(YATMItems.STEEL_DRILL_BIT.get());
		this.basicItem(YATMItems.GOLD_DRILL_BIT.get());
		this.basicItem(YATMItems.DIAMOND_DRILL_BIT.get());
		this.basicItem(YATMItems.NETHERITE_DRILL_BIT.get());
		
		this.basicItem(YATMItems.WOODEN_SAW_BLADE.get());
		this.basicItem(YATMItems.STONE_SAW_BLADE.get());
		this.basicItem(YATMItems.IRON_SAW_BLADE.get());
		this.basicItem(YATMItems.STEEL_SAW_BLADE.get());
		this.basicItem(YATMItems.GOLD_SAW_BLADE.get());
		this.basicItem(YATMItems.DIAMOND_SAW_BLADE.get());
		this.basicItem(YATMItems.NETHERITE_SAW_BLADE.get());
		
		this.basicItem(YATMItems.STEEL_DRILL_WOOD.get());
		this.basicItem(YATMItems.STEEL_DRILL_STONE.get());
		this.basicItem(YATMItems.STEEL_DRILL_IRON.get());
		this.basicItem(YATMItems.STEEL_DRILL_STEEL.get());
		this.basicItem(YATMItems.STEEL_DRILL_GOLD.get());
		this.basicItem(YATMItems.STEEL_DRILL_DIAMOND.get());
		this.basicItem(YATMItems.STEEL_DRILL_NETHERITE.get());
		
		this.basicItem(YATMItems.STEEL_SAW_WOOD.get());
		this.basicItem(YATMItems.STEEL_SAW_STONE.get());
		this.basicItem(YATMItems.STEEL_SAW_IRON.get());
		this.basicItem(YATMItems.STEEL_SAW_STEEL.get());
		this.basicItem(YATMItems.STEEL_SAW_GOLD.get());
		this.basicItem(YATMItems.STEEL_SAW_DIAMOND.get());
		this.basicItem(YATMItems.STEEL_SAW_NETHERITE.get());
		
		this.basicItem(YATMItems.STEEL_WRENCH.get());
		
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_HELMET.get());
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_CHESTPLATE.get());
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_LEGGINGS.get());
		this.basicItem(YATMItems.SOUL_ADORNED_NETHERITE_BOOTS.get());
		
		this.basicItem(YATMItems.DECAY_NETHERITE_HELMET.get());
		this.basicItem(YATMItems.DECAY_NETHERITE_CHESTPLATE.get());
		this.basicItem(YATMItems.DECAY_NETHERITE_LEGGINGS.get());
		this.basicItem(YATMItems.DECAY_NETHERITE_BOOTS.get());
		
		this.basicItem(YATMItems.CREATIVE_CURRENT_SOURCE_ITEM.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_VOID.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_STORER.get());
		this.basicItem(YATMItems.CREATIVE_FLUID_SOURCE.get());
	} // end registerModels()
	

} // end class