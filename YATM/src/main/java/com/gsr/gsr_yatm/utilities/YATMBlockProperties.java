package com.gsr.gsr_yatm.utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class YATMBlockProperties
{
	
	public static final BlockBehaviour.Properties TEMP = BlockBehaviour.Properties.of();
	
	// TODO, work on these
	// public static final BlockBehaviour.Properties BASE_WOOD_PROPERTIES = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
	public static final BlockBehaviour.Properties AERIAL_ROOTS_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS).strength(0.7F).randomTicks().sound(SoundType.MANGROVE_ROOTS).noOcclusion().isSuffocating(YATMBlockProperties::never).isViewBlocking(YATMBlockProperties::never).noOcclusion().ignitedByLava();
	public static final BlockBehaviour.Properties FLOWER_POT_PROPERTIES = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties LEAF_MULCH_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.1F).sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties LEAVES_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(YATMBlockProperties::ocelotOrParrot).isSuffocating(YATMBlockProperties::never).isViewBlocking(YATMBlockProperties::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(YATMBlockProperties::never);
	public static final BlockBehaviour.Properties RUBBER_WOOD_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
	public static final BlockBehaviour.Properties RUBBER_WOOD_SIGN = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava();
	public static final BlockBehaviour.Properties RUBBER_WOOD_SWITCH_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(0.5F).sound(SoundType.WOOD).ignitedByLava();
	public static final BlockBehaviour.Properties SAPLING_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties SOUL_AFFLICTED_RUBBER_WOOD_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD);
	public static final BlockBehaviour.Properties SOUL_AFFLICTED_RUBBER_WOOD_SIGN = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F);
	public static final BlockBehaviour.Properties SOUL_AFFLICTED_RUBBER_WOOD_SWITCH_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(0.5F).sound(SoundType.WOOD);
	
	
	
	public static final BlockBehaviour.Properties AURUM_SP = BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).sound(SoundType.NETHER_GOLD_ORE).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().strength(3.0F, 6.0F).noCollission().randomTicks();
	
	public static final BlockBehaviour.Properties CARCASS_ROOT_FOLIAGE = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final BlockBehaviour.Properties CARCASS_ROOT_ROOTED_DIRT = BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(1F).sound(SoundType.GRAVEL).randomTicks();
	public static final BlockBehaviour.Properties CARCASS_ROOT_ROOTED_NETHERRACK = BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.9F).sound(SoundType.NETHERRACK);
	
	public static final BlockBehaviour.Properties PHANTASMAL_SHELF_FUNGUS_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5F).sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY).noCollission().randomTicks();
	public static final BlockBehaviour.Properties CROP = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final BlockBehaviour.Properties PRISMARINE_CRYSTAL_MOSS = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY).replaceable()/* .requiresCorrectToolForDrops() */.strength(1.5F, 6.0F).noCollission().randomTicks();
	public static final BlockBehaviour.Properties FALLEN_SHULKWART_SPORES = BlockBehaviour.Properties.of().mapColor(MapColor.SAND).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final BlockBehaviour.Properties SPIDER_VINE = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();;

	
	
	public static final BlockBehaviour.Properties RUBBER_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2f).sound(SoundType.CANDLE);
	public static final BlockBehaviour.Properties ROOTED_SOUL_SOIL_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.ROOTED_DIRT);
	
	// TODO, actually design these
	public static final BlockBehaviour.Properties DATA_DEVICE_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.FROGLIGHT);
	
	public static final BlockBehaviour.Properties STEEL_MACHINE_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(6.0F, 8.0F).sound(SoundType.METAL);
	public static final BlockBehaviour.Properties SOLAR_PANEL_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.METAL);
	public static final BlockBehaviour.Properties WIRE_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0f).sound(SoundType.METAL);
	public static final BlockBehaviour.Properties STEEL_PIPE_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0f).sound(SoundType.METAL);
	
	// TODO, make color customizable by method
	public static final BlockBehaviour.Properties LIQUID_PROPERTIES = BlockBehaviour.Properties.of()/* .mapColor(MapColor.WATER) */.replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY);
	
	
	
	public static BlockBehaviour.Properties shulkwart(DyeColor color)
	{
		if(color != null) 
		{
			return BlockBehaviour.Properties.of().mapColor(color).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
		}
		else
		{
			return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();

		}
	} // end shulkwart()
	
	
	
	public static BlockBehaviour.Properties plankPropertiesWith(MapColor color)
	{
		return BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava();
	} // end plankPropertiesWith
	
	public static BlockBehaviour.Properties logPropertiesWith(MapColor topColor, MapColor sideColor) 
	{
		return BlockBehaviour.Properties.of().mapColor((bs) -> bs.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
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