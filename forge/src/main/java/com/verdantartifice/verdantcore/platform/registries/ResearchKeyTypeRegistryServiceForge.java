package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysPM;
import com.verdantartifice.verdantcore.common.research.keys.ResearchKeyType;
import com.verdantartifice.verdantcore.platform.services.registries.IResearchKeyTypeRegistryService;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Forge implementation of the research key type registry service.
 *
 * @author Daedalus4096
 */
public class ResearchKeyTypeRegistryServiceForge extends AbstractCustomRegistryServiceForge<ResearchKeyType<?>> implements IResearchKeyTypeRegistryService {
    private static final DeferredRegister<ResearchKeyType<?>> DEFERRED_TYPES = DeferredRegister.create(RegistryKeysPM.RESEARCH_KEY_TYPES, Constants.MOD_ID);
    private static final Supplier<IForgeRegistry<ResearchKeyType<?>>> TYPES = DEFERRED_TYPES.makeRegistry(RegistryBuilder::new);

    @Override
    protected Supplier<DeferredRegister<ResearchKeyType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Supplier<IForgeRegistry<ResearchKeyType<?>>> getRegistry() {
        return TYPES;
    }
}
