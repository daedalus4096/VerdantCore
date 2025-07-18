package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.theorycrafting.materials.ProjectMaterialType;
import com.verdantartifice.verdantcore.platform.services.registries.IProjectMaterialTypeRegistryService;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the project material type registry service.
 *
 * @author Daedalus4096
 */
public class ProjectMaterialTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<ProjectMaterialType<?>> implements IProjectMaterialTypeRegistryService {
    public static final Registry<ProjectMaterialType<?>> TYPES = new RegistryBuilder<>(RegistryKeysVC.PROJECT_MATERIAL_TYPES)
            .sync(true)
            .create();
    private static final DeferredRegister<ProjectMaterialType<?>> DEFERRED_TYPES = DeferredRegister.create(TYPES, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<ProjectMaterialType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Registry<ProjectMaterialType<?>> getRegistry() {
        return TYPES;
    }
}
