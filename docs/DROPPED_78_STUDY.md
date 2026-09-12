# Dropped-78 study: return path for every cut mod

Status snapshot 2026-08-29 (Modrinth API, raw: `.tmp/modrinth_status.json`).
**Policy (user directive, 2026-08-29): NO mod is dropped — ever.** Every mod
below has a concrete path back into the pack:

## 2026-09-12 mod-parity review (supersedes Sequencing below)

Full Aged 3.1.2 (212 mod jars) vs pack audit: **everything with a 26.2
upstream build is deployed** (cross-checked `resolved.json` picks against
server mod ids — zero ready-but-missing). User decisions recorded:

- **Fork-port queue is live, structures first.** Wave 1 ranked by feasibility
  (Modrinth probe 2026-09-12 — loaders, max MC, source):
  1. `dungeonz` (fabric ≤1.21.1, github Globox1997 — same family as our other Globox work)
  2. `lukis-grand-capitals` (ships a datapack loader ≤1.21.11 — data-only port may suffice)
  3. `spider-caves` (fabric ≤1.20.4, github HexagonNico — small)
  4. `profundis` (fabric ≤1.21.4, github firenh — cave biomes)
  5. `Dungeon Now Loading` (fabric ≤1.20.1, github hexnowloading — heavy NBT set)
  6. `dungeons+` LAST (forge/neoforge ONLY, gitlab modding-legacy — fabric port is a rewrite)
  - Re-locate first (404 on current slugs): `desert-dungeon`, `u_desert`,
    `underground-jungle`, `betterendcitiesvanilla`, `mns`, `mes`.
- Wave 2: mobs (adventurez, fleshz, creeper/enderman overhauls; astrocraft
  held — conflicts with realism north star, revisit with user).
- Wave 3: gear/decor/utility (inmis+backslot+trinkets cluster as ONE best,
  amarite, medievalweapons, chipped+athena, another_furniture, bbb,
  couplings, villager-transportation, smarterfarmers, exposure,
  antique-atlas, Pockets, async-locator, noisium).
- **Terrain duality kept**: Aged ships neither Terralith nor Tectonic, but
  both stay until the planned pick-ONE pass (user decision).
- **Solved since 8-29** (no port needed): herdspanic→`HerdPanic.java`,
  revive→survival `revive/` package, rpgdifficulty→skills `MobScaling`,
  seasonhud/crop_growth_modifier→world seasons-lite, naturalist→world
  `fauna/`, endrem/exposure/antique-atlas→world item ports (quest loops TBD),
  emi family→REI substitute, letsdo family→`letsdo-*` ports, Tier 1 adoptions
  (ExtendedDrawers, scholar, chalk pair, supermartijn libs, Boids,
  true-ending, MRU) all deployed, birdsboids deployed.
- **Pins**: architectury capped at 21.0.7 while loader is 0.19.3 / api
  0.159.0 (21.1.9+ needs loader≥0.19.5, api≥0.160.0 — broke boot 2026-09-12;
  see manifest note. `resolve_deps.py` does not model loader/API
  co-constraints — review picks before deploying).

- **adopt** — a 26.x build exists today; flip `mods-manifest.json` to `keep`
  and let the resolver pick it up.
- **watchlist** — author active on 26.1.x; auto-resolves on a future bump;
  fork-and-rebuild (YUNG treatment, see `docs/PATCH_PORT_STUDY.md`) if it
  stalls one full bump cycle.
- **fork-port** — we patch + rebuild for 26.2 locally, like the YUNG suite.
- **rebuild** — we deliver the feature inside a `hearthwind-*` module
  (datapack recipes/worldgen or custom mod code).
- **superseded** — the mod's role is already delivered in-house; the *feature*
  is kept, the duplicate jar is not needed. Re-adopt only if our module
  regresses.
- **re-locate** — slug not found on Modrinth (renamed/moved); find current
  upstream before choosing a path.

