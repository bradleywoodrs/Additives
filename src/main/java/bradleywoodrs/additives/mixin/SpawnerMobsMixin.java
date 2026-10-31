package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.blocks.BlockLogicHayBed;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.SpawnerMobs;
import net.minecraft.core.world.pos.TilePos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = SpawnerMobs.class, remap = false)
public abstract class SpawnerMobsMixin {
	@WrapOperation(
		method = "performSleepSpawning",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;isPlayerSleeping()Z")
	)
	private static boolean additives$noNightmaresInHayBed(Player player, Operation<Boolean> original) {
		if (!original.call(player)) {
			return false;
		}
		TilePos bed = player.bedTilePos;
		return bed == null || !BlockLogicHayBed.isHayBed(player.world, bed.x, bed.y, bed.z);
	}
}
