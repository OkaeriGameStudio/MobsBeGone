package moe.okaeri.mobsbegone;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MobsBeGone implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("mobsbegone");
	private static boolean[] BLACKLIST;
	public static final Set<Entity> TO_REMOVE = ConcurrentHashMap.newKeySet();

	@Override
	public void onInitialize() {
		BLACKLIST = MobsBeGoneConfig.loadBlacklist();

		int count = 0;
		for (boolean b : BLACKLIST) {
			if (b) count++;
		}

		ServerEntityEvents.ENTITY_LOAD.register(this::onEntityLoad);
		ServerTickEvents.END_SERVER_TICK.register(this::onEndServerTick);

		LOGGER.info("MobsBeGone is up and running! Loaded {} blacklisted entities!", count);
	}

	private void onEntityLoad(Entity entity, ServerWorld world) {
		if (entity instanceof LivingEntity) {
			if (isEntityBlacklisted(entity.getType())) {
				TO_REMOVE.add(entity);
			}
		}
	}

	private void onEndServerTick(MinecraftServer minecraftServer) {
		for (Entity toRemove : TO_REMOVE) {
			if (!toRemove.isRemoved()) {
				toRemove.discard();
			}
		}
		TO_REMOVE.clear();
	}

	public static boolean isEntityBlacklisted(EntityType<?> entityType) {
		int raw = Registries.ENTITY_TYPE.getRawId(entityType);
		return BLACKLIST[raw + 1];
	}
}
