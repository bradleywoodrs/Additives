package bradleywoodrs.additives.items;

import bradleywoodrs.additives.AdditivesConfig;
import bradleywoodrs.additives.blocks.AdditivesBlocks;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.tag.ItemTags;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;

import static bradleywoodrs.additives.Additives.MOD_ID;

public final class AdditivesItems {
	private AdditivesItems() {}

	public static Item HAY_BED;

	public static void register() {
		ItemBuilder hayBed = new ItemBuilder(MOD_ID).setStackSize(4);
		if (AdditivesConfig.CFG.getBoolean("Additions.hayBedsEnabled")) {
			hayBed = hayBed.setCreativeInventoryPlacement(new CreativeInventoryPlacement.After(() -> Items.BED));
		} else {
			hayBed = hayBed.addTags(ItemTags.NOT_IN_CREATIVE_MENU);
		}
		HAY_BED = hayBed.build(new ItemHayBed("bed.hay", itemKey("bed_hay"), AdditivesConfig.CFG.getInt("IDs.hayBedItemID"), AdditivesBlocks.HAY_BED));
	}

	private static String itemKey(String name) {
		return MOD_ID + ":item/" + name;
	}
}
