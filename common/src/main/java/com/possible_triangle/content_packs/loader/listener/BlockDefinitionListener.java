package com.possible_triangle.content_packs.loader.listener;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

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
            var blockKey = ResourceKey.create(Registry.BLOCK_REGISTRY, id);
            var block = definition.create();
            Registry.BLOCK.register(blockKey, block, Lifecycle.stable());

            definition.item().ifPresent(itemDefinition -> {
                var item = new BlockItem(block, new Item.Properties());
                var itemKey = ResourceKey.create(Registry.ITEM_REGISTRY, id);
                Registry.ITEM.register(itemKey, item, Lifecycle.stable());
            });
        });
    }
}
