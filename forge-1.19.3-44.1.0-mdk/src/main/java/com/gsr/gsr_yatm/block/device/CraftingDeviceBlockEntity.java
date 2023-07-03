package com.gsr.gsr_yatm.block.device;

import java.util.Enumeration;
import java.util.List;

import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.utilities.RecipeUtilities;
import com.gsr.gsr_yatm.utilities.network.Property;
import com.gsr.gsr_yatm.utilities.network.PropertyContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CraftingDeviceBlockEntity<T extends ITimedRecipe<C>, C extends Container> extends DeviceBlockEntity 
{	
	public static final String RECIPE_IDENTIFIER_TAG_NAME = "recipe";	
	public static final String CRAFT_PROGESS_TAG_NAME = "craftProgress";
	public static final String CRAFT_TIME_TAG_NAME = "craftTime";
	
	public static final int RECHECK_PERIOD = RecipeUtilities.RECHECK_CRAFTING_PERIOD;
	
	protected final RecipeType<T> m_recipeType;
	protected T m_activeRecipe = null;
	protected String m_activeRecipeIdentifier = null;
	protected int m_craftProgress = 0;
	protected int m_craftTime = 0;
	protected int m_timeSinceRecheck = 0;
	protected boolean m_waitingForLoad = false;
	
	protected final ContainerData m_craftProgressC = new PropertyContainerData(List.of(new Property<>(() -> this.m_craftProgress, (i) -> {}), new Property<>(() -> this.m_craftTime, (i) -> {})));
	
	
	
	public CraftingDeviceBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState, int inventorySlotCount, RecipeType<T> recipeType)
	{
		super(type, blockPos, blockState, inventorySlotCount);
		this.m_recipeType = recipeType;
	} // end Constructor

	
	
	@Override
	protected void onItemInsertion(int slot, ItemStack itemStack)
	{
		super.onItemInsertion(slot, itemStack);
		this.m_timeSinceRecheck = RECHECK_PERIOD;
	} // end onItemInsertion()

	@Override
	protected void onItemWithdrawal(int slot, int amount)
	{
		super.onItemWithdrawal(slot, amount);
		this.m_timeSinceRecheck = RECHECK_PERIOD;
	} // end onItemWithdrawal()
	
	@Override
	protected void onFluidContentsChanged() 
	{	
		this.m_timeSinceRecheck = RECHECK_PERIOD;
	} // end onFluidContentsChanged()



	protected boolean doCrafting() 
	{
		boolean changed = false;
		
		if (this.m_craftProgress > 0)
		{
			if (--this.m_craftProgress <= 0)
			{
				this.setRecipeResults(this.m_activeRecipe);
				this.tryStartNewRecipe();
			}
			changed = true;
		}
		else if (++this.m_timeSinceRecheck > RECHECK_PERIOD)
		{
			this.m_timeSinceRecheck = 0;
			this.tryStartNewRecipe();
			changed = true;
		}		
		return changed;
	} // end doCrafting()

	protected void tryStartNewRecipe()
	{
		this.m_activeRecipe = null;
		this.m_craftTime = 0;
		this.m_craftProgress = 0;
		
		Enumeration<T> recipes = RecipeUtilities.getRecipes(this.level, this.m_recipeType);
		while (recipes.hasMoreElements())//for (T r : recipes)
		{
			T r = recipes.nextElement();
		//List<T> recipes = level.getRecipeManager().getAllRecipesFor(this.m_recipeType);
		//for (T r : recipes)
		//{
			if (this.canUseRecipe(r))
			{
				this.m_activeRecipe = r;
				this.m_craftTime = r.getTimeInTicks();
				this.m_craftProgress = this.m_craftTime;
				this.startRecipe(r);
				break;
			}
		}
	} // end tryStartNewRecipe()	
	
	protected abstract void setRecipeResults(T from);
	protected abstract boolean canUseRecipe(T from);
	protected abstract void startRecipe(T from);

	protected void onRecipeLoad() 
	{
		this.m_waitingForLoad = false;
		this.m_activeRecipe = RecipeUtilities.loadRecipe(this.m_activeRecipeIdentifier, this.level, this.m_recipeType);
		if(this.m_activeRecipe == null || this.m_craftProgress <= 0 || this.m_craftTime <= 0) 
		{
			this.m_activeRecipe = null;
			this.m_craftProgress = 0;
			this.m_craftTime = 0;
		}
	} // end onRecipeLoad()
	
	
	
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		if(this.m_activeRecipe != null && this.m_craftProgress > 0 && this.m_craftTime > 0) 
		{
			tag.putString(CraftingDeviceBlockEntity.RECIPE_IDENTIFIER_TAG_NAME, this.m_activeRecipe.getId().toString());
			tag.putInt(CraftingDeviceBlockEntity.CRAFT_PROGESS_TAG_NAME, this.m_craftProgress);
			tag.putInt(CraftingDeviceBlockEntity.CRAFT_TIME_TAG_NAME, this.m_craftTime);
		}
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);	
		
		if(tag.contains(CraftingDeviceBlockEntity.RECIPE_IDENTIFIER_TAG_NAME) && tag.contains(CraftingDeviceBlockEntity.CRAFT_PROGESS_TAG_NAME) && tag.contains(CraftingDeviceBlockEntity.CRAFT_TIME_TAG_NAME)) 
		{
			this.m_activeRecipeIdentifier = tag.getString(CraftingDeviceBlockEntity.RECIPE_IDENTIFIER_TAG_NAME);
			this.m_craftProgress = tag.getInt(CraftingDeviceBlockEntity.CRAFT_PROGESS_TAG_NAME);
			this.m_craftTime = tag.getInt(CraftingDeviceBlockEntity.CRAFT_TIME_TAG_NAME);
			this.m_waitingForLoad = true;
			RecipeUtilities.addRecipeLoadListener(this::onRecipeLoad);
		}		
	} // end load()	
	
} // end class