Nothing in this document ends in "dropped permanently".

## Tier 1 — 26.x build exists NOW (adopt on next pack review) — 8

| Mod | 26.x | Role | Return action |
|---|---|---|---|
| ExtendedDrawers | 26.2 | Storage/drawers | adopt if storage gap confirmed (de-kludge: ONE storage system) |
| scholar | 26.2 | Book/writing UI | adopt |
| chalk-colorful-addon | 26.2 | Companion to kept `chalk` | adopt (completes the chalk pair) |
| supermartijn642configlib | 26.2 | Lib for supermartijn mods | adopt when any dependent mod returns |
| supermartijn642corelib | 26.2 | same | adopt with configlib |
| Boids | 26.2 | Ambient flocking mobs | adopt (ambience) |
| tru.e-ending | 26.1.2 | End content | adopt on a 26.2-minor bump or fork-port the 26.1.2 jar |
| MRU | 26.3-snapshot-7 | Lib | adopt with whichever dependent mod returns first |

## Tier 2 — 26.1.x exists (watchlist or fork-port) — 4

| Mod | 26.x | Role | Return action |
|---|---|---|---|
| paxi | 26.1.2 | Datapack loader | superseded — native world datapack does its job (`migrate_datapack.py`); keep the feature, not the jar |
| athena | 26.1.2 | CTM lib (needed by chipped) | watchlist; returns with chipped |
| arrp | 26.1.2 | Runtime resource pack lib | watchlist; returns with a dependent mod |
| birdsboids | 26.1 | Boids bird pack | watchlist; adopt once Boids-core lands |

## Tier 3 — stalled at ≤1.21.x (fork-port or rebuild) — grouped by feature

### Gear & accessories
| Mod | Max | Return action |
|---|---|---|
| medievalweapons | 1.21.1 | fork-port (content mod, assets reusable) or fold tiered-weapon roles into hearthwind-primitive tiered affixes |
| amarite | 1.20.1 | fork-port; author last touched 2024 → rebuild in primitive/steel age scope if port is heavy |
| inmis / inmisaddon | 1.21.1 | backpack slots; trinkets-dependent — port the pair together or rebuild as hearthwind accessory slot |
| backslot / backslotaddon | 1.21.1 | same accessory-slot cluster as inmis — ONE best survives (de-kludge), feature never lost |
| trinkets | 1.21.1 | API lib for the above; fork-port when the accessory cluster returns |
| revive | 1.21.1 | co-op revive; fork-port (small, server-side) — high multiplayer value |
| smitherz | 1.21.1 | smithing-focused content; overlap with hearthwind-skills smithing + jobs smither — rebuild unique pieces into those modules |
| travelerz | 1.21.1 | map/travel content; rebuild unique pieces into world/skills; fork-port if assets carry |
| libz | 1.21.1 | lib for smitherz/travelerz; returns with them |

### Mobs & ambience
| Mod | Max | Return action |
|---|---|---|
| naturespirit | 1.21.1 | big worldgen/biome content; watchlist + fork-port; competes with Tectonic/Terralith pick-ONE rule |
| gardens-of-the-dead | 1.21.1 | nether gardens; fork-port (data-driven worldgen) |
| naturalist | 1.21.1 | ambient animals; fork-port or rebuild as hearthwind-world ambient layer |
| creeperoverhaul | 1.21.1 | biome creeper variants; fork-port (mostly client visuals + spawn data) |
| endermanoverhaul | 1.21.1 | same pattern as creeperoverhaul |
| adventurez | 1.21.1 | mini-bosses; fork-port; schedule after Age-gating review |
| astrocraft | 1.21.4 | aliens/sci-fi — conflicts with realism north star; revisit with user before any port effort (kept, not dropped) |
| fleshz | 1.21.1 | flesh dimensions/mobs; fork-port with adventurez cluster |

