package com.possible_triangle.content_packs.platform;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;


@FunctionalInterface
public interface RegistryEvent {

    <T> Supplier<T> register(ResourceKey<Registry<T>> registry, ResourceLocation id, Supplier<T> factory);

}
