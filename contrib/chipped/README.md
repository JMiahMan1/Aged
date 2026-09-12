# Chipped contrib port (Wave 1, hardest+largest first)

- **Upstream**: https://github.com/terrarium-earth/Chipped
  (Terrarium Licence — same-mod native rebuild, like the satisfy family)
- **Base**: default branch at clone time (`version=4.1.0`, `minecraftVersion=1.21.4`)
- **Status**: sources staged at `custom-mods/chipped/src` (common+fabric java
  minus neoforge/datagen/JEI-compat, ~38k merged resource files, zero
  collisions between `resources/` and `generated/resources/`); NOT yet wired
  into `settings.gradle` — inert until the port lands.
- **Requires**: Athena (this repo's port) ≥4.0.0 + resourcefullib (have 5.0.3).
- **Known 26.2 work**: inline `@ExpectPlatform` bodies (FakeLevel,
  ChippedClient — same treatment as letsdo `PlatformHelper`), `DirectionBlock`
  (`DirectionProperty` is gone in 26.2), REI plugin against REI 26.2.
- **Patches**: recorded here under `patches/` when the port lands.
