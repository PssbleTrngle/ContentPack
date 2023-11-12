package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record ItemDefinition(ItemDefinitionType type) {

    public static final Codec<ItemDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ItemDefinitionType.CODEC.optionalFieldOf("type", BasicItemType.INSTANCE).forGetter(ItemDefinition::type)
            ).apply(builder, ItemDefinition::new)
    );

    public Item.Properties properties() {
        return new Item.Properties();
    }

    public Supplier<Item> register(ResourceLocation id) {
        Constants.LOGGER.debug("registered item with id {}", id);

        var key = ResourceKey.create(Registry.ITEM_REGISTRY, id);
        var holder = Registry.ITEM.register(key, type().create(id, this), Lifecycle.stable());
        return holder::value;
    }
}
