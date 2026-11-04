package bradleywoodrs.additives.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityFallingBlock;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;

import java.util.List;
import java.util.Random;

public class BlockLogicScaffolding extends BlockLogic {
	public BlockLogicScaffolding(@NotNull Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public boolean isClimbable(World world, TilePosc pos) {
		return true;
	}

	@Override
	public boolean collidesWithEntity(Entity entity, World world, TilePosc pos) {
		return !entity.isSneaking() && entity.bb.minY > pos.y() + 1 - 1.0E-5;
	}

	@Override
	public AABBdc getCollisionAABB(WorldSource world, TilePosc pos) {
		return null;
	}

	@Override
	public void getCollisionAABBs(World world, TilePosc pos, AABBdc box, List<AABBdc> list) {
		addIntersectingBoundingBox(box, super.getCollisionAABB(world, pos), list);
	}

	@Override
	public int getPlacedData(Player player, ItemStack stack, World world, TilePosc pos, Side side, double xHit, double yHit) {
		return distance(world, pos);
	}

	@Override
	public int tickDelay() {
		return 1;
	}

	@Override
	public void onPlacedByWorld(World world, TilePosc pos) {
		world.scheduleBlockUpdate(pos, block, tickDelay());
	}

	@Override
	public void onNeighborChanged(World world, TilePosc pos, Block<?> changed) {
		world.scheduleBlockUpdate(pos, block, tickDelay());
	}

	@Override
	public void updateTick(World world, TilePosc pos, Random random, boolean scheduled) {
		int data = world.getBlockData(pos);
		int distance = distance(world, pos);
		if (distance >= 7 && data == 7) {
			world.setBlockTypeNotify(pos, Blocks.AIR);
			EntityFallingBlock falling = new EntityFallingBlock(world, pos.x() + 0.5, pos.y() + 0.5, pos.z() + 0.5, block.id(), 0, null);
			falling.hasRemovedBlock = true;
			world.entityJoinedWorld(falling);
		} else if (distance >= 7) {
			world.playBlockEvent(pos, LevelListener.EVENT_BLOCK_BREAK, block.id());
			dropWithCause(world, EnumDropCause.WORLD, pos, data, null, null);
			world.setBlockTypeNotify(pos, Blocks.AIR);
		} else if (distance != data) {
			world.setBlockDataNotify(pos, distance);
		}
	}

	@Override
	public boolean onInteracted(World world, TilePosc pos, Player player, Side side, double xHit, double yHit) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack == null || stack.getItem() != asItem() || !(stack.getItem() instanceof ItemBlock<?> item)) {
			return false;
		}
		Direction direction = clickedTop(pos, player, side) ? player.getHorizontalPlacementDirection(Side.TOP) : Direction.UP;
		TilePos target = new TilePos(pos.x(), pos.y(), pos.z());
		int previous = 0;
		for (int steps = 0; world.getBlockType(target) == block; steps++) {
			if (direction != Direction.UP && (steps == 7 || previous == 7 - 1)) {
				return true;
			}
			previous = world.getBlockData(target);
			target.add(direction, target);
		}
		if (world.getEntitiesWithinAABB(EntityFallingBlock.class, new AABBd(target.x, target.y, target.z, target.x + 1, target.y + 1, target.z + 1)).isEmpty()) {
			item.placeWithoutShift(stack, world, player, target, side, xHit, yHit);
		}
		return true;
	}

	private boolean clickedTop(TilePosc pos, Player player, Side side) {
		Vector3dc eye = player.getPosition(1.0F, true);
		boolean inside = MathHelper.floor(eye.x()) == pos.x() && MathHelper.floor(eye.y()) == pos.y() && MathHelper.floor(eye.z()) == pos.z();
		if (!inside) {
			return side == Side.TOP;
		}
		Vector3dc view = player.getViewVector(1.0F);
		return -view.y() > Math.abs(view.x()) && -view.y() > Math.abs(view.z());
	}

	private int distance(World world, TilePosc pos) {
		TilePos p = new TilePos();
		int distance = 7;
		pos.add(Direction.DOWN, p);
		Block<?> below = world.getBlockType(p);
		if (below == block) {
			distance = world.getBlockData(p);
		} else if (below != Blocks.AIR && !below.hasTag(BlockTags.PLACE_OVERWRITES)) {
			return 0;
		}
		for (Direction direction : Direction.horizontal) {
			pos.add(direction, p);
			if (world.getBlockType(p) == block) {
				distance = Math.min(distance, world.getBlockData(p) + 1);
			}
		}
		return distance;
	}
}
