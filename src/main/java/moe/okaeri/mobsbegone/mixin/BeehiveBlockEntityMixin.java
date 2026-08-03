package moe.okaeri.mobsbegone.mixin;

import moe.okaeri.mobsbegone.MobsBeGone;

import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.EntityType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {
	// Thank you zonary123 for this fix! Returns true to mark bees as already spawned to prevent the legendary 20Hz bee dubstep.
	@Inject(method = "releaseBee", at = @At("HEAD"), cancellable = true)
	private static void onReleaseBee(CallbackInfoReturnable<Boolean> cir) {
		if (MobsBeGone.isEntityBlacklisted(EntityType.BEE)) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
}
