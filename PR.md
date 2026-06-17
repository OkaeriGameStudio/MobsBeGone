Resolves #1

### Summary
* Bumped mod version to 0.8.
* Optimized `ServerWorldMixin` to return `true` on cancelled spawns, resetting spawn timers for spawner blocks, preventing infinite tick spawn loops.
* Added `BeehiveBlockEntityMixin` to intercept `releaseBee` and prevent bees from spawning directly from beehives and bee nests if they are blacklisted, completely resolving the looping beehive exit sound and infinite tick loops.
* Improved security and stability in `MobsBeGone.java` by initializing the `blacklist` array to prevent potential `NullPointerExceptions` and adding array bounds checking to avoid index crashes.
* Cleaned up nested `if` statements in the entity load handler.

### Changes Table
| File | Change |
|------|--------|
| `gradle.properties` | Bumped version to `0.8`. |
| `src/main/resources/mobsbegone.mixins.json` | Registered the new `BeehiveBlockEntityMixin`. |
| `src/main/java/moe/okaeri/mobsbegone/MobsBeGone.java` | Safely initialize `blacklist`, add bounds check, and simplify `onEntityLoad`. |
| `src/main/java/moe/okaeri/mobsbegone/mixin/ServerWorldMixin.java` | Return `true` in `onAddEntity` to avoid spawner/beehive infinite tick loops. |
| `src/main/java/moe/okaeri/mobsbegone/mixin/BeehiveBlockEntityMixin.java` | [NEW] Prevent bees from releasing when blacklisted, fixing the exit sound loop bug. |
