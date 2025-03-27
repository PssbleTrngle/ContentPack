package com.possible_triangle.content_packs.mixin;

import net.minecraft.server.packs.PackType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

@Mixin(PackType.class)
public class PackTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static PackType[] $VALUES;

    private static final PackType CONTENT = packTypeExpansion$addVariant("content", PackType.SERVER_DATA);

    @Invoker("<init>")
    public static PackType packTypeExpansion$invokeInit(String internalName, int internalId, String directory) {
        throw new AssertionError();
    }

    @Unique
    private static PackType packTypeExpansion$addVariant(String directory, PackType type) {
        var variants = new ArrayList<>(Arrays.asList($VALUES));
        var  ordinal = variants.get(variants.size() - 1).ordinal() + 1;
        var created = packTypeExpansion$invokeInit(directory.toUpperCase(Locale.ROOT), ordinal, directory);
        variants.add(created);
        $VALUES = variants.toArray(new PackType[0]);
        return created;
    }

}
