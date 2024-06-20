package com.gsr.gsr_yatm.utilities;

import java.util.Optional;

import net.minecraft.world.level.block.grower.TreeGrower;

public class YATMTreeGrowers
{
	public static final TreeGrower RUBBER = new TreeGrower("rubber", Optional.empty(), Optional.of(YATMFeatureKeys.RUBBER_TREE), Optional.empty()); // TODO, post-1.0.0, make mega tree, and still add bee support
	public static final TreeGrower SOUL_AFFLICTED_RUBBER = new TreeGrower("soul_afflicted_rubber", Optional.empty(), Optional.of(YATMFeatureKeys.SOUL_AFFLICTED_RUBBER_TREE), Optional.empty());

} // end class