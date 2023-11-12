package com.possible_triangle.content_packs.platform;

import com.possible_triangle.content_packs.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public <T> RegistryCodecSupplier<T> createRegistry(Class<T> clazz, ResourceKey<Registry<T>> key) {
        Registry<T> registry = FabricRegistryBuilder.createSimple(clazz, key.location()).buildAndRegister();
        return registry::byNameCodec;
    }
}
