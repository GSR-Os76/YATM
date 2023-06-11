package com.gsr.gsr_yatm.recipe;

import com.gsr.gsr_yatm.YATMItems;
import com.gsr.gsr_yatm.YATMRecipeSerializers;
import com.gsr.gsr_yatm.YATMRecipeTypes;
import com.gsr.gsr_yatm.block.device.extruder.ExtruderBlockEntity;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.tags.ITag;

public class ExtrusionRecipe implements Recipe<Container>
{
	private final ResourceLocation m_identifier;
	private final ItemStack m_result;

	// TODO, swap to 'Ingredients' most likely
	private ItemStack m_inputStack = null;
	private ITag<Item> m_inputTag = null;
	private int m_inputCount = 1;
	private ItemStack m_dieStack = null;
	private ITag<Item> m_dieTag = null;

	private ItemStack m_inputRemainderStack = ItemStack.EMPTY;
	private ItemStack m_dieRemainderStack = null;
	private int m_currentPerTick = 8;
	private int m_timeInTicks = 20;



	private ExtrusionRecipe(ResourceLocation identifier, ItemStack result)// ItemStack input, ItemStack die,
	{
		this.m_identifier = identifier;
		this.m_result = result.copy();

		// this.m_inputStack = input.copy();
		// this.m_dieStack = die.copy();
		// this.m_dieRemainderStack = this.m_dieStack;
	} // end constructor


	@Deprecated
	public ItemStack getInput()
	{
		return this.m_inputStack;
	} // end getInput()

	@Deprecated
	public ItemStack getDie()
	{
		return this.m_dieStack;
	} // end getDie()

	@Deprecated
	public ItemStack getResult()
	{
		return this.m_result;
	} // end getResult()

	@Deprecated
	public ItemStack getInputRemainder()
	{
		return this.m_inputRemainderStack;
	} // end getInputRemainder()

	@Deprecated
	public ItemStack getDieRemainder()
	{
		return this.m_dieRemainderStack;
	} // end getDieRemainder()

	public int getCurrentPerTick()
	{
		return this.m_currentPerTick;
	} // end getCurrentPerTick()

	public int getTimeInTicks()
	{
		return this.m_timeInTicks;
	} // end getTimeInTicks()



//	public void tryPrintTags()
//	{
//		YetAnotherTechMod.LOGGER.info("printing for recipe: " + this.m_identifier);
//		if(this.m_inputTag != null) 
//		{
//			this.m_inputTag.forEach((i) -> YetAnotherTechMod.LOGGER.info("input tag has an item: " + i));
//		}
//		if(this.m_dieTag != null) 
//		{
//			this.m_dieTag.forEach((i) -> YetAnotherTechMod.LOGGER.info("die tag has an item: " + i));
//		}
//	} // end tryPrintTags()
//	


	public boolean canBeUsedOn(IItemHandler inventory)
	{
		return this.isStackValidForRecipeInput(inventory.getStackInSlot(ExtruderBlockEntity.INPUT_SLOT)) && this.isStackValidForRecipeDie(inventory.getStackInSlot(ExtruderBlockEntity.DIE_SLOT)) && inventory.insertItem(ExtruderBlockEntity.RESULT_SLOT, this.m_result.copy(), true).isEmpty() && inventory.insertItem(ExtruderBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainderStack.copy(), true).isEmpty();
	} // end canBeUsedOn()

	private boolean isStackValidForRecipeInput(ItemStack itemStack)
	{
		if (this.m_inputTag != null)
		{
			return this.m_inputTag.contains(itemStack.getItem()) && itemStack.getCount() >= this.m_inputCount;
		}
		else if (this.m_inputStack != null)
		{
			return itemStackGreaterThanOrEquals(itemStack, this.m_inputStack);
		}
		return false;
	} // end isStackValidForRecipeInput()

	private boolean isStackValidForRecipeDie(ItemStack itemStack)
	{
		if (this.m_dieTag != null)
		{
			return this.m_dieTag.contains(itemStack.getItem());
		}
		else if (this.m_dieStack != null)
		{
			return itemStackGreaterThanOrEquals(itemStack, this.m_dieStack);
		}
		return false;
	} // end isStackValidForRecipeDie()

	private static boolean itemStackGreaterThanOrEquals(ItemStack a, ItemStack b)
	{
		return a.getItem() == b.getItem() && a.getCount() >= b.getCount();
	} // end itemStackGreaterThanOrEquals()



	public void startRecipe(IItemHandler inventory)
	{
		inventory.extractItem(ExtruderBlockEntity.INPUT_SLOT, this.m_inputCount, false);
	} // end startRecipe()

	public void setResults(IItemHandler inventory)
	{
		if (this.m_dieRemainderStack != null)
		{
			inventory.extractItem(ExtruderBlockEntity.DIE_SLOT, inventory.getSlotLimit(ExtruderBlockEntity.DIE_SLOT), false);
			inventory.insertItem(ExtruderBlockEntity.DIE_SLOT, this.m_dieRemainderStack.copy(), false);
		}
		inventory.insertItem(ExtruderBlockEntity.RESULT_SLOT, this.m_result.copy(), false);

		inventory.insertItem(ExtruderBlockEntity.INPUT_REMAINDER_SLOT, this.m_inputRemainderStack.copy(), false);
	} // end setResults()



