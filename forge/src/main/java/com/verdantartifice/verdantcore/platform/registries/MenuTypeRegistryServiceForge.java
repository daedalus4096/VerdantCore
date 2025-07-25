package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.menus.base.IMenuFactory;
import com.verdantartifice.verdantcore.common.registries.IRegistryItem;
import com.verdantartifice.verdantcore.common.registries.RegistryItemForge;
import com.verdantartifice.verdantcore.platform.services.registries.IMenuTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the menu type registry service.
 *
 * @author Daedalus4096
 */
public class MenuTypeRegistryServiceForge extends AbstractBuiltInRegistryServiceForge<MenuType<?>> implements IMenuTypeRegistryService {
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
        return new RegistryItemForge<>(this.getDeferredRegisterSupplier().get().register(name, () -> IForgeMenuType.create(menuFactory::create)));
    }
}