### Structures & dungeons
| Mod | Max | Return action |
|---|---|---|
| Dungeon Now Loading | 1.20.1 | fork-port; heavy NBT structure set — assets reusable |
| dungeons+ | 1.20.4 | fork-port |
| dungeonz | 1.21.1 | fork-port |
| spirder-caves | 1.20.4 | fork-port (small) |
| profundis | 1.21.4 | cave worldgen; competes with Tectonic/Terralith pick-ONE — port as alternative profile |
| lukis-grand-capitals | 1.21.9 | closest to portability; fork-port |
| villagesandpillages | 1.21.4 | fork-port |
| betterendcitiesvanilla | — | re-locate (slug changed); then fork-port |
| u_desert / desert-dungeon / underground-jungle / mns / mes | — | re-locate (renames); then fork-port each |

### Let's Do food family (10) — all stalled ~1.20-1.21.1
| Mod | Return action |
|---|---|
| letsdo-API + bakery/brewery/candlelight/farm_and_charm/meadow/vinery/nethervinery/herbalbrews + emi-letsdo-compat + sushi_bar | rebuild: ONE cohesive food system in hearthwind (de-kludge: family collapsed to one best). Port the unique foods/recipes as datapack content under our namespaces; keep the family's identity features (bakery, brewing, farm charm) as rebuild epics. Nothing is lost — content migrates. |

### Furniture & deco
| Mod | Max | Return action |
|---|---|---|
| another_furniture | 1.21.1 | fork-port (pure content) |
| chipped | 1.21.1 | fork-port with athena (CTM); huge block-variant content — batch-generate variants via `gen_placeholder_assets.py` pattern |
| bbb (Barrels Bins Boxes) | — | re-locate; then fork-port (storage; competes with ExtendedDrawers pick-ONE) |

### Transport
| Mod | Max | Return action |
|---|---|---|
| smallships / ships | 1.21.4 | fork-port the better one first, then the other (pick-ONE: one ship system total) |
| niftycarts | 1.21.8 | fork-port; near-miss versions — cheap |
| immersive_aircraft | 1.21.11 | fork-port; Age-gate behind Mechanical Age |
| connectiblechains | — | re-locate; small mixin mod, fork-port |
| villager-transportation | 23w32a | fork-port (small) |

### Utility & UI
| Mod | Max | Return action |
|---|---|---|
| surveyor / lavender / Pockets / villagerfix / smarterfarmers / extendeddrawersaddon | — | re-locate (slugs changed), then fork-port each (all small) |
| couplings | 1.20.1 | fork-port (doors couple — tiny) |
| async-locator | 1.20.2 | fork-port (server-side locate optimization) |

### Libs & perf
| Mod | Max | Return action |
|---|---|---|
| moonlight | 1.21.1 | lib for many content mods; fork-port when first dependent returns |
| memoryleakfix | 22w07a | superseded — modern vanilla/fabric covered these leaks; re-adopt only on evidence |
| DEUF_Refabricated | 1.19.2 | entity collision perf; superseded by modern vanilla + lithium; re-adopt on profiling evidence |
| Grass_Overhaul | 1.21.1 | fork-port with naturespirit cluster |

### Superseded in-house (feature delivered by our modules)
| Mod | Return action |
|---|---|
| time-and-wind | superseded — hearthwind-world seasons/time pacing carries the feature (slug also gone from Modrinth) |

## Sequencing

1. **Now**: adopt Tier 1 (8 mods) at next pack review — zero port work.
2. **Next**: re-locate the 17 unknown slugs (mechanical API/website search).
3. **Then**: fork-port in value order: revive (co-op), niftycarts,
   lukis-grand-capitals, connectiblechains, couplings, creeperoverhaul,
   endermanoverhaul, naturalist.
4. **Epics** (rebuild or big forks): Let's Do family collapse, chipped+athena,
   ship cluster, accessory-slot cluster (inmis/backslot/trinkets),
   naturespirit/profundis worldgen profiles.
