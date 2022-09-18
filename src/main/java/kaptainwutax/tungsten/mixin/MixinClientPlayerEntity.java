package kaptainwutax.tungsten.mixin;

import com.mojang.authlib.GameProfile;
import kaptainwutax.tungsten.TungstenMod;
import kaptainwutax.tungsten.frame.Frame;
import kaptainwutax.tungsten.path.PathFinder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends PlayerEntity {

	public MixinClientPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
		super(world, pos, yaw, gameProfile, publicKey);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void start(CallbackInfo ci) {
		if(TungstenMod.EXECUTOR.isRunning()) {
			TungstenMod.EXECUTOR.tick((ClientPlayerEntity)(Object)this, MinecraftClient.getInstance().options);
		}

		if(!this.getAbilities().flying) {
			Frame.INSTANCE = Frame.of((ClientPlayerEntity)(Object)this);
			Frame.INSTANCE.tick(this.world);
		}

		if(MinecraftClient.getInstance().options.swapHandsKey.isPressed()) {
			PathFinder.find(this.world, TungstenMod.TARGET);
		}
	}

	@Inject(method = "tick", at = @At(value = "RETURN"))
	public void end(CallbackInfo ci) {
		if(!this.getAbilities().flying) {
			Frame.INSTANCE.compare((ClientPlayerEntity)(Object)this, false);
		}
	}

}
