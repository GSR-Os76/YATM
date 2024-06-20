package com.gsr.gsr_yatm.block.plant.vine;

import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.block.plant.OnceFruitingPlantStages;
import com.gsr.gsr_yatm.utilities.YATMBlockStateProperties;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class OnceFruitingVineBodyBlock extends GrowingPlantBodyBlock
{
	public static final MapCodec<OnceFruitingVineBodyBlock> CODEC = null; // TODO, maybe eventually implement
	public static final EnumProperty<OnceFruitingPlantStages> FRUITING_STAGE = YATMBlockStateProperties.ONCE_FRUITING_STAGE;

	
	
	private final @NotNull Supplier<GrowingPlantHeadBlock> m_meristem;
	private final @NotNull Supplier<ItemStack> m_harvestResult;
	
	
	public OnceFruitingVineBodyBlock(@NotNull Properties properties, @NotNull Supplier<GrowingPlantHeadBlock> meristem, @NotNull Supplier<ItemStack> harvestResult)
	{
		super(Objects.requireNonNull(properties), Direction.DOWN, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), false);
		this.m_meristem = Objects.requireNonNull(meristem);
		this.m_harvestResult = Objects.requireNonNull(harvestResult);

		this.registerDefaultState(this.defaultBlockState().setValue(OnceFruitingVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.IMMATURE));
	} // end constructor

	
	
	@Override
	protected GrowingPlantHeadBlock getHeadBlock()
	{
		return this.m_meristem.get();
	}

	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FRUITING_STAGE));
	} // end createBlockStateDefinition()



	@Override
	public boolean isRandomlyTicking(BlockState state)
	{
		return state.getValue(FRUITING_STAGE) == OnceFruitingPlantStages.IMMATURE;
	} // end isRandomlyTicking

	@Override
	public void randomTick(BlockState startState, ServerLevel level, BlockPos position, RandomSource random)
	{
		if(startState.getValue(FRUITING_STAGE) == OnceFruitingPlantStages.IMMATURE && random.nextInt(22) == 0) 
		{
			level.setBlock(position, startState.setValue(FRUITING_STAGE, OnceFruitingPlantStages.FRUITING), UPDATE_ALL);
		}
	} // end randomTick()



	@Override // TODO, verify both needed, not one is later in the interaction pass chain
	public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack held, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if (state.getValue(OnceFruitingVineBodyBlock.FRUITING_STAGE) == OnceFruitingPlantStages.FRUITING)
		{
			Block.popResource(level, position, this.m_harvestResult.get());
			level.playSound((Player) null, position, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
			level.setBlock(position, state.setValue(OnceFruitingVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.HARVESTED), Block.UPDATE_ALL);
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	} // end useItemOn()
	
	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, BlockHitResult hitResult)
	{
		if (state.getValue(OnceFruitingVineBodyBlock.FRUITING_STAGE) == OnceFruitingPlantStages.FRUITING)
		{
			Block.popResource(level, position, this.m_harvestResult.get());
			level.playSound((Player) null, position, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
			level.setBlock(position, state.setValue(OnceFruitingVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.HARVESTED), Block.UPDATE_ALL);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end useWithoutItem()



	@Override
	protected @NotNull MapCodec<OnceFruitingVineBodyBlock> codec()
	{
	    return OnceFruitingVineBodyBlock.CODEC;
	} // end codec()
	
} // end class

/*
WeightedPressurePlateBlock l;
	public static final MapCodec<OnceFruitingVineBodyBlock> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
	                    propertiesCodec(),
	                    BlockStateProvider.CODEC.fieldOf("meristem").forGetter(i -> i.m_meristem),
	                    ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("harvest_loottable").forGetter(i ->  i.m_harvestLoottable)
	                )
	                .apply(instance, OnceFruitingVineBodyBlock::new)
	    );
	public static final EnumProperty<OnceFruitingPlantStages> FRUITING_STAGE = YATMBlockStateProperties.ONCE_FRUITING_STAGE;

	
	
	private final @NotNull BlockStateProvider m_meristem;
	private final @NotNull ResourceKey<LootTable> m_harvestLoottable;
	
	
	
	public OnceFruitingVineBodyBlock(@NotNull Properties properties, @NotNull BlockStateProvider meristem, @NotNull ResourceKey<LootTable> harvestLoottable)
	{
		super(Objects.requireNonNull(properties), Direction.DOWN, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), false);
		this.m_meristem = Objects.requireNonNull(meristem);
		this.m_harvestLoottable = Objects.requireNonNull(harvestLoottable);

		this.registerDefaultState(this.defaultBlockState().setValue(OnceFruitingVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.IMMATURE));
	} // end constructor

	
	
	@Override
	protected GrowingPlantHeadBlock getHeadBlock()
	{
		return this.m_meristem.get();
	}

	
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder.add(FRUITING_STAGE));
	} // end createBlockStateDefinition()



	@Override
	public boolean isRandomlyTicking(BlockState state)
	{
		return state.getValue(FRUITING_STAGE) == OnceFruitingPlantStages.IMMATURE;
	} // end isRandomlyTicking

	@Override
	public void randomTick(BlockState startState, ServerLevel level, BlockPos position, RandomSource random)
	{
		if(startState.getValue(FRUITING_STAGE) == OnceFruitingPlantStages.IMMATURE && random.nextInt(22) == 0) 
		{
			level.setBlock(position, startState.setValue(FRUITING_STAGE, OnceFruitingPlantStages.FRUITING), UPDATE_ALL);
		}
	} // end randomTick()



	@Override // TODO, verify both needed, not one is later in the interaction pass chain
	public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack held, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if (state.getValue(OnceFruitingVineBodyBlock.FRUITING_STAGE) == OnceFruitingPlantStages.FRUITING)
		{
			Block.popResource(level, position, this.m_pickedFruit.get());
			level.playSound((Player) null, position, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
			level.setBlock(position, state.setValue(OnceFruitingVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.HARVESTED), Block.UPDATE_ALL);
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	} // end useItemOn()
	
	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, BlockHitResult hitResult)
	{
		if (state.getValue(OnceFruitingVineBodyBlock.FRUITING_STAGE) == OnceFruitingPlantStages.FRUITING)
		{
			Block.popResource(level, position, this.m_pickedFruit.get());
			level.playSound((Player) null, position, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
			level.setBlock(position, state.setValue(OnceFruitingVineBodyBlock.FRUITING_STAGE, OnceFruitingPlantStages.HARVESTED), Block.UPDATE_ALL);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end useWithoutItem()



	@Override
	protected @NotNull MapCodec<OnceFruitingVineBodyBlock> codec()
	{
	    return OnceFruitingVineBodyBlock.CODEC;
	} // end codec()
	 
*/