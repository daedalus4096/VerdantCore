package com.verdantartifice.verdantcore.common.util;

import com.verdantartifice.verdantcore.Constants;
import net.minecraft.resources.ResourceLocation;

public class ResourceUtils {
    public static ResourceLocation loc(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }
}
