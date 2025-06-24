package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IBlockEntityTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the block entity type registry service.
 *
 * @author Daedalus4096
 */
public class BlockEntityTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<BlockEntityType<?>> implements IBlockEntityTypeRegistryService {
    private static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<BlockEntityType<?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<BlockEntityType<?>> getRegistry() {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE;
    }
}
