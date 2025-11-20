package com.possible_triangle.content_packs.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Lifecycle;
import com.possible_triangle.content_packs.loader.redirect.IntrusiveHolderOwner;
import com.possible_triangle.content_packs.loader.redirect.PendingIntrusiveHolders;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin<T> implements IntrusiveHolderOwner<T> {

    @Override
    public Map<T, Holder<T>> content_packs$getIntrusiveHolders() {
        var accessor = (MappedRegistryAccessor<T>) this;
        return accessor.getUnregisteredIntrusiveHolders();
    }

    @WrapOperation(
            method = {
                    "getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;",
                    "get(Lnet/minecraft/resources/ResourceKey;)Ljava/lang/Object;",
            },
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public Object applyKeyRedirects(Map<ResourceKey<T>, Holder<T>> instance, Object name, Operation<Holder<T>> original) {
        var key = (ResourceKey<T>) name;
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(instance, redirected);
    }

    @WrapOperation(
            method = "get(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;",
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public Object applyNameRedirects(Map<ResourceLocation, Holder<T>> instance, Object name, Operation<Holder<T>> original) {
        var self = (MappedRegistry<T>) (Object) this;
        var key = ResourceKey.create(self.key(), (ResourceLocation) name);
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(instance, redirected.location());
    }

    @WrapMethod(
            method = "containsKey(Lnet/minecraft/resources/ResourceLocation;)Z"
    )
    public boolean directContainsKey(ResourceLocation name, Operation<Boolean> original) {
        var self = (MappedRegistry<T>) (Object) this;
        var key = ResourceKey.create(self.key(), name);
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(redirected.location());
    }

    @WrapMethod(
            method = "containsKey(Lnet/minecraft/resources/ResourceKey;)Z"
    )
    public boolean directContainsKey(ResourceKey<T> key, Operation<Boolean> original) {
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(redirected);
    }

    @Inject(
            method = "registerMapping(ILnet/minecraft/resources/ResourceKey;Ljava/lang/Object;Lcom/mojang/serialization/Lifecycle;)Lnet/minecraft/core/Holder$Reference;",
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public void notifyRedirectedIntrusiveHolders(int id, ResourceKey<T> key, T value, Lifecycle lifecycle, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        PendingIntrusiveHolders.notify(key, this);
    }

}
