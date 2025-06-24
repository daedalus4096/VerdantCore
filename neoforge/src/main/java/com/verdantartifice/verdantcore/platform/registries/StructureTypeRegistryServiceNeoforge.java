package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IStructureTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the structure type registry service.
 *
 * @author Daedalus4096
 */
public class StructureTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<StructureType<?>> implements IStructureTypeRegistryService {
    private static final DeferredRegister<StructureType<?>> TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<StructureType<?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<StructureType<?>> getRegistry() {
        return BuiltInRegistries.STRUCTURE_TYPE;
    }
}
