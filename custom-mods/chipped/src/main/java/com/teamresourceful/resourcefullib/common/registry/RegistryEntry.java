package com.teamresourceful.resourcefullib.common.registry;

import net.minecraft.core.Holder;

public interface RegistryEntry<R, T> {
    T get();
    Holder<R> holder();
}
