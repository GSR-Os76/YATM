package com.gsr.gsr_yatm.block.device.heat_furnace;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import com.gsr.gsr_yatm.YATMConfigs;
import com.gsr.gsr_yatm.api.capability.IHeatHandler;
import com.gsr.gsr_yatm.api.capability.YATMCapabilities;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.SerializableBehavior;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.component.InputComponentManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.CraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.crafting.HeatAcceleratedCraftingManager;
import com.gsr.gsr_yatm.block.device.behaviors.implementation.heat.HeatingManager;
import com.gsr.gsr_yatm.block.device.builder.DeviceBuilder;
import com.gsr.gsr_yatm.block.device.builder.DeviceDefinition;
import com.gsr.gsr_yatm.block.device.builder.game_objects.BuiltDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.smelting.WrappedSmeltingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.CapabilityUtil;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.heat.OnChangedHeatHandler;
import com.gsr.gsr_yatm.utilities.capability.item.InventoryWrapper;
import com.gsr.gsr_yatm.utilities.generic.BackedFunction;
import com.gsr.gsr_yatm.utilities.generic.Property;
import com.gsr.gsr_yatm.utilities.generic.SetUtil;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple;
import com.gsr.gsr_yatm.utilities.generic.tuples.Tuple2;
import com.gsr.gsr_yatm.utilities.network.container_data.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.container_data.implementation.PropertyContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;

// TODO, allow placing food items on top to cook them, rend them too. NOTE: campfires might have this functionality as well, refrenceable
// Note: smoker recipes likely are those that're desired
// Likely in a later update than 1.1.0
public class HeatFurnaceBlockEntity extends BuiltDeviceBlockEntity
{
	public static final int INVENTORY_SLOT_COUNT = 3;
	
	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int HEAT_SLOT = 2;
	
	public static final String BURN_PROGRESS_SPEC_KEY = "burnProgress";
	public static final String CRAFT_PROGRESS_SPEC_KEY = "craftProgress";
	public static final String TEMPERATURE_SPEC_KEY = "temperature";
	public static final String MAX_TEMPERATURE_SPEC_KEY = "maxTemperature";
	
	public static final ICompositeAccessSpecification ACCESS_SPEC = CompositeAccessSpecification.of(List.of(
			Map.entry(BURN_PROGRESS_SPEC_KEY, HeatingManager.SLOT_COUNT),
			Map.entry(CRAFT_PROGRESS_SPEC_KEY, CraftingManager.SLOT_COUNT),
			Map.entry(TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY),
			Map.entry(MAX_TEMPERATURE_SPEC_KEY, PropertyContainerData.LENGTH_PER_PROPERTY)
			));
	
