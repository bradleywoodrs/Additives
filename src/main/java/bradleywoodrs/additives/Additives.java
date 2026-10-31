package bradleywoodrs.additives;

import bradleywoodrs.additives.blocks.AdditivesBlocks;
import bradleywoodrs.additives.items.AdditivesItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.CommonEvents;
import turniplabs.halplibe.util.dependency.Key;

public class Additives implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("additives", true);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		AdditivesConfig.CFG.getBoolean("Additions.rightClickHarvestEnabled");
		Key key = Key.of(MOD_ID);
		CommonEvents.BEFORE_GAME_START.listen(key, this::beforeGameStart);
		CommonEvents.AFTER_BLOCK_INIT.listen(key, AdditivesBlocks::register);
		CommonEvents.AFTER_ITEM_INIT.listen(key, AdditivesItems::register);
		CommonEvents.RECIPES_NAMESPACE_INIT.listen(key, AdditivesRecipes::initNamespace);
		CommonEvents.RECIPES_READY.listen(key, AdditivesRecipes::register);
		CommonEvents.AFTER_GAME_START.listen(key, this::afterGameStart);
		LOGGER.info("Additives initialized.");
	}

	public void beforeGameStart() {

	}

	public void afterGameStart() {

	}
}
