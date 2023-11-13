package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import com.possible_triangle.content_packs.loader.definition.block.BlockFactory;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public abstract class BlockItemDefinitionType extends ItemDefinitionType {

    private static final Codec<BlockFactory> BLOCK_CODEC = Codec.either(Registry.BLOCK.byNameCodec(), BlockDefinition.CODEC).xmap(it ->
                    it.<BlockFactory>map(
                            block -> (r, id) -> block,
                            definition -> definition
                    ),
            factory -> {
                if (factory instanceof BlockDefinition definition) return Either.right(definition);
                return Either.left(factory.create(null, null));
            }
    );

    public static <T extends BlockItemDefinitionType> Products.P1<RecordCodecBuilder.Mu<T>, BlockFactory> commonCodec(RecordCodecBuilder.Instance<T> builder) {
        return builder.group(
                BLOCK_CODEC.fieldOf("block").forGetter(it -> it.block)
        );
    }

    protected final BlockFactory block;

    protected BlockItemDefinitionType(BlockFactory block) {
        this.block = block;
    }

    @Override
    public abstract Codec<? extends BlockItemDefinitionType> codec();

    protected abstract BlockItem create(ItemDefinition definition, Block block);

    @Override
    public final BlockItem create(RegistryEvent event, ResourceLocation id, ItemDefinition definition) {
        return create(definition, block.create(event, id));
    }

}
