package bradleywoodrs.additives.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicBed;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.useless.dragonfly.models.block.StaticBlockModel;

@Environment(EnvType.CLIENT)
public class BlockModelHayBed<T extends BlockLogic> extends BlockModelGeneric<T> {
	@NotNull
	public final StaticBlockModel head = BlockModelDispatcher.loadDataModel("additives:block/bed_hay/head").asModel();

	public BlockModelHayBed(Block<T> block) {
		super(block, BlockModelDispatcher.loadDataModel("additives:block/bed_hay/foot"));
	}

	@Override
	public boolean renderAttached(
		@NotNull TessellatorGeneral tessellator,
		@NotNull WorldSource worldSource,
		@NotNull TilePosc tilePos,
		boolean cullFaces,
		@Nullable IconCoordinate overrideTexture
	) {
		int direction = BlockLogicBed.DIRECTION.get(worldSource.getBlockData(tilePos));
		int rotation = switch (direction) {
			case 0 -> 3;
			case 1 -> 2;
			case 2 -> 1;
			default -> 0;
		};
		return this.getModel(worldSource, tilePos)
			.renderAttached(this, tessellator, worldSource, tilePos, 0, rotation, 0, 0.0, 0.0, 0.0, false, cullFaces, overrideTexture);
	}

	@NotNull
	@Override
	public StaticBlockModel getModelFromData(int data) {
		return BlockLogicBed.IS_HEAD.bool(data) ? this.head : this.staticModel;
	}
}
