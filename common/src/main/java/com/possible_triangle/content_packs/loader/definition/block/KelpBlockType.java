package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import com.possible_triangle.content_packs.world.block.CustomKelpBlock;
import com.possible_triangle.content_packs.world.block.CustomKelpPlantBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class KelpBlockType extends BlockDefinition {

    public static final Codec<KelpBlockType> CODEC = RecordCodecBuilder.create(builder ->
            commonCodec(builder).apply(builder, KelpBlockType::new)
    );

    protected KelpBlockType(BlockPropertiesFactory properties) {
        super(properties);
    }

    @Override
    public Codec<? extends BlockDefinition> codec() {
        return CODEC;
    }

    @Override
    protected Block create(RegistryEvent event, ResourceLocation id) {
        var plantBlock = event.register(Registries.BLOCK, id.withSuffix("_plant"), () -> new CustomKelpPlantBlock(properties.create())).get();
        var block = new CustomKelpBlock(properties.create(), plantBlock);
        plantBlock.setHeadBlock(block);
        return block;
    }

}
