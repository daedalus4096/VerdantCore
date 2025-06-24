package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.research.topics.ResearchTopicType;
import com.verdantartifice.verdantcore.platform.services.registries.IResearchTopicTypeRegistryService;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Forge implementation of the research topic type registry service.
 *
 * @author Daedalus4096
 */
public class ResearchTopicTypeRegistryServiceForge extends AbstractCustomRegistryServiceForge<ResearchTopicType<?>> implements IResearchTopicTypeRegistryService {
    private static final DeferredRegister<ResearchTopicType<?>> DEFERRED_TYPES = DeferredRegister.create(RegistryKeysVC.RESEARCH_TOPIC_TYPES, Constants.MOD_ID);
    private static final Supplier<IForgeRegistry<ResearchTopicType<?>>> TYPES = DEFERRED_TYPES.makeRegistry(RegistryBuilder::new);

    @Override
    protected Supplier<DeferredRegister<ResearchTopicType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Supplier<IForgeRegistry<ResearchTopicType<?>>> getRegistry() {
        return TYPES;
    }
}
