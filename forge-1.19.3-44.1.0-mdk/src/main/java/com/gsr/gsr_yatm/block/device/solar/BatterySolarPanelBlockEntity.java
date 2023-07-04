package com.gsr.gsr_yatm.block.device.solar;

import java.util.EnumMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableList;
import com.gsr.gsr_yatm.api.capability.ICurrentHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.api.implementation.CurrentUnitHandler;
import com.gsr.gsr_yatm.block.device.DeviceBlockEntity;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.utilities.SlotUtilities;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

public class BatterySolarPanelBlockEntity extends DeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 1;
	private static int s_dataSlotCount;
	
	public static int getDataSlotCount() 
	{ 
		return BatterySolarPanelBlockEntity.s_dataSlotCount;
	} // end getDataSlotCount()
	
	public static final int POWER_SLOT = 0;
	
	private static AccessSpecification s_currentData;
	
	public static AccessSpecification getCurrentData() 
	{ 
		return BatterySolarPanelBlockEntity.s_currentData;
	} // end getCurrenData()
	
	private static final String CURRENT_CAPACITY_TAG_NAME = "currentCapacity";
	private static final String MAX_SAFE_CURRENT_TAG_NAME = "maxSafeCurrent";
	private static final String MAX_CURRENT_TAG_NAME = "maxCurrent";	
	private static final String SOLAR_PANEL_SETTINGS_TAG_NAME = "solarSettings";
	
	private static final ImmutableList<Direction> EVER_ALLOWED_DIRECTIONS = ImmutableList.of(Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
	
	private static final int RECHECK_SLOT_PERIOD = 60;
	private static final int RECHECK_NEIGHBORS_PERIOD = 60;
	private int m_slotCapTryClaimCountDown = 0;
	private int m_recheckNighborsCountDown = 0;
	
	private boolean hasTriedClaimingSlotCapSinceReload = false;
	private ICurrentHandler m_slotCap = null;
	
	private int m_maxCurrentTransfer;
	private SolarPanelSettings m_settings;
	
	private LazyOptional<IItemHandler> m_powerSlotLazyOptional = LazyOptional.of(() -> this.m_inventory);
	
	private Map<Direction, ICurrentHandler> m_attachedSides = new EnumMap<>(Direction.class);
	private ContainerData m_data; 
	
	
	
	public BatterySolarPanelBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0, 0, 0, SolarPanelSettings.EMPTY);
	} // end constructor
	
	public BatterySolarPanelBlockEntity(BlockPos blockPos, BlockState blockState, int currentCapacity, int maxSafeCurrentTransfer, int maxCurrentTransfer, SolarPanelSettings settings)
	{
		super(YATMBlockEntityTypes.BATTERY_SOLAR_PANEL.get(), blockPos, blockState, 1);
		this.setup(currentCapacity, maxCurrentTransfer, maxCurrentTransfer, settings);
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
		this.m_internalCurrentStorer = new CurrentUnitHandler.Builder().capacity(currentCapacity).onCurrentExtracted(this::onCurrentExchanged).onCurrentRecieved(this::onCurrentExchanged).build();
		this.m_maxSafeCurrentTransfer = maxSafeCurrentTransfer;		
		this.m_maxCurrentTransfer = maxCurrentTransfer;
		this.m_settings = settings;
		this.m_data = this.createContainerData();
	} // end setup()
	
	protected ContainerData createContainerData() 
	{
		ContainerDataBuilder builder = new ContainerDataBuilder();
		BatterySolarPanelBlockEntity.s_currentData = builder.addContainerData(new CurrentHandlerContainerData(this.m_internalCurrentStorer));
		BatterySolarPanelBlockEntity.s_dataSlotCount = builder.getCount();
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
		return slot == POWER_SLOT && SlotUtilities.isValidPowerSlotInsert(itemStack);
	} // end itemInsertionValidator()

	
	
	
	@Override
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		if(!hasTriedClaimingSlotCapSinceReload) 
		{
			this.tryClaimSlotCap();
		}
		super.serverTick(level, pos, blockState);
		boolean changed = doGenerate(level, pos);
		changed |= this.doPowerSlot(level, pos);
		changed |= this.doPowerSides(level, pos);
		
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
		return SlotUtilities.tryPowerSlot(this.m_uncheckedInventory, POWER_SLOT, this.m_internalCurrentStorer, this.m_maxCurrentTransfer) > 0;
	} // end doGenerateTick()
	
	private boolean doPowerSides(Level level, BlockPos pos) 
	{
		if(this.m_slotCap == null && this.m_slotCapTryClaimCountDown-- <= 0) 
		{
			this.m_slotCapTryClaimCountDown = RECHECK_SLOT_PERIOD;
			if(!this.tryClaimSlotCap()) 
			{
				return false;
			}
		}
		else if(this.m_slotCap == null)
		{
			return false;
		}
		
		if(this.m_recheckNighborsCountDown-- <= 0) 
		{
			this.m_recheckNighborsCountDown = RECHECK_NEIGHBORS_PERIOD;
			for(Direction dir : EVER_ALLOWED_DIRECTIONS) 
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
		for(Direction dir : EVER_ALLOWED_DIRECTIONS) 
		{
			attachedSides += this.m_attachedSides.get(dir) != null ? 1 : 0;
		}
		int sideAllotment = (int)(((float)this.m_slotCap.extractCurrent(Integer.MAX_VALUE, true)) / attachedSides);
		for(Direction dir : EVER_ALLOWED_DIRECTIONS) 
		{
			ICurrentHandler c = this.m_attachedSides.get(dir);
			if(c != null) 
			{
				changed |= c.recieveCurrent(this.m_slotCap.extractCurrent(sideAllotment, false), false) > 0;
			}
		}
		
		return changed;
	} // end doPowerSides()
	

	
	private boolean tryClaimSlotCap() 
	{
		this.m_slotCap = this.m_uncheckedInventory.getStackInSlot(POWER_SLOT).getCapability(YATMCapabilities.CURRENT).orElse(null);
		return this.m_slotCap != null;
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

	
	
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(side == null || side != Direction.UP) 
		{
			LazyOptional<T> c = SlotUtilities.getSlotsCapability(this.m_powerSlotLazyOptional.cast(), cap);
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