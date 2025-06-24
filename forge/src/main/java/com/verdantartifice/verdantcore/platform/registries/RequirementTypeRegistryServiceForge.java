package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysPM;
import com.verdantartifice.verdantcore.common.research.requirements.RequirementType;
import com.verdantartifice.verdantcore.platform.services.registries.IRequirementTypeRegistryService;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Forge implementation of the requirement type registry service.
 *
 * @author Daedalus4096
 */
public class RequirementTypeRegistryServiceForge extends AbstractCustomRegistryServiceForge<RequirementType<?>> implements IRequirementTypeRegistryService {
    private static final DeferredRegister<RequirementType<?>> DEFERRED_TYPES = DeferredRegister.create(RegistryKeysPM.REQUIREMENT_TYPES, Constants.MOD_ID);
    private static final Supplier<IForgeRegistry<RequirementType<?>>> TYPES = DEFERRED_TYPES.makeRegistry(RegistryBuilder::new);

    @Override
    protected Supplier<DeferredRegister<RequirementType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Supplier<IForgeRegistry<RequirementType<?>>> getRegistry() {
        return TYPES;
    }
}
