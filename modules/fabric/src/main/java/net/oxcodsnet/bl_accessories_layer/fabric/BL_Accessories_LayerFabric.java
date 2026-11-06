package net.oxcodsnet.bl_accessories_layer.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.oxcodsnet.bl_accessories_layer.BL_Accessories_Layer;

public final class BL_Accessories_LayerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BL_Accessories_Layer.init(FabricLoader.getInstance().getConfigDir());
    }
}

