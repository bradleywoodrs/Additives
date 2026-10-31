package bradleywoodrs.additives.blocks;

import bradleywoodrs.additives.AdditivesConfig;
import bradleywoodrs.additives.items.AdditivesItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import turniplabs.halplibe.helper.BlockBuilder;

import static bradleywoodrs.additives.Additives.MOD_ID;

public final class AdditivesBlocks {
	private AdditivesBlocks() {}

	public static Block<BlockLogicHayBed> HAY_BED;

	public static void register() {
		HAY_BED = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.GRASS)
			.setHardness(0.2F)
			.setUseInternalLight()
			.setFlammability(60, 100)
			.setStatParent(() -> AdditivesItems.HAY_BED)
			.setTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.MINEABLE_BY_AXE)
			.build("bed.hay", "bed_hay", AdditivesConfig.CFG.getInt("IDs.hayBedBlockID"), BlockLogicHayBed::new);
	}
}
