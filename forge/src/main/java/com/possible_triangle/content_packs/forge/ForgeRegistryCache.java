package com.possible_triangle.content_packs.forge;

import com.possible_triangle.content_packs.ClientClass;
import com.possible_triangle.content_packs.CommonClass;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ForgeRegistryCache implements RegistryEvent {

    public static class FactoryEntry<T, R extends T> implements Supplier<R> {

        public final ResourceKey<Registry<T>> registry;
        public final ResourceLocation id;
        private final Supplier<R> factory;

        @Nullable
        private R value;

        public FactoryEntry(ResourceKey<Registry<T>> registry, ResourceLocation id, Supplier<R> factory) {
            this.registry = registry;
            this.id = id;
            this.factory = factory;
        }

        void register(RegisterEvent event) {
            this.value = factory.get();
            event.register(registry, id, () -> value);
        }

        @Override
        public R get() {
            return Objects.requireNonNull(value, () -> id + " has not been registered yet");
        }
    }

    private final Set<FactoryEntry<?, ?>> factories = new HashSet<>();
    private final Map<ResourceKey<CreativeModeTab>, Collection<Supplier<ItemStack>>> tabs = new HashMap<>();

    private boolean loaded;

    public void register(RegisterEvent event) {
        var registry = event.getRegistryKey().location();
        if (registry.getNamespace().equals(Constants.MOD_ID)) return;

        if (!loaded) load();
        synchronized (factories) {
            factories.forEach(it -> {
                it.register(event);
            });
        }
    }

    public void buildTabs(BuildCreativeModeTabContentsEvent event) {
        synchronized (tabs) {
            var items = tabs.get(event.getTabKey());
            if (items != null) items.forEach(it -> event.accept(it.get()));
        }
    }

    @Override
    public <T, R extends T> Supplier<R> register(ResourceKey<Registry<T>> registry, ResourceLocation id, Supplier<R> factory) {
        var entry = new FactoryEntry<>(registry, id, factory::get);
        synchronized (factories) {
            factories.add(entry);
        }
        return entry;
    }

    @Override
    public void addToTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> supplier) {
        synchronized (tabs) {
            tabs.computeIfAbsent(tab, $ -> new ArrayList<>()).add(supplier);
        }
    }

    private void load() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientClass.clientInit(this));
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> CommonClass.serverInit(this));
        loaded = true;
    }

}
