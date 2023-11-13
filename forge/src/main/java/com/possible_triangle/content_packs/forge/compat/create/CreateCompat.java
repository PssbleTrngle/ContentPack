package com.possible_triangle.content_packs.forge.compat.create;

import com.possible_triangle.content_packs.Registries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class CreateCompat {

    private static final String NAMESPACE = "create";

    public static void init(RegistryEvent event) {
        if (!ModList.get().isLoaded(NAMESPACE)) return;

        event.register(Registries.Keys.BLOCK_TYPES, new ResourceLocation(NAMESPACE, "cog"), () -> CogBlockType.CODEC);
        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation(NAMESPACE, "cog"), () -> CogItemType.CODEC);
        event.register(Registries.Keys.ITEM_TYPES, new ResourceLocation(NAMESPACE, "transition_item"), () -> TransitionItemType.CODEC);
    }

}
