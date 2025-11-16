package com.possible_triangle.content_packs.loader.redirect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class RegistryRedirector {

    public static final RegistryRedirector INSTANCE = new RegistryRedirector();

    private final Map<ResourceLocation, Map<ResourceLocation, ResourceLocation>> entries = new HashMap<>();
    private boolean frozen = false;

    public void freeze() {
        if (frozen) throw new IllegalStateException("registry redirector has already been frozen");
        frozen = true;
    }

    private void checkFrozen() {
        if (frozen) throw new IllegalStateException("cannot register redirects after content packs have loaded");
    }

    private <T> Map<ResourceLocation, ResourceLocation> entriesFor(ResourceKey<? extends Registry<T>> registry) {
        return entries.computeIfAbsent(registry.location(), $ -> new HashMap<>());
    }

    public <T> Optional<ResourceKey<T>> get(ResourceKey<T> from) {
        var registry = ResourceKey.<T>createRegistryKey(from.registry());
        return Optional.ofNullable(entriesFor(registry).get(from.location()))
                .map(it -> ResourceKey.create(registry, it));
    }

    public <T> void register(ResourceKey<Registry<T>> registry, ResourceKey<T> from, ResourceKey<T> to) {
        checkFrozen();
        entriesFor(registry).put(from.location(), to.location());
    }

}
