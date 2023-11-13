package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.Registries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public abstract class BlockDefinitionType {

    public static final Codec<BlockDefinitionType> CODEC = ExtraCodecs.lazyInitializedCodec(() -> {
        return Registries.BLOCK_TYPES.byNameCodec().dispatchStable(BlockDefinitionType::codec, Function.identity());
    });

    abstract Codec<? extends BlockDefinitionType> codec();

    public abstract Block create(RegistryEvent event, ResourceLocation id, BlockDefinition definition);

}
