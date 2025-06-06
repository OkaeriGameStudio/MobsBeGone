package moe.okaeri.mobsbegone;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class MobsBeGone implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("mobsbegone");
	private static long[] BLACKLIST;
	private static final ObjectArrayList<Entity> ENTITY_BUFFER = new ObjectArrayList<>(64);

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
	}

	private void onServerStarting(MinecraftServer minecraftServer) {
		BLACKLIST = MobsBeGoneConfig.loadBlacklist();

		ServerEntityEvents.ENTITY_LOAD.register(this::onEntityLoad);
		ServerTickEvents.END_SERVER_TICK.register(this::onEndServerTick);

		LOGGER.info("MobsBeGone >> Up and running!");
	}

	private void onEntityLoad(Entity entity, ServerWorld world) {
		if (entity instanceof LivingEntity) {
			if (isEntityBlacklisted(entity.getType())) {
				ENTITY_BUFFER.add(entity);
			}
		}
	}

	private void onEndServerTick(MinecraftServer minecraftServer) {
		int size = ENTITY_BUFFER.size();
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < size; i++) {
			Entity entity = ENTITY_BUFFER.get(i);
			if (entity != null && !entity.isRemoved()) {
				entity.discard();
			}
		}
		ENTITY_BUFFER.clear();
	}

	public static boolean isEntityBlacklisted(EntityType<?> entityType) {
		int raw = Registries.ENTITY_TYPE.getRawId(entityType);
		return (((BLACKLIST[raw >>> 6] >>> (raw & 63)) & 1L) != 0);
	}
}
