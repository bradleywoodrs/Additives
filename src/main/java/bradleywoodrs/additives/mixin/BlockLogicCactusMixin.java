package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.rightclickharvest.RightClickHarvest;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicCactus;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockLogicCactus.class, remap = false)
public abstract class BlockLogicCactusMixin extends BlockLogic {
	public BlockLogicCactusMixin(Block<?> block, Material material) {
		super(block, material);
	}

	@Override
	public boolean onInteracted(World world, TilePosc pos, Player player, Side side, double xHit, double yHit) {
		if (RightClickHarvest.canHarvest(player) && RightClickHarvest.harvestColumn(world, pos, Blocks.CACTUS)) {
			RightClickHarvest.damageHoe(player);
			return true;
		}

		return super.onInteracted(world, pos, player, side, xHit, yHit);
	}
}
