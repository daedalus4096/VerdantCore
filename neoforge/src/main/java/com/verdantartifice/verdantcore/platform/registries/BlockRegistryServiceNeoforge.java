package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IBlockRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the block registry service.
 *
 * @author Daedalus4096
 */
public class BlockRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<Block> implements IBlockRegistryService {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<Block>> getDeferredRegisterSupplier() {
        return () -> BLOCKS;
    }

    @Override
    protected Registry<Block> getRegistry() {
        return BuiltInRegistries.BLOCK;
    }
}
