package com.possible_triangle.content_packs.loader.listener;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinition;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ItemDefinitionListener extends CodecDrivenReloadListener<ItemDefinition> {

    public ItemDefinitionListener(RegistryAccess registryAccess) {
        super("item", registryAccess);
    }

    @Override
    protected Codec<ItemDefinition> codec() {
        return ItemDefinition.CODEC;
    }

    @Override
    protected void consume(Map<ResourceLocation, ItemDefinition> entries) {
        entries.forEach((id, definition) -> {
            definition.register(id);
        });
    }
}
