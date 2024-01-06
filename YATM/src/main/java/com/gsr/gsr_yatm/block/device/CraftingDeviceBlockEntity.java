package com.gsr.gsr_yatm.block.device;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.CraftingManager;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**manually use the CraftingManager behavior*/
@Deprecated(forRemoval=true)
public abstract class CraftingDeviceBlockEntity<T extends ITimedRecipe<C, A>, C extends Container, A> extends DeviceBlockEntity 
{	
	public static final String CRAFT_PROGRESS_SPEC_KEY = CraftingManager.SPEC_KEY;
	
	public static final String CRAFTING_MANAGER_TAG_NAME = "craftManager";
	
	protected final @NotNull CraftingManager<T, C, A> m_craftingManager;
	protected final @NotNull ContainerData m_craftProgressC;
	
	
	
	public CraftingDeviceBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state, @NotNegative int inventorySlotCount, @NotNull RecipeType<T> recipeType)
	{
		super(Objects.requireNonNull(type), Objects.requireNonNull(position), Objects.requireNonNull(state), Contract.notNegative(inventorySlotCount));
		this.m_craftingManager = new CraftingManager<>(Objects.requireNonNull(recipeType), this::getContext);
		this.m_craftProgressC = this.m_craftingManager.getData();
	} // end Constructor

	public @NotNull abstract A getContext();


	@Override
	public void setChanged()
	{
		super.setChanged();
		this.m_craftingManager.onChanged();
	} // end setChanged()



	@Override
	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		CompoundTag cmT = this.m_craftingManager.serializeNBT();
		if(cmT != null) 
		{
			tag.put(CraftingDeviceBlockEntity.CRAFTING_MANAGER_TAG_NAME, cmT);
		}
	} // end saveAdditional()
	
	@Override
	public void load(@NotNull CompoundTag tag)
	{
		super.load(tag);	
		
		if(tag.contains(CraftingDeviceBlockEntity.CRAFTING_MANAGER_TAG_NAME)) 
		{
			this.m_craftingManager.deserializeNBT(tag.getCompound(CraftingDeviceBlockEntity.CRAFTING_MANAGER_TAG_NAME));
		}
	} // end load()	
	
} // end class