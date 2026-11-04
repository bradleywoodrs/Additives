package bradleywoodrs.additives.blocks;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Materials;
import org.jetbrains.annotations.NotNull;

public class BlockLogicScaffolding extends BlockLogic {
	public BlockLogicScaffolding(@NotNull Block<?> block) {
		super(block, Materials.WOOD);
	}
}
