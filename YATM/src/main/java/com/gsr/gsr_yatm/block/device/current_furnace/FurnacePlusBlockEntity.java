package com.gsr.gsr_yatm.block.device.current_furnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.InventoryUtilities;
import com.gsr.gsr_yatm.utilities.capability.SlotUtilities;
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

public class FurnacePlusBlockEntity extends CraftingDeviceBlockEntity<WrappedSmeltingRecipe, Container>
{
	public static final int INVENTORY_SLOT_COUNT = 3;
	
	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int HEAT_SLOT = 2;
	
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String TEMPERATURE_DATA_SPEC_KEY = "temperatureData";

	private static final String MAX_TEMPERATURE_TAG_NAME = "maxTemperature";
	
	private static final String BURN_TIME_ELAPSED_TAG_NAME = "burnTimeElapsed";
	private static final String BURN_TIME_INITIAL_TAG_NAME = "burnTimeInitial";
	private static final String BURN_TEMP_TAG_NAME = "burnTemp";

	private int m_burnProgress = 0;
	private int m_burnTime = 0;
	private int m_temperature = 0;
	private int m_maxTemperature = 0;
	
	private static final IContainerDataProvider CONTAINER_DATA_PROVIDER = FurnacePlusBlockEntity.createContainerDataProvider();
	public static final ICompositeAccessSpecification ACCESS_SPEC = CONTAINER_DATA_PROVIDER.createSpec();
	private final ContainerData m_data = CONTAINER_DATA_PROVIDER.createFor(this);
	
	
	
 	public FurnacePlusBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, 0);
	} // end constructor()
	
	public FurnacePlusBlockEntity(BlockPos blockPos, BlockState blockState, int maxTemperature)
	{
		super(YATMBlockEntityTypes.FURNACE_PLUS.get(), blockPos, blockState, INVENTORY_SLOT_COUNT, YATMRecipeTypes.SMELTING_PLUS.get());
		this.setup(maxTemperature);
	} // end constructor()
	
	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(MAX_TEMPERATURE_TAG_NAME, this.m_maxTemperature);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		int maxTemperature = 0;
		if(tag.contains(MAX_TEMPERATURE_TAG_NAME)) 
		{
			maxTemperature = tag.getInt(MAX_TEMPERATURE_TAG_NAME);
		}
		this.setup(maxTemperature);
	} // end setupFromNBT()

	private void setup(int maxTemperature) 
	{
		this.m_maxTemperature = maxTemperature;
	} // end setup()

	private static IContainerDataProvider createContainerDataProvider()
	{
		return new IContainerDataProvider() 
		{
			private ICompositeAccessSpecification m_spec;
			
			@Override
			public <T> ContainerData createFor(T t)
			{
				if(t instanceof FurnacePlusBlockEntity f) 
				{
					List<Map.Entry<String, AccessSpecification>> specs = new ArrayList<>();
					ContainerDataBuilder builder = new ContainerDataBuilder();
					specs.add(Map.entry(CRAFT_PROGRESS_SPEC_KEY, builder.addContainerData(f.m_craftProgressC)));
					AccessSpecification burnP = builder.addPropety(() -> f.m_burnProgress, (i) -> {});
					AccessSpecification burnT = builder.addPropety(() -> f.m_burnTime, (i) -> {});
					specs.add(Map.entry(BURN_PROGRESS_SPEC_KEY, AccessSpecification.join(burnP, burnT)));
					AccessSpecification curTemp = builder.addPropety(() -> f.m_temperature, (i) -> {});
					AccessSpecification maxTemp = builder.addPropety(() -> f.m_maxTemperature, (i) -> {});
					specs.add(Map.entry(TEMPERATURE_DATA_SPEC_KEY, AccessSpecification.join(curTemp, maxTemp)));
					if(this.m_spec == null) 
					{
						this.m_spec = new CompositeAccessSpecification(specs);
					}
					return builder.build();
				}
				return null;
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
	public ContainerData getDataAccessor()
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
			case HEAT_SLOT -> SlotUtilities.isValidHeatingSlotInsert(itemStack);
			default -> throw new IllegalArgumentException("Unexpected value of: " + slot);
		};
	} // end itemInsertionValidator()
	
	
	
	
	
	

	
	
	public boolean isLit()
	{
		return this.m_burnProgress > 0;
	} // end isLit()
	
	
	
	@Override
	public void serverTick(Level level, BlockPos pos, BlockState blockState)
	{
		boolean wasLitInitially = this.isLit();
		boolean changed = doHeat();
		changed |= ((this.m_waitingForLoad || this.isLit()) ? false : this.doCrafting());
		
		if (wasLitInitially != this.isLit())
		{
			level.setBlockAndUpdate(pos, blockState.setValue(FurnacePlusBlock.LIT, this.isLit()));
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
			if (SlotUtilities.getHeatingBurnTime(this.m_inventory.getStackInSlot(HEAT_SLOT)) > 0)
			{
				ItemStack i = this.m_inventory.extractItem(HEAT_SLOT, 1, false);
				if(i.hasCraftingRemainingItem()) 
				{
					InventoryUtilities.insertItemOrDrop(this.level, this.worldPosition, this.m_inventory, HEAT_SLOT, i.getCraftingRemainingItem());
				}				

				this.m_burnTime = SlotUtilities.getHeatingBurnTime(i);
				this.m_burnProgress = this.m_burnTime;
				this.m_temperature = SlotUtilities.getHeatingTemperature(i);
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