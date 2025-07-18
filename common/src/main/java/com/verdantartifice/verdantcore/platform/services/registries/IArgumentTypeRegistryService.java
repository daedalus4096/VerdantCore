package com.verdantartifice.verdantcore.platform.services.registries;

import com.mojang.brigadier.arguments.ArgumentType;
import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

public interface IArgumentTypeRegistryService extends IRegistryService<ArgumentTypeInfo<?, ?>> {
    <T extends ArgumentType<?>> IRegistryItem<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<T>> registerSingleton(
            String name, Class<T> clazz, SingletonArgumentInfo<T> info);
    <T extends ArgumentType<?>, M extends ArgumentTypeInfo.Template<T>> IRegistryItem<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<T, M>> registerInfo(
            String name, Class<T> clazz, ArgumentTypeInfo<T, M> info);
}
