package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockBehaviour;

public record BlockProperties(
        float strength,
        int lightLevel,
        boolean collisions,
        boolean requiresCorrectToolForDrops
) {
    public BlockBehaviour.Properties create() {
        var properties = BlockBehaviour.Properties.of()
                .strength(strength)
                .lightLevel($ -> lightLevel);

        if (requiresCorrectToolForDrops) properties.requiresCorrectToolForDrops();
        if (!collisions) properties.noCollission();

        return properties;
    }


    public static final Codec<BlockProperties> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.FLOAT.fieldOf("strength").forGetter(BlockProperties::strength),
                    Codec.INT.optionalFieldOf("light", 0).forGetter(BlockProperties::lightLevel),
                    Codec.BOOL.optionalFieldOf("collision", true).forGetter(BlockProperties::collisions),
                    Codec.BOOL.optionalFieldOf("requires_correct_tool", false).forGetter(BlockProperties::requiresCorrectToolForDrops)
            ).apply(builder, BlockProperties::new)
    );
}

