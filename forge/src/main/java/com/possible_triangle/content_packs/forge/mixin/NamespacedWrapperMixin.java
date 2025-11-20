package com.possible_triangle.content_packs.forge.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.content_packs.loader.redirect.IntrusiveHolderOwner;
import com.possible_triangle.content_packs.loader.redirect.PendingIntrusiveHolders;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraftforge.registries.NamespacedWrapper", remap = false)
public abstract class NamespacedWrapperMixin<T> {

    @Inject(
            method = "onAdded",
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public void notifyRedirectedIntrusiveHolders(RegistryManager stage, int id, ResourceKey<T> key, T newValue, T oldValue, CallbackInfoReturnable<Holder.Reference<T>> cir) {
        PendingIntrusiveHolders.notify(key, (IntrusiveHolderOwner<T>) this);
    }

    @WrapOperation(
            method = {
                    "getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;",
            },
            at = @At(
                    value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public Object applyKeyRedirects(Map<ResourceLocation, Holder<T>> instance, Object name, Operation<Holder<T>> original) {
        var self = (Registry<T>) this;
        var key = ResourceKey.create(self.key(), (ResourceLocation) name);
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(instance, redirected.location());
    }

}
