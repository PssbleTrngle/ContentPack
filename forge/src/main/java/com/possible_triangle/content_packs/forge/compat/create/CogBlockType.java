package com.possible_triangle.content_packs.forge.compat.create;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import com.possible_triangle.content_packs.loader.definition.block.BlockProperties;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CogBlockType extends BlockDefinition {

    public static final Codec<CogBlockType> CODEC = RecordCodecBuilder.create(builder ->
            commonCodec(builder)
                    .and(Codec.BOOL.optionalFieldOf("large", false).forGetter(it -> it.large)
            ).apply(builder, CogBlockType::new)
    );

    private final boolean large;

    public CogBlockType(BlockProperties properties, boolean large) {
        super(properties);
        this.large = large;
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Block create(RegistryEvent event, ResourceLocation id) {
        return large ? CogWheelBlock.large(properties.create()) : CogWheelBlock.small(properties.create());
    }

}
