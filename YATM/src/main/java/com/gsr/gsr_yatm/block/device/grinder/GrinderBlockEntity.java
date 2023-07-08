package com.gsr.gsr_yatm.block.device.grinder;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.grinding.GrindingRecipe;
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
	//public static final int TEST = 4;
	
	
	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";
	
	
	
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
				// TODO, note, this value was transmitted entirely, not low sixteen, the preported effective short limitation might not be accurate, or might only happen for dedicated server? more tests needed
				//case TEST -> 0b0111_1111_1111_1111_1111_1111_1111_1111;
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
			return GrinderBlockEntity.DATA_SLOT_COUNT;
		} // end getCount
	};
	
	
	
	public GrinderBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0);
	} // end constructor
	
	public GrinderBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxCurrentTransfer)
	{
		super(YATMBlockEntityTypes.GRINDER.get(), blockPos, blockState, GrinderBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.GRINDING.get());
		this.setup(currentCapacity, maxCurrentTransfer);
	} // end constructor

	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CURRENT_CAPACITY_TAG_NAME, this.m_internalCurrentStorer.capacity());
		tag.putInt(MAX_CURRENT_TAG_NAME, this.m_maxSafeCurrentTransfer);
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
		this.m_maxSafeCurrentTransfer = maxCurrentTransfer;		
	} // end setup()
	
	
	
	
	
	
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
		boolean changed = /* this.m_activeRecipe != null && */this.m_waitingForLoad ? false : this.doCrafting();
		
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


} // end class