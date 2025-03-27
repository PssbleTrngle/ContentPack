package com.possible_triangle.content_packs.platform.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;


public interface IPlatformHelper {

    <T> RegistryCodecSupplier<T> createRegistry(ResourceKey<Registry<T>> key);

    @FunctionalInterface
    interface RegistryCodecSupplier<T> {
        Codec<T> byNameCodec();
    }

}
