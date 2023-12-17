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
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.HeatAcceleratedCraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.HeatingManager;
import com.gsr.gsr_yatm.recipe.melting.MeltingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple3;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidHandlerContainerData;

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

public class CrucibleBlockEntity extends CraftingDeviceBlockEntity<MeltingRecipe, Container, Tuple3<IItemHandler, IFluidHandler, IHeatHandler>>
{
	// TODO, make recipe progress reverse slowly when heat is lacking?
	public static final int INVENTORY_SLOT_COUNT = 3;
	
	public static final int INPUT_SLOT = 0;
	public static final int HEAT_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;

	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String DRAIN_PROGRESS_SPEC_KEY = "drainProgress";
	public static final String TANK_DATA_SPEC_KEY = "tankData";
	public static final String TEMPERATURE_SPEC_KEY = "temperature";
	public static final String MAX_TEMPERATURE_SPEC_KEY = "maxTemperature";
	
	private static final String CRAFTING_MANAGER_TAG_NAME = "craftingManager";
	private static final String DRAIN_RESULT_MANAGER_TAG_NAME = "drainResultManager";
	private static final String HEATING_MANAGER_TAG_NAME = "heatingManager";
	private static final String RESULT_TANK_TAG_NAME = "resultTank";
	private static final String TEMPERAURE_TAG_NAME = "temperature";
	
	
	
