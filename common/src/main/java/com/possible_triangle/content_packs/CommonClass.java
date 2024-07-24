package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.loader.ContentLoader;
import com.possible_triangle.content_packs.loader.definition.block.BasicBlockType;
import com.possible_triangle.content_packs.loader.definition.block.SlabBlockType;
import com.possible_triangle.content_packs.loader.definition.block.StairBlockType;
import com.possible_triangle.content_packs.loader.definition.item.BasicBlockItemType;
import com.possible_triangle.content_packs.loader.definition.item.BasicItemType;
import com.possible_triangle.content_packs.loader.listener.BlockDefinitionListener;
import com.possible_triangle.content_packs.loader.listener.ItemDefinitionListener;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadInstance;

import java.io.File;

public class CommonClass {

    public static final String PACK_FOLDER = "contentpacks";

    //  private static final RegistryAccess REGISTRY_ACCESS = RegistryAccess.builtinCopy();
    private static final RegistryAccess REGISTRY_ACCESS = null;

    static {
        Registries.load();
    }

    static ReloadInstance registerAndLoad(ContentLoader loader, RegistryEvent event, RegistryAccess registryAccess) {
        Constants.LOGGER.info("loading content packs");

        // TODO call events
        loader.register(new BlockDefinitionListener(registryAccess, event));
        loader.register(new ItemDefinitionListener(registryAccess, event));

        var reload = loader.load();
        reload.done().thenRun(() -> {
            Constants.LOGGER.info("finished loading content packs");
        });
        return reload;
    }

    public static void serverInit(RegistryEvent event) {
        var loader = new ContentLoader(Util.backgroundExecutor(), new File(".", PACK_FOLDER));
        var reload = registerAndLoad(loader, event, REGISTRY_ACCESS);
        reload.done().join();
    }

    public static void registerTypes(RegistryEvent event) {
        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation("basic"), () -> BasicBlockType.CODEC);
        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation("slab"), () -> SlabBlockType.CODEC);
        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation("stairs"), () -> StairBlockType.CODEC);

        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation("basic"), () -> BasicItemType.CODEC);
        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation("block_item"), () -> BasicBlockItemType.CODEC);
    }

    public static void load() {
        // Loads this class
    }
}