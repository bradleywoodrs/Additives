package bradleywoodrs.additives;

import bradleywoodrs.additives.blocks.AdditivesBlocks;
import bradleywoodrs.additives.items.AdditivesItems;
import net.minecraft.core.block.Blocks;
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
			RecipeBuilder.Shaped(MOD_ID, "WWW", "WWW")
				.addInput('W', Items.WHEAT)
				.create("bed_hay", new ItemStack(AdditivesItems.HAY_BED));
		}
		if (AdditivesConfig.CFG.getBoolean("Additions.scaffoldingEnabled")) {
			RecipeBuilder.Shaped(MOD_ID, "PPP", " S ", "S S")
				.addInput('P', Blocks.PLANKS_OAK)
				.addInput('S', Items.STICK)
				.create("wooden_scaffolding", new ItemStack(AdditivesBlocks.WOODEN_SCAFFOLDING));
		}
	}
}
