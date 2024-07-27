package com.possible_triangle.content_packs.forge.compat.create;

import com.possible_triangle.content_packs.Constants;
import com.possible_triangle.content_packs.ModRegistries;
import com.possible_triangle.content_packs.forge.compat.CompatMods;
import com.possible_triangle.content_packs.forge.compat.create.cog.CogBlockType;
import com.possible_triangle.content_packs.forge.compat.create.cog.CogItemType;
import com.possible_triangle.content_packs.forge.compat.create.cog.CustomCogInstance;
import com.possible_triangle.content_packs.platform.RegistryEvent;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;

public class CreateCompat {


    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(Constants.MOD_ID);

    private static final BlockEntityBuilder<BracketedKineticBlockEntity, ?> CUSTOM_COGWHEEL_BUILDER = REGISTRATE
            .blockEntity("cogwheel", BracketedKineticBlockEntity::new)
            .instance(() -> CustomCogInstance::new, false)
            .renderer(() -> BracketedKineticBlockEntityRenderer::new);

    public static final BlockEntityEntry<BracketedKineticBlockEntity> CUSTOM_COGWHEEL = CUSTOM_COGWHEEL_BUILDER.register();

    public static void init(IEventBus modBus) {
        REGISTRATE.registerEventListeners(modBus);
    }

    public static void register(RegistryEvent event) {
        event.register(ModRegistries.Keys.BLOCK_TYPES, new ResourceLocation(CompatMods.CREATE.modid, "cog"), () -> CogBlockType.CODEC);
        event.register(ModRegistries.Keys.ITEM_TYPES, new ResourceLocation(CompatMods.CREATE.modid, "cog"), () -> CogItemType.CODEC);
        event.register(ModRegistries.Keys.ITEM_TYPES, new ResourceLocation(CompatMods.CREATE.modid, "transition_item"), () -> TransitionItemType.CODEC);
    }

    public static void registerCogwheel(Block block, ResourceLocation id) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            CreateClient.MODEL_SWAPPER.getCustomBlockModels().register(id, BracketedKineticBlockModel::new);
        });

        CUSTOM_COGWHEEL_BUILDER.validBlock(() -> block);
    }

}
