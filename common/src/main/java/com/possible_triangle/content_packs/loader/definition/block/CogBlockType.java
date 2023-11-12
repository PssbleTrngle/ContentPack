package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CogBlockType extends BlockDefinitionType {

    public static final Codec<CogBlockType> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.BOOL.optionalFieldOf("large", false).forGetter(it -> it.large)
            ).apply(builder, CogBlockType::new)
    );

    private final boolean large;

    public CogBlockType(boolean large) {
        this.large = large;
    }

    @Override
    public Codec<? extends BlockDefinitionType> codec() {
        return CODEC;
    }

    @Override
    public Block create(ResourceLocation id, BlockDefinition definition) {
        // TODO
        return new Block(definition.properties());
    }

}
