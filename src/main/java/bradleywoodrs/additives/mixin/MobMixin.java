package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.blocks.AdditivesBlocks;
import bradleywoodrs.additives.blocks.BlockLogicScaffolding;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin {
	@ModifyExpressionValue(method = "moveEntityWithHeading", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isSneaking()Z", ordinal = 2))
	private boolean additives$descendScaffolding(boolean sneaking) {
		Mob self = (Mob) (Object) this;
		return sneaking && !(self.world.getBlock(MathHelper.floor(self.x), MathHelper.floor(self.bb.minY), MathHelper.floor(self.z)).getLogic() instanceof BlockLogicScaffolding);
	}

	@ModifyReturnValue(method = "canClimb", at = @At("RETURN"))
	private boolean additives$canClimbScaffolding(boolean canClimb) {
		Mob self = (Mob) (Object) this;
		return canClimb && (self instanceof Player || !(self.world.getBlock(MathHelper.floor(self.x), MathHelper.floor(self.bb.minY), MathHelper.floor(self.z)).getLogic() instanceof BlockLogicScaffolding));
	}
}
