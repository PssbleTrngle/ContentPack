package com.possible_triangle.content_packs.fabric.platform;

import com.possible_triangle.content_packs.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public <T> RegistryCodecSupplier<T> createRegistry(Class<T> clazz, ResourceKey<Registry<T>> key) {
        Registry<T> registry = FabricRegistryBuilder.createSimple(clazz, key.location()).buildAndRegister();
        return registry::byNameCodec;
    }

    @Override
    public Pack.PackConstructor createPackConstructor() {
        return (name, component, b, supplier, packMetadataSection, position, packSource) ->
                new Pack(name, component, b, supplier, packMetadataSection, PackType.SERVER_DATA, position, packSource);
    }
}
