package net.oxcodsnet.bl_accessories_layer.neoforge.compat.accessories;

import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.oxcodsnet.beltborne_lanterns.BLMod;
import net.oxcodsnet.beltborne_lanterns.common.LampRegistry;
import net.oxcodsnet.bl_accessories_layer.common.compat.accessories.AccessoriesRendererReloadScheduler;

import java.util.concurrent.atomic.AtomicBoolean;

public final class AccessoriesRendererReloadHandler {
    private static final AtomicBoolean REGISTERED = new AtomicBoolean();

    private AccessoriesRendererReloadHandler() {
    }

    public static void register(final Runnable action) {
        AccessoriesRendererReloadScheduler.setReloadAction(action);

        if (REGISTERED.compareAndSet(false, true)) {
            NeoForge.EVENT_BUS.addListener(AccessoriesRendererReloadHandler::onClientTick);
            NeoForge.EVENT_BUS.addListener(AccessoriesRendererReloadHandler::onClientLogin);
            NeoForge.EVENT_BUS.addListener(AccessoriesRendererReloadHandler::onTagsUpdated);
        } else {
            AccessoriesRendererReloadScheduler.requestReload();
        }
    }

    public static PreparableReloadListener createReloadListener() {
        return new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
                AccessoriesRendererReloadScheduler.requestReload();
            }
        };
    }

    private static void onClientTick(final ClientTickEvent.Post event) {
        AccessoriesRendererReloadScheduler.runIfReady();
    }

    private static void onClientLogin(final ClientPlayerNetworkEvent.LoggingIn event) {
        LampRegistry.init();
        AccessoriesRendererReloadScheduler.requestReload();
        BLMod.LOGGER.info("Accessories renderer cache refreshed on world join (tags bound): {} lamps", LampRegistry.items().size());
    }

    private static void onTagsUpdated(final TagsUpdatedEvent event) {
        LampRegistry.init();
        AccessoriesRendererReloadScheduler.requestReload();
    }
}
