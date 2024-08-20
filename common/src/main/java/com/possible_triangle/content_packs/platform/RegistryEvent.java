package com.possible_triangle.content_packs.platform;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public interface RegistryEvent {

    <T> Supplier<T> register(ResourceKey<Registry<T>> registry, ResourceLocation id, Supplier<T> factory);

    void addToTab(ResourceKey<CreativeModeTab> tab, Supplier<ItemStack> supplier);

}
