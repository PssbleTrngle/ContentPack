package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.loader.definition.item.BasicItemType;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinition;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.Optional;

public record BlockDefinition(BlockDefinitionType type, Optional<ItemDefinition> item) {

    private static final ItemDefinition BASIC_ITEM_DEFINITION = new ItemDefinition(BasicItemType.INSTANCE);

    private static final Codec<Optional<ItemDefinition>> BLOCK_ITEM_CODEC = Codec.either(Codec.BOOL, ItemDefinition.CODEC).xmap(it ->
                    it.map(
                            shouldCreate -> shouldCreate ? Optional.of(BASIC_ITEM_DEFINITION) : Optional.empty(),
                            Optional::of
                    ),
            it -> it.<Either<Boolean, ItemDefinition>>map(Either::right).orElse(Either.left(false))
    );

    public static final Codec<BlockDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    BlockDefinitionType.CODEC.optionalFieldOf("type", BasicBlockType.INSTANCE).forGetter(BlockDefinition::type),
                    BLOCK_ITEM_CODEC.optionalFieldOf("item", Optional.empty()).forGetter(BlockDefinition::item)
            ).apply(builder, BlockDefinition::new)
    );

    private Material material() {
        return Material.STONE;
    }

    public BlockBehaviour.Properties properties() {
        return BlockBehaviour.Properties.of(material());
    }

    public Block create() {
        return type().create(this);
    }

}
