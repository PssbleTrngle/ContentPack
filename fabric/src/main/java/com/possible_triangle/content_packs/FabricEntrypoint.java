package com.possible_triangle.content_packs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class FabricEntrypoint implements DedicatedServerModInitializer, ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CommonClass.clientInit();
    }

    @Override
    public void onInitializeServer() {
        CommonClass.serverInit();
    }
}
