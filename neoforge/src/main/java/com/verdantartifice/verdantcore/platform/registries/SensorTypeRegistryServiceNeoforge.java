package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.ISensorTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Neoforge implementation of the sensor type registry service.
 *
 * @author Daedalus4096
 */
public class SensorTypeRegistryServiceNeoforge extends AbstractRegistryServiceNeoforge<SensorType<?>> implements ISensorTypeRegistryService {
    private static final DeferredRegister<SensorType<?>> TYPES = DeferredRegister.create(Registries.SENSOR_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<SensorType<?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<SensorType<?>> getRegistry() {
        return BuiltInRegistries.SENSOR_TYPE;
    }
}
