package com.gsr.gsr_yatm.block.hanging_pot;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

public class HangingPotHookBlock extends Block implements EntityBlock
{
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	public HangingPotHookBlock(Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(properties);
		this.m_shape = Objects.requireNonNull(shape);
	} // end properties()

	
	
	@SuppressWarnings("deprecation")
	@Override
	public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos position, BlockState state) 
	{
		BlockEntity be = blockGetter.getBlockEntity(position);
		if (be != null && be instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
			if (pot != null)
			{
				return pot.getCloneItemStack(blockGetter, position, state);
			}
		}
		return super.getCloneItemStack(blockGetter, position, state);
	} // end getCloneItemStack
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext collisionContext)
	{
		BlockEntity be = blockGetter.getBlockEntity(position);
		if (be != null && be instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
			if (pot != null)
			{
				return Shapes.or(super.getCollisionShape(state, blockGetter, position, collisionContext), pot.getCollisionShape(state, blockGetter, position, collisionContext));
			}
		}
		return super.getCollisionShape(state, blockGetter, position, collisionContext);
	} // end getCollisionShape()

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext collisionContext)
	{
		BlockEntity be = blockGetter.getBlockEntity(position);
		if (be != null && be instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
			if (pot != null)
			{
				return Shapes.or(this.m_shape.getShape(state, blockGetter, position, collisionContext), pot.getShape(state, blockGetter, position, collisionContext));
			}
		}
		return this.m_shape.getShape(state, blockGetter, position, collisionContext);
	} // end getShape()



	// TODO, look for moving this into an event, so we can do drops only when player's don't have instabuild, to maintain consistency of creative block breaking not dropping and such
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState fromState, Level level, BlockPos position, BlockState toState, boolean p_60519_)
	{
		if (/* level instanceof ServerLevel serverLevel && */ !fromState.is(toState.getBlock()))
		{
			BlockEntity be = level.getBlockEntity(position);
			if (be != null && be instanceof HangingPotHookBlockEntity hphbe)
			{
				
				FlowerPotBlock pot = hphbe.getPot();
				Block.dropResources(pot.defaultBlockState(), level, position);
// TODO, figure out why this was causing apparent desynchronizations and drop loot
//				if (pot != null)
//				{
//					pot.defaultBlockState()
//					.getDrops(new LootParams.Builder(serverLevel))
//					.forEach((i) -> InventoryUtilities.drop(level, position, i));
//				}
			}
		}			
		super.onRemove(fromState, level, position, toState, p_60519_);
	} // end onRemove()

	
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		BlockEntity be = level.getBlockEntity(position);
		if (be != null && be instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
			ItemStack held = player.getItemInHand(hand);
			Block heldBlock = held.getItem() instanceof BlockItem bi ? bi.getBlock() : null;

			// set pot on to hook
			if (heldBlock instanceof FlowerPotBlock fpb && pot == null)
			{
				hphbe.setPot(fpb);
				level.playSound(player, position, SoundEvents.CHAIN_PLACE, SoundSource.BLOCKS);
				if (!player.getAbilities().instabuild)
				{
					held.shrink(1);
				}
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			// set plant into the pot, or remove plant from pot, or remove pot
			else if (pot != null)
			{
				Block toPlaceMaybe = pot.getEmptyPot().getFullPotsView().getOrDefault(ForgeRegistries.BLOCKS.getKey(heldBlock), ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.AIR)).get();
				boolean toPlaceIsAir = toPlaceMaybe == Blocks.AIR;
				ItemStack potContent = new ItemStack(pot.getContent());
				boolean potIsEmpty = potContent.isEmpty();
				// if could fill with held, but's already filled, yield
				if(!toPlaceIsAir && !potIsEmpty) 
				{
					return super.use(state, level, position, player, hand, hitResult);
				}
				// remove pot if not filling with held and it is empty
				else if (toPlaceIsAir && potIsEmpty)
				{
					ItemStack toDrop = new ItemStack(pot);
					if (held.isEmpty())
					{
						player.setItemInHand(hand, toDrop);
					}
					else if (!player.addItem(toDrop))
					{
						InventoryUtil.drop(level, position, toDrop);
					}
					hphbe.setPot(null);
					level.playSound(player, position, SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS);
					return InteractionResult.sidedSuccess(level.isClientSide);
				}
				else
				{
					// if the pot is empty and can fill with held fill
					if (potIsEmpty)
					{
						if (toPlaceMaybe instanceof FlowerPotBlock fpb)
						{
							hphbe.setPot(fpb);
							player.awardStat(Stats.POT_FLOWER);
							if (!player.getAbilities().instabuild)
							{
								held.shrink(1);
							}
							return InteractionResult.sidedSuccess(level.isClientSide);
						}

					}
					// if pot can't be filled with held take out current plant, or if taking the plant out doesn't yield a different pot, remove the pot
					else
					{
						if (held.isEmpty())
						{
							player.setItemInHand(hand, potContent);
						}
						else if (!player.addItem(potContent))
						{
							InventoryUtil.drop(level, position, potContent);
						}
						FlowerPotBlock emptyPot = pot.getEmptyPot();
						FlowerPotBlock newPot = emptyPot == pot ? null : emptyPot;
						hphbe.setPot(newPot);
						if(newPot == null) 
						{
							level.playSound(player, position, SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS);
						}
						return InteractionResult.sidedSuccess(level.isClientSide);
					}
				}
			}
		}
		return super.use(state, level, position, player, hand, hitResult);
	} // end use()

	

	@Override
	public BlockEntity newBlockEntity(BlockPos position, BlockState state)
	{
		return new HangingPotHookBlockEntity(position, state);
	} // end newBlockEntity()

} // end class