package com.gsr.gsr_yatm.block.device.crucible;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.IComponent;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.capability.item.ConfigurableInventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.FluidHandlerContainerData;
import com.gsr.gsr_yatm.utilities.network.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.PropertyContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class CrucibleBlockEntity extends CraftingDeviceBlockEntity<MeltingRecipe, Container>
{
	public static final int INVENTORY_SLOT_COUNT = 3;
	
	public static final int INPUT_SLOT = 0;
	public static final int HEAT_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;

	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String DRAIN_PROGRESS_SPEC_KEY = "drainProgress";
	public static final String TANK_DATA_SPEC_KEY = "tankInfo";
	public static final String TEMPERATURE_SPEC_KEY = "temperature";
	public static final String MAX_TEMPERATURE_SPEC_KEY = "maxTemperature";
	
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";
	private static final String MAX_TEMPERATURE_TAG_NAME = "maxTemperature";
	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	
	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
	private static final String BURN_TEMP_TAG_NAME = "burnTemperature";
	private static final String DRAIN_RESULT_COUNT_DOWN_TAG_NAME = "drainResultCount";
	private static final String DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME = "drainResultInitial";
	private static final String RESULT_TANK_TAG_NAME = "resultTank";
	private static final String TEMPERAURE_TAG_NAME = "temperature";
	

	
	private static final int DRAIN_RECHECK_PERIOD = 40;
	
	
	
	// TODO, force recheck when neighbor changes
	private int drainRecheckCounter = 0;
	private int m_burnProgress = 0;
	private int m_burnTime = 0;
	private int m_burnTemperature;
	private int m_maxTemperature;
	private int m_maxFluidTransferRate;
	private int m_drainResultTankCountDown = 0;
	private int m_drainResultTankInitialTransferSize = 0;	
	
	// TODO, might need next three to be in the constructor
	private final IItemHandler m_drainResultTankSlot = ConfigurableInventoryWrapper.Builder.of().inventory(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT}).build();
	private final IItemHandler m_inputSlot = ConfigurableInventoryWrapper.Builder.of().inventory(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.INPUT_SLOT}).build();;
	private final IItemHandler m_heatingSlot = ConfigurableInventoryWrapper.Builder.of().inventory(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.HEAT_SLOT}).build();;
	private FluidTank m_rawResultTank;
	private ConfigurableTankWrapper m_resultTank;	
	private OnChangedHeatHandler m_heatHandler = new OnChangedHeatHandler(IHeatHandler.getAmbientTemp(), (i) -> this.setChanged());
	
	private @Nullable ItemStack m_drainResultComponentStack = null;
	private @Nullable IComponent m_resultDrainComponent = null;
	private @NotNull List<LazyOptional<?>> m_drainResultAttachments = new ArrayList<>();
	private @Nullable IComponent m_heatComponent = null;
	private @Nullable LazyOptional<IHeatHandler> m_heatComponentAttachment = null;
	
	private @NotNull LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inventory);
	private @NotNull LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_drainResultTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_inputSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inputSlot);
	private @NotNull LazyOptional<IItemHandler> m_heatingSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatingSlot);
	private @NotNull LazyOptional<IFluidHandler> m_resultTankLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_resultTank);
	private @NotNull LazyOptional<IHeatHandler> m_heatLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatHandler);
	
	
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(CrucibleBlockEntity.BURN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(CrucibleBlockEntity.DRAIN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(CrucibleBlockEntity.TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(CrucibleBlockEntity.TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(CrucibleBlockEntity.MAX_TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
			));
	protected @NotNull ContainerData m_data;
	
	

	public CrucibleBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
	{
		this(Objects.requireNonNull(blockPos), Objects.requireNonNull(blockState), DeviceTierConstants.EMPTY);
	} // end constructor

	public CrucibleBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull DeviceTierConstants constants)
	{
		super(YATMBlockEntityTypes.CRUCIBLE.get(), Objects.requireNonNull(blockPos), Objects.requireNonNull(blockState), CrucibleBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.MELTING.get());
		this.setup(constants.maxFluidTransferRate(), constants.maxTemperature(), constants.tankCapacity());
	} // end constructor

	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CrucibleBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		tag.putInt(CrucibleBlockEntity.MAX_TEMPERATURE_TAG_NAME, this.m_maxTemperature);
		tag.putInt(CrucibleBlockEntity.TANK_CAPACITY_TAG_NAME, this.m_rawResultTank.getCapacity());
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		int tankCapacity = 0;
		int maxFluidTransferRate = 0;
		int maxTemperature = 0;
		
		if (tag.contains(CrucibleBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME))
		{
			maxFluidTransferRate = tag.getInt(CrucibleBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		if (tag.contains(CrucibleBlockEntity.MAX_TEMPERATURE_TAG_NAME))
		{
			maxTemperature = tag.getInt(CrucibleBlockEntity.MAX_TEMPERATURE_TAG_NAME);
		}
		if (tag.contains(CrucibleBlockEntity.TANK_CAPACITY_TAG_NAME))
		{
			tankCapacity = tag.getInt(CrucibleBlockEntity.TANK_CAPACITY_TAG_NAME);
		}
		
		this.setup(maxFluidTransferRate, maxTemperature, tankCapacity);
	} // end setupFromNBT()

	private void setup(@NotNegative int maxFluidTransferRate, @NotNegative int maxTemperature, @NotNegative int tankCapacity)
	{
		this.m_maxFluidTransferRate = Contract.NotNegative(maxFluidTransferRate);
		this.m_maxTemperature = Contract.NotNegative(maxTemperature);
		this.m_rawResultTank = new FluidTank(Contract.NotNegative(tankCapacity));
		this.m_resultTank = new ConfigurableTankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);
		this.m_resultTankLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_resultTank);
		
		ContainerDataBuilder cdb = new ContainerDataBuilder();
		cdb.addPropety(() -> this.m_craftProgress, (i) -> {});
		cdb.addPropety(() -> this.m_craftTime, (i) -> {});		
		cdb.addPropety(() -> this.m_burnProgress, (i) -> {});
		cdb.addPropety(() -> this.m_burnTime, (i) -> {});		
		cdb.addPropety(() -> this.m_drainResultTankCountDown, (i) -> {});
		cdb.addPropety(() -> this.m_drainResultTankInitialTransferSize, (i) -> {});
		cdb.addContainerData(new FluidHandlerContainerData(this.m_rawResultTank, 0));
		cdb.addPropety(() -> this.m_heatHandler.getTemperature(), (i) -> {});
		cdb.addPropety(() -> this.m_maxTemperature, (i) -> {});
		this.m_data = cdb.build();
	} // end setup()
	
	
	
	@Override
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch (slot)
		{
			case CrucibleBlockEntity.HEAT_SLOT -> SlotUtil.isValidHeatingSlotInsert(itemStack);
			case CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(itemStack);
			default -> true;
		};
	} // end itemInsertionValidator()
	
	

	@Override
	protected void onItemInsertion(int slot, ItemStack stack)
	{
		super.onItemInsertion(slot, stack);
		if(slot == CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT) 
		{
			this.updateDrainResultComponent();
		}
		else if(slot == CrucibleBlockEntity.HEAT_SLOT) 
		{
			this.updateHeatComponent();
		}
	} // end onItemInsertion()

	@Override
	protected void onItemWithdrawal(int slot, ItemStack stack)
	{
		super.onItemWithdrawal(slot, stack);
		if(slot == CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT) 
		{
			this.updateDrainResultComponent();
		}
		else if(slot == CrucibleBlockEntity.HEAT_SLOT) 
		{
			this.updateHeatComponent();
		}
	} // end onItemWithdrawal()
	
	protected void updateDrainResultComponent() 
	{
		if(this.m_resultDrainComponent != null) 
		{
			this.removeDrainResultAttachments();
			this.m_resultDrainComponent = null;
			this.m_drainResultComponentStack = null;
		}
		
		ItemStack drainResultStack = this.m_inventory.getStackInSlot(CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT);
		if(drainResultStack.getItem() instanceof IComponent fc) 
		{
			this.m_resultDrainComponent = fc;
			this.m_drainResultComponentStack = drainResultStack;
		}
		
	} // end updateDrainResultComponent()

	protected void updateHeatComponent() 
	{
		if(this.m_heatComponent != null) 
		{
			this.m_heatComponentAttachment.invalidate();
			this.m_heatComponentAttachment = null;
			this.m_heatComponent = null;
		}
		
		ItemStack heatSlot = this.m_inventory.getStackInSlot(CrucibleBlockEntity.HEAT_SLOT);
		if(heatSlot.getItem() instanceof IComponent hc && hc.getValidCapabilities().contains(YATMCapabilities.HEAT))
		{
			this.m_heatComponent = (IComponent)heatSlot.getItem();
			this.m_heatComponentAttachment = LazyOptional.of(() -> this.m_heatHandler);
			this.m_heatComponent.attachRecievingCapability(heatSlot, YATMCapabilities.HEAT, this.m_heatComponentAttachment);
		}
	} // end updateHeatComponent()
	
	protected void removeDrainResultAttachments()
	{
		for(LazyOptional<?> l : this.m_drainResultAttachments) 
		{
			this.m_resultDrainComponent.removeRecievingCapability(this.m_drainResultComponentStack, l);
		}
		this.m_drainResultAttachments = new ArrayList<>();
	} // end removeDrainResultAttachments()
	
	private void removeInvalidatedDrainResultAttachments(LazyOptional<?> lazyOptional)
	{
			if(this.m_drainResultAttachments.contains(lazyOptional)) 
			{
				this.m_drainResultAttachments.remove(lazyOptional);
				this.m_resultDrainComponent.removeRecievingCapability(this.m_drainResultComponentStack, lazyOptional);
			}
	} // removeInvalidatedDrainResultAttachments()
	
	
	
	@Override
	public void serverTick(Level level, BlockPos position, BlockState state)
	{
		super.serverTick(level, position, state);

		boolean changed = this.doHeat();
		changed |= this.m_waitingForLoad 
				|| (this.m_activeRecipe != null 
					&& (this.m_heatHandler.getTemperature() < this.m_activeRecipe.getTemperature())
					)
					? false 
					: this.doCrafting();
		changed |= this.doDrainResultTank();
		
		if(++drainRecheckCounter >= CrucibleBlockEntity.DRAIN_RECHECK_PERIOD) 
		{
			this.tryAttachResultTankDrainage(level, position);			
		}
		
		if (changed)
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doHeat()
	{
		boolean changed = false;
		
		if (this.m_burnProgress != 0)
		{
			if (--this.m_burnProgress <= 0)
			{
				this.m_burnTemperature = 0;
				this.m_burnProgress = 0;
				this.m_burnTime = 0;
			}
			this.m_heatHandler.heat(this.m_burnProgress > 0 ? this.m_burnTemperature : IHeatHandler.getAmbientTemp());
			changed = true;
		}
		else if (SlotUtil.getHeatingBurnTime(this.m_inventory.getStackInSlot(CrucibleBlockEntity.HEAT_SLOT)) > 0)
		{
			ItemStack i = this.m_inventory.extractItem(CrucibleBlockEntity.HEAT_SLOT, 1, false);
			if (i.hasCraftingRemainingItem())
			{
				InventoryUtilities.insertItemOrDrop(this.level, this.worldPosition, this.m_inventory, HEAT_SLOT, i.getCraftingRemainingItem());
			}

			this.m_burnTime = SlotUtil.getHeatingBurnTime(i);
			this.m_burnProgress = this.m_burnTime;
			this.m_burnTemperature = SlotUtil.getHeatingTemperature(i);
			changed = true;
		}
		else 
		{
			if(this.m_heatComponent != null) 
			{
				this.m_heatHandler.heat(IHeatHandler.getAmbientTemp());
			}
		}
		return changed;
	} // end doAcceptPower()

	private boolean doDrainResultTank()
	{
		boolean changed = false;
		if (this.m_drainResultTankCountDown > 0)
		{
			this.m_drainResultTankCountDown = SlotUtil.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_drainResultTankInitialTransferSize, this.m_drainResultTankCountDown, this.m_maxFluidTransferRate);
			if (this.m_drainResultTankCountDown <= 0)
			{
				this.m_drainResultTankInitialTransferSize = 0;
			}
			changed = true;
		}
		if (m_drainResultTankInitialTransferSize == 0)
		{
			this.m_drainResultTankInitialTransferSize = SlotUtil.queueToDrainToSlot(this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_resultTank, 0, this.m_maxFluidTransferRate);
			this.m_drainResultTankCountDown = this.m_drainResultTankInitialTransferSize;

			changed = true;
		}
		return changed;
	} // end doDrainInputTank()

	private void tryAttachResultTankDrainage(@NotNull Level level, @NotNull BlockPos position) 
	{
		BlockEntity be = level.getBlockEntity(position);
		if(this.m_resultDrainComponent == null || be == null) 
		{
			return;
		}
		
		for(Capability<?> cap : this.m_resultDrainComponent.getValidCapabilities()) 
		{
			this.t(be, cap);
		}
	} // end tryAttachResultTankDrainage()
	
	// used so generic types are properly constrained to the same type argument for calling attachRecievingCapability()
	private <T> void t(BlockEntity be, Capability<T> cap) 
	{
		LazyOptional<T> l = be.getCapability(cap);
		if(l.isPresent() && !this.m_drainResultAttachments.contains(l)) 
		{
			this.m_resultDrainComponent.attachRecievingCapability(this.m_drainResultComponentStack, cap, l);
			this.m_drainResultAttachments.add(l);
			l.addListener(this::removeInvalidatedDrainResultAttachments);
		}
	} // end t()
	
	

	@Override
	protected void setRecipeResults(@NotNull MeltingRecipe from)
	{
		from.setResults(this.m_rawResultTank);
	} // end setRecipeResults()

	@Override
	protected boolean canUseRecipe(@NotNull MeltingRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory, this.m_resultTank);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(@NotNull MeltingRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory);
	} // end startRecipe()



	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);

		tag.putInt(CrucibleBlockEntity.TEMPERAURE_TAG_NAME, this.m_heatHandler.getTemperature());
		if(this.m_burnProgress > 0 && this.m_burnTime > 0) 
		{
			tag.putInt(CrucibleBlockEntity.BURN_TIME_ELAPSED_TAG_NAME, this.m_burnProgress);
			tag.putInt(CrucibleBlockEntity.BURN_TIME_INITIAL_TAG_NAME, this.m_burnTime);
			tag.putInt(CrucibleBlockEntity.BURN_TEMP_TAG_NAME, this.m_burnTemperature);
			
		}
		if (this.m_rawResultTank.getFluidAmount() > 0)
		{
			tag.put(CrucibleBlockEntity.RESULT_TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
		}
		if (this.m_drainResultTankCountDown > 0 && this.m_drainResultTankInitialTransferSize > 0)
		{
			tag.putInt(CrucibleBlockEntity.DRAIN_RESULT_COUNT_DOWN_TAG_NAME, this.m_drainResultTankCountDown);
			tag.putInt(CrucibleBlockEntity.DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME, this.m_drainResultTankInitialTransferSize);
		}
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if (tag.contains(CrucibleBlockEntity.TEMPERAURE_TAG_NAME))
		{
			this.m_heatHandler.setTemperature(tag.getInt(CrucibleBlockEntity.TEMPERAURE_TAG_NAME));
		}	
		if (tag.contains(CrucibleBlockEntity.BURN_TIME_ELAPSED_TAG_NAME) && tag.contains(CrucibleBlockEntity.BURN_TIME_INITIAL_TAG_NAME))
		{
			this.m_burnProgress = tag.getInt(CrucibleBlockEntity.BURN_TIME_ELAPSED_TAG_NAME);
			this.m_burnTime = tag.getInt(CrucibleBlockEntity.BURN_TIME_INITIAL_TAG_NAME);
			this.m_burnTemperature = tag.getInt(CrucibleBlockEntity.BURN_TEMP_TAG_NAME);
		}
		if (tag.contains(CrucibleBlockEntity.RESULT_TANK_TAG_NAME))
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(CrucibleBlockEntity.RESULT_TANK_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.DRAIN_RESULT_COUNT_DOWN_TAG_NAME) && tag.contains(CrucibleBlockEntity.DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_drainResultTankCountDown = tag.getInt(CrucibleBlockEntity.DRAIN_RESULT_COUNT_DOWN_TAG_NAME);
			this.m_drainResultTankInitialTransferSize = tag.getInt(CrucibleBlockEntity.DRAIN_RESULT_TRANSFER_INITIAL_TAG_NAME);
		}
		
		this.updateHeatComponent();
		this.updateDrainResultComponent();
	} // end load()

	
	
	// capabilities
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(side == null) 
		{
			if(cap == ForgeCapabilities.ITEM_HANDLER) 
			{
				return this.m_inventoryLazyOptional.cast();
			}
			else if(cap == ForgeCapabilities.FLUID_HANDLER) 
			{
				return this.m_resultTankLazyOptional.cast();
			}
			else if(cap == YATMCapabilities.HEAT) 
			{
				return this.m_heatLazyOptional.cast();
			}
		}		
		else if (side == Direction.UP && cap == ForgeCapabilities.ITEM_HANDLER) 
		{				
			return this.m_inputSlotLazyOptional.cast();
		}
		else if(Direction.Plane.HORIZONTAL.test(side))
		{				
			if(this.m_heatComponent != null) 
			{
				LazyOptional<T> c = this.m_heatingSlot.getStackInSlot(0).getCapability(cap);
				if(c.isPresent()) 
				{
					return c;
				}
			}
			else if (cap == ForgeCapabilities.ITEM_HANDLER) 
			{
				return this.m_heatingSlotLazyOptional.cast();
			}
		}
		else if(side == Direction.DOWN && cap == ForgeCapabilities.ITEM_HANDLER) 
		{
			return this.m_drainResultTankSlotLazyOptional.cast();
		}
		else if(cap == YATMCapabilities.HEAT) 
		{
			return this.m_heatLazyOptional.cast();
		}
		
		return super.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_inventoryLazyOptional.invalidate();
		this.m_drainResultTankSlotLazyOptional.invalidate();
		this.m_inputSlotLazyOptional.invalidate();
		this.m_heatingSlotLazyOptional.invalidate();
		this.m_resultTankLazyOptional.invalidate();
		this.m_heatLazyOptional.invalidate();
		
		this.removeDrainResultAttachments();
		if(this.m_heatComponentAttachment != null) 
		{
			this.m_heatComponentAttachment.invalidate();
		}
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_inventoryLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inventory);
		this.m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_drainResultTankSlot);
		this.m_inputSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inputSlot);
		this.m_heatingSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatingSlot);
		this.m_resultTankLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_resultTank);
		this.m_heatLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatHandler);
	} // end reviveCaps()
	
} // end class