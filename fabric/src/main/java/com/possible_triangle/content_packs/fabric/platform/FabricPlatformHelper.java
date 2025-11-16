package com.possible_triangle.content_packs.fabric.platform;

import com.possible_triangle.content_packs.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public <T> RegistryCodecSupplier<T> createRegistry(ResourceKey<Registry<T>> key) {
        Registry<T> registry = FabricRegistryBuilder.createSimple(key).buildAndRegister();
        return registry::byNameCodec;
    }

}
