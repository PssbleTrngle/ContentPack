package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.Objects;
import java.util.function.Supplier;

public record BlockDefinition(BlockDefinitionType type, Material material) implements BlockFactory {

    public static final Codec<BlockDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    BlockDefinitionType.CODEC.optionalFieldOf("type", BasicBlockType.INSTANCE).forGetter(BlockDefinition::type),
                    MaterialCodec.CODEC.fieldOf("material").forGetter(BlockDefinition::material)
            ).apply(builder, BlockDefinition::new)
    );

    public BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of(material());
    }

    @Override
    public Block create(RegistryEvent event, ResourceLocation id) {
        Objects.requireNonNull(id);
        return this.register(event, id).get();
    }

    public Supplier<Block> register(RegistryEvent event, ResourceLocation id) {
        return event.register(Registry.BLOCK_REGISTRY, id, () -> {
            Constants.LOGGER.debug("registering block with id {}", id);
            return type().create(event, id, this);
        });
    }

}
