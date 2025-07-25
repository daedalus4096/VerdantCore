package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IMobEffectRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the mob effect registry service.
 *
 * @author Daedalus4096
 */
public class MobEffectRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<MobEffect> implements IMobEffectRegistryService {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<MobEffect>> getDeferredRegisterSupplier() {
        return () -> EFFECTS;
    }

    @Override
    protected Registry<MobEffect> getRegistry() {
        return BuiltInRegistries.MOB_EFFECT;
    }
}
