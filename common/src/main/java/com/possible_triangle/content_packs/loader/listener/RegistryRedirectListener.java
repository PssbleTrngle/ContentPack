package com.possible_triangle.content_packs.loader.listener;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirectDefinition;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.Map;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

public class RegistryRedirectListener extends CodecDrivenReloadListener<RegistryRedirectDefinition> {

    private final RegistryRedirector redirector;

    public RegistryRedirectListener(RegistryAccess registryAccess, RegistryRedirector redirector) {
        super("redirect", registryAccess);
        this.redirector = redirector;
    }

    @Override
    protected Codec<RegistryRedirectDefinition> codec() {
        return RegistryRedirectDefinition.CODEC;
    }

    @Override
    protected RegistryRedirectDefinition merge(RegistryRedirectDefinition previous, RegistryRedirectDefinition next) {
        if (next.replace()) return next;
        var entries = new ImmutableMap.Builder<ResourceLocation, ResourceLocation>();
        entries.putAll(previous.entries());
        entries.putAll(next.entries());
        return new RegistryRedirectDefinition(previous.replace(), entries.build());
    }

    @Override
    protected void apply(Map<ResourceLocation, RegistryRedirectDefinition> entries, ResourceManager manager, ProfilerFiller profiler) {
        entries.forEach((registryId, definitions) -> {
            var registryKey = ResourceKey.createRegistryKey(registryId);

            definitions.entries().forEach((from, to) ->
                    redirector.register(registryKey, ResourceKey.create(registryKey, from), ResourceKey.create(registryKey, to))
            );

            Constants.LOGGER.info("loaded {} registry redirects for {}", definitions.entries().size(), registryId);
        });

        redirector.freeze();
    }

}
