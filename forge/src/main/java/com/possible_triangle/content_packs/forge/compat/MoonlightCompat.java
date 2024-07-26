package com.possible_triangle.content_packs.forge.compat;

import com.possible_triangle.content_packs.Registries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;

public class MoonlightCompat {

    public static void register(RegistryEvent event) {
        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation(CompatMods.MOONLIGHT.modid, "vertical_slab"), () -> VerticalSlabBlockType.CODEC);
    }

}
