package com.possible_triangle.content_packs.world.block;

import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;

import java.util.Objects;

public class CustomKelpPlantBlock extends KelpPlantBlock {

    private GrowingPlantHeadBlock headBlock;

    public CustomKelpPlantBlock(Properties properties) {
        super(properties);
    }

    public void setHeadBlock(GrowingPlantHeadBlock headBlock) {
        this.headBlock = headBlock;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return Objects.requireNonNull(headBlock);
    }

}
