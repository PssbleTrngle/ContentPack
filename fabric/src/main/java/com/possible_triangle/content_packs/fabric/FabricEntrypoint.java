package com.possible_triangle.content_packs.fabric;

import com.possible_triangle.content_packs.ClientClass;
import com.possible_triangle.content_packs.CommonClass;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FabricEntrypoint implements ModInitializer, DedicatedServerModInitializer, ClientModInitializer {

    private static RegistryEvent REGISTER_EVENT = new RegistryEvent() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> Supplier<T> register(ResourceKey<Registry<T>> registryKey, ResourceLocation id, Supplier<T> factory) {
            var registryRegistry = (Registry<Registry<T>>) Registry.REGISTRY;
            var registry = registryRegistry.getOrThrow(registryKey);
            var created = Registry.register(registry, id, factory.get());
            return () -> created;
        }
    };

    @Override
    public void onInitialize() {
        CommonClass.registerTypes(REGISTER_EVENT);
    }

    @Override
    public void onInitializeClient() {
        ClientClass.clientInit(REGISTER_EVENT);
    }

    @Override
    public void onInitializeServer() {
        CommonClass.serverInit(REGISTER_EVENT);
    }
}
