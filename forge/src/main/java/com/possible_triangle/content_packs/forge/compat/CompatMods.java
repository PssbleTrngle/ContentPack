package com.possible_triangle.content_packs.forge.compat;

import net.minecraftforge.fml.ModList;

public enum CompatMods {

    CREATE("create"),
    BOTANIA("botania");

    public final String modid;

    CompatMods(String modid) {
        this.modid = modid;
    }

    public static void ifLoaded(CompatMods mod, Runnable runnable) {
        if(ModList.get().isLoaded(mod.modid)) {
            runnable.run();
        }
    }
}
