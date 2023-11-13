package com.possible_triangle.content_packs.loader.definition.block;

import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface BlockFactory {

    Block create(RegistryEvent event, ResourceLocation id);

}
