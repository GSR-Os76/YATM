package com.gsr.gsr_yatm.utilities.capability;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;
import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;

public class HeatUtil
{
	private static final List<Tuple2<Predicate<ItemStack>, Integer>> BURN_TEMPERATURE_REGISTRY = Lists.newArrayList();
	
	
	
	public static void addDefaultsTemperatureMappings() 
	{
		// lava bucket -----
		// block of coal -----
		// dried kelp block -----
		// blaze rod -----
		// coal -----
		// charcoal -----
		// boats
		// chest boats
		// bamboo mosaic, block/slab/stair
		// chiseled bookshelf
		// block of bamboo
		// overworld logs including stripped
		// overworld wood including striped
		// overworld planks
		// overworld slabs
		// overworld stairs
		// overworld pressure plates
		// overworld wooden button
		// overworld wooden trapdoor
		// overworld fence gate
		// overworld wooden fence
		// mangrove roots, generally aerial roots
		// ladder
		// crafting table
		// cartography table
		// fletching table
		// smithing table
		// loom
		// bookshelf
		// lectern
		// composter
		// chests
		// barrel
		// daylight sensor -----
		// jukebox
		// note block
		// banner -----
		// crossbow
		// bow
		// fishing rod
		// overworld wooden door
		// overworld sign
		// hanging sign
		// wooden pickaxe
		// wooden shovel
		// wooden hoe
		// wooden axe
		// wooden sword
		// bowl
		// saplings -----
		// stick
		// dead bush
		// azalea -----
		// wool -----
		// carpet -----
		// bamboo
		// scaffolding
		
		HeatUtil.addTemperatureMappings((i) -> i.is(Items.LAVA_BUCKET), Fluids.LAVA.getFluidType().getTemperature());
		HeatUtil.addTemperatureMappings((i) -> i.is(Tags.Items.STORAGE_BLOCKS_COAL), YATMConfigs.COAL_BURN_TEMPERATURE.get());
		HeatUtil.addTemperatureMappings((i) -> i.is(Tags.Items.RODS_BLAZE), YATMConfigs.BLAZE_ROD_BURN_TEMPERATURE.get());
		HeatUtil.addTemperatureMappings((i) -> i.is(ItemTags.COALS), YATMConfigs.COAL_BURN_TEMPERATURE.get());
		HeatUtil.addTemperatureMappings((i) -> i.is(ItemTags.BANNERS), YATMConfigs.WOOL_BURN_TEMPERATURE.get());
		HeatUtil.addTemperatureMappings((i) -> i.is(ItemTags.SAPLINGS), YATMConfigs.SAPLING_BURN_TEMPERATURE.get());
		HeatUtil.addTemperatureMappings((i) -> i.is(ItemTags.WOOL), YATMConfigs.WOOL_BURN_TEMPERATURE.get());
		HeatUtil.addTemperatureMappings((i) -> i.is(ItemTags.WOOL_CARPETS), YATMConfigs.WOOL_BURN_TEMPERATURE.get());

	} // end addDefaultsTemperatureMappings()
	
	public static void addTemperatureMappings(@NotNull Predicate<ItemStack> itemCondition, @NotNegative int temperature)
	{
		HeatUtil.BURN_TEMPERATURE_REGISTRY.add(Tuple.of(Objects.requireNonNull(itemCondition), Contract.notNegative(temperature)));
	} // end addTemperatureMappings()
	
	
	
	public static int getHeatingBurnTime(@NotNull ItemStack itemStack) 
	{
		return ForgeHooks.getBurnTime(itemStack, RecipeType.SMELTING);
	} // getHeatingBurnTime()
	
	public static int getHeatingTemperature(@NotNull ItemStack itemStack) 
	{
		Optional<Integer> i = HeatUtil.BURN_TEMPERATURE_REGISTRY.stream().filter((t) -> t.a().test(itemStack)).map((t) -> t.b()).findFirst();
		if(i.isPresent()) 
		{
			return i.get();
		}
		return YATMConfigs.DEFAULT_BURN_TEMPERATURE.get();
	} // end getHeatingTemperature()
	
} // end class