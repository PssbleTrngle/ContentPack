package com.possible_triangle.content_packs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

import java.util.stream.Stream;

public class MapPromotionCodec<T> implements Codec<T> {

    private final Codec<T> inner;

    public MapPromotionCodec(Codec<T> inner) {
        this.inner = inner;
    }

    @Override
    public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
        var map = ops.createMap(Stream.of(new Pair<>(ops.createString("type"), input)));
        return inner.decode(ops, map);
    }

    @Override
    public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
        throw new IllegalStateException("encoding not supported yet");
    }

}
