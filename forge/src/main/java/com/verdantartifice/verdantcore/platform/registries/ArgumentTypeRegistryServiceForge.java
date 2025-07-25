package com.verdantartifice.verdantcore.platform.registries;

import com.mojang.brigadier.arguments.ArgumentType;
import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import com.verdantartifice.verdantcore.platform.services.registries.IArgumentTypeRegistryService;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the command argument type registry service.
 *
 * @author Daedalus4096
 */
public class ArgumentTypeRegistryServiceForge extends AbstractBuiltInRegistryServiceForge<ArgumentTypeInfo<?, ?>> implements IArgumentTypeRegistryService {
    private static final DeferredRegister<ArgumentTypeInfo<?, ?>> TYPES = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<ArgumentTypeInfo<?, ?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<ArgumentTypeInfo<?, ?>> getRegistry() {
        return BuiltInRegistries.COMMAND_ARGUMENT_TYPE;
    }

    @Override
    public <T extends ArgumentType<?>> IRegistryItem<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<T>> registerSingleton(
            String name, Class<T> clazz, SingletonArgumentInfo<T> info) {
        return this.register(name, () -> ArgumentTypeInfos.registerByClass(clazz, info));
    }

    @Override
    public <T extends ArgumentType<?>, M extends ArgumentTypeInfo.Template<T>> IRegistryItem<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<T, M>> registerInfo(
            String name, Class<T> clazz, ArgumentTypeInfo<T, M> info) {
        return this.register(name, () -> ArgumentTypeInfos.registerByClass(clazz, info));
    }
}
