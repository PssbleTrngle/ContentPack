package com.possible_triangle.content_packs.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.KelpBlock;

public class CustomKelpBlock extends KelpBlock {

    private final Block plantBlock;

    public CustomKelpBlock(Properties properties, Block plantBlock) {
        super(properties);
        this.plantBlock = plantBlock;
    }

    @Override
    protected Block getBodyBlock() {
        return plantBlock;
    }

}
