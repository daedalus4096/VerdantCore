package com.verdantartifice.verdantcore.platform;

import com.verdantartifice.verdantcore.common.capabilities.FluidHandlerPMNeoforge;
import com.verdantartifice.verdantcore.common.capabilities.IFluidHandlerVC;
import com.verdantartifice.verdantcore.common.fluids.IFluidStackVC;
import com.verdantartifice.verdantcore.platform.services.IFluidHandlerService;

import java.util.function.Predicate;

public class FluidHandlerServiceNeoforge implements IFluidHandlerService {
    @Override
    public IFluidHandlerVC create(int capacity) {
        return new FluidHandlerPMNeoforge(capacity);
    }

    @Override
    public IFluidHandlerVC create(int capacity, Predicate<IFluidStackVC> validator) {
        return new FluidHandlerPMNeoforge(capacity, validator);
    }
}
