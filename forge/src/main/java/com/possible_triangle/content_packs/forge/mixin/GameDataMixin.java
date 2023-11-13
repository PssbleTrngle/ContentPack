package com.possible_triangle.content_packs.forge.mixin;

import com.possible_triangle.content_packs.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.GameData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.LinkedHashSet;

@Mixin(GameData.class)
public class GameDataMixin {

    @Redirect(
            method = "postRegisterEvents()V",
            at = @At(value = "NEW", target = "(Ljava/util/Collection;)Ljava/util/LinkedHashSet;")
    )
    private static LinkedHashSet<ResourceLocation> injected(Collection<ResourceLocation> vanillaRegistries) {
        var set = new LinkedHashSet<>(Registries.Keys.getPriorityKeys());
        set.addAll(vanillaRegistries);
        return set;
    }

}
