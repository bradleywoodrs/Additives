package bradleywoodrs.additives.mixin;

import bradleywoodrs.additives.rightclickharvest.RightClickHarvest;
import net.minecraft.core.block.BlockLogicPumpkin;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockLogicPumpkin.class, remap = false)
public class BlockLogicPumpkinMixin {
	@Inject(method = "onInteracted", at = @At("HEAD"), cancellable = true)
	private void additives$onInteracted(World world, TilePosc pos, Player player, Side side, double xHit, double yHit, CallbackInfoReturnable<Boolean> cir) {
		ItemStack held = player.getHeldItem();
		if (!RightClickHarvest.canHarvest(player)
			|| (held != null && held.getItem() instanceof ItemToolSword)
			|| world.getBlockType(pos) != Blocks.PUMPKIN
			|| world.getBlockType(pos.down(new TilePos())) != Blocks.FARMLAND_DIRT) {
			return;
		}

		if (!world.isClientSide) {
			RightClickHarvest.dropBreakResult(world, pos, Blocks.PUMPKIN, world.getBlockData(pos));
			world.setBlockTypeDataNotify(pos, Blocks.CROPS_PUMPKIN, 0);
		}
		RightClickHarvest.damageHoe(player);
		cir.setReturnValue(true);
	}
}
