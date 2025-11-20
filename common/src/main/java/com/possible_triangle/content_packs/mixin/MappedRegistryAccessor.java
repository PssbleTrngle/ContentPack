package com.possible_triangle.content_packs.mixin;

import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MappedRegistry.class)
public interface MappedRegistryAccessor<T> {

    @Accessor
    Map<T, Holder<T>> getUnregisteredIntrusiveHolders();

}
