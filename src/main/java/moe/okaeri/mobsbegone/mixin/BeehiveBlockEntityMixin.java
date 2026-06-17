package moe.okaeri.mobsbegone.mixin;

import moe.okaeri.mobsbegone.MobsBeGone;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {

	private BeehiveBlockEntityMixin() {
		// Privado para evitar la instanciación de la clase Mixin
	}

	@Inject(method = "releaseBee", at = @At("HEAD"), cancellable = true)
	private static void onReleaseBee(World world, BlockPos pos, BlockState state, Object bee, List<Entity> entities, Object beeState, BlockPos flowerPos, CallbackInfoReturnable<Boolean> cir) {
		if (MobsBeGone.isEntityBlacklisted(EntityType.BEE)) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
}
