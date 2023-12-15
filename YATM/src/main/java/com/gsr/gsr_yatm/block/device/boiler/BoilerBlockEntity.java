package com.gsr.gsr_yatm.block.device.boiler;

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
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.HeatingManager;
import com.gsr.gsr_yatm.recipe.boiling.BoilingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.CompoundTank;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.Tuple3;
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

public class BoilerBlockEntity extends CraftingDeviceBlockEntity<BoilingRecipe, Container, Tuple3<IFluidHandler, IFluidHandler, IHeatHandler>>
{
	public static final int INVENTORY_SLOT_COUNT = 4;


	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int DRAIN_RESULT_TANK_SLOT = 2;
	public static final int HEAT_SLOT = 3;

	public static final int LAST_INVENTORY_SLOT = HEAT_SLOT;
	public static final int FIRST_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int LAST_FLUID_SLOT = DRAIN_RESULT_TANK_SLOT;
	public static final int FIRST_FILL_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int LAST_FILL_FLUID_SLOT = FILL_INPUT_TANK_SLOT;
	public static final int FIRST_DRAIN_FLUID_SLOT = DRAIN_INPUT_TANK_SLOT;
	public static final int LAST_DRAIN_FLUID_SLOT = DRAIN_RESULT_TANK_SLOT;

	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String TEMPERATURE_SPEC_KEY = "temperature";
	public static final String MAX_TEMPERATURE_SPEC_KEY = "maxTemperature";
	public static final String INPUT_TANK_DATA_SPEC_KEY = "inputTankData";
	public static final String RESULT_TANK_DATA_SPEC_KEY = "resultTankData";
	public static final String FILL_INPUT_PROGRESS_SPEC_KEY = "fillInputProgress";
	public static final String DRAIN_INPUT_PROGRESS_SPEC_KEY = "drainInputProgress";
	public static final String DRAIN_RESULT_PROGRESS_SPEC_KEY = "drainResultProgress";
	
	private static final String CRAFTING_MANAGER_TAG_NAME = "craftingManager";
	private static final String DRAIN_INPUT_MANAGER_TAG_NAME = "drainInputManager";
	private static final String DRAIN_RESULT_MANAGER_TAG_NAME = "drainResultManager";
	private static final String FILL_INPUT_MANAGER_TAG_NAME = "fillInputManager";
	private static final String HEATING_MANAGER_TAG_NAME = "heatingManager";
	private static final String INPUT_TANK_TAG_NAME = "inputTank";
	private static final String RESULT_TANK_TAG_NAME = "resultTank";
	private static final String TEMPERAURE_TAG_NAME = "temperature";
	
	
	
