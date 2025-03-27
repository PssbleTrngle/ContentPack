package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.ModRegistries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ItemDefinition {

    public static final Codec<ItemDefinition> CODEC = ExtraCodecs.lazyInitializedCodec(() ->
            ModRegistries.ITEM_TYPES.byNameCodec().dispatchStable(ItemDefinition::codec, Function.identity())
    );

    public static <T extends ItemDefinition> Products.P1<RecordCodecBuilder.Mu<T>, ItemProperties> commonCodec(RecordCodecBuilder.Instance<T> builder) {
        return builder.group(
                ItemProperties.CODEC.optionalFieldOf("properties", ItemProperties.DEFAULT).forGetter(it -> it.properties)
        );
    }

    protected final ItemProperties properties;

    protected ItemDefinition(ItemProperties properties) {
        this.properties = properties;
    }

    public abstract Codec<? extends ItemDefinition> codec();

    protected abstract Item create(RegistryEvent event, ResourceLocation id);

    public final Supplier<Item> register(RegistryEvent event, ResourceLocation id) {
        return event.register(Registries.ITEM, id, () -> {
            Constants.LOGGER.debug("registering item with id {}", id);
            var registered = create(event, id);
            event.addToTab(properties.creativeTabKey(), () -> new ItemStack(registered));
            return registered;
        });
    }
}
