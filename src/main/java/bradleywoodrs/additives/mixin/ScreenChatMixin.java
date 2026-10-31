package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.AdditivesConfig;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.chat.ScreenChat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ScreenChat.class, remap = false)
public abstract class ScreenChatMixin extends Screen {
	@Override
	public boolean isPauseScreen() {
		return AdditivesConfig.CFG.getBoolean("Additions.chatPausesGame");
	}
}