	private final @NotNull IItemHandler m_drainResultTankSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT}).build();
	private final @NotNull IItemHandler m_inputSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.INPUT_SLOT}).build();;
	private final @NotNull IItemHandler m_heatingSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrucibleBlockEntity.HEAT_SLOT}).build();;
	private final @NotNull FluidTank m_rawResultTank = new FluidTank(YATMConfigs.CRUCIBLE_RESULT_TANK_CAPACITY.get());
	private final @NotNull TankWrapper m_resultTank = new TankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);	
	private final @NotNull OnChangedHeatHandler m_heatHandler = new OnChangedHeatHandler(IHeatHandler.getAmbientTemp(), (i) -> this.setChanged(), DeviceBlockEntity::deviceHeatEquation, YATMConfigs.CRUCIBLE_MAX_TEMPERATURE.get());
	
	// TODO, investigate why the update pattern necessitates this, and fix it
	private boolean m_updateDrainResultComponentQueued = false;
	
	private @NotNull LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inventory);
	private @NotNull LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_drainResultTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_inputSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_inputSlot);
	private @NotNull LazyOptional<IItemHandler> m_heatingSlotLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatingSlot);
	private @NotNull LazyOptional<IFluidHandler> m_resultTankLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_resultTank);
	private @NotNull LazyOptional<IHeatHandler> m_heatLazyOptional = LazyOptional.of(() -> CrucibleBlockEntity.this.m_heatHandler);

	private final @NotNull OutputComponentManager m_drainResultComponentManager = new OutputComponentManager(this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, () -> List.of(Direction.DOWN), YATMConfigs.CRUCIBLE_DRAIN_RECHECK_PERIOD.get());
	private final @NotNull DrainTankManager m_drainResultTankManager = new DrainTankManager(this.m_inventory, CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_resultTank, YATMConfigs.CRUCIBLE_MAX_FLUID_TRANSFER_RATE.get());
	private final @NotNull HeatAcceleratedCraftingManager m_heatAcceleratedCraftingManager = new HeatAcceleratedCraftingManager(CrucibleBlockEntity.this.m_craftingManager::tick, this.m_heatHandler,  CrucibleBlockEntity.this.m_craftingManager::getActiveRecipe);
	private final @NotNull InputComponentManager<IHeatHandler> m_heatComponentManager = new InputComponentManager<>(this.m_inventory, CrucibleBlockEntity.HEAT_SLOT, this.m_heatHandler, YATMCapabilities.HEAT);
	private final @NotNull HeatingManager m_heatingManager = new HeatingManager(this.m_inventory, CrucibleBlockEntity.HEAT_SLOT, this.m_heatHandler);
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(CrucibleBlockEntity.BURN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(CrucibleBlockEntity.DRAIN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(CrucibleBlockEntity.TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(CrucibleBlockEntity.TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(CrucibleBlockEntity.MAX_TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
			));
	protected final @NotNull ContainerData m_data = new ContainerDataBuilder()
			.addContainerData(this.m_craftProgressC)
			.addProperty(() -> this.m_heatingManager.burnProgress(), (i) -> {})
			.addProperty(() -> this.m_heatingManager.burnTime(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.initial(), (i) -> {})
			.addContainerData(new FluidHandlerContainerData(this.m_rawResultTank, 0))
			.addProperty(() -> this.m_heatHandler.getTemperature(), (i) -> {})
			.addProperty(() -> this.m_heatHandler.maxTemperature(), (i) -> {})
			.build();


	public CrucibleBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
	{
		super(YATMBlockEntityTypes.CRUCIBLE.get(), Objects.requireNonNull(blockPos), Objects.requireNonNull(blockState), CrucibleBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.MELTING.get());
	} // end constructor

	@Override
	public @NotNull Tuple3<IItemHandler, IFluidHandler, IHeatHandler> getContext()
	{
		return new Tuple3<>(this.m_inventory, this.m_resultTank, this.m_heatHandler);
	} // end getContext

	@Override
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
	@Override
	protected boolean itemInsertionValidator(@NotNegative int slot, @NotNull ItemStack stack, boolean simulate)
	{
		return switch (slot)
		{
			case CrucibleBlockEntity.HEAT_SLOT -> SlotUtil.isValidHeatingSlotInsert(stack);
			case CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(stack);
			default -> true;
		};
	} // end itemInsertionValidator()
	
	
	@Override
	protected void onItemChange(@NotNegative int slot, @NotNull ItemStack stack)
	{
		super.onItemChange(slot, stack);
		if(slot == CrucibleBlockEntity.DRAIN_RESULT_TANK_SLOT) 
		{
			this.m_updateDrainResultComponentQueued = true;
		}
		else if(slot == CrucibleBlockEntity.HEAT_SLOT) 
		{
			this.m_heatComponentManager.updateComponent();
		}
	} // end onItemChange()

	
	
	@Override
	public void serverTick(Level level, BlockPos position, BlockState state)
	{
		// TODO, possibly send an update packet to who it may concern when a component is updated, to prevent lag in them noticing it
		if(this.m_updateDrainResultComponentQueued) 
		{
			this.m_drainResultComponentManager.updateComponent();
			this.m_updateDrainResultComponentQueued = false;
		}

		
		
		boolean changed = this.m_heatingManager.tick(level, position);
		changed |= this.m_heatAcceleratedCraftingManager.tick(level, position);
		changed |= this.m_drainResultTankManager.tick(level, position);
		this.m_drainResultComponentManager.tick(level, position);
		
		
		if (changed)
		{
			this.setChanged();
		}		
		boolean shouldBeLit = this.m_heatHandler.getTemperature() > YATMConfigs.CRUCIBLE_LIT_ABOVE_TEMPERATURE.get();
		if(state.getValue(CrucibleBlock.LIT) ^ shouldBeLit) 
		{
			level.setBlock(position, state.setValue(CrucibleBlock.LIT, shouldBeLit), Block.UPDATE_CLIENTS);
		}
	} // end serverTick()



	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);

		CompoundTag cmTag = this.m_heatAcceleratedCraftingManager.serializeNBT();
		if(cmTag != null) 
		{
			tag.put(CrucibleBlockEntity.CRAFTING_MANAGER_TAG_NAME, cmTag);
		}
		CompoundTag drtmTag = this.m_drainResultTankManager.serializeNBT();
		if(drtmTag != null) 
		{
			tag.put(CrucibleBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME, drtmTag);
		}
		CompoundTag hmTag = this.m_heatingManager.serializeNBT();
		if(hmTag != null) 
		{
			tag.put(CrucibleBlockEntity.HEATING_MANAGER_TAG_NAME, hmTag);
		}
		if (!this.m_rawResultTank.getFluid().isEmpty())
		{
			tag.put(CrucibleBlockEntity.RESULT_TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
		}
		tag.putInt(CrucibleBlockEntity.TEMPERAURE_TAG_NAME, this.m_heatHandler.getTemperature());

	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if (tag.contains(CrucibleBlockEntity.CRAFTING_MANAGER_TAG_NAME))
		{
			this.m_heatAcceleratedCraftingManager.deserializeNBT(tag.getCompound(CrucibleBlockEntity.CRAFTING_MANAGER_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME))
		{
			this.m_drainResultTankManager.deserializeNBT(tag.getCompound(CrucibleBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.HEATING_MANAGER_TAG_NAME))
		{
			this.m_heatingManager.deserializeNBT(tag.getCompound(CrucibleBlockEntity.HEATING_MANAGER_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.RESULT_TANK_TAG_NAME))
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(CrucibleBlockEntity.RESULT_TANK_TAG_NAME));
		}
		if (tag.contains(CrucibleBlockEntity.TEMPERAURE_TAG_NAME))
		{
			this.m_heatHandler.setTemperature(tag.getInt(CrucibleBlockEntity.TEMPERAURE_TAG_NAME));
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
			return SlotUtil.componentOrSlot(cap, this.m_heatComponentManager.getCapability(cap), this.m_heatingSlotLazyOptional, () -> CrucibleBlockEntity.super.getCapability(cap, side));
		}
		else if(side == Direction.DOWN) 
		{
			return SlotUtil.componentOrSlot(cap, this.m_drainResultComponentManager.getCapability(cap), this.m_drainResultTankSlotLazyOptional, () -> CrucibleBlockEntity.super.getCapability(cap, side));
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
		
		this.m_drainResultComponentManager.updateComponent();
		this.m_heatComponentManager.updateComponent();		
	} // end reviveCaps()
	
} // end class