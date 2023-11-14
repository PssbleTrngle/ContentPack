package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class BasicItemType extends ItemDefinition {

    public static final Codec<BasicItemType> CODEC = RecordCodecBuilder.create(builder ->
        ItemDefinition.commonCodec(builder).apply(builder, BasicItemType::new)
    );

    protected BasicItemType(ItemProperties properties) {
        super(properties);
    }

    @Override
    public Codec<? extends ItemDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Item create(RegistryEvent event, ResourceLocation id) {
        return new Item(properties.create());
    }

}