	private final @NotNull IItemHandler m_fillInputTankSlot = new InventoryWrapper(this.m_inventory, new int[]{ BoilerBlockEntity.FILL_INPUT_TANK_SLOT });
	private final @NotNull IItemHandler m_drainInputTankSlot = new InventoryWrapper(this.m_inventory, new int[]{ BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT });
	private final @NotNull IItemHandler m_drainResultTankSlot = new InventoryWrapper(this.m_inventory, new int[]{ BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT });
	private final @NotNull IItemHandler m_heatingSlot = new InventoryWrapper(this.m_inventory, new int[] { BoilerBlockEntity.HEAT_SLOT });
	private final @NotNull FluidTank m_rawInputTank = new FluidTank(YATMConfigs.BOILER_INPUT_TANK_CAPACITY.get());
	private final @NotNull TankWrapper m_inputTank =  new TankWrapper(this.m_rawInputTank, this::onFluidContentsChanged);
	private final @NotNull FluidTank m_rawResultTank = new FluidTank(YATMConfigs.BOILER_RESULT_TANK_CAPACITY.get());
	private final @NotNull TankWrapper m_resultTank =  new TankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);
	private final @NotNull IFluidHandler m_compoundTank = new CompoundTank(this.m_resultTank, this.m_inputTank);
	private final @NotNull OnChangedHeatHandler m_heatHandler = new OnChangedHeatHandler(IHeatHandler.getAmbientTemp(), (i) -> this.setChanged(), DeviceBlockEntity::deviceHeatEquation, YATMConfigs.BOILER_MAX_TEMPERATURE.get());
	
	private boolean m_updateDrainInputComponentQueued = false;
	private boolean m_updateDrainResultComponentQueued = false;
	
	private LazyOptional<IFluidHandler> m_inputTankLazyOptional = LazyOptional.of(() -> this.m_inputTank);
	
	private @NotNull LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_inventory);
	private @NotNull LazyOptional<IItemHandler> m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_fillInputTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_drainInputTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_drainResultTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_heatingSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_heatingSlot);
	private @NotNull LazyOptional<IFluidHandler> m_compoundTankLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_compoundTank);
	private @NotNull LazyOptional<IHeatHandler> m_heatLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_heatHandler);

	private final @NotNull OutputComponentManager m_drainInputComponentManager = new OutputComponentManager(this.m_inventory, BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, () -> List.of(), YATMConfigs.BOILER_DRAIN_INPUT_RECHECK_PERIOD.get());
	private final @NotNull DrainTankManager m_drainInputTankManager = new DrainTankManager(this.m_inventory, BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, YATMConfigs.BOILER_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get());
	private final @NotNull OutputComponentManager m_drainResultComponentManager = new OutputComponentManager(this.m_inventory, BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT, () -> List.of(Direction.DOWN), YATMConfigs.BOILER_DRAIN_RESULT_RECHECK_PERIOD.get());
	private final @NotNull DrainTankManager m_drainResultTankManager = new DrainTankManager(this.m_inventory, BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_resultTank, YATMConfigs.BOILER_DRAIN_RESULT_MAX_FLUID_TRANSFER_RATE.get());
	private final @NotNull InputComponentManager<IFluidHandler> m_fillInputComponentManager = new InputComponentManager<>(this.m_inventory, BoilerBlockEntity.FILL_INPUT_TANK_SLOT, TankWrapper.Builder.of(this.m_inputTank).canDrain(() -> false).build(), ForgeCapabilities.FLUID_HANDLER);
	private final @NotNull FillTankManager m_fillInputTankManager = new FillTankManager(this.m_inventory, BoilerBlockEntity.FILL_INPUT_TANK_SLOT, TankWrapper.Builder.of(this.m_inputTank).canDrain(() -> false).build() /* this.m_inputTank */, YATMConfigs.BOILER_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get());
	private final @NotNull HeatAcceleratedCraftingManager m_heatAcceleratedCraftingManager = new HeatAcceleratedCraftingManager(BoilerBlockEntity.this.m_craftingManager::tick, this.m_heatHandler, BoilerBlockEntity.this.m_craftingManager::getActiveRecipe);
	private final @NotNull InputComponentManager<IHeatHandler> m_heatComponentManager = new InputComponentManager<>(this.m_inventory, BoilerBlockEntity.HEAT_SLOT, this.m_heatHandler, YATMCapabilities.HEAT);
	private final @NotNull HeatingManager m_heatingManager = new HeatingManager(this.m_inventory, BoilerBlockEntity.HEAT_SLOT, this.m_heatHandler);
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(BoilerBlockEntity.BURN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(BoilerBlockEntity.TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(BoilerBlockEntity.MAX_TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(BoilerBlockEntity.INPUT_TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(BoilerBlockEntity.RESULT_TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(BoilerBlockEntity.FILL_INPUT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(BoilerBlockEntity.DRAIN_INPUT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2), 
			Map.entry(BoilerBlockEntity.DRAIN_RESULT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2) 
			));
	protected final @NotNull ContainerData m_data = new ContainerDataBuilder()
			.addContainerData(this.m_craftProgressC)
			.addProperty(() -> this.m_heatingManager.burnProgress(), (i) -> {})
			.addProperty(() -> this.m_heatingManager.burnTime(), (i) -> {})
			.addProperty(() -> this.m_heatHandler.getTemperature(), (i) -> {})
			.addProperty(() -> this.m_heatHandler.maxTemperature(), (i) -> {})
			.addContainerData(new FluidHandlerContainerData(this.m_rawInputTank, 0))
			.addContainerData(new FluidHandlerContainerData(this.m_rawResultTank, 0))
			.addProperty(() -> this.m_fillInputTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_fillInputTankManager.initial(), (i) -> {})
			.addProperty(() -> this.m_drainInputTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_drainInputTankManager.initial(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.initial(), (i) -> {})
			.build();

	
	



	public BoilerBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.BOILER.get(), Objects.requireNonNull(position), Objects.requireNonNull(state), BoilerBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.BOILING.get());
	} // end constructor
	

	
	@Override
	public @NotNull Tuple3<IFluidHandler, IFluidHandler, IHeatHandler> getContext()
	{
		return new Tuple3<>(this.m_inputTank, this.m_resultTank, this.m_heatHandler);
	} // end getContext()

	@Override
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()



	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack stack, boolean simulate)
	{
		return switch(slot) 
		{
			case BoilerBlockEntity.FILL_INPUT_TANK_SLOT -> SlotUtil.isValidTankFillSlotInsert(stack);
			case BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT, BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(stack);
			case BoilerBlockEntity.HEAT_SLOT -> SlotUtil.isValidHeatingSlotInsert(stack);
			
			default -> throw new IllegalArgumentException("Unexpected value: " + slot);
		};
	} // end validateSlotInsertion()

	@Override
	protected void onItemChange(@NotNegative int slot, @NotNull ItemStack stack)
	{
		super.onItemChange(slot, stack);
		if(slot == BoilerBlockEntity.FILL_INPUT_TANK_SLOT) 
		{
			this.m_fillInputComponentManager.updateComponent();
		}
		if(slot == BoilerBlockEntity.DRAIN_INPUT_TANK_SLOT) 
		{
			this.m_updateDrainInputComponentQueued = true;
		}
		if(slot == BoilerBlockEntity.DRAIN_RESULT_TANK_SLOT) 
		{
			this.m_updateDrainResultComponentQueued = true;
		}
		else if(slot == BoilerBlockEntity.HEAT_SLOT) 
		{
			this.m_heatComponentManager.updateComponent();
		}
	} // end onItemChange()

	
	
	@Override
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(this.m_updateDrainInputComponentQueued) 
		{
			this.m_drainInputComponentManager.updateComponent();
			this.m_updateDrainInputComponentQueued = false;
		}
		if(this.m_updateDrainResultComponentQueued) 
		{
			this.m_drainResultComponentManager.updateComponent();
			this.m_updateDrainResultComponentQueued = false;
		}
		
		boolean changed = this.m_heatingManager.tick(level, position);
		changed |= this.m_fillInputTankManager.tick(level, position);
		changed |= this.m_heatAcceleratedCraftingManager.tick(level, position);
		changed |= this.m_drainInputTankManager.tick(level, position);
		changed |= this.m_drainResultTankManager.tick(level, position);
		this.m_drainInputComponentManager.tick(level, position);
		this.m_drainResultComponentManager.tick(level, position);
		
		if(changed) 
		{
			this.setChanged();
		}
		boolean shouldBeLit = this.m_heatHandler.getTemperature() > YATMConfigs.BOILER_LIT_ABOVE_TEMPERATURE.get();
		if(state.getValue(BoilerBlock.LIT) ^ shouldBeLit) 
		{
			level.setBlock(position, state.setValue(BoilerBlock.LIT, shouldBeLit), Block.UPDATE_CLIENTS);
		}
	} // end serverTick()

	
	
	@Override
 	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		CompoundTag cmTag = this.m_heatAcceleratedCraftingManager.serializeNBT();
		if(cmTag != null) 
		{
			tag.put(BoilerBlockEntity.CRAFTING_MANAGER_TAG_NAME, cmTag);
		}
		CompoundTag ditmTag = this.m_drainInputTankManager.serializeNBT();
		if(ditmTag != null) 
		{
			tag.put(BoilerBlockEntity.DRAIN_INPUT_MANAGER_TAG_NAME, ditmTag);
		}
		CompoundTag drtmTag = this.m_drainResultTankManager.serializeNBT();
		if(drtmTag != null) 
		{
			tag.put(BoilerBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME, drtmTag);
		}
		CompoundTag fitmTag = this.m_fillInputTankManager.serializeNBT();
		if(fitmTag != null) 
		{
			tag.put(BoilerBlockEntity.FILL_INPUT_MANAGER_TAG_NAME, fitmTag);
		}
		CompoundTag hmTag = this.m_heatingManager.serializeNBT();
		if(hmTag != null) 
		{
			tag.put(BoilerBlockEntity.HEATING_MANAGER_TAG_NAME, hmTag);
		}
		if(!this.m_rawInputTank.getFluid().isEmpty()) 
		{
			tag.put(BoilerBlockEntity.INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
		}
		if(!this.m_rawResultTank.getFluid().isEmpty()) 
		{
			tag.put(BoilerBlockEntity.RESULT_TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
		}
		tag.putInt(BoilerBlockEntity.TEMPERAURE_TAG_NAME, this.m_heatHandler.getTemperature());
	} // end saveAdditional()

	@Override
	public void load(@NotNull CompoundTag tag)
	{
		super.load(tag);
			
		if (tag.contains(BoilerBlockEntity.CRAFTING_MANAGER_TAG_NAME))
		{
			this.m_heatAcceleratedCraftingManager.deserializeNBT(tag.getCompound(BoilerBlockEntity.CRAFTING_MANAGER_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.DRAIN_INPUT_MANAGER_TAG_NAME))
		{
			this.m_drainInputTankManager.deserializeNBT(tag.getCompound(BoilerBlockEntity.DRAIN_INPUT_MANAGER_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME))
		{
			this.m_drainResultTankManager.deserializeNBT(tag.getCompound(BoilerBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.FILL_INPUT_MANAGER_TAG_NAME))
		{
			this.m_fillInputTankManager.deserializeNBT(tag.getCompound(BoilerBlockEntity.FILL_INPUT_MANAGER_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.HEATING_MANAGER_TAG_NAME))
		{
			this.m_heatingManager.deserializeNBT(tag.getCompound(BoilerBlockEntity.HEATING_MANAGER_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.INPUT_TANK_TAG_NAME))
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(BoilerBlockEntity.INPUT_TANK_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.RESULT_TANK_TAG_NAME))
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(BoilerBlockEntity.RESULT_TANK_TAG_NAME));
		}
		if (tag.contains(BoilerBlockEntity.TEMPERAURE_TAG_NAME))
		{
			this.m_heatHandler.setTemperature(tag.getInt(BoilerBlockEntity.TEMPERAURE_TAG_NAME));
		}
		
		this.m_drainInputComponentManager.updateComponent();
		this.m_drainResultComponentManager.updateComponent();
		this.m_fillInputComponentManager.updateComponent();
		this.m_heatComponentManager.updateComponent();
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
				return this.m_compoundTankLazyOptional.cast();
			}
			else if(cap == YATMCapabilities.HEAT) 
			{
				return this.m_heatLazyOptional.cast();
			}
		}		
		else if (side == Direction.UP) 
		{				
			return SlotUtil.componentOrSlot(cap, this.m_fillInputComponentManager.getCapability(cap), this.m_fillInputTankSlotLazyOptional, () -> BoilerBlockEntity.super.getCapability(cap, side));
		}
		else if(Direction.Plane.HORIZONTAL.test(side))
		{		
			return SlotUtil.componentOrSlot(cap, this.m_heatComponentManager.getCapability(cap), this.m_heatingSlotLazyOptional, () -> BoilerBlockEntity.super.getCapability(cap, side));
		}
		else if(side == Direction.DOWN) 
		{
			return SlotUtil.componentOrSlot(cap, this.m_drainResultComponentManager.getCapability(cap), this.m_drainResultTankSlotLazyOptional, () -> BoilerBlockEntity.super.getCapability(cap, side));
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
		this.m_inputTankLazyOptional.invalidate();
		
		this.m_inventoryLazyOptional.invalidate();
		this.m_fillInputTankSlotLazyOptional.invalidate();
		this.m_drainInputTankSlotLazyOptional.invalidate();
		this.m_drainResultTankSlotLazyOptional.invalidate();
		this.m_heatingSlotLazyOptional.invalidate();
		this.m_compoundTankLazyOptional.invalidate();
		this.m_heatLazyOptional.invalidate();
		
		this.m_drainInputComponentManager.invalidateCaps();
		this.m_drainResultComponentManager.invalidateCaps();
		this.m_fillInputComponentManager.invalidateCaps();
		this.m_heatComponentManager.invalidateCaps();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_inputTankLazyOptional = LazyOptional.of(() -> this.m_inputTank);
		
		this.m_inventoryLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_inventory);
		this.m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_fillInputTankSlot);
		this.m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_drainInputTankSlot);
		this.m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_drainResultTankSlot);
		this.m_heatingSlotLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_heatingSlot);
		this.m_compoundTankLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_compoundTank);
		this.m_heatLazyOptional = LazyOptional.of(() -> BoilerBlockEntity.this.m_heatHandler);
		
		this.m_drainInputComponentManager.updateComponent();
		this.m_drainResultComponentManager.updateComponent();
		this.m_fillInputComponentManager.updateComponent();
		this.m_heatComponentManager.updateComponent();
	} // end revivieCaps()
	
} // end class