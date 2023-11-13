package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BasicBlockType extends BlockDefinitionType {

    public static final BasicBlockType INSTANCE = new BasicBlockType();

    public static final Codec<BasicBlockType> CODEC = Codec.unit(INSTANCE);

    @Override
    public Codec<? extends BlockDefinitionType> codec() {
        return CODEC;
    }

    @Override
    public Block create(RegistryEvent event, ResourceLocation id, BlockDefinition definition) {
        return new Block(definition.properties());
    }

}
