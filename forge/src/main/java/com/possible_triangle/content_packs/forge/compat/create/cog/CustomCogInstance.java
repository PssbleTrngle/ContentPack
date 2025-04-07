package com.possible_triangle.content_packs.forge.compat.create.cog;


import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CustomCogInstance {

    private static final Map<Block, PartialModel> MODELS = new HashMap<>();

    public static SingleAxisRotatingVisual<BracketedKineticBlockEntity> create(VisualizationContext context, BracketedKineticBlockEntity tile, float partialTick) {
        var model = Models.partial(MODELS.get(tile.getBlockState().getBlock()));
        if (ICogWheel.isLargeCog(tile.getBlockState())) {
            return new Large(context, tile, partialTick, model);
        } else {
            return new SingleAxisRotatingVisual<>(context, tile, partialTick, model);
        }
    }

    public static void createModel(Block block, ResourceLocation id) {
        var path = id.withPrefix("block/");
        if (ICogWheel.isLargeCog(block)) path = path.withSuffix("_shaftless");
        var model = PartialModel.of(path);
        MODELS.putIfAbsent(block, model);
    }

    /**
     * Taken from {@link com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityVisual.LargeCogVisual}
     */
    public static class Large extends SingleAxisRotatingVisual<BracketedKineticBlockEntity> {
        protected final RotatingInstance additionalShaft;

        private Large(VisualizationContext context, BracketedKineticBlockEntity blockEntity, float partialTick, Model model) {
            super(context, blockEntity, partialTick, model);

            Direction.Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);

            additionalShaft = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(AllPartialModels.COGWHEEL_SHAFT))
                    .createInstance();

            additionalShaft.rotateToFace(axis)
                    .setup(blockEntity)
                    .setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos))
                    .setPosition(getVisualPosition())
                    .setChanged();
        }

        @Override
        public void update(float pt) {
            super.update(pt);
            additionalShaft.setup(blockEntity)
                    .setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(rotationAxis(), pos))
                    .setChanged();
        }

        @Override
        public void updateLight(float partialTick) {
            super.updateLight(partialTick);
            relight(additionalShaft);
        }

        @Override
        protected void _delete() {
            super._delete();
            additionalShaft.delete();
        }

        @Override
        public void collectCrumblingInstances(Consumer<Instance> consumer) {
            super.collectCrumblingInstances(consumer);
            consumer.accept(additionalShaft);
        }
    }

}