package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class StairBlockType extends BlockDefinition {

    public static final Codec<StairBlockType> CODEC = RecordCodecBuilder.create(builder ->
            commonCodec(builder)
                    .and(BlockState.CODEC.optionalFieldOf("base", Blocks.STONE.defaultBlockState()).forGetter(it -> it.base))
                    .apply(builder, StairBlockType::new)
    );

    private final BlockState base;

    protected StairBlockType(BlockProperties properties, BlockState base) {
        super(properties);
        this.base = base;
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Block create(RegistryEvent event, ResourceLocation id) {
        return new ModStairBlock(base, properties.create());
    }

    private static class ModStairBlock extends StairBlock {
        public ModStairBlock(BlockState base, Properties properties) {
            super(base, properties);
        }
    }

}
