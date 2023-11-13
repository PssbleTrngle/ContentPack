package com.possible_triangle.content_packs.loader.listener;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinition;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ItemDefinitionListener extends CodecDrivenReloadListener<ItemDefinition> {

    private final RegistryEvent event;

    public ItemDefinitionListener(RegistryAccess registryAccess, RegistryEvent event) {
        super("item", registryAccess);
        this.event = event;
    }

    @Override
    protected Codec<ItemDefinition> codec() {
        return ItemDefinition.CODEC;
    }

    @Override
    protected void consume(Map<ResourceLocation, ItemDefinition> entries) {
        entries.forEach((id, definition) -> {
            definition.register(event, id);
        });
    }
}