	public HeatFurnaceBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		super(YATMBlockEntityTypes.HEAT_FURNACE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state));
	} // end constructor



	@Override
	protected void define(@NotNull Consumer<DeviceDefinition> definitionReceiver, @NotNull ICapabilityProvider defaultCapabilityProvider)
	{
		BackedFunction<IItemHandler, IItemHandler> inv = new BackedFunction<>((i) -> InventoryWrapper.Builder.of(i).slotValidator((s, is, sim) -> s != HeatFurnaceBlockEntity.RESULT_SLOT).build());
		OnChangedHeatHandler h = this.m_helpers.newHeatHandler(IHeatHandler.getAmbientTemp(this.getLevel(), this.getBlockPos()), YATMConfigs.HEAT_FURNACE_MAX_TEMPERATURE.get());
		
		BackedFunction<IItemHandler, HeatingManager> hM = new BackedFunction<>((i) -> new HeatingManager(i, HeatFurnaceBlockEntity.HEAT_SLOT, h));
		BackedFunction<IItemHandler, InputComponentManager<IHeatHandler>> hFCM = new BackedFunction<>((i) -> new InputComponentManager<>(i, HeatFurnaceBlockEntity.HEAT_SLOT, h, YATMCapabilities.HEAT));

		BackedFunction<IItemHandler, CraftingManager<WrappedSmeltingRecipe, Container, Tuple2<IItemHandler, IHeatHandler>>> cM = new BackedFunction<>((i) -> new CraftingManager<>(YATMRecipeTypes.SMELTING_PLUS.get(), () -> Tuple.of(i, h)));

		DeviceBuilder.of(this::createInventory, definitionReceiver)
		.inventory()
		.slot().end()
		.slot().end()
		.slot().insertionValidator(SlotUtil.HEAT_SLOT_INSERTION_VALIDATOR).end()
		.end()
		
		.behavior(hM::apply).allDefaults().end()
		.behavior(hFCM::apply).allDefaults().end()
		.behavior((i) -> new SerializableBehavior(i, "inventory")).allDefaults().end()
		.behavior(new SerializableBehavior(h, "heatHandler")).allDefaults().end()
		.behavior(cM::apply).changeListener().serializable().end()
		.behavior(new HeatAcceleratedCraftingManager(cM.get()::tick, h, cM.get()::getActiveRecipe)).allDefaults().end()
		
		.containerData()
		.addContainerData(hM.get().getData())
		.addContainerData(cM.get().getData())
		.addProperty(new Property<>(h::getTemperature, (i) -> {}))
		.addProperty(new Property<>(h::maxTemperature, (i) -> {}))
		.end()
		
		.getInventory(inv::apply)
		
		.capabilityProvider()
		.face((Direction)null)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, inv.get())
		.elifReturnsWhen(YATMCapabilities.HEAT, h).end()
		
		.face(Direction.UP)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), HeatFurnaceBlockEntity.INPUT_SLOT)).end()
		
		.face(Direction.DOWN)
		.returnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), HeatFurnaceBlockEntity.RESULT_SLOT)).end()
		
		.face(() -> SetUtil.of(Direction.Plane.HORIZONTAL.stream()))
		.returns(hFCM.get())
		.elifEmptyReturnsWhen(ForgeCapabilities.ITEM_HANDLER, this.m_helpers.slot(inv.get(), HeatFurnaceBlockEntity.HEAT_SLOT)).end()
		
		.last(CapabilityUtil.whenOrDefault(YATMCapabilities.HEAT, h, defaultCapabilityProvider))
		.end();
		this.m_inventory = inv.get();
	} // end define()
	
} // end class
/*
CraftingDeviceBlockEntity<WrappedSmeltingRecipe, Container, Tuple2<IItemHandler, IHeatHandler>>
{
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
	private final int m_maxTemperature = YATMConfigs.HEAT_FURNACE_MAX_TEMPERATURE.get();
	
	private static final IContainerDataProvider<HeatFurnaceBlockEntity> CONTAINER_DATA_PROVIDER = HeatFurnaceBlockEntity.createContainerDataProvider();
	public static final ICompositeAccessSpecification ACCESS_SPEC = CONTAINER_DATA_PROVIDER.createSpec();
	private final ContainerData m_data = CONTAINER_DATA_PROVIDER.createFor(this);
	
	
	
// 	public CurrentFurnaceBlockEntity(BlockPos position, BlockState state)
//	{
//		this(Objects.requireNonNull(position), Objects.requireNonNull(state), DeviceTierConstants.EMPTY);
//	} // end constructor()
//	
	public HeatFurnaceBlockEntity(BlockPos position, BlockState state)//, DeviceTierConstants constants)
	{
		super(YATMBlockEntityTypes.HEAT_FURNACE.get(), Objects.requireNonNull(position), Objects.requireNonNull(state), HeatFurnaceBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.SMELTING_PLUS.get());
		// this.setup(Contract.notNegative(constants.maxTemperature()));
	} // end constructor()
	
	@Override
	public @NotNull Tuple2<IItemHandler, IHeatHandler> getContext()
	{
		return new Tuple2<>(this.m_inventory, new OnChangedHeatHandler(3233, (i) -> {}));
	} // end getContext()
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
	
	

	private static IContainerDataProvider<HeatFurnaceBlockEntity> createContainerDataProvider()
	{
		return new IContainerDataProvider<HeatFurnaceBlockEntity>() 
		{
			private ICompositeAccessSpecification m_spec;
			
			@Override
			public ContainerData createFor(HeatFurnaceBlockEntity t)
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
	protected boolean itemInsertionValidator(int slot, ItemStack stack, boolean simulate)
	{
		return switch(slot) 
		{
			case INPUT_SLOT -> true;
			case RESULT_SLOT -> false;
			case HEAT_SLOT -> SlotUtil.isValidHeatingSlotInsert(stack);
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
		changed |= this.m_craftingManager.tick(level, position);
		
		if (wasLitInitially != this.isLit())
		{
			level.setBlockAndUpdate(position, state.setValue(HeatFurnaceBlock.LIT, this.isLit()));
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
*/