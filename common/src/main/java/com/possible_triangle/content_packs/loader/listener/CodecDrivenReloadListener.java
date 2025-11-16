package com.possible_triangle.content_packs.loader.listener;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.possible_triangle.content_packs.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

public abstract class CodecDrivenReloadListener<T> extends SimplePreparableReloadListener<Map<ResourceLocation, T>> {

    private static final Gson GSON = new GsonBuilder().create();
    private final FileToIdConverter idConverter;
    private final RegistryAccess registryAccess;

    protected CodecDrivenReloadListener(String path, RegistryAccess registryAccess) {
        this.idConverter = FileToIdConverter.json(path);
        this.registryAccess = registryAccess;
    }

    protected abstract Codec<T> codec();

    @Override
    protected final Map<ResourceLocation, T> prepare(ResourceManager manager, ProfilerFiller profiler) {
        var ops = Optional.ofNullable(registryAccess)
                .<DynamicOps<JsonElement>>map(it -> RegistryOps.create(JsonOps.INSTANCE, it))
                .orElse(JsonOps.INSTANCE);

        var codec = codec();

        var entries = new HashMap<ResourceLocation, T>();

        var resources = idConverter.listMatchingResources(manager);

        resources.forEach((file, resource) -> {
            var id = idConverter.fileToId(file);

            try (var reader = resource.openAsReader()) {
                var json = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                var result = codec.parse(ops, json).resultOrPartial(Constants.LOGGER::error);
                result.ifPresent(value -> {
                    var previous = entries.get(id);
                    if (previous != null) value = merge(previous, value);
                    entries.put(id, value);
                });
            } catch (Exception e) {
                Constants.LOGGER.error("encountered an exception loading '{}': {}", id, e);
            }
        });

        return ImmutableMap.copyOf(entries);
    }

    protected T merge(T previous, T next) {
        return next;
    }

}
