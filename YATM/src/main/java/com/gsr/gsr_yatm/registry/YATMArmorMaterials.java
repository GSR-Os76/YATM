package com.gsr.gsr_yatm.registry;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.armor.decay_netherite.DecayNetheriteArmorMaterial;
import com.gsr.gsr_yatm.armor.empty.EmptyArmorMaterial;
import com.gsr.gsr_yatm.armor.foliar_steel.FoliarSteelArmorMaterial;
import com.gsr.gsr_yatm.armor.soul_adorned_netherite.SoulAdornedNetheriteArmorMaterial;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YATMArmorMaterials
{
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, YetAnotherTechMod.MODID);

	public static final RegistryObject<ArmorMaterial> DECAY_NETHERITE = ARMOR_MATERIALS.register("decay_netherite", () -> DecayNetheriteArmorMaterial.MATERIAL);
	public static final RegistryObject<ArmorMaterial> EMPTY = ARMOR_MATERIALS.register("empty", () -> EmptyArmorMaterial.MATERIAL);
	public static final RegistryObject<ArmorMaterial> FOLIAR_STEEL = ARMOR_MATERIALS.register("foliar_steel", () -> FoliarSteelArmorMaterial.MATERIAL);
	public static final RegistryObject<ArmorMaterial> SOUL_ADORNED_NETHERITE = ARMOR_MATERIALS.register("soul_adorned_netherite", () -> SoulAdornedNetheriteArmorMaterial.MATERIAL);
	
} // end class