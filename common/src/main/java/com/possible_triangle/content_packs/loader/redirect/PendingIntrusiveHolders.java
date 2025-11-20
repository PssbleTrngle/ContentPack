package com.possible_triangle.content_packs.loader.redirect;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.possible_triangle.content_packs.Constants;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

public class PendingIntrusiveHolders {

    private static final Multimap<ResourceKey<?>, Object> ENTRIES = ArrayListMultimap.create();
    private static final Set<ResourceKey<?>> PENDING = new HashSet<>();

    public static <T> void add(ResourceKey<T> redirect, T value, IntrusiveHolderOwner<T> owner) {
        Constants.LOGGER.debug("redirect intrusive holders for {}", redirect.toString());
        if (PENDING.contains(redirect)) {
            var holders = owner.content_packs$getIntrusiveHolders();
            var holder = holders.remove(value);
            if (holder != null) {
                bind(redirect, holder);
            }
        } else {
            ENTRIES.put(redirect, value);
        }
    }

    private static <T> void bind(ResourceKey<T> key, Holder<T> holder) {
        if (!PENDING.contains(key)) return;

        if (holder instanceof Holder.Reference<T> reference) {
            // TODO bind?
            Constants.LOGGER.debug("binding redirected intrusive holder to {}", key);
        }
    }

    public static <T> void notify(ResourceKey<T> key, IntrusiveHolderOwner<T> owner) {
        var entries = (Collection<T>) ENTRIES.get(key);
        if (entries.isEmpty()) {
            PENDING.add(key);
            return;
        }

        var holders = owner.content_packs$getIntrusiveHolders();
        Constants.LOGGER.debug("notifying {} intrusive holders for {}", entries.size(), key);
        holders.forEach(($, holder) -> bind(key, holder));
        entries.forEach(holders::remove);
    }

}
