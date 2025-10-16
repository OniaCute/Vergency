package cc.vergency.injections.mixins.player;

import cc.vergency.Vergency;
import cc.vergency.features.event.events.player.*;
import cc.vergency.utils.interfaces.Wrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.Hand;
import org.apache.logging.log4j.core.Core;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements Wrapper {
	@Shadow protected abstract void sendMovementPackets();

	public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Unique
	boolean pre_sprint_state = false;

	@Unique
	private boolean updateLock = false;

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void playerTickEvent$Hook(CallbackInfo info) {
		PlayerTickEvent event = new PlayerTickEvent();
		Vergency.EVENTBUS.post(event);

		if (event.isCancelled()) {
			info.cancel();
			if (event.getIterations() > 0) {
				for (int i = 0; i < event.getIterations(); i++) {
					updateLock = true;
					tick();
					updateLock = false;
					sendMovementPackets();
				}
			}
		}
	}

	@Inject(method = "setCurrentHand", at = @At(value = "HEAD"))
	private void handChangeEvent$Hook(Hand hand, CallbackInfo info) {
		Vergency.EVENTBUS.post(new HandChangeEvent(hand));
	}

	@Inject(method = "sendMovementPackets", at = @At(value = "HEAD"), cancellable = true)
	private void playerSyncEvent$Hook(CallbackInfo ci) {
		PlayerSyncEvent event = new PlayerSyncEvent(getYaw(), getPitch());
		Vergency.EVENTBUS.post(event);
		if (event.isCancelled()) {
			ci.cancel();
		}
	}
}
