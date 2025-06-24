package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.platform.services.registries.IEntityTypeRegistryService;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Forge implementation of the entity type registry service.
 *
 * @author Daedalus4096
 */
public class EntityTypeRegistryServiceForge extends AbstractBuiltInRegistryServiceForge<EntityType<?>> implements IEntityTypeRegistryService {
    private static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Constants.MOD_ID);

    @Override
    protected Supplier<DeferredRegister<EntityType<?>>> getDeferredRegisterSupplier() {
        return () -> TYPES;
    }

    @Override
    protected Registry<EntityType<?>> getRegistry() {
        return BuiltInRegistries.ENTITY_TYPE;
    }
}
