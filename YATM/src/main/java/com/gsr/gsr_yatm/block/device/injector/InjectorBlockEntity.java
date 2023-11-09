package com.gsr.gsr_yatm.block.device.injector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.device.CraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.block.device.DeviceTierConstants;
import com.gsr.gsr_yatm.block.device.TestCraftingDeviceBlockEntity;
import com.gsr.gsr_yatm.recipe.injecting.InjectingRecipe;
import com.gsr.gsr_yatm.registry.YATMBlockEntityTypes;
import com.gsr.gsr_yatm.registry.YATMRecipeTypes;
import com.gsr.gsr_yatm.utilities.capability.SlotUtil;
import com.gsr.gsr_yatm.utilities.capability.fluid.ConfigurableTankWrapper;
import com.gsr.gsr_yatm.utilities.network.AccessSpecification;
import com.gsr.gsr_yatm.utilities.network.CompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.ContainerDataBuilder;
import com.gsr.gsr_yatm.utilities.network.FluidHandlerContainerData;
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
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class InjectorBlockEntity extends TestCraftingDeviceBlockEntity<InjectingRecipe, Container>
{
	public static final int INVENTORY_SLOT_COUNT = 5;
	
	public static final int FILL_INPUT_TANK_SLOT = 0;
	public static final int DRAIN_INPUT_TANK_SLOT = 1;
	public static final int SUBSTRATE_SLOT = 2;
	public static final int RESULT_SLOT = 3;
	public static final int POWER_SLOT = 4;

	public static final String TANK_DATA_SPEC_KEY = "tankInfo";
	public static final String FILL_PROGRESS_SPEC_KEY = "fillProgress";
	public static final String DRAIN_PROGRESS_SPEC_KEY = "drainProgress";

	private static final String TANK_CAPACITY_TAG_NAME = "tankCapacity";
	private static final String MAX_FLUID_TRANSFER_RATE_TAG_NAME = "maxFluidTransferRate";

	public static final String DRAIN_INPUT_COUNT_DOWN_TAG_NAME = "drainInputCount";
	public static final String DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME = "drainInputInitial";
	public static final String FILL_INPUT_TRANSFER_BUFFER_TAG_NAME = "fillInputBuffer";
	public static final String FILL_INPUT_TRANSFER_INITIAL_TAG_NAME = "fillInputInitial";
	public static final String INPUT_TANK_TAG_NAME = "inputTank";



	private int m_maxFluidTransferRate;
	private FluidTank m_rawInputTank;
	private ConfigurableTankWrapper m_inputTank;
	private FluidTank m_fillInputTankBuffer;
	private int m_fillInputTankInitialTransferSize = 0;
	private int m_drainInputTankCountDown = 0;
	private int m_drainInputTankInitialTransferSize = 0;	



	public InjectorBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		this(blockPos, blockState, DeviceTierConstants.EMPTY);
	} // end constructor

	public InjectorBlockEntity(BlockPos blockPos, BlockState blockState, DeviceTierConstants constants)
	{
		super(YATMBlockEntityTypes.INJECTOR.get(), blockPos, blockState, InjectorBlockEntity.INVENTORY_SLOT_COUNT, YATMRecipeTypes.INJECTING.get());
		this.setup(constants.tankCapacity(), constants.maxFluidTransferRate());
	} // end constructor

	@Override
	protected @NotNull CompoundTag setupToNBT()
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt(InjectorBlockEntity.TANK_CAPACITY_TAG_NAME, this.m_rawInputTank.getCapacity());
		tag.putInt(InjectorBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME, this.m_maxFluidTransferRate);
		return tag;
	} // end setupToNBT()

	@Override
	protected void setupFromNBT(@NotNull CompoundTag tag)
	{
		int fluidCapacity = 0;
		int maxFluidTransferRate = 0;

		if (tag.contains(InjectorBlockEntity.TANK_CAPACITY_TAG_NAME))
		{
			fluidCapacity = tag.getInt(InjectorBlockEntity.TANK_CAPACITY_TAG_NAME);
		}
		if (tag.contains(InjectorBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME))
		{
			maxFluidTransferRate = tag.getInt(InjectorBlockEntity.MAX_FLUID_TRANSFER_RATE_TAG_NAME);
		}
		this.setup(fluidCapacity, maxFluidTransferRate);
	} // end setupFromNBT()

	private void setup(int tankCapacities, int maxFluidTransferRate)
	{
		this.m_maxFluidTransferRate = maxFluidTransferRate;
		this.m_fillInputTankBuffer = new FluidTank(tankCapacities);
		this.m_rawInputTank = new FluidTank(tankCapacities);
		this.m_inputTank = new ConfigurableTankWrapper(this.m_rawInputTank, this::onFluidContentsChanged);
		this.initializeContainerData();
	} // end setup()

	@SuppressWarnings("unchecked")
	@Override
	protected @NotNull IContainerDataProvider<InjectorBlockEntity> createContainerDataProvider()
	{
		return new IContainerDataProvider<InjectorBlockEntity>() 
		{
			private ICompositeAccessSpecification m_lazySpec = new LazyCompositeAccessSpecification(() -> this.m_spec);
			private ICompositeAccessSpecification m_spec;
			
			@Override
			public ContainerData createFor(InjectorBlockEntity t)
			{				
				List<Map.Entry<String, AccessSpecification>> specs = new ArrayList<>();
				ContainerDataBuilder builder = new ContainerDataBuilder();
				specs.add(Map.entry(CraftingDeviceBlockEntity.CRAFT_PROGRESS_SPEC_KEY, builder.addContainerData(t.m_craftProgressC)));
				specs.add(Map.entry(InjectorBlockEntity.TANK_DATA_SPEC_KEY, builder.addContainerData(new FluidHandlerContainerData(t.m_inputTank, 0))));
				AccessSpecification fillPrg = builder.addProperty(() -> t.m_fillInputTankBuffer.getFluidAmount(), (i) -> {});
				AccessSpecification fillInit = builder.addProperty(() -> t.m_fillInputTankInitialTransferSize, (i) -> {});
				specs.add(Map.entry(InjectorBlockEntity.FILL_PROGRESS_SPEC_KEY, AccessSpecification.join(fillPrg, fillInit)));
				AccessSpecification drainPrg = builder.addProperty(() -> t.m_drainInputTankCountDown, (i) -> {});
				AccessSpecification drainInit = builder.addProperty(() -> t.m_drainInputTankInitialTransferSize, (i) -> {});
				specs.add(Map.entry(InjectorBlockEntity.DRAIN_PROGRESS_SPEC_KEY, AccessSpecification.join(drainPrg, drainInit)));
				if(this.m_spec == null) 
				{
					this.m_spec = new CompositeAccessSpecification(specs);
					this.m_lazySpec = null;
				}
				return builder.build();
			} // end createFor()

			@Override
			public ICompositeAccessSpecification createSpec()
			{
				if(this.m_spec == null) 
				{
					return this.m_lazySpec;
				}
				return this.m_spec;
			} // end createSpec
		};
	} // end createContainerDataProvider()
	
	
	
	@Override
	protected boolean itemInsertionValidator(int slot, ItemStack itemStack, boolean simulate)
	{
		return switch (slot)
		{
			case InjectorBlockEntity.FILL_INPUT_TANK_SLOT -> SlotUtil.isValidTankFillSlotInsert(itemStack);
			case InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT -> SlotUtil.isValidTankDrainSlotInsert(itemStack);
			case InjectorBlockEntity.RESULT_SLOT -> false;
			default -> true;
		};
	} // end validateSlotInsertion()



	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState state)
	{
		super.serverTick(level, blockPos, state);

		boolean changed = this.doAcceptPower();
		changed |= this.doFillInputTank();
		changed |= this.m_waitingForLoad 
				? false 
				: 
					this.m_activeRecipe != null 
					&& this.m_currentTransferedThisTick < this.m_activeRecipe.getCurrentPerTick() 
					? false 
					: this.doCrafting();
		changed |= this.doDrainInputTank();

		if (changed)
		{
			this.setChanged();
		}
	} // end serverTick()

	private boolean doAcceptPower()
	{
		boolean changed = false;

		return changed;
	} // end doAcceptPower()

	private boolean doFillInputTank()
	{
		boolean changed = false;
		if (this.m_fillInputTankBuffer.getFluidAmount() <= 0)
		{
			this.m_fillInputTankInitialTransferSize = SlotUtil.queueToFillFromSlot(this.level, this.worldPosition, this.m_inventory, InjectorBlockEntity.FILL_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_fillInputTankBuffer, this.m_maxFluidTransferRate);
			if (this.m_fillInputTankInitialTransferSize > 0)
			{
				changed = true;
			}
		}
		if (this.m_fillInputTankBuffer.getFluidAmount() > 0)
		{
			this.m_inputTank.fill(this.m_fillInputTankBuffer.drain(this.m_maxFluidTransferRate, FluidAction.EXECUTE), FluidAction.EXECUTE);
			if (this.m_fillInputTankBuffer.getFluidAmount() <= 0)
			{
				this.m_fillInputTankInitialTransferSize = 0;
			}
			changed = true;
		}
		return changed;
	}// end doFillInputTank()

	private boolean doDrainInputTank()
	{
		boolean changed = false;
		if (this.m_drainInputTankCountDown > 0)
		{
			this.m_drainInputTankCountDown = SlotUtil.countDownOrDrainToSlot(this.level, this.worldPosition, this.m_inventory, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_drainInputTankInitialTransferSize, this.m_drainInputTankCountDown, this.m_maxFluidTransferRate);
			if (this.m_drainInputTankCountDown <= 0)
			{
				this.m_drainInputTankInitialTransferSize = 0;
			}
			changed = true;
		}
		if (m_drainInputTankInitialTransferSize == 0)
		{
			this.m_drainInputTankInitialTransferSize = SlotUtil.queueToDrainToSlot(this.m_inventory, InjectorBlockEntity.DRAIN_INPUT_TANK_SLOT, this.m_inputTank, 0, this.m_maxFluidTransferRate);
			this.m_drainInputTankCountDown = this.m_drainInputTankInitialTransferSize;

			changed = true;
		}
		return changed;
	} // end doDrainInputTank()



	@Override
	protected void setRecipeResults(InjectingRecipe from)
	{
		from.setResults(this.m_uncheckedInventory);
	} // end setRecipeResults()

	@Override
	protected boolean canUseRecipe(InjectingRecipe from)
	{
		return from.canBeUsedOn(this.m_uncheckedInventory, this.m_inputTank);
	} // end canUseRecipe()

	@Override
	protected void startRecipe(InjectingRecipe from)
	{
		from.startRecipe(this.m_uncheckedInventory, this.m_inputTank);
	} // end startRecipe()



	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);

		if (this.m_rawInputTank.getFluidAmount() > 0)
		{
			tag.put(InjectorBlockEntity.INPUT_TANK_TAG_NAME, this.m_rawInputTank.writeToNBT(new CompoundTag()));
		}
		if (this.m_fillInputTankBuffer.getFluidAmount() > 0 && this.m_fillInputTankInitialTransferSize > 0)
		{
			tag.put(InjectorBlockEntity.FILL_INPUT_TRANSFER_BUFFER_TAG_NAME, this.m_fillInputTankBuffer.writeToNBT(new CompoundTag()));
			tag.putInt(InjectorBlockEntity.FILL_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_fillInputTankInitialTransferSize);
		}
		if (this.m_drainInputTankCountDown > 0 && this.m_drainInputTankInitialTransferSize > 0)
		{
			tag.putInt(InjectorBlockEntity.DRAIN_INPUT_COUNT_DOWN_TAG_NAME, this.m_drainInputTankCountDown);
			tag.putInt(InjectorBlockEntity.DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME, this.m_drainInputTankInitialTransferSize);
		}
	} // end saveAdditional()

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		if (tag.contains(InjectorBlockEntity.INPUT_TANK_TAG_NAME))
		{
			this.m_rawInputTank.readFromNBT(tag.getCompound(InjectorBlockEntity.INPUT_TANK_TAG_NAME));
		}
		if (tag.contains(InjectorBlockEntity.FILL_INPUT_TRANSFER_BUFFER_TAG_NAME) && tag.contains(InjectorBlockEntity.FILL_INPUT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_fillInputTankBuffer.readFromNBT(tag.getCompound(InjectorBlockEntity.FILL_INPUT_TRANSFER_BUFFER_TAG_NAME));
			this.m_fillInputTankInitialTransferSize = tag.getInt(InjectorBlockEntity.FILL_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
		if (tag.contains(InjectorBlockEntity.DRAIN_INPUT_COUNT_DOWN_TAG_NAME) && tag.contains(InjectorBlockEntity.DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME))
		{
			this.m_drainInputTankCountDown = tag.getInt(InjectorBlockEntity.DRAIN_INPUT_COUNT_DOWN_TAG_NAME);
			this.m_drainInputTankInitialTransferSize = tag.getInt(InjectorBlockEntity.DRAIN_INPUT_TRANSFER_INITIAL_TAG_NAME);
		}
	} // end load()

} // end class