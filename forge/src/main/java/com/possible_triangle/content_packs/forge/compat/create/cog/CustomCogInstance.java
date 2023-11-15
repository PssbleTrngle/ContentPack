package com.possible_triangle.content_packs.forge.compat.create.cog;


import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;

import java.util.function.Supplier;

public class CustomCogInstance extends BracketedKineticBlockEntityInstance {

    public CustomCogInstance(MaterialManager materialManager, BracketedKineticBlockEntity tile) {
        super(materialManager, tile);
    }

    private PoseStack getRotation() {
        PoseStack stack = new PoseStack();
        var direction = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE).getOpposite();
        var transform = TransformStack.cast(stack).centre();
        transform.rotateToFace(direction);
        transform.unCentre();

        return stack;
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        var state = getRenderedBlockState();
        Supplier<BakedModel> model = () -> Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
        return getRotatingMaterial().model(Pair.of(axis, state), () -> BlockModel.of(model.get(), state, getRotation()));
    }
}