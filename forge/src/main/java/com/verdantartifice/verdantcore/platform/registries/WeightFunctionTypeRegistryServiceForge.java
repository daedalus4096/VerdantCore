package com.verdantartifice.verdantcore.platform.registries;

import com.verdantartifice.verdantcore.Constants;
import com.verdantartifice.verdantcore.common.registries.RegistryKeysVC;
import com.verdantartifice.verdantcore.common.theorycrafting.weights.WeightFunctionType;
import com.verdantartifice.verdantcore.platform.services.registries.IWeightFunctionTypeRegistryService;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * Forge implementation of the weight function type registry service.
 *
 * @author Daedalus4096
 */
public class WeightFunctionTypeRegistryServiceForge extends AbstractCustomRegistryServiceForge<WeightFunctionType<?>> implements IWeightFunctionTypeRegistryService {
    private static final DeferredRegister<WeightFunctionType<?>> DEFERRED_TYPES = DeferredRegister.create(RegistryKeysVC.PROJECT_WEIGHT_FUNCTION_TYPES, Constants.MOD_ID);
    private static final Supplier<IForgeRegistry<WeightFunctionType<?>>> TYPES = DEFERRED_TYPES.makeRegistry(RegistryBuilder::new);

    @Override
    protected Supplier<DeferredRegister<WeightFunctionType<?>>> getDeferredRegisterSupplier() {
        return () -> DEFERRED_TYPES;
    }

    @Override
    protected Supplier<IForgeRegistry<WeightFunctionType<?>>> getRegistry() {
        return TYPES;
    }
}
