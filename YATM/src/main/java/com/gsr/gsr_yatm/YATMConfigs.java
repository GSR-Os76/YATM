package com.gsr.gsr_yatm;

import com.gsr.gsr_yatm.utilities.config.ConfigBuilderHelper;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class YATMConfigs
{
	private static final int DRAIN_RECHECK_PERIOD = 40;
	private static final int LIT_ABOVE_TEMPERATURE = 300;
	private static final int STEEL_DEVICE_CURRENT_CAPACITY = 32768;
	private static final int STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE = 256;
	private static final int STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE = 60;
	private static final int STEEL_DEVICE_MAX_TEMPERATURE = 4000;
	private static final int STEEL_DEVICE_TANK_CAPACITY = 8000;
	
	
	
	private static ConfigBuilderHelper s_builder = new ConfigBuilderHelper();
	
	
	
	
	public static final ConfigValue<Integer> CANDLELILY_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.push("Plants: ").push("Candlelily:").bonemealSuccessRarity();
	public static final ConfigValue<Integer> CANDLELILY_MAX_HORIZONTAL_SPREAD_ATTEMPTS = YATMConfigs.s_builder.comment("The maximum horizontal extent any direction that new plants can be spread to.").defineInRange("horizontal_spread_extent", 2, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CANDLELILY_MAX_PLACEMENTS = YATMConfigs.s_builder.comment("The maximum number of new plants placed while spreading.").defineInRange("max_placements", 5, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CANDLELILY_SPREAD_ATTEMPTS = YATMConfigs.s_builder.comment("The maximum number of attempts to place down a new plant.").defineInRange("max_spread_attempts", 12, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CANDLELILY_MAX_VERTICAL_SPREAD_EXTENT = YATMConfigs.s_builder.comment("The maximum vertical extent any direction that new plants can be spread to.").defineInRange("vertical_spread_extent", 1, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> CONDUIT_VINE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.pop().push("Conduit Vine:").bonemealSuccessRarity();
	public static final ConfigValue<Integer> CONDUIT_VINE_GROWTH_RARITY  = YATMConfigs.s_builder.growthRarity(16);
	public static final ConfigValue<Integer> CONDUIT_VINE_MINIMUM_LIGHT_LEVEL = YATMConfigs.s_builder.minimumLightLevel();
	
	public static final ConfigValue<Integer> DWARF_PERSIMMON_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.pop().push("Dwarf Persimmon: ").bonemealSuccessRarity();
	public static final ConfigValue<Integer> DWARF_PERSIMMON_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(16);
	public static final ConfigValue<Integer> DWARF_PERSIMMON_MINIMUM_LIGHT_LEVEL = YATMConfigs.s_builder.minimumLightLevel();
	public static final ConfigValue<Integer> DWARF_PERSIMMON_MAX_AGE_INCREASE = YATMConfigs.s_builder.comment("The maximum amount the age block state property will increase on bonemeal success. Note: technically interchangeable with the min.").defineInRange("max_boneameal_age_increase", 3, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> DWARF_PERSIMMON_MIN_AGE_INCREASE = YATMConfigs.s_builder.comment("The minimum amount the age block state property will increase on bonemeal success. Note: technically interchangeable with the max.").defineInRange("min_boneameal_age_increase", 1, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> FIRE_EATER_LILY_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.pop().push("Fire Eater Lily: ").bonemealSuccessRarity();
	public static final ConfigValue<Integer> FIRE_EATER_LILY_LIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.bonemealSuccessRarity("bonemeal_success_rarity_lit_decorative");
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD = YATMConfigs.s_builder.comment("The maximum number of blocks horizontally that a canidate position will be picked out from.").defineInRange("max_horizontal_spread", 2, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_SPREAD_ATTEMPTS = YATMConfigs.s_builder.comment("The maximum number of canidate positions to consider while attempting spreading.").defineInRange("max_spread_attempts", 12, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_SPREADS = YATMConfigs.s_builder.comment("The maximum number of successful spread attempts that're allowed.").defineInRange("max_spreads", 5, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_VERTICAL_SPREAD = YATMConfigs.s_builder.comment("The maximum number of blocks vertically that a canidate position will be picked out from.").defineInRange("max_vertical_spread", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_UNLIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.bonemealSuccessRarity("bonemeal_success_rarity_unlit_decorative");
	
	
	
	public static final ConfigValue<Double> ADAMUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ore Themed Plants: ").push("Adamum: ").damageFactor(0d);
	public static final ConfigValue<Double> ADAMUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance(Double.MAX_VALUE);
	public static final ConfigValue<Integer> ADAMUM_FRUITING_RARITY = YATMConfigs.s_builder.comment("The average number of blocks matured for which one bears \"fruit\".").defineInRange("fruit_rarity", 3, 0, Integer.MAX_VALUE);	
	public static final ConfigValue<Integer> ADAMUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(64);
	public static final ConfigValue<Integer> ADAMUM_MIN_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at least this, inclusive.").defineInRange("min_major_age_increase", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> ADAMUM_MAX_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at most this, inclusive.").defineInRange("max_major_age_increase", 2, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> AURUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Aurum: ").damageFactor(4d);
	public static final ConfigValue<Double> AURUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance();
	public static final ConfigValue<Integer> AURUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Double> CARBUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Carbum").damageFactor(1d);
	public static final ConfigValue<Double> CARBUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance();
	public static final ConfigValue<Integer> CARBUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Double> CUPRUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Cuprum: ").damageFactor(2d);
	public static final ConfigValue<Double> CUPRUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance();
	public static final ConfigValue<Integer> CUPRUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Double> FERRUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ferrum: ").damageFactor(3.6d);
	public static final ConfigValue<Double> FERRUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance();
	public static final ConfigValue<Integer> FERRUM_FRUIT_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which \"fruit\" grow once.").defineInRange("fruit_rarity", 12, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FERRUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	public static final ConfigValue<Integer> FERRUM_MIN_FRUIT_COUNT = YATMConfigs.s_builder.comment("The average minimum number of \"fruit\" that can be harvested in one harvest.").defineInRange("min_fruit", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FERRUM_MAX_FRUIT_COUNT = YATMConfigs.s_builder.comment("The average maximum number of \"fruit\" that can be harvested in one harvest.").defineInRange("max_fruit", 3, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> FOLIUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Folium: ").damageFactor(4d);
	public static final ConfigValue<Double> FOLIUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance();
	public static final ConfigValue<Integer> FOLIUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Double> INFERNALUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Infernalum: ").damageFactor(6.6d);
	public static final ConfigValue<Double> INFERNALUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance();
	public static final ConfigValue<Integer> INFERNALUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(64);
	public static final ConfigValue<Integer> INFERNALUM_MIN_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at least this, inclusive.").defineInRange("min_major_age_increase", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> INFERNALUM_MAX_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at most this, inclusive.").defineInRange("max_major_age_increase", 2, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> LAPUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Lapum: ").damageFactor(0d);
	public static final ConfigValue<Double> LAPUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance(Double.MAX_VALUE);
	public static final ConfigValue<Integer> LAPUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Double> RUBERUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ruberum: ").damageFactor(0d);
	public static final ConfigValue<Double> RUBERUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance(Double.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	public static final ConfigValue<Integer> RUBERUM_MAX_PARTICLES = YATMConfigs.s_builder.comment("The maximum number of particles that might be choosen to be spawned when the plant is lit.").defineInRange("max_particles", 3, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_MIN_PARTICLES = YATMConfigs.s_builder.comment("The minimum number of particles that might be choosen to be spawned when the plant is lit.").defineInRange("min_particles", 0, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_PARTICLE_RARITY = YATMConfigs.s_builder.comment("The average number of animation ticks for which particles're spawned once.").defineInRange("particle_rarity", 6, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_SIGNAL_FACTOR = YATMConfigs.s_builder.comment("The factor by which the redstone signal emitted's strength from walking through the block's scaled.").defineInRange("signal_factor", 2, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Double> RUBERUM_SIGNAL_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without causing a redstone signal.").defineInRange("signal_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	
	public static final ConfigValue<Double> SAMARAGDUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Samaragdum: ").damageFactor(0d);
	public static final ConfigValue<Double> SAMARAGDUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance(Double.MAX_VALUE);
	public static final ConfigValue<Integer> SAMARAGDUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Double> VICUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Vicum: ").damageFactor(0d);
	public static final ConfigValue<Double> VICUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.damageTriggerTolerance(Double.MAX_VALUE);
	public static final ConfigValue<Integer> VICUM_GROWTH_RARITY = YATMConfigs.s_builder.growthRarity(36);
	
	public static final ConfigValue<Boolean> IS_HORIZONTAL_GROWRTH_UNBOUND = YATMConfigs.s_builder.pop().pop().push("Misc: ").comment("Determines if plants limit their ability to propagate horizontally.").define("is_horizontal_growth_unbound", false);
	
	
	public static final ConfigValue<Integer> BIOREACTOR_CURRENT_CAPACITY = YATMConfigs.s_builder.pop().pop().push("Devices: ").push("Bioreactor: ").currentCapacity(YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY);
	public static final ConfigValue<Integer> BIOREACTOR_DRAIN_RESULT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("result tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> BIOREACTOR_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.maxCurrentTransferRate(YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE);
	public static final ConfigValue<Integer> BIOREACTOR_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("result", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> BIOREACTOR_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("result", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	
	public static final ConfigValue<Integer> BOILER_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.pop().push("Boiler: ").maxDrainTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> BOILER_DRAIN_INPUT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("inpute tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> BOILER_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("result", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> BOILER_DRAIN_RESULT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("result tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> BOILER_FILL_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxFillTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> BOILER_INPUT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("input", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	public static final ConfigValue<Integer> BOILER_LIT_ABOVE_TEMPERATURE = YATMConfigs.s_builder.litAboveTemperature(YATMConfigs.LIT_ABOVE_TEMPERATURE);
	public static final ConfigValue<Integer> BOILER_MAX_TEMPERATURE = YATMConfigs.s_builder.maxTemperature(YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE);
	public static final ConfigValue<Integer> BOILER_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("result", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	
	public static final ConfigValue<Integer> CRUCIBLE_DRAIN_RESULT_RECHECK_PERIOD = YATMConfigs.s_builder.pop().push("Crucible: ").outputComponentRecheckPeriod("result tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> CRUCIBLE_LIT_ABOVE_TEMPERATURE = YATMConfigs.s_builder.litAboveTemperature(YATMConfigs.LIT_ABOVE_TEMPERATURE);
	public static final ConfigValue<Integer> CRUCIBLE_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("result", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> CRUCIBLE_MAX_TEMPERATURE = YATMConfigs.s_builder.maxTemperature(YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE);
	public static final ConfigValue<Integer> CRUCIBLE_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("result", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	
	public static final ConfigValue<Integer> CRYSTALLIZER_CURRENT_CAPACITY = YATMConfigs.s_builder.pop().push("Crystallizer: ").currentCapacity(YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY);
	public static final ConfigValue<Integer> CRYSTALLIZER_DRAIN_INPUT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("input tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> CRYSTALLIZER_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> CRYSTALLIZER_FILL_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxFillTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> CRYSTALLIZER_INPUT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("input", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	public static final ConfigValue<Integer> CRYSTALLIZER_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.maxCurrentTransferRate(YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE);
	
	public static final ConfigValue<Integer> EXTRACTOR_CURRENT_CAPACITY = YATMConfigs.s_builder.pop().push("Extractor: ").currentCapacity(YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY);
	public static final ConfigValue<Integer> EXTRACTOR_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("result", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> EXTRACTOR_DRAIN_RESULT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("result tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> EXTRACTOR_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.maxCurrentTransferRate(YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE);
	public static final ConfigValue<Integer> EXTRACTOR_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("result", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	
	public static final ConfigValue<Integer> GRINDER_CURRENT_CAPACITY = YATMConfigs.s_builder.pop().push("Grinder: ").currentCapacity(YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY);
	public static final ConfigValue<Integer> GRINDER_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.maxCurrentTransferRate(YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE);

	public static final ConfigValue<Integer> HEAT_FURNACE_MAX_TEMPERATURE = YATMConfigs.s_builder.pop().push("Heat Furnace: ").maxTemperature(YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE);
	
	public static final ConfigValue<Integer> INJECTOR_CURRENT_CAPACITY = YATMConfigs.s_builder.pop().push("Injector: ").currentCapacity(YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY);
	public static final ConfigValue<Integer> INJECTOR_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> INJECTOR_DRAIN_INPUT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("input tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> INJECTOR_FILL_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxFillTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> INJECTOR_INPUT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("input", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	public static final ConfigValue<Integer> INJECTOR_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.maxCurrentTransferRate(YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE);
	
	public static final ConfigValue<Integer> STILL_DISTILLATE_TANK_CAPACITY = YATMConfigs.s_builder.pop().push("Still: ").tankCapacity("distillate", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	public static final ConfigValue<Integer> STILL_DRAIN_DISTILLATE_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("distillate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> STILL_DRAIN_DISTILLATE_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("distillate tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> STILL_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> STILL_DRAIN_INPUT_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("input tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> STILL_DRAIN_REMAINDER_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxDrainTankRate("remainder", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> STILL_DRAIN_REMAINDER_RECHECK_PERIOD = YATMConfigs.s_builder.outputComponentRecheckPeriod("remainder tank", YATMConfigs.DRAIN_RECHECK_PERIOD);
	public static final ConfigValue<Integer> STILL_FILL_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.maxFillTankRate("input", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE);
	public static final ConfigValue<Integer> STILL_INPUT_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("input", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	public static final ConfigValue<Integer> STILL_LIT_ABOVE_TEMPERATURE = YATMConfigs.s_builder.litAboveTemperature(YATMConfigs.LIT_ABOVE_TEMPERATURE);
	public static final ConfigValue<Integer> STILL_MAX_TEMPERATURE = YATMConfigs.s_builder.maxTemperature(YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE);
	public static final ConfigValue<Integer> STILL_REMAINDER_TANK_CAPACITY = YATMConfigs.s_builder.tankCapacity("remainder", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY);
	
	
	
	public static final ConfigValue<Integer> TANK_CAPACITY = YATMConfigs.s_builder.pop().push("Tank: ").comment("The tank's capacity in milibuckets.").defineInRange("capacity", 16000, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> TANK_DRAIN_RECHECK_PERIOD = YATMConfigs.s_builder.comment("The period in ticks of the device trying to attach to a below neighbor's fluid handling capability.").defineInRange("drain_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> TANK_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("max_fluid_transfer_rate", 1000, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> CRAFTING_RECHECK_PERIOD = YATMConfigs.s_builder.pop().push("Misc: ").comment("The period in ticks while idle before a crafting device'll try to match it's contents to a new recipe. Note: recheck's occur automatically when device inventories change.").defineInRange("crafting_recheck_period", 20, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CONDUIT_LIKE_RECHECK_ATTACHMENTS_PERIOD = YATMConfigs.s_builder.pop().push("Misc: ").comment("The period in ticks after which the conduit rechecks it's self.").defineInRange("conduit_like_attachment_recheck_period", 80, 0, Integer.MAX_VALUE);
	
	
	
	public static final ConfigValue<Integer> EMBER_GLAND_MAX_TEMPERATURE = YATMConfigs.s_builder.pop().pop().push("Components: ").push("Current Heaters: ").push("Ember Gland: ").maxTemperature(1024);
	public static final ConfigValue<Double> EMBER_GLAND_KELVIN_PER_CURRENT = YATMConfigs.s_builder.kelvinPerCu(12d);
	public static final ConfigValue<Integer> FLAME_GLAND_MAX_TEMPERATURE = YATMConfigs.s_builder.pop().push("Flame Gland: ").maxTemperature(2048);
	public static final ConfigValue<Double> FLAME_GLAND_KELVIN_PER_CURRENT = YATMConfigs.s_builder.kelvinPerCu(16d);
	public static final ConfigValue<Integer> TORCH_GLAND_MAX_TEMPERATURE = YATMConfigs.s_builder.pop().push("Torch Gland: ").maxTemperature(4096);
	public static final ConfigValue<Double> TORCH_GLAND_KELVIN_PER_CURRENT = YATMConfigs.s_builder.kelvinPerCu(20d);
	
	public static final ConfigValue<Integer> CURRENT_TUBER = YATMConfigs.s_builder.pop().pop().push("Current Storers: ").push("Current Tuber: ").currentCapacity(32768);
	public static final ConfigValue<Integer> CURRENT_BATTERY = YATMConfigs.s_builder.pop().push("Current Battery: ").currentCapacity(98304);
	public static final ConfigValue<Integer> ADVANCED_CURRENT_BATTERY = YATMConfigs.s_builder.pop().push("Advanced Current Battery: ").currentCapacity(393216);

	
	
	public static final ConfigValue<Integer> DECAY_NETHERITE_ITEM_DAMAGE_REDUCTION_FACTOR = YATMConfigs.s_builder.pop().pop().pop().push("Armor: ").push("Decay Netherite: ").comment("The amount of damage removed from the armor piece's itemstack per unit of wither damage that the wearer recieves.").define("item_damage_reduction_factor", 9);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_DEFENSE_FACTOR = YATMConfigs.s_builder.netheriteRelativeDefenceFactor(0.89d);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_DURABILITY_FACTOR = YATMConfigs.s_builder.netheriteRelativeDurabilityFactor(0.5d);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR = YATMConfigs.s_builder.netheriteRelativeEnchantmentFactor(1.0d);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR = YATMConfigs.s_builder.netheriteRelativeKnockbackResistanceFactor(1.0d);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_TOUGHNESS_FACTOR = YATMConfigs.s_builder.netheriteRelativeToughnessFactor(0.72d);
	
	// TODO, maybe add the word ARMOR in to be clearer
	public static final ConfigValue<Integer> FOLIAR_STEEL_BOOTS_DEFENSE = YATMConfigs.s_builder.pop().push("Foliar Steel: ").comment("The boot's defense.").define("boots_defense", 2);
	public static final ConfigValue<Integer> FOLIAR_STEEL_BOOTS_DURABILITY = YATMConfigs.s_builder.comment("The boot's durability.").define("boots_durability", 13 * 27);
	public static final ConfigValue<Boolean> FOLIAR_STEEL_CAN_BREAK = YATMConfigs.s_builder.comment("Defines if the armor will break from incoming damage if it would deplete the durability, otherwise the armor simply stops providing bonuses until recharged.").define("can_break", false);
	public static final ConfigValue<Integer> FOLIAR_STEEL_CHESTPLATE_DEFENSE = YATMConfigs.s_builder.comment("The chestplate's defense.").define("chestplate_defense", 6);
	public static final ConfigValue<Integer> FOLIAR_STEEL_CHESTPLATE_DURABILITY = YATMConfigs.s_builder.comment("The chestplate's durability.").define("chestplate_durability", 16 * 27);
	public static final ConfigValue<Integer> FOLIAR_STEEL_CU_PER_DURABILITY = YATMConfigs.s_builder.comment("The current said to be stored in each item per unit of (durability minus one).").defineInRange("chestplate_durability", 1, 1, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FOLIAR_STEEL_ENCHANTMENT_VALUE = YATMConfigs.s_builder.comment("The enchantment value.").define("enchantment_value", 15);
	public static final ConfigValue<Integer> FOLIAR_STEEL_HELMET_DEFENSE = YATMConfigs.s_builder.comment("The helmet's defense.").define("helmet_defense", 5);
	public static final ConfigValue<Integer> FOLIAR_STEEL_HELMET_DURABILITY = YATMConfigs.s_builder.comment("The helmet's durability.").define("helmet_durability", 11 * 27);
	public static final ConfigValue<Double> FOLIAR_STEEL_KNOCKBACK_RESISTANCE = YATMConfigs.s_builder.comment("The knockback resistance.").define("knockback_resistance", 0.1d);
	public static final ConfigValue<Integer> FOLIAR_STEEL_LEGGINGS_DEFENSE = YATMConfigs.s_builder.comment("The legging's defense.").define("leggings_defense", 2);
	public static final ConfigValue<Integer> FOLIAR_STEEL_LEGGINGS_DURABILITY = YATMConfigs.s_builder.comment("The legging's durability.").define("leggings_durability", 15 * 27);
	public static final ConfigValue<Double> FOLIAR_STEEL_TOUGHNESS = YATMConfigs.s_builder.comment("The armor's toughness.").define("toughness", 3d);
	
	public static final ConfigValue<Integer> SOUL_ADORNED_NETHERITE_ITEM_DAMAGE_REDUCTION = YATMConfigs.s_builder.pop().push("Soul Adorned Netherite: ").comment("The amount of damage removed from the armor piece's itemstack each repair tick.").define("item_damage_reduction_factor", 1);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_DEFENSE_FACTOR = YATMConfigs.s_builder.netheriteRelativeDefenceFactor(0.625d);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_DURABILITY_FACTOR = YATMConfigs.s_builder.netheriteRelativeDurabilityFactor(1.6d);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR = YATMConfigs.s_builder.netheriteRelativeEnchantmentFactor(2.0d);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR = YATMConfigs.s_builder.netheriteRelativeKnockbackResistanceFactor(1.0d);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_TOUGHNESS_FACTOR = YATMConfigs.s_builder.netheriteRelativeToughnessFactor(1.33d);
	public static final ConfigValue<Integer> SOUL_ADORNED_NETHERITE_SELF_REPAIR_RARITY = YATMConfigs.s_builder.comment("The number of entity ticks it takes on average for a repair tick to occur.").defineInRange("self_repair_rarity", 120, 1, Integer.MAX_VALUE);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_WEARER_HEAL_AMOUNT = YATMConfigs.s_builder.comment("The amount of health returned to the wearer each heal tick.").define("wearer_heal_amount", .002d);
	public static final ConfigValue<Integer> SOUL_ADORNED_NETHERITE_WEARER_HEAL_RARITY = YATMConfigs.s_builder.comment("The number of entity ticks it takes on average for a heal tick to occur.").defineInRange("wearer_heal_rarity", 1, 1, Integer.MAX_VALUE);
	
	
	public static final ConfigValue<Double> COMPOSTABLE_BIOREACTING_CHANCE_TO_QUANTITY = YATMConfigs.s_builder.pop().pop().push("Recipe: ").push("Dynamic: ").push("Compostables to Bioreaction: ").comment("The amount of biofluid produced from an item with a 100% compost level increase chance, is scaled to other chances.").defineInRange("chance_to_quantity", 100d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Double> COMPOSTABLE_BIOREACTING_CHANCE_TO_TICKS = YATMConfigs.s_builder.comment("The number of ticks it take to complete the recipe at default speed for an item with a 100% compost level increase chance, is scaled to other chances.").defineInRange("chance_to_ticks", 20d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Integer> COMPOSTABLE_BIOREACTING_CURRENT_COST = YATMConfigs.s_builder.comment("The amount of current consumed each tick to preform the recipe.").defineInRange("current_cost", 12, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> BLAZE_ROD_BURN_TEMPERATURE = YATMConfigs.s_builder.burnTemperature("blaze_rods", 4096);
	public static final ConfigValue<Integer> COAL_BURN_TEMPERATURE = YATMConfigs.s_builder.burnTemperature("coal", 2048);
	public static final ConfigValue<Integer> DEFAULT_BURN_TEMPERATURE = YATMConfigs.s_builder.comment("The temperature of items burning when no other temperature is given, used for woods and similar plant things.").defineInRange("default", 1408, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> SAPLING_BURN_TEMPERATURE = YATMConfigs.s_builder.burnTemperature("saplings", 512);
	public static final ConfigValue<Integer> WOOL_BURN_TEMPERATURE = YATMConfigs.s_builder.burnTemperature("wool", 496);
	
	

	public static final ForgeConfigSpec SPEC = YATMConfigs.s_builder.pop().pop().build();

} // end class