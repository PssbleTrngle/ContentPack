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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;

public abstract class BlockItemDefinition extends ItemDefinition {

    private static final Codec<BlockFactory> BLOCK_CODEC = Codec.either(Registry.BLOCK.byNameCodec(), BlockDefinition.CODEC).xmap(it ->
                    it.<BlockFactory>map(
                            block -> (r, id) -> block,
                            definition -> definition
                    ),
            factory -> {
                if (factory instanceof BlockDefinition definition) return Either.right(definition);
                return Either.left(factory.createAndRegister(null, null));
            }
    );

    public static <T extends BlockItemDefinition> Products.P2<RecordCodecBuilder.Mu<T>, Rarity, BlockFactory> blockItemCodec(RecordCodecBuilder.Instance<T> builder) {
        return commonCodec(builder).and(BLOCK_CODEC.fieldOf("block").forGetter(it -> it.block));
    }

    protected final BlockFactory block;

    protected BlockItemDefinition(Rarity rarity, BlockFactory block) {
        super(rarity);
        this.block = block;
    }

    @Override
    public abstract Codec<? extends BlockItemDefinition> codec();

    protected abstract BlockItem create(Block block);

    @Override
    protected final BlockItem create(RegistryEvent event, ResourceLocation id) {
        return create(block.createAndRegister(event, id));
    }

}
