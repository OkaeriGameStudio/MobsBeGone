# MobsBeGone 🐾🚫

**MobsBeGone** is a lightweight Fabric mod for **Minecraft 1.21.1** that lets you blacklist mobs from spawning entirely.

It was made because existing solutions were too complicated for smaller projects — so this one is designed with simplicity and efficiency in mind.

## 🔧 How It Works Internally

- The mod reads a `mobsbegone-blacklist.json` file from your config folder.
- Inside it, you list entity IDs you want to completely disable (e.g. `"minecraft:creeper"`).
- It parses the file at startup and stores the blacklist in a fast O(1) lookup table.
- When any mob tries to spawn (natural, command, structure, etc.), it checks if it's blacklisted.
- If the mob is on the blacklist, it gets instantly discarded and never visually spawned.

The logic is designed to be safe and avoids issues by marking blacklisted entities for removal on the next server tick.

## 📂 Config Example

Put this file in: `config/mobsbegone-blacklist.json` (The default config already contains all of the current Minecraft mobs)

```json
[
  "minecraft:creeper",
  "minecraft:enderman",
  "anothermod:somebaka"
]
```
And that's it! Those mobs will never appear in your world again. 💣💥

You can add any entity from either the base game, or any other mod you might be using!

Please keep in mind that the **last entry of the list should NOT contain a trailing comma!**

## ⚠️ Disclaimer

This mod is provided **as-is**.

You're free to use it, modify it, fork it, or yell at it lovingly.  
No guarantees, no warranties — just vibes. ✨

## ✨ Credits

Made with love by **Okaeri Game Studio** 💜

**Authors:** Olivia, EasyMochi

---

Enjoy the peace and quiet of a mob-free world! 🌸