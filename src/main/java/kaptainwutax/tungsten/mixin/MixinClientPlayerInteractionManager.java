package kaptainwutax.tungsten.mixin;

import kaptainwutax.tungsten.TungstenMod;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

	@Shadow @Final private MinecraftClient client;

	@Inject(method = "attackBlock", at = @At("HEAD"))
	public void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> ci) {
		if(this.client.world.getBlockState(pos).getBlock() == Blocks.GOLD_BLOCK) {
			TungstenMod.TARGET = new Vec3d(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
		}
	}

}
