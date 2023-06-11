package com.gsr.gsr_yatm.block.device.extruder;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.YATMRecipeTypes;
import com.gsr.gsr_yatm.recipe.ExtrusionRecipe;
import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ExtruderBlockEntity extends BlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	public static final int DATA_SLOT_COUNT = 4;

	public static final int INPUT_SLOT = 0;
	public static final int DIE_SLOT = 1;
	public static final int RESULT_SLOT = 2;
	public static final int INPUT_REMAINDER_SLOT = 3;
	public static final int POWER_SLOT = 4;

	public static final int LAST_INVENTORY_SLOT = INPUT_REMAINDER_SLOT;

	public static final int EXTRUDE_PROGRESS_SLOT = 0;
	public static final int EXTRUDE_TIME_SLOT = 1;
	public static final int STORED_POWER_SLOT = 2;
	public static final int POWER_CAPACITY_SLOT = 3;

	public static final String ACTIVE_RECIPE_TAG_NAME = "recipe";
	public static final String EXTRUDE_PROGRESS_TAG_NAME = "extrudeProgress";
	public static final String EXTRUDE_TIME_TAG_NAME = "extrudeTime";
	public static final String POWER_TAG_NAME = "power";
	public static final String INVENTORY_TAG_NAME = "inventory";



	private String m_activeRecipeIdentifier = null;
	private int m_extrudeProgress = 0;
	private int m_extrudeTime = 0;
	private int m_storedPower = 0;
	private int m_powerCapacity = 0;

	private final ContainerData m_data = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			switch (index)
			{
				case EXTRUDE_PROGRESS_SLOT:
				{
					return m_extrudeProgress;
				}
				case EXTRUDE_TIME_SLOT:
				{
					return m_extrudeTime;
				}
				case STORED_POWER_SLOT:
				{
					return m_storedPower;
				}
				case POWER_CAPACITY_SLOT:
				{
					return m_powerCapacity;
				}
				default:
				{
					throw new IndexOutOfBoundsException("get index of: " + index + ", is out of the range");
				}
			}
		} // end get()

		@Override
		public void set(int index, int value)
		{
			switch (index)
			{
				case EXTRUDE_PROGRESS_SLOT:
				{
					m_extrudeProgress = value;
				}
				case EXTRUDE_TIME_SLOT:
				{
					m_extrudeTime = value;
				}
				case STORED_POWER_SLOT:
				{
					m_storedPower = value;
				}
				case POWER_CAPACITY_SLOT:
				{
					m_powerCapacity = value;
				}
				default:
				{
					throw new IndexOutOfBoundsException("get index of: " + index + ", is out of the range");
				}
			}
		} // end set()

		@Override
		public int getCount()
		{
			return DATA_SLOT_COUNT;
		} // end getCount()

	};

	private final ItemStackHandler m_rawInventory = new ItemStackHandler(INVENTORY_SLOT_COUNT);
	// TODO, make so you don't have to specify the slot conversion table, make so
	// you don't have to specify everything or have very many constructors
	private final ConfigurableInventoryWrapper m_inventory = new ConfigurableInventoryWrapper(m_rawInventory, new int[]
	{ 0, 1, 2, 3, 4 }, this::stackInsertionValidator, this::onStackInsertion, this::onStackWithdrawal);
	private LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> this.m_inventory);

	private final ConfigurableInventoryWrapper m_inputsSlots = new ConfigurableInventoryWrapper(m_rawInventory, new int[]
	{ INPUT_SLOT, DIE_SLOT });
	private LazyOptional<IItemHandler> m_inputsSlotsLazyOptional = LazyOptional.of(() -> this.m_inputsSlots);

	private final ConfigurableInventoryWrapper m_resultsSlots = new ConfigurableInventoryWrapper(m_rawInventory, new int[]
	{ RESULT_SLOT, INPUT_REMAINDER_SLOT });
	private LazyOptional<IItemHandler> m_resultsSlotsLazyOptional = LazyOptional.of(() -> this.m_resultsSlots);

	private static final int RECHECK_CRAFTING_PERIOD = 20;
	private int m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
	private ExtrusionRecipe m_activeRecipe = null;



	public ExtruderBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(YATMBlockEntityTypes.EXTRUDER_BLOCK_ENTITY.get(), blockPos, blockState);
	} // end constructor



	public IItemHandler getInventory()
	{
		return this.m_inventory;
	} // end getInventory()

	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()



	public static void tick(Level level, BlockPos blockPos, BlockState blockState, ExtruderBlockEntity blockEntity)
	{
		if (!level.isClientSide)
		{
			blockEntity.serverTick(level, blockPos, blockState, blockEntity);
		}
	} // end tick()

	private void serverTick(Level level, BlockPos blockPos, BlockState blockState, ExtruderBlockEntity blockEntity)
	{
		// accept in power
		// decrease power according to need. if not possible resverse recipe progress(wait no that doesn't make sense for this
		// why do i do count down not count up? seems like just adding complexity
		if (this.m_extrudeProgress > 0)
		{
			if( --this.m_extrudeProgress == 0) 
			{
				if (this.m_activeRecipeIdentifier == null)
				{
					return;
				}
				if (this.m_activeRecipe == null)
				{
					if(!this.loadRecipe(level)) 
					{
						return;
					}
				}
				this.m_activeRecipe.setResults(this.m_rawInventory);
				this.tryStartNewRecipe();
			}
		}
		else if (this.m_timeSinceRecheck++ > RECHECK_CRAFTING_PERIOD)
		{
			this.m_timeSinceRecheck = 0;
			this.tryStartNewRecipe();
		}
		// if ther's a recipe to match us start doing recipe things
		// on craft check it again
	} // end serverTick()

	private boolean loadRecipe(Level level)
	{
		List<ExtrusionRecipe> recipes = level.getRecipeManager().getAllRecipesFor(YATMRecipeTypes.EXTRUSION_RECIPE_TYPE.get());
		for (ExtrusionRecipe r : recipes)
		{
			if (r.getId().toString() == this.m_activeRecipeIdentifier)
			{
				this.m_activeRecipe = r;
				return true;
			}
		}
		return false;
	} // end loadRecipe()

	private void tryStartNewRecipe() 
	{
		this.m_activeRecipeIdentifier = null;
		this.m_activeRecipe = null;
		this.m_extrudeTime = 0;
		this.m_extrudeProgress = 0;
		
		List<ExtrusionRecipe> recipes = level.getRecipeManager().getAllRecipesFor(YATMRecipeTypes.EXTRUSION_RECIPE_TYPE.get());
		for (ExtrusionRecipe r : recipes)
		{
			if (r.canBeUsedOn(this.m_rawInventory))
			{
				this.m_activeRecipeIdentifier = r.getId().toString();
				this.m_activeRecipe = r;
				this.m_extrudeTime = r.getTimeInTicks();
				this.m_extrudeProgress = this.m_extrudeTime;
				r.startRecipe(this.m_rawInventory);
			}
		}
	} // end tryStartNewRecipe()
	


	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		if(this.m_activeRecipeIdentifier != null) 
		{
			tag.putString(ACTIVE_RECIPE_TAG_NAME, this.m_activeRecipeIdentifier);
		}
		tag.putInt(EXTRUDE_PROGRESS_TAG_NAME, this.m_extrudeProgress);
		tag.putInt(EXTRUDE_TIME_TAG_NAME, this.m_extrudeTime);
		// power tag here
		tag.put(INVENTORY_TAG_NAME, this.m_rawInventory.serializeNBT());
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if (tag.contains(ACTIVE_RECIPE_TAG_NAME))
		{
			this.m_activeRecipeIdentifier = tag.getString(ACTIVE_RECIPE_TAG_NAME);
		}
		if (tag.contains(EXTRUDE_PROGRESS_TAG_NAME))
		{
			this.m_extrudeProgress = tag.getInt(EXTRUDE_PROGRESS_TAG_NAME);
		}
		if (tag.contains(EXTRUDE_TIME_TAG_NAME))
		{
			this.m_extrudeTime = tag.getInt(EXTRUDE_TIME_TAG_NAME);
		}
		// power tag here
		if (tag.contains(INVENTORY_TAG_NAME))
		{
			this.m_rawInventory.deserializeNBT(tag.getCompound(INVENTORY_TAG_NAME));
		}
				
	} // end load()



	private boolean stackInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		if (slot == INPUT_SLOT || slot == DIE_SLOT)
		{
			return true;
		}
		if (slot == RESULT_SLOT || slot == INPUT_REMAINDER_SLOT)
		{
			return false;
		}
		if (slot == POWER_SLOT)
		{
			return true;
		}
		return false;
	} // end stackInsertionValidator()

	private void onStackInsertion(int slot, ItemStack itemStack)
	{
		if (slot == INPUT_SLOT || slot == DIE_SLOT)
		{
			this.m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
		}
		this.setChanged();
	} // end onStackInsertion
	
	private void onStackWithdrawal(int slot, int amount)
	{
		if (slot == DIE_SLOT || slot == RESULT_SLOT || slot == INPUT_REMAINDER_SLOT)
		{
			this.m_timeSinceRecheck = RECHECK_CRAFTING_PERIOD;
		}
		this.setChanged();
	} // end onStackInsertion



	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == ForgeCapabilities.ITEM_HANDLER)
		{
			if (side == null)
			{
				return this.m_inventoryLazyOptional.cast();
			}
			else if (side == Direction.DOWN)
			{
				return this.m_resultsSlotsLazyOptional.cast();
			}
			else
			{
				return this.m_inputsSlotsLazyOptional.cast();
			}
		}
		else
		{
			// try get component from POWER_SLOT, ...
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_inventoryLazyOptional = LazyOptional.of(() -> this.m_inventory);
		this.m_inputsSlotsLazyOptional = LazyOptional.of(() -> this.m_inputsSlots);
		this.m_resultsSlotsLazyOptional = LazyOptional.of(() -> this.m_resultsSlots);
	} // end reviceCaps()

	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_inventoryLazyOptional.invalidate();
		this.m_inputsSlotsLazyOptional.invalidate();
		this.m_resultsSlotsLazyOptional.invalidate();
	} // end invalidateCaps()

} // end class