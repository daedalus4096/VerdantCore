package com.verdantartifice.verdantcore.common.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Stream;

public class RegistryUtils {
    @Nullable
    public static <T> T getEntry(ResourceKey<Registry<T>> registryKey, ResourceKey<T> entryKey, RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(registryKey).get(entryKey);
    }

    @NotNull
    public static <T> Optional<Holder.Reference<T>> getHolder(ResourceKey<Registry<T>> registryKey, ResourceKey<T> entryKey, RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(registryKey).getHolder(entryKey);
    }

    @NotNull
    public static <T> Holder.Reference<T> getHolderOrDefault(ResourceKey<Registry<T>> registryKey, ResourceKey<T> entryKey, RegistryAccess registryAccess, ResourceKey<T> defaultKey) {
        Registry<T> registry = registryAccess.registryOrThrow(registryKey);
        return registry.getHolder(entryKey).orElseGet(() -> registry.getHolderOrThrow(defaultKey));
    }

    @NotNull
    public static <T> Holder.Reference<T> getHolderOrThrow(ResourceKey<Registry<T>> registryKey, ResourceKey<T> entryKey, RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(registryKey).getHolderOrThrow(entryKey);
    }

    @NotNull
    public static <T> Stream<T> stream(ResourceKey<Registry<T>> registryKey, RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(registryKey).stream();
    }
}
