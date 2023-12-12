package com.gsr.gsr_yatm;

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
	
	
	
	private static ForgeConfigSpec.Builder s_builder = new ForgeConfigSpec.Builder();
	
	// TODO, change doubles into float where a float is desired to remove unnecessary casting and code
	// TODO, don't defineInRange when the valid range is all values.
	
	public static final ConfigValue<Integer> FIRE_EATER_LILY_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.push("Plants: ").push("Fire Eater Lily: ").comment("The average number of boneamealings in which one's successful.").defineInRange("bonemeal_success_rarity", 3, 0, Integer.MAX_VALUE);	
	public static final ConfigValue<Integer> FIRE_EATER_LILY_LIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.comment("The average number of boneamealings in which one's successful for the decorative lit flower block.").defineInRange("bonemeal_success_rarity_lit_decorative", 3, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD = YATMConfigs.s_builder.comment("The maximum number of blocks horizontally that a canidate position will be picked out from.").defineInRange("max_horizontal_spread", 2, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_SPREAD_ATTEMPTS = YATMConfigs.s_builder.comment("The maximum number of canidate positions to consider while attempting spreading.").defineInRange("max_spread_attempts", 12, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_SPREADS = YATMConfigs.s_builder.comment("The maximum number of successful spread attempts that're allowed.").defineInRange("max_spreads", 5, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_MAX_VERTICAL_SPREAD = YATMConfigs.s_builder.comment("The maximum number of blocks vertically that a canidate position will be picked out from.").defineInRange("max_vertical_spread", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FIRE_EATER_LILY_UNLIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.comment("The average number of boneamealings in which one's successful for the decorative unlit flower block.").defineInRange("bonemeal_success_rarity_unlit_decorative", 3, 0, Integer.MAX_VALUE);
	
	
	
	public static final ConfigValue<Double> ADAMUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ore Themed Plants: ").push("Adamum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> ADAMUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> ADAMUM_FRUITING_RARITY = YATMConfigs.s_builder.comment("The average number of blocks matured for which one bears \"fruit\".").defineInRange("fruit_rarity", 3, 0, Integer.MAX_VALUE);	
	public static final ConfigValue<Integer> ADAMUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time..").defineInRange("growth_rarity", 64, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> ADAMUM_MIN_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at least this, inclusive.").defineInRange("min_major_age_increase", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> ADAMUM_MAX_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at most this, inclusive.").defineInRange("max_major_age_increase", 2, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> AURUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Aurum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 4d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> AURUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> AURUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> CARBUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Carbum").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 1d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> CARBUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> CARBUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> CUPRUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Cuprum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 2d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> CUPRUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> CUPRUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> FERRUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ferrum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 3.6d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> FERRUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> FERRUM_FRUIT_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which \"fruit\" grow once.").defineInRange("fruit_rarity", 12, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FERRUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FERRUM_MIN_FRUIT_COUNT = YATMConfigs.s_builder.comment("The average minimum number of \"fruit\" that can be harvested in one harvest.").defineInRange("min_fruit", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> FERRUM_MAX_FRUIT_COUNT = YATMConfigs.s_builder.comment("The average maximum number of \"fruit\" that can be harvested in one harvest.").defineInRange("max_fruit", 3, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> FOLIUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Folium: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 4d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> FOLIUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> FOLIUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> INFERNALUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Infernalum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 6.6d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> INFERNALUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> INFERNALUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 64, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> INFERNALUM_MIN_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at least this, inclusive.").defineInRange("min_major_age_increase", 1, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> INFERNALUM_MAX_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at most this, inclusive.").defineInRange("max_major_age_increase", 2, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> LAPUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Lapum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> LAPUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> LAPUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> RUBERUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ruberum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> RUBERUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_MAX_PARTICLES = YATMConfigs.s_builder.comment("The maximum number of particles that might be choosen to be spawned when the plant is lit.").defineInRange("max_particles", 3, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_MIN_PARTICLES = YATMConfigs.s_builder.comment("The minimum number of particles that might be choosen to be spawned when the plant is lit.").defineInRange("min_particles", 0, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_PARTICLE_RARITY = YATMConfigs.s_builder.comment("The average number of animation ticks for which particles're spawned once.").defineInRange("particle_rarity", 6, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> RUBERUM_SIGNAL_FACTOR = YATMConfigs.s_builder.comment("The factor by which the redstone signal emitted's strength from walking through the block's scaled.").defineInRange("signal_factor", 2, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Double> RUBERUM_SIGNAL_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without causing a redstone signal.").defineInRange("signal_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	
	public static final ConfigValue<Double> SAMARAGDUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Samaragdum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> SAMARAGDUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> SAMARAGDUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Double> VICUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Vicum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final ConfigValue<Double> VICUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Integer> VICUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Boolean> IS_HORIZONTAL_GROWRTH_UNBOUND = YATMConfigs.s_builder.pop().pop().push("Misc: ").comment("Determines if plants limit their ability to propagate horizontally.").define("is_horizontal_growth_unbound", false);
	
	
	public static final ConfigValue<Integer> BIOREACTOR_CURRENT_CAPACITY = YATMConfigs.s_builder.pop().pop().push("Devices: ").push("Bioreactor: ").comment("The maximum number of cu the device can hold.").defineInRange("current_capacity", YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BIOREACTOR_DRAIN_RECHECK_PERIOD = YATMConfigs.s_builder.comment("The period in ticks of the device trying to reattach a slotted result tank draining component to relavent neighbors.").defineInRange("drain_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BIOREACTOR_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.comment("The most current that can be moved per tick.").defineInRange("max_current_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BIOREACTOR_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BIOREACTOR_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.comment("The result tank's fluid capacity.").defineInRange("result_tank_capacity", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> BOILER_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.pop().push("Boiler: ").comment("The most fluid that can be moved per tick while drain out it's input tank.").defineInRange("drain_input_max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_DRAIN_INPUT_RECHECK_PERIOD = YATMConfigs.s_builder.comment("The period in ticks of the device trying to reattach a slotted input tank draining component to relavent neighbors.").defineInRange("drain_input_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick while drain out it's result tank.").defineInRange("drain_result_max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_DRAIN_RESULT_RECHECK_PERIOD = YATMConfigs.s_builder.comment("The period in ticks of the device trying to reattach a slotted result tank draining component to relavent neighbors.").defineInRange("drain_result_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_FILL_INPUT_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick while filling up it's input taking.").defineInRange("fill_input_max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_INPUT_TANK_CAPACITY = YATMConfigs.s_builder.comment("The input tank's fluid capacity.").defineInRange("input_tank_capacity", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_LIT_ABOVE_TEMPERATURE = YATMConfigs.s_builder.comment("The temperature above which the device will be switched to the lit state.").defineInRange("lit_above", YATMConfigs.LIT_ABOVE_TEMPERATURE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_MAX_TEMPERATURE = YATMConfigs.s_builder.comment("The highest possible temperature for the device.").defineInRange("max_temperature", YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> BOILER_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.comment("The result tank's fluid capacity.").defineInRange("result_tank_capacity", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> CRUCIBLE_DRAIN_RECHECK_PERIOD = YATMConfigs.s_builder.pop().push("Crucible: ").comment("The period in ticks of the device trying to reattach a slotted result tank draining component to relavent neighbors.").defineInRange("drain_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRUCIBLE_LIT_ABOVE_TEMPERATURE = YATMConfigs.s_builder.comment("The temperature above which the device will be switched to the lit state.").defineInRange("lit_above", YATMConfigs.LIT_ABOVE_TEMPERATURE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRUCIBLE_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRUCIBLE_MAX_TEMPERATURE = YATMConfigs.s_builder.comment("The highest possible temperature for the device.").defineInRange("max_temperature", YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRUCIBLE_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.comment("The result tank's fluid capacity.").defineInRange("result_tank_capacity", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> CRYSTALLIZER_CURRENT_CAPACITY  = YATMConfigs.s_builder.pop().push("Crystallizer: ").comment("The maximum number of cu the device can hold.").defineInRange("current_capacity", YATMConfigs.STEEL_DEVICE_CURRENT_CAPACITY, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRYSTALLIZER_DRAIN_RECHECK_PERIOD = YATMConfigs.s_builder.comment("The period in ticks of the device trying to reattach a slotted result tank draining component to relavent neighbors.").defineInRange("drain_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRYSTALLIZER_DRAIN_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("drain_max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRYSTALLIZER_FILL_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("fill_max_fluid_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_FLUID_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRYSTALLIZER_INPUT_TANK_CAPACITY = YATMConfigs.s_builder.comment("The input tank's fluid capacity.").defineInRange("input_tank_capacity", YATMConfigs.STEEL_DEVICE_TANK_CAPACITY, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CRYSTALLIZER_MAX_CURRENT_TRANSFER = YATMConfigs.s_builder.comment("The most current that can be moved per tick.").defineInRange("max_current_transfer_rate", YATMConfigs.STEEL_DEVICE_MAX_CURRENT_TRANSFER_RATE, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> CURRENT_FURNACE_MAX_TEMPERATURE = YATMConfigs.s_builder.pop().push("Current Furnace: ").comment("The highest possible temperature for the device.").defineInRange("max_temperature", YATMConfigs.STEEL_DEVICE_MAX_TEMPERATURE, 0, Integer.MAX_VALUE);
	
	
	
	public static final ConfigValue<Integer> TANK_CAPACITY = YATMConfigs.s_builder.pop().push("Tank: ").comment("The tank's capacity in milibuckets.").defineInRange("capacity", 16000, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> TANK_DRAIN_RECHECK_PERIOD = YATMConfigs.s_builder.comment("The period in ticks of the device trying to attach to a below neighbor's fluid handling capability.").defineInRange("drain_recheck_period", YATMConfigs.DRAIN_RECHECK_PERIOD, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> TANK_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("max_fluid_transfer_rate", 1000, 0, Integer.MAX_VALUE);
	
	public static final ConfigValue<Integer> CRAFTING_RECHECK_PERIOD = YATMConfigs.s_builder.pop().push("Misc: ").comment("The period in ticks while idle before a crafting device'll try to match it's contents to a new recipe. Note: recheck's occur automatically when device inventories change.").defineInRange("crafting recheck period", 20, 0, Integer.MAX_VALUE);
	
	
	
	public static final ConfigValue<Integer> EMBER_GLAND_HEAT = YATMConfigs.s_builder.pop().pop().push("Components: ").push("Current Heaters: ").push("Ember Gland: ").comment("The highest temperature the component can attain in kelvin.").defineInRange("heat", 1000, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Double> EMBER_GLAND_KELVIN_PER_CURRENT = YATMConfigs.s_builder.comment("The number kelvin produceable max from a single cu.").defineInRange("kelvin_per_cu", 12d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Integer> FLAME_GLAND_HEAT = YATMConfigs.s_builder.pop().push("Flame Gland: ").comment("The highest temperature the component can attain in kelvin.").defineInRange("heat", 2000, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Double> FLAME_GLAND_KELVIN_PER_CURRENT = YATMConfigs.s_builder.comment("The number kelvin produceable max from a single cu.").defineInRange("kelvin_per_cu", 16d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Integer> TORCH_GLAND_HEAT = YATMConfigs.s_builder.pop().push("Torch Gland: ").comment("The highest temperature the component can attain in kelvin.").defineInRange("heat", 4000, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Double> TORCH_GLAND_KELVIN_PER_CURRENT = YATMConfigs.s_builder.comment("The number kelvin produceable max from a single cu.").defineInRange("kelvin_per_cu", 20d, Double.MIN_VALUE, Double.MAX_VALUE);
	
	public static final ConfigValue<Integer> CURRENT_TUBER = YATMConfigs.s_builder.pop().pop().push("Current Storers: ").push("Current Tuber: ").comment("The item capacity in cu.").defineInRange("capacity", 32768, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> CURRENT_BATTERY = YATMConfigs.s_builder.pop().push("Current Battery: ").comment("The item capacity in cu.").defineInRange("capacity", 98304, 0, Integer.MAX_VALUE);
	public static final ConfigValue<Integer> ADVANCED_CURRENT_BATTERY = YATMConfigs.s_builder.pop().push("Advanced Current Battery: ").comment("The item capacity in cu.").defineInRange("capacity", 393216, 0, Integer.MAX_VALUE);

	
	
	public static final ConfigValue<Integer> DECAY_NETHERITE_ITEM_DAMAGE_REDUCTION_FACTOR = YATMConfigs.s_builder.pop().pop().pop().push("Armor: ").push("Decay Netherite: ").comment("The amount of damage removed from the armor piece's itemstack per unit of wither damage that the wearer recieves.").define("repair_damage_reduction_factor", 9);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_DEFENSE_FACTOR = YATMConfigs.s_builder.comment("The proportion of defence the armor pieces have relative to the corresponding Netherite armor piece.").defineInRange("netherite_relative_defense_factor", 0.89d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_DURABILITY_FACTOR = YATMConfigs.s_builder.comment("The proportion of durability the armor pieces have relative to the corresponding Netherite armor piece.").defineInRange("netherite_relative_durability_factor", 0.5d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR = YATMConfigs.s_builder.comment("The proportion of unchantability the armor has relative to Netherite armor.").defineInRange("netherite_relative_enchantment_factor", 1.0d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR = YATMConfigs.s_builder.comment("The proportion of knockback resistance which the armor has relative to Netherite armor.").defineInRange("netherite_relative_knockback_resistance_factor", 1.0d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> DECAY_NETHERITE_NETHERITE_RELATIVE_TOUGHNESS_FACTOR = YATMConfigs.s_builder.comment("The proportion of toughness the armor has relative to Netherite armor.").defineInRange("netherite_relative_toughness_factor", 0.72d, Double.MIN_VALUE, Double.MAX_VALUE);
	
	public static final ConfigValue<Integer> FOLIAR_STEEL_BOOTS_DEFENSE = YATMConfigs.s_builder.pop().push("Foliar Steel: ").comment("The boot's defense.").define("boots_defense", 2);
	public static final ConfigValue<Integer> FOLIAR_STEEL_BOOTS_DURABILITY = YATMConfigs.s_builder.comment("The boot's durability.").define("boots_durability", 13 * 27);
	public static final ConfigValue<Integer> FOLIAR_STEEL_CHESTPLATE_DEFENSE = YATMConfigs.s_builder.comment("The chestplate's defense.").define("chestplate_defense", 6);
	public static final ConfigValue<Integer> FOLIAR_STEEL_CHESTPLATE_DURABILITY = YATMConfigs.s_builder.comment("The chestplate's durability.").define("chestplate_durability", 16 * 27);
	public static final ConfigValue<Integer> FOLIAR_STEEL_ENCHANTMENT_VALUE = YATMConfigs.s_builder.comment("The enchantment value.").define("enchantment_value", 15);
	public static final ConfigValue<Integer> FOLIAR_STEEL_HELMET_DEFENSE = YATMConfigs.s_builder.comment("The helmet's defense.").define("helmet_defense", 5);
	public static final ConfigValue<Integer> FOLIAR_STEEL_HELMET_DURABILITY = YATMConfigs.s_builder.comment("The helmet's durability.").define("helmet_durability", 11 * 27);
	public static final ConfigValue<Float> FOLIAR_STEEL_KNOCKBACK_RESISTANCE = YATMConfigs.s_builder.comment("The knockback resistance.").define("knockback_resistance", 0.1f);
	public static final ConfigValue<Integer> FOLIAR_STEEL_LEGGINGS_DEFENSE = YATMConfigs.s_builder.comment("The legging's defense.").define("leggings_defense", 2);
	public static final ConfigValue<Integer> FOLIAR_STEEL_LEGGINGS_DURABILITY = YATMConfigs.s_builder.comment("The legging's durability.").define("leggings_durability", 15 * 27);
	public static final ConfigValue<Float> FOLIAR_STEEL_TOUGHNESS = YATMConfigs.s_builder.comment("The armor's toughness.").define("toughness", 3f);
	
	public static final ConfigValue<Integer> SOUL_ADORNED_NETHERITE_ITEM_DAMAGE_REDUCTION = YATMConfigs.s_builder.pop().push("Soul Adorned Netherite: ").comment("The amount of damage removed from the armor piece's itemstack each repair tick.").define("repair_damage_reduction", 1);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_DEFENSE_FACTOR = YATMConfigs.s_builder.comment("The proportion of defence the armor pieces have relative to the corresponding Netherite armor piece.").defineInRange("netherite_relative_defense_factor", 0.625d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_DURABILITY_FACTOR = YATMConfigs.s_builder.comment("The proportion of durability the armor pieces have relative to the corresponding Netherite armor piece.").defineInRange("netherite_relative_durability_factor", 1.6d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_ENCHANTMENT_FACTOR = YATMConfigs.s_builder.comment("The proportion of unchantability the armor has relative to Netherite armor.").defineInRange("netherite_relative_enchantment_factor", 2.0d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_KNOCKBACK_RESISTANCE_FACTOR = YATMConfigs.s_builder.comment("The proportion of knockback resistance which the armor has relative to Netherite armor.").defineInRange("netherite_relative_knockback_resistance_factor", 1.0d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Double> SOUL_ADORNED_NETHERITE_NETHERITE_RELATIVE_TOUGHNESS_FACTOR = YATMConfigs.s_builder.comment("The proportion of toughness the armor has relative to Netherite armor.").defineInRange("netherite_relative_toughness_factor", 1.33d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Integer> SOUL_ADORNED_NETHERITE_SELF_REPAIR_RARITY = YATMConfigs.s_builder.comment("The number of entity ticks it takes on average for a repair tick to occur.").defineInRange("self_repair_rarity", 120, 1, Integer.MAX_VALUE);
	public static final ConfigValue<Float> SOUL_ADORNED_NETHERITE_WEARER_HEAL_AMOUNT = YATMConfigs.s_builder.comment("The amount of health returned to the wearer each heal tick.").define("wearer_heal_amount", .002f);
	public static final ConfigValue<Integer> SOUL_ADORNED_NETHERITE_WEARER_HEAL_RARITY = YATMConfigs.s_builder.comment("The number of entity ticks it takes on average for a heal tick to occur.").defineInRange("wearer_heal_rarity", 1, 1, Integer.MAX_VALUE);
	
	
	public static final ConfigValue<Double> COMPOSTABLE_BIOREACTING_CHANCE_TO_QUANTITY = YATMConfigs.s_builder.pop().pop().push("Recipe: ").push("Dynamic: ").push("Compostables to Bioreaction: ").comment("The amount of biofluid produced from an item with a 100% compost level increase chance, is scaled to other chances.").defineInRange("chance_to_quantity", 100d, 0d, Double.MAX_VALUE);
	public static final ConfigValue<Double> COMPOSTABLE_BIOREACTING_CHANCE_TO_TICKS = YATMConfigs.s_builder.comment("The number of ticks it take to complete the recipe at default speed for an item with a 100% compost level increase chance, is scaled to other chances.").defineInRange("chance_to_ticks", 20d, Double.MIN_VALUE, Double.MAX_VALUE);
	public static final ConfigValue<Integer> COMPOSTABLE_BIOREACTING_CURRENT_COST = YATMConfigs.s_builder.comment("The amount of current consumed each tick to preform the recipe.").defineInRange("current_cost", 12, 0, Integer.MAX_VALUE);
	
	
	
	public static final ForgeConfigSpec SPEC = YATMConfigs.s_builder.pop().pop().pop().build();

} // end class