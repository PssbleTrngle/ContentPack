package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

    private static final Map<ResourceKey<? extends Registry<?>>, Map<ResourceLocation, ?>> CACHE = new HashMap<>();

    @SuppressWarnings("unchecked")
    private static <T> void save(ResourceKey<Registry<T>> registry, ResourceLocation id, T value) {
        var map = (Map<ResourceLocation, T>) CACHE.computeIfAbsent(registry, $ -> new WeakHashMap<ResourceLocation, T>());
        map.put(id, value);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getCached(ResourceKey<Registry<T>> registry, ResourceLocation id) {
        Constants.LOGGER.debug("using cache for " + id);
        return Optional.ofNullable(CACHE.get(registry))
                .map(map -> (Map<ResourceLocation, T>) map)
                .map(map -> map.get(id))
                .orElseThrow();
    }

    private static RegistryEvent createRegisterEvent(RegisterEvent event) {
        return new RegistryEvent() {
            @Override
            public <T> Supplier<T> register(ResourceKey<Registry<T>> registry, ResourceLocation id, Supplier<T> factory) {
                if (event.getRegistryKey().equals(registry)) {
                    var value = factory.get();
                    save(registry, id, value);
                    event.register(registry, id, () -> value);
                    return () -> value;
                } else {
                    return () -> getCached(registry, id);
                }
            }
        };
    }

    public ForgeEntrypoint() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        CommonClass.load();

        modBus.addListener((RegisterEvent event) -> {
            CommonClass.registerTypes(createRegisterEvent(event));
        });

        modBus.addListener(EventPriority.LOWEST, (RegisterEvent event) -> {
            var wrappedEvent = createRegisterEvent(event);
            // if (event.getRegistryKey().equals(Registries.Keys.ITEM_TYPES)) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CommonClass.clientInit(wrappedEvent));
            DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> CommonClass.serverInit(wrappedEvent));
            //}
        });
    }
}