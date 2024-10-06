package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.Optional;

public record ItemProperties(Rarity rarity, Optional<Integer> stackSize, Optional<Integer> durablity,
                             boolean fireResistant, String creativeTab) {

    public static final ItemProperties DEFAULT = new ItemProperties(Rarity.COMMON, Optional.empty(), Optional.empty(), false, "search");

    public static final Codec<ItemProperties> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    RarityCodec.CODEC.optionalFieldOf("rarity", Rarity.COMMON).forGetter(it -> it.rarity),
                    Codec.INT.optionalFieldOf("stack_size").forGetter(it -> it.stackSize),
                    Codec.INT.optionalFieldOf("durablity").forGetter(it -> it.durablity),
                    Codec.BOOL.optionalFieldOf("fire_resistant", false).forGetter(it -> it.fireResistant),
                    Codec.STRING.optionalFieldOf("creative_tab", "search").forGetter(it -> it.creativeTab)
            ).apply(builder, ItemProperties::new)
    );

    public ResourceKey<CreativeModeTab> creativeTabKey() {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(creativeTab));
    }

    public Item.Properties create() {
        var properties = new Item.Properties()
                .rarity(rarity);

        stackSize.ifPresent(properties::stacksTo);
        durablity.ifPresent(properties::durability);
        if (fireResistant) properties.fireResistant();

        return properties;
    }

}
