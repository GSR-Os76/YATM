package com.gsr.gsr_yatm.block.device.grinder;

import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.recipe.GrindingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.SlotUtilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GrinderBlockEntity extends CraftingDeviceBlockEntity<GrindingRecipe, Container>
{	
	public static final int INVENTORY_SLOT_COUNT = 3;
	public static final int DATA_SLOT_COUNT = 4;

	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int POWER_SLOT = 2;
	
	public static final int GRIND_PROGESS_INDEX = 0;
	public static final int GRIND_TIME_INDEX = 1;
	public static final int CURRENT_STORED_INDEX = 2;
	public static final int CURRENT_CAPACITY_INDEX = 3;
	
	public static final String CURRENT_HANDLER_TAG_NAME = "current";
	
	
	private CurrentUnitHandler m_internalCurrentStorer;	
	
	protected ContainerData m_dataAccessor = new ContainerData()
	{
		@Override
		public int get(int index)
		{
			return switch(index) 
			{
				// seperate this out
				case GRIND_PROGESS_INDEX -> GrinderBlockEntity.this.m_craftProgress;
				case GRIND_TIME_INDEX -> GrinderBlockEntity.this.m_craftTime;
				// end
				// maybe start again
				case CURRENT_STORED_INDEX -> GrinderBlockEntity.this.m_internalCurrentStorer.stored();
				case CURRENT_CAPACITY_INDEX -> GrinderBlockEntity.this.m_internalCurrentStorer.capacity();
				// maybe end again
				default -> throw new IllegalArgumentException("Unexpected value of: " + index);
			};
		}

		@Override
		public void set(int index, int value)
		{
			return;
		} // end set()

		@Override
		public int getCount()
		{
			return GrinderBlockEntity.DATA_SLOT_COUNT;
		} // end getCount
	};
	
	
	
	public GrinderBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, DeviceTierConstants.STEEL_CURRENT_CAPACITY, DeviceTierConstants.STEEL_MAX_CURRENT);
	} // end constructor
	
	public GrinderBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxCurrentTransfer)
	{
		super(YATMBlockEntityTypes.GRINDER.get(), blockPos, blockState, GrinderBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.GRINDING.get());
		this.m_internalCurrentStorer = new CurrentUnitHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxCurrentTransfer = maxCurrentTransfer;
	} // end constructor

	
	
	@Override
	public ContainerData getDataAccessor()
	{
		return this.m_dataAccessor;
	} // end getDataAccussor()

	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch(slot) 
		{
			case GrinderBlockEntity.INPUT_SLOT -> true;
			case GrinderBlockEntity.POWER_SLOT -> SlotUtilities.isValidPowerSlotInsert(itemStack);
			case GrinderBlockEntity.RESULT_SLOT -> false;
			
			default -> throw new IllegalArgumentException("Unexpected value: " + slot);
		};
	} // end itemInsertionValidator()

	
	
	@Override
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		boolean changed = this.m_waitingForLoad ? false : this.doCrafting();
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()
	
	@Override
	protected void setRecipeResults(GrindingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory);
	} // end setRecipeResults()

	@Override
	protected boolean canUseRecipe(GrindingRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(GrindingRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory);
	} // end startRecipe()
	
	
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		if(this.m_internalCurrentStorer.stored() > 0) 
		{
			tag.put(CURRENT_HANDLER_TAG_NAME, this.m_internalCurrentStorer.serializeNBT());
		}
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if(tag.contains(CURRENT_HANDLER_TAG_NAME)) 
		{
			 this.m_internalCurrentStorer.deserializeNBT(tag.getCompound(CURRENT_HANDLER_TAG_NAME));
		}		
	} // end load()
} // end class