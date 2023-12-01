package com.gsr.gsr_yatm.block.device;

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.Property;
import com.gsr.gsr_yatm.utilities.network.PropertyContainerData;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CraftingDeviceBlockEntity<T extends ITimedRecipe<C>, C extends Container> extends DeviceBlockEntity 
{	
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	
	public static final String RECIPE_IDENTIFIER_TAG_NAME = "recipe";	
	public static final String CRAFT_PROGESS_TAG_NAME = "craftProgress";
	public static final String CRAFT_TIME_TAG_NAME = "craftTime";
	
	public static final int RECHECK_PERIOD = YATMConfigs.CRAFTING_RECHECK_PERIOD.get();
	
	protected final @NotNull RecipeType<T> m_recipeType;
	protected @Nullable T m_activeRecipe = null;
	protected @Nullable String m_activeRecipeIdentifier = null;
	protected @NotNegative int m_craftCountDown = 0;
	protected @NotNegative int m_craftTime = 0;
	protected @NotNegative int m_timeSinceRecheck = 0;
	protected boolean m_waitingForLoad = false;
	
	protected final @NotNull ContainerData m_craftProgressC = new PropertyContainerData(List.of(new Property<>(() -> this.m_craftCountDown, (i) -> {}), new Property<>(() -> this.m_craftTime, (i) -> {})));
	
	
	
	public CraftingDeviceBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos position, @NotNull BlockState state, @NotNegative int inventorySlotCount, @NotNull RecipeType<T> recipeType)
	{
		super(Objects.requireNonNull(type), Objects.requireNonNull(position), Objects.requireNonNull(state), Contract.notNegative(inventorySlotCount));
		this.m_recipeType = Objects.requireNonNull(recipeType);
	} // end Constructor



	@Override
	public void setChanged()
	{
		super.setChanged();
		this.m_timeSinceRecheck = CraftingDeviceBlockEntity.RECHECK_PERIOD;
	} // end setChanged()



	protected boolean doCrafting() 
	{
		boolean changed = false;
		if(this.m_waitingForLoad || (this.m_activeRecipe != null && !this.canTick())) 
		{
			return changed;
		}
		if(this.m_activeRecipe != null && !this.canUseRecipe(this.m_activeRecipe)) 
		{
			this.clearCurrentRecipe();
		}
		
		
		if (this.m_craftCountDown > 0)
		{
			--this.m_craftCountDown;
			this.recipeTick();		
			if (this.m_craftCountDown <= 0)
			{
				this.setRecipeResults(this.m_activeRecipe);
				this.tryStartNewRecipe();
			}
			changed = true;
		}
		else if (++this.m_timeSinceRecheck > CraftingDeviceBlockEntity.RECHECK_PERIOD)
		{
			this.m_timeSinceRecheck = 0;
			this.tryStartNewRecipe();
			changed = true;
		}		
		return changed;
	} // end doCrafting()

	protected void tryStartNewRecipe()
	{
		this.clearCurrentRecipe();
		Enumeration<T> recipes = RecipeUtil.getRecipes(this.level, this.m_recipeType);
		while (recipes.hasMoreElements())
		{
			T r = recipes.nextElement();
			if (this.canUseRecipe(r))
			{
				this.m_activeRecipe = r;
				this.m_craftTime = r.getTimeInTicks();
				this.m_craftCountDown = this.m_craftTime;
				this.startRecipe(r);
				break;
			}
		}
	} // end tryStartNewRecipe()
	
	protected void clearCurrentRecipe() 
	{
		this.m_activeRecipe = null;
		this.m_craftTime = 0;
		this.m_craftCountDown = 0;		
	} // end clearCurrentRecipe()
	
	/** If the active recipe can be used this tick or not, should only be called while an active recipe's present. */ protected boolean canTick() {return true;}
	/** If the recipes material inputs are matched and outputs aren't blocked, excluding tick based things like current or present temperature. */ protected abstract boolean canUseRecipe(@NotNull T from);
	protected void setRecipeResults(@NotNull T from) {}
	protected void startRecipe(@NotNull T from) {}
	/** Place for post tick recipe related changes. Only called while a recipe is active. */protected void recipeTick() {}
	
	protected void onRecipeLoad() 
	{
		this.m_waitingForLoad = false;
		this.m_activeRecipe = RecipeUtil.loadRecipe(this.m_activeRecipeIdentifier, this.level, this.m_recipeType);
		if(this.m_activeRecipe == null || this.m_craftCountDown <= 0 || this.m_craftTime <= 0) 
		{
			this.clearCurrentRecipe();
		}
	} // end onRecipeLoad()
	
	
	
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		if(this.m_activeRecipe != null && this.m_craftCountDown > 0 && this.m_craftTime > 0) 
		{
			tag.putString(CraftingDeviceBlockEntity.RECIPE_IDENTIFIER_TAG_NAME, this.m_activeRecipe.getId().toString());
			tag.putInt(CraftingDeviceBlockEntity.CRAFT_PROGESS_TAG_NAME, this.m_craftCountDown);
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
			this.m_craftCountDown = tag.getInt(CraftingDeviceBlockEntity.CRAFT_PROGESS_TAG_NAME);
			this.m_craftTime = tag.getInt(CraftingDeviceBlockEntity.CRAFT_TIME_TAG_NAME);
			this.m_waitingForLoad = true;
			RecipeUtil.addRecipeLoadListener(this::onRecipeLoad);
		}		
	} // end load()	
	
} // end class