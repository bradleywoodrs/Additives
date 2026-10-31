package bradleywoodrs.additives;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static bradleywoodrs.additives.Additives.MOD_ID;

public class AdditivesConfig {
	private static final Toml TOML = new Toml("Additives TOML Config");
	public static final TomlConfigHandler CFG;


	static {
		TOML.addCategory("Fixes")
			.addEntry("mouseInputFix", "Default: true", true);
		TOML.addCategory("Additions")
			.addEntry("rightClickHarvestEnabled", "Default: true", true)
			.addEntry("rightClickHarvestHoeRequired", "Default: true", true)
			.addEntry("chatPausesGame", "Default: false", false)
			.addEntry("placeLaddersOnSlabs", "Default: true", true)
		CFG = new TomlConfigHandler(MOD_ID, TOML);
	}
}
