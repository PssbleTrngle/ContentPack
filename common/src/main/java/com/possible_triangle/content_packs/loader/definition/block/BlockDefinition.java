package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.Objects;
import java.util.function.Supplier;

public record BlockDefinition(BlockDefinitionType type) implements BlockFactory {

    public static final Codec<BlockDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    BlockDefinitionType.CODEC.optionalFieldOf("type", BasicBlockType.INSTANCE).forGetter(BlockDefinition::type)
            ).apply(builder, BlockDefinition::new)
    );

    private Material material() {
        return Material.STONE;
    }

    public BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of(material());
    }

    @Override
    public Block create(ResourceLocation id) {
        Objects.requireNonNull(id);
        return this.register(id).get();
    }

    public Supplier<Block> register(ResourceLocation id) {
        Constants.LOGGER.debug("registered block with id {}", id);

        var key = ResourceKey.create(Registry.BLOCK_REGISTRY, id);
        var holder = Registry.BLOCK.register(key, type().create(id, this), Lifecycle.stable());
        return holder::value;
    }

}
