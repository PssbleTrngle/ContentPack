package com.possible_triangle.content_packs.loader.definition.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface BlockFactory {

    Block create(ResourceLocation id);

}
