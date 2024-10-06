package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockBehaviour;

@FunctionalInterface
public interface BlockPropertiesFactory {

    Codec<BlockPropertiesFactory> CODEC = Codec.either(BuiltInRegistries.BLOCK.byNameCodec(), BlockProperties.CODEC).xmap(it ->
                    it.<BlockPropertiesFactory>map(
                            block -> () -> BlockBehaviour.Properties.copy(block),
                            properties -> properties
                    ),
            factory -> {
                if (factory instanceof BlockProperties definition) return Either.right(definition);
                throw new IllegalStateException("Cannot serialize block properties factory");
            }
    );

    BlockBehaviour.Properties create();

}
