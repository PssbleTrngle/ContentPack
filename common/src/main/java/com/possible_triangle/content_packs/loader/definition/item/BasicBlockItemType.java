package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.block.BlockFactory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class BasicBlockItemType extends BlockItemDefinitionType {

    public static final Codec<BasicBlockItemType> CODEC = RecordCodecBuilder.create(builder ->
            commonCodec(builder).apply(builder, BasicBlockItemType::new)
    );

    protected BasicBlockItemType(BlockFactory block) {
        super(block);
    }

    @Override
    public Codec<? extends BlockItemDefinitionType> codec() {
        return CODEC;
    }

    @Override
    public BlockItem create(ItemDefinition definition, Block block) {
        return new BlockItem(block, definition.properties());
    }

}
