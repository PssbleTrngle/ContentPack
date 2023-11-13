package com.possible_triangle.content_packs.forge.compat.botania;

import com.possible_triangle.content_packs.Registries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class BotaniaCompat {

    private static final String NAMESPACE = "botania";

    public static void init(RegistryEvent event) {
        if (!ModList.get().isLoaded(NAMESPACE)) return;

        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation(NAMESPACE, "rune"), () -> RuneItemType.CODEC);
    }

}
