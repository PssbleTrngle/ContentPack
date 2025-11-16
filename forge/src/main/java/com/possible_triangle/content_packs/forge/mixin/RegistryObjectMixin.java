package com.possible_triangle.content_packs.forge.mixin;

import com.google.common.base.Suppliers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = RegistryObject.class, remap = false)
public class RegistryObjectMixin<T> {

    @Unique
    private final Supplier<Optional<ResourceKey<T>>> content_packs$redirecting = Suppliers.memoize(this::content_packs$loadRedirect);

    @Unique
    private Optional<ResourceKey<T>> content_packs$loadRedirect() {
        @SuppressWarnings("unchecked")
        var self = (RegistryObject<T>) (Object) this;
        if (self.getKey() == null) return Optional.empty();
        return RegistryRedirector.INSTANCE.get(self.getKey());
    }

    @WrapOperation(
            method = {
                    "updateReference(Lnet/minecraftforge/registries/IForgeRegistry;)V",
                    "updateReference(Lnet/minecraft/core/Registry;)V",
            },
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraftforge/registries/RegistryObject;name:Lnet/minecraft/resources/ResourceLocation;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public ResourceLocation applyKeyRedirects(RegistryObject<T> instance, Operation<ResourceLocation> original) {
        return content_packs$redirecting.get()
                .map(ResourceKey::location)
                .orElseGet(() -> original.call(instance));
    }

    @WrapOperation(
            method = {
                    "updateReference(Lnet/minecraftforge/registries/IForgeRegistry;)V",
                    "updateReference(Lnet/minecraft/core/Registry;)V",
            },
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraftforge/registries/RegistryObject;key:Lnet/minecraft/resources/ResourceKey;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public ResourceKey<T> applyNameRedirects(RegistryObject<T> instance, Operation<ResourceKey<T>> original) {
        return content_packs$redirecting.get()
                .orElseGet(() -> original.call(instance));
    }

}
