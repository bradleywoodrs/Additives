package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.AdditivesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.MouseInput;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MouseInput.class, remap = false)
public abstract class MouseInputMixin {
	@Shadow @Final public Minecraft minecraft;
	@Shadow public double deltaX;
	@Shadow public double deltaY;

	@Unique
	private boolean ignoreNextDelta;

	@Unique
	private static boolean isEnabled() {
		return AdditivesConfig.CFG.getBoolean("Fixes.mouseInputFix");
	}

	@Unique
	private void centerCursor() {
		Mouse.setCursorPosition(this.minecraft.resolution.getWidthScreenCoords() / 2, this.minecraft.resolution.getHeightScreenCoords() / 2);
	}

	@Inject(method = "grabCursor", at = @At("HEAD"))
	private void centerBeforeGrab(CallbackInfo ci) {
		if (isEnabled() && !Mouse.isGrabbed()) {
			centerCursor();
		}
	}

	@Inject(method = "grabCursor", at = @At("TAIL"))
	private void discardDeltaAfterGrab(CallbackInfo ci) {
		if (!isEnabled()) {
			return;
		}
		this.deltaX = 0;
		this.deltaY = 0;
		this.ignoreNextDelta = true;
	}

	@Inject(method = "releaseCursor", at = @At("HEAD"))
	private void centerBeforeRelease(CallbackInfo ci) {
		if (isEnabled() && Mouse.isGrabbed()) {
			centerCursor();
		}
	}

	@Inject(method = "update", at = @At("HEAD"), cancellable = true)
	private void ignoreFirstMove(CallbackInfo ci) {
		if (this.ignoreNextDelta) {
			this.ignoreNextDelta = false;
			Mouse.getDX();
			Mouse.getDY();
			this.deltaX = 0;
			this.deltaY = 0;
			ci.cancel();
		}
	}
}
