package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.rightclickharvest.RightClickHarvest;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicCropsWheat;
import net.minecraft.core.block.BlockLogicFlower;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockLogicCropsWheat.class, remap = false)
public abstract class BlockLogicCropsWheatMixin extends BlockLogicFlower {
	public BlockLogicCropsWheatMixin(Block<?> block) {
		super(block);
	}

	@Override
	public boolean onInteracted(World world, TilePosc pos, Player player, Side side, double xHit, double yHit) {
		int data = world.getBlockData(pos);
		if (!RightClickHarvest.canHarvest(player)
			|| (data & BlockLogicCropsWheat.MASK_GROWTH) < BlockLogicCropsWheat.MAX_GROWTH_STATE) {
			return super.onInteracted(world, pos, player, side, xHit, yHit);
		}

		if (!world.isClientSide) {
			RightClickHarvest.dropBreakResult(world, pos, Blocks.CROPS_WHEAT, data);
			world.setBlockDataNotify(pos, data & ~BlockLogicCropsWheat.MASK_GROWTH);
		}
		RightClickHarvest.damageHoe(player);
		return true;
	}
}
