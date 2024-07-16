package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.loader.ContentLoader;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.core.RegistryAccess;

import java.io.File;

public class ClientClass {

    private static final RegistryAccess REGISTRY_ACCESS = null;

    public static void clientInit(RegistryEvent event) {
        var minecraft = Minecraft.getInstance();
        var loader = new ContentLoader(minecraft, new File(minecraft.gameDirectory, CommonClass.PACK_FOLDER));

        var reload = CommonClass.registerAndLoad(loader, event, REGISTRY_ACCESS);
        minecraft.setOverlay(new LoadingOverlay(minecraft, reload, it -> it.ifPresent(error -> {
            Constants.LOGGER.error("an error occurred loading content packs", error);
        }), false));
        reload.done().join();
    }

}
