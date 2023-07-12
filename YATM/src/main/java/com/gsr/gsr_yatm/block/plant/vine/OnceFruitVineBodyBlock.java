package com.gsr.gsr_yatm.block.plant.vine;

import java.util.function.Supplier;

import com.gsr.gsr_yatm.YATMBlockStateProperties;
import com.gsr.gsr_yatm.block.plant.OnceFruitingPlantStages;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class OnceFruitVineBodyBlock extends GrowingPlantBodyBlock
{
	public static final EnumProperty<OnceFruitingPlantStages> FRUITING_STAGE = YATMBlockStateProperties.ONCE_FRUITING_STAGE;

	
	
	private final Supplier<GrowingPlantHeadBlock> m_meristem;
	private final Supplier<ItemStack> m_pickedFruit;
	
	
	public OnceFruitVineBodyBlock(Properties properties, Supplier<GrowingPlantHeadBlock> meristem, Supplier<ItemStack> pickedFruit)
	{
		super(properties, Direction.DOWN, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), false);
		this.m_meristem = meristem;
		
		this.registerDefaultState(this.defaultBlockState().setValue(FRUITING_STAGE, OnceFruitingPlantStages.IMMATURE));
		this.m_pickedFruit = pickedFruit;
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



	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
	{
		if (state.getValue(FRUITING_STAGE) == OnceFruitingPlantStages.FRUITING)
		{
			Block.popResource(level, position, this.m_pickedFruit.get());
			level.playSound((Player) null, position, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, 1.0f);
			level.setBlock(position, state.setValue(FRUITING_STAGE, OnceFruitingPlantStages.HARVESTED), UPDATE_ALL);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	} // end use()
	
} // end class