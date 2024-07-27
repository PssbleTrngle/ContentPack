package com.possible_triangle.content_packs.fabric.platform;

import com.possible_triangle.content_packs.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public <T> RegistryCodecSupplier<T> createRegistry(ResourceKey<Registry<T>> key) {
        Registry<T> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        return registry::byNameCodec;
    }

    @Override
    public void addToTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> supplier) {
        ItemGroupEvents.modifyEntriesEvent(tab)
                .register(entries -> entries.accept(supplier.get()));
    }
}
