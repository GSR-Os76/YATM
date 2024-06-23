package com.gsr.gsr_yatm.block.hanging_pot;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class HangingPotHookBlockEntity extends BlockEntity
{
	public static final String POT_TAG_NAME = "pot";
	public static final String NULL_KEY = "null";
	protected @Nullable FlowerPotBlock m_pot = (FlowerPotBlock)Blocks.FLOWER_POT;

	
	
	public HangingPotHookBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.HANGING_POT_HOOK.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor()
	
	
	
	public @Nullable FlowerPotBlock getPot() 
	{
		return this.m_pot;
	} // end getPot()
	
	public void setPot(@Nullable FlowerPotBlock pot) 
	{
		if(pot != this.m_pot) 
		{
			this.m_pot = pot;
			this.setChanged();
		}
	} // end setPot()


	
	

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider holderLookup)
	{
		super.saveAdditional(tag, holderLookup);
		tag.putString(HangingPotHookBlockEntity.POT_TAG_NAME, this.m_pot == null ? HangingPotHookBlockEntity.NULL_KEY : ForgeRegistries.BLOCKS.getKey(this.m_pot).toString());
	} // end saveAdditional()
	
	@Override
	protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider holderLookup)
	{
		super.loadAdditional(tag, holderLookup);
		if(tag.contains(HangingPotHookBlockEntity.POT_TAG_NAME))
		{
			String value = tag.getString(HangingPotHookBlockEntity.POT_TAG_NAME);
			Block block = value.equals(HangingPotHookBlockEntity.NULL_KEY) ? null : ForgeRegistries.BLOCKS.getValue(new ResourceLocation(value));
			this.m_pot = ((block != null && block instanceof FlowerPotBlock fb) ? fb : null);
		}
	} // end loadAdditional()
	
	

	@Override
	public @NotNull CompoundTag getUpdateTag(@NotNull HolderLookup.Provider holderLookup)
	{
		CompoundTag tag = super.getUpdateTag(holderLookup);
		tag.putString(HangingPotHookBlockEntity.POT_TAG_NAME, this.m_pot == null ? HangingPotHookBlockEntity.NULL_KEY : ForgeRegistries.BLOCKS.getKey(this.m_pot).toString());
		
		return tag;
	} // end getUpdateTag()
	
} // end class