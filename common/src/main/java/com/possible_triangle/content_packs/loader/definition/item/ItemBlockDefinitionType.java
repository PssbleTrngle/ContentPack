package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Registries;
import net.minecraft.core.Registry;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public abstract class ItemBlockDefinitionType extends ItemDefinitionType {

    public <T> Codec<? extends BlockItem> commonCodec(RecordCodecBuilder.Instance<T> builder) {
        builder.group(
                Registry.BLOCK.byNameCodec().fieldOf("block").forGetter(it -> it.block)
        )
    };

    private final Block block;

    public ItemBlockDefinitionType(Block block) {
        this.block = block;
    }

    @Override
    public final Codec<? extends ItemDefinitionType> codec() {
        return factoryCodec();
    }

    public abstract Codec<Function<Block, ? extends ItemBlockDefinitionType>> factoryCodec();

}
