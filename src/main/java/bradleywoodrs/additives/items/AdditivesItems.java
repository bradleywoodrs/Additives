package bradleywoodrs.additives.items;

import bradleywoodrs.additives.AdditivesConfig;
import bradleywoodrs.additives.blocks.AdditivesBlocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.tag.ItemTags;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;

import static bradleywoodrs.additives.Additives.MOD_ID;

public final class AdditivesItems {
	private AdditivesItems() {}

	public static Item HAY_BED;

	public static void register() {
		if (AdditivesConfig.CFG.getBoolean("Additions.hayBedsEnabled")) {
			HAY_BED = new ItemBuilder(MOD_ID)
				.setStackSize(4)
				.setCreativeInventoryPlacement(new CreativeInventoryPlacement.Category(CreativeInventoryCategory.PLACEABLES))
				.build(new ItemHayBed("bed.hay", itemKey("bed_hay"), AdditivesConfig.CFG.getInt("IDs.hayBedItemID"), AdditivesBlocks.HAY_BED));
		}
	}

	private static String itemKey(String name) {
		return MOD_ID + ":item/" + name;
	}
}
