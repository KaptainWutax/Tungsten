package kaptainwutax.tungsten.mixin;

import kaptainwutax.tungsten.TungstenMod;
import kaptainwutax.tungsten.world.VoxelWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

	@Shadow @Nullable public ClientWorld world;

	@Inject(at = @At("HEAD"), method = "tick")
	private void tick(CallbackInfo info) {
		if(this.world == null) {
			TungstenMod.WORLD = null;
		} else if(TungstenMod.WORLD == null) {
			TungstenMod.WORLD = new VoxelWorld(this.world);
		} else if(TungstenMod.WORLD.parent != this.world) {
			TungstenMod.WORLD = new VoxelWorld(this.world);
		}
	}

}
