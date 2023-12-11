package com.gsr.gsr_yatm.armor;

import com.gsr.gsr_yatm.armor.decay_netherite.DecayNetheriteArmorMaterial;
import com.gsr.gsr_yatm.armor.empty.EmptyArmorMaterial;
import com.gsr.gsr_yatm.armor.soul_adorned_netherite.SoulAdornedNetheriteArmorMaterial;

import net.minecraft.world.item.ArmorMaterial;

public class YATMArmorMaterials
{
	public static final ArmorMaterial DECAY_NETHERITE = new DecayNetheriteArmorMaterial();
	public static final ArmorMaterial EMPTY = new EmptyArmorMaterial();
	public static final ArmorMaterial SOUL_ADORNED_NETHERITE = new SoulAdornedNetheriteArmorMaterial();
	
} // end class