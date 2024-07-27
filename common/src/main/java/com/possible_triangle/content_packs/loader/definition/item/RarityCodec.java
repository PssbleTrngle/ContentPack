package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.Rarity;

import java.util.Locale;

public class RarityCodec {

    public static final Codec<Rarity> CODEC = Codec.STRING.flatXmap(key -> {
        try {
            var rarity = Rarity.valueOf(key);
            return DataResult.success(rarity);
        } catch (IllegalArgumentException ex) {
            return DataResult.error(() -> "unknown rarity '" + key + "'");
        }
    }, rarity -> DataResult.success(rarity.toString().toLowerCase(Locale.ROOT)));

}
