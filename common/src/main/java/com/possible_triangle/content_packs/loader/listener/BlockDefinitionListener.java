package com.possible_triangle.content_packs.loader.listener;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class BlockDefinitionListener extends CodecDrivenReloadListener<BlockDefinition> {

    private final RegistryEvent event;

    public BlockDefinitionListener(RegistryAccess registryAccess, RegistryEvent event) {
        super("block", registryAccess);
        this.event = event;
    }

    @Override
    protected Codec<BlockDefinition> codec() {
        return BlockDefinition.CODEC;
    }

    @Override
    protected void consume(Map<ResourceLocation, BlockDefinition> entries) {
        entries.forEach((id, definition) -> {
            definition.register(event, id);
        });
    }
}
