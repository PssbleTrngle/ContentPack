package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.LazyCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockBehaviour;

@FunctionalInterface
public interface BlockPropertiesFactory {

    Codec<BlockPropertiesFactory> CODEC = Codec.either(LazyCodecs.byNameCodec(BuiltInRegistries.BLOCK), BlockProperties.CODEC).xmap(it ->
                    it.<BlockPropertiesFactory>map(
                            block -> () -> BlockBehaviour.Properties.copy(block.get()),
                            properties -> properties
                    ),
            factory -> {
                if (factory instanceof BlockProperties definition) return Either.right(definition);
                throw new IllegalStateException("Cannot serialize block properties factory");
            }
    );

    BlockBehaviour.Properties create();

}
