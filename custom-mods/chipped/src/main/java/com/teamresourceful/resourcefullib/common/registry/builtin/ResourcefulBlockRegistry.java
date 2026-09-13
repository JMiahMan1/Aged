package com.teamresourceful.resourcefullib.common.registry.builtin;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class ResourcefulBlockRegistry extends ResourcefulRegistry<Block> {
    public ResourcefulBlockRegistry(ResourceKey<? extends net.minecraft.core.registries.Registry<Block>> key) {
        super(key);
    }
}
