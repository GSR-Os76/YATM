package com.gsr.gsr_yatm.block.device.crafting.crystallizer;

import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.OutputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.current.CurrentFillManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid.FillTankManager;
import com.gsr.gsr_yatm.block.device.crafting.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.cystallizing.CrystallizingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple3;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.current.CurrentHandlerContainerData;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.fluid.FluidHandlerContainerData;

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
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class CrystallizerBlockEntity extends CraftingDeviceBlockEntity<CrystallizingRecipe, Container, Tuple3<IFluidHandler, IItemHandler, ICurrentHandler>>
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int SEED_SLOT = 2;
	public static final int RESULT_SLOT = 3;
	public static final int POWER_SLOT = 4;

	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	public static final String DRAIN_PROGRESS_SPEC_KEY = "drainProgress";
	public static final String FILL_PROGRESS_SPEC_KEY = "fillProgress";
	public static final String TANK_DATA_SPEC_KEY = "tankData";
	
	private static final String CURRENT_HANDLER_TAG_NAME = "current";
	private static final String DRAIN_INPUT_MANAGER_TAG_NAME = "drainInputManager";
	private static final String FILL_INPUT_MANAGER_TAG_NAME = "fillInputManager";
	private static final String INPUT_TANK_TAG_NAME = "inputTank";


	
	private final @NotNull IItemHandler m_currentSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrystallizerBlockEntity.POWER_SLOT}).build();;
	private final @NotNull IItemHandler m_drainInputTankSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT}).build();
	private final @NotNull IItemHandler m_fillInputTankSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT}).build();;
	private final @NotNull IItemHandler m_resultSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {CrystallizerBlockEntity.RESULT_SLOT}).build();;
	private final @NotNull FluidTank m_rawInputTank = new FluidTank(YATMConfigs.CRYSTALLIZER_INPUT_TANK_CAPACITY.get());
	private final @NotNull TankWrapper m_inputTank = new TankWrapper(this.m_rawInputTank, this::onFluidContentsChanged);
	private final @NotNull CurrentHandler m_currentStorer = CurrentHandler.Builder.of(YATMConfigs.CRYSTALLIZER_CURRENT_CAPACITY.get()).onCurrentExtracted((i) -> this.setChanged()).onCurrentRecieved((i) -> this.setChanged()).maxTransfer(YATMConfigs.CRYSTALLIZER_MAX_CURRENT_TRANSFER.get()).build();	
		
	private boolean m_updateDrainInputComponentQueued = false;
	
	private @NotNull LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_inventory);
	private @NotNull LazyOptional<IItemHandler> m_currentSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_currentSlot);
	private @NotNull LazyOptional<IItemHandler> m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_drainInputTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_fillInputTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_resultSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_resultSlot);
	private @NotNull LazyOptional<IFluidHandler> m_inputTankLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_inputTank);
	private @NotNull LazyOptional<ICurrentHandler> m_currentStorerLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_currentStorer);
	
	private final @NotNull InputComponentManager<ICurrentHandler> m_currentComponentManager = new InputComponentManager<>(this.m_inventory, CrystallizerBlockEntity.POWER_SLOT, this.m_currentStorer, YATMCapabilities.CURRENT);
	private final @NotNull CurrentFillManager m_currentFillManager = new CurrentFillManager(this.m_inventory, CrystallizerBlockEntity.POWER_SLOT, this.m_currentStorer, YATMConfigs.CRYSTALLIZER_MAX_CURRENT_TRANSFER.get());
	private final @NotNull OutputComponentManager m_drainInputComponentManager = new OutputComponentManager(this.m_inventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, () -> List.of(), YATMConfigs.CRYSTALLIZER_DRAIN_INPUT_RECHECK_PERIOD.get());
	private final @NotNull DrainTankManager m_drainInputTankManager = new DrainTankManager(this.m_inventory, CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, YATMConfigs.CRYSTALLIZER_DRAIN_INPUT_MAX_FLUID_TRANSFER_RATE.get());
	private final @NotNull InputComponentManager<IFluidHandler> m_fillInputComponentManager = new InputComponentManager<>(this.m_inventory, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, TankWrapper.Builder.of(this.m_inputTank).canDrain(() -> false).build(), ForgeCapabilities.FLUID_HANDLER);
	private final @NotNull FillTankManager m_fillInputTankManager = new FillTankManager(this.m_inventory, CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT, TankWrapper.Builder.of(this.m_inputTank).canDrain(() -> false).build() /* this.m_inputTank */, YATMConfigs.CRYSTALLIZER_FILL_INPUT_MAX_FLUID_TRANSFER_RATE.get());
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(CrystallizerBlockEntity.TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(CrystallizerBlockEntity.CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT), 
			Map.entry(CrystallizerBlockEntity.DRAIN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(CrystallizerBlockEntity.FILL_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2)
			));
	
	protected final @NotNull ContainerData m_data = new ContainerDataBuilder()
			.addContainerData(this.m_craftProgressC)
			.addContainerData(new FluidHandlerContainerData(this.m_inputTank, 0))
			.addContainerData(new CurrentHandlerContainerData(this.m_currentStorer))
			.addProperty(() -> this.m_drainInputTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_drainInputTankManager.initial(), (i) -> {})			
			.addProperty(() -> this.m_fillInputTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_fillInputTankManager.initial(), (i) -> {})			
			.build();
	
	
	
	public CrystallizerBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.CRYSTALLIZER.get(), position, state, CrystallizerBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.CRYSTALLIZING.get());
	} // end constructor
	
	@Override
	public @NotNull Tuple3<IFluidHandler, IItemHandler, ICurrentHandler> getContext()
	{
		return new Tuple3<>(this.m_inputTank, this.m_uncheckedInventory, this.m_currentStorer);
	} // end getContext()
	
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
	@Override
	protected boolean itemInsertionValidator(@NotNegative int slot, @NotNull ItemStack stack, boolean simulate)
	{
		return switch(slot) 
		{
			case CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT -> SlotUtil.isValidTankFillSlotInsert(stack);
			case CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(stack);
			case CrystallizerBlockEntity.SEED_SLOT -> true;
			case CrystallizerBlockEntity.RESULT_SLOT -> false;
			case CrystallizerBlockEntity.POWER_SLOT -> SlotUtil.isValidPowerSlotInsert(stack);
			default -> throw new IllegalArgumentException("Unexpected value of : " + slot);
		};
	} // end validateSlotInsertion()

	@Override
	protected void onItemChange(@NotNegative int slot, @NotNull ItemStack stack)
	{
		super.onItemChange(slot, stack);
		if(slot == CrystallizerBlockEntity.POWER_SLOT) 
		{
			this.m_currentComponentManager.updateComponent();
		}
		if(slot == CrystallizerBlockEntity.FILL_INPUT_TANK_SLOT) 
		{
			this.m_fillInputComponentManager.updateComponent();
		}
		if(slot == CrystallizerBlockEntity.DRAIN_INPUT_TANK_SLOT) 
		{
			this.m_updateDrainInputComponentQueued = true;
		}
	} // end onItemChange()



	@Override
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		super.serverTick(level, position, state);
		
		if(this.m_updateDrainInputComponentQueued) 
		{
			this.m_drainInputComponentManager.updateComponent();
			this.m_updateDrainInputComponentQueued = false;
		}
		
		boolean changed = this.m_currentFillManager.tick(level, position);
		changed |= this.m_fillInputTankManager.tick(level, position);
		changed |= this.m_craftingManager.tick(level, position);
		changed |= this.m_drainInputTankManager.tick(level, position);
		this.m_drainInputComponentManager.tick(level, position);

		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()
	
	

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		if(this.m_currentStorer.stored() > 0) 
		{
			tag.put(CrystallizerBlockEntity.CURRENT_HANDLER_TAG_NAME, this.m_currentStorer.serializeNBT());
		}
		CompoundTag ditmTag = this.m_drainInputTankManager.serializeNBT();
		if(ditmTag != null) 
		{
			tag.put(CrystallizerBlockEntity.DRAIN_INPUT_MANAGER_TAG_NAME, ditmTag);
		}
		CompoundTag fitmTag = this.m_fillInputTankManager.serializeNBT();
		if(fitmTag != null) 
		{
			tag.put(CrystallizerBlockEntity.FILL_INPUT_MANAGER_TAG_NAME, fitmTag);
		}
		if(this.m_rawInputTank.getFluidAmount() > 0) 
		{
			tag.put(CrystallizerBlockEntity.INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
		}
		
	} // end saveAdditional()

	@Override
	public void load(@NotNull CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(CrystallizerBlockEntity.CURRENT_HANDLER_TAG_NAME)) 
		{
			this.m_currentStorer.deserializeNBT(tag.getCompound(CrystallizerBlockEntity.CURRENT_HANDLER_TAG_NAME));
		}
		if (tag.contains(CrystallizerBlockEntity.DRAIN_INPUT_MANAGER_TAG_NAME))
		{
			this.m_drainInputTankManager.deserializeNBT(tag.getCompound(CrystallizerBlockEntity.DRAIN_INPUT_MANAGER_TAG_NAME));
		}
		if (tag.contains(CrystallizerBlockEntity.FILL_INPUT_MANAGER_TAG_NAME))
		{
			this.m_fillInputTankManager.deserializeNBT(tag.getCompound(CrystallizerBlockEntity.FILL_INPUT_MANAGER_TAG_NAME));
		}
		
		if(tag.contains(CrystallizerBlockEntity.INPUT_TANK_TAG_NAME)) 
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(CrystallizerBlockEntity.INPUT_TANK_TAG_NAME));
		}
		
		this.m_currentComponentManager.updateComponent();
		this.m_drainInputComponentManager.updateComponent();
		this.m_fillInputComponentManager.updateComponent();
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
				return this.m_inputTankLazyOptional.cast();
			}
			else if(cap == YATMCapabilities.CURRENT) 
			{
				return this.m_currentStorerLazyOptional.cast();
			}
		}		
		else if (side == Direction.UP) 
		{				
			return SlotUtil.componentOrSlot(cap, this.m_fillInputComponentManager.getCapability(cap), this.m_fillInputTankSlotLazyOptional, () -> CrystallizerBlockEntity.super.getCapability(cap, side));
		}
		else if(Direction.Plane.HORIZONTAL.test(side))
		{				
			return SlotUtil.componentOrSlot(cap, this.m_currentComponentManager.getCapability(cap), this.m_currentSlotLazyOptional, () -> CrystallizerBlockEntity.super.getCapability(cap, side));
		}
		else if(side == Direction.DOWN && cap == ForgeCapabilities.ITEM_HANDLER) 
		{
			return this.m_resultSlotLazyOptional.cast();
		}
		
		return super.getCapability(cap, side);
	} // end getCapability()

	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_inventoryLazyOptional.invalidate();
		this.m_currentSlotLazyOptional.invalidate();
		this.m_drainInputTankSlotLazyOptional.invalidate();
		this.m_fillInputTankSlotLazyOptional.invalidate();
		this.m_resultSlotLazyOptional.invalidate();
		this.m_inputTankLazyOptional.invalidate();
		this.m_currentStorerLazyOptional.invalidate();
		
		this.m_currentComponentManager.invalidateCaps();
		this.m_drainInputComponentManager.invalidateCaps();
		this.m_fillInputComponentManager.invalidateCaps();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_inventoryLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_inventory);
		this.m_currentSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_currentSlot);
		this.m_drainInputTankSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_drainInputTankSlot);
		this.m_fillInputTankSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_fillInputTankSlot);
		this.m_resultSlotLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_resultSlot);
		this.m_inputTankLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_inputTank);
		this.m_currentStorerLazyOptional = LazyOptional.of(() -> CrystallizerBlockEntity.this.m_currentStorer);

		this.m_currentComponentManager.updateComponent();
		this.m_drainInputComponentManager.updateComponent();
		this.m_fillInputComponentManager.updateComponent();
	} // end reviveCaps()
	
} // end class