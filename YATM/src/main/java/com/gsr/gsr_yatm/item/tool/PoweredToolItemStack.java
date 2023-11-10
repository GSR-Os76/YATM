package com.gsr.gsr_yatm.item.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.item.IEfficiencyUpgradeItem;
import com.gsr.gsr_yatm.item.ISpeedUpgradeItem;
import com.gsr.gsr_yatm.item.component.IComponent;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandlerWrapper;
import com.gsr.gsr_yatm.utilities.capability.current.ItemStackCurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.capability.item.ItemStackItemStackHandler;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.PropertyContainerData;

import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class PoweredToolItemStack implements ICurrentHandler, ICapabilityProvider
{
	public static final int BASE_CURRENT_CAPACITY = 8000;
	
	public static final int INVENTORY_SLOT_COUNT = 3;
	public static final int POWER_SLOT = 0;
	public static final int UPGRADE_SLOT_ONE = 1;
	public static final int UPGRADE_SLOT_TWO = 2;
	
	public static final String CURRENT_STORED_SPEC_KEY = "currentStored";
	public static final String CURRENT_CAPACITY_SPEC_KEY = "currentCapacity";
	
	private final @NotNull ItemStack m_self;
	
	private final @NotNull IItemHandler m_inventory;
	
	private final @NotNull LazyOptional<ICurrentHandler> m_cap = LazyOptional.of(() -> this);
	private final @NotNull ICurrentHandler m_builtInBattery;
	private final @NotNull LazyOptional<ICurrentHandler> m_batteryCap;
	
	private @Nullable ItemStack m_cComponentStack;
	private @Nullable IComponent m_cComponent;
	private @Nullable ICurrentHandler m_cComponentCHandler;
	private @NotNull Map<Capability<?>, LazyOptional<?>> m_cComponentOptionals = new HashMap<>();

	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(PoweredToolItemStack.CURRENT_STORED_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(PoweredToolItemStack.CURRENT_CAPACITY_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
			));
	private final ContainerData m_data;
	
	
	
	public PoweredToolItemStack(@NotNull ItemStack self) 
	{
		this.m_self = Objects.requireNonNull(self);
		this.m_inventory = InventoryWrapper.Builder.of(new ItemStackItemStackHandler(PoweredToolItemStack.INVENTORY_SLOT_COUNT, this.m_self))
				.onInsertion(PoweredToolItemStack.this::onInventoryChange)
				.onWithdrawal(PoweredToolItemStack.this::onInventoryChange)
				.slotValidator(PoweredToolItemStack.this::insertionValidator).build();
		
		this.m_builtInBattery = CurrentHandlerWrapper.Builder.of(new ItemStackCurrentHandler(PoweredToolItemStack.BASE_CURRENT_CAPACITY, this.m_self))// new CurrentHandler.Builder().capacity(PoweredToolItemStack.BASE_CURRENT_CAPACITY)
				.onCurrentExtracted((i) -> this.updateDamage())
				.onCurrentRecieved((i) -> this.updateDamage()).build();
		this.m_batteryCap = LazyOptional.of(() -> this.m_builtInBattery);
		
		ContainerDataBuilder cdb = new ContainerDataBuilder();
		cdb.addProperty(() -> /*PoweredToolItemStack.this.stored(), (i) -> {});//*/PoweredToolItemStack.this.m_builtInBattery.stored(), (i) -> {});
		cdb.addProperty(() -> /*PoweredToolItemStack.this.capacity(), (i) -> {});//*/PoweredToolItemStack.this.m_builtInBattery.capacity(), (i) -> {});
		this.m_data = cdb.build();
		
		this.updateDamage();
	} // end constructor
	
	
	
	public @NotNull IItemHandler getInventory() 
	{
		return this.m_inventory;
	} // end getInventory()
	
	public @NotNull ContainerData getDataAccessor() 
	{
		return this.m_data;
	} // end getData()
	
	
	
	private void updateDamage() 
	{
		this.m_self.setDamageValue(
				Math.min(this.m_builtInBattery.capacity(), 
						Math.max(1, 
								(this.m_builtInBattery.capacity() + 1) - this.m_builtInBattery.stored()
								)
						)
				);
	} // end updateDamage()
	
	private void onInventoryChange(@NotNegative int slot, @NotNull ItemStack stack)
	{
		if(slot == PoweredToolItemStack.POWER_SLOT) 
		{
			this.tryAttachCComponent();
		}
	} // end onInventoryChange()
	
	private void tryAttachCComponent()
	{
		ItemStack stack = this.m_inventory.getStackInSlot(PoweredToolItemStack.POWER_SLOT);
		if(stack == this.m_cComponentStack) 
		{
			return;
		}
		
		if(this.m_cComponent != null) 
		{
			this.m_cComponentStack = null;
			this.m_cComponent.removeRecievingCapability(stack, this.m_batteryCap);
			this.m_cComponent = null;
			this.m_cComponentCHandler = null;
			this.m_cComponentOptionals = new HashMap<>();
		}
		
		if(stack.getItem() instanceof IComponent c) 
		{
			if(c.getValidCapabilities().contains(YATMCapabilities.CURRENT)) 
			{
				this.m_cComponentStack = stack;
				this.m_cComponent.attachRecievingCapability(stack, YATMCapabilities.CURRENT, this.m_batteryCap);
				this.m_cComponent = c;
				this.m_cComponentCHandler = stack.getCapability(YATMCapabilities.CURRENT).orElse(null);
			}
		}
	} // end tryAttachCComponent()
	
	private boolean insertionValidator(@NotNegative int slot, @NotNull ItemStack stack, boolean simulated)
	{
		return switch(slot) 
		{
			case PoweredToolItemStack.POWER_SLOT -> SlotUtil.isValidPowerSlotInsert(stack);
			case PoweredToolItemStack.UPGRADE_SLOT_ONE -> SlotUtil.isValidUpgradeSlotInsert(stack);
			case PoweredToolItemStack.UPGRADE_SLOT_TWO -> SlotUtil.isValidUpgradeSlotInsert(stack);
			default -> throw new IllegalArgumentException("Unexpected value of: " + slot);
		};
	} // end insertionValidator()
	
	
	
	public float getEfficiency() 
	{
		float bonus = 1f;
		// TODO, possibly make into a loop based system
		ItemStack s1 = this.m_inventory.getStackInSlot(PoweredToolItemStack.UPGRADE_SLOT_ONE);
		if(s1.getItem() instanceof IEfficiencyUpgradeItem u)
		{
			switch(u.getBonusType()) 
			{
				case ADDITIVE:
					bonus += (u.getBonus() * s1.getCount());
					break;
				case MULTIPLICATIVE:
					bonus *= (u.getBonus() * s1.getCount());
					break;
				default:
					throw new IllegalArgumentException("Unexpected value of: " + u.getBonusType());
			}
		}
		ItemStack s2 = this.m_inventory.getStackInSlot(PoweredToolItemStack.UPGRADE_SLOT_TWO);
		if(s2.getItem() instanceof IEfficiencyUpgradeItem u)
		{
			switch(u.getBonusType()) 
			{
				case ADDITIVE:
					bonus += (u.getBonus() * s2.getCount());
					break;
				case MULTIPLICATIVE:
					bonus *= (u.getBonus() * s2.getCount());
					break;
				default:
					throw new IllegalArgumentException("Unexpected value of: " + u.getBonusType());
			}
		}
		return bonus;
	} // end getEfficiency()
	
	public float getSpeedMultiplier() 
	{
		float bonus = 1f;
		// TODO, possibly make into a loop based system, consolidate with experience
		ItemStack s1 = this.m_inventory.getStackInSlot(PoweredToolItemStack.UPGRADE_SLOT_ONE);
		if(s1.getItem() instanceof ISpeedUpgradeItem u)
		{
			switch(u.getBonusType()) 
			{
				case ADDITIVE:
					bonus += (u.getBonus() * s1.getCount());
					break;
				case MULTIPLICATIVE:
					bonus *= (u.getBonus() * s1.getCount());
					break;
				default:
					throw new IllegalArgumentException("Unexpected value of: " + u.getBonusType());
			}
		}
		ItemStack s2 = this.m_inventory.getStackInSlot(PoweredToolItemStack.UPGRADE_SLOT_TWO);
		if(s2.getItem() instanceof ISpeedUpgradeItem u)
		{
			switch(u.getBonusType()) 
			{
				case ADDITIVE:
					bonus += (u.getBonus() * s2.getCount());
					break;
				case MULTIPLICATIVE:
					bonus *= (u.getBonus() * s2.getCount());
					break;
				default:
					throw new IllegalArgumentException("Unexpected value of: " + u.getBonusType());
			}
		}
		return bonus;
	} // end getSpeedMultiplier()
	
	
	
	@Override
	public int recieveCurrent(int amount, boolean simulate)
	{
		return this.m_cComponentCHandler != null 				
				? this.m_cComponentCHandler.recieveCurrent(amount, simulate) 
				: this.m_builtInBattery.recieveCurrent(amount, simulate);
	} // end recieveCurrent()

	@Override
	public int extractCurrent(int amount, boolean simulate)
	{
		return 0;
	} // end extractCurrent()

	@Override
	public int capacity()
	{
		return this.m_builtInBattery.capacity() + (this.m_cComponentCHandler != null ? this.m_cComponentCHandler.capacity() : 0);
	} // end capacity()

	@Override
	public int stored()
	{
		return this.m_builtInBattery.stored() + (this.m_cComponentCHandler != null ? this.m_cComponentCHandler.stored() : 0);
	} // end stored()
	
	public int getStoredCurrent() 
	{
		return this.m_builtInBattery.stored();
	} // end getStoredCurrent()
	
	public void setStoredCurrent(int to) 
	{
		int d = this.m_builtInBattery.stored() - to;
		if(d > 0) 
		{
			this.m_builtInBattery.extractCurrent(Math.abs(d), false);
		}
		else if (d < 0)
		{
			this.m_builtInBattery.recieveCurrent(Math.abs(d), false);
		}
	} // end setStoredCurrent()

	
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == YATMCapabilities.CURRENT) 
		{
			return this.m_cap.cast();
		}
		else
		{
			if(!this.m_cComponentOptionals.containsKey(cap)) 
			{
				LazyOptional<T> l = this.m_cComponentStack.getCapability(cap);
				if(l.isPresent()) 
				{
					this.m_cComponentOptionals.put(cap, LazyOptional.of(() -> l.orElse((T)null)));
				}
			}
			if(this.m_cComponentOptionals.containsKey(cap)) 
			{
				return this.m_cComponentOptionals.get(cap).cast();
			}
		}
		
		return LazyOptional.empty();
	} // end getCapability()

} // end class