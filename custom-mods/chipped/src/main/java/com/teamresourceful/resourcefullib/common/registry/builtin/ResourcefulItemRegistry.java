package com.teamresourceful.resourcefullib.common.registry.builtin;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ResourcefulItemRegistry extends ResourcefulRegistry<Item> {
    public ResourcefulItemRegistry(ResourceKey<? extends net.minecraft.core.registries.Registry<Item>> key) {
        super(key);
    }
}
