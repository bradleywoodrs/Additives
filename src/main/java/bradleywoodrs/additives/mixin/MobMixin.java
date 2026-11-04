package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.blocks.AdditivesBlocks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin {
	@ModifyExpressionValue(method = "moveEntityWithHeading", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isSneaking()Z", ordinal = 2))
	private boolean additives$descendScaffolding(boolean sneaking) {
		Mob self = (Mob) (Object) this;
		return sneaking && self.world.getBlock(MathHelper.floor(self.x), MathHelper.floor(self.bb.minY), MathHelper.floor(self.z)) != AdditivesBlocks.WOODEN_SCAFFOLDING;
	}
}
