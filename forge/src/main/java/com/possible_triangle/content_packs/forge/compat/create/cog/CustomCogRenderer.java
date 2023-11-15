package com.possible_triangle.content_packs.forge.compat.create.cog;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class CustomCogRenderer extends BracketedKineticBlockEntityRenderer {

    public CustomCogRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(BracketedKineticBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (Backend.canUseInstancing(be.getLevel())) return;

        BlockState state = getRenderedBlockState(be);
        RenderType type = getRenderType(be, state);
        var model = getRotatedModel(be, state);

        var base = standardKineticRotationTransform(model, be, light);
        //var rotated = switch (getRotationAxisOf(be)) {
        //    case X -> base.rotateCentered(Direction.NORTH, (float) (Math.PI / 2F));
        //    case Z -> base.rotateCentered(Direction.EAST, (float) (Math.PI / 2F));
        //    case Y -> base;
        //};

        base.renderInto(ms, buffer.getBuffer(type));
    }


}
