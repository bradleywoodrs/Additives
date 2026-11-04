package bradleywoodrs.additives;

import bradleywoodrs.additives.blocks.AdditivesBlocks;
import bradleywoodrs.additives.blocks.BlockModelHayBed;
import bradleywoodrs.additives.items.AdditivesItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.model.BlockModelTransparent;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.item.model.ItemModelStandard;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.dependency.Key;

import static bradleywoodrs.additives.Additives.MOD_ID;
import static net.minecraft.client.render.block.model.BlockModelDispatcher.loadDataModel;

public class AdditivesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Key key = Key.of(MOD_ID);

		ClientEvents.BLOCK_MODEL_RELOAD.listen(key, dispatcher -> {
			dispatcher.addDispatch(new BlockModelHayBed<>(AdditivesBlocks.HAY_BED));
			dispatcher.addDispatch(new BlockModelTransparent<>(AdditivesBlocks.WOODEN_SCAFFOLDING, false).setAllTextures(MOD_ID + ":block/wooden_scaffolding"));
			});


		ClientEvents.ITEM_MODEL_RELOAD.listen(key, dispatcher ->
			dispatcher.addDispatch(new ItemModelStandard(AdditivesItems.HAY_BED))
		);
	}
}
