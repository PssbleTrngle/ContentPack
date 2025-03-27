package com.possible_triangle.content_packs;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.loader.definition.block.BlockDefinition;
import com.possible_triangle.content_packs.loader.definition.item.ItemDefinition;
import com.possible_triangle.content_packs.platform.Services;
import com.possible_triangle.content_packs.platform.services.IPlatformHelper.RegistryCodecSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;

public class ModRegistries {

    public static class Keys {
        public static final ResourceKey<Registry<Codec<? extends BlockDefinition>>> BLOCK_TYPES =
                ResourceKey.createRegistryKey(new ResourceLocation(Constants.MOD_ID, "block_type"));

        public static final ResourceKey<Registry<Codec<? extends ItemDefinition>>> ITEM_TYPES =
                ResourceKey.createRegistryKey(new ResourceLocation(Constants.MOD_ID, "item_type"));

        public static Collection<ResourceLocation> getPriorityKeys() {
            return List.of(BLOCK_TYPES.location(), ITEM_TYPES.location());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> codecClass() {
        return (Class<T>) Codec.class;
    }


    public static final RegistryCodecSupplier<Codec<? extends BlockDefinition>> BLOCK_TYPES = Services.PLATFORM.createRegistry(Keys.BLOCK_TYPES);
    public static final RegistryCodecSupplier<Codec<? extends ItemDefinition>> ITEM_TYPES = Services.PLATFORM.createRegistry(Keys.ITEM_TYPES);

    public static void load() {
        // Loads this class
    }

}