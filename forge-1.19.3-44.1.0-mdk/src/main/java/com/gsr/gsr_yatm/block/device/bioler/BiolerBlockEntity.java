package com.gsr.gsr_yatm.block.device.bioler;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YetAnotherTechMod;
import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.bioling.BiolingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.SlotUtilities;
import com.gsr.gsr_yatm.utilities.network.AccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.CurrentHandlerContainerData;
import com.gsr.gsr_yatm.utilities.network.FluidHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class BiolerBlockEntity extends CraftingDeviceBlockEntity<BiolingRecipe, Container>
{
	public static final int DATA_SLOT_COUNT = 11;
	public static final int INVENTORY_SLOT_COUNT = 4;
	
	
	public static final int INPUT_SLOT = 0;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;
	public static final int POWER_SLOT = 3;

	// this is tragic, these are mutable, but should be strictly set, but also they should be defined dynamically by the conainerData creation, but they should also be guaranteed consistent across instances, this contract is something weak 
	private static AccessSpecification s_craftData;
	private static AccessSpecification s_resultTankData;
	private static AccessSpecification s_currentData;
	private static AccessSpecification s_drainResultTankData;
	
	public static AccessSpecification getCraftData() 
	{ 
		return s_craftData;
	} // end getCraftData()
	public static AccessSpecification getResultTankData() 
	{ 
		return s_resultTankData;
	} // end getResultTankData()
	public static AccessSpecification getCurrentData() 
	{ 
		return s_currentData;
	} // end getCurrenData()
	public static AccessSpecification getDrainResultTankData() 
	{ 
		return s_drainResultTankData;
	} // end getDrainResultTankData()
	
	public static final int HAS_REMAINDER_FLAG_INDEX = 1;

	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";
	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";

	public static final String TANK_TAG_NAME = "resultTank";



	private FluidTank m_rawResultTank;
	private IFluidHandler m_resultTank;
	
	private int m_maxFluidTransferRate;

	// TODO, add saving this, nice but not game breaking
	private int m_resultTankDrainCountDown = 0;
	private int m_initialDrainResultTankTransferSize = 0;
		
	private ContainerData m_data;
	
	
	
	public BiolerBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0, 0, 0);
	} // end constructor
	
	public BiolerBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxCurrentTransfer, int fluidCapacity, int maxFluidTransferRate)
	{
		super(YATMBlockEntityTypes.BIOLER.get(), blockPos, blockState, BiolerBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.BIOLING.get());
		this.setup(currentCapacity, maxCurrentTransfer, fluidCapacity, maxFluidTransferRate);
		this.m_data = this.createContainerData();
	} // end constructor

	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		YetAnotherTechMod.LOGGER.info("writing setup to");
		CompoundTag tag = new CompoundTag();
		tag.putInt(BiolerBlockEntity.CURRENT_CAPACITY_TAG_NAME, this.m_internalCurrentStorer.capacity());
		tag.putInt(BiolerBlockEntity.MAX_CURRENT_TAG_NAME, this.m_maxCurrentTransfer);
		tag.putInt(BiolerBlockEntity.TANK_CAPACITY_TAG_NAME, this.m_rawResultTank.getCapacity());
		tag.putInt(BiolerBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(CompoundTag tag)
	{
		YetAnotherTechMod.LOGGER.info("reading setup from");
		
		int currentCapacity = 0;
		int maxCurrentTransfer = 0;
		int fluidCapacity = 0;
		int maxFluidTransferRate = 0;
		
		if(tag.contains(BiolerBlockEntity.CURRENT_CAPACITY_TAG_NAME)) 
		{
			currentCapacity = tag.getInt(BiolerBlockEntity.CURRENT_CAPACITY_TAG_NAME);
		}
		if(tag.contains(BiolerBlockEntity.MAX_CURRENT_TAG_NAME)) 
		{
			maxCurrentTransfer = tag.getInt(BiolerBlockEntity.MAX_CURRENT_TAG_NAME);
		}
		if(tag.contains(BiolerBlockEntity.TANK_CAPACITY_TAG_NAME)) 
		{
			fluidCapacity = tag.getInt(BiolerBlockEntity.TANK_CAPACITY_TAG_NAME);
		}
		if(tag.contains(BiolerBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME)) 
		{
			maxFluidTransferRate = tag.getInt(BiolerBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		this.setup(currentCapacity, maxCurrentTransfer, fluidCapacity, maxFluidTransferRate);
	} // end setupFromNBT()
	
	private void setup(int currentCapacity, int maxCurrentTransfer, int fluidCapacity, int maxFluidTransferRate) 
	{
		this.m_rawResultTank = new FluidTank(fluidCapacity);
		this.m_resultTank = new ConfigurableTankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);
		this.m_internalCurrentStorer = new CurrentUnitHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxCurrentTransfer = maxCurrentTransfer;
		this.m_maxFluidTransferRate = maxFluidTransferRate;
		this.m_data = this.createContainerData();
	} // end setup()
	
	protected ContainerData createContainerData() 
	{
		ContainerDataBuilder builder = new ContainerDataBuilder();
		BiolerBlockEntity.s_craftData = builder.addContainerData(this.m_craftProgressC);
		BiolerBlockEntity.s_resultTankData = builder.addContainerData(new FluidHandlerContainerData(this.m_resultTank, 0));
		BiolerBlockEntity.s_currentData = builder.addContainerData(new CurrentHandlerContainerData(this.m_internalCurrentStorer));
		AccessSpecification cd = builder.addPropety(() -> this.m_resultTankDrainCountDown, (i) -> {});
		AccessSpecification ts = builder.addPropety(() -> this.m_initialDrainResultTankTransferSize, (i) -> {});
		BiolerBlockEntity.s_drainResultTankData = new AccessSpecification(cd.startIndex(), ts.endIndex());
		return builder.build();
	} // end createContainerData()
	
	
	
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
			case BiolerBlockEntity.INPUT_SLOT -> true;
			case BiolerBlockEntity.DRAIN_RESULT_TANK_SLOT -> SlotUtilities.isValidTankDrainSlotInsert(itemStack);
			case BiolerBlockEntity.POWER_SLOT -> SlotUtilities.isValidPowerSlotInsert(itemStack);
			default -> throw new IllegalArgumentException("Unexpected value of: " + slot);
		};
	} // end itemInsertionValidator()

	

	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState)
	{
		boolean changed = this.m_waitingForLoad ? false : this.doCrafting();
		changed |= this.doDrainResultTank();
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doDrainResultTank() 
	{
		boolean changed = false;		
		if (this.m_resultTankDrainCountDown > 0)
		{
			this.m_resultTankDrainCountDown = SlotUtilities.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_initialDrainResultTankTransferSize, this.m_resultTankDrainCountDown, this.m_maxFluidTransferRate);
			if (this.m_resultTankDrainCountDown <= 0)
			{
				this.m_initialDrainResultTankTransferSize = 0;
			}
			changed = true;
		}
		if(m_initialDrainResultTankTransferSize == 0) 
		{
			this.m_initialDrainResultTankTransferSize = SlotUtilities.queueToDrainToSlot(this.m_inventory, DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_maxFluidTransferRate);
			this.m_resultTankDrainCountDown = this.m_initialDrainResultTankTransferSize;
			changed = true;
		}
		return changed;
	} // end doDrainResultTank()
	
	@Override
	protected void setRecipeResults(BiolingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory, this.m_resultTank);
	} // end setRecipeResults()

	@Override
	protected boolean canUseRecipe(BiolingRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory, this.m_resultTank);
	} // end canUseRecipe()
	
	@Override
	protected void startRecipe(BiolingRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory);
	} // end startRecipe()
	
	
	
	

	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.put(BiolerBlockEntity.TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
	} // end saveAdditional()
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(BiolerBlockEntity.TANK_TAG_NAME)) 
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(BiolerBlockEntity.TANK_TAG_NAME));
		}
	} // end load()

} // end class 