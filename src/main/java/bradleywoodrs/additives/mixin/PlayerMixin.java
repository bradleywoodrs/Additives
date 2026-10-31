package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.blocks.BlockLogicHayBed;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicBed;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.world.pos.TilePos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {
	@Unique
	private static final double HAY_BED_SLEEP_DROP = 9.0 / 16.0 - BlockLogicHayBed.HEIGHT;

	@Unique
	private TilePos additives$hayBedToConsume;

	@Unique
	private Player additives$self() {
		return (Player) (Object) this;
	}

	@WrapWithCondition(
		method = "sleepInBedAt",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;setPlayerSpawnPoint(Lnet/minecraft/core/world/pos/TilePos;)V")
	)
	private boolean additives$skipHayBedSpawn(Player player, TilePos pos, int x, int y, int z) {
		return !BlockLogicHayBed.isHayBed(player.world, x, y, z);
	}

	@WrapWithCondition(
		method = "sleepInBedAt",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/player/Player;sendStatusMessageTranslated(Ljava/lang/String;[Ljava/lang/String;)V")
	)
	private boolean additives$skipHayBedSpawnMessage(Player player, String key, String[] args, int x, int y, int z) {
		return !"messages.bed.setSpawn".equals(key) || !BlockLogicHayBed.isHayBed(player.world, x, y, z);
	}

	@ModifyExpressionValue(
		method = "sleepInBedAt",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/SpawnerMobs;isUnsafeToSleep(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;)Z")
	)
	private boolean additives$hayBedAlwaysSafe(boolean unsafe, int x, int y, int z) {
		return unsafe && !BlockLogicHayBed.isHayBed(additives$self().world, x, y, z);
	}

	@Inject(method = "setPlayerSleeping", at = @At("TAIL"))
	private void additives$lowerOntoHayBed(int x, int y, int z, CallbackInfo ci) {
		Player self = additives$self();
		if (BlockLogicHayBed.isHayBed(self.world, x, y, z)) {
			self.setPos(self.x, self.y - HAY_BED_SLEEP_DROP, self.z);
		}
	}

	@Inject(method = "wakeUpPlayer", at = @At("HEAD"))
	private void additives$rememberHayBed(boolean flag, boolean flag1, CallbackInfo ci) {
		Player self = additives$self();
		TilePos bed = self.bedTilePos;
		additives$hayBedToConsume = !self.world.isClientSide
			&& self.isPlayerFullyAsleep()
			&& bed != null
			&& BlockLogicHayBed.isHayBed(self.world, bed.x, bed.y, bed.z)
			? new TilePos(bed.x, bed.y, bed.z)
			: null;
	}

	@Inject(method = "wakeUpPlayer", at = @At("TAIL"))
	private void additives$consumeHayBed(boolean flag, boolean flag1, CallbackInfo ci) {
		TilePos bed = additives$hayBedToConsume;
		additives$hayBedToConsume = null;
		Player self = additives$self();
		if (bed != null && BlockLogicHayBed.isHayBed(self.world, bed.x, bed.y, bed.z)) {
			World world = self.world;
			Block<?> block = world.getBlockType(bed);
			world.playBlockEvent(bed, LevelListener.EVENT_BLOCK_BREAK, block.id());
			TilePos otherHalf = BlockLogicBed.otherHalf(world, bed, world.getBlockData(bed));
			if (otherHalf != null) {
				world.playBlockEvent(otherHalf, LevelListener.EVENT_BLOCK_BREAK, block.id());
			}
			world.setBlockTypeNotify(bed, Blocks.AIR);
		}
	}
}
