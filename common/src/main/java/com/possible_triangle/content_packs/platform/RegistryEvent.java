package com.possible_triangle.content_packs.platform;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;


@FunctionalInterface
public interface RegistryEvent {

    <T> void register(ResourceKey<Registry<T>> registry, ResourceLocation id, T value);

}
