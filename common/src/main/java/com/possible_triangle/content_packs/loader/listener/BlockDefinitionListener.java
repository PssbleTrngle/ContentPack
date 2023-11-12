package com.possible_triangle.content_packs.loader.listener;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class BlockDefinitionListener extends CodecDrivenReloadListener<BlockDefinition> {

    public BlockDefinitionListener(RegistryAccess registryAccess) {
        super("block", registryAccess);
    }

    @Override
    protected Codec<BlockDefinition> codec() {
        return BlockDefinition.CODEC;
    }

    @Override
    protected void consume(Map<ResourceLocation, BlockDefinition> entries) {
        entries.forEach((id, definition) -> {
            definition.register(id);
        });
    }
}
