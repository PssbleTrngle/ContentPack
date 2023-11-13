package com.possible_triangle.content_packs.loader.definition.item;

import com.mojang.serialization.Codec;
import com.possible_triangle.content_packs.MapPromotionCodec;
import com.possible_triangle.content_packs.Registries;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public abstract class ItemDefinitionType {

    public static final Codec<ItemDefinitionType> CODEC = ExtraCodecs.lazyInitializedCodec(() -> new MapPromotionCodec<>(
            Registries.ITEM_TYPES.byNameCodec()
                    .dispatchStable(ItemDefinitionType::codec, Function.identity())
    ));

    public abstract Codec<? extends ItemDefinitionType> codec();

    public abstract Item create(RegistryEvent event, ResourceLocation id, ItemDefinition definition);

}
