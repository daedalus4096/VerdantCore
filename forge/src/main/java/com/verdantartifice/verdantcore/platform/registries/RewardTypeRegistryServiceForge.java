package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.rewards.RewardType;
import com.verdantartifice.verdantcore.platform.services.registries.IRewardTypeRegistryService;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Forge implementation of the theorycrafting reward type registry service.
 *
 * @author Daedalus4096
 */
public class RewardTypeRegistryServiceForge extends AbstractCustomRegistryServiceForge<RewardType<?>> implements IRewardTypeRegistryService {
    private static final DeferredRegister<RewardType<?>> DEFERRED_TYPES = DeferredRegister.create(RegistryKeysVC.REWARD_TYPES, Constants.MOD_ID);
    private static final Supplier<IForgeRegistry<RewardType<?>>> TYPES = DEFERRED_TYPES.makeRegistry(RegistryBuilder::new);

    @Override
    protected Supplier<DeferredRegister<RewardType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Supplier<IForgeRegistry<RewardType<?>>> getRegistry() {
        return TYPES;
    }
}
