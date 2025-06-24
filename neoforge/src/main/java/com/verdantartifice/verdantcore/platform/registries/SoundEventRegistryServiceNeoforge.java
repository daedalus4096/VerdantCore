package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.ISoundEventRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the sound event registry service.
 *
 * @author Daedalus4096
 */
public class SoundEventRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<SoundEvent> implements ISoundEventRegistryService {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<SoundEvent>> getDeferredRegisterSupplier() {
        return () -> SOUNDS;
    }

    @Override
    protected Registry<SoundEvent> getRegistry() {
        return BuiltInRegistries.SOUND_EVENT;
    }
}
