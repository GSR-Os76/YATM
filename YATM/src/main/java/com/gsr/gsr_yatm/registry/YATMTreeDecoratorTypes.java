package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.block.plant.tree.LeafLitterTreeDecorator;
import com.gsr.gsr_yatm.block.plant.tree.TrunkSideTreeDecorator;

import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YATMTreeDecoratorTypes
{
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, YetAnotherTechMod.MODID);

	
	
	public static final RegistryObject<TreeDecoratorType<LeafLitterTreeDecorator>> LEAF_LITTER_TREE_DECORATOR = TREE_DECORATOR_TYPES.register("leaf_litter_tree_decorator", () -> new TreeDecoratorType<>(LeafLitterTreeDecorator.CODEC));	
	public static final RegistryObject<TreeDecoratorType<TrunkSideTreeDecorator>> TRUNK_SIDE_TREE_DECORATOR = TREE_DECORATOR_TYPES.register("trunk_side_tree_decorator", () -> new TreeDecoratorType<>(TrunkSideTreeDecorator.CODEC));	

	
} // end class