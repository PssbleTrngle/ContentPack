package com.possible_triangle.content_packs.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin<T> {

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @WrapOperation(
            method = {
                    "getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;",
                    "get(Lnet/minecraft/resources/ResourceKey;)Ljava/lang/Object;",
            },
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public Holder<T> applyKeyRedirects(Map<ResourceKey<T>, Holder<T>> instance, ResourceKey<T> key, Operation<Holder<T>> original) {
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(instance, redirected);
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @WrapOperation(
            method = "get(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;",
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public Holder<T> applyNameRedirects(Map<ResourceLocation, Holder<T>> instance, ResourceLocation name, Operation<Holder<T>> original) {
        var self = (MappedRegistry<T>) (Object) this;
        var key = ResourceKey.create(self.key(), name);
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(instance, redirected.location());
    }

}
