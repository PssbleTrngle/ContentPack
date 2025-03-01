package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public record BlockProperties(
        Material material,
        float strength,
        int lightLevel,
        boolean collisions,
        boolean requiresCorrectToolForDrops
) {
    public BlockBehaviour.Properties create() {
        var properties = BlockBehaviour.Properties.of(material)
                .strength(strength)
                .lightLevel($ -> lightLevel);

        if (requiresCorrectToolForDrops) properties.requiresCorrectToolForDrops();
        if (!collisions) properties.noCollission();

        return properties;
    }


    public static final Codec<BlockProperties> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    MaterialCodec.CODEC.fieldOf("material").forGetter(BlockProperties::material),
                    Codec.FLOAT.fieldOf("strength").forGetter(BlockProperties::strength),
                    Codec.INT.optionalFieldOf("light", 0).forGetter(BlockProperties::lightLevel),
                    Codec.BOOL.optionalFieldOf("collision", true).forGetter(BlockProperties::collisions),
                    Codec.BOOL.optionalFieldOf("requires_correct_tool", false).forGetter(BlockProperties::requiresCorrectToolForDrops)
            ).apply(builder, BlockProperties::new)
    );
}

