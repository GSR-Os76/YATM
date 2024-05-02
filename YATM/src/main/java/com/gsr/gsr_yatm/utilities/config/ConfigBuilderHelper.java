package com.gsr.gsr_yatm.utilities.config;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import com.gsr.gsr_yatm.block.device.solar.SolarPanelSettings;
import com.gsr.gsr_yatm.utilities.PrimitiveUtil;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigBuilderHelper extends ForgeConfigSpec.Builder
{
	
	@Override
	public @NotNull ConfigBuilderHelper pop()
	{
		 super.pop();
		 return this;
	} // end pop()

	@Override
	public @NotNull ConfigBuilderHelper pop(int count)
	{
		super.pop(count);
		return this;
	} // end pop()
	
	
	
	@Override
	public @NotNull ConfigBuilderHelper push(String path)
	{
		super.push(path);
		return this;
	} // end push()

	@Override
	public @NotNull ConfigBuilderHelper push(List<String> path)
	{
		super.push(path);
		return this;
	} // end push()

	
	
	public @NotNull ConfigValue<Integer> bonemealSuccessRarity()
	{
		return this.bonemealSuccessRarity("bonemeal_success_rarity", 3);
	} // end bonemealSuccessRarity
	
	public @NotNull ConfigValue<Integer> bonemealSuccessRarity(@NotNull String key)
	{
		return this.bonemealSuccessRarity(key, 3);
	} // end bonemealSuccessRarity
	
	public @NotNull ConfigValue<Integer> bonemealSuccessRarity(@NotNegative int rarity)
	{
		return this.bonemealSuccessRarity("bonemeal_success_rarity", rarity);
	} // end bonemealSuccessRarity
	
	public @NotNull ConfigValue<Integer> bonemealSuccessRarity(@NotNull String key, @NotNegative int rarity)
	{
		return this.comment("The average number of boneamealings in which one's successful.").defineInRange(key, rarity, 0, Integer.MAX_VALUE);
	} // end bonemealSuccessRarity
	
	
	
	public @NotNull ConfigValue<Integer> growthRarity(@NotNegative int rarity)
	{
		return this.growthRarity("growth_rarity", rarity);
	} // end growthRarity()
	
	public @NotNull ConfigValue<Integer> growthRarity(@NotNull String key, @NotNegative int rarity)
	{
		return this.comment("The average number of random ticks for which the plant increases in age one time.").defineInRange(key, rarity, 0, Integer.MAX_VALUE);
	} // end growthRarity()
	
	
	
	public @NotNull ConfigValue<Integer> minimumLightLevel()
	{
		return this.comment("The minimum light level at which the plant'll grow.").defineInRange("minimum_light_level", 9, 0, 15);
	} // end minimumLightLevel()
	
	
	
	public @NotNull ConfigValue<Double> damageFactor(@NotNegative double factor)
	{
		// TODO, maybe should allow negative values.
		return this.comment("The factor by which the damage done by walking through the block's scaled.").defineInRange("damage_factor", factor, 0d, Double.MAX_VALUE);
	} // end damageFactor()
	
	public @NotNull ConfigValue<Double> damageTriggerTolerance()
	{
		return this.damageTriggerTolerance(.1d);
	} // end damageTriggerTolerance()
	
	public @NotNull ConfigValue<Double> damageTriggerTolerance(@NotNegative double tolerance)
	{
		return this.comment("The maximum speed an entity can be moving while inside the block without taking damage.").defineInRange("damage_trigger_tolerance", tolerance, 0d, Double.MAX_VALUE);
	} // end damageTriggerTolerance()
	
	
	
	public @NotNull ConfigValue<Integer> currentCapacity(@NotNegative int capacity)
	{
		return this.comment("The maximum number of Cu that can be held.").defineInRange("current_capacity", capacity, 0, Integer.MAX_VALUE);
	} // end currentCapacity()
	
	
	
	public @NotNull ConfigValue<Integer> maxCurrentTransferRate(@NotNegative int rate)
	{
		return this.comment("The most current that can be moved per tick.").defineInRange("max_current_transfer_rate", rate, 0, Integer.MAX_VALUE);

	} // end maxCurrentTransferRate()
	
	
	
	public @NotNull ConfigValue<Integer> burnTemperature(@NotNull String name, @NotNegative int temperature)
	{
		return this.comment("The burn temperature for " + name + ".").defineInRange(name, temperature, 0, Integer.MAX_VALUE);
	} // end burnTemperature() 
	
	public @NotNull ConfigValue<Integer> litAboveTemperature(@Range(from = -1, to = Integer.MAX_VALUE) int temperature)
	{
		return this.comment("The temperature above which the device will be switched to the lit state.").defineInRange("lit_above", temperature, -1, Integer.MAX_VALUE);
	} // end litAboveTemperature()
	
	public @NotNull ConfigValue<Integer> maxTemperature(@NotNegative int temperature)
	{
		return this.comment("The highest possible temperature that is attainable.").defineInRange("max_temperature", temperature, 0, Integer.MAX_VALUE);
	} // end maxTemperature()
	
	
	
	public @NotNull ConfigValue<Integer> tankCapacity(@NotNull String tankName, @NotNegative int millibuckets)
	{
		return this.comment("The " + tankName + " tank's fluid capacity.").defineInRange(tankName + "_tank_capacity", millibuckets, 0, Integer.MAX_VALUE);
	} // end tankCapacity()
	
	public @NotNull ConfigValue<Integer> maxDrainTankRate(@NotNull String tankName, @NotNegative int millibuckets)
	{
		return this.comment("The most fluid that can be moved per tick while draining the device's " + tankName + " tank.").defineInRange("drain_" + tankName + "_max_fluid_transfer_rate", millibuckets, 0, Integer.MAX_VALUE);
	} // end maxDrainTankRate()	
	
	public @NotNull ConfigValue<Integer> maxFillTankRate(@NotNull String tankName, @NotNegative int millibuckets)
	{
		return this.comment("The most fluid that can be moved per tick while filling up it's " + tankName + " tank.").defineInRange("fill_" + tankName + "_max_fluid_transfer_rate", millibuckets, 0, Integer.MAX_VALUE);
	} // end maxFillTankRate()	
	
	
	
	public Supplier<SolarPanelSettings> solarPanelSettings(@NotNegative int outputRecheckPeriod, @NotNegative int maxCurrentTransfer, @NotNegative int currentCapacity, int currentPerTick, float percentWithoutSky, float percentDuringDay, float percentDuringNight, float percentDuringPrecipitation)
	{
		ConfigValue<Integer> orp = this.outputComponentRecheckPeriod("drain current", outputRecheckPeriod);
		ConfigValue<Integer> mct = this.maxCurrentTransferRate(maxCurrentTransfer);
		ConfigValue<Integer> cp = this.currentCapacity(currentCapacity);
		
		ConfigValue<Integer> cpt = this.comment("The base current generation rate.").defineInRange("current_per_tick", currentPerTick, 0, Integer.MAX_VALUE);
		// TODO, make float after next update, same often elsewhere.
		ConfigValue<Double> pws = this.comment("The percentage of the base current generation when the sky's obscured above the panel, stacks with all other conditions.").defineInRange("percentage_without_sky", percentWithoutSky, 0d, Double.MAX_VALUE);
		ConfigValue<Double> pwd = this.comment("The percentage of the base current generation daytime, stacks with all other conditions.").defineInRange("percentage_during_day", percentDuringDay, 0d, Double.MAX_VALUE);
		ConfigValue<Double> pwn = this.comment("The percentage of the base current generation during nightime, stacks with all other conditions.").defineInRange("percentage_during_night", percentDuringNight, 0d, Double.MAX_VALUE);
		ConfigValue<Double> pwp = this.comment("The percentage of the base current generation during precipitating, stacks with all other conditions.").defineInRange("percentage_during_precipitation", percentDuringPrecipitation, 0d, Double.MAX_VALUE);
		
		return () -> 
		new SolarPanelSettings
		(
			orp.get(),
			mct.get(),
			cp.get(),
			cpt.get(),
			PrimitiveUtil.toFloatSupplier(pws).get(),
			PrimitiveUtil.toFloatSupplier(pwd).get(),
			PrimitiveUtil.toFloatSupplier(pwn).get(),
			PrimitiveUtil.toFloatSupplier(pwp).get()
		);
	} // end solarPanelSettings
	
	
	
	public @NotNull ConfigValue<Double> kelvinPerCu(double kPCu)
	{
		return this.comment("The number kelvin produceable max from a single cu.").define("kelvin_per_cu", kPCu);
	} // end kelvinPerCu()
	
	
	
	
	public @NotNull ConfigValue<Double> netheriteRelativeDefenceFactor(double f)
	{
		return this.comment("The proportion of defence the armor pieces have relative to the corresponding Netherite armor piece.").define("netherite_relative_defense_factor", f);
	} // end netheriteRelativeDefenceFactor()
	
	public @NotNull ConfigValue<Double> netheriteRelativeDurabilityFactor(double f)
	{
		return this.comment("The proportion of durability the armor pieces have relative to the corresponding Netherite armor piece.").define("netherite_relative_durability_factor", f);
	} // end netheriteRelativeDurabilityFactor()
	
	public @NotNull ConfigValue<Double> netheriteRelativeEnchantmentFactor(double f)
	{
		return this.comment("The proportion of enchantability the armor has relative to Netherite armor.").define("netherite_relative_enchantment_factor", f);
	} // end netheriteRelativeEnchantmentFactor()
	
	public @NotNull ConfigValue<Double> netheriteRelativeKnockbackResistanceFactor(double f)
	{
		return this.comment("The proportion of knockback resistance which the armor has relative to Netherite armor.").define("netherite_relative_knockback_resistance_factor", f);
	} // end netheriteRelativeKnockbackResistanceFactor()
	
	public @NotNull ConfigValue<Double> netheriteRelativeToughnessFactor(double f)
	{
		return this.comment("The proportion of toughness the armor has relative to Netherite armor.").define("netherite_relative_toughness_factor", f);
	} // end netheriteRelativeToughnessFactor()
	
	
	
	public @NotNull ConfigValue<Integer> outputComponentRecheckPeriod(@NotNull String name, @NotNegative int period)
	{
		return this.comment("The period in ticks of the device trying to reattach a slotted " + name + " draining component to relavent neighbors.").defineInRange("drain_" + name.replace(" ", "_") + "_recheck_period", period, 0, Integer.MAX_VALUE);
	} // end outputComponentRecheckPeriod()
	
	public @NotNull ConfigValue<Integer> outputComponentRecheckPeriod(@NotNull String name, @NotNull String key, @NotNegative int period)
	{
		return this.comment("The period in ticks of the device trying to reattach a slotted " + name + " draining component to relavent neighbors.").defineInRange("drain_" + key + "_recheck_period", period, 0, Integer.MAX_VALUE);
	} // end outputComponentRecheckPeriod()
	
	
	
	public @NotNull ConfigValue<Integer> maxParticles(@NotNegative int particles)
	{
		return this.comment("The maximum number of particles that might be spawned.").defineInRange("max_particles", particles, 0, Integer.MAX_VALUE);
	} // end maxParticles()
	
	public @NotNull ConfigValue<Integer> minParticles(@NotNegative int particles)
	{
		return this.comment("The minimum number of particles that might be spawned.").defineInRange("min_particles", particles, 0, Integer.MAX_VALUE);
	} // end minParticles()

	
	
	
	
} // end class