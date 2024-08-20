package com.possible_triangle.content_packs.forge;

import com.possible_triangle.content_packs.CommonClass;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.forge.compat.CompatMods;
import com.possible_triangle.content_packs.forge.compat.VSlabCompatCompat;
import com.possible_triangle.content_packs.forge.compat.botania.BotaniaCompat;
import com.possible_triangle.content_packs.forge.compat.create.CreateCompat;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Supplier;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

    private final ForgeRegistryCache cache = new ForgeRegistryCache();

    private RegistryEvent createRegisterEvent(RegisterEvent event) {
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

            @Override
            public void addToTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> supplier) {
                cache.addToTab(tab, supplier);
            }
        };
    }

    public ForgeEntrypoint() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        CompatMods.ifLoaded(CompatMods.CREATE, () -> CreateCompat.init(modBus));

        CommonClass.load();

        modBus.addListener((RegisterEvent event) -> {
            var wrapped = createRegisterEvent(event);
            CommonClass.registerTypes(wrapped);

            CompatMods.ifLoaded(CompatMods.CREATE, () -> CreateCompat.register(wrapped));
            CompatMods.ifLoaded(CompatMods.BOTANIA, () -> BotaniaCompat.register(wrapped));
            CompatMods.ifLoaded(CompatMods.V_SLAB, () -> VSlabCompatCompat.register(wrapped));
        });

        modBus.addListener(EventPriority.LOWEST, (RegisterEvent event) -> {
            cache.register(event);
        });

        modBus.addListener(cache::buildTabs);
    }
}