package com.possible_triangle.content_packs.fabric;

import com.possible_triangle.content_packs.ClientClass;
import com.possible_triangle.content_packs.CommonClass;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class FabricEntrypoint implements ModInitializer, DedicatedServerModInitializer, ClientModInitializer {

    private static RegistryEvent REGISTER_EVENT = new RegistryEvent() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> Supplier<T> register(ResourceKey<Registry<T>> registryKey, ResourceLocation id, Supplier<T> factory) {
            var registryRegistry = (Registry<Registry<T>>) BuiltInRegistries.REGISTRY;
            var registry = registryRegistry.getOrThrow(registryKey);
            var created = Registry.register(registry, id, factory.get());
            return () -> created;
        }

        @Override
        public void addToTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> supplier) {
            ItemGroupEvents.modifyEntriesEvent(tab)
                    .register(entries -> entries.accept(supplier.get()));
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
