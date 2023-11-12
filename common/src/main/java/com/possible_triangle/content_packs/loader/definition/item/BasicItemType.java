package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class BasicItemType extends ItemDefinitionType {

    public static final BasicItemType INSTANCE = new BasicItemType();

    public static final Codec<BasicItemType> CODEC = Codec.unit(INSTANCE);

    @Override
    public Codec<? extends ItemDefinitionType> codec() {
        return CODEC;
    }

    @Override
    public Item create(ResourceLocation id, ItemDefinition definition) {
        return new Item(definition.properties());
    }

}
