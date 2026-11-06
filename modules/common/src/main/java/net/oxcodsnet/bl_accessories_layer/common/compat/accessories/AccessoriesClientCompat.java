package net.oxcodsnet.bl_accessories_layer.common.compat.accessories;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.world.item.Item;
import net.oxcodsnet.beltborne_lanterns.BLMod;
import net.oxcodsnet.beltborne_lanterns.common.LampRegistry;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Client-only helpers for Accessories integration.
 */
public final class AccessoriesClientCompat {
    private static final AtomicBoolean REGISTERED = new AtomicBoolean();
    private static final Set<Item> BOUND = ConcurrentHashMap.newKeySet();

    private AccessoriesClientCompat() {
    }

    /**
     * Marks Accessories' default renderer as disabled for all Beltborne lanterns.
     *
     * @return a task that should be executed when it is safe to rebuild the Accessories renderer cache
     */
    public static Runnable disableDefaultRendering() {
        if (!REGISTERED.compareAndSet(false, true)) {
            return AccessoriesClientCompat::refreshRendererCache;
        }

        registerNoRenderers();
        return AccessoriesClientCompat::refreshRendererCache;
    }

    private static void registerNoRenderers() {
        int added = 0;
        for (Item lamp : List.copyOf(LampRegistry.items())) {
            if (BOUND.add(lamp)) {
                AccessoriesRendererRegistry.bindItemToEmptyRenderer(lamp);
                added++;
            }
        }
        if (added > 0) {
            BLMod.LOGGER.info("Accessories default renderer disabled for {} new lamp item(s) (total {})", added, BOUND.size());
        }
    }

    public static void refreshRendererCache() {
        registerNoRenderers();
        AccessoriesRendererRegistry.onReload();
    }
}
