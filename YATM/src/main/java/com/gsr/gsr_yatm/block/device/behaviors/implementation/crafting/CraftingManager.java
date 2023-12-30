package com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting;

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.IChangedListenerBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;
import com.gsr.gsr_yatm.utilities.recipe.RecipeUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CraftingManager<T extends ITimedRecipe<C, A>, C extends Container, A> implements IChangedListenerBehavior, ISerializableBehavior, ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(IChangedListenerBehavior.class, ISerializableBehavior.class, ITickableBehavior.class);
	} // end behaviorTypes()
	
	public static final int SLOT_COUNT = PropertyContainerData.LENGTH_PER_PROPERTY * 2;
	public static final String SPEC_KEY = "craftProgress";
	
	public static final String RECIPE_IDENTIFIER_TAG_NAME = "recipe";	
	public static final String CRAFT_PROGESS_TAG_NAME = "craftProgress";
	public static final String CRAFT_TIME_TAG_NAME = "craftTime";
	
	
	
	public static final int RECHECK_PERIOD = YATMConfigs.CRAFTING_RECHECK_PERIOD.get();
	
	
	
	protected final @NotNull Supplier<@NotNull A> m_context;
	protected final @NotNull RecipeType<T> m_recipeType;
	
	protected @Nullable T m_activeRecipe = null;
	protected @Nullable String m_activeRecipeIdentifier = null;
	protected @NotNegative int m_craftCountDown = 0;
	protected @NotNegative int m_craftTime = 0;
	protected @NotNegative int m_timeSinceRecheck = 0;
	protected boolean m_waitingForLoad = false;
	
	protected @NotNull Level m_level;
	
	protected final @NotNull ContainerData m_data = new PropertyContainerData(List.of(new Property<>(() -> this.m_craftCountDown, (i) -> {}), new Property<>(() -> this.m_craftTime, (i) -> {})));
	
	
	
	public CraftingManager(@NotNull RecipeType<T> recipeType, @NotNull Supplier<@NotNull A> context)
	{
		this.m_recipeType = Objects.requireNonNull(recipeType);
		this.m_context = context;
	} // end Constructor

	public @NotNull ContainerData getData()
	{
		return this.m_data;
	} // end getData()
	
	public @NotNull T getActiveRecipe() 
	{
		return this.m_activeRecipe;
	} // end getActiveRecipe()
	
	

	public void onChanged()
	{
		this.m_timeSinceRecheck = CraftingManager.RECHECK_PERIOD;
	} // end setChanged()



	@Override
	public boolean tick(@NotNull Level level, @NotNull BlockPos position) 
	{
		boolean changed = false;
		this.m_level = Objects.requireNonNull(level);
		if(this.m_waitingForLoad || (this.m_activeRecipe != null && !this.m_activeRecipe.canTick(this.m_context.get()))) 
		{
			return changed;
		}
		if(this.m_activeRecipe != null && !this.m_activeRecipe.matches(this.m_context.get())) 
		{
			this.clearCurrentRecipe();
		}
		
		
		if (this.m_craftCountDown > 0)
		{
			--this.m_craftCountDown;
			this.m_activeRecipe.tick(this.m_context.get());		
			if (this.m_craftCountDown <= 0)
			{
				this.m_activeRecipe.end(this.m_context.get());
				this.tryStartNewRecipe(level);
			}
			changed = true;
		}
		else if (++this.m_timeSinceRecheck > CraftingManager.RECHECK_PERIOD)
		{
			this.m_timeSinceRecheck = 0;
			this.tryStartNewRecipe(level);
			changed = true;
		}		
		return changed;
	} // end doCrafting()

	public void tryStartNewRecipe(@NotNull Level level)
	{
		this.clearCurrentRecipe();
		Enumeration<RecipeHolder<T>> recipes = RecipeUtil.getRecipes(level, this.m_recipeType);
		while (recipes.hasMoreElements())
		{
			RecipeHolder<T> r = recipes.nextElement();
			if (r.value().matches(this.m_context.get()))
			{
				this.m_activeRecipe = r.value();
				this.m_activeRecipeIdentifier = r.id().toString();
				this.m_craftTime = this.m_activeRecipe.getTimeInTicks();
				this.m_craftCountDown = this.m_craftTime;
				this.m_activeRecipe.start(this.m_context.get());
				break;
			}
		}
	} // end tryStartNewRecipe()
	
	protected void clearCurrentRecipe() 
	{
		this.m_activeRecipe = null;
		this.m_activeRecipeIdentifier = null;
		this.m_craftTime = 0;
		this.m_craftCountDown = 0;		
	} // end clearCurrentRecipe()
	
	protected void onRecipeLoad() 
	{
		this.m_waitingForLoad = false;
		RecipeHolder<T> rHolder = RecipeUtil.loadRecipe(this.m_activeRecipeIdentifier, this.m_level, this.m_recipeType);
		if(rHolder == null) 
		{
			this.clearCurrentRecipe();
			return;
		}
		this.m_activeRecipe = rHolder.value();
		this.m_activeRecipeIdentifier = rHolder.id().toString();
	} // end onRecipeLoad()
	
	
	
	@Override
	public @Nullable CompoundTag serializeNBT()
	{
		if(this.m_activeRecipe != null && this.m_craftCountDown > 0 && this.m_craftTime > 0) 
		{
			CompoundTag tag = new CompoundTag();
			tag.putString(CraftingManager.RECIPE_IDENTIFIER_TAG_NAME, this.m_activeRecipeIdentifier);
			tag.putInt(CraftingManager.CRAFT_PROGESS_TAG_NAME, this.m_craftCountDown);
			tag.putInt(CraftingManager.CRAFT_TIME_TAG_NAME, this.m_craftTime);
			return tag;
		}
		return null;
	} // end saveAdditional()
	
	@Override
	public void deserializeNBT(@NotNull CompoundTag tag)
	{
		if(tag.contains(CraftingManager.RECIPE_IDENTIFIER_TAG_NAME) && tag.contains(CraftingManager.CRAFT_PROGESS_TAG_NAME) && tag.contains(CraftingManager.CRAFT_TIME_TAG_NAME)) 
		{
			this.m_activeRecipeIdentifier = tag.getString(CraftingManager.RECIPE_IDENTIFIER_TAG_NAME);
			this.m_craftCountDown = tag.getInt(CraftingManager.CRAFT_PROGESS_TAG_NAME);
			this.m_craftTime = tag.getInt(CraftingManager.CRAFT_TIME_TAG_NAME);
			if (this.m_craftCountDown <= 0 || this.m_craftTime <= 0) 
			{
				this.clearCurrentRecipe();
				return;
			}
			this.m_waitingForLoad = true;
			RecipeUtil.addRecipeLoadListener(this::onRecipeLoad);
		}		
	} // end load()
	
} // end class