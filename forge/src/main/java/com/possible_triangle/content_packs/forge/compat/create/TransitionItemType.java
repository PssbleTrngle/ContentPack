package com.possible_triangle.content_packs.forge.compat.create;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinition;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class TransitionItemType extends ItemDefinition {

    public static final Codec<TransitionItemType> CODEC = RecordCodecBuilder.create(builder ->
            commonCodec(builder).apply(builder, TransitionItemType::new)
    );


    protected TransitionItemType(Rarity rarity) {
        super(rarity);
    }

    @Override
    public Codec<? extends ItemDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Item create(RegistryEvent event, ResourceLocation id) {
        return new SequencedAssemblyItem(properties());
    }
}
