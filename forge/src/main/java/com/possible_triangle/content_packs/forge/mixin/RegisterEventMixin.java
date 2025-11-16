package com.possible_triangle.content_packs.forge.mixin;

import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RegisterEvent.class, remap = false)
public class RegisterEventMixin {

    @Inject(
            method = "register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/resources/ResourceLocation;Ljava/util/function/Supplier;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public <T> void applyForgeRedirects(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation id, Supplier<T> valueSupplier, CallbackInfo ci) {
        var key = ResourceKey.create(registryKey, id);
        if (RegistryRedirector.INSTANCE.get(key).isPresent()) {
            ci.cancel();
        }
    }

}
