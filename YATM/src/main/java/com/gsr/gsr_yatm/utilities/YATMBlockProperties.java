package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.candle_lantern.CandleLanternBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class YATMBlockProperties
{
	
	public static final Properties EMPTY = Properties.of();
	
	// TODO, work on these, alphabetize the chain method calls
	// public static final BlockBehaviour.Properties BASE_WOOD_PROPERTIES = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
	public static final Properties AERIAL_ROOTS = Properties.of().mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS).strength(0.7f).randomTicks().sound(SoundType.MANGROVE_ROOTS).noOcclusion().isSuffocating(YATMBlockProperties::never).isViewBlocking(YATMBlockProperties::never).noOcclusion().ignitedByLava();
	public static final Properties FLOWER_POT = Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
	public static final Properties LEAF_MULCH = Properties.of().mapColor(MapColor.PODZOL).strength(0.1f).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY);
	public static final Properties LEAVES = Properties.of().mapColor(MapColor.PLANT).strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(YATMBlockProperties::ocelotOrParrot).isSuffocating(YATMBlockProperties::never).isViewBlocking(YATMBlockProperties::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(YATMBlockProperties::never);
	
	public static final Properties PARTIALLY_STRIPPED_RUBBER_WOOD = YATMBlockProperties.rubberWood().randomTicks();
	public static final Properties PARTIALLY_STRIPPED_SOUL_AFFLICTED_RUBBER_WOOD =  YATMBlockProperties.soulAfflictedRubberWood().randomTicks();
	
	
	public static final Properties RUBBER_WOOD = YATMBlockProperties.rubberWood();
	public static final Properties RUBBER_WOOD_SIGN = Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f).ignitedByLava();
	public static final Properties RUBBER_WOOD_SWITCH = Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(0.5f).sound(SoundType.WOOD).ignitedByLava();
	public static final Properties SAPLING = Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
	public static final Properties SOUL_AFFLICTED_RUBBER_WOOD = YATMBlockProperties.soulAfflictedRubberWood();
	public static final Properties SOUL_AFFLICTED_RUBBER_WOOD_SIGN = Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0f);
	public static final Properties SOUL_AFFLICTED_RUBBER_WOOD_SWITCH = Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(0.5f).sound(SoundType.WOOD);
	
	
	
	public static final Properties ADAMUM = Properties.of().mapColor(MapColor.COLOR_BLACK).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(6.0F, 1200.0F);
	public static final Properties AURUM = Properties.of().mapColor(MapColor.GOLD).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.NETHER_GOLD_ORE);
	public static final Properties BASIN_OF_TEARS = YATMBlockProperties.basinOfTears();
	public static final Properties BASIN_OF_TEARS_VEGETATIVE = YATMBlockProperties.basinOfTears().noLootTable();
	public static final Properties BLEACHED_ICE_CORAL = YATMBlockProperties.iceCoral().mapColor(DyeColor.WHITE);
	public static final Properties CANDLELILY = Properties.of().instabreak().mapColor(MapColor.COLOR_GRAY).noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.HONEY_BLOCK);
	public static final Properties CARBUM = Properties.of().mapColor(MapColor.COLOR_BLACK).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(1.0f, 4.0f);
	public static final Properties CACTUS = Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.4f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY);
	public static final Properties CARCASS_ROOT_FOLIAGE = Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final Properties CARCASS_ROOT_ROOTED_DIRT = Properties.of().mapColor(MapColor.DIRT).strength(1f).sound(SoundType.GRAVEL).randomTicks();
	public static final Properties CARCASS_ROOT_ROOTED_NETHERRACK = Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.9f).sound(SoundType.NETHERRACK);
	public static final Properties CONDUIT_VINES = Properties.of().instabreak().mapColor(MapColor.COLOR_BLUE).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.VINE);

	public static final Properties CROP = Properties.of().instabreak().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.CROP);
	public static final Properties CUPRUM = Properties.of().mapColor(MapColor.WARPED_NYLIUM).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(1.5f, 6.0f).sound(SoundType.COPPER);
	public static final Properties DWARF_PERSIMMON = Properties.of().instabreak().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.CROP);
	public static final Properties FALLEN_SHULKWART_SPORES = Properties.of().mapColor(MapColor.SAND).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final Properties FERRUM = Properties.of().mapColor(MapColor.RAW_IRON).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2.5F, 6.0F);
	public static final Properties FIRE_EATER_LILY = YATMBlockProperties.fireEaterLily();
	public static final Properties FIRE_EATER_LILY_UNLIT = YATMBlockProperties.fireEaterLily().mapColor(MapColor.COLOR_PURPLE);
	
	public static final Properties FOLIUM = Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2.75F, 6.0F);
	public static final Properties ICE_CORAL = YATMBlockProperties.iceCoral();
	public static final Properties INFERNALUM = Properties.of().mapColor(MapColor.COLOR_BLACK).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(6.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS);	
	public static final Properties LAPUM = Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(1.0f, 4.0f).sound(SoundType.CALCITE);	
	public static final Properties PHANTASMAL_SHELF_FUNGUS = Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5f).sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY).noCollission().randomTicks();
	public static final Properties PRISMARINE_CRYSTAL_MOSS = Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().replaceable().requiresCorrectToolForDrops().sound(SoundType.MOSS).strength(1.5f, 6.0f);
	public static final Properties RUBERUM = Properties.of().mapColor(MapColor.FIRE).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(2.0f, 4.0f).sound(SoundType.METAL);	
	public static final Properties SAMARAGDUM = Properties.of().mapColor(MapColor.EMERALD).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().strength(2.0f, 6.0f).sound(SoundType.METAL);
	public static final Properties SPIDER_VINE = Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();;
	public static final Properties VICUM = Properties.of().mapColor(MapColor.COLOR_BLACK).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().sound(SoundType.BASALT).strength(.75F, 4.0F);
	
	public static final Properties FOLIAR_STEEL_BLOCK = Properties.of().instrument(NoteBlockInstrument.XYLOPHONE).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(5.5F, 6.0F).sound(SoundType.METAL);
	public static final Properties RUBBER_BLOCK = Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2f).sound(SoundType.CANDLE);
	public static final Properties ROOTED_SOUL_SOIL = Properties.of().mapColor(MapColor.DIRT).strength(0.5f).sound(SoundType.ROOTED_DIRT);
	
	public static final Properties HANGING_POT_HOOK = Properties.of().instabreak().noCollission().noOcclusion().pushReaction(PushReaction.DESTROY);
	
	public static final Properties CANDLE_LANTERN = Properties.of().forceSolidOn().lightLevel((s) -> (s.getValue(CandleLanternBlock.LIT) ? 9 : 0)).mapColor(MapColor.METAL).noOcclusion().pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN);
	
	public static final Properties GRAFTING_TABLE = Properties.of().ignitedByLava().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2.5F);
	
	// TODO, this
	public static final Properties SAP_COLLECTOR = Properties.of().ignitedByLava().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(0.6f);

	// TODO, actually design these, later
	public static final Properties DATA_DEVICE = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.FROGLIGHT);
	
	public static final Properties MACHINE = Properties.of().instrument(NoteBlockInstrument.IRON_XYLOPHONE).mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(5.5f, 6.0f);
	public static final Properties SOLAR_PANEL = Properties.of().instrument(NoteBlockInstrument.PLING).mapColor(MapColor.METAL).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(3.0f);
	public static final Properties WIRE = Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2.0f);
	
	
	
	
	public static final Properties CURRENT_TUBER_BLOCK = Properties.of().mapColor(MapColor.COLOR_BLUE).sound(SoundType.BAMBOO_SAPLING).strength(1f, 4.0f);
	public static final Properties CURRENT_BATTERY_BLOCK = Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2f, 5.0f);
	public static final Properties ADVANCED_CURRENT_BATTERY_BLOCK = Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2.5f, 6.0f);
	public static final Properties STEEL_TANK = Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2f, 6.0f);

	
	public static final Properties CONDUIT_VINE_BUNDLE = Properties.of().mapColor(MapColor.COLOR_BLUE).sound(SoundType.BAMBOO_SAPLING).strength(2.0f, 3.0f);
	public static final Properties CHANNEL_VINES = Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().sound(SoundType.METAL).strength(2f, 6.0f);

	public static final Properties CREATIVE = Properties.of().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.COLOR_PURPLE).noOcclusion().pushReaction(PushReaction.BLOCK).strength(Float.MAX_VALUE);
	
	
	
	public static @NotNull Properties rubberWood()
	{
		return Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2.0f);
	} // end rubberWood()
	
	public static @NotNull Properties soulAfflictedRubberWood()
	{
		return Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2.0f);
	} // end soulAfflictedRubberWood()
	
	public static @NotNull Properties basinOfTears()
	{
		return Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.CROP).strength(0.1f);
	} // end basinOfTears()
	
	public static @NotNull Properties fireEaterLily()
	{
		return Properties.of().instabreak().mapColor(MapColor.COLOR_ORANGE).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.CROP);
	} // end fireEaterLily()
	
	public static @NotNull Properties iceCoral()
	{
		return Properties.of().mapColor(MapColor.ICE).noCollission().noOcclusion().pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.GLASS).strength(0.5f);
	} // end iceCoral()
	
	public static @NotNull Properties shulkwart(@NotNull DyeColor color)
	{
		if(color != null) 
		{
			return Properties.of().mapColor(color).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
		}
		else
		{
			return Properties.of().mapColor(MapColor.COLOR_PURPLE).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();

		}
	} // end shulkwart()
	
	public static @NotNull Properties stoneOre()
	{
		return Properties.of().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F);
	} // end stoneOre()
	
	public static @NotNull Properties deepslateOre()
	{
		return YATMBlockProperties.stoneOre().mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE);
	} // end deepslateOre()
		
	public static @NotNull Properties liquid(MapColor color) 
	{
		return Properties.of().liquid().mapColor(color).noCollission().noLootTable().pushReaction(PushReaction.DESTROY).replaceable().sound(SoundType.EMPTY).strength(100.0f);
	} // end liquid()
	
//	public static @NotNull Properties plankWith(@NotNull MapColor color)
//	{
//		return Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava();
//	} // end plankWith()
	
//	public static @NotNull Properties logWith(@NotNull MapColor topColor, @NotNull MapColor sideColor) 
//	{
//		return Properties.of().mapColor((bs) -> bs.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
//	} // end logWith()
	
	public static boolean ocelotOrParrot(@NotNull BlockState bs, @NotNull BlockGetter bg, @NotNull BlockPos bp, @NotNull EntityType<?> et) 
	{
		return et == EntityType.OCELOT || et == EntityType.PARROT;
	} // end ocelotOrParrot()
	
	public static boolean never(@NotNull BlockState bs, @NotNull BlockGetter bg, @NotNull BlockPos bp) 
	{
		return false; 
	} // end never()
	
	
	// TODO, add copy method
} // end class