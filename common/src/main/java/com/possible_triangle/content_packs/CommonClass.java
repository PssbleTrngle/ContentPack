package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.loader.ContentLoader;
import com.possible_triangle.content_packs.loader.definition.block.BasicBlockType;
import com.possible_triangle.content_packs.loader.definition.block.CogBlockType;
import com.possible_triangle.content_packs.loader.definition.item.BasicBlockItemType;
import com.possible_triangle.content_packs.loader.definition.item.BasicItemType;
import com.possible_triangle.content_packs.loader.listener.BlockDefinitionListener;
import com.possible_triangle.content_packs.loader.listener.ItemDefinitionListener;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import java.io.File;

public class CommonClass {

    private static final String PACK_FOLDER = "contentpacks";

  //  private static final RegistryAccess REGISTRY_ACCESS = RegistryAccess.builtinCopy();
    private static final RegistryAccess REGISTRY_ACCESS = null;

    static {
        Registries.load();
    }

    private static void registerAndLoad(ContentLoader loader, RegistryAccess registryAccess) {
        Constants.LOGGER.info("loading contentpacks");

        // TODO call events
        loader.register(new BlockDefinitionListener(registryAccess));
        loader.register(new ItemDefinitionListener(registryAccess));
        loader.load();
    }

    public static void serverInit() {
        var loader = new ContentLoader(Util.backgroundExecutor(), new File(".", PACK_FOLDER));
        registerAndLoad(loader, REGISTRY_ACCESS);
    }

    public static void clientInit() {
        var minecraft = Minecraft.getInstance();
        var loader = new ContentLoader(minecraft, new File(minecraft.gameDirectory, PACK_FOLDER));
        registerAndLoad(loader, REGISTRY_ACCESS);
    }

    public static void registerTypes(RegistryEvent event) {
        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation("basic"), BasicBlockType.CODEC);
        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation("create", "cog"), CogBlockType.CODEC);

        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation("basic"), BasicItemType.CODEC);
        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation("block_item"), BasicBlockItemType.CODEC);
    }

    public static void load() {
        // Loads this class
    }
}