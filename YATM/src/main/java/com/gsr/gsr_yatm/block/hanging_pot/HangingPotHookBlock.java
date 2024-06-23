package com.gsr.gsr_yatm.block.hanging_pot;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.gsr.gsr_yatm.utilities.BlockUtilities;
import com.gsr.gsr_yatm.utilities.InventoryUtil;
import com.gsr.gsr_yatm.utilities.shape.ICollisionVoxelShapeProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

public class HangingPotHookBlock extends Block implements EntityBlock
{
	private final @NotNull ICollisionVoxelShapeProvider m_shape;
	
	public HangingPotHookBlock(@NotNull Properties properties, @NotNull ICollisionVoxelShapeProvider shape)
	{
		super(Objects.requireNonNull(properties));
		this.m_shape = Objects.requireNonNull(shape);
	} // end properties()

	
	
	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult hitResult, @NotNull LevelReader level, @NotNull BlockPos position, Player player) 
	{
		if (level.getBlockEntity(position) instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
			if (pot != null)
			{
				return pot.getCloneItemStack(level, position, state);
			}
		}
		return super.getCloneItemStack(state, hitResult, level, position, player);
	} // end getCloneItemStack()
	
	
	
	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos position)
	{
		return Block.canSupportCenter(level, position.above(), Direction.DOWN);
	} // end canSurvive()
	
	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, @NotNull Block formerNeighbor, @NotNull BlockPos neighborPos, boolean p_60514_)
	{
		if(!this.canSurvive(state, level, position)) 
		{
			BlockUtilities.breakBlock(level, state, position);
		}
		super.neighborChanged(state, level, position, formerNeighbor, neighborPos, p_60514_);
	} // end neighborChanged()
	
	
	
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



	@Override
	public void onRemove(BlockState fromState, Level level, BlockPos position, BlockState toState, boolean p_60519_)
	{
		if (!fromState.is(toState.getBlock()))
		{
			BlockEntity be = level.getBlockEntity(position);
			if (be != null && be instanceof HangingPotHookBlockEntity hphbe)
			{
				
				FlowerPotBlock pot = hphbe.getPot();
				if(pot != null) 
				{
					Block.dropResources(pot.defaultBlockState(), level, position);
				}
			}
		}			
		super.onRemove(fromState, level, position, toState, p_60519_);
	} // end onRemove()

	
	
	
	
	@Override
	public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack held, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if (level.getBlockEntity(position) instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
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
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
			// set plant into the pot, or remove plant from pot, or remove pot
			else if (pot != null)
			{
				Block toPlaceMaybe/*fullPotToPlace*/ = pot.getEmptyPot().getFullPotsView().getOrDefault(ForgeRegistries.BLOCKS.getKey(heldBlock), () -> (Block)null).get();
				boolean nothingToPlace = toPlaceMaybe == null;
				ItemStack potContent = new ItemStack(pot.getPotted());
				boolean potIsEmpty = potContent.isEmpty();
				// if could fill with held, but's already filled, yield
				if(!nothingToPlace && !potIsEmpty) 
				{
					return super.useItemOn(held, state, level, position, player, hand, hitResult);
				}
				// remove pot if not filling with held and it is empty
				else if (nothingToPlace && potIsEmpty) // TODO, theoretically unattainable route
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
					return ItemInteractionResult.sidedSuccess(level.isClientSide);
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
							return ItemInteractionResult.sidedSuccess(level.isClientSide);
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
						return ItemInteractionResult.sidedSuccess(level.isClientSide);
					}
				}
			}
		}
		return super.useItemOn(held, state, level, position, player, hand, hitResult);
	} // end useItemOn()

	@Override
	protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos position, Player player, BlockHitResult hitResult)
	{
		if (level.getBlockEntity(position) instanceof HangingPotHookBlockEntity hphbe)
		{
			FlowerPotBlock pot = hphbe.getPot();
			
			// if pot on the hook: remove plant from the pot, or remove pot
			if (pot != null)
			{
				ItemStack potContent = new ItemStack(pot.getPotted());

				ItemStack toDrop;
				// remove pot if it's empty
				if (potContent.isEmpty())
				{
					toDrop = new ItemStack(pot);
					hphbe.setPot(null);
				}
				// remove pot content is it's not empty, or if it doesn't have an empty pot state, remove the pot
				else
				{
					
					FlowerPotBlock emptyPot = pot.getEmptyPot();
					toDrop = emptyPot == pot  ? new ItemStack(pot) : potContent;
					FlowerPotBlock newPot = emptyPot == pot ? null : emptyPot;
					hphbe.setPot(newPot);
				}
//				if (held.isEmpty())
//				{
//					player.setItemInHand(hand, potContent);
//				}
				/*else*/ if (!player.addItem(toDrop))
				{
					InventoryUtil.drop(level, position, toDrop);
				}
				
				// if pot removed play the dechaining sound
				if(hphbe.getPot() == null) 
				{
					level.playSound(player, position, SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS);
				}
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}
		return super.useWithoutItem(state, level, position, player, hitResult);
	} // end useWithoutItem()

	

	

	@Override
	public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state)
	{
		return new HangingPotHookBlockEntity(position, state);
	} // end newBlockEntity()

} // end class