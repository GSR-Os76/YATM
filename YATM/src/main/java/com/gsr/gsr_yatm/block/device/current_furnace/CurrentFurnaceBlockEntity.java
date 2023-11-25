package com.gsr.gsr_yatm.block.device.current_furnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.network.AccessSpecification;
import com.gsr.gsr_yatm.utilities.network.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.IContainerDataProvider;
import com.gsr.gsr_yatm.utilities.network.LazyCompositeAccessSpecification;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CurrentFurnaceBlockEntity extends CraftingDeviceBlockEntity<WrappedSmeltingRecipe, Container>
{
	// TODO, allow placing food items on top to cook them, rend them too. NOTE: campfires might have this functionality as well, refrenceable
	public static final int INVENTORY_SLOT_COUNT = 3;
	
	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int HEAT_SLOT = 2;
	
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String TEMPERATURE_DATA_SPEC_KEY = "temperatureData";
	
	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
	private static final String BURN_TEMP_TAG_NAME = "burnTemp";

	private int m_burnProgress = 0;
	private int m_burnTime = 0;
	private int m_temperature = 0;
	private final int m_maxTemperature = YATMConfigs.CURRENT_FURNACE_MAX_TEMPERATURE.get();
	
	private static final IContainerDataProvider<CurrentFurnaceBlockEntity> CONTAINER_DATA_PROVIDER = CurrentFurnaceBlockEntity.createContainerDataProvider();
	public static final ICompositeAccessSpecification ACCESS_SPEC = CONTAINER_DATA_PROVIDER.createSpec();
	private final ContainerData m_data = CONTAINER_DATA_PROVIDER.createFor(this);
	
	
	
// 	public CurrentFurnaceBlockEntity(BlockPos position, BlockState state)
//	{
//		this(Objects.requireNonNull(position), Objects.requireNonNull(state), DeviceTierConstants.EMPTY);
//	} // end constructor()
//	
	public CurrentFurnaceBlockEntity(BlockPos position, BlockState state)//, DeviceTierConstants constants)
	{
		super(YATMBlockEntityTypes.FURNACE_PLUS.get(), Objects.requireNonNull(position), Objects.requireNonNull(state), CurrentFurnaceBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.SMELTING_PLUS.get());
		// this.setup(Contract.notNegative(constants.maxTemperature()));
	} // end constructor()
//	
//	@Override
//	protected @NotNull CompoundTag setupToNBT()
//	{
//		CompoundTag tag = new CompoundTag();
//		tag.putInt(CurrentFurnaceBlockEntity.MAX_TEMPERATURE_TAG_NAME, this.m_maxTemperature);
//		return tag;
//	} // end setupToNBT()
//
//	@Override
//	protected void setupFromNBT(@NotNull CompoundTag tag)
//	{
//		int maxTemperature = 0;
//		if(tag.contains(CurrentFurnaceBlockEntity.MAX_TEMPERATURE_TAG_NAME)) 
//		{
//			maxTemperature = tag.getInt(CurrentFurnaceBlockEntity.MAX_TEMPERATURE_TAG_NAME);
//		}
//		this.setup(maxTemperature < 0 ? 0 : maxTemperature);
//	} // end setupFromNBT()
//
//	private void setup(@NotNegative int maxTemperature) 
//	{
//		this.m_maxTemperature = maxTemperature;
//	} // end setup()
	
	

	private static IContainerDataProvider<CurrentFurnaceBlockEntity> createContainerDataProvider()
	{
		return new IContainerDataProvider<CurrentFurnaceBlockEntity>() 
		{
			private ICompositeAccessSpecification m_spec;
			
			@Override
			public ContainerData createFor(CurrentFurnaceBlockEntity t)
			{
				List<Map.Entry<String, AccessSpecification>> specs = new ArrayList<>();
				ContainerDataBuilder builder = new ContainerDataBuilder();
				specs.add(Map.entry(CRAFT_PROGRESS_SPEC_KEY, builder.addContainerDataS(t.m_craftProgressC)));
				AccessSpecification burnP = builder.addPropertyS(() -> t.m_burnProgress, (i) -> {});
				AccessSpecification burnT = builder.addPropertyS(() -> t.m_burnTime, (i) -> {});
				specs.add(Map.entry(BURN_PROGRESS_SPEC_KEY, AccessSpecification.join(burnP, burnT)));
				AccessSpecification curTemp = builder.addPropertyS(() -> t.m_temperature, (i) -> {});
				AccessSpecification maxTemp = builder.addPropertyS(() -> t.m_maxTemperature, (i) -> {});
				specs.add(Map.entry(TEMPERATURE_DATA_SPEC_KEY, AccessSpecification.join(curTemp, maxTemp)));
				if(this.m_spec == null) 
				{
					this.m_spec = new CompositeAccessSpecification(specs);
				}
				return builder.build();
			}

			@Override
			public ICompositeAccessSpecification createSpec()
			{
				if(this.m_spec == null) 
				{
					return new LazyCompositeAccessSpecification(() -> this.m_spec);
				}
				return this.m_spec;
			} // end createSpec
			
		};
	} // end createContainerDataProvider()
	
	
	
	@Override
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor()

	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch(slot) 
		{
			case INPUT_SLOT -> true;
			case RESULT_SLOT -> false;
			case HEAT_SLOT -> SlotUtil.isValidHeatingSlotInsert(itemStack);
			default -> throw new IllegalArgumentException("Unexpected value of: " + slot);
		};
	} // end itemInsertionValidator()
	
	
	
	
	public boolean isLit()
	{
		return this.m_burnProgress > 0;
	} // end isLit()
	
	
	
	@Override
	public void serverTick(@NotNull Level level, @NotNull BlockPos position, @NotNull BlockState state)
	{
		boolean wasLitInitially = this.isLit();
		boolean changed = doHeat();
		changed |= ((this.m_waitingForLoad || this.isLit()) ? false : this.doCrafting());
		
		if (wasLitInitially != this.isLit())
		{
			level.setBlockAndUpdate(position, state.setValue(CurrentFurnaceBlock.LIT, this.isLit()));
		}
		if(changed) 
		{
			this.setChanged();
		}
	} // end serverTick()
	
	
	private boolean doHeat() 
	{
		boolean changed = false;
		if (this.m_burnProgress > 0)
		{
			if (--this.m_burnProgress == 0)
			{
				this.m_temperature = 0;
				this.m_burnTime = 0;
			}
			changed = true;
		}
		else
		{
			if (SlotUtil.getHeatingBurnTime(this.m_inventory.getStackInSlot(HEAT_SLOT)) > 0)
			{
				ItemStack i = this.m_inventory.extractItem(HEAT_SLOT, 1, false);
				if(i.hasCraftingRemainingItem()) 
				{
					InventoryUtil.insertItemOrDrop(this.level, this.worldPosition, this.m_inventory, HEAT_SLOT, i.getCraftingRemainingItem());
				}				

				this.m_burnTime = SlotUtil.getHeatingBurnTime(i);
				this.m_burnProgress = this.m_burnTime;
				this.m_temperature = SlotUtil.getHeatingTemperature(i);
				changed = true;
			}
		}
		return changed;
	} // end doHeat()
	
	@Override
	protected void setRecipeResults(WrappedSmeltingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory);
	} // end setRecipeResults()

	@Override
	protected boolean canUseRecipe(WrappedSmeltingRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(WrappedSmeltingRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory);
	} // end startRecipe()
	
	
	
	
	
	
	@Override
 	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		
		tag.putInt(BURN_TEMP_TAG_NAME, this.m_temperature);
		if(this.m_burnProgress > 0 && this.m_burnTime > 0) 
		{
			tag.putInt(BURN_TIME_ELAPSED_TAG_NAME, this.m_burnProgress);
			tag.putInt(BURN_TIME_INITIAL_TAG_NAME, this.m_burnTime);
		}
	} // end saveAdditional

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		if (tag.contains(BURN_TEMP_TAG_NAME))
		{
			this.m_temperature = tag.getInt(BURN_TEMP_TAG_NAME);
		}		
		if (tag.contains(BURN_TIME_ELAPSED_TAG_NAME) && tag.contains(BURN_TIME_INITIAL_TAG_NAME))
		{
			this.m_burnProgress = tag.getInt(BURN_TIME_ELAPSED_TAG_NAME);
			this.m_burnTime = tag.getInt(BURN_TIME_INITIAL_TAG_NAME);
		}
	} // end load()
	
} // end class