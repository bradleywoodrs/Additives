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

	public static void register() {
	}

	private static String itemKey(String name) {
		return MOD_ID + ":item/" + name;
	}
}
