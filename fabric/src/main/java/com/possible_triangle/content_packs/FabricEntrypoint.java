package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FabricEntrypoint implements ModInitializer, DedicatedServerModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.registerTypes(new RegistryEvent() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> void register(ResourceKey<Registry<T>> registryKey, ResourceLocation id, T value) {
                var registryRegistry = (Registry<Registry<T>>) Registry.REGISTRY;
                var registry = registryRegistry.getOrThrow(registryKey);
                Registry.register(registry, id, value);
            }
        });
    }

    @Override
    public void onInitializeClient() {
        CommonClass.clientInit();
    }

    @Override
    public void onInitializeServer() {
        CommonClass.serverInit();
    }
}
