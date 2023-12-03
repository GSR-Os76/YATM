package com.gsr.gsr_yatm.block.device.bioreactor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.behaviors.CurrentFillManager;
import com.gsr.gsr_yatm.block.device.behaviors.DrainTankManager;
import com.gsr.gsr_yatm.block.device.behaviors.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.OutputComponentManager;
import com.gsr.gsr_yatm.recipe.bioreacting.BioreactingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.fluid.TankWrapper;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.CurrentHandlerContainerData;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class BioreactorBlockEntity extends CraftingDeviceBlockEntity<BioreactingRecipe, Container>
{
	public static final int INVENTORY_SLOT_COUNT = 3;	
	
	public static final int INPUT_SLOT = 0;
	public static final int DRAIN_RESULT_TANK_SLOT = 1;
	public static final int POWER_SLOT = 2;
	
	public static final String CURRENT_DATA_SPEC_KEY = "currentData";
	public static final String DRAIN_PROGRESS_SPEC_KEY = "drainProgress";
	public static final String TANK_DATA_SPEC_KEY = "tankData";
	
	private static final String CURRENT_HANDLER_TAG_NAME = "current";
	private static final String DRAIN_RESULT_MANAGER_TAG_NAME = "drainResultManager";
	private static final String TANK_TAG_NAME = "resultTank";

	
	
	private final @NotNull IItemHandler m_currentSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {BioreactorBlockEntity.POWER_SLOT}).build();;
	private final @NotNull IItemHandler m_drainResultTankSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {BioreactorBlockEntity.DRAIN_RESULT_TANK_SLOT}).build();
	private final @NotNull IItemHandler m_inputSlot = InventoryWrapper.Builder.of(this.m_inventory).slotTranslationTable(new int[] {BioreactorBlockEntity.INPUT_SLOT}).build();;
	private final @NotNull FluidTank m_rawResultTank = new FluidTank(YATMConfigs.BIOREACTOR_RESULT_TANK_CAPACITY.get());
	private final @NotNull IFluidHandler m_resultTank = new TankWrapper(this.m_rawResultTank, this::onFluidContentsChanged);;
	private final @NotNull CurrentHandler m_currentStorer = CurrentHandler.Builder.of(YATMConfigs.BIOREACTOR_CURRENT_CAPACITY.get()).onCurrentExtracted((i) -> this.setChanged()).onCurrentRecieved((i) -> this.setChanged()).maxTransfer(YATMConfigs.BIOREACTOR_MAX_CURRENT_TRANSFER.get()).build();	
	
	private boolean m_updateDrainResultComponentQueued = false;
	
	private @NotNull LazyOptional<IItemHandler> m_inventoryLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_inventory);
	private @NotNull LazyOptional<IItemHandler> m_currentSlotLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_currentSlot);
	private @NotNull LazyOptional<IItemHandler> m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_drainResultTankSlot);
	private @NotNull LazyOptional<IItemHandler> m_inputSlotLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_inputSlot);
	private @NotNull LazyOptional<IFluidHandler> m_resultTankLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_resultTank);
	private @NotNull LazyOptional<ICurrentHandler> m_currentStorerLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_currentStorer);

	private final @NotNull InputComponentManager<ICurrentHandler> m_currentComponentManager = new InputComponentManager<>(this.m_inventory, BioreactorBlockEntity.POWER_SLOT, this.m_currentStorer, YATMCapabilities.CURRENT);
	private final @NotNull CurrentFillManager m_currentFillManager = new CurrentFillManager(this.m_inventory, BioreactorBlockEntity.POWER_SLOT, this.m_currentStorer, YATMConfigs.BIOREACTOR_MAX_CURRENT_TRANSFER.get());
	private final @NotNull OutputComponentManager m_drainResultComponentManager = new OutputComponentManager(this.m_inventory, BioreactorBlockEntity.DRAIN_RESULT_TANK_SLOT, () -> List.of(Direction.DOWN), YATMConfigs.BIOREACTOR_DRAIN_RECHECK_PERIOD.get());
	private final @NotNull DrainTankManager m_drainResultTankManager = new DrainTankManager(this.m_inventory, BioreactorBlockEntity.DRAIN_RESULT_TANK_SLOT, this.m_resultTank, YATMConfigs.BIOREACTOR_MAX_FLUID_TRANSFER_RATE.get());	
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2),
			Map.entry(BioreactorBlockEntity.TANK_DATA_SPEC_KEY, FluidHandlerContainerData.SLOT_COUNT), 
			Map.entry(BioreactorBlockEntity.CURRENT_DATA_SPEC_KEY, CurrentHandlerContainerData.SLOT_COUNT), 
			Map.entry(BioreactorBlockEntity.DRAIN_PROGRESS_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY * 2)
			));
	
	protected final @NotNull ContainerData m_data = new ContainerDataBuilder()
			.addProperty(() -> this.m_craftCountDown, (i) -> {})
			.addProperty(() -> this.m_craftTime, (i) -> {})
			.addContainerData(new FluidHandlerContainerData(this.m_resultTank, 0))
			.addContainerData(new CurrentHandlerContainerData(this.m_currentStorer))
			.addProperty(() -> this.m_drainResultTankManager.countDown(), (i) -> {})
			.addProperty(() -> this.m_drainResultTankManager.initial(), (i) -> {})			
			.build();
	
	
	
	public BioreactorBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.BIOLER.get(), Objects.requireNonNull(position), Objects.requireNonNull(state), BioreactorBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.BIOREACTING.get());
	} // end constructor

	
	
	@Override
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	
	
	@Override
	protected boolean itemInsertionValidator(@NotNegative int slot, @NotNull ItemStack stack, boolean simulate)
	{
		return switch(slot) 
		{
			case BioreactorBlockEntity.INPUT_SLOT -> true;
			case BioreactorBlockEntity.DRAIN_RESULT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(stack);
			case BioreactorBlockEntity.POWER_SLOT -> SlotUtil.isValidPowerSlotInsert(stack);
			default -> throw new IllegalArgumentException("Unexpected value of: " + slot);
		};
	} // end itemInsertionValidator()

	@Override
	protected void onItemChange(@NotNegative int slot, @NotNull ItemStack stack)
	{
		super.onItemChange(slot, stack);
		// TODO, power seems to work without being queued,but drain doesnt

		if(slot == BioreactorBlockEntity.POWER_SLOT) 
		{
			this.m_currentComponentManager.updateComponent();
		}
		if(slot == BioreactorBlockEntity.DRAIN_RESULT_TANK_SLOT) 
		{
			this.m_updateDrainResultComponentQueued = true;
		}
	} // end onItemChange()



	@Override
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		if(this.m_updateDrainResultComponentQueued) 
		{
			this.m_drainResultComponentManager.updateComponent();
			this.m_updateDrainResultComponentQueued = false;
		}
		
		
		boolean changed = this.m_currentFillManager.tick(level, position);
		changed |= this.doCrafting();
		changed |= this.m_drainResultTankManager.tick(level, position);
		this.m_drainResultComponentManager.tick(level, position);
		
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()
	
	
	
	
	@Override
	protected boolean canTick()
	{
		return this.m_activeRecipe.canTick(this.m_currentStorer);
	} // end canTick()

	@Override
	protected boolean canUseRecipe(@NotNull BioreactingRecipe from)
	{
		return from.matches(this.m_uncheckedInventory, this.m_resultTank);
	} // end canUseRecipe()
	
	@Override
	protected void setRecipeResults(@NotNull BioreactingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory, this.m_resultTank);
	} // end setRecipeResults()
	
	@Override
	protected void recipeTick()
	{
		this.m_activeRecipe.tick(this.m_currentStorer);
	} // end recipeTick()



	@Override
	protected void saveAdditional(@NotNull CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		if(this.m_currentStorer.stored() > 0) 
		{
			tag.put(BioreactorBlockEntity.CURRENT_HANDLER_TAG_NAME, this.m_currentStorer.serializeNBT());
		}
		CompoundTag drtmTag = this.m_drainResultTankManager.serializeNBT();
		if(drtmTag != null) 
		{
			tag.put(BioreactorBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME, drtmTag);
		}
		if(!this.m_rawResultTank.getFluid().isEmpty()) 
		{
			tag.put(BioreactorBlockEntity.TANK_TAG_NAME, this.m_rawResultTank.writeToNBT(new CompoundTag()));
		}
	} // end saveAdditional()
	
	@Override
	public void load(@NotNull CompoundTag tag)
	{
		super.load(tag);
		if(tag.contains(BioreactorBlockEntity.CURRENT_HANDLER_TAG_NAME)) 
		{
			this.m_currentStorer.deserializeNBT(tag.getCompound(BioreactorBlockEntity.CURRENT_HANDLER_TAG_NAME));
		}
		if(tag.contains(BioreactorBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME)) 
		{
			this.m_drainResultTankManager.deserializeNBT(tag.getCompound(BioreactorBlockEntity.DRAIN_RESULT_MANAGER_TAG_NAME));
		}
		if(tag.contains(BioreactorBlockEntity.TANK_TAG_NAME)) 
		{
			this.m_rawResultTank.readFromNBT(tag.getCompound(BioreactorBlockEntity.TANK_TAG_NAME));
		}
		
		this.m_currentComponentManager.updateComponent();
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
			else if(cap == YATMCapabilities.CURRENT) 
			{
				return this.m_currentStorerLazyOptional.cast();
			}
		}		
		else if (side == Direction.UP && cap == ForgeCapabilities.ITEM_HANDLER) 
		{				
			return this.m_inputSlotLazyOptional.cast();
		}
		else if(Direction.Plane.HORIZONTAL.test(side))
		{				
			return SlotUtil.componentOrSlot(cap, this.m_currentComponentManager.getCapability(cap), this.m_currentSlotLazyOptional, () -> BioreactorBlockEntity.super.getCapability(cap, side));
		}
		else if(side == Direction.DOWN) 
		{
			return SlotUtil.componentOrSlot(cap, this.m_drainResultComponentManager.getCapability(cap), this.m_drainResultTankSlotLazyOptional, () -> BioreactorBlockEntity.super.getCapability(cap, side));
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
		this.m_currentSlotLazyOptional.invalidate();
		this.m_resultTankLazyOptional.invalidate();
		this.m_currentStorerLazyOptional.invalidate();
		
		this.m_currentComponentManager.invalidateCaps();
		this.m_drainResultComponentManager.invalidateCaps();
	} // end invalidateCaps()

	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_inventoryLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_inventory);
		this.m_drainResultTankSlotLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_drainResultTankSlot);
		this.m_inputSlotLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_inputSlot);
		this.m_currentSlotLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_currentSlot);
		this.m_resultTankLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_resultTank);
		this.m_currentStorerLazyOptional = LazyOptional.of(() -> BioreactorBlockEntity.this.m_currentStorer);
		
		this.m_currentComponentManager.updateComponent();
		this.m_drainResultComponentManager.updateComponent();
	} // end reviveCaps()
} // end class 