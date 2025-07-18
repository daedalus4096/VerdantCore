package com.verdantartifice.verdantcore.common.util;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class RegistryUtils {
    @Nullable
    public static <T> T getEntry(ResourceKey<Registry<T>> registryKey, ResourceKey<T> entryKey, RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(registryKey).get(entryKey);
    }

    public static <T> Stream<T> stream(ResourceKey<Registry<T>> registryKey, RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(registryKey).stream();
    }
}
