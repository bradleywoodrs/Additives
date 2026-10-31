package bradleywoodrs.additives;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static bradleywoodrs.additives.Additives.MOD_ID;

public class AdditivesConfig {
	private static final Toml TOML = new Toml("Additives TOML Config");
	public static final TomlConfigHandler CFG;


	static {
		TOML.addCategory("RightClickHarvest")
			.addEntry("enabled", "Default: true", true)
			.addEntry("hoeRequired", "Default: true", true);
		TOML.addCategory("Fixes")
			.addEntry("mouseInputFix", "Default: true", true);
		TOML.addCategory("Chat")
			.addEntry("pauseGame", "Default: false", false);
		CFG = new TomlConfigHandler(MOD_ID, TOML);
	}
}
