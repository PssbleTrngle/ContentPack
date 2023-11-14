package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.Registries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BlockDefinition implements BlockFactory {

    public static final Codec<BlockDefinition> CODEC = ExtraCodecs.lazyInitializedCodec(() -> {
        return Registries.BLOCK_TYPES.byNameCodec().dispatchStable(BlockDefinition::codec, Function.identity());
    });

    public static <T extends BlockDefinition> Products.P1<RecordCodecBuilder.Mu<T>, BlockProperties> commonCodec(RecordCodecBuilder.Instance<T> builder) {
        return builder.group(
                BlockProperties.CODEC.fieldOf("properties").forGetter(it -> it.properties)
        );
    }

    protected final BlockProperties properties;

    protected BlockDefinition(BlockProperties properties) {
        this.properties = properties;
    }

    public abstract Codec<? extends BlockDefinition> codec();

    protected abstract Block create(RegistryEvent event, ResourceLocation id);

    @Override
    public final Block createAndRegister(RegistryEvent event, ResourceLocation id) {
        Objects.requireNonNull(id);
        return this.register(event, id).get();
    }

    public final Supplier<Block> register(RegistryEvent event, ResourceLocation id) {
        return event.register(Registry.BLOCK_REGISTRY, id, () -> {
            Constants.LOGGER.debug("registering block with id {}", id);
            return create(event, id);
        });
    }

}
