package cc.vergency.injections.mixins.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.player.HandChangeEvent;
import cc.vergency.features.event.events.player.PlayerTickEvent;
import cc.vergency.utils.interfaces.Wrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements Wrapper {
	public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void playerTickEvent$Hook(CallbackInfo info) {
		if (mc.player != null && mc.world != null) {
			Vergency.EVENTBUS.post(new PlayerTickEvent());
		}
	}

	@Inject(method = "setCurrentHand", at = @At(value = "HEAD"))
	private void handChangeEvent$Hook(Hand hand, CallbackInfo info) {
		Vergency.EVENTBUS.post(new HandChangeEvent(hand));
	}
}
