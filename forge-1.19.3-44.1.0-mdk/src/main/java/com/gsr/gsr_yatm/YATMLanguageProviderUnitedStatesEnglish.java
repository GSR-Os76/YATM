package com.gsr.gsr_yatm;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class YATMLanguageProviderUnitedStatesEnglish extends LanguageProvider
{

	public YATMLanguageProviderUnitedStatesEnglish(PackOutput output)
	{
		super(output, YetAnotherTechMod.MODID, "en_us");
	} // end constructor

	
	
	@Override
	protected void addTranslations()
	{
		this.add("item_group.gsr_yatm.yatm_general", "Yet Another Tech Mod General");
		
		this.add("menu.title.gsr_yatm.boiler_menu", "Boiler");
		this.add("menu.title.gsr_yatm.extruder_menu", "Extruder");
		this.add("menu.title.gsr_yatm.extractor_menu", "Extractor");
		
		
		
		this.add(YATMBlocks.RUBBER_MERISTEM.get(), "Rubber Meristem");
		this.add(YATMBlocks.RUBBER_LEAVES_YOUNG.get(), "Rubber Leaves Young");
		this.add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get(), "Rubber Leaves Flowering");
		this.add(YATMBlocks.RUBBER_LEAVES_OLD.get(), "Rubber Leaves Old");	
		this.add(YATMBlocks.RUBBER_LOG.get(), "Rubber Log");
		this.add(YATMBlocks.RUBBER_WOOD.get(), "Rubber Wood");
		this.add(YATMBlocks.PARTIALLY_STRIPPED_RUBBER_LOG.get(), "Partially Stripped Rubber Log");
		this.add(YATMBlocks.STRIPPED_RUBBER_LOG.get(), "Stripped Rubber Log");
		this.add(YATMBlocks.STRIPPED_RUBBER_WOOD.get(), "Stripped Rubber Wood");
		this.add(YATMBlocks.RUBBER_PLANKS.get(), "Rubber Planks");
		this.add(YATMBlocks.FANCY_RUBBER_PLANKS.get(), "Fancy Rubber Planks");
		this.add(YATMBlocks.RUBBER_STAIRS.get(), "Rubber Stairs");
		this.add(YATMBlocks.RUBBER_SLAB.get(), "Rubber Slab");
		this.add(YATMBlocks.RUBBER_FENCE.get(), "Rubber Fence");
		this.add(YATMBlocks.RUBBER_FENCE_GATE.get(), "Fancy Fence Gate");
		this.add(YATMBlocks.RUBBER_DOOR.get(), "Rubber Door");
		this.add(YATMBlocks.RUBBER_TRAPDOOR.get(), "Rubber Trapdoor");
		this.add(YATMBlocks.RUBBER_PRESSURE_PLATE.get(), "Rubber Pressure Plate");
		this.add(YATMBlocks.RUBBER_BUTTON.get(), "Rubber Button");
		
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get(), "Soul Afflicted Rubber Meristem");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get(), "Soul Afflicted Rubber Leaves Young");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get(), "Soul Afflicted Rubber Leaves Flowering");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get(), "Soul Afflicted Rubber Leaves Old");	
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get(), "Soul Afflicted Rubber Log");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_WOOD.get(), "Soul Afflicted Rubber Wood");
		this.add(YATMBlocks.SOUL_AFFLICTED_PARTIALLY_STRIPPED_RUBBER_LOG.get(), "Soul Afflicted Partially Stripped Rubber Log");
		this.add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_LOG.get(), "Soul Afflicted Stripped Rubber Log");
		this.add(YATMBlocks.SOUL_AFFLICTED_STRIPPED_RUBBER_WOOD.get(), "Soul Afflicted Stripped Rubber Wood");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PLANKS.get(), "Soul Afflicted Rubber Planks");
		this.add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS.get(), "Soul Afflicted Fancy Rubber Planks (Untiled Whirl)");
		this.add(YATMBlocks.SOUL_AFFLICTED_FANCY_RUBBER_PLANKS_TILED.get(), "Soul Afflicted Fancy Rubber Planks (Tiled Whirl)");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_STAIRS.get(), "Soul Afflicted Rubber Stairs");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_SLAB.get(), "Soul Afflicted Rubber Slab");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE.get(), "Soul Afflicted Rubber Fence");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_FENCE_GATE.get(), "Fancy Fence Gate");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_DOOR.get(), "Rubber Door");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_TRAPDOOR.get(), "Soul Afflicted Rubber Trapdoor");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_PRESSURE_PLATE.get(), "Soul Afflicted Rubber Pressure Plate");
		this.add(YATMBlocks.SOUL_AFFLICTED_RUBBER_BUTTON.get(), "Soul Afflicted Rubber Button");
		
		this.add(YATMBlocks.RUBBER_BLOCK.get(), "Rubber Block");
		
		
		this.add(YATMBlocks.LARGE_COPPER_HEAT_SINK.get(), "Large Copper Heat Sink");
		this.add(YATMBlocks.STEEL_BOILER_TANK.get(), "Steel Boiler Tank");
		this.add(YATMBlocks.STEEL_BOILER.get(), "Steel Boiler");
		this.add(YATMBlocks.STEEL_EXTRACTOR.get(), "Steel Extractor");
		this.add(YATMBlocks.STEEL_EXTRUDER.get(), "Steel Extruder");
		this.add(YATMBlocks.C_U_F_E_I.get(), "Energy Converter (CU to FE)");
		
		
		
		this.add(YATMBlocks.ONE_CU_WIRE.get(), "1cu Wire");
		this.add(YATMBlocks.EIGHT_CU_WIRE.get(), "8cu Wire");
		this.add(YATMBlocks.SIXTYFOUR_CU_WIRE.get(), "64cu Wire");
		this.add(YATMBlocks.FIVEHUNDREDTWELVE_CU_WIRE.get(), "512cu Wire");
		this.add(YATMBlocks.FOURTHOUSANDNINTYSIX_CU_WIRE.get(), "4096cu Wire");
		
		this.add(YATMBlocks.ENAMELED_ONE_CU_WIRE.get(), "Enameled 1cu Wire");
		this.add(YATMBlocks.ENAMELED_EIGHT_CU_WIRE.get(), "Enameled 8cu Wire");
		this.add(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get(), "Enameled 64cu Wire");
		this.add(YATMBlocks.ENAMELED_FIVEHUNDREDTWELVE_CU_WIRE.get(), "Enameled 512cu Wire");
		this.add(YATMBlocks.ENAMELED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), "Enameled 4096cu Wire");
		
		this.add(YATMBlocks.INSULATED_ONE_CU_WIRE.get(), "Insulated 1cu Wire");
		this.add(YATMBlocks.INSULATED_EIGHT_CU_WIRE.get(), "Insulated 8cu Wire");
		this.add(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get(), "Insulated 64cu Wire");
		this.add(YATMBlocks.INSULATED_FIVEHUNDREDTWELVE_CU_WIRE.get(), "Insulated 512cu Wire");
		this.add(YATMBlocks.INSULATED_FOURTHOUSANDNINTYSIX_CU_WIRE.get(), "Insulated 4096cu Wire");
		
		this.add(YATMBlocks.STEEL_FLUID_CONDUIT.get(), "Steel Fluid Conduit");
		
		
		// ITEMS \\
		this.add(YATMItems.STEEL_FLUID_EXCHANGER.get(), "Steel Fluid Exchanged");
		
		this.add(YATMItems.ONE_CU_CURRENT_REGULATOR.get(), "1cu Current Regulator");
		this.add(YATMItems.EIGHT_CU_CURRENT_REGULATOR.get(), "8cu Current Regulator");
		this.add(YATMItems.SIXTYFOUR_CU_CURRENT_REGULATOR.get(), "64cu Current Regulator");
		this.add(YATMItems.FIVEHUNDREDTWELVE_CU_CURRENT_REGULATOR.get(), "512cu Current Regulator");
		this.add(YATMItems.FOURTHOUSANDNINTYSIX_CU_CURRENT_REGULATOR.get(), "4096cu Current Regulator");
		
		this.add(YATMItems.ESSENCE_OF_DECAY_BUCKET.get(), "Essence Of Decay Bucket");
		this.add(YATMItems.LATEX_BUCKET.get(), "Latex Bucket");
		this.add(YATMItems.SOUL_SAP_BUCKET.get(), "Soul Sap Bucket");
		
		this.add(YATMItems.ESSENCE_OF_DECAY_BOTTLE.get(), "Esence Of Decay Bottle");
		this.add(YATMItems.SOUL_SAP_BOTTLE.get(), "Soul Sap Bottle");
		
		this.add(YATMItems.IRON_WIRE_DIE.get(), "Iron Die (Wire)");
		this.add(YATMItems.STEEL_WIRE_DIE.get(), "Steel Die (Wire)");
		
		this.add(YATMItems.SILVER_INGOT.get(), "Silver Ingot");
		this.add(YATMItems.STEEL_INGOT.get(), "Steel Ingot");
		this.add(YATMItems.RUBBER_BAR.get(), "Rubber Bar");
		this.add(YATMItems.RUBBER_SCRAP_BALL.get(), "Rubber Scrap Ball");
		
		this.add(YATMItems.SILVER_NUGGET.get(), "Silver Nugget");
		this.add(YATMItems.COPPER_NUGGET.get(), "Copper Nugget");
		this.add(YATMItems.NETHERITE_NUGGET.get(), "Netherite Nugget");
		this.add(YATMItems.WAX_BIT_ITEM.get(), "Wax Bit");
		this.add(YATMItems.RUBBER_SCRAP_ITEM.get(), "Rubber Scraps");
		
		this.add(YATMItems.CREATIVE_FLUID_SOURCE.get(), "Creative Fluid Source");
		this.add(YATMItems.CREATIVE_FLUID_STORER.get(), "Creative Fluid Storer");
		this.add(YATMItems.CREATIVE_FLUID_VOID.get(), "Creative Fluid Void");
		
	} // end addTranslations()

} // end class