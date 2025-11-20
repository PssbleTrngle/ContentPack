package com.possible_triangle.content_packs.loader.redirect;

import java.util.Map;
import net.minecraft.core.Holder;

public interface IntrusiveHolderOwner<T> {

    Map<T, Holder<T>> content_packs$getIntrusiveHolders();

}
