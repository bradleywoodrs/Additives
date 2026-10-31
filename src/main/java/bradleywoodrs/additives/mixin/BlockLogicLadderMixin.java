package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.AdditivesConfig;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLadder;
import net.minecraft.core.block.BlockLogicSlab;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockLogicLadder.class, remap = false)
public abstract class BlockLogicLadderMixin {
	@Inject(method = "canExistAt", at = @At("RETURN"), cancellable = true)
	private void allowOnSlabs(World world, TilePosc pos, int meta, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValueZ() || !AdditivesConfig.CFG.getBoolean("Additions.placeLaddersOnSlabs")) {
			return;
		}
		BlockLogicLadder ladder = (BlockLogicLadder) (Object) this;
		Block<?> block = world.getBlockType(pos.add(ladder.getSideFromMeta(meta).opposite().direction(), new TilePos()));
		if (block == null) {
			return;
		}
		if (block.getLogic() instanceof BlockLogicSlab) {
			cir.setReturnValue(true);
		}
	}
}
