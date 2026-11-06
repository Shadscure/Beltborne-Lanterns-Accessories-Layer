# Changelog

## [1.2.3] - 2026-11-06
### Fixed
- **Datapack Lamps Rendering (`#15`)**: Fixed a flat 2D item sprite rendering on top of the 3D belt lantern model for lamps added via datapacks (e.g. Paxi or world datapacks) by re-registering empty Accessories item renderers upon world join and tag sync.
- **Respawn Container Sync (`#14`)**: Fixed `Unable to Sync Container Data for a given Entity as it is null on the Client!` error after death and respawn by not querying `newPlayer` capability prematurely during `PlayerEvent.Clone`.
- **The Aether Compatibility (`#13`)**: Lowered the minimum `accessories` version requirement on `1.21.1` to `>= 1.1.0-beta.1` so the mod loads cleanly alongside The Aether's bundled Accessories version (`1.1.0-beta.48`).
- **Config & Loader Metadata**: Improved `bl_accessories_layer.json` resilience on malformed JSON, separated Fabric/NeoForge mixin configs, and tightened `minecraft`, `neoforge`, and `beltborne_lanterns` dependency bounds.

## [1.2.2] - 2026-11-04
### Fixed
- Fixed a crash on Fabric when the Accessories slot mixin could not remap the `getMaxStackSize` method descriptor.
