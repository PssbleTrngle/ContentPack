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