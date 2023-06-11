package com.gsr.gsr_yatm;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

public class YATMBlockTags extends BlockTagsProvider
{
	private static final String MINECRAFT_ID = "minecraft";
	private static final ITagManager<Block> TM = ForgeRegistries.BLOCKS.tags();
	
	
	
	public static final TagKey<Block> MINECRAFT_DIRT_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "dirt"));
	public static final TagKey<Block> MINECRAFT_SAND_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "sand"));	
	public static final TagKey<Block> MINECRAFT_FLOWERS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "flowers"));
	public static final TagKey<Block> MINECRAFT_LOGS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "logs"));
	public static final TagKey<Block> MINECRAFT_SAPLINGS_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "saplings"));
	public static final TagKey<Block> MINECRAFT_SOUL_FIRE_BASE_KEY = TM.createTagKey(new ResourceLocation(MINECRAFT_ID, "soul_fire_base_blocks"));
	
	// forge
	
	public static final TagKey<Block> RUBBER_TREE_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "rubber_tree_blocks"));
	public static final TagKey<Block> SOUL_RUBBER_TREE_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "soul_rubber_tree_blocks"));
	
	public static final TagKey<Block> RUBBER_MERISTEM_CAN_GROW_ON_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_meristem_can_grow_on"));
	public static final TagKey<Block> RUBBER_ROOTS_CAN_GROW_IN_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/rubber_roots_can_grow_in"));
	
	public static final TagKey<Block> SOUL_AFFLICTING_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "mediums/soul_afflicting_blocks"));
	
	public static final TagKey<Block> SIXTYFOUR_CU_WIRE_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "conduits/sixtyfour_cu"));
	
	public static final TagKey<Block> UNOXIDIXED_COPPER_BLOCKS_KEY = TM.createTagKey(new ResourceLocation(YetAnotherTechMod.MODID, "unoxidized_copper_blocks"));
	
	
	
	// seems pretty safe to store long term and keep
	public static final ITag<Block> SOUL_AFFLICTING_BLOCKS = TM.getTag(SOUL_AFFLICTING_BLOCKS_KEY);
	public static final ITag<Block> RUBBER_MERISTEM_CAN_GROW_ON = TM.getTag(RUBBER_MERISTEM_CAN_GROW_ON_KEY);	
	
	
	
	
	public YATMBlockTags(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(output, lookupProvider, modId, existingFileHelper);
	} // end constructor


	
	@Override
	protected void addTags(Provider provider)
	{
		// TODO, separate soul and regular into individual their log tags, add those tags to here, like vanilla does
		this.tag(MINECRAFT_LOGS_KEY).add(YATMBlocks.RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get());
		this.tag(MINECRAFT_FLOWERS_KEY).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()); // maybe eventually add soul rubber flowers too, but seems like they should be special, and I dunno how to modify minecraft entity beehaviours
		this.tag(MINECRAFT_SAPLINGS_KEY).add(YATMBlocks.RUBBER_MERISTEM.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_MERISTEM.get());
		this.tag(MINECRAFT_SOUL_FIRE_BASE_KEY).addTag(SOUL_RUBBER_TREE_BLOCKS_KEY);
		
		
		
		this.tag(RUBBER_TREE_BLOCKS_KEY).add(YATMBlocks.RUBBER_LOG.get()).add(YATMBlocks.RUBBER_LEAVES_OLD.get()).add(YATMBlocks.RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.RUBBER_LEAVES_YOUNG.get());
		this.tag(SOUL_RUBBER_TREE_BLOCKS_KEY).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LOG.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_OLD.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_FLOWERING.get()).add(YATMBlocks.SOUL_AFFLICTED_RUBBER_LEAVES_YOUNG.get());
		this.tag(RUBBER_MERISTEM_CAN_GROW_ON_KEY).addTag(SOUL_RUBBER_TREE_BLOCKS_KEY).addTag(RUBBER_TREE_BLOCKS_KEY).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK).add(Blocks.FARMLAND).add(Blocks.MUD).add(Blocks.CLAY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);
		this.tag(RUBBER_ROOTS_CAN_GROW_IN_KEY).addTag(MINECRAFT_DIRT_KEY).addTag(MINECRAFT_SAND_KEY).add(Blocks.CLAY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL);
		
		// add soil fire base blocks, and soul fire related things, if nearby chance afflict too, not just on
		this.tag(SOUL_AFFLICTING_BLOCKS_KEY).add(Blocks.SOUL_SAND).add(Blocks.SOUL_SOIL).addTag(SOUL_RUBBER_TREE_BLOCKS_KEY);
		this.tag(SIXTYFOUR_CU_WIRE_KEY).add(YATMBlocks.SIXTYFOUR_CU_WIRE.get()).add(YATMBlocks.ENAMELED_SIXTYFOUR_CU_WIRE.get()).add(YATMBlocks.INSULATED_SIXTYFOUR_CU_WIRE.get());
		this.tag(UNOXIDIXED_COPPER_BLOCKS_KEY).add(Blocks.COPPER_BLOCK).add(Blocks.CUT_COPPER);
	} // end addTags()
	
} // end class