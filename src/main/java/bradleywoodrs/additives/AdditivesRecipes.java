package bradleywoodrs.additives;

import bradleywoodrs.additives.items.AdditivesItems;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.helper.RecipeBuilder;

import static bradleywoodrs.additives.Additives.MOD_ID;

public final class AdditivesRecipes {
	private AdditivesRecipes() {}

	public static void initNamespace() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}

	public static void register() {
		if (AdditivesConfig.CFG.getBoolean("Additions.hayBedsEnabled")) {
		}
	}
}
