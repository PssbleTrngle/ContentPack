package com.possible_triangle.content_packs.platform.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;



public interface IPlatformHelper {

    <T> RegistryCodecSupplier<T> createRegistry(Class<T> clazz,ResourceKey<Registry<T>> key);

    @FunctionalInterface
    interface RegistryCodecSupplier<T> {
        Codec<T> byNameCodec();
    }

}
