package com.gsr.gsr_yatm.registry.custom;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.armor.IArmorSet;
import com.gsr.gsr_yatm.armor.decay_netherite.DecayNetheriteArmorSet;
import com.gsr.gsr_yatm.armor.soul_adorned_netherite.SoulAdornedNetheriteArmorSet;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMArmorSets
{
	public static final DeferredRegister<IArmorSet> ARMOR_SETS = DeferredRegister.create(new ResourceLocation(YetAnotherTechMod.MODID, "armor_sets"), YetAnotherTechMod.MODID);
	
	
	
	public static final RegistryObject<SoulAdornedNetheriteArmorSet> SOUL_ADORNED_NETHERITE = ARMOR_SETS.register("soul_adorned_netherite", () -> new SoulAdornedNetheriteArmorSet());	
	public static final RegistryObject<DecayNetheriteArmorSet> DECAY_NETHERITE = ARMOR_SETS.register("decay_netherite", () -> new DecayNetheriteArmorSet());	
	
} // end class