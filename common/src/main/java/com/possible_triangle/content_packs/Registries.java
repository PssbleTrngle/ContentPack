package com.possible_triangle.content_packs;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinitionType;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinitionType;
import com.possible_triangle.content_packs.platform.Services;
import com.possible_triangle.content_packs.platform.services.IPlatformHelper.RegistryCodecSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class Registries {

    public static class Keys {
        public static final ResourceKey<Registry<Codec<? extends BlockDefinitionType>>> BLOCK_TYPES =
                ResourceKey.createRegistryKey(new ResourceLocation(Constants.MOD_ID, "block_type"));

        public static final ResourceKey<Registry<Codec<? extends ItemDefinitionType>>> ITEM_TYPES =
                ResourceKey.createRegistryKey(new ResourceLocation(Constants.MOD_ID, "block_type"));
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> codecClass() {
        return (Class<T>) Codec.class;
    }


    public static final RegistryCodecSupplier<Codec<? extends BlockDefinitionType>> BLOCK_TYPES = Services.PLATFORM.createRegistry(codecClass(), Keys.BLOCK_TYPES);
    public static final RegistryCodecSupplier<Codec<? extends ItemDefinitionType>> ITEM_TYPES = Services.PLATFORM.createRegistry(codecClass(), Keys.ITEM_TYPES);

    public static void load() {
        // Loads this class
    }

}