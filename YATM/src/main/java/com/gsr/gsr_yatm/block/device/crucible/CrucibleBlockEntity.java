package com.gsr.gsr_yatm.block.device.crucible;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.behaviors.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.HeatingManager;
import com.gsr.gsr_yatm.block.device.behaviors.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.OutputComponentManager;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class CrucibleBlockEntity extends CraftingDeviceBlockEntity<MeltingRecipe, Container>
{
	// TODO, make crucible consume some heat to start a recipe
	// TODO, make recipe progress reverse slowly when heat is lacking?
	public static final int INVENTORY_SLOT_COUNT = 3;
	
	public static final int INPUT_SLOT = 0;
	public static final int HEAT_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;

	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String DRAIN_PROGRESS_SPEC_KEY = "drainProgress";
	public static final String TANK_DATA_SPEC_KEY = "tankInfo";
	public static final String TEMPERATURE_SPEC_KEY = "temperature";
	public static final String MAX_TEMPERATURE_SPEC_KEY = "maxTemperature";
	
//	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
//	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
//	private static final String BURN_TEMP_TAG_NAME = "burnTemperature";
	private static final String DRAIN_RESULT_MANAGER_TAG_NAME = "drainResultManager";
	private static final String HEATING_MANAGER_TAG_NAME = "heatingManager";
	private static final String RESULT_TANK_TAG_NAME = "resultTank";
	private static final String TEMPERAURE_TAG_NAME = "temperature";
	private static final String TICKS_PERFORMED_TAG_NAME = "ticksPerformed";
	private static final String TICKS_SCHEDULED_TAG_NAME = "ticksScheduled";
	
	//private static final int DRAIN_RECHECK_PERIOD = YATMConfigs.CRUCIBLE_DRAIN_RECHECK_PERIOD.get();
	
	
	private int m_ticksPerformed = 0;
	private float m_ticksScheduled = 0f;	
	
	private final int m_maxTemperature = YATMConfigs.CRUCIBLE_MAX_TEMPERATURE.get();
	private final int m_maxFluidTransferRate = YATMConfigs.CRUCIBLE_MAX_FLUID_TRANSFER_RATE.get();
	private int drainRecheckCounter = 0;
	
	private final IItemHandler m_drainResultTankSlot = InventoryWrapper.Builder.of().inventory(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT}).build();
	private final IItemHandler m_inputSlot = InventoryWrapper.Builder.of().inventory(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.INPUT_SLOT}).build();;
	private final IItemHandler m_heatingSlot = InventoryWrapper.Builder.of().inventory(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.HEAT_SLOT}).build();;
	private FluidTank m_rawResultTank = new FluidTank(YATMConfigs.CRUCIBLE_RESULT_TANK_CAPACITY.get());
	private TankWrapper m_resultTank = new TankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);	
	private OnChangedHeatHandler m_heatHandler = new OnChangedHeatHandler(IHeatHandler.getAmbientTemp(), (i) -> this.setChanged(), DeviceBlockEntity::deviceHeatEquation);
	
	private boolean m_updateDrainResultComponentQueued = false;
	private boolean m_updateHeatComponentQueued = false;
	
	private @NotNull LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inventory);
	private @NotNull LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_drainResultTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_inputSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inputSlot);
	private @NotNull LazyOptional<IItemHandler> m_heatingSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatingSlot);
	private @NotNull LazyOptional<IFluidHandler> m_resultTankLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_resultTank);
	private @NotNull LazyOptional<IHeatHandler> m_heatLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatHandler);
	
	private @NotNull HeatingManager m_heatingManager = new HeatingManager(this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_heatHandler);
	private @NotNull DrainTankManager m_drainResultTankManager = new DrainTankManager(this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_resultTank, this.m_maxFluidTransferRate);
	private @NotNull OutputComponentManager m_drainResultComponentManager = new OutputComponentManager(this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT);
	private @NotNull InputComponentManager<IHeatHandler> m_heatComponentManager = new InputComponentManager<>(this.m_inventory, CrucibleBlockEntity.HEAT_SLOT, this.m_heatHandler, YATMCapabilities.HEAT);
	
	
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(CrucibleBlockEntity.BURN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(CrucibleBlockEntity.DRAIN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(CrucibleBlockEntity.TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(CrucibleBlockEntity.TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(CrucibleBlockEntity.MAX_TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
			));
	protected final @NotNull ContainerData m_data = new ContainerDataBuilder()
			.addProperty(() -> this.m_craftCountDown, (i) -> {})
			.addProperty(() -> this.m_craftTime, (i) -> {})
			.addProperty(() -> this.m_heatingManager.burnProgress(), (i) -> {})
			.addProperty(() -> this.m_heatingManager.burnTime(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.initial(), (i) -> {})
			.addContainerData(new FluidHandlerContainerData(this.m_rawResultTank, 0))
			.addProperty(() -> this.m_heatHandler.getTemperature(), (i) -> {})
			.addProperty(() -> this.m_maxTemperature, (i) -> {})
			.build();


	public CrucibleBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
	{
		super(YATMBlockEntityTypes.CRUCIBLE.get(), Objects.requireNonNull(blockPos), Objects.requireNonNull(blockState), CrucibleBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.MELTING.get());
	} // end constructor
	
	
	
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
			this.m_updateDrainResultComponentQueued = true;
			// this.updateDrainResultComponent();
		}
		else if(slot == CrucibleBlockEntity.HEAT_SLOT) 
		{
			this.m_updateHeatComponentQueued = true;
			// this.updateHeatComponent();
		}
	} // end onItemInsertion()

	@Override
	protected void onItemWithdrawal(int slot, ItemStack stack)
	{
		super.onItemWithdrawal(slot, stack);
		if(slot == CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT) 
		{
			this.m_updateDrainResultComponentQueued = true;
			// this.updateDrainResultComponent();
		}
		else if(slot == CrucibleBlockEntity.HEAT_SLOT) 
		{
			this.m_updateHeatComponentQueued = true;
			// this.updateHeatComponent();
		}
	} // end onItemWithdrawal()

	
	
	@Override
	public void serverTick(Level level, BlockPos position, BlockState state)
	{
		// TODO, possibly send an update packet to who it may concern when a component is updated, to prevent lag in them noticing it
		if(this.m_updateDrainResultComponentQueued) 
		{
			this.m_drainResultComponentManager.updateComponent();
			this.m_updateDrainResultComponentQueued = false;
		}
		if(this.m_updateHeatComponentQueued) 
		{
			this.m_heatComponentManager.updateComponent();
			this.m_updateHeatComponentQueued = false;			
		}
		
		
		
		boolean changed = this.m_heatingManager.tick(level, position);
		if(!this.m_waitingForLoad) 
		{
			
			if(this.m_activeRecipe != null && !this.m_activeRecipe.matches(this.m_uncheckedInventory, this.m_resultTank)) 
			{	
				this.clearCurrentRecipe();
			}
			if(this.m_activeRecipe == null || this.m_activeRecipe.canTick(this.m_heatHandler)) 
			{
				this.doCrafting();
				if(this.m_activeRecipe != null) 
				{
					this.m_ticksPerformed += 1;
					this.m_ticksScheduled += ((float)this.m_heatHandler.getTemperature()) / ((float)this.m_activeRecipe.getTemperature());
					int bonusTicks = ((int)this.m_ticksScheduled) - this.m_ticksPerformed;
					for(int i = 0; (this.m_activeRecipe != null) && (i < bonusTicks); i++) 
					{
						this.doCrafting();
						this.m_ticksPerformed += 1;
					}
				}
			}		
		}
		changed |= this.m_drainResultTankManager.tick(level, position);
		
		if(++drainRecheckCounter >= YATMConfigs.CRUCIBLE_DRAIN_RECHECK_PERIOD.get()) 
		{
			this.m_drainResultComponentManager.tryAttach(level, position, Direction.DOWN);
		}
		
		if (changed)
		{
			this.setChanged();
		}
		
		boolean shouldBeLit = this.m_heatHandler.getTemperature() > 500;
		if(state.getValue(CrucibleBlock.LIT) ^ shouldBeLit) 
		{
			level.setBlock(position, state.setValue(CrucibleBlock.LIT, shouldBeLit), Block.UPDATE_CLIENTS);
		}
	} // end serverTick()

//	private boolean doHeat()
//	{
//		boolean changed = false;
//		
//		int amb = IHeatHandler.getAmbientTemp();
//		this.m_heatHandler.heat(amb);
//		
//		if (this.m_burnProgress != 0)
//		{
//			if (--this.m_burnProgress <= 0)
//			{
//				this.m_burnTemperature = 0;
//				this.m_burnProgress = 0;
//				this.m_burnTime = 0;
//			}
//			else 
//			{
//				this.m_heatHandler.heat(this.m_burnTemperature);
//			}
//			changed = true;
//		}
//		else if (SlotUtil.getHeatingBurnTime(this.m_inventory.getStackInSlot(CrucibleBlockEntity.HEAT_SLOT)) > 0)
//		{
//			ItemStack i = this.m_inventory.extractItem(CrucibleBlockEntity.HEAT_SLOT, 1, false);
//			if (i.hasCraftingRemainingItem())
//			{
//				InventoryUtil.insertItemOrDrop(this.level, this.worldPosition, this.m_inventory, HEAT_SLOT, i.getCraftingRemainingItem());
//			}
//
//			this.m_burnTime = SlotUtil.getHeatingBurnTime(i);
//			this.m_burnProgress = this.m_burnTime;
//			this.m_burnTemperature = SlotUtil.getHeatingTemperature(i);
//			changed = true;
//		}
//		return changed;
//	} // end doHeat()

	
	
	@Override
	protected boolean canUseRecipe(@NotNull MeltingRecipe from)
	{
		return from.matches(this.m_uncheckedInventory, this.m_resultTank);
	} // end canUseRecipe()

	@Override
	protected void setRecipeResults(@NotNull MeltingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory, this.m_resultTank);
		this.m_ticksPerformed = 0;
		this.m_ticksScheduled = 0f;
	} // end setRecipeResults()

//	@Override
//	protected void startRecipe(@NotNull MeltingRecipe from)
//	{
//		this.m_ticksPerformed = 0;
//		this.m_ticksScheduled = 0f;
//	} // end startRecipe()



	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);

		if(this.m_activeRecipe != null) 
		{
			tag.putInt(CrucibleBlockEntity.TICKS_PERFORMED_TAG_NAME, this.m_ticksPerformed);
			tag.putFloat(CrucibleBlockEntity.TICKS_SCHEDULED_TAG_NAME, this.m_ticksScheduled);
		}
		tag.putInt(CrucibleBlockEntity.TEMPERAURE_TAG_NAME, this.m_heatHandler.getTemperature());
		
		if (this.m_rawResultTank.getFluidAmount() > 0)
		{
			tag.put(CrucibleBlockEntity.RESULT_TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
		}
		tag.put(CrucibleBlockEntity.HEATING_MANAGER_TAG_NAME, this.m_heatingManager.serializeNBT());
		tag.put(CrucibleBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME, this.m_drainResultTankManager.serializeNBT());
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if (tag.contains(CrucibleBlockEntity.TICKS_PERFORMED_TAG_NAME) && tag.contains(CrucibleBlockEntity.TICKS_SCHEDULED_TAG_NAME))
		{
			this.m_ticksPerformed = tag.getInt(CrucibleBlockEntity.TICKS_PERFORMED_TAG_NAME);
			this.m_ticksScheduled = tag.getFloat(CrucibleBlockEntity.TICKS_SCHEDULED_TAG_NAME);
		}
		if (tag.contains(CrucibleBlockEntity.TEMPERAURE_TAG_NAME))
		{
			this.m_heatHandler.setTemperature(tag.getInt(CrucibleBlockEntity.TEMPERAURE_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.RESULT_TANK_TAG_NAME))
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(CrucibleBlockEntity.RESULT_TANK_TAG_NAME));
		}
		
		if (tag.contains(CrucibleBlockEntity.HEATING_MANAGER_TAG_NAME))
		{
			this.m_heatingManager.deserializeNBT(tag.getCompound(CrucibleBlockEntity.HEATING_MANAGER_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME))
		{
			this.m_drainResultTankManager.deserializeNBT(tag.getCompound(CrucibleBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME));
		}
		
		this.m_heatComponentManager.updateComponent();
		this.m_drainResultComponentManager.updateComponent();
	} // end load()

	
	
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
			LazyOptional<T> l = this.m_heatComponentManager.getCapability(cap);
			if(l.isPresent()) 
			{
				return l.cast();
			}
			else if (cap == ForgeCapabilities.ITEM_HANDLER) 
			{
				return this.m_heatingSlotLazyOptional.cast();
			}
		}
		else if(side == Direction.DOWN) 
		{
			LazyOptional<T> l = this.m_drainResultComponentManager.getCapability(cap);
			if(l.isPresent()) 
			{
				return l.cast();
			}
			else if (cap == ForgeCapabilities.ITEM_HANDLER) 
			{
				return this.m_drainResultTankSlotLazyOptional.cast();
			}
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
		
		this.m_drainResultComponentManager.invalidateCaps();
		this.m_heatComponentManager.invalidateCaps();
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