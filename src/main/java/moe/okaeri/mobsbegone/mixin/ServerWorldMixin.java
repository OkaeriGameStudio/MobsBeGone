package moe.okaeri.mobsbegone.mixin;

import moe.okaeri.mobsbegone.MobsBeGone;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
	private void onAddEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof LivingEntity) {
			if (MobsBeGone.isEntityBlacklisted(entity.getType())) {
				entity.discard();
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
