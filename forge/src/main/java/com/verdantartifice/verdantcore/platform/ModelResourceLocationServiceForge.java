package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IModelResourceLocationService;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModelResourceLocationServiceForge implements IModelResourceLocationService {
    @Override
    public String getStandaloneVariant() {
        return "";
    }

    @Override
    public String getInventoryVariant() {
        return ModelResourceLocation.INVENTORY_VARIANT;
    }
}
