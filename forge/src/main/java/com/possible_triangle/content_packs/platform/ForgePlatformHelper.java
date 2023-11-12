package com.possible_triangle.content_packs.platform;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Objects;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    private static class RegistryDelegate<T> implements RegistryCodecSupplier<T> {

        private Supplier<IForgeRegistry<T>> registry;
        public RegistryDelegate(RegistryBuilder<T> builder) {
            var modBus = FMLJavaModLoadingContext.get().getModEventBus();
            modBus.addListener((NewRegistryEvent event) -> {
                registry = event.create(builder);
            });
        }

        @Override
        public Codec<T> byNameCodec() {
            return Objects.requireNonNull(registry).get().getCodec();
        }
    }

    @Override
    public <T> RegistryCodecSupplier<T> createRegistry(Class<T> clazz, ResourceKey<Registry<T>> key) {
        var builder = new RegistryBuilder<T>().setName(key.location());
        return new RegistryDelegate<>(builder);
    }

    @Override
    public Pack.PackConstructor createPackConstructor() {
        return (name, component, b, supplier, packMetadataSection, position, packSource, hidden) ->
                new Pack(name, component, b, supplier, packMetadataSection, PackType.SERVER_DATA, position, packSource, hidden);
    }
}
