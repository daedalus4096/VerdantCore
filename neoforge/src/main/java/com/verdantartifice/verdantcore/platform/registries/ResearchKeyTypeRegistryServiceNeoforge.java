package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysPM;
import com.verdantartifice.verdantcore.common.research.keys.ResearchKeyType;
import com.verdantartifice.verdantcore.platform.services.registries.IResearchKeyTypeRegistryService;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the research key type registry service.
 *
 * @author Daedalus4096
 */
public class ResearchKeyTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<ResearchKeyType<?>> implements IResearchKeyTypeRegistryService {
    public static final Registry<ResearchKeyType<?>> TYPES = new RegistryBuilder<>(RegistryKeysPM.RESEARCH_KEY_TYPES)
            .sync(true)
            .create();
    private static final DeferredRegister<ResearchKeyType<?>> DEFERRED_TYPES = DeferredRegister.create(TYPES, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<ResearchKeyType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Registry<ResearchKeyType<?>> getRegistry() {
        return TYPES;
    }
}
