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

	private BeehiveBlockEntityMixin() { }

	@Inject(method = "releaseBee", at = @At("HEAD"), cancellable = true)
	private static void onReleaseBee(World world, BlockPos pos, BlockState state, BeehiveBlockEntity.BeeData bee, List<Entity> entities, BeehiveBlockEntity.BeeState beeState, BlockPos flowerPos, CallbackInfoReturnable<Boolean> cir) {
		if (MobsBeGone.isEntityBlacklisted(EntityType.BEE)) {
			cir.setReturnValue(true);
			cir.cancel();
		}
	}
}
