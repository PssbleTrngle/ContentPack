package com.possible_triangle.content_packs.loader.context;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.DelegatingOps;
import net.minecraft.util.ExtraCodecs;

public class DefinitionContextOps<T> extends DelegatingOps<T> {

    protected final DefinitionContext context;

    public DefinitionContextOps(DynamicOps<T> delegate, DefinitionContext context) {
        super(delegate);
        this.context = context;
    }


    public static MapCodec<DefinitionContext> retrieveContext() {
        return ExtraCodecs.retrieveContext(ops -> {
            if (ops instanceof DefinitionContextOps<?> contextOps) {
                return DataResult.success(contextOps.context);
            } else {
                return DataResult.error("Not a context ops");
            }
        });
    }

}
