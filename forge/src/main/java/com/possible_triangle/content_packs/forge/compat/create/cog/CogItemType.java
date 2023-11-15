package com.possible_triangle.content_packs.forge.compat.create.cog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.block.BlockFactory;
import com.possible_triangle.content_packs.loader.definition.item.BlockItemDefinition;
import com.possible_triangle.content_packs.loader.definition.item.ItemProperties;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class CogItemType extends BlockItemDefinition {

    public static final Codec<CogItemType> CODEC = RecordCodecBuilder.create(builder ->
            blockItemCodec(builder).apply(builder, CogItemType::new)
    );

    protected CogItemType(ItemProperties properties, BlockFactory block) {
        super(properties, block);
    }

    @Override
    public Codec<? extends BlockItemDefinition> codec() {
        return CODEC;
    }

    @Override
    protected BlockItem create(Block block) {
        if (block instanceof CogWheelBlock cog) {
            return new CogwheelBlockItem(cog, properties.create());
        } else {
            throw new IllegalArgumentException("CogItemType requires a cogwheel block");
        }
    }

}
