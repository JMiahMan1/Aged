package com.teamresourceful.resourcefullib.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public final class ResourcefulRegistries {
    public static <T> ResourcefulRegistry<T> create(ResourceKey<? extends net.minecraft.core.registries.Registry<T>> key) {
        return new ResourcefulRegistry<>(key);
    }
}
