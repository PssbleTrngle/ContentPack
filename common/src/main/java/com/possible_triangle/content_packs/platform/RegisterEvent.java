package com.possible_triangle.content_packs.platform;

import net.minecraft.resources.ResourceLocation;


@FunctionalInterface
public interface RegisterEvent<T> {

    void register(ResourceLocation id, T value);

}
