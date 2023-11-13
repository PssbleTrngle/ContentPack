package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.block.BlockFactory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;

public class BasicBlockItemType extends BlockItemDefinition {

    public static final Codec<BasicBlockItemType> CODEC = RecordCodecBuilder.create(builder ->
            blockItemCodec(builder).apply(builder, BasicBlockItemType::new)
    );

    protected BasicBlockItemType(Rarity rarity, BlockFactory block) {
        super(rarity, block);
    }

    @Override
    public Codec<? extends BlockItemDefinition> codec() {
        return CODEC;
    }

    @Override
    public BlockItem create(Block block) {
        return new BlockItem(block, properties());
    }

}
