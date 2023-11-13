package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record ItemDefinition(ItemDefinitionType type) {

    public static final Codec<ItemDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ItemDefinitionType.CODEC.fieldOf("type").forGetter(ItemDefinition::type)
            ).apply(builder, ItemDefinition::new)
    );

    public Item.Properties properties() {
        return new Item.Properties();
    }

    public Supplier<Item> register(RegistryEvent event, ResourceLocation id) {
        return event.register(Registry.ITEM_REGISTRY, id, () -> {
            Constants.LOGGER.debug("registering item with id {}", id);
            return this.type().create(event, id, this);
        });
    }
}
