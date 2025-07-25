package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IMemoryModuleTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the memory module type registry service.
 *
 * @author Daedalus4096
 */
public class MemoryModuleTypeRegistryServiceForge extends AbstractBuiltInRegistryServiceForge<MemoryModuleType<?>> implements IMemoryModuleTypeRegistryService {
    private static final DeferredRegister<MemoryModuleType<?>> TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<MemoryModuleType<?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<MemoryModuleType<?>> getRegistry() {
        return BuiltInRegistries.MEMORY_MODULE_TYPE;
    }
}
