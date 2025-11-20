package com.possible_triangle.content_packs.forge.mixin;

import com.google.common.collect.BiMap;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.possible_triangle.content_packs.loader.redirect.IntrusiveHolderOwner;
import com.possible_triangle.content_packs.loader.redirect.RegistryRedirector;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ForgeRegistry.class, remap = false)
public abstract class ForgeRegistryMixin<T> implements IntrusiveHolderOwner<T> {

    @Override
    public Map<T, Holder<T>> content_packs$getIntrusiveHolders() {
        var self = (ForgeRegistry<T>) (Object) this;
        var slaveId = new ResourceLocation("forge", "registry_defaulted_wrapper");
        var wrapper = (IntrusiveHolderOwner<T>) self.getSlaveMap(slaveId, MappedRegistry.class);
        return wrapper.content_packs$getIntrusiveHolders();
    }

    @WrapOperation(
            method = {
                    "getValue(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;",
                    "getRaw(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;",
            },
            at = @At(
                    value = "INVOKE", target = "Lcom/google/common/collect/BiMap;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    public Object redirectGetValue(BiMap<ResourceKey<T>, Holder<T>> instance, Object name, Operation<Holder<T>> original) {
        var self = (IForgeRegistry<T>) this;
        var key = ResourceKey.create(self.getRegistryKey(), (ResourceLocation) name);
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(instance, redirected.location());
    }

    @WrapMethod(
            method = "containsKey(Lnet/minecraft/resources/ResourceLocation;)Z"
    )
    public boolean directContainsKey(ResourceLocation name, Operation<Boolean> original) {
        var self = (IForgeRegistry<T>) this;
        var key = ResourceKey.create(self.getRegistryKey(), name);
        var redirected = RegistryRedirector.INSTANCE.get(key).orElse(key);
        return original.call(redirected.location());
    }

}
