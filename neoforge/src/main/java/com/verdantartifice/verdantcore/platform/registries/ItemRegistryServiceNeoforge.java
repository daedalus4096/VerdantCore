package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IItemRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the item registry service.
 *
 * @author Daedalus4096
 */
public class ItemRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<Item> implements IItemRegistryService {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<Item>> getDeferredRegisterSupplier() {
        return () -> ITEMS;
    }

    @Override
    protected Registry<Item> getRegistry() {
        return BuiltInRegistries.ITEM;
    }
}
