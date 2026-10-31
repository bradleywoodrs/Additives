package bradleywoodrs.additives;

import bradleywoodrs.additives.blocks.AdditivesBlocks;
import bradleywoodrs.additives.blocks.BlockModelHayBed;
import bradleywoodrs.additives.items.AdditivesItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.model.ItemModelStandard;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.dependency.Key;

import static bradleywoodrs.additives.Additives.MOD_ID;

public class AdditivesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Key key = Key.of(MOD_ID);

		ClientEvents.BLOCK_MODEL_RELOAD.listen(key, dispatcher ->
			dispatcher.addDispatch(new BlockModelHayBed<>(AdditivesBlocks.HAY_BED))
		);

		ClientEvents.ITEM_MODEL_RELOAD.listen(key, dispatcher ->
			dispatcher.addDispatch(new ItemModelStandard(AdditivesItems.HAY_BED))
		);
	}
}
