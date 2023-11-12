package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;

public record ItemDefinition(ItemDefinitionType type) {

    public static final Codec<ItemDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ItemDefinitionType.CODEC.optionalFieldOf("type", BasicItemType.INSTANCE).forGetter(ItemDefinition::type)
            ).apply(builder, ItemDefinition::new)
    );

    public Item.Properties properties() {
        return new Item.Properties();
    }

    public Item create() {
        return type().create(this);
    }
}
