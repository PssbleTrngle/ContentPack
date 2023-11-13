package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BasicBlockType extends BlockDefinition {

    public static final Codec<BasicBlockType> CODEC = RecordCodecBuilder.create(builder ->
        commonCodec(builder).apply(builder, BasicBlockType::new)
    );

    protected BasicBlockType(Material material) {
        super(material);
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    public Block create(RegistryEvent event, ResourceLocation id, BlockDefinition definition) {
        return new Block(definition.properties());
    }

}
