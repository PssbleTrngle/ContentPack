package com.possible_triangle.content_packs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class LazyCodecs {

    public static <T> Codec<Supplier<T>> byNameCodec(Registry<T> registry) {
        var type = registry.key().location();
        return ResourceLocation.CODEC.flatXmap(
                key -> DataResult.success(() -> registry.getOrThrow(ResourceKey.create(registry.key(), key))),
                supplier -> {
                    var key = registry.getKey(supplier.get());
                    if (key == null) return DataResult.error(() -> "Unknown %s".formatted(type));
                    return DataResult.success(key);
                }
        );
    }

}
