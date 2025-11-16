package com.possible_triangle.content_packs.loader.redirect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public record RegistryRedirectDefinition(boolean replace, Map<ResourceLocation, ResourceLocation> entries) {

    public static final Codec<RegistryRedirectDefinition> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.BOOL.optionalFieldOf("replace", false).forGetter(RegistryRedirectDefinition::replace),
                    Codec.unboundedMap(ResourceLocation.CODEC, ResourceLocation.CODEC).fieldOf("entries").forGetter(RegistryRedirectDefinition::entries)
            ).apply(builder, RegistryRedirectDefinition::new)
    );

}
