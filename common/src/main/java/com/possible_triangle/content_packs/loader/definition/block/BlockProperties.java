package com.possible_triangle.content_packs.loader.definition.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.Optional;

public record BlockProperties(
        float strength,
        float explosionResistance,
        int lightLevel,
        boolean collisions,
        boolean requiresCorrectToolForDrops,
        Optional<SoundType> sound,
        Optional<MapColor> mapColor,
        Optional<NoteBlockInstrument> instrument
        ) implements BlockPropertiesFactory {

    @Override
    public BlockBehaviour.Properties create() {
        var properties = BlockBehaviour.Properties.of()
                .strength(explosionResistance < 0 ? strength : explosionResistance, strength)
                .lightLevel($ -> lightLevel);

        if (requiresCorrectToolForDrops) properties.requiresCorrectToolForDrops();
        if (!collisions) properties.noCollission();
        sound.ifPresent(properties::sound);
        mapColor.ifPresent(properties::mapColor);
        instrument.ifPresent(properties::instrument);

        return properties;
    }

    public static final Codec<BlockProperties> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.FLOAT.fieldOf("strength").forGetter(BlockProperties::strength),
                    Codec.FLOAT.optionalFieldOf("explosion_resistance", -1F).forGetter(BlockProperties::explosionResistance),
                    Codec.INT.optionalFieldOf("light", 0).forGetter(BlockProperties::lightLevel),
                    Codec.BOOL.optionalFieldOf("collision", true).forGetter(BlockProperties::collisions),
                    Codec.BOOL.optionalFieldOf("requires_correct_tool", false).forGetter(BlockProperties::requiresCorrectToolForDrops),
                    SoundTypeCodec.CODEC.optionalFieldOf("sound").forGetter(BlockProperties::sound),
                    MapColorCodec.CODEC.optionalFieldOf("map_color").forGetter(BlockProperties::mapColor),
                    StringRepresentable.fromEnum(NoteBlockInstrument::values).optionalFieldOf("instrument").forGetter(BlockProperties::instrument)
            ).apply(builder, BlockProperties::new)
    );
}

