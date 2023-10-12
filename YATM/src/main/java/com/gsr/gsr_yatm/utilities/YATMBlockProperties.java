package com.gsr.gsr_yatm.utilities;

import org.jetbrains.annotations.NotNull;

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
	
	
	
	public static final Properties AURUM_SP = Properties.of().mapColor(MapColor.GOLD).noCollission().pushReaction(PushReaction.BLOCK).randomTicks().requiresCorrectToolForDrops().sound(SoundType.NETHER_GOLD_ORE).strength(3.0f, 6.0f);
	public static final Properties BASIN_OF_TEARS = YATMBlockProperties.basinOfTears();
	public static final Properties BASIN_OF_TEARS_VEGETATIVE = YATMBlockProperties.basinOfTears().noLootTable();
	public static final Properties BLEACHED_ICE_CORAL = YATMBlockProperties.iceCoral().mapColor(DyeColor.WHITE);
	public static final Properties CACTUS = Properties.of().mapColor(MapColor.PLANT).randomTicks().strength(0.4f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY);
	public static final Properties CARCASS_ROOT_FOLIAGE = Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).ignitedByLava().pushReaction(PushReaction.DESTROY);
	public static final Properties CARCASS_ROOT_ROOTED_DIRT = Properties.of().mapColor(MapColor.DIRT).strength(1f).sound(SoundType.GRAVEL).randomTicks();
	public static final Properties CARCASS_ROOT_ROOTED_NETHERRACK = Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.9f).sound(SoundType.NETHERRACK);
	public static final Properties CROP = Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final Properties FALLEN_SHULKWART_SPORES = Properties.of().mapColor(MapColor.SAND).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final Properties FIRE_EATER_LILY = Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();
	public static final Properties ICE_CORAL = YATMBlockProperties.iceCoral();
	public static final Properties PHANTASMAL_SHELF_FUNGUS = Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.5f).sound(SoundType.FUNGUS).pushReaction(PushReaction.DESTROY).noCollission().randomTicks();
	public static final Properties PRISMARINE_CRYSTAL_MOSS = Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY).replaceable()/* .requiresCorrectToolForDrops() */.strength(1.5f, 6.0f).noCollission().randomTicks();
	public static final Properties SPIDER_VINE = Properties.of().mapColor(MapColor.PLANT).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).instabreak().noCollission().randomTicks();;

	
	
	
	public static final Properties RUBBER_BLOCK = Properties.of().mapColor(MapColor.COLOR_BLACK).strength(2f).sound(SoundType.CANDLE);
	public static final Properties ROOTED_SOUL_SOIL = Properties.of().mapColor(MapColor.DIRT).strength(0.5f).sound(SoundType.ROOTED_DIRT);
	
	public static final Properties HANGING_POT_HOOK = Properties.of().instabreak().noCollission().noOcclusion().pushReaction(PushReaction.DESTROY);
	// TODO, this
	public static final Properties SAP_COLLECTOR = Properties.of().ignitedByLava().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(0.6f);

	// TODO, actually design these
	public static final Properties DATA_DEVICE = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0f, 3.0f).sound(SoundType.FROGLIGHT);
	
	public static final Properties STEEL_MACHINE = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(6.0F, 8.0F).sound(SoundType.METAL);
	public static final Properties STEEL_PLANT_MACHINE = Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(6.0f, 8.0f).sound(SoundType.METAL).noCollission().noOcclusion();
	public static final Properties SOLAR_PANEL = Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.METAL);
	public static final Properties WIRE = Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0f).sound(SoundType.METAL);
	public static final Properties STEEL_PIPE = Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0f).sound(SoundType.METAL);
	
	public static final Properties CONDUIT_VINES = Properties.of().mapColor(MapColor.METAL)/*.requiresCorrectToolForDrops()*/.strength(2.0f, 3.0f).sound(SoundType.VINE).noCollission().noOcclusion();
	
	// TODO, make color customizable by method
	public static final Properties LIQUID = Properties.of()/* .mapColor(MapColor.WATER) */.replaceable().noCollission().strength(100.0f).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY);

	
	
	
	
	
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