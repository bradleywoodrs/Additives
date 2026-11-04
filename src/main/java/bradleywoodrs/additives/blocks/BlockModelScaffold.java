package bradleywoodrs.additives.blocks;

import net.minecraft.client.render.block.model.BlockModelTransparent;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBdc;

public class BlockModelScaffold extends BlockModelTransparent {
	public BlockModelScaffold(Block block, boolean renderInside) {
		super(block, renderInside);
	}
	@Override
	public boolean shouldSideBeRendered(WorldSource world, AABBdc bounds, TilePosc neighbor, Side side) {
		Block<?> neighborBlock = world.getBlockType(neighbor);
		return side == Side.TOP && neighborBlock == block || neighborBlock == Blocks.AIR;
	}
}