	@Override
	public boolean matches(Container container, Level level)
	{
		// should this consider if it's blocked by some result?
		// SmeltingRecipe s;
		// i deduce now
		return this.isStackValidForRecipeInput(container.getItem(ExtruderBlockEntity.INPUT_SLOT)) && this.isStackValidForRecipeDie(container.getItem(ExtruderBlockEntity.DIE_SLOT));
	} // end matches()

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess)
	{
		// would've thought you modify the whole inventory with this, SmeltingRecipe
		// indicates not to though, will do elsewhere
		return this.m_result.copy();
	} // end assemble()

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		// something about the recipe book
		// TODO, understand this better, currently basically guessing at it
		return true;
	} // end canCraftInDimensions()

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess)
	{
		// expecting this not to be modified anyhow
		return this.m_result;
	} // end getResultItem

	@Override
	public ResourceLocation getId()
	{
		return this.m_identifier;
	} // end getId()

	@Override
	public RecipeSerializer<ExtrusionRecipe> getSerializer()
	{
		return YATMRecipeSerializers.EXTRUSION_SERIALIZER.get();
	} // end getSerializer()

	@Override
	public RecipeType<ExtrusionRecipe> getType()
	{
		return YATMRecipeTypes.EXTRUSION_RECIPE_TYPE.get();
	} // end getType()



	@Override
	public ItemStack getToastSymbol()
	{
		return new ItemStack(YATMItems.STEEL_EXTRUDER_ITEM.get());
	} // end getToastSymbol()



	@Override
	public NonNullList<ItemStack> getRemainingItems(Container p_44004_)
	{
		// TODO Auto-generated method stub
		return Recipe.super.getRemainingItems(p_44004_);
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		// TODO Auto-generated method stub
		return Recipe.super.getIngredients();
//				NonNullList.create().of(
//				this.m_inputStack == null ? this.m_inputTag.getRandomElement(null) : this.m_inputStack
//						, null)//
	}

	// TODO, figure this out
	@Override
	public String getGroup()
	{

		return Recipe.super.getGroup();
	}



	// BUILDER BUILDER BUILDER \\
	public static class Builder
	{
		private ExtrusionRecipe m_building;



		public Builder(ResourceLocation identifier, ItemStack result) // ItemStack input, ItemStack die,
		{
			this.m_building = new ExtrusionRecipe(identifier, result); // input, die,
		} // end constructor



		public Builder input(ITag<Item> itemTag, ItemStack itemStack, int count)
		{
			if (itemTag != null)
			{
				this.m_building.m_inputTag = itemTag;
				this.m_building.m_inputStack = null;
				this.m_building.m_inputCount = Math.min(count, 1);
			}
			else if (itemStack != null)
			{
				this.m_building.m_inputTag = null;
				this.m_building.m_inputStack = itemStack;
				this.m_building.m_inputCount = Math.min(count, 1);
			}
			return this;
		} // end input()

		public Builder die(ITag<Item> itemTag, ItemStack itemStack)
		{
			if (itemTag != null)
			{
				this.m_building.m_dieTag = itemTag;
				this.m_building.m_dieStack = null;
			}
			else if (itemStack != null)
			{
				this.m_building.m_dieTag = null;
				this.m_building.m_dieStack = itemStack;
			}
			return this;
		} // end die()



		public Builder inputRemainder(ItemStack itemStack)
		{
			this.m_building.m_inputRemainderStack = itemStack.copy();
			return this;
		} // end inputRemainder()

		public Builder dieRemainder(ItemStack itemStack)
		{
			this.m_building.m_dieRemainderStack = itemStack.copy();
			return this;
		} // end dieRemainder()

		public Builder currentPerTick(int currentPerTick)
		{
			this.m_building.m_currentPerTick = currentPerTick;
			return this;
		} // end currentPerTick()

		public Builder timeInTicks(int timeInTicks)
		{
			this.m_building.m_timeInTicks = timeInTicks;
			return this;
		} // end currentPerTick()



		public ExtrusionRecipe build()// throws InvalidObjectException
		{
			ExtrusionRecipe temp = this.m_building;
			if (temp.m_inputStack == null && temp.m_inputTag == null)
			{
				// throw new InvalidObjectException("an input must be specified to create the
				// recipe, either as a tag or an itemstack");
			}
			if (temp.m_dieStack == null && temp.m_dieTag == null)
			{
				// throw new InvalidObjectException("a die must be specified to create the
				// recipe, either as a tag or an itemstack");
			}
			this.copyFromObject(temp);
			return temp;
		} // end build()

		private void copyFromObject(ExtrusionRecipe r)
		{
			this.m_building = new ExtrusionRecipe(r.m_identifier, r.m_result);

			this.m_building.m_inputTag = r.m_inputTag;
			this.m_building.m_inputStack = r.m_inputStack;
			this.m_building.m_inputCount = r.m_inputCount;
			this.m_building.m_dieTag = r.m_dieTag;
			this.m_building.m_dieStack = r.m_dieStack;

			this.m_building.m_inputRemainderStack = r.m_inputRemainderStack;
			this.m_building.m_dieRemainderStack = r.m_dieRemainderStack;
			this.m_building.m_currentPerTick = r.m_currentPerTick;
			this.m_building.m_timeInTicks = r.m_timeInTicks;
		} // end copyFromObject()


	} // end inner class

} // end outer class