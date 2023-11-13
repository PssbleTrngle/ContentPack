package com.possible_triangle.content_packs.loader.definition.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.world.level.material.Material;

public class MaterialCodec {

    private static final BiMap<String, Material> LOOKUP = new ImmutableBiMap.Builder<String, Material>()
            .put("air", Material.AIR)
            .put("structural_air", Material.STRUCTURAL_AIR)
            .put("portal", Material.PORTAL)
            .put("cloth_decoration", Material.CLOTH_DECORATION)
            .put("plant", Material.PLANT)
            .put("water_plant", Material.WATER_PLANT)
            .put("replaceable_plant", Material.REPLACEABLE_PLANT)
            .put("replaceable_fireproof_plant", Material.REPLACEABLE_FIREPROOF_PLANT)
            .put("replaceable_water_plant", Material.REPLACEABLE_WATER_PLANT)
            .put("water", Material.WATER)
            .put("bubble_column", Material.BUBBLE_COLUMN)
            .put("lava", Material.LAVA)
            .put("top_snow", Material.TOP_SNOW)
            .put("fire", Material.FIRE)
            .put("decoration", Material.DECORATION)
            .put("web", Material.WEB)
            .put("sculk", Material.SCULK)
            .put("buildable_glass", Material.BUILDABLE_GLASS)
            .put("clay", Material.CLAY)
            .put("dirt", Material.DIRT)
            .put("grass", Material.GRASS)
            .put("ice_solid", Material.ICE_SOLID)
            .put("sand", Material.SAND)
            .put("sponge", Material.SPONGE)
            .put("shulker_shell", Material.SHULKER_SHELL)
            .put("wood", Material.WOOD)
            .put("nether_wood", Material.NETHER_WOOD)
            .put("bamboo_sapling", Material.BAMBOO_SAPLING)
            .put("bamboo", Material.BAMBOO)
            .put("wool", Material.WOOL)
            .put("explosive", Material.EXPLOSIVE)
            .put("leaves", Material.LEAVES)
            .put("glass", Material.GLASS)
            .put("ice", Material.ICE)
            .put("cactus", Material.CACTUS)
            .put("stone", Material.STONE)
            .put("metal", Material.METAL)
            .put("snow", Material.SNOW)
            .put("heavy_metal", Material.HEAVY_METAL)
            .put("barrier", Material.BARRIER)
            .put("piston", Material.PISTON)
            .put("moss", Material.MOSS)
            .put("vegetable", Material.VEGETABLE)
            .put("egg", Material.EGG)
            .put("cake", Material.CAKE)
            .put("amethyst", Material.AMETHYST)
            .put("powder_snow", Material.POWDER_SNOW)
            .put("frogspawn", Material.FROGSPAWN)
            .put("froglight", Material.FROGLIGHT)
            .build();

    public static final Codec<Material> CODEC = Codec.STRING.flatXmap(key -> {
        var material = LOOKUP.get(key);
        if(material  != null) return DataResult.success(material);
        return DataResult.error(String.format("Unknown material with name %s".formatted(key)));
    }, material ->  {
        var key = LOOKUP.inverse().get(material);
        if(key  != null) return DataResult.success(key);
        return DataResult.error("custom materials are not support yet");
    });

}
