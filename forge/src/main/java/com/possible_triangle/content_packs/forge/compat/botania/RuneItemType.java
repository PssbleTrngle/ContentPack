package com.possible_triangle.content_packs.forge.compat.botania;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinition;
import com.possible_triangle.content_packs.loader.definition.item.ItemProperties;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import vazkii.botania.common.item.material.RuneItem;

public class RuneItemType extends ItemDefinition {

    public static final Codec<RuneItemType> CODEC = RecordCodecBuilder.create(builder ->
            commonCodec(builder).apply(builder, RuneItemType::new)
    );

    protected RuneItemType(ItemProperties properties) {
        super(properties);
    }

    @Override
    public Codec<? extends ItemDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Item create(RegistryEvent event, ResourceLocation id) {
        return new RuneItem(properties.create());
    }
}
