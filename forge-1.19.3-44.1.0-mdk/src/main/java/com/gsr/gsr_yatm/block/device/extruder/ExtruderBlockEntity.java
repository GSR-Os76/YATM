package com.gsr.gsr_yatm.block.device.extruder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.ExtrusionRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.SlotUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class ExtruderBlockEntity extends CraftingDeviceBlockEntity<ExtrusionRecipe, Container>
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	public static final int DATA_SLOT_COUNT = 4;

	public static final int INPUT_SLOT = 0;
	public static final int DIE_SLOT = 1;
	public static final int RESULT_SLOT = 2;
	public static final int INPUT_REMAINDER_SLOT = 3;
	public static final int POWER_SLOT = 4;

	public static final int EXTRUDE_PROGRESS_SLOT = 0;
	public static final int EXTRUDE_TIME_SLOT = 1;
	public static final int CURRENT_STORED_SLOT = 2;
	public static final int CURRENT_CAPACITY_SLOT = 3;

	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";
	
	
	
	private final ContainerData m_data = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			return switch(index) 
			{
				case EXTRUDE_PROGRESS_SLOT -> ExtruderBlockEntity.this.m_craftProgress;
				case EXTRUDE_TIME_SLOT -> ExtruderBlockEntity.this.m_craftTime;
				case CURRENT_STORED_SLOT -> ExtruderBlockEntity.this.m_internalCurrentStorer.stored();
				case CURRENT_CAPACITY_SLOT ->  ExtruderBlockEntity.this.m_internalCurrentStorer.capacity();
				default -> throw new IllegalArgumentException("Unexpected value of: " + index);
			};

		} // end get()

		@Override
		public void set(int index, int value)
		{
			return;
		} // end set()

		@Override
		public int getCount()
		{
			return DATA_SLOT_COUNT;
		} // end getCount()

	};

	private LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> this.m_inventory);

	private final ConfigurableInventoryWrapper m_inputsSlots = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ INPUT_SLOT, DIE_SLOT });
	private LazyOptional<IItemHandler> m_inputsSlotsLazyOptional = LazyOptional.of(() -> this.m_inputsSlots);

	private final ConfigurableInventoryWrapper m_resultsSlots = new ConfigurableInventoryWrapper(m_inventory, new int[]
	{ RESULT_SLOT, INPUT_REMAINDER_SLOT });
	private LazyOptional<IItemHandler> m_resultsSlotsLazyOptional = LazyOptional.of(() -> this.m_resultsSlots);

	
	
	public ExtruderBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0);
	} // end constructor
	
	public ExtruderBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxCurrentTransfer)
	{
		super(YATMBlockEntityTypes.EXTRUDER.get(), blockPos, blockState, ExtruderBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.EXTRUSION.get());
		this.setup(currentCapacity, maxCurrentTransfer);
	} // end constructor

	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CURRENT_CAPACITY_TAG_NAME, this.m_internalCurrentStorer.capacity());
		tag.putInt(MAX_CURRENT_TAG_NAME, this.m_maxCurrentTransfer);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(CompoundTag tag)
	{
		int currentCapacity = 0;
		int maxCurrentTransfer = 0;
		
		if(tag.contains(CURRENT_CAPACITY_TAG_NAME)) 
		{
			currentCapacity = tag.getInt(CURRENT_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_CURRENT_TAG_NAME)) 
		{
			maxCurrentTransfer = tag.getInt(MAX_CURRENT_TAG_NAME);
		}
		this.setup(currentCapacity, maxCurrentTransfer);
	} // end setupFromNBT()
	
	private void setup(int currentCapacity, int maxCurrentTransfer) 
	{
		this.m_internalCurrentStorer = new CurrentUnitHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxCurrentTransfer = maxCurrentTransfer;		
	} // end setup()
	
	

	@Override
	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()

		@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
			return switch(slot) 
			{
				case ExtruderBlockEntity.INPUT_SLOT, ExtruderBlockEntity.DIE_SLOT -> true;
				case ExtruderBlockEntity.RESULT_SLOT, ExtruderBlockEntity.INPUT_REMAINDER_SLOT -> false;
				case ExtruderBlockEntity.POWER_SLOT -> SlotUtilities.isValidPowerSlotInsert(itemStack);
				default -> throw new IllegalArgumentException("Unexpected value of: " + slot);				
			};
	} // end itemInsertionValidator()



	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState)
	{	
		boolean changed = /* this.m_activeRecipe != null && */this.m_waitingForLoad ? false :this.doCrafting();
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

	@Override
	protected void setRecipeResults(ExtrusionRecipe from)
	{
		from.setResults(this.m_uncheckedInventory);
	} // end setRecipeResult()

	@Override
	protected boolean canUseRecipe(ExtrusionRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(ExtrusionRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory);
	} // end startRecipe()

	

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