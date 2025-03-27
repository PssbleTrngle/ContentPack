package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BasicBlockType extends BlockDefinition {

    public static final Codec<BasicBlockType> CODEC = RecordCodecBuilder.create(builder ->
        commonCodec(builder).apply(builder, BasicBlockType::new)
    );

    protected BasicBlockType(BlockPropertiesFactory properties) {
        super(properties);
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Block create(RegistryEvent event, ResourceLocation id) {
        return new Block(properties.create());
    }

}
