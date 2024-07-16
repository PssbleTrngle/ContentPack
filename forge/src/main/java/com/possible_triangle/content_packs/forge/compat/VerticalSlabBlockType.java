package com.possible_triangle.content_packs.forge.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import com.possible_triangle.content_packs.loader.definition.block.BlockProperties;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.mehvahdjukaar.moonlight.api.block.VerticalSlabBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class VerticalSlabBlockType extends BlockDefinition {

    public static final Codec<VerticalSlabBlockType> CODEC = RecordCodecBuilder.create(builder ->
        commonCodec(builder).apply(builder, VerticalSlabBlockType::new)
    );

    protected VerticalSlabBlockType(BlockProperties properties) {
        super(properties);
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Block create(RegistryEvent event, ResourceLocation id) {
        return new VerticalSlabBlock(properties.create());
    }

}
