package com.possible_triangle.content_packs.loader.definition.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.world.level.material.MapColor;

public class MapColorCodec {

    private static final BiMap<String, net.minecraft.world.level.material.MapColor> LOOKUP = new ImmutableBiMap.Builder<String, MapColor>()
            .put("none", MapColor.NONE)
            .put("grass", MapColor.GRASS)
            .put("sand", MapColor.SAND)
            .put("wool", MapColor.WOOL)
            .put("fire", MapColor.FIRE)
            .put("ice", MapColor.ICE)
            .put("metal", MapColor.METAL)
            .put("plant", MapColor.PLANT)
            .put("snow", MapColor.SNOW)
            .put("clay", MapColor.CLAY)
            .put("dirt", MapColor.DIRT)
            .put("stone", MapColor.STONE)
            .put("water", MapColor.WATER)
            .put("wood", MapColor.WOOD)
            .put("quartz", MapColor.QUARTZ)
            .put("color_orange", MapColor.COLOR_ORANGE)
            .put("color_magenta", MapColor.COLOR_MAGENTA)
            .put("color_light_blue", MapColor.COLOR_LIGHT_BLUE)
            .put("color_yellow", MapColor.COLOR_YELLOW)
            .put("color_light_green", MapColor.COLOR_LIGHT_GREEN)
            .put("color_pink", MapColor.COLOR_PINK)
            .put("color_gray", MapColor.COLOR_GRAY)
            .put("color_light_gray", MapColor.COLOR_LIGHT_GRAY)
            .put("color_cyan", MapColor.COLOR_CYAN)
            .put("color_purple", MapColor.COLOR_PURPLE)
            .put("color_blue", MapColor.COLOR_BLUE)
            .put("color_brown", MapColor.COLOR_BROWN)
            .put("color_green", MapColor.COLOR_GREEN)
            .put("color_red", MapColor.COLOR_RED)
            .put("color_black", MapColor.COLOR_BLACK)
            .put("gold", MapColor.GOLD)
            .put("diamond", MapColor.DIAMOND)
            .put("lapis", MapColor.LAPIS)
            .put("emerald", MapColor.EMERALD)
            .put("podzol", MapColor.PODZOL)
            .put("nether", MapColor.NETHER)
            .put("terracotta_white", MapColor.TERRACOTTA_WHITE)
            .put("terracotta_orange", MapColor.TERRACOTTA_ORANGE)
            .put("terracotta_magenta", MapColor.TERRACOTTA_MAGENTA)
            .put("terracotta_light_blue", MapColor.TERRACOTTA_LIGHT_BLUE)
            .put("terracotta_yellow", MapColor.TERRACOTTA_YELLOW)
            .put("terracotta_light_green", MapColor.TERRACOTTA_LIGHT_GREEN)
            .put("terracotta_pink", MapColor.TERRACOTTA_PINK)
            .put("terracotta_gray", MapColor.TERRACOTTA_GRAY)
            .put("terracotta_light_gray", MapColor.TERRACOTTA_LIGHT_GRAY)
            .put("terracotta_cyan", MapColor.TERRACOTTA_CYAN)
            .put("terracotta_purple", MapColor.TERRACOTTA_PURPLE)
            .put("terracotta_blue", MapColor.TERRACOTTA_BLUE)
            .put("terracotta_brown", MapColor.TERRACOTTA_BROWN)
            .put("terracotta_green", MapColor.TERRACOTTA_GREEN)
            .put("terracotta_red", MapColor.TERRACOTTA_RED)
            .put("terracotta_black", MapColor.TERRACOTTA_BLACK)
            .put("crimson_nylium", MapColor.CRIMSON_NYLIUM)
            .put("crimson_stem", MapColor.CRIMSON_STEM)
            .put("crimson_hyphae", MapColor.CRIMSON_HYPHAE)
            .put("warped_nylium", MapColor.WARPED_NYLIUM)
            .put("warped_stem", MapColor.WARPED_STEM)
            .put("warped_hyphae", MapColor.WARPED_HYPHAE)
            .put("warped_wart_block", MapColor.WARPED_WART_BLOCK)
            .put("deepslate", MapColor.DEEPSLATE)
            .put("raw_iron", MapColor.RAW_IRON)
            .put("glow_lichen", MapColor.GLOW_LICHEN)
            .build();

    public static final Codec<MapColor> CODEC = Codec.STRING.flatXmap(key -> {
        var mapColor = LOOKUP.get(key);
        if(mapColor  != null) return DataResult.success(mapColor);
        return DataResult.error(() -> String.format("Unknown map color with name %s".formatted(key)));
    }, mapColor ->  {
        var key = LOOKUP.inverse().get(mapColor);
        if(key  != null) return DataResult.success(key);
        return DataResult.error(() -> "custom map colors are not support yet");
    });

}
