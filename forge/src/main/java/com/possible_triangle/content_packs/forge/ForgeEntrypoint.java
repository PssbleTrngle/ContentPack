package com.possible_triangle.content_packs.forge;

import com.possible_triangle.content_packs.CommonClass;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.forge.compat.create.CreateCompat;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

    private static RegistryEvent createRegisterEvent(RegisterEvent event) {
        return new RegistryEvent() {
            @Override
            public <T> Supplier<T> register(ResourceKey<Registry<T>> registry, ResourceLocation id, Supplier<T> factory) {
                if (event.getRegistryKey().equals(registry)) {
                    var value = factory.get();
                    event.register(registry, id, () -> value);
                    return () -> value;
                } else {
                    return () -> {
                        throw new IllegalStateException();
                    };
                }
            }
        };
    }

    private final ForgeRegistryCache cache = new ForgeRegistryCache();

    public ForgeEntrypoint() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        CommonClass.load();

        modBus.addListener((RegisterEvent event) -> {
            var wrapped = createRegisterEvent(event);
            CommonClass.registerTypes(wrapped);
            CreateCompat.init(wrapped);
        });

        modBus.addListener(EventPriority.LOWEST, (RegisterEvent event) -> {
            cache.register(event);
        });
    }
}