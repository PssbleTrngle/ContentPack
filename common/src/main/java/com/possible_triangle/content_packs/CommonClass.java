package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.loader.ContentLoader;
import com.possible_triangle.content_packs.loader.listener.BlockDefinitionListener;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;

import java.io.File;

public class CommonClass {

    private static final String PACK_FOLDER = "contentpacks";

    static {
        Registries.load();
    }

    private static void registerAndLoad(ContentLoader loader, RegistryAccess registryAccess) {
        Constants.LOGGER.info("loading contentpacks");

        // TODO call events
        loader.register(new BlockDefinitionListener(registryAccess));
        loader.load();
    }

    public static void serverInit() {
        var loader = new ContentLoader(Util.backgroundExecutor(), new File(".", PACK_FOLDER));
        registerAndLoad(loader, RegistryAccess.builtinCopy());
    }

    public static void clientInit() {
        var minecraft = Minecraft.getInstance();
        var loader = new ContentLoader(minecraft, new File(minecraft.gameDirectory, PACK_FOLDER));
        registerAndLoad(loader, RegistryAccess.builtinCopy());
    }

}