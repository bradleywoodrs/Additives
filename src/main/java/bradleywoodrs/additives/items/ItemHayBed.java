package bradleywoodrs.additives.items;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicBed;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IPlaceable;
import net.minecraft.core.item.ItemBed;
import net.minecraft.core.item.ItemPlaceablePair;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemHayBed extends ItemPlaceablePair {
	public ItemHayBed(@NotNull String name, @NotNull String namespaceId, int id, @NotNull Block<?> block) {
		super(name, namespaceId, id, block, block);
	}

	@NotNull
	@Override
	protected PlacementResult getPlacement(
		@NotNull ItemStack selfStack,
		@NotNull World world,
		@Nullable Player player,
		@NotNull TilePosc blockPos,
		@NotNull Side side,
		double xHit,
		double yHit,
		@NotNull PlacementResult result
	) {
		Direction dir = ItemBed.getDirection(player, side);
		result.dataA = result.dataB = dir.legacyIndex();
		result.dataB = BlockLogicBed.IS_HEAD.set(result.dataB, 1);
		dir.getOffset(result.offsetB);
		result.offsetB.mul(-1);		return result;
	}

	@Override
	public boolean canPlaceDirectlyAtPosition(
		@NotNull ItemStack selfStack, @NotNull World world, @Nullable Player player, @NotNull TilePosc blockPos, @NotNull Side side, double xHit, double yHit
	) {
		Direction dir = ItemBed.getDirection(player, side);
		return IPlaceable.canPlaceBlockDirectlyAtPosition(this.blockA, world, blockPos, side)
			&& IPlaceable.canPlaceBlockDirectlyAtPosition(this.blockB, world, blockPos.sub(dir, new TilePos()), side);
	}
}
