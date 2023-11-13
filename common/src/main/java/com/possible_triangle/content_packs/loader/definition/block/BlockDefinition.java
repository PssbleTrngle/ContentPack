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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BlockDefinition implements BlockFactory {

    public static final Codec<BlockDefinition> CODEC = ExtraCodecs.lazyInitializedCodec(() -> {
        return Registries.BLOCK_TYPES.byNameCodec().dispatchStable(BlockDefinition::codec, Function.identity());
    });

    public static <T extends BlockDefinition> Products.P1<RecordCodecBuilder.Mu<T>, Material> commonCodec(RecordCodecBuilder.Instance<T> builder) {
        return builder.group(
                MaterialCodec.CODEC.fieldOf("material").forGetter(it -> it.material)
        );
    }

    protected final Material material;

    protected BlockDefinition( Material material) {
        this.material = material;
    }

    abstract Codec<? extends BlockDefinition> codec();

    public abstract Block create(RegistryEvent event, ResourceLocation id, BlockDefinition definition);

    public BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of(material);
    }

    @Override
    public Block create(RegistryEvent event, ResourceLocation id) {
        Objects.requireNonNull(id);
        return this.register(event, id).get();
    }

    public final Supplier<Block> register(RegistryEvent event, ResourceLocation id) {
        return event.register(Registry.BLOCK_REGISTRY, id, () -> {
            Constants.LOGGER.debug("registering block with id {}", id);
            return create(event, id, this);
        });
    }

}
