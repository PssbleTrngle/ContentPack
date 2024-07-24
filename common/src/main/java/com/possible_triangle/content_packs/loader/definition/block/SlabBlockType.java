package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

public class SlabBlockType extends BlockDefinition {

    public static final Codec<SlabBlockType> CODEC = RecordCodecBuilder.create(builder ->
        commonCodec(builder).apply(builder, SlabBlockType::new)
    );

    protected SlabBlockType(BlockProperties properties) {
        super(properties);
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Block create(RegistryEvent event, ResourceLocation id) {
        return new SlabBlock(properties.create());
    }

}
