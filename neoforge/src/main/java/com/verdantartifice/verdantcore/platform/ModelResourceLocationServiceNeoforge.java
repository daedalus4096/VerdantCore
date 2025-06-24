package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.platform.services.IModelResourceLocationService;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModelResourceLocationServiceNeoforge implements IModelResourceLocationService {
    @Override
    public String getStandaloneVariant() {
        return ModelResourceLocation.STANDALONE_VARIANT;
    }

    @Override
    public String getInventoryVariant() {
        return ModelResourceLocation.INVENTORY_VARIANT;
    }
}
