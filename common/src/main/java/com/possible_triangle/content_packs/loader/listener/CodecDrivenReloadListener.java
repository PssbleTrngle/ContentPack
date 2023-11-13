package com.possible_triangle.content_packs.loader.listener;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.possible_triangle.content_packs.Constants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;
import java.util.Optional;

public abstract class CodecDrivenReloadListener<T> extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private final RegistryAccess registryAccess;

    protected CodecDrivenReloadListener(String path, RegistryAccess registryAccess) {
        super(GSON, path);
        this.registryAccess = registryAccess;
    }

    protected abstract Codec<T> codec();

    @Override
    protected final void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager manager, ProfilerFiller profiler) {
        var ops = Optional.ofNullable(registryAccess)
                .<DynamicOps<JsonElement>>map(it -> RegistryOps.create(JsonOps.INSTANCE, it))
                .orElse(JsonOps.INSTANCE);

        var codec = codec();

        var entries = new ImmutableMap.Builder<ResourceLocation, T>();

        elements.forEach((id, json) -> {
            try {
                var result = codec.parse(ops, json).resultOrPartial(Constants.LOGGER::error);
                result.ifPresent(value -> entries.put(id, value));
            } catch (Exception e) {
                Constants.LOGGER.error("encountered an exception loading '{}': {}", id, e);
            }
        });

        consume(entries.build());
    }

    protected abstract void consume(Map<ResourceLocation, T> entries);

}
