package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.theorycrafting.materials.ProjectMaterialType;
import com.verdantartifice.verdantcore.platform.services.registries.IProjectMaterialTypeRegistryService;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Forge implementation of the project material type registry service.
 *
 * @author Daedalus4096
 */
public class ProjectMaterialTypeRegistryServiceForge extends AbstractCustomRegistryServiceForge<ProjectMaterialType<?>> implements IProjectMaterialTypeRegistryService {
    private static final DeferredRegister<ProjectMaterialType<?>> DEFERRED_TYPES = DeferredRegister.create(RegistryKeysVC.PROJECT_MATERIAL_TYPES, Constants.MOD_ID);
    private static final Supplier<IForgeRegistry<ProjectMaterialType<?>>> TYPES = DEFERRED_TYPES.makeRegistry(RegistryBuilder::new);

    @Override
    protected Supplier<DeferredRegister<ProjectMaterialType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Supplier<IForgeRegistry<ProjectMaterialType<?>>> getRegistry() {
        return TYPES;
    }
}
