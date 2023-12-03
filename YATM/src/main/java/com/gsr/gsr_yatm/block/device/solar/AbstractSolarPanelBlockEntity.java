package com.gsr.gsr_yatm.block.device.solar;

import java.util.EnumMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.current.CurrentHandler;
import com.gsr.gsr_yatm.utilities.capability.current.ExtractOnlyCurrentHandler;
import com.gsr.gsr_yatm.utilities.network.AccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.CurrentHandlerContainerData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public abstract class AbstractSolarPanelBlockEntity extends DeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 1;
	private static int s_dataSlotCount;
	
	public static int getDataSlotCount() 
	{ 
		return AbstractSolarPanelBlockEntity.s_dataSlotCount;
	} // end getDataSlotCount()
	
	public static final int POWER_SLOT = 0;
	
	private static AccessSpecification s_currentData;
	
	public static AccessSpecification getCurrentData() 
	{ 
		return AbstractSolarPanelBlockEntity.s_currentData;
	} // end getCurrenData()
	
	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_SAFE_CURRENT_TAG_NAME = "maxSafeCurrent";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";	
	private static final String SOLAR_PANEL_SETTINGS_TAG_NAME = "solarSettings";
	
	private final ImmutableList<Direction> m_powerableFaces;
	
	private static final int RECHECK_SLOT_PERIOD = 60;
	private static final int RECHECK_NEIGHBORS_PERIOD = 60;
	private int m_slotCapTryClaimCountDown = 0;
	private int m_recheckNighborsCountDown = 0;
	
	private boolean hasTriedClaimingSlotCapSinceReload = false;
	private LazyOptional<ICurrentHandler> m_slotCap = LazyOptional.empty();
	private ICurrentHandler m_slotHandler = null;
	
	private int m_maxCurrentTransfer;
	private SolarPanelSettings m_settings;
	
	private LazyOptional<IItemHandler> m_powerSlotLazyOptional = LazyOptional.of(() -> this.m_inventory);
	
	private Map<Direction, ICurrentHandler> m_attachedSides = new EnumMap<>(Direction.class);
	private ContainerData m_data; 
	
	@Deprecated(forRemoval = true) public ICurrentHandler m_internalCurrentStorer;
	@Deprecated(forRemoval = true) public int m_maxSafeCurrentTransfer;
	
	public AbstractSolarPanelBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState, ImmutableList<Direction> powerableFaces)
	{
		this(type, blockPos, blockState, powerableFaces, 0, 0, 0, SolarPanelSettings.EMPTY);
	} // end constructor
	
	public AbstractSolarPanelBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState, ImmutableList<Direction> powerableFaces, int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer, SolarPanelSettings settings)
	{
		super(type, blockPos, blockState, 1);
		this.setup(currentCapacity, maxCurrentTransfer, maxCurrentTransfer, settings);
		this.m_powerableFaces = powerableFaces;
	} // end constructor
	
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(CURRENT_CAPACITY_TAG_NAME, this.m_internalCurrentStorer.capacity());
		tag.putInt(MAX_SAFE_CURRENT_TAG_NAME, this.m_maxSafeCurrentTransfer);
		tag.putInt(MAX_CURRENT_TAG_NAME, this.m_maxCurrentTransfer);
		tag.put(SOLAR_PANEL_SETTINGS_TAG_NAME, this.m_settings.serializeNBT());
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(CompoundTag tag)
	{
		int currentCapacity = 0;		
		int maxSafeCurrentTransfer = 0;
		int maxCurrentTransfer = 0;
		SolarPanelSettings settings = SolarPanelSettings.EMPTY;
		
		if(tag.contains(CURRENT_CAPACITY_TAG_NAME)) 
		{
			currentCapacity = tag.getInt(CURRENT_CAPACITY_TAG_NAME);
		}
		if(tag.contains(MAX_SAFE_CURRENT_TAG_NAME)) 
		{
			maxSafeCurrentTransfer = tag.getInt(MAX_SAFE_CURRENT_TAG_NAME);
		}
		if(tag.contains(MAX_CURRENT_TAG_NAME)) 
		{
			maxCurrentTransfer = tag.getInt(MAX_CURRENT_TAG_NAME);
		}
		if(tag.contains(SOLAR_PANEL_SETTINGS_TAG_NAME)) 
		{
			settings = SolarPanelSettings.deserializeNBT(tag.getCompound(SOLAR_PANEL_SETTINGS_TAG_NAME));
		}
		this.setup(currentCapacity, maxSafeCurrentTransfer, maxCurrentTransfer, settings);
	} // end setupFromNBT()
	
	private void setup(int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer, SolarPanelSettings settings) 
	{
		this.m_internalCurrentStorer = new CurrentHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved((i) -> this.setChanged()).build();
		this.m_maxSafeCurrentTransfer = maxSafeCurrentTransfer;		
		this.m_maxCurrentTransfer = maxCurrentTransfer;
		this.m_settings = settings;
		this.m_data = this.createContainerData();
	} // end setup()
	
	protected ContainerData createContainerData() 
	{
		ContainerDataBuilder builder = new ContainerDataBuilder();
		AbstractSolarPanelBlockEntity.s_currentData = builder.addContainerDataS(new CurrentHandlerContainerData(this.m_internalCurrentStorer));
		AbstractSolarPanelBlockEntity.s_dataSlotCount = builder.getCount();
		return builder.build();
	} // end createContainerData()
	
	
	
	@Override
	public ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()
	
	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack stack, boolean simulate)
	{
		return slot == POWER_SLOT && SlotUtil.isValidPowerSlotInsert(stack);
	} // end itemInsertionValidator()
	
	@Override
	protected void onItemInsertion(int slot, ItemStack itemStack)
	{
		super.onItemInsertion(slot, itemStack);
		this.tryClaimSlotCap();
	} // end onItemInsertion()
	

	@Override
	protected void onItemWithdrawal(int slot, ItemStack stack)
	{
		super.onItemWithdrawal(slot, stack);
		this.clearOldCap();
	} // end onItemWithdrawal()
	
	
	

	@Override
	public void serverTick(Level level, BlockPos position, BlockState state)
	{
		if(!hasTriedClaimingSlotCapSinceReload) 
		{
			this.tryClaimSlotCap();
		}
		super.serverTick(level, position, state);
		boolean changed = doGenerate(level, position);
		changed |= this.doPowerSlot(level, position);
		changed |= this.doPowerSides(level, position);
		
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doGenerate(Level level, BlockPos pos) 
	{
		boolean changed = false;
		float generated = this.m_settings.currentPerTick();
		generated *= level.canSeeSky(pos.above()) ? 1 : this.m_settings.percentWithoutSky();
		generated *= !level.isDay() ? 1 : this.m_settings.percentDuringDay();
		generated *= !level.isNight() ? 1 : this.m_settings.percentDuringNight();
		// TODO, this seemingly isn't working as is expected, specifically, nothing is happening.
		generated *= !level.isRainingAt(pos) ? 1 : this.m_settings.percentDuringPrecipitation();
		int scaledGenerated = (int)generated;
		if(scaledGenerated > 0) 
		{
			return this.m_internalCurrentStorer.recieveCurrent(scaledGenerated, false) > 0;
		}
		return changed;
	} // end doGenerateTick()
	
	private boolean doPowerSlot(Level level, BlockPos pos) 
	{
		if(this.m_slotHandler != null) 
		{
			return this.m_slotHandler.recieveCurrent(this.m_internalCurrentStorer.extractCurrent(this.m_slotHandler.recieveCurrent(this.m_internalCurrentStorer.extractCurrent(this.m_maxCurrentTransfer, true), true), false), false) > 0;
			// return SlotUtilities.tryPowerSlot(this.m_uncheckedInventory, POWER_SLOT, this.m_internalCurrentStorer, this.m_maxCurrentTransfer) > 0;
		}
		return false;
	} // end doGenerateTick()
	
	private boolean doPowerSides(Level level, BlockPos pos) 
	{
		if(this.m_slotHandler == null && this.m_slotCapTryClaimCountDown-- <= 0) 
		{
			this.m_slotCapTryClaimCountDown = RECHECK_SLOT_PERIOD;
			if(!this.tryClaimSlotCap()) 
			{
				return false;
			}
		}
		else if(this.m_slotHandler == null)
		{
			return false;
		}
		
		if(this.m_recheckNighborsCountDown-- <= 0) 
		{
			this.m_recheckNighborsCountDown = RECHECK_NEIGHBORS_PERIOD;
			for(Direction dir : m_powerableFaces) 
			{
				if(this.m_attachedSides.get(dir) == null) 
				{
					BlockEntity be = level.getBlockEntity(pos.relative(dir));
					if(be != null)
					{
						this.tryAttachCap(dir, be.getCapability(YATMCapabilities.CURRENT));
					}
				}
			}
		}
		boolean changed = false;
		
		float attachedSides = 0;
		for(Direction dir : m_powerableFaces) 
		{
			attachedSides += this.m_attachedSides.get(dir) != null ? 1 : 0;
		}
		int sideAllotment = (int)(((float)this.m_slotHandler.extractCurrent(Integer.MAX_VALUE, true)) / attachedSides);
		for(Direction dir : m_powerableFaces) 
		{
			ICurrentHandler c = this.m_attachedSides.get(dir);
			if(c != null) 
			{
				changed |= c.recieveCurrent(this.m_slotHandler.extractCurrent(sideAllotment, false), false) > 0;
			}
		}
		
		return changed;
	} // end doPowerSides()
	

	
	private boolean tryClaimSlotCap() 
	{
		LazyOptional<ICurrentHandler> slotRawCap = this.m_uncheckedInventory.getStackInSlot(POWER_SLOT).getCapability(YATMCapabilities.CURRENT);
		if(slotRawCap.isPresent()) 
		{
			this.clearOldCap();
			this.m_slotCap = LazyOptional.of(() -> new ExtractOnlyCurrentHandler(slotRawCap.orElse(null)));
			slotRawCap.addListener((s) -> this.m_slotCap.invalidate());
		}
		this.m_slotHandler = slotRawCap.orElse(null);
		return this.m_slotHandler != null;
	} // end tryClaimSlotCap()
	
	private void tryAttachCap(Direction dir, LazyOptional<ICurrentHandler> cap) 
	{
		ICurrentHandler ch = cap.orElse(null);
		if(ch != null) 
		{
			this.m_attachedSides.put(dir, ch);
			cap.addListener((l) -> m_attachedSides.remove(dir));
		}
	} // end tryAttachCap()

	private void clearOldCap() 
	{
		if(this.m_slotCap.isPresent()) 
		{
			this.m_slotCap.invalidate();
		}
		this.m_slotCap = LazyOptional.empty();
		this.m_slotHandler = null;
	} // end clearOldCap()
	
	
	
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(side == null || this.m_powerableFaces.contains(side)) 
		{
			if(cap == YATMCapabilities.CURRENT && this.m_slotCap.isPresent()) 
			{
				this.m_slotCap.cast();
			}
			LazyOptional<T> c = SlotUtil.getSlotsCapability(this.m_powerSlotLazyOptional.cast(), cap);
			if(c.isPresent()) 
			{
				return c;
			}
		}
		return super.getCapability(cap, side);
	} // end getCapabilities()
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		this.m_powerSlotLazyOptional.invalidate();
	} // end invalidateCaps()
	
	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		this.m_powerSlotLazyOptional = LazyOptional.of(() -> this.m_inventory);
	} // end reviveCaps()
	
} // end class