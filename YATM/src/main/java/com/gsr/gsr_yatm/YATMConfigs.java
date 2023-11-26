package com.gsr.gsr_yatm;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class YATMConfigs
{
	private static ForgeConfigSpec.Builder s_builder = new ForgeConfigSpec.Builder();
	
	
	public static final IntValue FIRE_EATER_LILY_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.push("Fire Eater Lily: ").comment("The average number of boneamealings in which one's successful.").defineInRange("bonemeal_success_rarity", 3, 0, Integer.MAX_VALUE);	
	public static final IntValue FIRE_EATER_LILY_LIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.comment("The average number of boneamealings in which one's successful for the decorative lit flower block.").defineInRange("bonemeal_success_rarity_lit_decorative", 3, 0, Integer.MAX_VALUE);
	public static final IntValue FIRE_EATER_LILY_MAX_HORIZONTAL_SPREAD = YATMConfigs.s_builder.comment("The maximum number of blocks horizontally that a canidate position will be picked out from.").defineInRange("max_horizontal_spread", 2, 0, Integer.MAX_VALUE);
	public static final IntValue FIRE_EATER_LILY_MAX_SPREAD_ATTEMPTS = YATMConfigs.s_builder.comment("The maximum number of canidate positions to consider while attempting spreading.").defineInRange("max_spread_attempts", 12, 0, Integer.MAX_VALUE);
	public static final IntValue FIRE_EATER_LILY_MAX_SPREADS = YATMConfigs.s_builder.comment("The maximum number of successful spread attempts that're allowed.").defineInRange("max_spreads", 5, 0, Integer.MAX_VALUE);
	public static final IntValue FIRE_EATER_LILY_MAX_VERTICAL_SPREAD = YATMConfigs.s_builder.comment("The maximum number of blocks vertically that a canidate position will be picked out from.").defineInRange("max_vertical_spread", 1, 0, Integer.MAX_VALUE);
	public static final IntValue FIRE_EATER_LILY_UNLIT_DECORATIVE_BONEMEAL_SUCCESS_RARITY = YATMConfigs.s_builder.comment("The average number of boneamealings in which one's successful for the decorative unlit flower block.").defineInRange("bonemeal_success_rarity_unlit_decorative", 3, 0, Integer.MAX_VALUE);
	
	
	
	public static final DoubleValue ADAMUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ore Themed Plants: ").push("Adamum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final DoubleValue ADAMUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final IntValue ADAMUM_FRUITING_RARITY = YATMConfigs.s_builder.comment("The average number of blocks matured for which one bears \"fruit\".").defineInRange("fruit_rarity", 3, 0, Integer.MAX_VALUE);	
	public static final IntValue ADAMUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time..").defineInRange("growth_rarity", 64, 0, Integer.MAX_VALUE);
	public static final IntValue ADAMUM_MIN_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at least this, inclusive.").defineInRange("min_major_age_increase", 1, 0, Integer.MAX_VALUE);
	public static final IntValue ADAMUM_MAX_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at most this, inclusive.").defineInRange("max_major_age_increase", 2, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue AURUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Aurum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 4d, 0d, Float.MAX_VALUE);
	public static final DoubleValue AURUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final IntValue AURUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue CARBUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Carbum").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 1d, 0d, Float.MAX_VALUE);
	public static final DoubleValue CARBUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final IntValue CARBUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue CUPRUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Cuprum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 2d, 0d, Float.MAX_VALUE);
	public static final DoubleValue CUPRUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final IntValue CUPRUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue FERRUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ferrum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 3.6d, 0d, Float.MAX_VALUE);
	public static final DoubleValue FERRUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final IntValue FERRUM_FRUIT_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which \"fruit\" grow once.").defineInRange("fruit_rarity", 12, 0, Integer.MAX_VALUE);
	public static final IntValue FERRUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	public static final IntValue FERRUM_MIN_FRUIT_COUNT = YATMConfigs.s_builder.comment("The average minimum number of \"fruit\" that can be harvested in one harvest.").defineInRange("min_fruit", 1, 0, Integer.MAX_VALUE);
	public static final IntValue FERRUM_MAX_FRUIT_COUNT = YATMConfigs.s_builder.comment("The average maximum number of \"fruit\" that can be harvested in one harvest.").defineInRange("max_fruit", 3, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue FOLIUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Folium: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 4d, 0d, Float.MAX_VALUE);
	public static final DoubleValue FOLIUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final IntValue FOLIUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue INFERNALUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Infernalum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 6.6d, 0d, Float.MAX_VALUE);
	public static final DoubleValue INFERNALUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	public static final IntValue INFERNALUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 64, 0, Integer.MAX_VALUE);
	public static final IntValue INFERNALUM_MIN_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at least this, inclusive.").defineInRange("min_major_age_increase", 1, 0, Integer.MAX_VALUE);
	public static final IntValue INFERNALUM_MAX_MAJOR_AGE_INCREASE = YATMConfigs.s_builder.comment("Every time the block chain grows longer the next one down will have it's major age increased by at most this, inclusive.").defineInRange("max_major_age_increase", 2, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue LAPUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Lapum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final DoubleValue LAPUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final IntValue LAPUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final ForgeConfigSpec.DoubleValue RUBERUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Ruberum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final ForgeConfigSpec.DoubleValue RUBERUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final ForgeConfigSpec.IntValue RUBERUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.IntValue RUBERUM_MAX_PARTICLES = YATMConfigs.s_builder.comment("The maximum number of particles that might be choosen to be spawned when the plant is lit.").defineInRange("max_particles", 3, 0, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.IntValue RUBERUM_MIN_PARTICLES = YATMConfigs.s_builder.comment("The minimum number of particles that might be choosen to be spawned when the plant is lit.").defineInRange("min_particles", 0, 0, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.IntValue RUBERUM_PARTICLE_RARITY = YATMConfigs.s_builder.comment("The average number of animation ticks for which particles're spawned once.").defineInRange("particle_rarity", 6, 0, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.IntValue RUBERUM_SIGNAL_FACTOR = YATMConfigs.s_builder.comment("The factor by which the redstone signal emitted's strength from walking through the block's scaled.").defineInRange("signal_factor", 2, 0, Integer.MAX_VALUE);
	public static final ForgeConfigSpec.DoubleValue RUBERUM_SIGNAL_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without causing a redstone signal.").defineInRange("signal_trigger_tolerance", .1d, 0d, Double.MAX_VALUE);
	
	public static final DoubleValue SAMARAGDUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Samaragdum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final DoubleValue SAMARAGDUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final IntValue SAMARAGDUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	public static final DoubleValue VICUM_DAMAGE_FACTOR = YATMConfigs.s_builder.pop().push("Vicum: ").comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", 0d, 0d, Float.MAX_VALUE);
	public static final DoubleValue VICUM_DAMAGE_TRIGGER_TOLERANCE = YATMConfigs.s_builder.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", Double.MAX_VALUE, 0d, Double.MAX_VALUE);
	public static final IntValue VICUM_GROWTH_RARITY = YATMConfigs.s_builder.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange("growth_rarity", 36, 0, Integer.MAX_VALUE);
	
	
	
	public static final IntValue CRUCIBLE_DRAIN_RECHECK_PERIOD  = YATMConfigs.s_builder.pop().pop().push("Devices: ").push("Crucible: ").comment("The period in ticks of the device trying to reattach a slotted result tank draining component to relavent neighbors.").defineInRange("drain_recheck_period", 40, 0, Integer.MAX_VALUE);
	public static final IntValue CRUCIBLE_MAX_FLUID_TRANSFER_RATE = YATMConfigs.s_builder.comment("The most fluid that can be moved per tick.").defineInRange("max_fluid_transfer_rate", 16, 0, Integer.MAX_VALUE);
	public static final IntValue CRUCIBLE_MAX_TEMPERATURE = YATMConfigs.s_builder.comment("The highest possible temperature for the device.").defineInRange("max_temperature", 4000, 0, Integer.MAX_VALUE);
	public static final IntValue CRUCIBLE_RESULT_TANK_CAPACITY = YATMConfigs.s_builder.comment("The result tank's fluid capacity.").defineInRange("result_tank_capacity", 4000, 0, Integer.MAX_VALUE);
	
	public static final IntValue CURRENT_FURNACE_MAX_TEMPERATURE = YATMConfigs.s_builder.pop().push("Current Furnace: ").comment("The highest possible temperature for the device.").defineInRange("max_temperature", 4000, 0, Integer.MAX_VALUE);
	
	
	public static final ForgeConfigSpec SPEC = YATMConfigs.s_builder.pop().pop().build();


	
} // end class