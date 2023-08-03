package com.gsr.gsr_yatm.block.hanging_pot;

import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;

import net.minecraft.core.BlockPos;
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
	protected @Nullable FlowerPotBlock m_pot = (FlowerPotBlock)Blocks.FLOWER_POT; //null;
	
	
	public HangingPotHookBlockEntity(BlockPos position, BlockState state)
	{
		super(YATMBlockEntityTypes.HANGING_POT_HOOK.get(), position, state);
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
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		if(this.m_pot != null) 
		{
			tag.putString(HangingPotHookBlockEntity.POT_TAG_NAME, ForgeRegistries.BLOCKS.getKey(this.m_pot).toString());
		} 
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(HangingPotHookBlockEntity.POT_TAG_NAME))
		{
			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tag.getString(HangingPotHookBlockEntity.POT_TAG_NAME)));
			this.m_pot = ((block != null && block instanceof FlowerPotBlock fb) ? fb : null);
		}
	} // end load()



	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag tag = super.getUpdateTag();
		if(this.m_pot != null) 
		{
			tag.putString(HangingPotHookBlockEntity.POT_TAG_NAME, ForgeRegistries.BLOCKS.getKey(this.m_pot).toString());
		}
		return tag;
	} // end getUpdateTag()
	
} // end class