package com.possible_triangle.content_packs.forge.compat.botania;

import com.possible_triangle.content_packs.ModRegistries;
import com.possible_triangle.content_packs.forge.compat.CompatMods;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;

public class BotaniaCompat {

    public static void register(RegistryEvent event) {
        event.register(ModRegistries.Keys.ITEM_TYPES, new ResourceLocation(CompatMods.BOTANIA.modid, "rune"), () -> RuneItemType.CODEC);
    }

}
