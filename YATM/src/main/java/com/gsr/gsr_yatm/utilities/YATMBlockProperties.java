package com.gsr.gsr_yatm.utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class YATMBlockProperties
{
	
	public static final Properties EMPTY = Properties.of();
	
	// TODO, work on these
	// public static final BlockBehaviour.Properties BASE_WOOD_PROPERTIES = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
	public static final Properties AERIAL_ROOTS_PROPERTIES = Properties.of().mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS).strength(0.7F).randomTicks().sound(SoundType.MANGROVE_ROOTS).noOcclusion().isSuffocating(YATMBlockProperties::never).isViewBlocking(YATMBlockProperties::never).noOcclusion().ignitedByLava();
	public static final Properties FLOWER_POT_PROPERTIES = Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
	public static final Properties LEAF_MULCH_PROPERTIES = Properties.of().mapColor(MapColor.PODZOL).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY);
	public static final Properties LEAVES_PROPERTIES = Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(YATMBlockProperties::ocelotOrParrot).isSuffocating(YATMBlockProperties::never).isViewBlocking(YATMBlockProperties::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(YATMBlockProperties::never);
	
	public static final Properties PARTIALLY_STRIPPED_RUBBER_WOOD_PROPERTIES = YATMBlockProperties.rubberWoodProperties().randomTicks();
	public static final Properties PARTIALLY_STRIPPED_SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES =  YATMBlockProperties.soulAfflictedRubberWoodProperties().randomTicks();
	
	
	public static final Properties RUBBER_WOOD_PROPERTIES = YATMBlockProperties.rubberWoodProperties();
	public static final Properties RUBBER_WOOD_SIGN = Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava();
	public static final Properties RUBBER_WOOD_SWITCH_PROPERTIES = Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(0.5F).sound(SoundType.WOOD).ignitedByLava();
	public static final Properties SAPLING_PROPERTIES = Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
	public static final Properties SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES = YATMBlockProperties.soulAfflictedRubberWoodProperties();
	public static final Properties SOUL_AFFLICTED_RUBBER_WOOD_SIGN = Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F);
	public static final Properties SOUL_AFFLICTED_RUBBER_WOOD_SWITCH_PROPERTIES = Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(0.5F).sound(SoundType.WOOD);
	
	
	
	public static final Properties AURUM_SP = Properties.of().mapColor(MapColor.GOLD).sound(SoundType.NETHER_GOLD_ORE).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().strength(3.0F, 6.0F).noCollission().randomTicks();
	
	public static final Properties CARCASS_ROOT_FOLIAGE = Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final Properties CARCASS_ROOT_ROOTED_DIRT = Properties.of().mapColor(MapColor.DIRT).strength(1F).sound(SoundType.GRAVEL).randomTicks();
	public static final Properties CARCASS_ROOT_ROOTED_NETHERRACK = Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.9F).sound(SoundType.NETHERRACK);
	
	public static final Properties PHANTASMAL_SHELF_FUNGUS_PROPERTIES = Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY).noCollission().randomTicks();
	public static final Properties CROP = Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final Properties PRISMARINE_CRYSTAL_MOSS = Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY).replaceable()/* .requiresCorrectToolForDrops() */.strength(1.5F, 6.0F).noCollission().randomTicks();
	public static final Properties FALLEN_SHULKWART_SPORES = Properties.of().mapColor(MapColor.SAND).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final Properties SPIDER_VINE = Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();;

	public static final Properties CACTUS = Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.4F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY);
	
	public static final Properties HANGING_POT_HOOK = Properties.of().instabreak().noCollission().noOcclusion().pushReaction(PushReaction.DESTROY);
	
	
	public static final Properties RUBBER_BLOCK_PROPERTIES = Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2f).sound(SoundType.CANDLE);
	public static final Properties ROOTED_SOUL_SOIL_PROPERTIES = Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.ROOTED_DIRT);
	
	// TODO, this
	public static final Properties SAP_COLLECTOR = YATMBlockProperties.EMPTY;

	// TODO, actually design these
	public static final Properties DATA_DEVICE_PROPERTIES = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.FROGLIGHT);
	
	public static final Properties STEEL_MACHINE_PROPERTIES = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(6.0F, 8.0F).sound(SoundType.METAL);
	public static final Properties SOLAR_PANEL_PROPERTIES = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.METAL);
	public static final Properties WIRE_PROPERTIES = Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0f).sound(SoundType.METAL);
	public static final Properties STEEL_PIPE_PROPERTIES = Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0f).sound(SoundType.METAL);
	
	// TODO, make color customizable by method
	public static final Properties LIQUID_PROPERTIES = Properties.of()/* .mapColor(MapColor.WATER) */.replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY);

	
	
	
	
	
	public static Properties rubberWoodProperties()
	{
		return Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
	} // end rubberWoodProperties;
	
	public static Properties soulAfflictedRubberWoodProperties()
	{
		return Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD);
	} // end rubberWoodProperties;
	
	
	
	public static Properties shulkwart(DyeColor color)
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
	
	
	
	public static Properties plankPropertiesWith(MapColor color)
	{
		return Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava();
	} // end plankPropertiesWith
	
	public static Properties logPropertiesWith(MapColor topColor, MapColor sideColor) 
	{
		return Properties.of().mapColor((bs) -> bs.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
	} // end logPropertiesWith()
	
	public static boolean ocelotOrParrot(BlockState bs, BlockGetter bg, BlockPos bp, EntityType<?> et) 
	{
		return et == EntityType.OCELOT || et == EntityType.PARROT;
	} // end ocelotOrParrot()
	
	public static boolean never(BlockState bs, BlockGetter bg, BlockPos bp) 
	{
		return false; 
	} // end never()
	
	
	// TODO, add copy method
} // end class