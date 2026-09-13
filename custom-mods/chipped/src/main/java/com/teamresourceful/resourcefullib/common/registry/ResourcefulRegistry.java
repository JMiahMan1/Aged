package com.teamresourceful.resourcefullib.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ResourcefulRegistry<T> {
    private final ResourceKey<? extends net.minecraft.core.registries.Registry<T>> key;

    public ResourcefulRegistry(ResourceKey<? extends net.minecraft.core.registries.Registry<T>> key) {
        this.key = key;
    }

    public <I extends T> RegistryEntry<T, I> register(ResourceLocation id, Supplier<I> supplier) {
        // In production: registers with vanilla registry and wraps in RegistryEntry
        // For port stub: return a minimal entry that holds the value
        I value = supplier.get();
        return new Entry<>(value);
    }

    public <I extends T> RegistryEntry<T, I> register(String path, Supplier<I> supplier) {
        return register(new ResourceLocation(path), supplier);
    }

    static class Entry<T, I extends T> implements RegistryEntry<T, I> {
        final I value;
        Entry(I value) { this.value = value; }
        public I get() { return value; }
        public Holder<T> holder() { return Holder.direct(value); }
    }
}
