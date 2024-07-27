package com.possible_triangle.content_packs.forge.compat;

import com.possible_triangle.content_packs.ModRegistries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;

public class VSlabCompatCompat {

    public static void register(RegistryEvent event) {
        event.register(ModRegistries.Keys.BLOCK_TYPES, new ResourceLocation(CompatMods.V_SLAB.modid, "vertical_slab"), () -> VerticalSlabBlockType.CODEC);
    }

}
