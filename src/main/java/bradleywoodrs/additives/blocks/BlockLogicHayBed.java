package bradleywoodrs.additives.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicBed;
import bradleywoodrs.additives.items.AdditivesItems;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockLogicHayBed extends BlockLogicBed {
	public static final double HEIGHT = 4.0 / 16.0;

	public BlockLogicHayBed(@NotNull Block<?> block) {
		super(block);
		this.setBlockBounds(0.0, 0.0, 0.0, 1.0, HEIGHT, 1.0);
	}

	public static boolean isHayBed(@NotNull World world, int x, int y, int z) {
		Block<?> block = world.getBlockType(new TilePos(x, y, z));
		return block != null && block.getLogic() instanceof BlockLogicHayBed;
	}

	@Override
	public String getLanguageKey(int data) {
		return AdditivesItems.HAY_BED.getKey();
	}

	@Override
	public ItemStack[] getBreakResult(@NotNull World world, @NotNull EnumDropCause dropCause, int data, @Nullable TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(AdditivesItems.HAY_BED)};
	}

	@Nullable
	private TilePos pairedHalf(@NotNull World world, @NotNull TilePosc tilePos, int data) {
		TilePos other = otherHalf(world, tilePos, data);
		return other != null && world.getBlockType(other) == this.block ? other : null;
	}

	@Override
	public void onNeighborChanged(@NotNull World world, @NotNull TilePosc tilePos, @NotNull Block<?> block) {
		if (pairedHalf(world, tilePos, world.getBlockData(tilePos)) == null) {			world.setBlockTypeNotify(tilePos, Blocks.AIR);
		}
	}

	@Override
	public void onRemoved(@NotNull World world, @NotNull TilePosc tilePos, int data) {
		TilePos other = pairedHalf(world, tilePos, data);		if (other != null) {
			world.setBlockTypeNotify(other, Blocks.AIR);
		}
	}
}
