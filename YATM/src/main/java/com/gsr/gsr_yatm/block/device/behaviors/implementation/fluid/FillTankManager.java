package com.gsr.gsr_yatm.block.device.behaviors.implementation.fluid;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.block.device.behaviors.IBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ISerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.ITickableBehavior;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.contract.Contract;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

public class FillTankManager implements ISerializableBehavior, ITickableBehavior
{
	@Override
	public @NotNull Set<Class<? extends IBehavior>> behaviorTypes()
	{
		return Set.of(ISerializableBehavior.class, ITickableBehavior.class);
	} // end behaviorTypes()
	
	public static final int SLOT_COUNT = PropertyContainerData.LENGTH_PER_PROPERTY * 2;
	private static final String BUFFER_TAG_NAME = "buffer";
	private static final String INITIAL_TAG_NAME = "initial";
	private static final String STACK_TAG_NAME = "stack";
	
	private final @NotNull IItemHandler m_inventory;
	private final @NotNegative int m_slot;
	private final @NotNull IFluidHandler m_fluidHandler;
	private final @NotNegative int m_tank;
	private final @NotNegative int m_transferRate;
	
	private final @NotNull FluidTank m_buffer;
	private @NotNegative int m_initial = 0;
	private @Nullable ItemStack m_initialItemStack = null;
	
	protected final @NotNull ContainerData m_data = new PropertyContainerData(List.of(new Property<>(this::countDown, (i) -> {}), new Property<>(this::initial, (i) -> {})));
	
	
	
	public FillTankManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull IFluidHandler fluidHandler, @NotNegative int transferRate) 
	{
		this(Objects.requireNonNull(inventory), Contract.notNegative(slot), Objects.requireNonNull(fluidHandler), Contract.notNegative(transferRate), 0);
	} // end constructor()
	
	public FillTankManager(@NotNull IItemHandler inventory, @NotNegative int slot, @NotNull IFluidHandler fluidHandler, @NotNegative int transferRate, @NotNegative int tank) 
	{
		this.m_inventory = Objects.requireNonNull(inventory);
		this.m_slot = Contract.notNegative(slot);
		
		this.m_fluidHandler = Objects.requireNonNull(fluidHandler);
		this.m_tank = Contract.notNegative(tank);
		this.m_transferRate = Contract.notNegative(transferRate);
		
		this.m_buffer = new FluidTank(this.m_fluidHandler.getTankCapacity(this.m_tank));
	} // end constructor
	
	
	
	public @NotNull ContainerData getData()
	{
		return this.m_data;
	} // end getData()
	
	public @NotNegative int countDown() 
	{
		return this.m_buffer.getFluidAmount();
	} // end countDown()
	
	public @NotNegative int initial() 
	{
		return this.m_initial;
	} // end initial()
	
	
	
	public boolean tick(@NotNull Level level, @NotNull BlockPos position)
	{
		ItemStack holding = this.m_inventory.getStackInSlot(this.m_slot);
		if(this.m_initialItemStack != null && !ItemStack.matches(this.m_initialItemStack, holding)) 
		{
			this.m_buffer.setFluid(FluidStack.EMPTY);
			this.m_initial = 0;
		}
		
		IFluidHandlerItem heldHandler = holding.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
		
		
		// TODO, preemptive bucket removal voids the untransfered fraction, probably change this
		boolean changed = false;
		
		if (holding.isEmpty() || heldHandler == null)// || holding.getCapability(YATMCapabilities.COMPONENT).isPresent()) 
		{
			return changed;
		}
		
		

		if (this.m_buffer.getFluidAmount() <= 0)
		{
			// TODO, inline
			this.m_initial = SlotUtil.queueToFillFromSlot(level, position, this.m_inventory, this.m_slot, this.m_fluidHandler, this.m_tank, this.m_buffer, this.m_transferRate);
			if (this.m_initial > 0)
			{
				this.m_initialItemStack = this.m_inventory.getStackInSlot(this.m_slot);
				changed = true;
			}
		}
		if (this.m_buffer.getFluidAmount() > 0)
		{
			this.m_fluidHandler.fill(this.m_buffer.drain(this.m_transferRate, FluidAction.EXECUTE), FluidAction.EXECUTE);
		
			if (this.m_buffer.getFluidAmount() <= 0)
			{
				this.m_initial = 0;
				this.m_initialItemStack = null;
			}
			changed = true;
		}
		return changed;
	} // end tick()
	
	

	@Override
	public @Nullable CompoundTag serializeNBT()
	{
		if(this.m_initial > 0 && this.m_buffer.getFluidAmount() > 0 && this.m_initialItemStack != null) 
		{
			CompoundTag tag = new CompoundTag();
			tag.put(FillTankManager.BUFFER_TAG_NAME, this.m_buffer.writeToNBT(new CompoundTag()));
			tag.putInt(FillTankManager.INITIAL_TAG_NAME, this.m_initial);
			tag.put(FillTankManager.STACK_TAG_NAME, this.m_initialItemStack.save(new CompoundTag()));
			return tag;
		}
		return null;
	} // end serializeNBT()

	@Override
	public void deserializeNBT(@NotNull CompoundTag tag)
	{
		if (tag.contains(FillTankManager.BUFFER_TAG_NAME) && tag.contains(FillTankManager.INITIAL_TAG_NAME) && tag.contains(FillTankManager.STACK_TAG_NAME))
		{
			this.m_buffer.readFromNBT(tag.getCompound(FillTankManager.BUFFER_TAG_NAME));
			this.m_initial = tag.getInt(FillTankManager.INITIAL_TAG_NAME);
			this.m_initialItemStack = ItemStack.of(tag.getCompound(FillTankManager.STACK_TAG_NAME));
		}
	} // end deserializeNBT()
	
} // end class