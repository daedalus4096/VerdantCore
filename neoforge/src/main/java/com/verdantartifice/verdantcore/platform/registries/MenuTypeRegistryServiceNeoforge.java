package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.menus.base.IMenuFactory;
import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import com.verdantartifice.verdantcore.common.registries.RegistryItemNeoforge;
import com.verdantartifice.verdantcore.platform.services.registries.IMenuTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the menu type registry service.
 *
 * @author Daedalus4096
 */
public class MenuTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<MenuType<?>> implements IMenuTypeRegistryService {
    private static final DeferredRegister<MenuType<?>> TYPES = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<MenuType<?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<MenuType<?>> getRegistry() {
        return BuiltInRegistries.MENU;
    }

    @Override
    public <T extends AbstractContainerMenu> IRegistryItem<MenuType<?>, MenuType<T>> register(String name, IMenuFactory<T> menuFactory) {
        return new RegistryItemNeoforge<>(this.getDeferredRegisterSupplier().get().register(name, () -> IMenuTypeExtension.create(menuFactory::create)));
    }
}
