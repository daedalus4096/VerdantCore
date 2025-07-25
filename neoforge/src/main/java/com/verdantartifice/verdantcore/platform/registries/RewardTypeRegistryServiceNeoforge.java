package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.rewards.RewardType;
import com.verdantartifice.verdantcore.platform.services.registries.IRewardTypeRegistryService;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the theorycrafting reward type registry service.
 *
 * @author Daedalus4096
 */
public class RewardTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<RewardType<?>> implements IRewardTypeRegistryService {
    public static final Registry<RewardType<?>> TYPES = new RegistryBuilder<>(RegistryKeysVC.REWARD_TYPES)
            .sync(true)
            .create();
    private static final DeferredRegister<RewardType<?>> DEFERRED_TYPES = DeferredRegister.create(TYPES, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<RewardType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Registry<RewardType<?>> getRegistry() {
        return TYPES;
    }
}
