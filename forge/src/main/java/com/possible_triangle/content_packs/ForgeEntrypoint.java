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

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

    public ForgeEntrypoint() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        CommonClass.load();

        modBus.addListener((RegisterEvent event) -> {
            CommonClass.registerTypes(new RegistryEvent() {
                @Override
                public <T> void register(ResourceKey<Registry<T>> registry, ResourceLocation id, T value) {
                    event.register(registry, id, () -> value);
                }
            });
        });

        modBus.addListener(EventPriority.LOWEST, (RegisterEvent event) -> {
            if(!event.getRegistryKey().equals(Registry.BIOME_REGISTRY)) return;
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommonClass::clientInit);
            DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> CommonClass::serverInit);
        });
    }
}