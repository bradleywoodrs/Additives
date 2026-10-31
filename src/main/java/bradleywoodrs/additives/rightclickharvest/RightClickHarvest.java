package bradleywoodrs.additives.rightclickharvest;

import bradleywoodrs.additives.AdditivesConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolHoe;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

public class RightClickHarvest {
	public static boolean canHarvest(Player player) {
		if (!AdditivesConfig.CFG.getBoolean("Additions.rightClickHarvestEnabled")) {
			return false;
		}

		if (AdditivesConfig.CFG.getBoolean("Additions.rightClickHarvestHoeRequired")) {
			ItemStack held = player.getHeldItem();
			return held != null && held.getItem() instanceof ItemToolHoe;
		}

		return true;
	}

	public static void damageHoe(Player player) {
		if (!AdditivesConfig.CFG.getBoolean("Additions.rightClickHarvestHoeRequired")) {
			return;
		}

		ItemStack held = player.getHeldItem();
		if (held != null && held.getItem() instanceof ItemToolHoe) {
			held.damageItem(1, player);
		}
	}

	public static void dropBreakResult(World world, TilePosc pos, Block<?> block, int data) {
		ItemStack[] drops = block.getLogic().getBreakResult(world, EnumDropCause.PROPER_TOOL, data, null);
		if (drops == null) {
			return;
		}
		for (ItemStack stack : drops) {
			if (stack != null) {
				world.dropItem(pos, stack);
			}
		}
	}

	public static boolean harvestColumn(World world, TilePosc pos, Block<?> block) {
		TilePos bottom = new TilePos(pos);
		while (world.getBlockType(new TilePos(bottom.x, bottom.y - 1, bottom.z)) == block) {
			bottom.y--;
		}

		TilePos top = new TilePos(pos);
		while (world.getBlockType(new TilePos(top.x, top.y + 1, top.z)) == block) {
			top.y++;
		}

		if (top.y == bottom.y) {
			return false;
		}

		if (!world.isClientSide) {
			for (int y = top.y; y > bottom.y; y--) {
				TilePos p = new TilePos(bottom.x, y, bottom.z);
				dropBreakResult(world, p, block, world.getBlockData(p));
				world.setBlockTypeNotify(p, Blocks.AIR);
			}
		}
		return true;
	}
}
