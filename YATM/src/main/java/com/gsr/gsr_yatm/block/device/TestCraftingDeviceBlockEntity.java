package com.gsr.gsr_yatm.block.device;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gsr.gsr_yatm.recipe.ITimedRecipe;
import com.gsr.gsr_yatm.utilities.contract.annotation.NotNegative;
import com.gsr.gsr_yatm.utilities.network.ICompositeAccessSpecification;
import com.gsr.gsr_yatm.utilities.network.IContainerDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TestCraftingDeviceBlockEntity<T extends ITimedRecipe<C>, C extends Container> extends CraftingDeviceBlockEntity<T, C>
{
	private static IContainerDataProvider<DeviceBlockEntity> s_containerDataProvider;
	private static ICompositeAccessSpecification m_accessSpec;
	protected @NotNull ContainerData m_data;// = Objects.requireNonNull(s_containerDataProvider.createFor(this));
	
	
	
	public TestCraftingDeviceBlockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNegative int inventorySlotCount, @NotNull RecipeType<T> recipeType)
	{
		super(type, blockPos, blockState, inventorySlotCount, recipeType);
		
	} // end constructor

	protected abstract <D extends DeviceBlockEntity> @NotNull IContainerDataProvider<D> createContainerDataProvider();
	
	
	protected void initializeContainerData() 
	{
		if(TestCraftingDeviceBlockEntity.s_containerDataProvider == null) 
		{
			TestCraftingDeviceBlockEntity.s_containerDataProvider = Objects.requireNonNull(this.createContainerDataProvider());
			TestCraftingDeviceBlockEntity.m_accessSpec = Objects.requireNonNull(s_containerDataProvider.createSpec());
		}
		this.m_data = Objects.requireNonNull(s_containerDataProvider.createFor(this));
	} // end initializeContainerData()
	
	
	public static @Nullable ICompositeAccessSpecification getAccessSpec() 
	{
		return TestCraftingDeviceBlockEntity.m_accessSpec;
	} // end getAccessSpec()
	
	@Override
	public @NotNull ContainerData getDataAccessor()
	{
		return this.m_data;
	} // end getDataAccessor

} // end class