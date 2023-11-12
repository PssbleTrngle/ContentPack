package com.possible_triangle.content_packs.loader.listener;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.possible_triangle.content_packs.Constants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public abstract class CodecDrivenReloadListener<T> extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().create();
    private final RegistryAccess registryAccess;

    public CodecDrivenReloadListener(String path, RegistryAccess registryAccess) {
        super(GSON, path);
        this.registryAccess = registryAccess;
    }

    protected abstract Codec<T> codec();

    @Override
    protected final void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager manager, ProfilerFiller profiler) {
        var ops = RegistryOps.create(JsonOps.INSTANCE, this.registryAccess);
        var codec = codec();

        var entries = new ImmutableMap.Builder<ResourceLocation, T>();

        elements.forEach((id, json) -> {
            var result = codec.parse(ops, json);
            var value = result.getOrThrow(false, Constants.LOGGER::error);
            entries.put(id, value);
        });

        consume(entries.build());
    }

    protected abstract void consume(Map<ResourceLocation, T> entries);

}