package moe.okaeri.mobsbegone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

import static moe.okaeri.mobsbegone.MobsBeGone.LOGGER;

public class MobsBeGoneConfig {
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("mobsbegone-blacklist.json");
	private static final Type SET_STRING = new TypeToken<Set<String>>(){}.getType();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static boolean[] loadBlacklist() {
		Set<String> rawStrings = loadBlacklistStrings();
		//int maxRegistryRaw = Registries.ENTITY_TYPE.stream().mapToInt(Registries.ENTITY_TYPE::getRawId).max().orElse(-1);
		//boolean[] lookup = new boolean[maxRegistryRaw + 2];
		boolean[] lookup = new boolean[1024 + 2];

		for (String s : rawStrings) {
			try {
				Identifier id = Identifier.of(s);
				var type = Registries.ENTITY_TYPE.get(id);
				int raw = Registries.ENTITY_TYPE.getRawId(type);
				if (raw >= 0) {
					lookup[raw + 1] = true;
				}
			} catch (Exception ex) {
				LOGGER.warn("Invalid entity ID in config: {}", s);
			}
		}

		return lookup;
	}

	private static Set<String> loadBlacklistStrings() {
		try {
			if (Files.notExists(CONFIG_PATH)) {
				try (InputStream in = getDefaultStream()) {
					if (in == null) throw new IOException("Default config not found!");
					Files.copy(in, CONFIG_PATH);
				}
			}
			String content = Files.readString(CONFIG_PATH);
			return GSON.fromJson(content, SET_STRING);

		} catch (IOException e) {
			e.printStackTrace();
			try (InputStream in = getDefaultStream()) {
				if (in == null) return Collections.emptySet();
				return GSON.fromJson(new InputStreamReader(in), SET_STRING);
			} catch (IOException inner) {
				inner.printStackTrace();
				return Collections.emptySet();
			}
		}
	}

	private static InputStream getDefaultStream() {
		return MobsBeGoneConfig.class.getClassLoader().getResourceAsStream("mobsbegone-blacklist.json");
	}
}
