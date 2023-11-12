package com.possible_triangle.content_packs;

import com.possible_triangle.content_packs.loader.definition.block.BasicBlockType;
import com.possible_triangle.content_packs.loader.definition.block.CogBlockType;
import com.possible_triangle.content_packs.loader.definition.item.BasicItemType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class ForgeEntrypoint {

    public ForgeEntrypoint() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommonClass::clientInit);
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> CommonClass::serverInit);

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((RegisterEvent event) -> {
            event.register(Registries.Keys.BLOCK_TYPES, registry -> {
                registry.register(new ResourceLocation("basic"), BasicBlockType.CODEC);
                registry.register(new ResourceLocation("create", "cog"), CogBlockType.CODEC);
            });
            event.register(Registries.Keys.ITEM_TYPES, registry -> {
                registry.register(new ResourceLocation("basic"), BasicItemType.CODEC);
            });
        });
    }
}